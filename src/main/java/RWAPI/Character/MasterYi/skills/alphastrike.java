package RWAPI.Character.MasterYi.skills;

import RWAPI.Character.*;
import RWAPI.Character.MasterYi.MasterYi;
import RWAPI.Character.MasterYi.entity.EntityAlpha;
import RWAPI.Character.monster.entity.AbstractMob;
import RWAPI.main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class alphastrike extends MasterYiS {

    public static final int target_num = 4;

    private PlayerClass _class;
    private cool cool;

    protected final double[] skilldamage={
            90,93,96,102,105,1110,113,116,119,122,125,128
    };
    protected final double[] skillAdcoe={
            1,1,1,1,1,1,1,1,1,1,1,1
    };
    protected final double[] skillApcoe={
            0,0,0,0,0,0,0,0,0,0,0,0
    };
    protected final double[] skillcost={
            50,50,55,55,55,70,70,70,80,80,80,100
    };

    protected final double[] cooldown = {
            18,17.5,17,16.5,16,15,14.5,14,13.5,13,12,11
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
            data.nonWorking = true;
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv-1]));
            EntityAlpha entity = new EntityAlpha(player.world, player, (float) (skilldamage[lv-1]+ skillAdcoe[lv-1] * data.getAd()));
            entity.setNoGravity(true);
            entity.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.0f, 0);
            player.world.spawnEntity(entity);
            //player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 40, 255,false,false));
            this.cool = new cool(cooldown[lv-1],1, (EntityPlayerMP) player);
            data.nonWorking = false;
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

    class cool extends CooldownHandler {

        public cool(double cool, int id, EntityPlayerMP player) {
            super(cool, id, player);
        }

        public void reduceCool(){
            this.skillTimer += cooldown * 0.7;
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
