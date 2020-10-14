package RWAPI.items.gameItem;

import RWAPI.Character.PlayerData;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Coldsteel_passive;
import RWAPI.items.gameItem.inherence.Lifeline_passive;
import RWAPI.items.gameItem.inherence.Thorn_passive;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Wardensmail extends ItemBase {

	private final int time = 1;
	private final double  minusas= 0.15;

	public Wardensmail(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] =ModItems.Clotharmor;
		down_item[1] =ModItems.Clotharmor;
		
		phase = 2;
		this.name = "파수꾼의 갑옷";
		this.gold = 1000;
		refund_gold = 700;
		// TODO Auto-generated constructor stub
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("inherence","기본 공격에 피격 된 경우 "+String.format("%d",(int)time)+"초 동안 "+String.format("%d",(int)0.15*100)+"%의 공격속도가 감소합니다.");
		return super.initCapabilities(stack,nbt);
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	0,	0,	40,	0,	0,	0,	0,	0,	0,	0
		};
		this.stat = stat;
	}

	@Override
	public List<Class<? extends inherence_handler>> get_inherence_handler() {
		List<Class<? extends inherence_handler>> list = new ArrayList<>();
		list.add(Coldsteel_passive.class);
		return list;
	}

	@Override
	public ItemBase.inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends ItemBase.inherence_handler> _class) {
		if(_class.equals(Coldsteel_passive.class)){
			return new Coldsteel_passive(data,stack,time,minusas);
		}

		return null;

	}

}
