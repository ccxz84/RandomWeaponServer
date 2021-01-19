package RWAPI.items.gameItem.inherence;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.item.ItemStack;

public class Bladeoftheruinedking_passive extends ItemBase.inherence_handler{

    EventClass eventClass;
    PlayerData data;
    private final double reducePercent;

    public Bladeoftheruinedking_passive(PlayerData data, ItemStack stack, double reducePercent){
        super(data,stack);
        this.data = data;
        this.reducePercent = reducePercent;
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
            double damage = source.getDamage();

            EntityData data = source.getAttacker();

            if(data.equals(this.data) && source instanceof DamageSource.AttackDamage){
                double aDamage = source.getTarget().getCurrentHealth() - (source.getTarget().getCurrentHealth()/100)*reducePercent > 0 ?
                        (source.getTarget().getCurrentHealth()/100)*reducePercent : source.getTarget().getCurrentHealth();
                DamageSource sourcee = DamageSource.causeUnknownPhysics(source.getAttacker(),source.getTarget(),aDamage);
                DamageSource.attackDamage(sourcee,false);
                //source.getTarget().setCurrentHealth(source.getTarget().getCurrentHealth() - aDamage);
                //System.out.println("추가 데미지 : " + aDamage);

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
