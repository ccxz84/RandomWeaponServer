package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class Warmogsarmor extends ItemBase {

	public Warmogsarmor(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[3];
		down_item[0] =ModItems.Rubycrystal;
		down_item[1] =ModItems.Giantsbelt;
		down_item[2] =ModItems.Crystallinebracer;
		
		phase = 3;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		this.stat[0] = 0;
		this.stat[1] = 0;
		this.stat[2] = 1200;
		this.stat[3] = 0;
		this.stat[4] = 0;
		this.stat[5] = 0;
		this.stat[6] = 4;
		this.stat[7] = 0;
	}
}
