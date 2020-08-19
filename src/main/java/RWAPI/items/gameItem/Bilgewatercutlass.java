package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Bilgewatercutlass extends ItemBase {

	public Bilgewatercutlass(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] = ModItems.VampiricScepter;
		down_item[1] = ModItems.LongSword;
		
		phase = 2;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat(){
		this.stat[0] = 25;
	}

	protected class handler extends ItemBase.handler{

		@Override
		public void itemHandler(TickEvent.ServerTickEvent event) {

		}
	}
}
