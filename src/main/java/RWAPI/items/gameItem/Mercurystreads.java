package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class Mercurystreads extends ItemBase {

	public Mercurystreads(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] =ModItems.Bootsofspeed;
		down_item[1] =ModItems.Nullmagicmantle;
		
		phase = 2;
		this.name = "헤르메스의 발걸음";
		this.gold = 1100;
		refund_gold = 770;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	0,	0,	0,	25,	20,	0,	0,	0,	0,	0
		};
		this.stat = stat;
	}

}
