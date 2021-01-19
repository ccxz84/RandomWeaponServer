package RWAPI.items.gameItem.inherence;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.buff.Buff;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.main;
import RWAPI.util.DamageSource.*;
import RWAPI.util.NetworkUtil;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class Coldsteel_passive extends ItemBase.inherence_handler{

    EventClass eventClass;
    PlayerData data;
    ItemStack stack;
    private final int time;
    private final double  minusas;
    private List<buff> bufflist;

    public Coldsteel_passive(PlayerData data, ItemStack stack,int time, double minusas) {
        super(data, stack);
        this.stack = stack;
        this.time = time;
        this.minusas = minusas;
        this.data = data;
        bufflist = new ArrayList<buff>();
        registerAttackEvent();
    }

    private void registerAttackEvent() {
        this.eventClass = new EventClass(data);
        main.game.getEventHandler().register(this.eventClass);
    }

    private void bufreset(buff instance){
        bufflist.remove(instance);
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
            double damage = source.getDamage();
            buff inst = null;

            PlayerData target = source.getTarget() instanceof PlayerData ? (PlayerData) source.getTarget() : null;
            PlayerData attacker = source.getAttacker() instanceof PlayerData ? (PlayerData) source.getAttacker() : null;

            if(this.data.equals(target) && source instanceof DamageSource.AttackDamage && attacker != null){
                for(buff instance : bufflist){
                    if(instance.getPlayerdata().equals(attacker)){
                        inst = instance;
                    }
                }
                if(inst == null){
                    bufflist.add(new buff(time, attacker));
                }
                else{
                    inst.resettime();
                }
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

    private class buff extends Buff {


        public buff(double duration, PlayerData player, double... data) {
            super(duration, player,true,true, data);
        }

        @Override
        public void setEffect() {
            this.player.setPlusAttackspeed(this.player.getPlusAttackspeed() - minusas);
            player.addBuff(this);
        }

        @Override
        public void resetEffect() {
            this.player.setPlusAttackspeed(this.player.getPlusAttackspeed() + minusas);
            bufreset(this);
            player.removeBuff(this);
        }

        @Override
        public ItemStack getBuffIcon() {
            return new ItemStack(stack.getItem());
        }

        public PlayerData getPlayerdata() {
            return player;
        }

        public void resettime(){
            timer = 0;
        }
    }
}
