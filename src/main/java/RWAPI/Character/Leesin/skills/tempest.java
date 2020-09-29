package RWAPI.Character.Leesin.skills;

import RWAPI.Character.CooldownHandler;
import RWAPI.Character.Leesin.entity.EntityTempest;
import RWAPI.Character.PlayerClass;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.Character.buff.Buff;
import RWAPI.main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;

public class tempest implements Skill {

    private PlayerClass _class;
    private cool handler;
    private Buff buff;

    public tempest(PlayerClass _class){
        this._class = _class;
    }

    protected final double[] skilldamage={
            90,
            94,
            98,
            102,
            106,
            115,
            120,
            125,
            131,
            137,
            143,
            149,
            155,
            161,
            167,
            173,
            179,
            185
    };
    protected final double[] skillAdcoe={
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.35,
            0.35,
            0.35,
            0.35,
            0.35,
            0.35,
            0.4,
            0.4,
            0.4,
            0.4,
            0.4,
            0.4,
            0.4
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
            70,
            70,
            70,
            70,
            70,
            75,
            75,
            75,
            75,
            75,
            75,
            80,
            80,
            80,
            80,
            80,
            80,
            80

    };

    protected final double[] cooldown = {
            12,
            12,
            12,
            12,
            12,
            11,
            11,
            11,
            10,
            10,
            10,
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
        if(data.getCool(3) <= 0 && data.getCurrentMana() > skillcost[lv-1]) {
            data.nonWorking = true;
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv-1]));
            EntityTempest tempest = new EntityTempest(player.world, player, (skilldamage[lv-1]+ skillAdcoe[lv-1] * data.getAd()));
            tempest.setNoGravity(true);
            tempest.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.0f, 0);
            player.world.spawnEntity(tempest);
            buff = new Buff(1, data.getPlayer()) {
                double minus;
                @Override
                public void setEffect() {
                    PlayerData data = main.game.getPlayerData(player.getUniqueID());
                    minus = (data.getMove() * 0.7);
                    data.setMove(data.getMove() - minus);
                }

                @Override
                public void resetEffect() {
                    PlayerData data = main.game.getPlayerData(player.getUniqueID());
                    data.setMove(data.getMove() + minus);
                    data.removeBuff(this);
                    buff = null;
                    data.nonWorking = false;
                }
            };
            data.addBuff(buff);
            this.handler = new cool(cooldown[lv-1], 3, (EntityPlayerMP) player);
            _class.skill0(player);

        }
    }

    @Override
    public void Skillset(EntityPlayer player){

    }

    @Override
    public void skillEnd(EntityPlayer player) {

    }
    class cool extends CooldownHandler {

        public cool(double cool, int id, EntityPlayerMP player) {
            super(cool, id, player);
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
