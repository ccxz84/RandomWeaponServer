package RWAPI.items.weapon;

import RWAPI.init.ModWeapons;
import RWAPI.util.ClassList;
import net.minecraft.creativetab.CreativeTabs;

public class kassadin extends WeaponBase{
    public kassadin(String name) {
        super(ToolMaterial.DIAMOND,name);
        setCreativeTab(CreativeTabs.MATERIALS);
        this.ClassCode = ClassList.Nasus;
        ModWeapons.weapon.add(this);
        basename = "카사딘";
        // TODO Auto-generated constructor stub
    }
}
