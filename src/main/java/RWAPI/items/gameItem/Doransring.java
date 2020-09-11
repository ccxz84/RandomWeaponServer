package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Doransring extends ItemBase {

	public Doransring(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		
		down_item = new ItemBase[0];
		
		phase = 3;
		this.name = "도란의 반지";
		this.gold = 400;
		refund_gold = 160;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		this.stat[0] = 0;
		this.stat[1] = 15;
		this.stat[2] = 120;
		this.stat[3] = 0;
		this.stat[4] = 0;
		this.stat[5] = 0;
		this.stat[6] = 0;
		this.stat[7] = 0.5;
	}
}
