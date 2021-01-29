package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class Sorcerersshoes extends ItemBase implements ItemBase.shoes {

	public Sorcerersshoes(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[1];
		down_item[0] =ModItems.Bootsofspeed;
		
		phase = 2;
		this.name = "마법사의 신발";
		this.gold = 1100;
		refund_gold = 770;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	0,	0,	0,	0,	20,	0,	0,	0,	0,	18,	0
		};
		this.stat = stat;
	}
}
