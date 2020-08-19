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
            0,0,0,0,0,0,0,0,0,0,0,0,0
    };
    protected final double[] skillAdcoe={
            0,0,0,0,0,0,0,0,0,0,0,0
    };
    protected final double[] skillApcoe={
            0,0,0,0,0,0,0,0,0,0,0,0
    };
    protected final double[] skillcost={
            50,50,55,55,55,70,70,70,75,75,75,90
    };

    protected final double[] cooldown = {
            12, 11.7, 11.5, 11.2, 11, 10, 10.9, 10.8, 10.5, 10.2, 10, 10
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
            if((this.skillTimer < 20)) {
                float x = (float) player.getLookVec().x;
                float y = (float) player.getLookVec().y;
                float z = (float) player.getLookVec().z;
                BlockPos pos = new BlockPos(player.posX + x * 0.5,player.posY + y * 0.5,player.posZ + z * 0.5);
                if(player.onGround && y < 0) {
                    y=0;
                }
                if(!(player.world.getBlockState(pos).getBlock().equals(Blocks.AIR))&&!(player.world.getBlockState(pos).getBlock().equals(Blocks.SNOW_LAYER)) && y != 0) {
                    x=0;
                    y=0;
                    z=0;
                }
                //player.move(MoverType.SELF, x * 0.5, y * 0.5, z * 0.5);
                player.connection.setPlayerLocation(player.posX + x * 0.5, player.posY + y * 0.5, player.posZ + z * 0.5, player.rotationYaw, player.rotationPitch);
            }
            super.skillTimer(event);
        }
    }
}
