package RWAPI.items.gameItem.inherence;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.game.event.UseSkillEventHandle;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.item.ItemStack;

public class Eternity_passive extends ItemBase.inherence_handler {

    PlayerData data;
    ItemStack stack;
    final int hphealper;
    final int manahealper;
    AttackEventClass attackEventClass;
    UseEventClass useEventClass;

    public Eternity_passive(PlayerData data, ItemStack stack, int hphealper, int mannahealper) {
        super(data, stack);
        this.data = data;
        this.stack = stack;
        this.hphealper = hphealper;
        this.manahealper = mannahealper;
        registerEvent();
    }

    private void registerEvent(){
        attackEventClass = new AttackEventClass(data);
        useEventClass = new UseEventClass(data);
        main.game.getEventHandler().register(attackEventClass);
        main.game.getEventHandler().register(useEventClass);
    }

    @Override
    public void removeHandler() {
        super.removeHandler();
        if(attackEventClass != null){
            main.game.getEventHandler().unregister(attackEventClass);
        }
        if(useEventClass != null){
            main.game.getEventHandler().unregister(useEventClass);
        }
    }

    private class AttackEventClass extends PlayerAttackEventHandle {

        PlayerData data;

        private AttackEventClass(PlayerData data){
            this.data = data;
        }

        @Override
        public void EventListener(AbstractBaseEvent event) {
            PlayerAttackEvent pevent = (PlayerAttackEvent) event;
            DamageSource source = pevent.getSource();
            EntityData target = source.getTarget();
            EntityData attacker = source.getAttacker();
            if(target.equals(data) && attacker instanceof PlayerData){
                double mana = (data.getCurrentMana() + ((source.getDamage()/100) * manahealper)) >= data.getMaxMana()?data.getMaxMana(): (data.getCurrentMana() + ((source.getDamage()/100) * manahealper));
                data.setCurrentMana(mana);
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

    private class UseEventClass extends UseSkillEventHandle{

        PlayerData data;

        private UseEventClass(PlayerData data){
            this.data = data;
        }

        @Override
        public void EventListener(AbstractBaseEvent event) {
            UseSkillEvent sevent = (UseSkillEvent) event;
            PlayerData target = sevent.getData();

            if(target.equals(data)){
                double healhp = ((sevent.getMana()/100)*hphealper >= 15 ? 15 : (sevent.getMana()/100)*hphealper);
                double hp = target.getCurrentHealth() + healhp >= target.getMaxHealth() ? target.getMaxHealth() : target.getCurrentHealth() + healhp;
                target.setCurrentHealth(hp);
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
}
