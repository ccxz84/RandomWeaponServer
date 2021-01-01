package RWAPI.Character.Leesin.entity;

import RWAPI.Character.Leesin.skills.sonicwave;
import RWAPI.main;
import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.SkillEntity;
import RWAPI.Character.Leesin.Leesin;
import RWAPI.Character.monster.entity.IMob;
import RWAPI.util.DamageSource.DamageSource.EnemyStatHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityUmpa extends SkillEntity {
	
	public EntityUmpa(World worldIn) {
		super(worldIn);
	}

	public EntityUmpa(World worldIn, EntityPlayer playerin, float skilldamage) {
		super(worldIn,playerin, skilldamage);
		this.judg = 0.75d;
	}

	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		super.shoot(x, y, z, velocity, inaccuracy);
	}

	@Override
	public void shoot(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset,
			float velocity, float inaccuracy) {
		super.shoot(entityThrower, rotationPitchIn, rotationYawIn, pitchOffset, velocity, inaccuracy);
	}

	@Override
	public void setVelocity(double x, double y, double z) {
		// TODO Auto-generated method stub
		super.setVelocity(x, y, z);
	}

	@Override
	public void onUpdate() {
		if(ticksExisted >20) {
			setDead();
		}
		super.onUpdate();
		
	}



	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender() {
		return 0xf000f0;
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		// TODO Auto-generated method stub

		
	}

	protected void SkillImpact(RayTraceResult result){
		if(result.entityHit != null && result.entityHit instanceof Entity) {
			if(main.game == null )
				setDead();
			if(!(result.entityHit.getUniqueID().equals(this.thrower.getUniqueID()))) {
				PlayerData attacker = main.game.getPlayerData(this.thrower.getUniqueID());
				EntityData target;
				if(result.entityHit instanceof IMob) {
					IMob mob = (IMob) result.entityHit;
					target = mob.getData();
					RWAPI.util.DamageSource.DamageSource source = RWAPI.util.DamageSource.DamageSource.causeSkillPhysics(attacker, target, this.skilldamage);
					RWAPI.util.DamageSource.DamageSource.attackDamage(source,true);
					EnemyStatHandler.EnemyStatSetter(source);
				}
				else if(result.entityHit instanceof EntityPlayer){
					target = main.game.getPlayerData(result.entityHit.getUniqueID());
					RWAPI.util.DamageSource.DamageSource source = RWAPI.util.DamageSource.DamageSource.causeSkillPhysics(attacker, target, this.skilldamage);
					RWAPI.util.DamageSource.DamageSource.attackDamage(source,true);
					EnemyStatHandler.EnemyStatSetter(source);
				}
				makeEffect((EntityLivingBase) result.entityHit, attacker.getPlayer());
				result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)1);
				setDead();
			}
		}
	}
	
	public void makeEffect(EntityLivingBase entity, EntityPlayerMP attacker) {
		if(this.isDead) {
			return;
		}
		sonicwave wave;
		EntityResonating resonating;
		if(main.game.getPlayerData(this.thrower.getUniqueID()).get_class() instanceof Leesin) {
			wave = (sonicwave) main.game.getPlayerData(this.thrower.getUniqueID()).get_class().getSkill(1);
			resonating = new EntityResonating(world,entity,1);
			resonating.shoot(entity, entity.rotationPitch, entity.rotationYaw, 0.0F, 0.0f, 0);
			resonating.setNoGravity(true);
			resonating.posY -= 1.2;
			resonating.posZ += 0.5;
			entity.world.spawnEntity(resonating);
			wave.setResonating(resonating);
			wave.setResonatingtimer(resonating,attacker);
		}
		
	}
	
	
}
