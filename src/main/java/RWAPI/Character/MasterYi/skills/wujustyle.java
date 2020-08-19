package RWAPI.Character.MasterYi.skills;

import RWAPI.Character.*;
import RWAPI.Character.buff.Buff;
import RWAPI.Character.monster.entity.AbstractMob;
import RWAPI.main;
import RWAPI.util.DamageSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class wujustyle extends MasterYiS {

    private PlayerClass _class;
    private bufftimer buft;
    private cool cool;

    public wujustyle(PlayerClass _class){
        this._class = _class;
    }

    protected final double[] skilldamage={
            18,20,22,24,26,30,32,34,36,38,41,45
    };
    protected final double[] skillAdcoe={
            0.3,0.3,0.3,0.3,0.3,0.4,0.4,0.4,0.4,0.4,0.4,0.6
    };
    protected final double[] skillApcoe={
            0,0,0,0,0,0,0,0,0,0,0,0
    };
    protected final double[] skillcost={
            0,0,0,0,0,0,0,0,0,0,0,0
    };

    protected final double[] cooldown = {
            18,17.5,17,16.5,16,15,14.5,14,13.5,13,12,11
    };

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
        if(data.getCool(3) <= 0 && data.getCurrentMana() > skillcost[lv-1]&& data.nonWorking == false&& cool == null) {
            cool = new cool(cooldown[lv-1],3, (EntityPlayerMP) player);
            buft = new bufftimer(5, (EntityPlayerMP) player,skilldamage[lv-1] + skillAdcoe[lv-1] * data.getAd());
        }
    }

    @Override
    public void Skillset(EntityPlayer player) {
        buft = null;
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
                        DamageSource source = new DamageSource(attacker,target, data[0]);
                        DamageSource.attackDamage(source);
                        DamageSource.EnemyStatHandler.EnemyStatSetter(source);
                    }
                }
            }
        }
    }
}
