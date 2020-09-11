package RWAPI.Character.MasterYi.skills;

import RWAPI.Character.CooldownHandler;
import RWAPI.Character.PlayerClass;
import RWAPI.main;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.Character.buff.Buff;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class meditation extends MasterYiS {

    private PlayerClass _class;
    private bufftimer buft;
    private cool cool;

    public meditation(PlayerClass _class){
        this._class = _class;
    }

    protected final double[] skilldamage={
            1,1.1,1.2,1.3,1.4,1.6,1.7,1.8,1.9,2,2.2,2.4
    };
    protected final double[] skillAdcoe={
            0,0,0,0,0,0,0,0,0,0,0,0
    };
    protected final double[] skillApcoe={
            0.2,0.2,0.2,0.2,0.2,0.3,0.3,0.3,0.3,0.3,0.3,0.4
    };
    protected final double[] skillcost={
            50,50,50,50,50,70,70,70,70,70,70,80
    };

    protected final double[] cooldown = {
            28,27,26,25,24,22,21,20,19,18,17,15
    };

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
        if(data.getCool(2) <= 0 && data.getCurrentMana() > skillcost[lv-1]/*&& data.nonWorking == false*/ && cool == null) {
            data.nonWorking = true;
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv-1]));
            data.nonWorking = false;
            cool = new cool(cooldown[lv-1],2, (EntityPlayerMP) player);
            buft =  new bufftimer(4, (EntityPlayerMP) player,(float)skilldamage[lv-1] + ((float)skillApcoe[lv-1] * data.getAp())/40);

        }
    }

    @Override
    public void Skillset(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        buft = null;
        cool = null;
        data.nonWorking = false;
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

        int x,y,z;
        PlayerData pdata;

        public bufftimer(double duration, EntityPlayerMP player, double... data) {
            super(duration, player, data);
        }

        @Override
        public void setEffect() {
            x = (int) player.posX;
            y = (int) player.posY;
            z = (int) player.posZ;
            pdata = main.game.getPlayerData(player.getUniqueID());
        }

        @Override
        public void resetEffect() {
            Skillset(player);
        }

        @Override
        public void BuffTimer(TickEvent.ServerTickEvent event) throws Throwable {
            if((int)player.posX != x || (int)player.posY != y || (int)player.posZ != z || pdata.nonWorking == true) {
                resetEffect();
                MinecraftForge.EVENT_BUS.unregister(this);
            }
            super.BuffTimer(event);
            pdata.setCurrentHealth((pdata.getCurrentHealth() + data[0]) > pdata.getMaxHealth() ? pdata.getMaxHealth() : pdata.getCurrentHealth() + data[0]);
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
