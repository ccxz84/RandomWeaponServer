package RWAPI.Character;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class SkillEntity extends EntityThrowable {
	
	protected double skilldamage;
	protected double judg = 0.3d;
	
	public SkillEntity(World world) {
		super(world);
	}
	
	public SkillEntity(World worldIn, EntityLivingBase playerin, double skilldamage) {
		super(worldIn,playerin);
		this.skilldamage = skilldamage;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		impactupdate();
	}

	public void impactupdate(){
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
					AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().grow(judg);
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
			if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK && this.world.getBlockState(raytraceresult.getBlockPos()).getBlock() == Blocks.PORTAL)
			{
				this.setPortal(raytraceresult.getBlockPos());
			}
			else if (!net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult))
			{
				this.SkillImpact(raytraceresult);
			}
		}
	}

	protected void SkillImpact(RayTraceResult result){

	}

}
