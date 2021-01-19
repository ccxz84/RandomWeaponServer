package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Dagger extends ItemBase {

	public Dagger(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		
		down_item = new ItemBase[0];
		
		phase = 3;
		this.name = "단검";
		this.gold = 300;
		refund_gold = 210;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	0,	0,	0,	0,	0,	0.1,	0,	0,	0,	0,	0

		};
		this.stat = stat;
	}
}
