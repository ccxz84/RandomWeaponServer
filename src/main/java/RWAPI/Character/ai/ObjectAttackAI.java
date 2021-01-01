package RWAPI.Character.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.world.World;

public class ObjectAttackAI extends EntityAIBase {

    World world;
    protected EntityCreature attacker;
    double speedTowardsTarget;
    protected int attackTick;
    Path path;
    private double targetX;
    private double targetY;
    private double targetZ;

    public ObjectAttackAI(EntityCreature creature, double speedIn)
    {
        this.attacker = creature;
        this.world = creature.world;
        this.speedTowardsTarget = speedIn;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        return false;
    }
}
