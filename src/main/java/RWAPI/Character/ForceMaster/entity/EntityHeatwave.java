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
import net.minecraft.world.World;

import java.util.List;

public class EntityHeatwave extends SkillEntity {
    public EntityHeatwave(World world) {
        super(world);
    }

    public EntityHeatwave(World worldIn, EntityLivingBase playerin, double skilldamage, double posX,double posY, double posZ) {
        super(worldIn, playerin, skilldamage);
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.judg = 0d;
    }

    @Override
    public void onUpdate() {
        if(this.thrower == null) {
            setDead();
        }
        // TODO Auto-generated method stub
        if(this.ticksExisted > 1) {
            this.setDead();
        }
        super.onUpdate();
    }

    @Override
    public void setDead() {
        if(main.game == null){
            super.setDead();
            return;
        }
        List<Entity> mini =  this.world.getEntitiesWithinAABB(Entity.class, this.getEntityBoundingBox().grow(2,0.7,2));
        EntityData target = null;
        EntityData attacker = null;
        if(this.thrower instanceof EntityPlayer) {
            attacker = main.game.getPlayerData(this.thrower.getUniqueID());
            for(Entity mi : mini) {
                if(mi instanceof EntityPlayerMP && !(mi.equals(this.thrower))) {
                    target = main.game.getPlayerData(((EntityPlayer) mi).getUniqueID());
                }
                else if(mi instanceof IMob) {
                    target = ((IMob) mi).getData();
                }
                if(target != null && attacker != null) {
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
}
