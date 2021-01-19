package RWAPI.items.gameItem;

import RWAPI.Character.PlayerData;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Deathfiregrasp_passive;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Deathfiregrasp extends ItemBase {

	private final double reducePercent = 12;

	public Deathfiregrasp(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[3];
		down_item[0] =ModItems.Amplifyingtome;
		down_item[1] =ModItems.Lostchapter;
		down_item[2] =ModItems.Amplifyingtome;
		
		phase = 1;
		this.name = "죽음불꽃 손아귀";
		this.gold = 2700;
		refund_gold = 1890;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	120,	0,	300,	0,	0,	0,	0,	0,	3,	0,	10,	0

		};
		this.stat = stat;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("inherence","스킬 공격 시, 대상의 현재 체력의 "+String.format("%.0f",this.reducePercent)+"%에 해당하는 추가 피해를 입힙니다.");
		return super.initCapabilities(stack,nbt);
	}

	@Override
	public inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends ItemBase.inherence_handler> _class, int idx) {
		if(_class.equals(Deathfiregrasp_passive.class)){
			return new Deathfiregrasp_passive(data,stack, reducePercent);
		}

		return null;

	}

	@Override
	public List<Class<? extends inherence_handler>> get_inherence_handler() {
		List<Class<? extends inherence_handler>> list = new ArrayList<>();
		list.add(Deathfiregrasp_passive.class);
		return list;
	}

}
