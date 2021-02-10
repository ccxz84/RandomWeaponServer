package RWAPI.Character.ForceMaster.skills;

import RWAPI.Character.ForceMaster.ForceMaster;
import RWAPI.Character.PlayerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class froststep extends formaster{

    protected final double[] skilldamage={
            28,
            30,
            32,
            34,
            36,
            38,
            40,
            42,
            45,
            48,
            51,
            54,
            57,
            60,
            63,
            66,
            69,
            72
    };
    protected final double[] skillAdcoe={
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2,
            0.2
    };
    protected final double[] skillApcoe={
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1.2,
            1.2,
            1.2,
            1.4,
            1.4,
            1.4,
            1.4,
            1.4,
            1.4,
            1.4
    };
    protected final double[] skillcost={
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2,
            2
    };

    protected final double[] cooldown = {
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1
    };


    public froststep(ForceMaster forceMaster, Item skill) {
        super(skill);
        this.forceMaster = forceMaster;
    }

    @Override
    public void switchSkill(PlayerData data, int idx, boolean flag) {

    }

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

    @Override
    public double[] getskilldamage() {
        return new double[0];
    }

    @Override
    public double[] getskillAdcoe() {
        return new double[0];
    }

    @Override
    public double[] getskillApcoe() {
        return new double[0];
    }

    @Override
    public double[] getskillcost() {
        return new double[0];
    }

    @Override
    public double[] getcooldown() {
        return new double[0];
    }
}
