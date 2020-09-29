package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Berserkersgreaves extends ItemBase implements ItemBase.shoes {

	public Berserkersgreaves(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] =ModItems.Bootsofspeed;
		down_item[1] =ModItems.Dagger;
		
		phase = 2;

		this.name = "광전사의 군화";
		this.gold = 1100;
		refund_gold = 770;
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void initstat() {
		this.stat[4] = 15;
		this.stat[5] = 0.5;
	}
}
