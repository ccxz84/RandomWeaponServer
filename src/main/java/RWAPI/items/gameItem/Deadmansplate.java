package RWAPI.items.gameItem;

import RWAPI.Character.PlayerData;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Bladeoftheruinedking_passive;
import RWAPI.items.gameItem.inherence.Dreadnought_passive;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Deadmansplate extends ItemBase {

	double plusmove = 2;

	public Deadmansplate(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] =ModItems.Chainvest;
		down_item[1] =ModItems.Giantsbelt;
		
		phase = 1;
		this.name = "망자의 갑옷";
		this.gold = 2900;
		refund_gold = 2030;
		// TODO Auto-generated constructor stub
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("inherence","이동 시 중첩이 쌓이며, 중첩 1회당 이동속도가 " + String.format("%d",(int)plusmove)+" 증가합니다.");
		return super.initCapabilities(stack,nbt);
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	450,	0,	60,	0,	0,	0,	0,	0,	0,	0
		};
		this.stat = stat;
	}

	@Override
	public List<Class<? extends inherence_handler>> get_inherence_handler() {
		List<Class<? extends inherence_handler>> list = new ArrayList<>();
		list.add(Dreadnought_passive.class);
		return list;
	}

	@Override
	public ItemBase.inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends ItemBase.inherence_handler> _class) {
		if(_class.equals(Dreadnought_passive.class)){
			return new Dreadnought_passive(data,stack,plusmove);
		}

		return null;

	}
}
