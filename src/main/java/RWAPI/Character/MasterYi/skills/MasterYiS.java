package RWAPI.Character.MasterYi.skills;

import RWAPI.Character.Skill;
import net.minecraft.entity.player.EntityPlayer;

public abstract class MasterYiS implements Skill {
    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {

    }

    @Override
    public void Skillset(EntityPlayer player) {

    }

    @Override
    public void skillEnd(EntityPlayer player) {

    }

    abstract void reduceCool();
}
