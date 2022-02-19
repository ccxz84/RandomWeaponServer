package RWAPI.Character.monster.entity;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.monster.entity.AbstractMob;
import RWAPI.main;
import RWAPI.util.GameStatus;
import RWAPI.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import org.jline.utils.Log;

public abstract class AbstractObject extends AbstractMob {

    private double[] stat;
    private boolean flag;
    protected boolean buff= true;

    public AbstractObject(World world, EntityData data, double[] stat,double[] basestat) {
        super(world, data,stat,basestat);
        flag = true;
    }

    abstract public int getGametime();

    abstract public void setBuff(PlayerData data);

    public void attackmob(Entity entityIn){

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        flag = true;
        return super.writeToNBT(compound);
    }

    public boolean isFlag(){
        return this.flag;
    }

    @Override
    protected boolean canDespawn() {
        if (main.game.start != GameStatus.START || (Reference.GAMEITME - main.game.gettimer()) - 30 < 0){
            return true;
        }
        return false;
    }
}