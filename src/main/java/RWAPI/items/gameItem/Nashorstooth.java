package RWAPI.items.gameItem;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Nahorstooth_passive;
import RWAPI.items.gameItem.inherence.Sanguineblade_passive;
import RWAPI.main;
import RWAPI.util.DamageSource.AttackPhysicsDamageSource;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Nashorstooth extends ItemBase {

	double plusdamageper = 0.15, plusdamage = 15;

	public Nashorstooth(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] =ModItems.Serrateddirk;
		down_item[1] =ModItems.VampiricScepter;
		
		phase = 1;
		this.name = "내셔의 이빨";
		this.gold = 2850;
		refund_gold = 1995;
		// TODO Auto-generated constructor stub
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("inherence","기본 공격 시, "+ String.format("%d", (int)plusdamage)+" + 주문력의 " +String.format("%d",(int)(plusdamageper*100))+"%의 마법 피해를 추가로 입힙니다.");
		return super.initCapabilities(stack,nbt);
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	80,	0,	0,	0,	0,	0,	0.5,	0,	0,	0,	0
		};
		this.stat = stat;
	}

	@Override
	public List<Class<? extends inherence_handler>> get_inherence_handler() {
		List<Class<? extends inherence_handler>> list = new ArrayList<>();
		list.add(Nahorstooth_passive.class);
		return list;
	}

	@Override
	public inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends inherence_handler> _class) {
		if(_class.equals(Nahorstooth_passive.class)){
			return new Nahorstooth_passive(data,stack,plusdamageper,plusdamage);
		}

		return null;

	}
}
