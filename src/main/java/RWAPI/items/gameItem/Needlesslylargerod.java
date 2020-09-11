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
		this.stat[0] = 0;
		this.stat[1] = 60;
		this.stat[2] = 0;
		this.stat[3] = 0;
		this.stat[4] = 0;
		this.stat[5] = 0;
		this.stat[6] = 0;
		this.stat[7] = 0;
	}
}
