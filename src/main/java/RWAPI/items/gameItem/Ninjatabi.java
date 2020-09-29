package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Ninjatabi extends ItemBase implements ItemBase.shoes {

	public Ninjatabi(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] =ModItems.Bootsofspeed;
		down_item[1] =ModItems.Rubycrystal;
		
		phase = 2;
		this.name = "닌자의 신발";
		this.gold = 1100;
		refund_gold = 770;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		this.stat[0] = 0;
		this.stat[1] = 0;
		this.stat[2] = 450;
		this.stat[3] = 0;
		this.stat[4] = 20;
		this.stat[5] = 0;
		this.stat[6] = 0;
		this.stat[7] = 0;
	}
}
