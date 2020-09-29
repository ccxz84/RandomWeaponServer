package RWAPI.Character.ForceMaster.entity;

import RWAPI.Character.EntityData;
import RWAPI.Character.SkillEntity;
import RWAPI.Character.monster.entity.AbstractMob;
import RWAPI.main;
import RWAPI.util.DamageSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
        List<EntityLivingBase> mini =  this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(2.5,0.75,2.5));
        EntityData target = null;
        EntityData attacker = null;
        if(this.thrower instanceof EntityPlayer) {
            attacker = main.game.getPlayerData(this.thrower.getUniqueID());
            for(EntityLivingBase mi : mini) {
                if(mi instanceof EntityPlayerMP && !(mi.equals(this.thrower))) {
                    target = main.game.getPlayerData(((EntityPlayer) mi).getUniqueID());
                }
                else if(mi instanceof AbstractMob) {
                    target = ((AbstractMob) mi).getData();
                }
                if(target != null && attacker != null) {
                    RWAPI.util.DamageSource source = RWAPI.util.DamageSource.causeSkill(attacker, target, this.skilldamage);
                    RWAPI.util.DamageSource.attackDamage(source,true);
                    DamageSource.EnemyStatHandler.EnemyStatSetter(source);
                    mi.attackEntityFrom(net.minecraft.util.DamageSource.causeThrownDamage(this, this.getThrower()), (float)1);
                }

            }
        }
        // TODO Auto-generated method stub
        super.setDead();
    }
}
