package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Lostchapter extends ItemBase {

	public Lostchapter(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] = ModItems.Amplifyingtome;
		down_item[1] = ModItems.Sapphirecrystal;

		phase = 2;
		this.name = "사라진 양피지";
		this.gold = 1100;
		refund_gold = 770;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	35,	0,	300,	0,	0,	0,	0,	0,	1,	0,	0,0
		};
		this.stat = stat;
	}
}
