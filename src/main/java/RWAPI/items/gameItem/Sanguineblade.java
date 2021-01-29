package RWAPI.items.gameItem;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Sanguineblade_passive;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Sanguineblade extends ItemBase {

	double plusas = 0.3, plusamorpen = 5, vamPercent = 5;

	public Sanguineblade(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] =ModItems.Serrateddirk;
		down_item[1] =ModItems.VampiricScepter;
		
		phase = 1;
		this.name = "핏빛 칼날";
		this.gold = 3000;
		refund_gold = 2100;
		// TODO Auto-generated constructor stub
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("inherence","일정 범위 안에 적이 있는 경우 "+String.format("%d",(int)(plusas*100))+"%의 공격속도와 " +String.format("%d",(int)plusamorpen)+  "의 방어 관통력이 증가합니다.");
		nbt.setString("basic","기본 공격 시, 데미지의 "+String.format("%.0f",vamPercent)+"%를 회복합니다.");
		return super.initCapabilities(stack,nbt);
	}

	@Override
	protected void initstat() {
		double[] stat = {
				55,	0,	0,	0,	0,	0,	0,	0,	0,	0,	10,	0,0
		};
		this.stat = stat;
	}

	@Override
	public ItemBase.basic_handler create_basic_handler(PlayerData data, ItemStack stack) {
		return new basic_handler(data,stack);
	}

	@Override
	public List<Class<? extends inherence_handler>> get_inherence_handler() {
		List<Class<? extends inherence_handler>> list = new ArrayList<>();
		list.add(Sanguineblade_passive.class);
		return list;
	}

	@Override
	public ItemBase.inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends ItemBase.inherence_handler> _class, int idx) {
		if(_class.equals(Sanguineblade_passive.class)){
			return new Sanguineblade_passive(data,stack,plusas,plusamorpen);
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
