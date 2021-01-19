package RWAPI.Character.ForceMaster.skills;

import RWAPI.Character.ForceMaster.ForceMaster;
import RWAPI.Character.ForceMaster.entity.EntityInferno;
import RWAPI.Character.ForceMaster.entity.EntitySnowball;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;

public class snowball extends formaster{

    private cool handler;

    protected final double[][] skilldamage={
            {//이속 가소 %단위
                    30,
                    30,
                    30,
                    30,
                    30,
                    30,
                    30,
                    30,
                    30,
                    30,
                    30,
                    30,
                    30,
                    30,
                    30,
                    30,
                    30,
                    30
            },
            {// 데미지
                    70,
                    73,
                    76,
                    79,
                    82,
                    85,
                    88,
                    91,
                    95,
                    99,
                    103,
                    107,
                    111,
                    115,
                    119,
                    123,
                    127,
                    131

            }
    };
    protected final double[] skillAdcoe={
            0.25,
            0.25,
            0.25,
            0.25,
            0.25,
            0.25,
            0.25,
            0.25,
            0.25,
            0.25,
            0.25,
            0.35,
            0.35,
            0.35,
            0.35,
            0.35,
            0.35,
            0.35
    };
    protected final double[] skillApcoe={
            0.5,
            0.5,
            0.5,
            0.5,
            0.5,
            0.5,
            0.5,
            0.5,
            0.5,
            0.5,
            0.5,
            0.6,
            0.6,
            0.6,
            0.6,
            0.6,
            0.6,
            0.6
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
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5
    };

    public snowball(ForceMaster forceMaster, Item skill){
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

        if(data.getCool(2) <= 0 && data.getCurrentMana() > skillcost[lv-1]&&this.handler == null) {
            data.nonWorking = true;
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv-1]));
            EntitySnowball snowball = new EntitySnowball(player.world,player, (skilldamage[1][lv-1]+skillAdcoe[lv-1]*data.getAd()
                    + skillApcoe[lv-1]*data.getAp()),skilldamage[0][lv-1],2);
            snowball.setNoGravity(true);
            snowball.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1f, 0);
            player.world.spawnEntity(snowball);
            this.raiseevent(data,skillcost[lv-1]);
            this.handler = new cool(cooldown[lv-1], 2, (EntityPlayerMP)player,this,data.getSkillacc());
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
        return this.skilldamage[0];
    }


    public double[] getskilldamage1() {
        return this.skilldamage[1];
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
