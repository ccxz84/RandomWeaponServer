package RWAPI.items.gameItem.inherence;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.buff.Buff;
import RWAPI.game.event.BaseEvent;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.game.event.UseSkillEventHandle;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.Archangelsstaff;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.items.gameItem.Manamune;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import RWAPI.util.NetworkUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class Awe_passive extends ItemBase.inherence_handler {

    private int stack;
    private final int plusmana;
    private final int cool;
    private final int maxstack;
    private final int maxsttack = 100;
    private final int refundper;
    private EventClass eventClass;
    private attackEventClass attackEventClass;
    private PlayerData data;
    private buffClass buffClass;
    private ItemStack itemStack;
    private int mana;
    private int idx;
    private cool handle = null;

    public Awe_passive(PlayerData data, ItemStack stack, int plusmana, int cool, int maxstack, int refundper, int idx) {
        super(data, stack);
        this.data = data;
        this.plusmana = plusmana;
        this.cool = cool;
        this.maxstack = maxstack;
        this.stack = maxstack;
        this.itemStack = stack;
        this.mana = 0;
        this.idx = idx;
        checkstack();
        if(mana >= maxsttack){
            checkmaxstack();
        }
        this.refundper = refundper;
        buffClass = new buffClass(40,data,false,false,mana);
        registerskilluse();
        if(stack.getItem() instanceof Manamune){
            registerattack();
        }
    }

    private void checkmaxstack(){
        if(itemStack.getItem() instanceof Manamune){
            data.getPlayer().inventory.setInventorySlotContents(idx, new ItemStack(ModItems.Muramana));
        }
        else if(itemStack.getItem() instanceof Archangelsstaff){
            data.getPlayer().inventory.setInventorySlotContents(idx, new ItemStack(ModItems.Seraphsembrace));
        }
    }

    private void registerattack() {
        this.attackEventClass = new attackEventClass(this.data);
        main.game.getEventHandler().register(this.attackEventClass);
    }

    private void checkstack() {
        Integer mana = NetworkUtil.<Integer>restoreBaseData(this.data,"awe");
        if(mana != null){
            data.setCurrentMana(data.getCurrentMana() + mana);
            data.setMaxMana(data.getMaxMana() + mana);
            this.mana = mana;
        }

    }

    private void registerskilluse() {
        this.eventClass = new EventClass(this.data);
        main.game.getEventHandler().register(this.eventClass);
    }

    @Override
    public void removeHandler() {
        data.setCurrentMana(data.getCurrentMana() - mana);
        data.setMaxMana(data.getMaxMana() - mana);
        NetworkUtil.saveBaseData(data,new Integer(mana),"awe");
        main.game.getEventHandler().unregister(this.eventClass);
        this.data.removeBuff(buffClass);
        if(attackEventClass != null){
            main.game.getEventHandler().unregister(attackEventClass);
        }
    }

    private class buffClass extends Buff {

        ItemStack stack;

        public buffClass(double duration, PlayerData player, boolean debuff, boolean clean, double... data) {
            super(duration, player, debuff, clean, data);
            MinecraftForge.EVENT_BUS.unregister(this);
            this.stack = itemStack;
            this.duration = (int) (data[0] * 40);
            this.timer = 0;
        }

        @Override
        public void setEffect() {
            this.player.addBuff(this);
        }

        @Override
        public void resetEffect() {
            this.player.removeBuff(this);
        }

        @Override
        public ItemStack getBuffIcon() {
            return this.stack;
        }

        public void addstack(int stack){
            this.duration += stack * 40;
        }
    }

    private class attackEventClass extends PlayerAttackEventHandle {
        PlayerData ddata;

        public attackEventClass(PlayerData data) {
            super();
            this.ddata = data;
        }


        @Override
        public void EventListener(AbstractBaseEvent event) {
            PlayerData target = null;
            if(((PlayerAttackEvent)event).getSource().getAttacker() instanceof PlayerData){
                target = (PlayerData) ((PlayerAttackEvent)event).getSource().getAttacker();
            }
            if(target != null && target.equals(this.ddata) && ((PlayerAttackEvent) event).getSource() instanceof DamageSource.AttackDamage){
                if(stack > 0 && mana < maxsttack){
                    --stack;
                    if(handle == null){
                        if(!itemStack.equals(data.getPlayer().inventory.getStackInSlot(idx))){
                            itemStack = data.getPlayer().inventory.getStackInSlot(idx);
                        }
                        handle = new cool(cool,itemStack);
                    }
                    int changemana = mana;
                    mana = (mana + plusmana) >= maxsttack ? maxsttack : (mana + plusmana);
                    ddata.setMaxMana(ddata.getMaxMana() + (mana - changemana));
                    buffClass.addstack(plusmana);
                    if(mana >= maxsttack){
                        checkmaxstack();
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
            return code.attacker;
        }

        @Override
        public EntityData getAttacker() {
            return null;
        }

        @Override
        public EntityData getTarget() {
            return ddata;
        }


    }

    private class EventClass extends UseSkillEventHandle{
        PlayerData ddata;

        public EventClass(PlayerData data) {
            super();
            this.ddata = data;
        }


        @Override
        public void EventListener(AbstractBaseEvent event) {
            PlayerData target = ((UseSkillEvent)event).getData();
            if(target.equals(this.ddata) ){
                if(stack > 0 && mana < maxsttack){
                    --stack;
                    if(handle == null){
                        if(!itemStack.equals(data.getPlayer().inventory.getStackInSlot(idx))){
                            itemStack = data.getPlayer().inventory.getStackInSlot(idx);
                        }
                        handle = new cool(cool,itemStack);
                    }
                    int changemana = mana;
                    mana = (mana + plusmana) >= maxsttack ? maxsttack : (mana + plusmana);
                    ddata.setMaxMana(ddata.getMaxMana() + (mana - changemana));
                    buffClass.addstack(plusmana);
                    if(mana >= maxsttack){
                        checkmaxstack();
                    }
                }

                double cmana = (target.getCurrentMana() + ((((UseSkillEvent)event).getMana()/100) * refundper) >= target.getMaxMana() ? target.getMaxMana() :
                        target.getCurrentMana() + ((((UseSkillEvent)event).getMana()/100) * refundper));
                System.out.println(cmana);
                target.setCurrentMana(cmana);
            }
        }

        @Override
        public EventPriority getPriority() {
            return EventPriority.NORMAL;
        }

        @Override
        public PlayerData getPlayer() {
            return ddata;
        }


    }

    private class cool extends ItemBase.coolHandler{

        private ItemStack istack;

        public cool(double timer, ItemStack stack) {
            super(timer, stack);
            istack = stack;
        }

        @SubscribeEvent
        public void gameTimer(TickEvent.ServerTickEvent event) {
            currentTime++;
            NetworkUtil.setCool(this.istack,(MaxTime-(double)currentTime)/40);
            if(currentTime > MaxTime) {
                if(maxstack > (stack+1)){
                    ++stack;
                    currentTime = 0;
                }
                else{
                    MinecraftForge.EVENT_BUS.unregister(this);
                    TimerEnd();
                }
            }
        }

        @Override
        public void TimerEnd() {
            if(maxstack >= (stack+1)) {
                ++stack;
            }
            handle = null;
        }
    }

}
