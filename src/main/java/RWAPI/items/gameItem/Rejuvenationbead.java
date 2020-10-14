package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Rejuvenationbead extends ItemBase {

	public Rejuvenationbead(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[0];
		
		phase = 3;
		this.name = "원기 회복의 구슬";
		this.gold = 150;
		refund_gold = 105;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	0,	0,	0,	0,	0,	0,	1.2,	0,	0,	0
		};
		this.stat = stat;
	}

}
