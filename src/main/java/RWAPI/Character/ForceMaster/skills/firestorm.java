package RWAPI.Character.ForceMaster.skills;

import RWAPI.Character.CooldownHandler;
import RWAPI.Character.ForceMaster.ForceMaster;
import RWAPI.Character.ForceMaster.entity.EntityFirestorm;
import RWAPI.Character.Leesin.skills.tempest;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class firestorm extends formaster {

    private cool handler;

    protected final double[] skilldamage={
            70,
            72,
            74,
            76,
            78,
            80,
            82,
            84,
            87,
            90,
            93,
            96,
            99,
            102,
            105,
            108,
            111,
            114

    };
    protected final double[] skillAdcoe={
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2
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
            0.5,
            0.5,
            0.5,
            0.5,
            0.5,
            0.5,
            0.5
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
            11,
            11,
            11,
            11,
            11,
            10,
            10,
            10,
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

    public firestorm(ForceMaster forceMaster, Item skill){
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

        if(data.getCool(1) <= 0 && data.getCurrentMana() > skillcost[lv-1]&&this.handler == null) {
            data.nonWorking = true;
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv-1]));
            EntityFirestorm storm = new EntityFirestorm(player.world,player, (skilldamage[lv-1]+skillAdcoe[lv-1]*data.getAd()
                    + skillApcoe[lv-1]*data.getAp()));
            storm.setNoGravity(true);
            storm.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.0f, 0);
            player.world.spawnEntity(storm);
            this.raiseevent(data,skillcost[lv-1]);
            this.handler = new cool(cooldown[lv-1], 1, (EntityPlayerMP)player,this,data.getSkillacc());
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
    public void switchSkill(PlayerData data,int idx,boolean flag) {
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
