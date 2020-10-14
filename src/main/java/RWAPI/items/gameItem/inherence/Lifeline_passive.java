package RWAPI.items.gameItem.inherence;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.buff.Buff;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.items.gameItem.Youmuusghostblade;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import RWAPI.util.DamageSource.SkillFixedDamageSource;
import RWAPI.util.DamageSource.SkillMagicDamageSource;
import RWAPI.util.DamageSource.SkillPhysicsDamageSource;
import RWAPI.util.NetworkUtil;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Lifeline_passive extends ItemBase.inherence_handler{

    EventClass eventClass;
    PlayerData data;
    Lifeline_passive.cool cool;
    ItemStack stack;
    buff buff;
    private final int cooltime;

    private final int time;
    private final double vamPer, plusad;

    public Lifeline_passive(PlayerData data, ItemStack stack, int cooltime, int time, double vamPer, double plusad) {
        super(data, stack);
        this.data = data;
        this.cooltime = cooltime;
        boolean flag = NetworkUtil.getStackData(stack,"buff") != null ? (boolean)NetworkUtil.getStackData(stack,"buff"):false;
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
        registerAttackEvent();
    }

    private void registerAttackEvent() {
        this.eventClass = new EventClass(data);
        main.game.getEventHandler().register(this.eventClass);
    }

    private void coolreset() {
        cool = null;
    }


    @Override
    public void removeHandler() {
        main.game.getEventHandler().unregister(this.eventClass);
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

        public EventClass(PlayerData data){
            super();
            this.data = data;
        }

        @Override
        public void EventListener(AbstractBaseEvent event) {
            DamageSource source = ((PlayerAttackEvent)event).getSource();
            EntityData target = source.getTarget();
            EntityData attacker = source.getAttacker();
            double damage = source.getDamage();

            if(target.equals(this.data)){
                if(target.getCurrentHealth() <= target.getMaxHealth() * 0.3 && cool == null){
                    if(cool != null){
                        return;
                    }
                    buff = new buff(time,((PlayerData)target).getPlayer());
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
    }

    private void bufreset(){
        buff = null;
        NetworkUtil.setStackData(stack,false,"buff");
        cool = new cool(cooltime,stack);
    }

    private class buff extends Buff {

        PlayerData pdata;

        public buff(double duration, EntityPlayerMP player, double... data) {
            super(duration, player, data);
            NetworkUtil.setStackData(stack, true, "buff");
        }

        @Override
        public void BuffTimer(TickEvent.ServerTickEvent event) throws Throwable {
            super.BuffTimer(event);
            NetworkUtil.setCool(stack, (this.duration - (double) this.timer) / 40);
        }

        @Override
        public void setEffect() {
            this.pdata = main.game.getPlayerData(player.getUniqueID());
            this.pdata.setAd(this.pdata.getAd() + plusad);
        }

        @Override
        public void resetEffect() {
            this.pdata.setAd(this.pdata.getAd() - plusad);
            NetworkUtil.setCool(stack, 0);
            bufreset();
        }
    }
}
