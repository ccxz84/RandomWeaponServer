package RWAPI.items.gameItem.inherence;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.buff.Buff;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import RWAPI.util.NetworkUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Lifeline_passive extends ItemBase.inherence_handler{

    EventClass eventClass1,eventClass2;
    PlayerData data;
    Lifeline_passive.cool cool;
    ItemStack stack;
    buff buff;
    private int idx;
    private final int cooltime;

    private final int time;
    private final double vamPer, plusad, shield;

    public Lifeline_passive(PlayerData data, ItemStack stack, int cooltime, int time, double vamPer, double plusad, double shield, int idx) {
        super(data, stack);
        this.data = data;
        this.cooltime = cooltime;
        this.idx = idx;
        this.stack = stack;
        boolean flag = NetworkUtil.getStackData(stack,"buff") != null ? (boolean)NetworkUtil.getStackData(stack,"buff"):false;
        if(!this.stack.equals(this.data.getPlayer().inventory.getStackInSlot(this.idx))){
            this.stack = this.data.getPlayer().inventory.getStackInSlot(this.idx);
        }
        if(flag){
            cool = new Lifeline_passive.cool(cooltime,stack);
            NetworkUtil.setStackData(stack,false,"buff");
        }
        else if(NetworkUtil.getCool(stack) != 0){
            cool = new Lifeline_passive.cool(NetworkUtil.getCool(stack),stack);
            NetworkUtil.setStackData(stack,false,"buff");
        }
        this.stack = stack;
        this.time = time;
        this.vamPer = vamPer;
        this.plusad = plusad;
        this.shield = shield;
        registerAttackEvent();
    }

    private void registerAttackEvent() {
        this.eventClass1 = new EventClass(data, PlayerAttackEventHandle.code.attacker);
        this.eventClass2 = new EventClass(data, PlayerAttackEventHandle.code.target);
        main.game.getEventHandler().register(this.eventClass1);
        main.game.getEventHandler().register(this.eventClass2);
    }

    private void coolreset() {
        cool = null;
    }


    @Override
    public void removeHandler() {
        if(this.eventClass1 != null){
            main.game.getEventHandler().unregister(this.eventClass1);
        }
        if(this.eventClass2 != null){
            main.game.getEventHandler().unregister(this.eventClass2);
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

    private class EventClass extends PlayerAttackEventHandle {

        PlayerData data;
        code code;

        public EventClass(PlayerData data, code code){
            super();
            this.data = data;
            this.code = code;
        }

        @Override
        public void EventListener(AbstractBaseEvent event) {
            DamageSource source = ((PlayerAttackEvent)event).getSource();
            EntityData target = source.getTarget();
            EntityData attacker = source.getAttacker();
            double damage = source.getDamage();
            if(target.equals(this.data)){
                if(target.getCurrentHealth() <= target.getMaxHealth() * 0.3 && buff == null){
                    if(cool != null){
                        return;
                    }
                    buff = new buff(time,((PlayerData)target));
                }
            }
            if(attacker.equals(this.data)){
                if(buff != null){
                    if(source.getAttacker().getCurrentHealth() < source.getAttacker().getMaxHealth()){
                        double heal = source.getAttacker().getCurrentHealth() + (damage/100) * vamPer > source.getAttacker().getMaxHealth() ?
                                source.getAttacker().getMaxHealth() : source.getAttacker().getCurrentHealth() + (damage/100) * vamPer;
                        source.getAttacker().setCurrentHealth(heal);
                    }
                }
            }

        }

        @Override
        public EventPriority getPriority() {
            return EventPriority.NORMAL;
        }

        @Override
        public code getEventCode() {
            return code;
        }

        @Override
        public EntityData getAttacker() {
            return data;
        }

        @Override
        public EntityData getTarget() {
            return data;
        }
    }

    private void bufreset(){
        buff = null;
        NetworkUtil.setStackData(stack,false,"buff");
        if(!this.stack.equals(this.data.getPlayer().inventory.getStackInSlot(this.idx))){
            this.stack = this.data.getPlayer().inventory.getStackInSlot(this.idx);
        }
        cool = new cool(cooltime,stack);
    }

    private class buff extends Buff {

        EntityData.shield shield_instance;

        public buff(double duration, PlayerData player, double... data) {
            super(duration, player,false,false, data);
            NetworkUtil.setStackData(stack, true, "buff");
        }

        @Override
        public void BuffTimer(TickEvent.ServerTickEvent event) throws Throwable {
            super.BuffTimer(event);
            NetworkUtil.setCool(stack, (this.duration - (double) this.timer) / 40);
        }

        @Override
        public void setEffect() {
            this.player.setAd(this.player.getAd() + plusad);
            shield_instance = new EntityData.shield(shield);
            this.player.addShield(shield_instance);
            this.player.addBuff(this);
        }

        @Override
        public void resetEffect() {
            this.player.setAd(this.player.getAd() - plusad);
            this.player.removeShield(shield_instance);
            player.removeBuff(this);
            NetworkUtil.setCool(stack, 0);
            bufreset();
        }

        @Override
        public ItemStack getBuffIcon() {
            return new ItemStack(stack.getItem());
        }
    }
}
