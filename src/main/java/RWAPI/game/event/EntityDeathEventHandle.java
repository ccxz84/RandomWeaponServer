package RWAPI.game.event;

import RWAPI.util.DamageSource.DamageSource;

public abstract class EntityDeathEventHandle  extends BaseEvent{

    @Override
    public int EventCode() {
        return 2;
    }

    public abstract EventPriority getPriority();

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
}
