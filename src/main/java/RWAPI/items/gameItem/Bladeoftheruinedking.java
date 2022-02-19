package RWAPI.items.gameItem;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Bladeoftheruinedking_passive;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Bladeoftheruinedking extends ItemBase {

	private final double vamPercent = 15;
	private final double reducePercent = 8;

	public Bladeoftheruinedking(String name) {
		super(name);
		
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] = ModItems.Bilgewatercutlass;
		down_item[1] = ModItems.Recurvebow;
		
		phase = 1;
		this.name = "몰락한 왕의 검";
		this.gold = 3300;
		refund_gold = 2310;
		// TODO Auto-generated constructor stub
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("basic","기본 공격 시, 데미지의 "+String.format("%.0f",vamPercent)+"% 회복합니다.");
		nbt.setString("inherence","대상의 현재 체력의 "+String.format("%.0f",reducePercent)+"%에 해당하는 추가 피해를 입힙니다.");
		return super.initCapabilities(stack,nbt);
	}

	@Override
	protected void initstat() {
		double[] stat = {
				30,	0,	0,	0,	0,	0,	0,	0.25,	0,	0,	0,	0,	0
		};
		this.stat = stat;
	}

	@Override
	public List<Class<? extends inherence_handler>> get_inherence_handler() {
		List<Class<? extends inherence_handler>> list = new ArrayList<>();
		list.add(Bladeoftheruinedking_passive.class);
		return list;
	}

	@Override
	public ItemBase.basic_handler create_basic_handler(PlayerData data, ItemStack stack) {
		return new basic_handler(data,stack);
	}

	@Override
	public ItemBase.inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends ItemBase.inherence_handler> _class, int idx) {
		if(_class.equals(Bladeoftheruinedking_passive.class)){
			return new Bladeoftheruinedking_passive(data,stack,reducePercent);
		}

		return null;

	}

	protected class basic_handler extends ItemBase.basic_handler{

		EventClass eventClass;
		PlayerData data;

		private basic_handler(PlayerData data, ItemStack stack){
			super(data,stack);
			this.data = data;
			registerAttackEvent();
		}

		private void registerAttackEvent() {
			this.eventClass = new EventClass(data);
			main.game.getEventHandler().register(this.eventClass);
		}

		@Override
		public void removeHandler() {
			main.game.getEventHandler().unregister(this.eventClass);
		}

		private class EventClass extends PlayerAttackEventHandle {
			PlayerData data;

			public EventClass(PlayerData data) {
				super();
				this.data = data;
			}

			@Override
			public void EventListener(AbstractBaseEvent event) {
				DamageSource source = ((PlayerAttackEvent)event).getSource();
				double damage = source.getDamage();

				EntityData data = source.getAttacker();

				if(data.equals(this.data) && source.getAttackType() == DamageSource.AttackType.ATTACK
						&& source.getDamageType() == DamageSource.DamageType.PHYSICS){
					if(source.getAttacker().getCurrentHealth() < source.getAttacker().getMaxHealth()){
						double heal = source.getAttacker().getCurrentHealth() + (damage/100) * vamPercent > source.getAttacker().getMaxHealth() ?
								source.getAttacker().getMaxHealth() : source.getAttacker().getCurrentHealth() + (damage/100) * vamPercent;
						source.getAttacker().setCurrentHealth(heal);
					}
					//source.getTarget().setCurrentHealth(source.getTarget().getCurrentHealth() - aDamage);
					//System.out.println("추가 데미지 : " + aDamage);

				}
			}

			@Override
			public EventPriority getPriority() {
				return EventPriority.NORMAL;
			}

			@Override
			public code getEventCode() {
				return code.attacker;
			}

			@Override
			public EntityData getAttacker() {
				return data;
			}

			@Override
			public EntityData getTarget() {
				return null;
			}
		}
	}
}
