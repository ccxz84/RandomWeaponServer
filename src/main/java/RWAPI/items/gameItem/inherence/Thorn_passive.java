package RWAPI.items.gameItem.inherence;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.buff.Buff;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.main;
import RWAPI.util.DamageSource.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashMap;

public class Thorn_passive extends ItemBase.inherence_handler{

    private final double armorPer;
    private final double damage;
    PlayerData data;
    EventClass eventClass;
    ItemStack stack;
    HashMap<PlayerData, buff> map = new HashMap<>();
    buff temp = null;
    final int duration;

    public Thorn_passive(PlayerData data, ItemStack stack, double damage, double armorPer, int duration) {
        super(data, stack);
        this.data = data;
        this.armorPer = armorPer;
        this.damage = damage;
        this.stack = stack;
        this.duration = duration;
        registerAttackEvent();
    }

    private void registerAttackEvent() {
        this.eventClass = new EventClass(data);
        main.game.getEventHandler().register(this.eventClass);
    }

    private void removebuff(PlayerData data){
        map.remove(data);
    }

    @Override
    public void removeHandler() {
        main.game.getEventHandler().unregister(this.eventClass);
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

            EntityData data = source.getTarget();
            PlayerData attacker = source.getAttacker() instanceof PlayerData ? (PlayerData) source.getAttacker() : null ;

            if(data.equals(this.data) && source.getAttackType() == DamageSource.AttackType.ATTACK
                    && source.getDamageType() == DamageSource.DamageType.PHYSICS){

                double aDamage = damage + (data.getArmor()/100)*armorPer;
                DamageSource sourcee;
                if(source.isRanged()){
                    sourcee = DamageSource.causeUnknownRangedMagic(source.getTarget(),source.getAttacker(),aDamage);
                }
                else{
                    sourcee = DamageSource.causeUnknownMeleeMagic(source.getTarget(),source.getAttacker(),aDamage);
                }
                DamageSource.attackDamage(sourcee,false);

                if(attacker != null){
                    temp = map.get(attacker);
                    if(temp == null){
                        temp = new buff(duration, attacker,true,true);
                        map.put(attacker,temp);
                        temp = null;
                    }
                    else{
                        temp.resettimer();
                    }
                }
                //System.out.println("추가 데미지 : " + aDamage);

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
