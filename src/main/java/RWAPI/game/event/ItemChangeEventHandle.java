package RWAPI.game.event;

import RWAPI.Character.PlayerData;
import RWAPI.util.DamageSource;

public abstract class ItemChangeEventHandle  extends BaseEvent{

    @Override
    public int EventCode() {
        return 3;
    }

    public static class ItemChangeEvent extends AbstractBaseEvent{

        private PlayerData data;

        public ItemChangeEvent(PlayerData data){
            this.data = data;
        }

        public PlayerData getData(){
            return this.data;
        }


        @Override
        public int EventCode() {
            return 3;
        }
    }
}
