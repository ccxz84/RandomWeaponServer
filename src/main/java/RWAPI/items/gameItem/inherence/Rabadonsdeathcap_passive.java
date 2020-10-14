package RWAPI.items.gameItem.inherence;

import RWAPI.Character.PlayerData;
import RWAPI.game.event.BaseEvent;
import RWAPI.game.event.ItemChangeEventHandle;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.main;
import net.minecraft.item.ItemStack;

public class Rabadonsdeathcap_passive extends ItemBase.inherence_handler{
    EventClass eventClass1, eventClass2;
    PlayerData data;
    private final double APPer;

    public Rabadonsdeathcap_passive(PlayerData data, ItemStack stack, double APPer){
        super(data,stack);
        this.data = data;
        this.APPer = APPer;
        registerAttackEvent();
    }

    private void registerAttackEvent() {
        this.eventClass1 = new EventClass(data, BaseEvent.EventPriority.HIGHTEST);
        this.eventClass2 = new EventClass(data, BaseEvent.EventPriority.LOWEST);
        main.game.getEventHandler().register(this.eventClass1);
        main.game.getEventHandler().register(this.eventClass2);
    }

    @Override
    public void removeHandler() {
        main.game.getEventHandler().unregister(this.eventClass1);
        main.game.getEventHandler().unregister(this.eventClass2);
    }

    private class EventClass extends ItemChangeEventHandle {
        PlayerData data;
        EventPriority priority;

        public EventClass(PlayerData data, EventPriority priority) {
            super();
            this.data = data;
            this.priority = priority;
        }

        @Override
        public void EventListener(BaseEvent.AbstractBaseEvent event) {
            PlayerData data = ((ItemChangeEvent)event).getData();

            if(data.equals(data)){
                if(this.priority == EventPriority.HIGHTEST){
                    data.setAp(data.getAp()-(data.getAp()/(100+APPer)) * APPer);
                }
                else if(this.priority == EventPriority.LOWEST){
                    data.setAp(data.getAp()+(data.getAp()/100) * APPer);
                }
            }
        }

        @Override
        public EventPriority getPriority() {
            return this.priority;
        }
    }
}
