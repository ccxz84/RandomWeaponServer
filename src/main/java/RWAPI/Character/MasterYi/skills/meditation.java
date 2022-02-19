package RWAPI.Character.MasterYi.skills;

import RWAPI.Character.*;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.init.ModSkills;
import RWAPI.main;
import RWAPI.Character.buff.Buff;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class meditation extends MasterYiS {

    private PlayerClass _class;
    private bufftimer buft;
    private cool cool;


    public meditation(PlayerClass _class){
        this._class = _class;
    }

    protected final double[] skilldamage={
            0.2,
            0.25,
            0.3,
            0.35,
            0.4,
            0.45,
            0.5,
            0.55,
            0.6,
            0.65,
            0.7,
            0.8,
            0.9,
            1,
            1.1,
            1.2,
            1.3,
            1.4

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
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3

    };
    protected final double[] skillcost={
            50,
            50,
            50,
            50,
            50,
            70,
            70,
            70,
            70,
            70,
            70,
            80,
            80,
            80,
            80,
            80,
            80,
            80
    };

    protected final double[] cooldown = {
            25,
            25,
            25,
            25,
            25,
            22,
            22,
            22,
            19,
            19,
            19,
            16,
            16,
            16,
            16,
            16,
            16,
            16

    };

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
        if(data.getCool(2) <= 0 && data.getCurrentMana() > skillcost[lv-1]/*&& data.nonWorking == false*/ && cool == null) {
            data.nonWorking = true;
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv-1]));
            data.nonWorking = false;
            this.raiseevent(data,skillcost[lv-1]);
            cool = new cool(cooldown[lv-1],2, (EntityPlayerMP) player,data.getSkillacc());
            buft =  new bufftimer(4, data,(float)skilldamage[lv-1] + ((float)skillApcoe[lv-1] * data.getAp())/40);
        }
    }

    @Override
    public void Skillset(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        buft = null;
        cool = null;
        data.nonWorking = false;
    }

    @Override
    public void skillEnd(EntityPlayer player) {

    }

    @Override
    void reduceCool() {
        if(cool != null)
            this.cool.reduceCool();
    }

    class cool extends CooldownHandler {

        public cool(double cool, int id, EntityPlayerMP player,int skillacc) {
            super(cool, id, player,true,skillacc);
        }

        public void reduceCool(){
            this.skillTimer += cooldown * 0.7;
        }
    }

    class bufftimer extends Buff {

        int x,y,z;
        EventClass eventClass;

        public bufftimer(double duration, PlayerData player, double... data) {
            super(duration, player,false,false, data);
            registerAttackEvent();
        }

        private void registerAttackEvent() {
            this.eventClass = new EventClass(player);
            main.game.getEventHandler().register(this.eventClass);
        }



        @Override
        public void setEffect() {
            x = (int) player.getPlayer().posX;
            y = (int) player.getPlayer().posY;
            z = (int) player.getPlayer().posZ;
            player.addBuff(this);
        }

        @Override
        public void resetEffect() {
            player.getPlayer().hurtResistantTime=0;
            player.getPlayer().maxHurtResistantTime = 0;
            main.game.getEventHandler().unregister(this.eventClass);
            player.removeBuff(this);
            Skillset(player.getPlayer());
        }

        @Override
        public ItemStack getBuffIcon() {
            return new ItemStack(ModSkills.meditation);
        }

        @Override
        public void BuffTimer(TickEvent.ServerTickEvent event) throws Throwable {
            if((int)player.getPlayer().posX != x || (int)player.getPlayer().posY != y || (int)player.getPlayer().posZ != z || player.nonWorking == true) {
                resetEffect();
                MinecraftForge.EVENT_BUS.unregister(this);
            }
            super.BuffTimer(event);
            double dehealth = player.getMaxHealth() - player.getCurrentHealth();
            player.setCurrentHealth((player.getCurrentHealth() + data[0] + dehealth * 0.003) > player.getMaxHealth() ? player.getMaxHealth() : player.getCurrentHealth() + data[0] + dehealth * 0.003);
        }

        private class EventClass extends PlayerAttackEventHandle {
            PlayerData data;

            public EventClass(PlayerData data) {
                super();
                this.data = data;
            }

            @Override
            public void EventListener(AbstractBaseEvent event) {
                DamageSource source = ((PlayerAttackEvent)event).getSource();
                double damage = source.getDamage();

                EntityData target = source.getTarget();

                if(data.equals(target)){
                    if(!(data.getCurrentHealth() <= 0)){

                        double health = source.getTarget().getCurrentHealth() + (((PlayerAttackEvent) event).getSource().getDamage() * 0.2) > 0 ?
                                (((PlayerAttackEvent) event).getSource().getDamage() * 0.2) : source.getTarget().getMaxHealth();
                        source.getTarget().setCurrentHealth(source.getTarget().getCurrentHealth() + health);
                    }

                }
            }

            @Override
            public EventPriority getPriority() {
                return EventPriority.NORMAL;
            }

            @Override
            public code getEventCode() {
                return code.target;
            }

            @Override
            public EntityData getAttacker() {
                return null;
            }

            @Override
            public EntityData getTarget() {
                return data;
            }
        }
    }

    @Override
    public double[] getskilldamage() {
        return this.skilldamage;
    }

    @Override
    public double[] getskillAdcoe() {
        return this.skillAdcoe;
    }

    @Override
    public double[] getskillApcoe() {
        return this.skillApcoe;
    }

    @Override
    public double[] getskillcost() {
        return this.skillcost;
    }

    @Override
    public double[] getcooldown() {
        return this.cooldown;
    }
}
