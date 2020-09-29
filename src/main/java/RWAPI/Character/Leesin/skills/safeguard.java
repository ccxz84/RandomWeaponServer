package RWAPI.Character.Leesin.skills;

import RWAPI.Character.CooldownHandler;
import RWAPI.Character.PlayerClass;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class safeguard implements Skill {

    private PlayerClass _class;
    private cool handler;

    protected final double[] skilldamage={
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
    protected final double[] skillAdcoe={
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
            50,
            50,
            50,
            50,
            50,
            60,
            60,
            60,
            70,
            70,
            70,
            80,
            80,
            80,
            80,
            80,
            80,
            80

    };

    protected final double[] cooldown = {
            13,
            13,
            13,
            13,
            13,
            11,
            11,
            11,
            9,
            9,
            9,
            8,
            8,
            8,
            8,
            8,
            8,
            8

    };

    public safeguard(PlayerClass _class){
        this._class = _class;
    }

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
        if(data.getCool(2) <= 0 && data.getCurrentMana() > skillcost[lv-1]) {
            data.nonWorking = true;
            data.nonWorking = false;
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv-1]));
            this.handler = new cool(cooldown[lv-1], 2, (EntityPlayerMP) player);
            _class.skill0(player);
        }
    }

    @Override
    public void Skillset(EntityPlayer player) {

    }

    @Override
    public void skillEnd(EntityPlayer player) {

    }

    class cool extends CooldownHandler {

        public cool(double cool, int id, EntityPlayerMP player) {
            super(cool, id, player);
        }

        @Override
        public void skillTimer(TickEvent.ServerTickEvent event) throws Throwable {
            if((this.skillTimer < 15)) {
                float x = (float) player.getLookVec().x;
                float y = (float) player.getLookVec().y;
                float z = (float) player.getLookVec().z;
                BlockPos pos = new BlockPos(player.posX + x * 0.7,player.posY + y * 0.7,player.posZ + z * 0.7);
                if(player.onGround && y < 0) {
                    y=0;
                }
                if(!(player.world.getBlockState(pos).getBlock().equals(Blocks.AIR))&&!(player.world.getBlockState(pos).getBlock().equals(Blocks.SNOW_LAYER)) && y != 0) {
                    x=0;
                    y=0;
                    z=0;
                }
                //player.move(MoverType.SELF, x * 0.5, y * 0.5, z * 0.5);
                player.connection.setPlayerLocation(player.posX + x * 0.7, player.posY + y * 0.7, player.posZ + z * 0.7, player.rotationYaw, player.rotationPitch);
            }
            super.skillTimer(event);
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
