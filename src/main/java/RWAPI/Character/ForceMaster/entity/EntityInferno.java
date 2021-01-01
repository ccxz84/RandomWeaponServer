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

public class EntityInferno extends SkillEntity {

    public EntityInferno(World world){
        super(world);
    }

    public EntityInferno(World worldIn, EntityLivingBase playerin, double skilldamage) {
        super(worldIn, playerin, skilldamage);
        this.judg = 1.3d;
    }

    @Override
    public void onUpdate() {
        if(ticksExisted >15) {
            setDead();
        }
        super.onUpdate();
    }

    protected void SkillImpact(RayTraceResult result){
        if(!(result.entityHit != null && result.entityHit instanceof Entity)){
            if (main.game == null)
                setDead();
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

            setDead();
        }
    }
}
