package RWAPI.Character.ForceMaster.entity;

import RWAPI.Character.ForceMaster.skills.heatwave;
import RWAPI.Character.PlayerData;
import RWAPI.Character.SkillEntity;
import RWAPI.init.ModSkills;
import RWAPI.items.skillItem.SkillBase;
import RWAPI.main;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntitytempHeatwave extends SkillEntity {

    PlayerData data;
    heatwave wave;

    public EntitytempHeatwave(World world){
        super(world);
    }

    public EntitytempHeatwave(World world, PlayerData data, heatwave wave) {
        super(world);
        this.data = data;
        this.wave = wave;
    }

    @Override
    public void onUpdate() {
        // TODO Auto-generated method stub
        if(this.ticksExisted > 100) {
            this.setDead();
        }
        super.onUpdate();
    }
}
