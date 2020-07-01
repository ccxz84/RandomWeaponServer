package RWAPI.Character.Leesin.entity;

import RWAPI.Character.SkillEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityResonating extends SkillEntity{

	public EntityResonating(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}
	
	public EntityResonating(World worldIn, EntityLivingBase playerin, float skilldamage) {
		super(worldIn,playerin, skilldamage);
	}
	
	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub
		setSize(99999999f,99999999f);
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
		//setDead();
		if(this.thrower != null) {
			if(this.thrower.isDead) {
				setDead();
				return;
			}
		}else {
			setDead();
			return;
		}
		
		posX = this.thrower.posX;
		posY = this.thrower.posY + 0.2;
		posZ = this.thrower.posZ + 0.5;
		if(this.ticksExisted > 1000) {
			setDead();
		}
		super.onUpdate();
		
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender() {
		return 0xf000f0;
	}

}
