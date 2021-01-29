package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Nullmagicmantle extends ItemBase {

	public Nullmagicmantle(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		this.name = "마법무효화의 망토";
		down_item = new ItemBase[0];
		
		phase = 3;
		this.gold = 450;
		refund_gold = 315;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	0,	0,	0,	25,	0,	0,	0,	0,	0,	0,0

		};
		this.stat = stat;
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {

		super.onCreated(stack, worldIn, playerIn);
	}
}
