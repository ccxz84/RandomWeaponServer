package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Needlesslylargerod extends ItemBase {

	public Needlesslylargerod(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		
		down_item = new ItemBase[0];
		
		phase = 3;
		this.name = "쓸데없이 큰 지팡이";
		this.gold = 1250;
		refund_gold = 875;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	60,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0
		};
		this.stat = stat;
	}
}
