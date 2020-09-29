package RWAPI.game.event;

import RWAPI.Character.PlayerData;
import RWAPI.util.DamageSource;
import net.minecraft.item.ItemStack;

public abstract class ItemChangeEventHandle  extends BaseEvent{

    @Override
    public int EventCode() {
        return 3;
    }

    public static class ItemChangeEvent extends AbstractBaseEvent{

        private PlayerData data;
        private ItemStack stack;
        private boolean remove;

        public ItemChangeEvent(PlayerData data, ItemStack stack, boolean flag){
            this.data = data;
            this.stack = stack;
            this.remove = flag;
        }

        public PlayerData getData(){
            return this.data;
        }

        public ItemStack getStack(){
            return this.stack;
        }

        public boolean isRemove(){
            return this.remove;
        }

        @Override
        public int EventCode() {
            return 3;
        }
    }
}
