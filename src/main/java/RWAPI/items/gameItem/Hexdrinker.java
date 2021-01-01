package RWAPI.items.gameItem;

import RWAPI.Character.PlayerData;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Lifeline_passive;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Hexdrinker extends ItemBase {

	private final int cooltime = 30;
	private final int time = 5;
	private final double vamPer = 5, plusad = 20, shield = 100;

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
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("inherence","체력이 30% 이하가 된 경우 "+String.format("%d", time)+"초 동안 모든 공격 데미지의 "+String.format("%d", (int)vamPer)+"%를 체력으로 회복하고 공격력이 "+String.format("%d", (int)plusad)+" 증가한다. 방어막 "+String.format("%d", (int)shield)+"을 부여한다.");
		return super.initCapabilities(stack,nbt);
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
			return new Lifeline_passive(data,stack,cooltime,time,vamPer,plusad,shield);
		}
		return null;
	}

}
