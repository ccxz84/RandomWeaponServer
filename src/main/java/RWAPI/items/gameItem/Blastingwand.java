package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Blastingwand extends ItemBase {

	public Blastingwand(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		this.name = "방출의 마법봉";
		down_item = new ItemBase[0];
		
		phase = 3;
		this.gold = 850;
		refund_gold = 595;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	45,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0
		};
		this.stat = stat;
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {

		super.onCreated(stack, worldIn, playerIn);
	}
}
