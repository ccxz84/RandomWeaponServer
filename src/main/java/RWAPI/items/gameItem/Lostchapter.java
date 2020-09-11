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
		this.stat[0] = 0;
		this.stat[1] = 30;
		this.stat[2] = 0;
		this.stat[3] = 300;
		this.stat[4] = 0;
		this.stat[5] = 0;
		this.stat[6] = 0;
		this.stat[7] = 2;
	}
}
