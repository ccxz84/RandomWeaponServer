package RWAPI.Character.Nasus.skills;

import RWAPI.Character.CooldownHandler;
import RWAPI.Character.EntityData;
import RWAPI.Character.Nasus.Nasus;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.Character.buff.Buff;
import RWAPI.game.event.BaseEvent;
import RWAPI.game.event.EntityDeathEventHandle;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.game.event.UseSkillEventHandle;
import RWAPI.init.ModItems;
import RWAPI.init.ModSkills;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class spiritfire implements Skill {

    Nasus _class;
    final int duraiton = 5;

    protected final double[] skilldamage={
            80,
            80.5,
            81,
            81.5,
            82,
            82.5,
            83,
            83.5,
            84,
            84.5,
            85,
            86,
            87,
            88,
            89,
            90,
            91,
            92
    };
    protected final double[] skillAdcoe={
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0
    };
    protected final double[] skillApcoe={
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0

    };
    protected final double[] skillcost={
            70,
            70,
            80,
            80,
            80,
            90,
            90,
            90,
            110,
            110,
            110,
            130,
            130,
            130,
            130,
            130,
            130,
            130
    };

    protected final double[] cooldown = {
            12,
            12,
            12,
            12,
            12,
            12,
            12,
            12,
            12,
            12,
            12,
            12,
            12,
            12,
            12,
            12,
            12,
            12
    };

    public spiritfire(Nasus nasus) {
        this._class = nasus;
    }

    @Override
    public void skillpreExecute(EntityPlayer player) {
        
    }

    @Override
    public void skillExecute(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();

        if(data.getCool(3) <= 0 && data.getCurrentMana() > skillcost[lv-1]) {
            float x = (float) player.getLookVec().x;
            float y = (float) player.getLookVec().y;
            float z = (float) player.getLookVec().z;
            BlockPos pos = null;
            double posx = 0, posy = 0,posz = 0;
            for(int i = 0; i < 6; i++){
                pos = new BlockPos(Math.floor(player.posX) + x * i, Math.floor(player.posY + player.eyeHeight) +y* i ,Math.floor(player.posZ)+z*i);
                if(!(player.world.getBlockState(pos).getBlock().equals(Blocks.AIR))) {
                    posx = pos.getX();
                    posy = pos.getY()+1;
                    posz = pos.getZ();
                    break;
                }
            }
            if(!(player.world.getBlockState(pos).getBlock().equals(Blocks.AIR))){
                if(player.world.getBlockState(pos).getBlock().equals(Blocks.SNOW_LAYER)) {
                    posy -= 1;
                }
                /*System.out.println("블록 : " + player.world.getBlockState(pos).getBlock().getUnlocalizedName() +
                        "좌표 X : " + posx + " Y : " + posy + " Z : " + posz);*/
                data.nonWorking = true;
                data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv-1]));
                List<Entity> entities = data.getPlayer().world.getEntitiesWithinAABBExcludingEntity(player,new AxisAlignedBB(posx,posy,posz
                        ,posx,posy,posz).grow(2.5,0.75,2));
                for(Entity entity : entities){
                    if(entity instanceof EntityPlayerMP){
                        PlayerData target = main.game.getPlayerData(entity.getUniqueID());
                        new buff(duraiton,data,target,true,true);
                    }
                }
                this.raiseevent(data,skillcost[lv-1]);
                new CooldownHandler(cooldown[lv-1], 3, (EntityPlayerMP)player,true,data.getSkillacc());
                data.nonWorking = false;
            }
        }
    }

    @Override
    public void Skillset(EntityPlayer player) {

    }

    @Override
    public void skillEnd(EntityPlayer player) {

    }

    @Override
    public void raiseevent(PlayerData data, double mana) {
        UseSkillEventHandle.UseSkillEvent event = new UseSkillEventHandle.UseSkillEvent(data,mana);
        for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
            main.game.getEventHandler().RunEvent(event,priority);
        }
    }

    @Override
    public double[] getskilldamage() {
        return skilldamage;
    }

    @Override
    public double[] getskillAdcoe() {
        return new double[0];
    }

    @Override
    public double[] getskillApcoe() {
        return skillApcoe;
    }

    @Override
    public double[] getskillcost() {
        return skillcost;
    }

    @Override
    public double[] getcooldown() {
        return cooldown;
    }



    private class buff extends Buff {

        ItemStack icon;
        EventClass eventClass;
        PlayerData attacker;

        public buff(double duration,PlayerData attacker, PlayerData target, boolean debuff, boolean clean, double... data) {
            super(duration, target, debuff, clean, data);
            this.attacker = attacker;
            icon = new ItemStack(ModSkills.spiritfire); //바꿔야할 것
            registerevent();
        }

        private void registerevent() {
            this.eventClass = new EventClass(attacker,player);
            main.game.getEventHandler().register(this.eventClass);
        }

        @Override
        public void setEffect() {
            this.player.addBuff(this);
        }

        @Override
        public void resetEffect() {
            if(eventClass != null){
                main.game.getEventHandler().unregister(eventClass);
            }
            this.player.removeBuff(this);
        }


        @Override
        public ItemStack getBuffIcon() {
            return icon;
        }

        private class EventClass extends PlayerAttackEventHandle {

            PlayerData attacker, target;

            public EventClass(PlayerData attacker, PlayerData target) {
                super();
                this.attacker = attacker;
                this.target = target;
            }

            @Override
            public void EventListener(AbstractBaseEvent event) {
                PlayerAttackEvent devent = (PlayerAttackEvent) event;
                EntityData attacker = devent.getSource().getAttacker();
                EntityData target = devent.getSource().getTarget();

                if(this.attacker.equals(attacker) && this.target.equals(target)){
                    int lv = ((PlayerData)attacker).getLevel();
                    try{
                        Nasus nasus = Nasus.class.cast(((PlayerData) attacker).get_class());
                        DamageSource source = DamageSource.causeSkillMeleeMagic(attacker,target,skilldamage[lv-1] + (attacker.getAp() * skillApcoe[lv-1]) + nasus.getStack());
                        DamageSource.attackDamage(source,false);
                        DamageSource.EnemyStatHandler.EnemyStatSetter(source);
                        timer = duration * 40;
                    }
                    catch (ClassCastException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public EventPriority getPriority() {
                return EventPriority.NORMAL;
            }

            @Override
            public code getEventCode() {
                return code.all;
            }

            @Override
            public EntityData getAttacker() {
                return attacker;
            }

            @Override
            public EntityData getTarget() {
                return target;
            }
        }
    }
}
