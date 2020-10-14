package RWAPI.items.gameItem.inherence;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.main;
import RWAPI.util.DamageSource.*;
import net.minecraft.item.ItemStack;

public class Thorn_passive extends ItemBase.inherence_handler{

    private final double armorPer;
    private final double damage;
    PlayerData data;
    EventClass eventClass;

    public Thorn_passive(PlayerData data, ItemStack stack, double damage, double armorPer) {
        super(data, stack);
        this.data = data;
        this.armorPer = armorPer;
        this.damage = damage;
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

            EntityData data = source.getTarget();

            if(data.equals(this.data) && source instanceof AttackPhysicsDamageSource){

                double aDamage = damage + (data.getArmor()/100)*armorPer;
                DamageSource sourcee = DamageSource.causeUnknownMagic(source.getTarget(),source.getAttacker(),aDamage);
                DamageSource.attackDamage(sourcee,false);
                //System.out.println("추가 데미지 : " + aDamage);

            }
        }

        @Override
        public EventPriority getPriority() {
            return EventPriority.NORMAL;
        }
    }
}
