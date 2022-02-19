package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class Pickaxe extends ItemBase {

	public Pickaxe(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		this.name = "곡괭이";
		down_item = new ItemBase[0];
		
		phase = 3;
		this.gold = 875;
		refund_gold = 612;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				25,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,0
		};
		this.stat = stat;
	}
}
