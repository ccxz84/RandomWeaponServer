package RWAPI.Character.monster.entity;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.monster.entity.AbstractMob;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.jline.utils.Log;

public abstract class AbstractObject extends AbstractMob {

    private double[] stat;

    public AbstractObject(World world, EntityData data, double[] stat) {
        super(world, data,stat);

    }

    abstract public int getGametime();

    abstract public void setBuff(PlayerData data);

    public void attackmob(Entity entityIn){

    }
}