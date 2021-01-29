package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class Fiendishcodex extends ItemBase {

	public Fiendishcodex(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[1];
		down_item[0] =ModItems.Amplifyingtome;
		
		phase = 2;
		this.name = "악마의 마법서";
		this.gold = 900;
		refund_gold = 630;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	50,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	10

		};
		this.stat = stat;
	}

}
