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

public class EntityIcecoil extends SkillEntity {

    double heal;
    EntityPlayer player;

    public EntityIcecoil(World world) {
        super(world);
    }

    public EntityIcecoil(World worldIn, EntityLivingBase playerin, double skilldamage,double heal, double posX, double posY, double posZ) {
        super(worldIn, playerin, skilldamage);
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.heal = heal;
        this.player = (EntityPlayer) playerin;
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
        if(main.game == null){
            super.setDead();
            return;
        }
        List<EntityLivingBase> mini =  this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(2.5,0.75,2.5));
        EntityData target = null;
        EntityData attacker = null;
        if(this.player instanceof EntityPlayer) {
            attacker = main.game.getPlayerData(this.player.getUniqueID());
            for(EntityLivingBase mi : mini) {
                if(mi instanceof EntityPlayerMP && !(mi.equals(this.player))) {
                    System.out.println("this player : " +this.player.getName()
                            + " mi entity : "+ mi.getName());
                    target = main.game.getPlayerData(((EntityPlayer) mi).getUniqueID());
                }
                else if(mi instanceof AbstractMob) {
                    target = ((AbstractMob) mi).getData();
                }
                if(target != null && attacker != null&& !(mi.equals(this.player))) {
                    RWAPI.util.DamageSource source = RWAPI.util.DamageSource.causeSkill(attacker, target, this.skilldamage);
                    RWAPI.util.DamageSource.attackDamage(source,true);
                    DamageSource.EnemyStatHandler.EnemyStatSetter(source);
                    mi.attackEntityFrom(net.minecraft.util.DamageSource.causeThrownDamage(this, this.getThrower()), (float)1);

                    double healmana = attacker.getCurrentMana() + heal >= attacker.getMaxMana() ? attacker.getMaxMana() : attacker.getCurrentMana() + heal;
                    attacker.setCurrentMana(healmana);
                }
            }
        }
        // TODO Auto-generated method stub
        super.setDead();
    }
}