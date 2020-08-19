package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class Doransblade extends ItemBase {

	public Doransblade(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		
		down_item = new ItemBase[0];
		
		phase = 3;
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void initstat() {
		this.stat[0] = 8;
		this.stat[1] = 0;
		this.stat[2] = 150;
		this.stat[3] = 0;
		this.stat[4] = 0;
		this.stat[5] = 0;
		this.stat[6] = 0.2;
		this.stat[7] = 0;
	}
}
