package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class Youmuusghostblade extends ItemBase {

	public Youmuusghostblade (String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] =ModItems.Serrateddirk;
		down_item[1] =ModItems.Caulfieldswarhammer;
		
		phase = 3;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		this.stat[0] = 60;
		this.stat[1] = 0;
		this.stat[2] = 200;
		this.stat[3] = 100;
		this.stat[4] = 0;
		this.stat[5] = 0;
		this.stat[6] = 0;
		this.stat[7] = 0;
	}
}
