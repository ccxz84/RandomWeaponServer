package RWAPI.Character.ForceMaster.skills;

import RWAPI.Character.CooldownHandler;
import RWAPI.Character.ForceMaster.ForceMaster;
import RWAPI.Character.ForceMaster.entity.EntityBlazingbeam;
import RWAPI.Character.ForceMaster.entity.EntityFirestorm;
import RWAPI.Character.ForceMaster.entity.EntityInferno;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class blazingbeam extends formaster {

    private cool handler;

    protected final double[] skilldamage={
            28,
            30,
            32,
            34,
            36,
            38,
            40,
            42,
            45,
            48,
            51,
            54,
            57,
            60,
            63,
            66,
            69,
            72
    };
    protected final double[] skillAdcoe={
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2
    };
    protected final double[] skillApcoe={
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1.2,
            1.2,
            1.2,
            1.4,
            1.4,
            1.4,
            1.4,
            1.4,
            1.4,
            1.4
    };
    protected final double[] skillcost={
            3,
            3,
            3,
            3,
            3,
            3,
            3,
            3,
            3,
            3,
            3,
            3,
            3,
            3,
            3,
            3,
            3,
            3
    };

    protected final double[] cooldown = {
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

    public blazingbeam(ForceMaster forceMaster, Item skill) {
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

        if(data.getCool(2) <= 0 && data.getCurrentMana() > skillcost[lv-1]&&this.handler == null)  {
            data.nonWorking = true;
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv-1]));
            EntityBlazingbeam blazingbeam = new EntityBlazingbeam(player.world,player, (skilldamage[lv-1]+skillAdcoe[lv-1]*data.getAd()
                    + skillApcoe[lv-1]*data.getAp()));
            blazingbeam.setNoGravity(true);
            blazingbeam.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1f, 0);
            player.world.spawnEntity(blazingbeam);
            this.handler = new cool(cooldown[lv-1], 2, (EntityPlayerMP)player,this);
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
