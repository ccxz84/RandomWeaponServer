package RWAPI.Character.ForceMaster.entity;

import RWAPI.Character.EntityData;
import RWAPI.Character.SkillEntity;
import RWAPI.Character.monster.entity.AbstractMob;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class EntityIcerain extends SkillEntity {
    List<EntityLivingBase> baselist;
    double effect, duration;
    EntityPlayer player;

    public EntityIcerain(World world){
        super(world);
    }

    public EntityIcerain(World worldIn, EntityLivingBase playerin, double skilldamage, double posX, double posY, double posZ, double effect, double duration) {
        super(worldIn, playerin, skilldamage);
        baselist = new ArrayList<EntityLivingBase>();
        this.effect = effect;
        this.duration = duration;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.player = (EntityPlayer) playerin;
    }

    @Override
    public void onUpdate() {
        if (main.game == null)
            setDead();
        if(ticksExisted >duration * 20) {
            setDead();
        }
        if(ticksExisted % 2 == 0){
            DamageEntity();
        }

        List<EntityLivingBase> comparelist =  this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(3,0.75,3));

        if(this.thrower instanceof EntityPlayer){
            for(EntityLivingBase entity : comparelist){
                if(!baselist.contains(entity)&&!entity.equals(this.player)){
                    setEffect(entity);
                }
            }

            for (EntityLivingBase entity : baselist){
                if(!comparelist.contains(entity)&&!entity.equals(this.player)){
                    removeEffect(entity);
                }
            }
            baselist = comparelist;
        }

        super.onUpdate();
    }

    @Override
    public void setDead() {
        super.setDead();
        if(main.game == null)
            return;
        if(baselist == null)
            return;
        if(!baselist.isEmpty()){
            for(EntityLivingBase entity : baselist){
                if(!entity.equals(this.player))
                    removeEffect(entity);
            }
        }
    }

    private void removeEffect(EntityLivingBase entity) {
        EntityData target = null;
        if(entity instanceof EntityPlayer){
            target = main.game.getPlayerData(entity.getUniqueID());
            target.setMove(target.getMove() + effect);
        }
    }

    private void setEffect(EntityLivingBase entity) {
        EntityData target = null;
        if(entity instanceof EntityPlayer){
            target = main.game.getPlayerData(entity.getUniqueID());
            target.setMove(target.getMove() - effect);
        }
    }

    private void DamageEntity() {
        List<EntityLivingBase> mini =  this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(3,0.75,3));
        EntityData target = null;
        EntityData attacker = null;
        if(this.player instanceof EntityPlayer) {
            attacker = main.game.getPlayerData(this.player.getUniqueID());
            for(EntityLivingBase mi : mini) {
                if(mi instanceof EntityPlayerMP && !(mi.equals(this.player))) {
                    target = main.game.getPlayerData(((EntityPlayer) mi).getUniqueID());
                }
                else if(mi instanceof AbstractMob) {
                    target = ((AbstractMob) mi).getData();
                }
                if(target != null && attacker != null&& !(mi.equals(this.player))) {
                    DamageSource source = DamageSource.causeSkillMagic(attacker, target, this.skilldamage/5);
                    DamageSource.attackDamage(source,true);
                    DamageSource.EnemyStatHandler.EnemyStatSetter(source);
                    if(this.ticksExisted % 10 == 0){
                        mi.attackEntityFrom(net.minecraft.util.DamageSource.causeThrownDamage(this, this.getThrower()), (float)1);
                    }
                }
            }
        }
    }
}
