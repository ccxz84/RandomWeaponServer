package RWAPI.Character.MasterYi.skills;

import RWAPI.Character.*;
import RWAPI.Character.MasterYi.MasterYi;
import RWAPI.Character.MasterYi.entity.EntityAlpha;
import RWAPI.Character.monster.entity.IMob;
import RWAPI.main;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class alphastrike extends MasterYiS {

    public static final int target_num = 4;

    private PlayerClass _class;
    private cool cool;

    protected final double[] skilldamage={
            80,
            81,
            82,
            83,
            84,
            85.5,
            87,
            88.5,
            91,
            93.5,
            96,
            100,
            104,
            108,
            112,
            116,
            120,
            124

    };
    protected final double[] skillAdcoe={
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
            1,
            1,
            1,
            1

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
            18,
            18,
            18,
            18,
            18,
            17.5,
            17.5,
            17.5,
            17,
            17,
            17,
            16,
            16,
            16,
            16,
            16,
            16,
            16
    };

    public alphastrike(MasterYi masterYi) {
        super();
        this._class = masterYi;
    }

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
        if(data.getCool(1) <= 0 && data.getCurrentMana() > skillcost[lv-1]&& data.nonWorking == false && cool == null) {
            float x = (float) player.getLookVec().x;
            float y = (float) player.getLookVec().y;
            float z = (float) player.getLookVec().z;
            double posx = 0, posy = 0,posz = 0;
            List<Entity> entities = null;
            boolean flag = false;
            for(int i = 0; i < 6; i++){
                flag = false;
                entities = data.getPlayer().world.getEntitiesWithinAABBExcludingEntity(player,new AxisAlignedBB(Math.floor(player.posX) + x * i,(Math.floor(player.posY + player.eyeHeight) +y* i)-1,Math.floor(player.posZ)+z*i
                        ,(Math.floor(player.posX) + x * i)+1,(Math.floor(player.posY + player.eyeHeight) +y* i)+1,(Math.floor(player.posZ)+z*i)+1));
                //System.out.println("x : " + (Math.floor(player.posX) + x * i)+ " Y : " + (Math.floor(player.posY + player.eyeHeight) +y* i) + " z : " + (Math.floor(player.posZ)+z*i));
                //pos = new BlockPos(Math.floor(player.posX) + x * i, Math.floor(player.posY + player.eyeHeight) +y* i ,Math.floor(player.posZ)+z*i);
                for(Entity entity:entities){
                    if((entity instanceof EntityPlayerMP && entity != player) || entity instanceof IMob){
                        flag = true;
                        break;
                    }
                }
                if(flag == true) {
                    posx = Math.floor(player.posX) + x * i;
                    posy = Math.floor(player.posY + player.eyeHeight) +y* i;
                    posz = Math.floor(player.posZ)+z*i;
                    break;
                }
            }
            if(flag == true) {
                ((EntityPlayerMP)player).connection.setPlayerLocation(posx,posy,posz,player.rotationYaw,player.rotationPitch);
                data.nonWorking = true;
                data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv - 1]));
                EntityAlpha entity = new EntityAlpha(player.world, player, (float) (skilldamage[lv - 1] + skillAdcoe[lv - 1] * data.getAd()));
                entity.setNoGravity(true);
                entity.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.0f, 0);
                player.world.spawnEntity(entity);
                this.raiseevent(data,skillcost[lv-1]);
                this.cool = new cool(cooldown[lv - 1], 1, (EntityPlayerMP) player,data.getSkillacc());
                data.nonWorking = false;
            }
        }
    }

    @Override
    public void Skillset(EntityPlayer player) {
        cool = null;
    }

    @Override
    public void skillEnd(EntityPlayer player) {

    }

    @Override
    void reduceCool() {
        if(cool != null)
            this.cool.reduceCool();
    }

    public void doublereducecool(){
        if(cool != null)
            this.cool.doublereducecool();
    }

    class cool extends CooldownHandler {

        public cool(double cool, int id, EntityPlayerMP player,int skillacc) {
            super(cool, id, player,true,skillacc);
        }

        public void reduceCool(){
            this.skillTimer += cooldown * 0.7;
        }

        public void doublereducecool(){
            skillTimer += 20;
        }

        @Override
        public void skillTimer(TickEvent.ServerTickEvent event) throws Throwable {
            if(skillTimer > cooldown) {
                Skillset(player);
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
