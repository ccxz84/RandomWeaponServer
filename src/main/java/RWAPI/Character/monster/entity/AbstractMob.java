package RWAPI.Character.monster.entity;

import RWAPI.Character.ClientData;
import RWAPI.Character.EntityData;
import RWAPI.main;
import RWAPI.util.GameStatus;
import RWAPI.util.NetworkUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.jline.utils.Log;

import java.io.*;

public class AbstractMob extends EntityMob implements IMob{

	private double[] stat;
	protected EntityData data;
	private double[] basestat;

	public AbstractMob(World worldIn) {
		super(worldIn);
		// TODO Auto-generated constructor stub
	}

	public AbstractMob(World world, EntityData data, double[] stat,double[] basestat) {
		this(world);
		this.data = data;
		if(stat.length >= 4)
			this.stat = stat;
		else
			Log.error("object monstre stat length error");

		if(basestat.length >= 4)
			this.basestat = basestat;
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

    public double[] getBasestat() {
		return this.basestat;
    }

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NetworkUtil.saveData(data.getData(),"data",compound);
		return super.writeToNBT(compound);

	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		Object ob = NetworkUtil.restoreData(compound,"data",ClientData.class);
		if(ob != null){
			this.getData().setData((ClientData)ob);
		}
		super.readFromNBT(compound);
	}
}


