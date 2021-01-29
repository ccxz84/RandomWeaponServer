package RWAPI.Character.Kassadin.skills;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.entity.player.EntityPlayer;

public class voidstone implements Skill {

    AttackEventClass attackEventClass;
    HurtEventClass hurtEventClass;

    public static final int reducephy = 5;
    public static final int reducemag = 10;

    protected final double[] skilldamage={
            20,
            21,
            22,
            23,
            24,
            25,
            26,
            27,
            28,
            29,
            30,
            31,
            32,
            33,
            34,
            35,
            36,
            37
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
        this.attackEventClass = new AttackEventClass(data);
        this.hurtEventClass = new HurtEventClass(data);
        main.game.getEventHandler().register(this.attackEventClass);
        main.game.getEventHandler().register(this.hurtEventClass);
    }

    @Override
    public void skillEnd(EntityPlayer player) {
        if(this.hurtEventClass != null){
            main.game.getEventHandler().unregister(this.hurtEventClass);
            this.hurtEventClass = null;
        }
        if(this.attackEventClass != null){
            main.game.getEventHandler().unregister(this.attackEventClass);
            this.attackEventClass = null;
        }
    }

    @Override
    public void raiseevent(PlayerData data, double mana) {

    }

    @Override
    public double[] getskilldamage() {
        return this.skilldamage;
    }

    @Override
    public double[] getskillAdcoe() {
        return new double[0];
    }

    @Override
    public double[] getskillApcoe() {
        return this.skillApcoe;
    }

    @Override
    public double[] getskillcost() {
        return new double[0];
    }

    @Override
    public double[] getcooldown() {
        return new double[0];
    }

    private class AttackEventClass extends PlayerAttackEventHandle {

        PlayerData data;

        public AttackEventClass(PlayerData data){
            super();
            this.data = data;
        }

        @Override
        public void EventListener(AbstractBaseEvent event) {
            PlayerAttackEvent pevent = (PlayerAttackEvent) event;
            DamageSource source = pevent.getSource();
            EntityData attacker = source.getAttacker();
            EntityData target = source.getTarget();

            if(attacker.equals(this.data) && source.getAttackType() == DamageSource.AttackType.ATTACK){
                int lv = this.data.getLevel();
                DamageSource dsource = DamageSource.causeAttackMeleeMagic(attacker,target,skilldamage[lv-1] + attacker.getAp() * skillApcoe[lv-1]);
                DamageSource.attackDamage(dsource,false);
                DamageSource.EnemyStatHandler.EnemyStatSetter(dsource);
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

    private class HurtEventClass extends PlayerAttackEventHandle {

        PlayerData data;

        public HurtEventClass(PlayerData data){
            super();
            this.data = data;
        }

        @Override
        public void EventListener(AbstractBaseEvent event) {
            PlayerAttackEvent pevent = (PlayerAttackEvent) event;
            DamageSource source = pevent.getSource();
            EntityData attacker = source.getAttacker();
            EntityData target = source.getTarget();

            if(target.equals(this.data)){
                double damage = source.getDamage();
                double heal = 0;
                if(source.getDamageType() == DamageSource.DamageType.MAGIC){
                    heal = (damage/100) * reducephy;
                }
                else if(source.getDamageType() == DamageSource.DamageType.PHYSICS){
                    heal = (damage/100) * reducemag;
                }
                double health = heal + this.data.getCurrentHealth() >= this.data.getMaxHealth() ? this.data.getMaxHealth() : heal + this.data.getCurrentHealth();
                this.data.setCurrentHealth(health);
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
