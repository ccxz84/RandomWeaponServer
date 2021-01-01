package RWAPI.Character.ForceMaster.entity;

import RWAPI.Character.EntityData;
import RWAPI.Character.SkillEntity;
import RWAPI.Character.monster.entity.IMob;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class EntityFirestorm extends SkillEntity {

    public EntityFirestorm(World world){
        super(world);
    }

    public EntityFirestorm(World worldIn, EntityLivingBase playerin, double skilldamage) {
        super(worldIn, playerin, skilldamage);
        this.posX = playerin.posX;
        this.posY = playerin.posY;
        this.posZ = playerin.posZ;
    }

    @Override
    public void onUpdate() {
        // TODO Auto-generated method stub
        if(this.ticksExisted > 2) {
            this.setDead();
        }
        super.onUpdate();
    }

    @Override
    public void setDead() {
        if (main.game == null) {
            super.setDead();
            return;
        }
        List<Entity> mini =  this.world.getEntitiesWithinAABB(Entity.class, this.getEntityBoundingBox().grow(2.5,0.75,2.5));
        EntityData target = null;
        EntityData attacker = null;
        if(this.thrower instanceof EntityPlayer) {
            attacker = main.game.getPlayerData(this.thrower.getUniqueID());
            for(Entity mi : mini) {
                if(mi instanceof EntityPlayerMP) {
                    if((mi.equals(this.thrower)))
                        continue;
                    target = main.game.getPlayerData(((EntityPlayer) mi).getUniqueID());
                }
                else if(mi instanceof IMob) {
                    target = ((IMob) mi).getData();
                }
                if(target != null && attacker != null) {
                    if(mi instanceof EntityLivingBase){
                        double distance = mi.getDistance(this.thrower);
                        Vec3d vec = new Vec3d((mi.posX-this.thrower.posX)/distance, (mi.posY-this.thrower.posY)/distance,(mi.posZ-this.thrower.posZ)/distance);
                        knockBack((EntityLivingBase) mi,this.thrower,3f,vec.x,vec.z);
                    }
                    DamageSource source = DamageSource.causeSkillMagic(attacker, target, this.skilldamage);
                    DamageSource.attackDamage(source,true);
                    DamageSource.EnemyStatHandler.EnemyStatSetter(source);
                    mi.attackEntityFrom(net.minecraft.util.DamageSource.causeThrownDamage(this, this.getThrower()), (float)1);
                }

            }
        }
        // TODO Auto-generated method stub
        super.setDead();
    }

    public void knockBack(EntityLivingBase target, Entity entityIn, float strength, double xRatio, double zRatio){
        target.maxHurtResistantTime = 0;
        target.hurtResistantTime = 0;
        net.minecraftforge.event.entity.living.LivingKnockBackEvent event = net.minecraftforge.common.ForgeHooks.onLivingKnockBack(target, entityIn, strength, xRatio, zRatio);
        if(event.isCanceled()) return;
        strength = strength; xRatio = xRatio; zRatio = zRatio;
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
}
