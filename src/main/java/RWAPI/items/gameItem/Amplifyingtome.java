package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.List;

public class Amplifyingtome extends ItemBase {

	public Amplifyingtome(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		this.name = "증폭의 고서";
		down_item = new ItemBase[0];
		
		phase = 3;
		this.gold = 435;
		refund_gold = 305;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		this.stat[1] = 25;
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {

		super.onCreated(stack, worldIn, playerIn);
	}
}
