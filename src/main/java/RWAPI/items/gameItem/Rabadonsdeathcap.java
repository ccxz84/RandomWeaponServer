package RWAPI.items.gameItem;

import RWAPI.Character.PlayerData;
import RWAPI.game.event.BaseEvent;
import RWAPI.game.event.ItemChangeEventHandle;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Bladeoftheruinedking_passive;
import RWAPI.items.gameItem.inherence.Rabadonsdeathcap_passive;
import RWAPI.items.gameItem.inherence.Thorn_passive;
import RWAPI.main;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Rabadonsdeathcap extends ItemBase {

	private final double APPer = 40;

	public Rabadonsdeathcap(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] =ModItems.Needlesslylargerod;
		down_item[1] =ModItems.Needlesslylargerod;
		
		phase = 2;
		this.name = "라바돈의 죽음모자";
		this.gold = 3600;
		refund_gold = 2520;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	150,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,0
		};
		this.stat = stat;
	}

	@Override
	public List<Class<? extends inherence_handler>> get_inherence_handler() {
		List<Class<? extends ItemBase.inherence_handler>> list = new ArrayList<>();
		list.add(Rabadonsdeathcap_passive.class);
		return list;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("inherence","주문력이 추가로 "+String.format("%.0f",APPer)+"% 증가합니다.");
		return super.initCapabilities(stack,nbt);
	}

	@Override
	public inherence_handler create_inherence_handler (PlayerData data, ItemStack stack, Class<? extends ItemBase.inherence_handler> _class, int idx) {
		if(_class.equals(Rabadonsdeathcap_passive.class)){
			return new Rabadonsdeathcap_passive(data,stack,APPer);
		}

		return null;

	}
}
