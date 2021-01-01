package RWAPI.Character.Leesin.entity;

import java.util.List;

import RWAPI.main;
import RWAPI.Character.EntityData;
import RWAPI.Character.SkillEntity;
import RWAPI.Character.monster.entity.IMob;
import RWAPI.util.DamageSource.DamageSource.EnemyStatHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityTempest extends SkillEntity{

	public EntityTempest(World world) {
		super(world);
		//setSize(0.7f,0.4f);
		// TODO Auto-generated constructor stub
	}
	
	public EntityTempest(World worldIn, EntityLivingBase playerin, double skilldamage) {
		super(worldIn,playerin, skilldamage);
		this.posX = playerin.posX;
		this.posY = playerin.posY;
		this.posZ = playerin.posZ;
	}
	
	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		super.shoot(x, y, z, velocity, inaccuracy);
		this.getEntityBoundingBox().grow(500, 500,500);
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
		if(this.thrower != null) {
			this.posX = this.thrower.posX;
			this.posY = this.thrower.posY;
			this.posZ = this.thrower.posZ;
		}
		else {
			setDead();
		}
		// TODO Auto-generated method stub
		if(this.ticksExisted > 10) {
			this.setDead();
		}
		super.onUpdate();
	}
	
	@Override
	public void setDead() {
		if(main.game == null)
			return;
		List<Entity> mini =  this.world.getEntitiesWithinAABB(Entity.class, this.getEntityBoundingBox().grow(3,0,3));
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
					RWAPI.util.DamageSource.DamageSource source = RWAPI.util.DamageSource.DamageSource.causeSkillMagic(attacker, target, this.skilldamage);
					RWAPI.util.DamageSource.DamageSource.attackDamage(source,true);
					EnemyStatHandler.EnemyStatSetter(source);
					mi.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)1);
				}
				
			}
		}
		// TODO Auto-generated method stub
		super.setDead();
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		// TODO Auto-generated method stub
		super.onImpact(result);
	}
	
	

}
