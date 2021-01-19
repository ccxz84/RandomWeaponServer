package RWAPI.game.event;

import RWAPI.Character.EntityData;
import RWAPI.util.DamageSource.DamageSource;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class PlayerAttackEventHandle extends BaseEvent {

    @Override
    public int EventCode() {
        return 1;
    }

    public abstract EventPriority getPriority();

    public abstract code getEventCode();

    public abstract EntityData getAttacker();

    public abstract EntityData getTarget();

    public static class PlayerAttackEvent extends AbstractBaseEvent{
        DamageSource source;


        public PlayerAttackEvent(DamageSource source){
            this.source = source;
        }

        public DamageSource getSource(){
            return source;
        }

        @Override
        public int EventCode() {
            return 1;
        }
    }

    public static class PlayerAttackEventList extends AbstractEventList{

        HashMap<EntityData, List<PlayerAttackEventHandle>> attackerlist = new HashMap<EntityData, List<PlayerAttackEventHandle>>();
        HashMap<EntityData, List<PlayerAttackEventHandle>> targetlist = new HashMap<EntityData, List<PlayerAttackEventHandle>>();
        HashMap<key, List<PlayerAttackEventHandle>> alllist = new HashMap<key, List<PlayerAttackEventHandle>>();

        @Override
        public void addlist(BaseEvent eventobject)throws ClassCastException, NullPointerException{
            PlayerAttackEventHandle handle = PlayerAttackEventHandle.class.cast(eventobject);
            code code = handle.getEventCode();
            if(code == code.attacker){
                List<PlayerAttackEventHandle> list = attackerlist.get(handle.getAttacker());
                if(list == null){
                    list = new ArrayList<PlayerAttackEventHandle>();
                    list.add(handle);
                    attackerlist.put(handle.getAttacker(), list);
                }
                else{
                    list.add(handle);
                }
            }
            else if(code == code.target){
                List<PlayerAttackEventHandle> list = targetlist.get(handle.getAttacker());
                if(list == null){
                    list = new ArrayList<PlayerAttackEventHandle>();
                    list.add(handle);
                    targetlist.put(handle.getTarget(), list);
                }
                else{
                    list.add(handle);
                }
            }
            else if(code == code.all){
                key key = new key(handle.getAttacker(),handle.getTarget());
                List<PlayerAttackEventHandle> list = alllist.get(key);
                if(list == null){
                    list = new ArrayList<PlayerAttackEventHandle>();
                    list.add(handle);
                    alllist.put(key, list);
                }
                else{
                    list.add(handle);
                }
            }
        }

        @Override
        public void removelist(BaseEvent eventobject) throws ClassCastException, NullPointerException {
            PlayerAttackEventHandle handle = PlayerAttackEventHandle.class.cast(eventobject);
            code code = handle.getEventCode();

            if(code == code.attacker){
                List<PlayerAttackEventHandle> list = attackerlist.get(handle.getAttacker());
                if(list != null){
                    list.remove(eventobject);
                }
            }
            else if(code == code.target){
                List<PlayerAttackEventHandle> list = targetlist.get(handle.getAttacker());
                if(list != null){
                    list.remove(eventobject);
                }
            }
            else if(code == code.all){
                key key = new key(handle.getAttacker(),handle.getTarget());
                List<PlayerAttackEventHandle> list = alllist.get(key);
                if(list != null){
                    list.remove(eventobject);
                }
            }
        }

        @Override
        public void runlist(AbstractBaseEvent event) throws ClassCastException, NullPointerException{
            EntityData attacker = PlayerAttackEvent.class.cast(event).getSource().getAttacker();
            EntityData target = PlayerAttackEvent.class.cast(event).getSource().getTarget();

            List<PlayerAttackEventHandle> list;
            if(attackerlist != null){
                list = attackerlist.get(attacker);
                if(list != null){
                    runlist(list,event);
                }
            }
            if(targetlist != null){
                list = targetlist.get(target);
                if(list != null){
                    runlist(list,event);
                }
            }
            if(alllist != null){
                key key = new key(attacker,target);
                list = alllist.get(key);
                if(list != null){
                    runlist(list,event);
                }
            }
        }

        public void runlist(List<PlayerAttackEventHandle> list,AbstractBaseEvent event){
            for(PlayerAttackEventHandle handle : list.stream().collect(Collectors.toList())){
                handle.EventListener(event);
            }
        }

        private class key{
            EntityData attacker, target;

            private key(EntityData attacker, EntityData target){
                this.attacker = attacker;
                this.target = target;
            }

            @Override
            public int hashCode() {
                return Objects.hash(attacker,target);
            }

            @Override
            public boolean equals(Object obj) {
                try{
                    key o = key.class.cast(obj);
                    if(o.attacker.equals(attacker) && o.target.equals(target)){
                        return true;
                    }
                }
                catch (NullPointerException | ClassCastException e){

                }
                return false;
            }
        }
    }

    public enum code{
        attacker,
        target,
        all
    }
}
