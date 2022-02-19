package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class Chainvest extends ItemBase {

	public Chainvest(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[1];
		down_item[0] =ModItems.Clotharmor;
		
		phase = 2;
		this.name = "쇠사슬 조끼";
		this.gold = 800;
		refund_gold = 560;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	0,	0,	40,	0,	0,	0,	0,	0,	0,	0,	0
		};
		this.stat = stat;
	}

}
