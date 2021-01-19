package RWAPI.Character.ForceMaster.entity;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.SkillEntity;
import RWAPI.Character.monster.entity.IMob;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.entity.Entity;
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
        this.judg = 0.8d;
    }

    @Override
    public void onUpdate() {
        if(ticksExisted >7) {
            setDead();
        }
        super.onUpdate();
    }

    protected void SkillImpact(RayTraceResult result){
        if (main.game == null) {
            super.setDead();
            return;
        }
        if(!(result.entityHit != null && result.entityHit instanceof Entity)){
            super.setDead();
            return;
        }
        if(this.thrower == null){
            super.setDead();
            return;
        }
        else if(!(result.entityHit.getUniqueID().equals(this.thrower.getUniqueID()))) {
            PlayerData attacker = main.game.getPlayerData(this.thrower.getUniqueID());
            EntityData target;
            if (result.entityHit instanceof IMob) {
                IMob mob = (IMob) result.entityHit;
                target = mob.getData();
                DamageSource source = DamageSource.causeSkillMagic(attacker, target, this.skilldamage);
                DamageSource.attackDamage(source, true);
                DamageSource.EnemyStatHandler.EnemyStatSetter(source);
            } else if (result.entityHit instanceof EntityPlayer) {
                target = main.game.getPlayerData(result.entityHit.getUniqueID());
                DamageSource source = DamageSource.causeSkillMagic(attacker, target, this.skilldamage);
                DamageSource.attackDamage(source, true);
                DamageSource.EnemyStatHandler.EnemyStatSetter(source);
            }
            result.entityHit.attackEntityFrom(net.minecraft.util.DamageSource.causeThrownDamage(this, this.getThrower()), (float)1);
            super.setDead();
        }
    }
}
