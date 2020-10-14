package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Bansheesveil extends ItemBase {

	public Bansheesveil(String name)  {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		this.name = "밴시의 장막";
		down_item = new ItemBase[3];

		//아이템 추가가
		down_item[0] =ModItems.Fiendishcodex;
		down_item[1] =ModItems.Nullmagicmantle;
		down_item[2] =ModItems.Blastingwand;

		phase = 1;
		this.gold = 3000;
		refund_gold = 2100;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	70,	0,	0,	0,	65,	0,	0,	0,	0,	0,	0
		};
		this.stat = stat;
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {

		super.onCreated(stack, worldIn, playerIn);
	}
}
