package RWAPI.Character.Kassadin.entity;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.SkillEntity;
import RWAPI.Character.monster.entity.IMob;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityForcepulse extends SkillEntity {
    public EntityForcepulse(World world) {
        super(world);
    }

    public EntityForcepulse(World worldIn, EntityLivingBase playerin, double skilldamage) {
        super(worldIn, playerin, skilldamage);
    }

    @Override
    public void onUpdate() {
        if(ticksExisted >1) {
            setDead();
        }
        super.onUpdate();
    }
}
