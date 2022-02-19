package RWAPI.Character.monster.entity;

import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public interface IEntityMultiPart {
    World getWorld();

    boolean attackEntityFromPart(DragonPart dragonPart, DamageSource source, float damage);
}
