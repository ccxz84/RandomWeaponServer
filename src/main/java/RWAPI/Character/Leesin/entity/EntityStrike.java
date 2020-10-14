package RWAPI.Character.Leesin.entity;

import RWAPI.main;
import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.SkillEntity;
import RWAPI.Character.monster.entity.AbstractMob;
import RWAPI.util.DamageSource.DamageSource.EnemyStatHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityStrike extends SkillEntity{
	EntityLivingBase target;
	
	public EntityStrike(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	public EntityStrike(World worldIn, EntityLivingBase playerin, float skilldamage, EntityLivingBase target) {
		super(worldIn, playerin, skilldamage);
		this.target = target;
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		this.posX = target.posX;
		this.posY = target.posY;
		this.posZ = target.posZ;
		if(this.thrower != null && this.thrower instanceof EntityPlayerMP) {
			((EntityPlayerMP)this.thrower).connection.setPlayerLocation(target.posX, target.posY, target.posZ, this.thrower.rotationYaw, this.thrower.rotationPitch);
		}
		super.onUpdate();
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		System.out.println(result.entityHit);
		// TODO Auto-generated method stub
		if(result.entityHit != null) {
			if(!(result.entityHit.getUniqueID().equals(this.thrower.getUniqueID()))) {
				PlayerData attacker = main.game.getPlayerData(this.thrower.getUniqueID());
				EntityData target;
				if(result.entityHit instanceof AbstractMob) {
					AbstractMob mob = (AbstractMob) result.entityHit;
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
				result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)1);
				setDead();
			}
		}
		setDead();
		super.onImpact(result);
	}

	
}
