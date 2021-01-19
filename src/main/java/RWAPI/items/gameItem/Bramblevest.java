package RWAPI.items.gameItem;

import RWAPI.Character.PlayerData;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Bladeoftheruinedking_passive;
import RWAPI.items.gameItem.inherence.Lifeline_passive;
import RWAPI.items.gameItem.inherence.Thorn_passive;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Bramblevest extends ItemBase {

	private final double armorPer = 3;
	private final double damage = 8;

	public Bramblevest(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] =ModItems.Clotharmor;
		down_item[1] =ModItems.Clotharmor;
		
		phase = 2;
		this.name = "덤불 조끼";
		this.gold = 1000;
		refund_gold = 700;
		// TODO Auto-generated constructor stub
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("inherence","기본 공격에 피격 된 경우 "+String.format("%d",(int)damage)+" + 현재 방어력의 "+String.format("%d",(int)armorPer)+" %에 해당하는 마법 피해를 입힙니다.");
		return super.initCapabilities(stack,nbt);
	}

	@Override
	public List<Class<? extends inherence_handler>> get_inherence_handler() {
		List<Class<? extends inherence_handler>> list = new ArrayList<>();
		list.add(Thorn_passive.class);
		return list;
	}

	@Override
	public ItemBase.inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends ItemBase.inherence_handler> _class, int idx) {
		System.out.println("덤불 조끼");
		if(_class.equals(Thorn_passive.class)){
			return new Thorn_passive(data,stack,damage,armorPer);
		}

		return null;
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	0,	0,	35,	0,	0,	0,	0,	0,	0,	0,	0
		};
		this.stat = stat;
	}

}
