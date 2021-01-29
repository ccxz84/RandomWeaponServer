package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class Stinger extends ItemBase {

	private final double plusdmg = 10;

	public Stinger(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] = ModItems.Dagger;
		down_item[1] = ModItems.Dagger;
		
		phase = 2;
		this.name = "쐐기검";
		this.gold = 950;
		refund_gold = 665;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	0,	0,	0,	0,	0,	0.35,	0,	0,	0,	0,10
		};
		this.stat = stat;
	}
}
