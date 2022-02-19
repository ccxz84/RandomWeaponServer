package RWAPI.items.gameItem;

import RWAPI.Character.PlayerData;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Bansheesveil_passive;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Bansheesveil extends ItemBase {

	private int cooltime = 40;

	public Bansheesveil(String name)  {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		this.name = "밴시의 장막";
		down_item = new ItemBase[3];

		//아이템 추가가
		down_item[0] =ModItems.Fiendishcodex;
		down_item[1] =ModItems.Nullmagicmantle;
		down_item[2] =ModItems.Blastingwand;

		phase = 1;
		this.gold = 3000;
		refund_gold = 2100;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	70,	0,	0,	0,	65,	0,	0,	0,	0,	0,	0,	10
		};
		this.stat = stat;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("inherence","적의 스킬 공격을 1회 무시합니다 쿨타임 : " + cooltime + "초.");
		return super.initCapabilities(stack,nbt);
	}

	@Override
	public List<Class<? extends inherence_handler>> get_inherence_handler() {
		List<Class<? extends inherence_handler>> list = new ArrayList<>();
		list.add(Bansheesveil_passive.class);
		return list;
	}

	@Override
	public ItemBase.inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends ItemBase.inherence_handler> _class, int idx) {
		if(_class.equals(Bansheesveil_passive.class)){
			return new Bansheesveil_passive(data,stack,cooltime,idx);
		}

		return null;

	}
}
