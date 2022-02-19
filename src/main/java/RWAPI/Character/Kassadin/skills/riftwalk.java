package RWAPI.Character.Kassadin.skills;

import RWAPI.Character.*;
import RWAPI.Character.Nasus.skills.spiritfire;
import RWAPI.Character.buff.Buff;
import RWAPI.Character.monster.entity.IMob;
import RWAPI.game.event.BaseEvent;
import RWAPI.game.event.UseSkillEventHandle;
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

public class riftwalk implements Skill {

    private int stack = 0;
    public final int maxstack = 4;
    public final int duration = 15;

    private buff handler;

    protected final double[][] skilldamage={
            {
                    60,
                    62.5,
                    65,
                    67.5,
                    70,
                    72.5,
                    75,
                    77.5,
                    80,
                    82.5,
                    85,
                    88,
                    91,
                    94,
                    97,
                    100,
                    103,
                    106
            },
            {
                    20,
                    22.5,
                    25,
                    27.5,
                    30,
                    32.5,
                    35,
                    37.5,
                    40,
                    42.5,
                    45,
                    48,
                    51,
                    54,
                    57,
                    60,
                    63,
                    66
            }
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
    protected final double[][] skillApcoe={
            {
                    0.4,
                    0.4,
                    0.4,
                    0.4,
                    0.4,
                    0.4,
                    0.4,
                    0.4,
                    0.4,
                    0.4,
                    0.4,
                    0.4,
                    0.4,
                    0.4,
                    0.4,
                    0.4,
                    0.4,
                    0.4
            },
            {
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1
            },
            {
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1,
                    0.1
            },
            {
                    0.05,
                    0.05,
                    0.05,
                    0.05,
                    0.05,
                    0.05,
                    0.05,
                    0.05,
                    0.05,
                    0.05,
                    0.05,
                    0.05,
                    0.05,
                    0.05,
                    0.05,
                    0.05,
                    0.05,
                    0.05
            }
    };
    protected final double[] skillcost={
            50,
            50,
            50,
            50,
            50,
            50,
            50,
            50,
            50,
            50,
            50,
            50,
            50,
            50,
            50,
            50,
            50,
            50
    };
    protected final double[] cooldown = {
            5,
            5,
            5,
            5,
            5,
            4,
            4,
            4,
            4,
            4,
            4,
            3,
            3,
            3,
            3,
            2,
            2,
            2
    };

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
        double mana = skillcost[lv-1];
        for(int i = 0; i < stack; i++){
            mana *= 2;
        }
        if(data.getCool(4) <= 0&& data.getCurrentMana() > mana) {
            data.nonWorking = true;
            data.setCurrentMana((float) (data.getCurrentMana() - mana));
            double posx = 0, posy = 0,posz = 0;
            float x = (float) player.getLookVec().x;
            float y = (float) player.getLookVec().y;
            float z = (float) player.getLookVec().z;

            for(int i = 7; i >= 0; --i){
                BlockPos pos = new BlockPos(player.posX + i*(x * 0.7),(player.posY) + i*(y * 0.7),player.posZ + i*(z * 0.7));
                if(player.onGround && y < 0) {
                    y=0;
                }
                if(!(player.world.getBlockState(pos).getBlock().equals(Blocks.AIR))&&!(player.world.getBlockState(pos).getBlock().equals(Blocks.SNOW_LAYER)) && y != 0) {
                    continue;
                }
                ((EntityPlayerMP)player).connection.setPlayerLocation(player.posX + i*(x * 0.7),player.posY + i*(y * 0.7),player.posZ + i*(z * 0.7), player.rotationYaw, player.rotationPitch);
                break;
            }

            posx = data.getEntity().posX;
            posy = data.getEntity().posY;
            posz = data.getEntity().posZ;

            List<Entity> entities = data.getPlayer().world.getEntitiesWithinAABBExcludingEntity(player,new AxisAlignedBB(posx,posy,posz
                    ,posx,posy,posz).grow(2.5,0.75,2));
            double damage = (skilldamage[0][lv-1] + skillApcoe[0][lv-1] * data.getAp() + skillApcoe[1][lv-1] * data.getMaxMana())
                    + stack * (skilldamage[1][lv-1] + skillApcoe[2][lv-1] * data.getAp() + skillApcoe[3][lv-1] * data.getMaxMana());
            for(Entity entity : entities){
                EntityData target = null;
                if(entity instanceof EntityPlayerMP){
                    target = main.game.getPlayerData(entity.getUniqueID());
                }
                else if(entity instanceof IMob){
                    target = ((IMob) entity).getData();
                }
                if(target != null){
                    DamageSource source = DamageSource.causeSkillRangedMagic(data,target,damage);
                    DamageSource.attackDamage(source,true);
                    DamageSource.EnemyStatHandler.EnemyStatSetter(source);
                    target.getEntity().attackEntityFrom(net.minecraft.util.DamageSource.causeThrownDamage(new SkillEntity(data.getEntity().world), data.getEntity()), (float)1);
                }
            }
            if(stack < maxstack){
                ++stack;
            }
            if(handler != null){
                this.handler.resettimer();
            }
            else{
                this.handler = new buff(duration,data,false,false);
            }
            this.raiseevent(data,mana);
            new CooldownHandler(cooldown[lv-1], 4, (EntityPlayerMP)player,true,data.getSkillacc());
            data.nonWorking = false;
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
        return skilldamage[0];
    }

    @Override
    public double[] getskillAdcoe() {
        return new double[0];
    }

    @Override
    public double[] getskillApcoe() {
        return new double[0];
    }

    @Override
    public double[] getskillcost() {
        return skillcost;
    }

    @Override
    public double[] getcooldown() {
        return cooldown;
    }

    public double[][] getskillapcoe(){
        return this.skillApcoe;
    }

    public double[] getskill1damage(){
        return this.skilldamage[1];
    }

    class buff extends Buff {

        ItemStack icon;

        public buff(double duration, PlayerData player, boolean debuff, boolean clean, double... data) {
            super(duration, player, debuff, clean, data);
            icon = new ItemStack(ModSkills.riftwalk);
        }

        public void resettimer(){
            timer = 0;
        }

        @Override
        public void setEffect() {
            player.addBuff(this);
        }

        @Override
        public void resetEffect() {
            player.removeBuff(this);
            resetstack();
        }

        @Override
        public ItemStack getBuffIcon() {
            return icon;
        }
    }

    private void resetstack() {
        this.handler = null;
        stack = 0;
    }
}
