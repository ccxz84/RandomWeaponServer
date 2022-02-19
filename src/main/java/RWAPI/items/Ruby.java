package RWAPI.items;

import RWAPI.Character.Leesin.entity.EntityUmpa;
import RWAPI.Character.MasterYi.entity.EntityAlpha;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.ItemBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
public class Ruby extends Item {

	public Ruby (String name)
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		this.maxStackSize = 1;
		setCreativeTab(CreativeTabs.MATERIALS);
	}

}
