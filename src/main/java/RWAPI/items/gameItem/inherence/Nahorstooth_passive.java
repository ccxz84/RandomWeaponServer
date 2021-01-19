package RWAPI.items.gameItem.inherence;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.item.ItemStack;

public class Nahorstooth_passive extends ItemBase.inherence_handler{

    EventClass eventClass;
    PlayerData data;
    private final double plusdamageper, plusdamage;

    public Nahorstooth_passive(PlayerData data, ItemStack stack, double plusdamageper, double plusdamage) {
        super(data, stack);
        this.data = data;
        this.plusdamage = plusdamage;
        this.plusdamageper = plusdamageper;
        registerAttackEvent();
    }

    private void registerAttackEvent() {
        this.eventClass = new EventClass(data);
        main.game.getEventHandler().register(this.eventClass);
    }

    @Override
    public void removeHandler() {
        main.game.getEventHandler().unregister(this.eventClass);
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

            EntityData data = source.getAttacker();

            if(data.equals(this.data) && source instanceof DamageSource.AttackDamage){
                double adamage = source.getAttacker().getAp() * plusdamageper + plusdamageper;
                DamageSource sourcee = DamageSource.causeUnknownMagic(source.getAttacker(),source.getTarget(),adamage);
                DamageSource.attackDamage(sourcee,false);
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
}
