package RWAPI.Character.Leesin.entity;

import RWAPI.Character.Leesin.skills.sonicwave;
import RWAPI.main;
import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerClass;
import RWAPI.Character.PlayerData;
import RWAPI.Character.SkillEntity;
import RWAPI.Character.Leesin.Leesin;
import RWAPI.Character.monster.entity.AbstractMob;
import RWAPI.Character.monster.entity.EntityMinion;
import RWAPI.util.DamageSource.EnemyStatHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityUmpa extends SkillEntity {
	
	public EntityUmpa(World worldIn) {
		super(worldIn);
	}

	public EntityUmpa(World worldIn, EntityPlayer playerin, float skilldamage) {
		super(worldIn,playerin, skilldamage);
		//System.out.println("pre : " +this.getEntityBoundingBox().maxX);
		//this.setEntityBoundingBox(this.getEntityBoundingBox().expand(100,100,100));
		//System.out.println("current : " +this.getEntityBoundingBox().maxX);
		System.out.println("max X : " +this.getEntityBoundingBox().maxX+" min X : " +this.getEntityBoundingBox().minX
				+" max Y : " +this.getEntityBoundingBox().maxY
				+" min Y : " +this.getEntityBoundingBox().minY
				+" max Z : " +this.getEntityBoundingBox().maxZ
				+" min Z : " +this.getEntityBoundingBox().minZ);
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
		this.setEntityBoundingBox(this.getEntityBoundingBox().grow(10d));
		if(ticksExisted >30) {
			setDead();
		}
		super.onUpdate();
		//System.out.println("motion x : " + motionX + "motion Y : " + motionY +"motion z : " + motionZ);
		test();
		/*System.out.println("max X : " +this.getEntityBoundingBox().maxX+" min X : " +this.getEntityBoundingBox().minX
				+" max Y : " +this.getEntityBoundingBox().maxY
				+" min Y : " +this.getEntityBoundingBox().minY
				+" max Z : " +this.getEntityBoundingBox().maxZ
				+" min Z : " +this.getEntityBoundingBox().minZ);*/
		
	}

	public void test(){

		Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
		Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d, vec3d1);
		vec3d = new Vec3d(this.posX, this.posY, this.posZ);
		vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

		if (raytraceresult != null)
		{
			vec3d1 = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
		}

		Entity entity = null;
		List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0D));
		/*System.out.println("max X : " +box.maxX+" min X : " +box.minX
				+" max Y : " +box.maxY
				+" min Y : " +box.minY
				+" max Z : " +box.maxZ
				+" min Z : " +box.minZ);*/
		double d0 = 0.0D;
		boolean flag = false;

		for (int i = 0; i < list.size(); ++i)
		{
			Entity entity1 = list.get(i);

			if (entity1.canBeCollidedWith())
			{

				if (entity1 == this.ignoreEntity)
				{
					flag = true;
				}
				else if (this.thrower != null && this.ticksExisted < 2 && this.ignoreEntity == null)
				{
					this.ignoreEntity = entity1;
					flag = true;
				}
				else
				{
					flag = false;
					AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(1.0D);
					RayTraceResult raytraceresult1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);

					if (raytraceresult1 != null)
					{
						double d1 = vec3d.squareDistanceTo(raytraceresult1.hitVec);

						if (d1 < d0 || d0 == 0.0D)
						{
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}
		}

		if (entity != null)
		{
			raytraceresult = new RayTraceResult(entity);
		}

		if (raytraceresult != null)
		{
			System.out.println(raytraceresult.entityHit);
			if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK && this.world.getBlockState(raytraceresult.getBlockPos()).getBlock() == Blocks.PORTAL)
			{
				this.setPortal(raytraceresult.getBlockPos());
			}
			else if (!net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult))
			{
				this.onImpact1(raytraceresult);
			}
		}
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

	private void onImpact1(RayTraceResult result){
		System.out.println("impact : " +this.getEntityBoundingBox().maxX);
		if(result.entityHit != null && result.entityHit instanceof EntityLivingBase) {
			if(main.game == null )
				setDead();
			if(!(result.entityHit.getUniqueID().equals(this.thrower.getUniqueID()))) {
				PlayerData attacker = main.game.getPlayerData(this.thrower.getUniqueID());
				EntityData target;
				if(result.entityHit instanceof AbstractMob) {
					AbstractMob mob = (AbstractMob) result.entityHit;
					target = mob.getData();
					RWAPI.util.DamageSource source =RWAPI.util.DamageSource.causeSkill(attacker, target, this.skilldamage);
					RWAPI.util.DamageSource.attackDamage(source,true);
					EnemyStatHandler.EnemyStatSetter(source);
				}
				else if(result.entityHit instanceof EntityPlayer){
					target = main.game.getPlayerData(result.entityHit.getUniqueID());
					RWAPI.util.DamageSource source = RWAPI.util.DamageSource.causeSkill(attacker, target, this.skilldamage);
					RWAPI.util.DamageSource.attackDamage(source,true);
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
