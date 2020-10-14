package RWAPI.game.event;

import RWAPI.util.DamageSource.DamageSource;

public abstract class PlayerAttackEventHandle extends BaseEvent {

    @Override
    public int EventCode() {
        return 1;
    }

    public abstract EventPriority getPriority();

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
}
