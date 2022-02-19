package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Bootsofswiftness extends ItemBase implements ItemBase.shoes {

	public Bootsofswiftness(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[1];
		down_item[0] =ModItems.Bootsofspeed;
		
		phase = 2;
		this.name = "신속의 장화";
		this.gold = 900;
		refund_gold = 630;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	0,	0,	0,	0,	25,	0,	0,	0,	0,	0,	0
		};
		this.stat = stat;
	}
}
