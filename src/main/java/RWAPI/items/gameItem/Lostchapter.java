package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Lostchapter extends ItemBase {

	public Lostchapter(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[3];
		down_item[0] = ModItems.Amplifyingtome;
		down_item[1] = ModItems.Sapphirecrystal;
		down_item[2] = ModItems.Amplifyingtome;
		
		phase = 2;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		this.stat[0] = 0;
		this.stat[1] = 50;
		this.stat[2] = 0;
		this.stat[3] = 300;
		this.stat[4] = 0;
		this.stat[5] = 0;
		this.stat[6] = 0;
		this.stat[7] = 2;
	}
}