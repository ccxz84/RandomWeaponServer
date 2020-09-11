package RWAPI.items.weapon;

import RWAPI.init.ModWeapons;
import RWAPI.util.ClassList;
import RWAPI.util.Reference;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class leesin extends WeaponBase{

	public leesin(String name) {
		super(ToolMaterial.DIAMOND,name);
		setCreativeTab(CreativeTabs.MATERIALS);
		this.ClassCode = ClassList.Leesin;
		ModWeapons.weapon.add(this);
		basename = "리 신";
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

	}
}
