package RWAPI.game.event;

import RWAPI.Character.ClientData;
import RWAPI.Character.PlayerData;
import RWAPI.util.StatList;
import net.minecraft.item.ItemStack;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class StatChangeEventHandle extends BaseEvent{

    public abstract PlayerData getPlayer();

    public abstract StatList getCode();

    @Override
    public int EventCode() {
        return 4;
    }

    public static class StatChangeEvent<T extends Number> extends AbstractBaseEvent{

        private PlayerData data;
        private StatList code;
        private ClientData.dataRef<T> ref;
        private T prev;

        public StatChangeEvent(PlayerData data, StatList code, ClientData.dataRef<T> ref, T prev){
            this.data = data;
            this.code = code;
            this.ref = ref;
            this.prev = prev;
        }

        public PlayerData getData(){
            return this.data;
        }

        public StatList getCode(){
            return this.code;
        }

        public ClientData.dataRef<T> getRef(){
            return this.ref;
        }

        public T getPrev(){
            return this.prev;
        }

        @Override
        public int EventCode() {
            return 4;
        }
    }

    public static class StatChangeEventList extends AbstractEventList{

        HashMap<key, List<StatChangeEventHandle>> playerlist = new HashMap<key, List<StatChangeEventHandle>>();

        @Override
        public void addlist(BaseEvent eventobject) throws ClassCastException, NullPointerException{
            StatChangeEventHandle handle = StatChangeEventHandle.class.cast(eventobject);
            PlayerData player = handle.getPlayer();
            StatList code = handle.getCode();
            key key = new key(player,code);

            List<StatChangeEventHandle> list = playerlist.get(key);
            if(list == null){
                list = new ArrayList<>();
                playerlist.put(key,list);
            }
            list.add(handle);
        }

        @Override
        public void removelist(BaseEvent eventobject) throws ClassCastException, NullPointerException {
            StatChangeEventHandle handle = StatChangeEventHandle.class.cast(eventobject);
            PlayerData player = handle.getPlayer();
            StatList code = handle.getCode();
            key key = new key(player,code);
            if(playerlist != null){
                List<StatChangeEventHandle> list = playerlist.get(key);
                if(list != null)
                    playerlist.remove(eventobject);
            }
        }

        @Override
        public void runlist(AbstractBaseEvent event) throws ClassCastException, NullPointerException {
            PlayerData player = StatChangeEvent.class.cast(event).getData();
            StatList code = StatChangeEvent.class.cast(event).getCode();
            key key = new key(player,code);
            if(playerlist != null){
                List<StatChangeEventHandle> list = playerlist.get(key);
                if(list != null){
                    for(StatChangeEventHandle handle : list.stream().collect(Collectors.toList())){
                        handle.EventListener(event);
                    }
                }
            }
        }

        private class key{
            PlayerData data;
            StatList code;

            private key(PlayerData data, StatList code){
                this.data = data;
                this.code = code;
            }

            @Override
            public int hashCode() {
                return Objects.hash(data,code);
            }

            @Override
            public boolean equals(Object obj) {
                try{
                    key o = key.class.cast(obj);
                    if(o.data.equals(data) && o.code.equals(code)){
                        return true;
                    }
                }
                catch (NullPointerException | ClassCastException e){

                }
                return false;
            }
        }
    }
}
