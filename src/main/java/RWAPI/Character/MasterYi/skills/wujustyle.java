package RWAPI.Character.MasterYi.skills;

import RWAPI.Character.*;
import RWAPI.Character.buff.Buff;
import RWAPI.Character.monster.entity.AbstractMob;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
            cool = new cool(cooldown[lv-1],3, (EntityPlayerMP) player);
            buft = new bufftimer(5, (EntityPlayerMP) player,skilldamage[lv-1] + skillAdcoe[lv-1] * data.getAd());
            data.addBuff(buft);
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

        public cool(double cool, int id, EntityPlayerMP player) {
            super(cool, id, player);
        }

        public void reduceCool(){
            this.skillTimer += cooldown * 0.7;
        }
    }

    class bufftimer extends Buff {

        public bufftimer(double duration, EntityPlayerMP player, double... data) {
            super(duration, player, data);
        }

        @Override
        public void setEffect() {
            // TODO Auto-generated method stub

        }

        @Override
        public void resetEffect() {
            // TODO Auto-generated method stub
            PlayerData pdata = main.game.getPlayerData(player.getUniqueID());
            pdata.removeBuff(buft);
            Skillset(player);
        }

        @SubscribeEvent(priority = EventPriority.NORMAL)
        public void attack(LivingAttackEvent event) {
            if(event.getSource().getTrueSource() != null) {
                if(event.getSource().getTrueSource().equals(player) && (event.getEntityLiving() instanceof AbstractMob || event.getEntityLiving() instanceof EntityPlayer)) {
                    EntityData target = null;
                    if(event.getEntityLiving() instanceof AbstractMob) {
                        target = ((AbstractMob)event.getEntityLiving()).getData();
                    }
                    if(event.getEntityLiving() instanceof EntityPlayer){
                        target = main.game.getPlayerData(event.getEntityLiving().getUniqueID());
                    }
                    if(target != null) {
                        PlayerData attacker = main.game.getPlayerData(event.getSource().getTrueSource().getUniqueID());
                        DamageSource source = DamageSource.causeAttackFixed(attacker,target,data[0]);
                        DamageSource.attackDamage(source,false);
                        DamageSource.EnemyStatHandler.EnemyStatSetter(source);
                    }
                }
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
