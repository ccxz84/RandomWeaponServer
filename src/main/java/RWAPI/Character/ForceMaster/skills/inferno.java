package RWAPI.Character.ForceMaster.skills;

import RWAPI.Character.ForceMaster.ForceMaster;
import RWAPI.Character.ForceMaster.entity.EntityInferno;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;

public class inferno extends formaster {

    private cool handler;

    protected final double[] skilldamage={
            100,
            100,
            103,
            103,
            103,
            106,
            106,
            106,
            110,
            110,
            110,
            115,
            115,
            115,
            120,
            120,
            120,
            130
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
            0.9,
            0.9,
            0.9,
            0.9,
            0.9,
            0.9,
            0.9,
            0.9,
            0.9,
            0.9,
            0.9,
            1,
            1,
            1,
            1,
            1,
            1,
            1
    };
    protected final double[] skillcost={
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2

    };

    protected final double[] cooldown = {
            80,
            80,
            78,
            78,
            78,
            75,
            75,
            75,
            72,
            72,
            72,
            70,
            70,
            70,
            70,
            70,
            70,
            70
    };

    public inferno(ForceMaster forceMaster, Item skill){
        super(skill);
        this.forceMaster = forceMaster;
    }

    @Override
    public void skillpreExecute(EntityPlayer player) {


    }

    @Override
    public void skillExecute(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();

        if(data.getCool(4) <= 0 && data.getCurrentMana() > skillcost[lv-1]&&this.handler == null) {
            data.nonWorking = true;
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv-1]));
            EntityInferno inferno = new EntityInferno(player.world,player, (skilldamage[lv-1]+skillAdcoe[lv-1]*data.getAd()
                    + skillApcoe[lv-1]*data.getAp()));
            inferno.setNoGravity(true);
            inferno.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1f, 0);
            player.world.spawnEntity(inferno);
            this.raiseevent(data,skillcost[lv-1]);
            this.handler = new cool(cooldown[lv-1], 4, (EntityPlayerMP)player,this,data.getSkillacc());
            data.nonWorking = false;
        }
    }

    @Override
    public void Skillset(EntityPlayer player) {

    }

    @Override
    public void skillEnd(EntityPlayer player) {
        if(handler != null){
            handler.unregist();
            handler = null;
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

    @Override
    public void switchSkill(PlayerData data, int idx, boolean flag) {
        if(flag == true){
            if(handler != null){
                handler.switchCool(true);
            }
            else{
                data.setCool(idx, 0);
            }
        }
        else{
            if(handler != null){
                handler.switchCool(false);
            }
            else{
                data.setCool(idx, 0);
            }
        }
    }
}
