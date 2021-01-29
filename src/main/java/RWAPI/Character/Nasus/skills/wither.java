package RWAPI.Character.Nasus.skills;

import RWAPI.Character.CooldownHandler;
import RWAPI.Character.MasterYi.entity.EntityAlpha;
import RWAPI.Character.MasterYi.skills.alphastrike;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.Character.buff.Buff;
import RWAPI.Character.monster.entity.IMob;
import RWAPI.init.ModSkills;
import RWAPI.main;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class wither implements Skill {

    public final int duration = 5;

    buff buff;

    protected final double[][] skilldamage={
            {
                    47,
                    47,
                    47,
                    47,
                    47,
                    47,
                    47,
                    47,
                    59,
                    59,
                    59,
                    71,
                    71,
                    71,
                    71,
                    71,
                    71,
                    71
            },
            {
                    0.235,
                    0.235,
                    0.235,
                    0.235,
                    0.235,
                    0.235,
                    0.235,
                    0.235,
                    0.355,
                    0.355,
                    0.355,
                    0.415,
                    0.415,
                    0.415,
                    0.415,
                    0.415,
                    0.415,
                    0.415
            }
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
            80,
            80,
            80,
            80,
            80,
            80,
            80,
            80,
            80,
            80,
            80,
            80,
            80,
            80,
            80,
            80,
            80,
            80
    };

    protected final double[] cooldown = {
            15,
            15,
            15,
            15,
            15,
            14,
            14,
            14,
            13,
            13,
            13,
            12,
            12,
            12,
            12,
            11,
            11,
            11
    };

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
        if(data.getCool(2) <= 0 && data.getCurrentMana() > skillcost[lv-1]&& data.nonWorking == false) {
            float x = (float) player.getLookVec().x;
            float y = (float) player.getLookVec().y;
            float z = (float) player.getLookVec().z;
            List<Entity> entities = null;
            boolean flag = false;
            for(int i = 0; i < 6; i++){
                flag = false;
                entities = data.getPlayer().world.getEntitiesWithinAABBExcludingEntity(player,new AxisAlignedBB(Math.floor(player.posX) + x * i,(Math.floor(player.posY + player.eyeHeight) +y* i)-1,Math.floor(player.posZ)+z*i
                        ,(Math.floor(player.posX) + x * i)+1,(Math.floor(player.posY + player.eyeHeight) +y* i)+1,(Math.floor(player.posZ)+z*i)+1));
                for(Entity entity:entities){
                    if((entity instanceof EntityPlayerMP && entity != player)){
                        flag = true;
                        data.nonWorking = true;
                        data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv - 1]));
                        PlayerData target = main.game.getPlayerData(entity.getUniqueID());
                        buff = new buff(duration,target,true,true,skilldamage[0][lv-1],skilldamage[1][lv-1]);
                        this.raiseevent(data,skillcost[lv-1]);
                        new CooldownHandler(cooldown[lv - 1], 2, (EntityPlayerMP) player,true,data.getSkillacc());
                        data.nonWorking = false;
                        break;
                    }
                }
                if(flag == true) {
                    break;
                }
            }
        }
    }

    @Override
    public void Skillset(EntityPlayer player) {

    }

    @Override
    public void skillEnd(EntityPlayer player) {

    }

    @Override
    public void raiseevent(PlayerData data, double mana) {

    }

    @Override
    public double[] getskilldamage() {
        return new double[0];
    }

    @Override
    public double[] getskillAdcoe() {
        return new double[0];
    }

    @Override
    public double[] getskillApcoe() {
        return new double[0];
    }

    @Override
    public double[] getskillcost() {
        return skillcost;
    }

    @Override
    public double[] getcooldown() {
        return cooldown;
    }

    public double[][] getSkilldamage(){
        return this.skilldamage;
    }

    private class buff extends Buff {

        ItemStack icon;
        double minus;

        public buff(double duration, PlayerData player, boolean debuff, boolean clean, double... data) {
            super(duration, player, debuff, clean, data);
            icon = new ItemStack(ModSkills.wither); //바꿀것
        }

        @Override
        public void setEffect() {
            minus = (player.getMove()/100) * data[0];
            player.setMove(player.getMove() - minus);
            player.setPlusAttackspeed(player.getPlusAttackspeed() - data[1]);
            this.player.addBuff(this);
        }

        @Override
        public void resetEffect() {
            player.setMove(player.getMove() + minus);
            player.setPlusAttackspeed(player.getPlusAttackspeed() + data[1]);
            this.player.removeBuff(this);
        }

        @Override
        public ItemStack getBuffIcon() {
            return icon;
        }
    }
}
