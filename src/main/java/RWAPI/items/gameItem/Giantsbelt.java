package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Giantsbelt extends ItemBase {

	public Giantsbelt(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		
		down_item = new ItemBase[1];
		down_item[0] =ModItems.Rubycrystal;
		
		phase = 2;
		this.name = "거인의 허리띠";
		this.gold = 1000;
		refund_gold = 700;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	400,	0,	0,	0,	0,	0,	0,	0,	0,	0
		};
		this.stat = stat;
	}
}
