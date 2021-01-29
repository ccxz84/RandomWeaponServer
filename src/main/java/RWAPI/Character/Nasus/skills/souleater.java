package RWAPI.Character.Nasus.skills;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.Character.buff.Buff;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.init.ModSkills;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class souleater implements Skill {

    EventClass event;
    buff buff;

    protected final double[] skilldamage={
            12,
            12,
            12,
            12,
            12,
            12,
            12,
            12,
            18,
            18,
            18,
            24,
            24,
            24,
            24,
            24,
            24,
            24
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

    protected final double[] cooldown = {
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

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {

    }

    @Override
    public void Skillset(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        event = new EventClass(data);
        main.game.getEventHandler().register(event);
        buff = new buff(1,data,false,false);
    }

    @Override
    public void skillEnd(EntityPlayer player) {
        if(event != null) {
            main.game.getEventHandler().unregister(event);
        }
    }

    @Override
    public void raiseevent(PlayerData data, double mana) {

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
        return new double[0];
    }

    @Override
    public double[] getskillcost() {
        return new double[0];
    }

    @Override
    public double[] getcooldown() {
        return new double[0];
    }

    public void setstack(int stack){
        if(buff != null){
            buff.setstack(stack);
        }
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

            EntityData data = source.getAttacker();

            if(this.data.equals(data) && source.getDamageType() == DamageSource.DamageType.PHYSICS){
                if(this.data.getCurrentHealth() < this.data.getMaxHealth()){
                    int lv = this.data.getLevel();
                    double vamPercent = skilldamage[lv-1];
                    double heal = this.data.getCurrentHealth() + (damage/100) * vamPercent > this.data.getMaxHealth() ?
                            this.data.getMaxHealth() : this.data.getCurrentHealth() + (damage/100) * vamPercent;
                    this.data.setCurrentHealth(heal);
                }
            }
        }

        @Override
        public EventPriority getPriority() {
            return EventPriority.NORMAL;
        }

        @Override
        public code getEventCode() {
            return code.attacker;
        }

        @Override
        public EntityData getAttacker() {
            return data;
        }

        @Override
        public EntityData getTarget() {
            return null;
        }
    }

    private class buff extends Buff {

        ItemStack icon;

        public buff(double duration, PlayerData player, boolean debuff, boolean clean, double... data) {
            super(duration, player, debuff, clean, data);
            icon = new ItemStack(ModSkills.siphoningstrike);
            MinecraftForge.EVENT_BUS.unregister(this);
            this.duration = 0;
        }

        @Override
        public void setEffect() {
            this.player.addBuff(this);
        }

        @Override
        public void resetEffect() {
            this.player.removeBuff(this);
        }

        @Override
        public ItemStack getBuffIcon() {
            return icon;
        }

        public void setstack(int stack){
            this.duration = stack * 40;
        }
    }
}

