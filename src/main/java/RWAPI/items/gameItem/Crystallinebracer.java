package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Crystallinebracer extends ItemBase {

	public Crystallinebracer(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] =ModItems.Rubycrystal;
		down_item[1] =ModItems.Rejuvenationbead;
		
		phase = 2;
		this.name = "수정팔 보호구";
		this.gold = 650;
		refund_gold = 455;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		this.stat[2] = 300;
		this.stat[6] = 1.4;
	}

}
