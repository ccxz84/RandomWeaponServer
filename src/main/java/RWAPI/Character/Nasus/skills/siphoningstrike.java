package RWAPI.Character.Nasus.skills;

import RWAPI.Character.CooldownHandler;
import RWAPI.Character.EntityData;
import RWAPI.Character.Nasus.Nasus;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.Character.monster.entity.AbstractObject;
import RWAPI.Character.monster.entity.IMob;
import RWAPI.game.event.BaseEvent;
import RWAPI.game.event.EntityDeathEventHandle;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.game.event.UseSkillEventHandle;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketCooldown;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class siphoningstrike implements Skill {

    private cool handler;
    EventClass EventClass;
    Nasus _class;

    protected final double[] skilldamage={
            30,
            31,
            32,
            33,
            34,
            35,
            36,
            37,
            38,
            39,
            40,
            44,
            48,
            52,
            56,
            60,
            64,
            68
    };
    protected final double[] skillAdcoe={
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0
    };
    protected final double[] skillApcoe={
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0

    };
    protected final double[] skillcost={
            20,
            20,
            20,
            20,
            20,
            20,
            20,
            20,
            20,
            20,
            20,
            20,
            20,
            20,
            20,
            20,
            20,
            20
    };

    protected final double[] cooldown = {
            7.5,
            7.5,
            7.5,
            7.5,
            7.5,
            6.5,
            6.5,
            6.5,
            5.5,
            5.5,
            5.5,
            4.5,
            4.5,
            4.5,
            4.5,
            3.5,
            3.5,
            3.5
    };

    public siphoningstrike(Nasus _class){
        this._class = _class;
    }

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
        if(data.getCool(1) <= 0&& data.getCurrentMana() > skillcost[lv-1]) {
            data.nonWorking = true;
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv-1]));
            data.nonWorking = false;
            this.handler = new cool(3, 1, (EntityPlayerMP) player,(float) (skilldamage[lv-1] + _class.getStack()),cooldown[lv-1],data.getSkillacc());
            registerevent(data);
            this.raiseevent(data,skillcost[lv-1]);
        }
    }

    @Override
    public void Skillset(EntityPlayer player) {

    }

    @Override
    public void skillEnd(EntityPlayer player) {

    }

    @Override
    public void raiseevent(PlayerData data, double mana) {
        UseSkillEventHandle.UseSkillEvent event = new UseSkillEventHandle.UseSkillEvent(data,mana);
        for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
            main.game.getEventHandler().RunEvent(event,priority);
        }
    }

    @Override
    public double[] getskilldamage() {
        return skilldamage;
    }

    @Override
    public double[] getskillAdcoe() {
        return new double[0];
    }

    @Override
    public double[] getskillApcoe() {
        return new double[0];
    }

    @Override
    public double[] getskillcost() {
        return skillcost;
    }

    @Override
    public double[] getcooldown() {
        return cooldown;
    }

    private void registerevent(PlayerData data){
        EventClass = new EventClass(data);
        main.game.getEventHandler().register(EventClass);
    }

    private void unregisterevent(){
        if(EventClass != null){
            main.game.getEventHandler().unregister(EventClass);
            EventClass = null;
        }
    }

    private void handlerremover(){
        handler = null;
    }

    class cool extends CooldownHandler {
        float skilldamage1;
        double cool;
        EventClass aeventClass;

        public cool(double cool, int id, EntityPlayerMP player, float skilldamage1, double waiting_time, int skillacc) {
            super(cool, id, player, false, 0);
            this.skilldamage1 = skilldamage1;
            this.cool = waiting_time;
            this.skillacc = skillacc;
            registerevent();
        }

        private void registerevent(){
            PlayerData data = main.game.getPlayerData(player.getUniqueID());
            aeventClass = new EventClass(data,this);
            main.game.getEventHandler().register(aeventClass);
        }

        private void unregisteraevent(){
            if(aeventClass != null){
                main.game.getEventHandler().unregister(aeventClass);
                aeventClass = null;
            }
        }


        @Override
        public void skillTimer(TickEvent.ServerTickEvent event) throws Throwable {
            if (skillTimer > cooldown) {
                double cooltime = _class.getfury() ? cool/2 : cool;
                new CooldownHandler(cooltime, 1, (EntityPlayerMP) player, true, skillacc);
                MinecraftForge.EVENT_BUS.unregister(this);
                unregisterevent();
            }
            data.setCool(this.id, ((float) (cooldown - skillTimer) / 40));
            skillTimer++;
        }

        private class EventClass extends PlayerAttackEventHandle {

            PlayerData data;
            cool handler;

            EventClass(PlayerData data, cool cool){
                super();
                this.data = data;
                this.handler = cool;
            }

            @Override
            public void EventListener(AbstractBaseEvent event) {
                PlayerAttackEvent eevent = (PlayerAttackEvent) event;
                EntityData attacker = eevent.getSource().getAttacker();
                EntityData target = eevent.getSource().getTarget();

                if(this.data.equals(attacker) && eevent.getSource().getAttackType() == DamageSource.AttackType.ATTACK){
                    DamageSource sourcee = DamageSource.causeSkillMeleePhysics(attacker, target, skilldamage1);
                    DamageSource.attackDamage(sourcee,false);
                    DamageSource.EnemyStatHandler.EnemyStatSetter(sourcee);
                    double cooltime = _class.getfury() ? cool/2 : cool;
                    new CooldownHandler(cooltime, 1, (EntityPlayerMP) player,true,skillacc);
                    MinecraftForge.EVENT_BUS.unregister(handler);
                    handlerremover();
                    unregisterevent();
                    unregisteraevent();
                }
            }

            @Override
            public EventPriority getPriority() {
                return EventPriority.LOWEST;
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
    }

    private class EventClass extends EntityDeathEventHandle {

        PlayerData data;

        public EventClass(PlayerData data) {
            super();
            this.data = data;
        }

        @Override
        public void EventListener(AbstractBaseEvent event) {
            EntityDeathEvent eevent = (EntityDeathEvent) event;
            DamageSource source = eevent.getSource();
            EntityData attacker = source.getAttacker();

            if(this.data.equals(attacker)){
                if(source.getTarget() instanceof PlayerData || source.getTarget().getEntity() instanceof AbstractObject){
                    _class.addStack(12);
                }
                else{
                    _class.addStack(6);
                }
                unregisterevent();
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
}
