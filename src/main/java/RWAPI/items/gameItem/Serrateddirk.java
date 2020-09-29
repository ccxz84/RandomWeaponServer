package RWAPI.items.gameItem;

import RWAPI.Character.PlayerData;
import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
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
		this.name = "톱날 단검";
		this.gold = 1100;
		refund_gold = 770;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		this.stat[0] = 25;
		this.stat[1] = 0;
		this.stat[2] = 150;
		this.stat[3] = 0;
		this.stat[4] = 0;
		this.stat[5] = 0;
		this.stat[6] = 0;
		this.stat[7] = 0;
	}

	protected class handler extends ItemBase.handler{
		public handler(PlayerData data, ItemStack stack) {
			super(data,stack);
		}
	}
}
