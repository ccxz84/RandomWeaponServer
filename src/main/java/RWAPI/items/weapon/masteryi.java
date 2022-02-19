package RWAPI.items.weapon;

import RWAPI.init.ModWeapons;
import RWAPI.util.ClassList;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class masteryi extends WeaponBase{
    public masteryi(String name) {
        super(Item.ToolMaterial.DIAMOND,name);
        setCreativeTab(CreativeTabs.MATERIALS);
        this.ClassCode = ClassList.MasterYi;
        ModWeapons.weapon.add(this);
        basename = "마스터 이";
        // TODO Auto-generated constructor stub
    }
}
