package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class Ionianbootsoflucidity extends ItemBase implements ItemBase.shoes {

	public Ionianbootsoflucidity(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[1];
		down_item[0] =ModItems.Bootsofspeed;
		
		phase = 2;
		this.name = "명석함의 아이오니화 장화";
		this.gold = 900;
		refund_gold = 630;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	0,	0,	0,	0,	20,	0,	0,	0,	0,	0,	20
		};
		this.stat = stat;
	}
}
