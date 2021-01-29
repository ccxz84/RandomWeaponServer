package RWAPI.items.weapon;

import RWAPI.init.ModWeapons;
import RWAPI.util.ClassList;
import net.minecraft.creativetab.CreativeTabs;

public class nasus extends WeaponBase{
    public nasus(String name) {
        super(ToolMaterial.DIAMOND,name);
        setCreativeTab(CreativeTabs.MATERIALS);
        this.ClassCode = ClassList.Nasus;
        ModWeapons.weapon.add(this);
        basename = "나서스";
        // TODO Auto-generated constructor stub
    }
}
