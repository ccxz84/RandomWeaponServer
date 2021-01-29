package RWAPI.Character.Leesin.skills;

import RWAPI.Character.*;
import RWAPI.Character.Nasus.skills.wither;
import RWAPI.Character.monster.entity.IMob;
import RWAPI.game.event.BaseEvent;
import RWAPI.game.event.UseSkillEventHandle;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;
import java.util.Random;

public class dragonsrage implements Skill {

    private PlayerClass _class;

    protected final double[] skilldamage={
            250,
            250,
            290,
            290,
            290,
            340,
            340,
            340,
            400,
            400,
            400,
            450,
            450,
            450,
            460,
            460,
            460,
            470
    };
    protected final double[] skillAdcoe={
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.9,
            0.9,
            0.9,
            0.9,
            0.9,
            0.9,
            0.9,
            0.9,
            0.9,
            0.9

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
            40,
            40,
            40,
            40,
            40,
            45,
            45,
            45,
            45,
            45,
            45,
            50,
            50,
            50,
            50,
            50,
            50,
            50
    };

    protected final double[] cooldown = {
            100,
            100,
            95,
            95,
            95,
            90,
            90,
            90,
            85,
            85,
            85,
            80,
            80,
            80,
            80,
            80,
            80,
            80

    };

    public dragonsrage(PlayerClass _class){
        this._class = _class;
    }

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
        if(data.getCool(4) <= 0&& data.getCurrentMana() > skillcost[lv-1]) {
            data.nonWorking = true;
            float x = (float) player.getLookVec().x;
            float y = (float) player.getLookVec().y;
            float z = (float) player.getLookVec().z;
            List<Entity> entities = null;
            boolean flag = false;
            for(int i = 0; i < 3; i++){
                flag = false;
                entities = data.getPlayer().world.getEntitiesWithinAABBExcludingEntity(player,new AxisAlignedBB(Math.floor(player.posX) + x * i,(Math.floor(player.posY + player.eyeHeight) +y* i)-1,Math.floor(player.posZ)+z*i
                        ,(Math.floor(player.posX) + x * i)+1,(Math.floor(player.posY + player.eyeHeight) +y* i)+1,(Math.floor(player.posZ)+z*i)+1));
                for(Entity entity:entities){
                    if((entity instanceof EntityPlayerMP && entity != player)){
                        flag = true;
                        data.nonWorking = true;
                        data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv - 1]));
                        PlayerData target = main.game.getPlayerData(entity.getUniqueID());
                        knockBack(target.getPlayer(), 2, -data.getPlayer().getLookVec().x, -data.getPlayer().getLookVec().z);
                        DamageSource sourcee = DamageSource.causeSkillMeleePhysics(data, target, skilldamage[lv-1] + skillAdcoe[lv-1] * data.getAd());
                        DamageSource.attackDamage(sourcee,true);
                        DamageSource.EnemyStatHandler.EnemyStatSetter(sourcee);
                        target.getEntity().attackEntityFrom(net.minecraft.util.DamageSource.causeThrownDamage(new SkillEntity(data.getEntity().world), data.getEntity()), (float) 1);
                        data.nonWorking = false;
                        new CooldownHandler(this.cooldown[lv-1], 4, (EntityPlayerMP) player,true,data.getSkillacc());
                        this.raiseevent(data,skillcost[lv-1]);
                        _class.skill0(player);
                        break;
                    }
                }
                if(flag == true) {
                    break;
                }
            }
        }
    }

    public void knockBack(EntityLivingBase target, float strength, double xRatio, double zRatio){

        target.maxHurtResistantTime = 0;
        target.hurtResistantTime = 0;
        target.isAirBorne = true;
        target.velocityChanged = true;
        float f = MathHelper.sqrt(xRatio * xRatio + zRatio * zRatio);
        target.motionX /= 2.0D;
        target.motionZ /= 2.0D;
        target.motionX -= xRatio / (double)f * (double)strength;
        target.motionZ -= zRatio / (double)f * (double)strength;
        if (target.onGround)
        {
            target.motionY /= 2.0D;
            target.motionY += (double)strength;

            if (target.motionY > 0.4000000059604645D)
            {
                target.motionY = 0.4000000059604645D;
            }
        }
        if(target instanceof EntityPlayerMP){
            ((EntityPlayerMP)target).connection.sendPacket(new SPacketEntityVelocity(target));
        }

        target.velocityChanged = false;
    }

    @Override
    public void Skillset(EntityPlayer player) {

    }

    @Override
    public void skillEnd(EntityPlayer player) {

    }

    @Override
    public void raiseevent(PlayerData data, double mana) {
        UseSkillEventHandle.UseSkillEvent event = new UseSkillEventHandle.UseSkillEvent(data,mana);
        for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
            main.game.getEventHandler().RunEvent(event,priority);
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
