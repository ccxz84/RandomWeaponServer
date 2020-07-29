package RWAPI.Character.MasterYi.skills;

import RWAPI.Character.*;
import RWAPI.Character.buff.Buff;
import RWAPI.Character.monster.entity.AbstractMob;
import RWAPI.main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class highlander extends MasterYiS {

    private PlayerClass _class;
    private bufftimer buft;
    private cool cool;

    public highlander(PlayerClass _class){
        this._class = _class;
    }

    protected final double[][] skilldamage={
            {30,30,40,40,40,55,55,55,65,65,65,80},
            {0.3,0.3,0.4,0.4,0.4,0.55,0.55,0.55,0.65,0.65,0.65,0.85}
    };
    protected final double[] skillAdcoe={
            0.3,0.3,0.3,0.3,0.3,0.4,0.4,0.4,0.4,0.4,0.4,0.6
    };
    protected final double[] skillApcoe={
            0,0,0,0,0,0,0,0,0,0,0,0
    };
    protected final double[] skillcost={
            100,100,150,150,150,200,200,200,250,250,250,250
    };

    protected final double[] cooldown = {
            85,85,80,80,80,75,75,75,70,70,70,60
    };

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();

        if(data.getCool(4) <= 0 && data.getCurrentMana() > skillcost[lv-1] && data.nonWorking == false && cool == null) {
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv-1]));
            cool = new cool(cooldown[lv-1],4, (EntityPlayerMP) player);
            buft = new bufftimer(7, (EntityPlayerMP) player,(float)skilldamage[0][lv-1],(float)skilldamage[1][lv-1]);
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

        public bufftimer(double duration, EntityPlayerMP player, float... data) {
            super(duration, player, data);
        }

        @Override
        public void resetEffect() {
            // TODO Auto-generated method stub
            PlayerData data = main.game.getPlayerData(player.getUniqueID());
            data.setAttackSpeed(data.getAttackSpeed()-this.data[1]);
            data.setMove(data.getMove() - this.data[0]);
            Skillset(player);
        }

        @Override
        public void setEffect() {
            // TODO Auto-generated method stub
            PlayerData data = main.game.getPlayerData(player.getUniqueID());
            data.setAttackSpeed(this.data[1] + data.getAttackSpeed());
            data.setMove(this.data[0] + data.getMove());
        }

        @SubscribeEvent(priority = EventPriority.LOW)
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
                        if(target.getCurrentHealth() <= 0) {
                            this.duration += 5 * 40;
                        }
                    }
                }
            }
        }
    }


}
