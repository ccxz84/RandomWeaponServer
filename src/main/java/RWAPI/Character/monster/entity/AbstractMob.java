package RWAPI.Character.monster.entity;

import RWAPI.Character.ClientData;
import RWAPI.Character.EntityData;
import RWAPI.main;
import RWAPI.util.GameStatus;
import RWAPI.util.NetworkUtil;
import RWAPI.util.Reference;
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
		if(main.game != null) {
			compound.setInteger("time",((Reference.GAMEITME - main.game.gettimer())/60));
		}
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
			if(main.game != null) {
				int prevtime = compound.getInteger("time");
				int currenttime = (Reference.GAMEITME - main.game.gettimer())/60;
				if(currenttime - prevtime > 0){
					int sub = currenttime - prevtime;
					this.getData().setMaxHealth(this.getData().getMaxHealth() + getStat()[0] * sub);
					this.getData().setCurrentHealth(this.getData().getCurrentHealth() + getStat()[0] * sub);
					this.getData().setAd(this.getData().getAd() + getStat()[1]);
					this.getData().setArmor(this.getData().getArmor() + getStat()[2]);
					this.getData().setMagicresistance(this.getData().getMagicresistance() + getStat()[3]);
				}
			}
		}
		super.readFromNBT(compound);
	}
}


