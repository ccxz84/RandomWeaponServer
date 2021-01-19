package RWAPI.Character.MasterYi.skills;

import RWAPI.Character.*;
import RWAPI.Character.buff.Buff;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.init.ModSkills;
import RWAPI.main;
import RWAPI.util.DamageSource.AttackPhysicsMeleeDamageSource;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class wujustyle extends MasterYiS {

    private PlayerClass _class;
    private bufftimer buft;
    private cool cool;

    public wujustyle(PlayerClass _class){
        this._class = _class;
    }

    protected final double[] skilldamage={
            18,
            18.5,
            19,
            19.5,
            20,
            20.5,
            21,
            21.5,
            23.5,
            25.5,
            27.5,
            29.5,
            31.5,
            33.5,
            35.5,
            37.5,
            39.5,
            41.5
    };
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
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3
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

    protected final double[] cooldown = {
            18,
            18,
            18,
            18,
            18,
            15,
            15,
            15,
            12,
            12,
            12,
            9,
            9,
            9,
            9,
            9,
            9,
            9

    };

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
        if(data.getCool(3) <= 0 && data.getCurrentMana() > skillcost[lv-1]&& data.nonWorking == false&& cool == null) {
            data.nonWorking = true;
            this.raiseevent(data,skillcost[lv-1]);
            cool = new cool(cooldown[lv-1],3, (EntityPlayerMP) player,data.getSkillacc());
            buft = new bufftimer(5, data,skilldamage[lv-1] + skillAdcoe[lv-1] * data.getAd());
            data.nonWorking = false;
        }
    }

    @Override
    public void Skillset(EntityPlayer player) {
        buft = null;
        cool = null;
    }

    @Override
    public void skillEnd(EntityPlayer player) {

    }

    @Override
    void reduceCool() {
        if(cool != null)
            this.cool.reduceCool();
    }

    class cool extends CooldownHandler {

        public cool(double cool, int id, EntityPlayerMP player,int skillacc) {
            super(cool, id, player,true,skillacc);
        }

        public void reduceCool(){
            this.skillTimer += cooldown * 0.7;
        }
    }

    class bufftimer extends Buff {

        EventClass eventClass;

        public bufftimer(double duration, PlayerData player, double... data) {
            super(duration, player,false,false, data);

        }

        @Override
        public void setEffect() {
            // TODO Auto-generated method stub
            registerAttackEvent();
            this.player.addBuff(this);
        }

        private void registerAttackEvent() {
            this.eventClass = new EventClass(player, data[0]);
            main.game.getEventHandler().register(this.eventClass);
        }

        @Override
        public void resetEffect() {
            // TODO Auto-generated method stub
            player.removeBuff(buft);
            Skillset(player.getPlayer());
            if(eventClass != null){
                main.game.getEventHandler().unregister(this.eventClass);
            }
        }

        @Override
        public ItemStack getBuffIcon() {
            return new ItemStack(ModSkills.wujustyle);
        }

        private class EventClass extends PlayerAttackEventHandle {
            PlayerData data;
            double damage;

            public EventClass(PlayerData data, double damage) {
                super();
                this.data = data;
                this.damage = damage;
            }

            @Override
            public void EventListener(AbstractBaseEvent event) {
                DamageSource source = ((PlayerAttackEvent)event).getSource();
                double damage = source.getDamage();

                EntityData data = source.getAttacker();

                if(data.equals(this.data) && source instanceof AttackPhysicsMeleeDamageSource){
                    EntityData target = source.getTarget();
                    DamageSource dsource = DamageSource.causeAttackFixed(data,target,damage);
                    DamageSource.attackDamage(dsource,false);
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

    @Override
    public double[] getskilldamage() {
        return this.skilldamage;
    }

    @Override
    public double[] getskillAdcoe() {
        return this.skillAdcoe;
    }

    @Override
    public double[] getskillApcoe() {
        return this.skillApcoe;
    }

    @Override
    public double[] getskillcost() {
        return this.skillcost;
    }

    @Override
    public double[] getcooldown() {
        return this.cooldown;
    }
}
