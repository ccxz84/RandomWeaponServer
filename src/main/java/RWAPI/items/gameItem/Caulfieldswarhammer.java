package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Caulfieldswarhammer extends ItemBase {

	public Caulfieldswarhammer(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] =ModItems.LongSword;
		down_item[1] =ModItems.LongSword;
		
		phase = 2;
		this.name = "콜필드의 망치";
		this.gold = 1100;
		refund_gold = 770;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				25,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	10
		};
		this.stat = stat;
	}
}
