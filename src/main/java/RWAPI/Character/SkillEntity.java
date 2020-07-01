package RWAPI.Character;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class SkillEntity extends EntityThrowable {
	
	protected float skilldamage;
	
	public SkillEntity(World world) {
		super(world);
	}
	
	public SkillEntity(World worldIn, EntityLivingBase playerin, float skilldamage) {
		super(worldIn,playerin);
		this.skilldamage = skilldamage;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		// TODO Auto-generated method stub
		
	}

}
