package RWAPI.items.gameItem;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.init.ModItems;
import RWAPI.main;
import RWAPI.util.DamageSource.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Ninjatabi extends ItemBase implements ItemBase.shoes {

	private final double reducePercent = 12;

	public Ninjatabi(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] =ModItems.Bootsofspeed;
		down_item[1] =ModItems.Clotharmor;
		
		phase = 2;
		this.name = "닌자의 신발";
		this.gold = 1100;
		refund_gold = 770;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	400,	0,	15,	0,	20,	0,	0,	0,	0,	0,0
		};
		this.stat = stat;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("inherence","기본 공격에 피격 된 경우 " + String.format("%d",(int)reducePercent) + "%의 데미지가 감소합니다.");
		return super.initCapabilities(stack,nbt);
	}

	@Override
	public ItemBase.inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends ItemBase.inherence_handler> _class, int idx) {
		if(_class.equals(inherence_handler.class)){
			return new inherence_handler(data,stack,reducePercent);
		}

		return null;

	}

	@Override
	public List<Class<? extends ItemBase.inherence_handler>> get_inherence_handler() {
		List<Class<? extends ItemBase.inherence_handler>> list = new ArrayList<>();
		list.add(inherence_handler.class);
		return list;
	}

	private class inherence_handler extends ItemBase.inherence_handler{

		EventClass eventClass;
		PlayerData data;
		private final double reducePercent;

		public inherence_handler(PlayerData data, ItemStack stack,double reducePercent){
			super(data,stack);
			this.data = data;
			this.reducePercent = reducePercent;
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

				EntityData data = source.getTarget();

				if(data.equals(this.data) && source.getAttackType() == DamageSource.AttackType.ATTACK
						&& source.getDamageType() == DamageSource.DamageType.PHYSICS){

					double aDamage =  (source.getDamage()/100)*reducePercent + data.getCurrentHealth() > data.getMaxHealth() ? data.getMaxHealth() : (source.getDamage()/100)*reducePercent + data.getCurrentHealth();
					data.setCurrentHealth(aDamage);
					//System.out.println("추가 데미지 : " + aDamage);

				}
			}

			@Override
			public EventPriority getPriority() {
				return EventPriority.NORMAL;
			}

			@Override
			public code getEventCode() {
				return code.target;
			}

			@Override
			public EntityData getAttacker() {
				return null;
			}

			@Override
			public EntityData getTarget() {
				return data;
			}
		}
	}
}
