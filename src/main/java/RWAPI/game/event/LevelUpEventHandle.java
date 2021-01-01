package RWAPI.game.event;

import RWAPI.Character.PlayerData;
import net.minecraft.item.ItemStack;

public abstract class LevelUpEventHandle extends BaseEvent{

    @Override
    public int EventCode() {
        return 4;
    }

    public static class LevelUpEvent extends AbstractBaseEvent{

        private PlayerData data;

        public LevelUpEvent(PlayerData data){
            this.data = data;
        }

        public PlayerData getData(){
            return this.data;
        }

        @Override
        public int EventCode() {
            return 4;
        }
    }
}
