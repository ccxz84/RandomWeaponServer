package RWAPI.Character.monster.entity;

import RWAPI.Character.EntityData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.jline.utils.Log;

public class AbstractMob extends EntityMob implements IMob{

	private double[] stat;
	protected EntityData data;

	public AbstractMob(World worldIn) {
		super(worldIn);
		// TODO Auto-generated constructor stub
	}

	public AbstractMob(World world, EntityData data, double[] stat) {
		this(world);
		this.data = data;
		if(stat.length >= 4)
			this.stat = stat;
		else
			Log.error("object monstre stat length error");
	}

	public EntityData getData() {
		return data;
	}

	public void setData(EntityData data) {
		this.data = data;
	}

	public double[] getStat(){
		return this.stat;
	}
}


