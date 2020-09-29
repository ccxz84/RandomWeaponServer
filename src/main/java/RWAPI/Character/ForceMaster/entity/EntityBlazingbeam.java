package RWAPI.Character.ForceMaster.entity;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.SkillEntity;
import RWAPI.Character.monster.entity.AbstractMob;
import RWAPI.main;
import RWAPI.util.DamageSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityBlazingbeam extends SkillEntity {

    public EntityBlazingbeam(World world){
        super(world);
    }

    public EntityBlazingbeam(World worldIn, EntityLivingBase playerin, double skilldamage) {
        super(worldIn, playerin, skilldamage);
        d1 = 0.3;
    }

    @Override
    public void onUpdate() {
        if(ticksExisted >15) {
            setDead();
        }
        super.onUpdate();
    }

    protected void SkillImpact(RayTraceResult result){
        if (main.game == null) {
            super.setDead();
            return;
        }
        if(!(result.entityHit != null && result.entityHit instanceof EntityLivingBase)){
            super.setDead();
        }
        else if(!(result.entityHit.getUniqueID().equals(this.thrower.getUniqueID()))) {
            PlayerData attacker = main.game.getPlayerData(this.thrower.getUniqueID());
            EntityData target;
            if (result.entityHit instanceof AbstractMob) {
                AbstractMob mob = (AbstractMob) result.entityHit;
                target = mob.getData();
                RWAPI.util.DamageSource source = RWAPI.util.DamageSource.causeSkill(attacker, target, this.skilldamage);
                RWAPI.util.DamageSource.attackDamage(source, true);
                DamageSource.EnemyStatHandler.EnemyStatSetter(source);
            } else if (result.entityHit instanceof EntityPlayer) {
                target = main.game.getPlayerData(result.entityHit.getUniqueID());
                RWAPI.util.DamageSource source = RWAPI.util.DamageSource.causeSkill(attacker, target, this.skilldamage);
                RWAPI.util.DamageSource.attackDamage(source, true);
                DamageSource.EnemyStatHandler.EnemyStatSetter(source);
            }
            result.entityHit.attackEntityFrom(net.minecraft.util.DamageSource.causeThrownDamage(this, this.getThrower()), (float)1);
            super.setDead();
        }
    }
}
