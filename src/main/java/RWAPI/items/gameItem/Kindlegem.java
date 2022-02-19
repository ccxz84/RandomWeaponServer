package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Kindlegem extends ItemBase {

	public Kindlegem(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[1];
		down_item[0] =ModItems.Rubycrystal;
		
		phase = 2;
		this.name = "점화석";
		this.gold = 800;
		refund_gold = 560;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	300,	0,	0,	0,	0,	0,	0,	0,	0,	0,	10

		};
		this.stat = stat;
	}

}
