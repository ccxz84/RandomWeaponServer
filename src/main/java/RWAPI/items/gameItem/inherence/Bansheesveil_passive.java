package RWAPI.items.gameItem.inherence;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import RWAPI.util.NetworkUtil;
import net.minecraft.item.ItemStack;

public class Bansheesveil_passive extends ItemBase.inherence_handler{

    EventClass eventClass;
    PlayerData data;
    ItemStack stack;
    private int idx;
    private int cooltime;
    private cool cool;

    public Bansheesveil_passive(PlayerData data, ItemStack stack, int cooltime, int idx) {
        super(data, stack);
        this.cooltime = cooltime;
        this.data = data;
        this.stack = stack;
        this.idx = idx;
        if(!this.stack.equals(this.data.getPlayer().inventory.getStackInSlot(this.idx))){
            this.stack = this.data.getPlayer().inventory.getStackInSlot(this.idx);
        }
        registerAttackEvent();
    }

    private void registerAttackEvent() {
        this.eventClass = new EventClass(data);
        main.game.getEventHandler().register(this.eventClass);
    }

    @Override
    public void removeHandler() {
        main.game.getEventHandler().unregister(this.eventClass);
    }

    private void coolreset() {
        cool = null;
    }

    private class EventClass extends PlayerAttackEventHandle {

        PlayerData data;

        public EventClass(PlayerData data) {
            super();
            this.data = data;
        }

        @Override
        public void EventListener(AbstractBaseEvent event) {
            DamageSource source = ((PlayerAttackEvent) event).getSource();
            double damage = source.getDamage();

            EntityData target = source.getTarget();
            if (target.equals(this.data) && source instanceof DamageSource.SkillDamage && cool == null) {
                double hp = data.getCurrentHealth() + damage >= data.getMaxHealth() ? data.getMaxHealth() : data.getCurrentHealth() + damage;
                data.setCurrentHealth(hp);
                cool = new cool(cooltime,stack);
            }
        }

        @Override
        public EventPriority getPriority() {
            return EventPriority.NORMAL;
        }

        @Override
        public code getEventCode() {
            return code.target;
        }

        @Override
        public EntityData getAttacker() {
            return null;
        }

        @Override
        public EntityData getTarget() {
            return data;
        }
    }

    private class cool extends ItemBase.coolHandler {

        public cool(double timer,ItemStack stack) {
            super(timer,stack);
        }

        @Override
        public void TimerEnd() {
            NetworkUtil.setCool(stack,0);
            coolreset();
        }
    }
}
