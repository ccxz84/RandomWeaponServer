package RWAPI.items.gameItem.inherence;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.buff.Buff;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.game.event.UseSkillEventHandle;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import RWAPI.util.NetworkUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.Sys;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Spellblade_passive extends ItemBase.inherence_handler{

    PlayerData data;
    ItemStack stack;
    UseEventClass event1;
    AttackEventClass event2;
    Method calcul;
    cool cool;
    buff buff;
    private int idx;
    private final int duration = 10;
    private final double cooltime = 1.5;

    public Spellblade_passive(PlayerData data, ItemStack stack,int idx, Method method) {
        super(data, stack);
        this.data = data;
        this.idx = idx;
        this.stack = stack;
        this.calcul = method;
        boolean flag = NetworkUtil.getStackData(stack,"buff") != null ? (boolean)NetworkUtil.getStackData(stack,"buff"):false;
        if(!this.stack.equals(this.data.getPlayer().inventory.getStackInSlot(this.idx))){
            this.stack = this.data.getPlayer().inventory.getStackInSlot(this.idx);
        }
        if(flag){
            cool = new cool(cooltime,stack);
            NetworkUtil.setStackData(stack,false,"buff");
        }
        else if(NetworkUtil.getCool(stack) != 0){
            cool = new cool(NetworkUtil.getCool(stack),stack);
            NetworkUtil.setStackData(stack,false,"buff");
        }
        registerSkilluse();
    }

    private void registerSkilluse() {
        event1 = new UseEventClass(data);
        main.game.getEventHandler().register(event1);
    }

    @Override
    public void removeHandler() {
        super.removeHandler();
        if(cool != null){
            cool.reset();
            cool = null;
        }
        if(buff != null){
            buff.Timerend();
            buff = null;
        }
        if(event1 != null){
            main.game.getEventHandler().unregister(event1);
        }
        if(event2 != null){
            main.game.getEventHandler().unregister(event2);
        }
    }

    private class UseEventClass extends UseSkillEventHandle {
        PlayerData data;

        private UseEventClass(PlayerData data){
            this.data = data;
        }

        @Override
        public void EventListener(AbstractBaseEvent event) {
            PlayerData player = ((UseSkillEvent)event).getData();
            if(data.equals(player) && cool == null && buff == null){
                System.out.println("skill use");
                buff = new buff(duration,data,false,false);
                event2 = new AttackEventClass(data);
                main.game.getEventHandler().register(event2);
            }
        }

        @Override
        public EventPriority getPriority() {
            return EventPriority.NORMAL;
        }

        @Override
        public PlayerData getPlayer() {
            return data;
        }
    }

    private class AttackEventClass extends PlayerAttackEventHandle {
        PlayerData data;

        private AttackEventClass(PlayerData data){
            this.data = data;
        }

        @Override
        public void EventListener(AbstractBaseEvent event) {
            EntityData attacker = ((PlayerAttackEvent)event).getSource().getAttacker();
            EntityData target = ((PlayerAttackEvent)event).getSource().getTarget();
            DamageSource source = ((PlayerAttackEvent)event).getSource();
            if(attacker.equals(data) && source instanceof DamageSource.AttackDamage){
                try{
                    System.out.println(source.getAttacker().getName());
                    DamageSource bonusdamage = DamageSource.class.cast(calcul.invoke(null,attacker,target));
                    System.out.println("spell blade");
                    DamageSource.attackDamage(bonusdamage,false);
                    buff.Timerend();
                }
                catch (InvocationTargetException | IllegalAccessException | ClassCastException e){
                    e.printStackTrace();
                }
            }
            //때렸을때 데미지
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
            return this.data;
        }

        @Override
        public EntityData getTarget() {
            return null;
        }
    }

    private class cool extends ItemBase.coolHandler{

        public cool(double timer, ItemStack stack) {
            super(timer, stack);
        }

        public void reset(){
            MinecraftForge.EVENT_BUS.unregister(this);
        }

        @Override
        public void TimerEnd() {
            resetcool();
        }
    }

    private void resetcool() {
        cool = null;
    }

    private void resetbuff(){
        System.out.println(event2);
        buff = null;
        main.game.getEventHandler().unregister(event2);
        event2 = null;
        NetworkUtil.setStackData(stack,false,"buff");
        if(!this.stack.equals(this.data.getPlayer().inventory.getStackInSlot(this.idx))){
            this.stack = this.data.getPlayer().inventory.getStackInSlot(this.idx);
        }
        cool = new cool(cooltime,stack);
    }

    private class buff extends Buff {

        ItemStack icon;
        public buff(double duration, PlayerData player, boolean debuff, boolean clean, double... data) {
            super(duration, player, debuff, clean, data);
            this.icon = stack.copy();
        }

        @Override
        public void setEffect() {
            if(!stack.equals(this.player.getPlayer().inventory.getStackInSlot(idx))){
                stack = this.player.getPlayer().inventory.getStackInSlot(idx);
            }
            NetworkUtil.setStackData(stack,true,"buff");
            player.addBuff(this);
        }

        @Override
        public void resetEffect() {
            resetbuff();
            player.removeBuff(this);
        }

        public void Timerend(){
            resetEffect();
            MinecraftForge.EVENT_BUS.unregister(this);
        }

        @Override
        public ItemStack getBuffIcon() {
            return icon;
        }
    }

}
