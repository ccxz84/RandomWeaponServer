package RWAPI.items.gameItem;

import RWAPI.Character.PlayerData;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Bladeoftheruinedking_passive;
import RWAPI.items.gameItem.inherence.Coldsteel_passive;
import RWAPI.items.gameItem.inherence.Thorn_passive;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Thornmail extends ItemBase {

	private final double armorPer = 10;
	private final double damage = 25;
	private final int time = 1;
	private final double  minusas= 0.15;
	private final int duration = 3;


	public Thornmail(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[3];
		down_item[0] =ModItems.Bramblevest;
		down_item[1] =ModItems.Rubycrystal;
		down_item[2] =ModItems.Wardensmail;
		
		phase = 1;
		this.name = "가시 갑옷";
		this.gold = 2900;
		refund_gold = 2030;
		// TODO Auto-generated constructor stub
	}


	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("inherence","기본 공격에 피격 된 경우 "+String.format("%d",(int)damage)+" + 현재 방어력의 "+String.format("%d",(int)armorPer)+" %에 해당하는 마법 피해를 입힙니다.\n"+
				"기본 공격에 피격 된 경우 "+String.format("%d",(int)time)+"초 동안 "+String.format("%d",(int)0.15*100)+"%의 공격속도가 감소합니다.\n"+
				"기본 공격에 피격 된 경우 공격한 적에게 "+duration+"초 동안 치유 감소 효과가 적용됩니다.");
		return super.initCapabilities(stack,nbt);
	}

	@Override
	public List<Class<? extends inherence_handler>> get_inherence_handler() {
		List<Class<? extends ItemBase.inherence_handler>> list = new ArrayList<>();
		list.add(Thorn_passive.class);
		list.add(Coldsteel_passive.class);
		return list;
	}

	@Override
	public ItemBase.inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends ItemBase.inherence_handler> _class, int idx) {
		if(_class.equals(Thorn_passive.class)) {
			return new Thorn_passive(data, stack, damage, armorPer,duration);
		}
		else if(_class.equals(Coldsteel_passive.class)){
			return new Coldsteel_passive(data,stack,time,minusas);
		}
		return null;
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	250,	0,	80,	0,	0,	0,	0,	0,	0,	0,0
		};
		this.stat = stat;
	}

}
