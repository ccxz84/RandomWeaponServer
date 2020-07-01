package RWAPI.items.weapon;

import RWAPI.init.ModWeapons;
import RWAPI.util.ClassList;
import RWAPI.util.Reference;
import net.minecraft.creativetab.CreativeTabs;

public class leesin extends WeaponBase{

	public leesin(String name) {
		super(ToolMaterial.DIAMOND,name);
		setCreativeTab(CreativeTabs.MATERIALS);
		this.ClassCode = ClassList.Leesin;
		ModWeapons.weapon.add(this);
		// TODO Auto-generated constructor stub
	}

}
