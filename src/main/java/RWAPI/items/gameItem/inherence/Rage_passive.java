package RWAPI.items.gameItem.inherence;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.buff.Buff;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class Rage_passive extends ItemBase.inherence_handler{

    PlayerData data;
    ItemStack stack;
    buffClass buff = null;
    EventClass EventClass;
    final int duration;
    final double move;

    public Rage_passive(PlayerData data, ItemStack stack, int duration, double move) {
        super(data, stack);
        this.data = data;
        this.stack = stack;
        this.duration = duration;
        this.move = move;
        registerAttackEvent();
    }

    private void registerAttackEvent() {
        EventClass = new EventClass(data);
        main.game.getEventHandler().register(EventClass);
    }

    @Override
    public void removeHandler() {
        if(buff != null){
            buff.remove();
            buff = null;
        }
        if(EventClass != null){
            main.game.getEventHandler().register(EventClass);
            EventClass = null;
        }
    }

    private void resetbuff(){
        buff = null;
    }

    private class EventClass extends PlayerAttackEventHandle {

        PlayerData data;

        public EventClass(PlayerData data) {
            super();
            this.data = data;
        }

        @Override
        public void EventListener(AbstractBaseEvent event) {
            DamageSource source = ((PlayerAttackEvent)event).getSource();
            EntityData attacker = source.getAttacker();

            if(attacker.equals(data) && source.getAttackType() == DamageSource.AttackType.ATTACK){
                if(buff == null){
                    if(source.isRanged()) {
                        buff = new buffClass(duration, data, false, false, move / 2);
                    }
                    else{
                        buff = new buffClass(duration,data,false,false,move);
                    }
                }
                else{
                    buff.resettimer();
                }
            }
        }

        @Override
        public EventPriority getPriority() {
            return EventPriority.NORMAL;
        }

        @Override
        public code getEventCode() {
            return code.attacker;
        }

        @Override
        public EntityData getAttacker() {
            return data;
        }

        @Override
        public EntityData getTarget() {
            return null;
        }
    }

    private class buffClass extends Buff {
        ItemStack icon;

        public buffClass(double duration, PlayerData player, boolean debuff, boolean clean, double... data) {
            super(duration, player, debuff, clean, data);
            this.icon = stack.copy();
        }

        @Override
        public void setEffect() {
            player.setMove(player.getMove() + data[0]);
            player.addBuff(this);
        }

        @Override
        public void resetEffect() {
            player.setMove(player.getMove() - data[0]);
            player.removeBuff(this);
            resetbuff();
        }

        public void resettimer(){
            this.timer = 0;
        }

        public void remove(){
            MinecraftForge.EVENT_BUS.unregister(this);
            resetEffect();
        }

        @Override
        public ItemStack getBuffIcon() {
            return icon;
        }
    }
}
