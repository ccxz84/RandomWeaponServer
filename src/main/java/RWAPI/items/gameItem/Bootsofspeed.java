package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Bootsofspeed extends ItemBase implements ItemBase.shoes {

	public Bootsofspeed(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		
		down_item = new ItemBase[0];
		
		phase = 3;
		this.name = "속도의 장화";
		this.gold = 300;
		refund_gold = 210;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		this.stat[4] = 5;
	}


}

