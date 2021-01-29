package RWAPI.Character.ForceMaster.skills;

import RWAPI.Character.ForceMaster.ForceMaster;
import RWAPI.Character.ForceMaster.entity.EntityFirestorm;
import RWAPI.Character.ForceMaster.entity.EntityIcecoil;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;

public class icecoil extends formaster {

    private ForceMaster forceMaster;
    private double heal = 1;

    protected final double[] skilldamage={
            55,
            57,
            59,
            61,
            63,
            65,
            67,
            69,
            72,
            75,
            78,
            81,
            84,
            87,
            90,
            93,
            96,
            99
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
            0.25,
            0.25,
            0.25,
            0.25,
            0.25,
            0.25,
            0.25
    };
    protected final double[] skillApcoe={
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
            0.35,
            0.35,
            0.35,
            0.35,
            0.35,
            0.35,
            0.35

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
            7,
            7,
            7,
            7,
            7,
            7,
            7,
            7,
            6,
            6,
            6,
            6,
            6,
            6,
            6,
            6,
            6,
            6
    };
    private cool handler;

    public icecoil(ForceMaster forceMaster, Item skill) {
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
            float x = (float) player.getLookVec().x;
            float y = (float) player.getLookVec().y;
            float z = (float) player.getLookVec().z;
            BlockPos pos = null;
            double posx = 0, posy = 0,posz = 0;
            for(int i = 0; i < 6; i++){
                pos = new BlockPos(Math.floor(player.posX) + x * i, Math.floor(player.posY + player.eyeHeight) +y* i ,Math.floor(player.posZ)+z*i);
                if(!(player.world.getBlockState(pos).getBlock().equals(Blocks.AIR))) {
                    posx = pos.getX();
                    posy = pos.getY()+1;
                    posz = pos.getZ();
                    break;
                }
            }
            if(!(player.world.getBlockState(pos).getBlock().equals(Blocks.AIR))){
                if(player.world.getBlockState(pos).getBlock().equals(Blocks.SNOW_LAYER)) {
                    posy -= 1;
                }
                /*System.out.println("블록 : " + player.world.getBlockState(pos).getBlock().getUnlocalizedName() +
                        "좌표 X : " + posx + " Y : " + posy + " Z : " + posz);*/
                data.nonWorking = true;
                data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv-1]));

                EntityIcecoil coil = new EntityIcecoil(player.world,player, (skilldamage[lv-1]+skillAdcoe[lv-1]*data.getAd()
                        + skillApcoe[lv-1]*data.getAp()),1,posx,posy,posz);
                coil.setNoGravity(true);
                coil.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.0f, 0);
                player.world.spawnEntity(coil);
                this.raiseevent(data,skillcost[lv-1]);
                this.handler = new cool(cooldown[lv-1], 1, (EntityPlayerMP)player,this,data.getSkillacc());
                data.nonWorking = false;
            }
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
