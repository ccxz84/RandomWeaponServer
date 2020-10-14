package RWAPI.items.gameItem;

import RWAPI.Character.PlayerData;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Lifeline_passive;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Hexdrinker extends ItemBase {

	public Hexdrinker(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] =ModItems.LongSword;
		down_item[1] =ModItems.Nullmagicmantle;
		
		phase = 2;
		this.name = "주문포식자";
		this.gold = 1300;
		refund_gold = 910;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				20,	0,	0,	0,	0,	35,	0,	0,	0,	0,	0,	0
		};
		this.stat = stat;
	}

	@Override
	public List<Class<? extends inherence_handler>> get_inherence_handler() {
		List<Class<? extends inherence_handler>> list = new ArrayList<>();
		list.add(Lifeline_passive.class);
		return list;
	}

	@Override
	public ItemBase.inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends ItemBase.inherence_handler> _class) {
		return new Lifeline_passive(data,stack,30,5,5,20);
	}

}
