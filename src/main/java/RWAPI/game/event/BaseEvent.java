package RWAPI.game.event;

public abstract class BaseEvent {

    public abstract void EventListener(AbstractBaseEvent event);

    public abstract int EventCode();

    public abstract EventPriority getPriority();

    public enum EventPriority{
        HIGHTEST,
        HIGH,
        NORMAL,
        LOW,
        LOWEST;
    }

    public abstract static class AbstractBaseEvent{
        public abstract int EventCode();

    }
}


