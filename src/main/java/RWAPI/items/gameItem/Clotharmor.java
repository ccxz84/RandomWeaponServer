package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Clotharmor extends ItemBase {

	public Clotharmor(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		this.name = "천 갑옷";
		down_item = new ItemBase[0];
		
		phase = 3;
		this.gold = 300;
		refund_gold = 210;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	0,	0,	10,	0,	0,	0,	0,	0,	0,	0,	0
		};
		this.stat = stat;
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {

		super.onCreated(stack, worldIn, playerIn);
	}
}
