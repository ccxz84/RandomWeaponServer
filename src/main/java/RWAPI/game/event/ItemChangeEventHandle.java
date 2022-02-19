package RWAPI.game.event;

import RWAPI.Character.PlayerData;
import net.minecraft.item.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ItemChangeEventHandle  extends BaseEvent{

    @Override
    public int EventCode() {
        return 3;
    }

    public abstract PlayerData getPlayer();

    public static class ItemChangeEvent extends AbstractBaseEvent{

        private PlayerData data;
        private ItemStack stack;
        private boolean remove;

        public ItemChangeEvent(PlayerData data, ItemStack stack, boolean flag){
            this.data = data;
            this.stack = stack;
            this.remove = flag;
        }

        public PlayerData getData(){
            return this.data;
        }

        public ItemStack getStack(){
            return this.stack;
        }

        public boolean isRemove(){
            return this.remove;
        }

        @Override
        public int EventCode() {
            return 3;
        }
    }

    public static class ItemChangeEventList extends AbstractEventList{

        HashMap<PlayerData, List<ItemChangeEventHandle>> playerlist = new HashMap<PlayerData, List<ItemChangeEventHandle>>();

        @Override
        public void addlist(BaseEvent eventobject) throws ClassCastException, NullPointerException {
            ItemChangeEventHandle handle = ItemChangeEventHandle.class.cast(eventobject);
            PlayerData player = handle.getPlayer();
            if(player == null)
                return;
            List<ItemChangeEventHandle> list = playerlist.get(player);
            if(list == null){
                list = new ArrayList<>();
                playerlist.put(player,list);
            }
            list.add(handle);
        }

        @Override
        public void removelist(BaseEvent eventobject) throws ClassCastException, NullPointerException {
            ItemChangeEventHandle handle = ItemChangeEventHandle.class.cast(eventobject);
            PlayerData player = handle.getPlayer();

            if(playerlist != null){
                List<ItemChangeEventHandle> list = playerlist.get(player);
                if(list != null)
                    list.remove(eventobject);
            }

        }

        @Override
        public void runlist(AbstractBaseEvent event) throws ClassCastException, NullPointerException {
            PlayerData player = ItemChangeEvent.class.cast(event).getData();
            if(playerlist != null) {
                List<ItemChangeEventHandle> list = playerlist.get(player);
                if(list != null){
                    for (ItemChangeEventHandle handle : list.stream().collect(Collectors.toList())) {
                        handle.EventListener(event);
                    }
                }
            }
        }
    }

}
