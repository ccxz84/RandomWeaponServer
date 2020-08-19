package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class Hunterstalisman extends ItemBase {

	public Hunterstalisman(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		
		down_item = new ItemBase[0];
		
		phase = 3;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		this.stat[0] = 0;
		this.stat[1] = 0;
		this.stat[2] = 30;
		this.stat[3] = 20;
		this.stat[4] = 0;
		this.stat[5] = 0;
		this.stat[6] = 0.05;
		this.stat[7] = 0.05;
	}
}
