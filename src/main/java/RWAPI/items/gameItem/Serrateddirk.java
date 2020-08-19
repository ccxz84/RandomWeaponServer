package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Serrateddirk extends ItemBase {

	public Serrateddirk(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] = ModItems.LongSword;
		down_item[1] = ModItems.LongSword;
		
		phase = 2;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		this.stat[0] = 25;
		this.stat[1] = 0;
		this.stat[2] = 100;
		this.stat[3] = 0;
		this.stat[4] = 0;
		this.stat[5] = 0;
		this.stat[6] = 0;
		this.stat[7] = 0;
	}

	protected class handler extends ItemBase.handler{

		@Override
		public void itemHandler(TickEvent.ServerTickEvent event) {

		}
	}
}
