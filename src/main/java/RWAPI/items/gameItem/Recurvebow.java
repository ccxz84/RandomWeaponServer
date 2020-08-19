package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class Recurvebow extends ItemBase {

	public Recurvebow(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] = ModItems.Dagger;
		down_item[1] = ModItems.Dagger;
		
		phase = 2;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		this.stat[0] = 0;
		this.stat[1] = 0;
		this.stat[2] = 0;
		this.stat[3] = 0;
		this.stat[4] = 0;
		this.stat[5] = 0.25;
		this.stat[6] = 0;
		this.stat[7] = 0;
	}
}
