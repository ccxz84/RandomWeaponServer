package RWAPI.game.event;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.util.DamageSource.DamageSource;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class EntityDeathEventHandle  extends BaseEvent{

    @Override
    public int EventCode() {
        return 2;
    }

    public abstract EventPriority getPriority();

    public abstract code getEventCode();

    public abstract EntityData getAttacker();

    public abstract EntityData getTarget();


    public static class EntityDeathEvent extends AbstractBaseEvent {
        DamageSource source;

        public EntityDeathEvent(DamageSource source){
            this.source = source;
        }

        public DamageSource getSource(){
            return source;
        }

        @Override
        public int EventCode() {
            return 2;
        }
    }

    public static class EntityDeathEventList extends AbstractEventList{

        HashMap<EntityData, List<EntityDeathEventHandle>> attackerlist = new HashMap<EntityData, List<EntityDeathEventHandle>>();
        HashMap<EntityData, List<EntityDeathEventHandle>> targetlist = new HashMap<EntityData, List<EntityDeathEventHandle>>();
        HashMap<key, List<EntityDeathEventHandle>> alllist = new HashMap<key, List<EntityDeathEventHandle>>();

        @Override
        public void addlist(BaseEvent eventobject) throws ClassCastException, NullPointerException{
            EntityDeathEventHandle handle = EntityDeathEventHandle.class.cast(eventobject);
            code code = handle.getEventCode();
            if(code == code.attacker && handle.getAttacker() != null){
                List<EntityDeathEventHandle> list = attackerlist.get(handle.getAttacker());
                if(list == null){
                    list = new ArrayList<EntityDeathEventHandle>();
                    list.add(handle);
                    attackerlist.put(handle.getAttacker(), list);
                }
                else{
                    list.add(handle);
                }
            }
            else if(code == code.target && handle.getTarget() != null){
                List<EntityDeathEventHandle> list = targetlist.get(handle.getTarget());
                if(list == null){
                    list = new ArrayList<EntityDeathEventHandle>();
                    list.add(handle);
                    targetlist.put(handle.getTarget(), list);
                }
                else{
                    list.add(handle);
                }
            }
            else if(code == code.all && handle.getTarget() != null && handle.getAttacker() != null){
                key key = new key(handle.getAttacker(),handle.getTarget());
                List<EntityDeathEventHandle> list = alllist.get(key);
                if(list == null){
                    list = new ArrayList<EntityDeathEventHandle>();
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
            EntityDeathEventHandle handle = EntityDeathEventHandle.class.cast(eventobject);
            code code = handle.getEventCode();

            if(code == code.attacker){
                if(attackerlist != null){
                    List<EntityDeathEventHandle> list = attackerlist.get(handle.getAttacker());
                    if(list != null){
                        list.remove(eventobject);
                    }
                }
            }
            else if(code == code.target){
                if(targetlist != null){
                    List<EntityDeathEventHandle> list = targetlist.get(handle.getTarget());
                    if(list != null){
                        list.remove(eventobject);
                    }
                }
            }
            else if(code == code.all){
                if(alllist != null){
                    key key = new key(handle.getAttacker(),handle.getTarget());
                    List<EntityDeathEventHandle> list = alllist.get(key);
                    if(list != null){
                        list.remove(eventobject);
                    }
                }
            }
        }

        @Override
        public void runlist(AbstractBaseEvent event) throws ClassCastException, NullPointerException{
            EntityData attacker = EntityDeathEvent.class.cast(event).getSource().getAttacker();
            EntityData target = EntityDeathEvent.class.cast(event).getSource().getTarget();

            List<EntityDeathEventHandle> list;
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

        public void runlist(List<EntityDeathEventHandle> list,AbstractBaseEvent event){
            for(EntityDeathEventHandle handle : list.stream().collect(Collectors.toList())){
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
