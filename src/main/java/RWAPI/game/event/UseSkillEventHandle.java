package RWAPI.game.event;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.util.DamageSource.DamageSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public abstract class UseSkillEventHandle extends BaseEvent {

    @Override
    public int EventCode() {
        return 5;
    }

    public abstract EventPriority getPriority();

    public abstract PlayerData getPlayer();

    public static class UseSkillEvent extends AbstractBaseEvent{
        private PlayerData data;
        private double mana;

        public UseSkillEvent(PlayerData data, double mana){
            this.data = data;
            this.mana = mana;
        }

        public PlayerData getData(){
            return this.data;
        }

        public double getMana(){
            return this.mana;
        }

        @Override
        public int EventCode() {
            return 5;
        }
    }

    public static class UseSkillEventList extends AbstractEventList{

        HashMap<PlayerData, List<UseSkillEventHandle>> playerlist = new HashMap<PlayerData, List<UseSkillEventHandle>>();

        @Override
        public void addlist(BaseEvent eventobject) throws ClassCastException, NullPointerException{
            UseSkillEventHandle handle = UseSkillEventHandle.class.cast(eventobject);
            PlayerData player = handle.getPlayer();

            List<UseSkillEventHandle> list = playerlist.get(player);
            if(list == null){
                list = new ArrayList<>();
                playerlist.put(player,list);
            }
            list.add(handle);
        }

        @Override
        public void removelist(BaseEvent eventobject) throws ClassCastException, NullPointerException {
            UseSkillEventHandle handle = UseSkillEventHandle.class.cast(eventobject);
            PlayerData player = handle.getPlayer();

            if(playerlist != null){
                List<UseSkillEventHandle> list = playerlist.get(player);
                if(list != null)
                    list.remove(eventobject);
            }
        }

        @Override
        public void runlist(AbstractBaseEvent event) throws ClassCastException, NullPointerException{
            PlayerData player = UseSkillEventHandle.UseSkillEvent.class.cast(event).getData();
            if(playerlist != null){
                List<UseSkillEventHandle> list = playerlist.get(player);
                if(list != null){
                    for(UseSkillEventHandle handle : list.stream().collect(Collectors.toList())){
                        handle.EventListener(event);
                    }
                }
            }
        }
    }
}
