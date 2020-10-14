package RWAPI.Character.MasterYi.skills;

import RWAPI.Character.*;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.main;
import RWAPI.Character.buff.Buff;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
            cool = new cool(cooldown[lv-1],2, (EntityPlayerMP) player);
            buft =  new bufftimer(4, (EntityPlayerMP) player,(float)skilldamage[lv-1] + ((float)skillApcoe[lv-1] * data.getAp())/40);
            data.addBuff(buft);
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

        public cool(double cool, int id, EntityPlayerMP player) {
            super(cool, id, player);
        }

        public void reduceCool(){
            this.skillTimer += cooldown * 0.7;
        }
    }

    class bufftimer extends Buff {

        int x,y,z;
        PlayerData pdata;
        EventClass eventClass;

        public bufftimer(double duration, EntityPlayerMP player, double... data) {
            super(duration, player, data);
            registerAttackEvent();
        }

        private void registerAttackEvent() {
            this.eventClass = new EventClass(pdata);
            main.game.getEventHandler().register(this.eventClass);
        }



        @Override
        public void setEffect() {
            x = (int) player.posX;
            y = (int) player.posY;
            z = (int) player.posZ;
            pdata = main.game.getPlayerData(player.getUniqueID());
        }

        @Override
        public void resetEffect() {
            pdata.getPlayer().hurtResistantTime=0;
            pdata.getPlayer().maxHurtResistantTime = 0;
            main.game.getEventHandler().unregister(eventClass);
            pdata.removeBuff(this);
            Skillset(player);
        }

        @Override
        public void BuffTimer(TickEvent.ServerTickEvent event) throws Throwable {
            if((int)player.posX != x || (int)player.posY != y || (int)player.posZ != z || pdata.nonWorking == true) {
                resetEffect();
                MinecraftForge.EVENT_BUS.unregister(this);
            }
            super.BuffTimer(event);
            double dehealth = pdata.getMaxHealth() - pdata.getCurrentHealth();
            pdata.setCurrentHealth((pdata.getCurrentHealth() + data[0] + dehealth * 0.003) > pdata.getMaxHealth() ? pdata.getMaxHealth() : pdata.getCurrentHealth() + data[0] + dehealth * 0.003);
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
