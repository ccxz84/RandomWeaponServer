package RWAPI.items.gameItem;

import RWAPI.Character.PlayerData;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Lifeline_passive;
import RWAPI.items.gameItem.inherence.Thorn_passive;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Mawofmalmortius extends ItemBase {

	public Mawofmalmortius(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] =ModItems.Hexdrinker;
		down_item[1] =ModItems.Caulfieldswarhammer;
		
		phase = 1;
		this.name = "맬모셔스의 아귀";
		this.gold = 3250;
		refund_gold = 2275;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				50,	0,	100,	0,	0,	50,	0,	0,	0,	0,	0,	0
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
		if(_class.equals(Lifeline_passive.class)){
			return new Lifeline_passive(data,stack,30,8,10,25);
		}

		return null;

	}

}
