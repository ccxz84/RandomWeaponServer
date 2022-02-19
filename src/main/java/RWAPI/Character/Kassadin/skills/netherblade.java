package RWAPI.Character.Kassadin.skills;

import RWAPI.Character.CooldownHandler;
import RWAPI.Character.EntityData;
import RWAPI.Character.Nasus.skills.siphoningstrike;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.game.event.BaseEvent;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.game.event.UseSkillEventHandle;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class netherblade implements Skill {

    private cool handler;

    protected final double[][] skilldamage={
            {
                    70,
                    71.5,
                    73,
                    74.5,
                    76,
                    77.5,
                    79,
                    80.5,
                    83,
                    85.5,
                    88,
                    91,
                    94,
                    97,
                    100,
                    103,
                    106,
                    109
            },
            {
                    4,
                    4.1,
                    4.2,
                    4.3,
                    4.4,
                    4.5,
                    4.6,
                    4.7,
                    4.8,
                    4.9,
                    5,
                    5.15,
                    5.3,
                    5.45,
                    5.6,
                    5.75,
                    5.9,
                    6

            }
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
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.8
    };
    protected final double[] skillcost={
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1
    };

    protected final double[] cooldown = {
            7,
            7,
            7,
            7,
            7,
            7,
            7,
            7,
            7,
            7,
            7,
            7,
            7,
            7,
            7,
            7,
            7,
            7
    };

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
        if(data.getCool(2) <= 0&& data.getCurrentMana() > skillcost[lv-1]&& this.handler == null) {
            data.nonWorking = true;
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv-1]));
            this.handler = new cool(3, 2, (EntityPlayerMP) player,(float) (skilldamage[0][lv-1] + skillApcoe[lv-1] * data.getAp()),
                    skilldamage[1][lv-1],cooldown[lv-1],data.getSkillacc());
            this.raiseevent(data,skillcost[lv-1]);
            data.nonWorking = false;
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
        return skilldamage[0];
    }

    @Override
    public double[] getskillAdcoe() {
        return new double[0];
    }

    @Override
    public double[] getskillApcoe() {
        return skillApcoe;
    }

    @Override
    public double[] getskillcost() {
        return skillcost;
    }

    @Override
    public double[] getcooldown() {
        return cooldown;
    }

    public double[] getskill1damage(){
        return this.skilldamage[1];
    }

    private void handlerremover(){
        handler = null;
    }

    class cool extends CooldownHandler {
        float skilldamage1;
        double skilldamage2;
        double cool;
        EventClass aeventClass;

        public cool(double cool, int id, EntityPlayerMP player, float skilldamage1,double skilldamage2, double waiting_time, int skillacc) {
            super(cool, id, player, false, 0);
            this.skilldamage1 = skilldamage1;
            this.cool = waiting_time;
            this.skillacc = skillacc;
            this.skilldamage2 = skilldamage2;
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
            handlerremover();
        }


        @Override
        public void skillTimer(TickEvent.ServerTickEvent event) throws Throwable {
            if (skillTimer > cooldown) {
                new CooldownHandler(cool, 2, (EntityPlayerMP) player, true, skillacc);
                MinecraftForge.EVENT_BUS.unregister(this);
                unregisteraevent();
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
                    new CooldownHandler(cool, 2, (EntityPlayerMP) player,true,skillacc);
                    double mana = attacker.getMaxMana() - attacker.getCurrentMana();
                    mana = (mana / 100) * skilldamage2;
                    if(target instanceof PlayerData){
                        mana *= 5;
                    }
                    attacker.setCurrentMana(attacker.getCurrentMana() + mana);
                    MinecraftForge.EVENT_BUS.unregister(handler);
                    unregisteraevent();
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
}
