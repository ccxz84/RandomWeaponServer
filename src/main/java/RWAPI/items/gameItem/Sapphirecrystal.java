package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Sapphirecrystal extends ItemBase {

	public Sapphirecrystal(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		
		down_item = new ItemBase[0];
		
		phase = 3;
		this.name = "사파이어 수정";
		this.gold = 350;
		refund_gold = 245;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	0,	200,	0,	0,	0,	0,	0,	0,	0,	0
		};
		this.stat = stat;
	}
}
