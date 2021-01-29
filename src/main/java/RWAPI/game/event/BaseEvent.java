package RWAPI.game.event;

import java.lang.reflect.InvocationTargetException;

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

    public abstract static class AbstractEventList{
        public abstract void addlist(BaseEvent eventobject) throws ClassCastException , NullPointerException;

        public abstract void removelist(BaseEvent eventobject) throws ClassCastException , NullPointerException;

        public abstract void runlist(BaseEvent.AbstractBaseEvent event) throws ClassCastException, NullPointerException;
    }


}


