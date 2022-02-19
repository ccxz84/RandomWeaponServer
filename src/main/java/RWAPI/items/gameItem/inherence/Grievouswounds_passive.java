package RWAPI.items.gameItem.inherence;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.buff.Buff;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;


public class Grievouswounds_passive extends ItemBase.inherence_handler{

    HashMap<PlayerData, buff> map = new HashMap<>();
    buff temp = null;
    final int duration;
    ItemStack stack;
    PlayerData data;
    EventClass EventClass = null;
    Class<DamageSource> _class;

    public Grievouswounds_passive(PlayerData data, ItemStack stack, int duration, Class _class) {
        super(data, stack);
        this.data = data;
        this.duration = duration;
        this.stack = stack;
        this._class = _class;
        registerattack();
    }

    private void removebuff(PlayerData data){
        map.remove(data);
    }

    private void registerattack(){
        EventClass = new EventClass(data);
        main.game.getEventHandler().register(EventClass);
    }

    @Override
    public void removeHandler() {
        if(EventClass != null){
            main.game.getEventHandler().unregister(EventClass);
        }
    }

    private class EventClass extends PlayerAttackEventHandle {

        PlayerData data;

        private EventClass(PlayerData data){
            this.data = data;
        }

        @Override
        public void EventListener(AbstractBaseEvent event) {
            PlayerAttackEvent pevent = (PlayerAttackEvent) event;
            DamageSource source = pevent.getSource();
            EntityData attacker = source.getAttacker();
            EntityData target = source.getTarget();

            if(attacker.equals(data) && target instanceof PlayerData){
                try{
                    _class.cast(source);
                    temp = map.get(target);
                    if(temp == null){
                        temp = new buff(duration, (PlayerData) target,true,true);
                        map.put((PlayerData) target,temp);
                        temp = null;
                    }
                    else{
                        temp.resettimer();
                    }
                }
                catch (ClassCastException e){

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

    private class buff extends Buff {

        ItemStack icon;

        public buff(double duration, PlayerData player, boolean debuff, boolean clean, double... data) {
            super(duration, player, debuff, clean, data);
            this.icon = stack.copy();
        }

        @Override
        public void BuffTimer(TickEvent.ServerTickEvent event) throws Throwable {
            if(this.player.getGrievousWounds() == false){
                this.player.setGrievousWounds(true);
            }
            super.BuffTimer(event);
        }

        @Override
        public void setEffect() {
            this.player.setGrievousWounds(true);
            this.player.addBuff(this);
        }

        @Override
        public void resetEffect() {
            this.player.setGrievousWounds(false);
            this.player.removeBuff(this);
            removebuff(player);
        }

        private void resettimer(){
            this.timer = 0;
        }

        @Override
        public ItemStack getBuffIcon() {
            return icon;
        }
    }
}
