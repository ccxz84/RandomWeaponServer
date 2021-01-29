package RWAPI.Character.MasterYi.skills;

import RWAPI.Character.CooldownHandler;
import RWAPI.Character.EntityData;
import RWAPI.Character.MasterYi.MasterYi;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.Character.monster.entity.IMob;
import RWAPI.game.event.EntityDeathEventHandle;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import RWAPI.util.EntityStatus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class doublestrike implements Skill {

    private MasterYi _class;
    private MasterYiS[] skills = new MasterYiS[4];
    private eventhandle handler = null;

    protected final double[] skillAdcoe={
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.4,
            0.4,
            0.4,
            0.4,
            0.4,
            0.4,
            0.4,
            0.4,
            0.4
    };

    public doublestrike(MasterYi _class){
        this._class = _class;

    }

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {

    }

    @Override
    public void Skillset(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        for(int i =1;i<5;i++){
            skills[i-1] = (MasterYiS) _class.getSkill(i);
        }
        data.setCool(0,0);
        handler = new eventhandle(data);
        main.game.getEventHandler().register(handler);
    }

    @Override
    public void skillEnd(EntityPlayer player) {
        if(handler != null){
            main.game.getEventHandler().unregister(handler);
        }

    }

    @Override
    public void raiseevent(PlayerData data, double mana) {

    }

    class eventhandle extends PlayerAttackEventHandle{
        PlayerData data;
        private int attack;

        private eventhandle(PlayerData data){
            this.data = data;
        }

        @Override
        public void EventListener(AbstractBaseEvent event) {
            EntityData attacker = ((PlayerAttackEventHandle.PlayerAttackEvent)event).getSource().getAttacker();
            EntityData target = ((PlayerAttackEventHandle.PlayerAttackEvent)event).getSource().getTarget();
            DamageSource dsource = ((PlayerAttackEventHandle.PlayerAttackEvent)event).getSource();

            if(attacker.equals(this.data) && dsource.getAttackType() == DamageSource.AttackType.ATTACK){
                int lv = data.getLevel();
                attack = (attack+1) % 4;
                ((alphastrike)skills[0]).doublereducecool();
                if((attack == 3 && target.getCurrentHealth() > 0 && target.getStatus() == EntityStatus.ALIVE)){
                    DamageSource source = DamageSource.causeAttackMeleePhysics(attacker,target,attacker.getAd() * skillAdcoe[lv-1]);
                    attack = (attack+1) % 4;
                    ((alphastrike)skills[0]).doublereducecool();

                    DamageSource.attackDamage(source,true);
                    DamageSource.EnemyStatHandler.EnemyStatSetter(source);
                }
                this.data.setCool(0,attack);
                if(target.getCurrentHealth() <= 0 && target instanceof PlayerData) {
                    for(MasterYiS skill : skills) {
                        skill.reduceCool();
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
            return data;
        }

        @Override
        public EntityData getTarget() {
            return null;
        }
    }

    @Override
    public double[] getskilldamage() {
        return null;
    }

    @Override
    public double[] getskillAdcoe() {
        return this.skillAdcoe;
    }

    @Override
    public double[] getskillApcoe() {
        return null;
    }

    @Override
    public double[] getskillcost() {
        return null;
    }

    @Override
    public double[] getcooldown() {
        return null;
    }
}
