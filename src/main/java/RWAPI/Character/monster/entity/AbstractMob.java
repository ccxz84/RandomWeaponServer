package RWAPI.Character.monster.entity;

import RWAPI.Character.EntityData;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class AbstractMob extends EntityMob{
	
	protected EntityData data;

	public AbstractMob(World worldIn) {
		super(worldIn);
		// TODO Auto-generated constructor stub
	}
	
	public AbstractMob(World world, EntityData data) {
		this(world);
		this.data = data;
	}

	public EntityData getData() {
		return data;
	}

	public void setData(EntityData data) {
		this.data = data;
	}
	
	

}
