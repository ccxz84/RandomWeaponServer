package RWAPI.items.gameItem.inherence;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.EntityDeathEventHandle;
import RWAPI.items.gameItem.Hunterstalisman;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.item.ItemStack;

public class Hunterstalisman_passive extends ItemBase.inherence_handler{

    EventClass eventClass;
    PlayerData data;
    private final int plusGold;

    public Hunterstalisman_passive(PlayerData data, ItemStack stack,int plusGold){
        super(data,stack);
        this.data = data;
        this.plusGold = plusGold;
        registerDeathEvent();
    }

    private void registerDeathEvent() {
        this.eventClass = new EventClass(data);
        main.game.getEventHandler().register(this.eventClass);
    }

    @Override
    public void removeHandler() {
        main.game.getEventHandler().unregister(this.eventClass);
    }

    private class EventClass extends EntityDeathEventHandle {
        PlayerData data;

        public EventClass(PlayerData data) {
            super();
            this.data = data;
        }

        @Override
        public void EventListener(AbstractBaseEvent event) {
            DamageSource source = ((EntityDeathEvent)event).getSource();
            double damage = source.getDamage();

            EntityData data = source.getAttacker();

            if(data.equals(this.data)){
                ((PlayerData)data).setGold(plusGold + ((PlayerData)data).getGold());
            }
        }

        @Override
        public EventPriority getPriority() {
            return EventPriority.NORMAL;
        }
    }
}
