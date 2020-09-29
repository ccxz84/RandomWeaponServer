package RWAPI.items.gameItem;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.init.ModItems;
import RWAPI.main;
import RWAPI.util.AttackDamageSource;
import RWAPI.util.DamageSource;
import RWAPI.util.SkillDamageSource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class Deathfiregrasp extends ItemBase {

	private final double reducePercent = 10;

	public Deathfiregrasp(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[3];
		down_item[0] =ModItems.Amplifyingtome;
		down_item[1] =ModItems.Lostchapter;
		down_item[2] =ModItems.Amplifyingtome;
		
		phase = 1;
		this.name = "죽음불꽃 손아귀";
		this.gold = 2700;
		refund_gold = 1890;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		this.stat[0] = 0;
		this.stat[1] = 120;
		this.stat[2] = 0;
		this.stat[3] = 300;
		this.stat[4] = 0;
		this.stat[5] = 0;
		this.stat[6] = 0;
		this.stat[7] = 3;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("basic","스킬 공격 시, 대상의 현재 체력의 "+String.format("%.0f",this.reducePercent)+"%에 해당하는 추가 피해를 입힙니다.");
		return super.initCapabilities(stack,nbt);
	}

	@Override
	public ItemBase.handler create_handler(PlayerData data, ItemStack stack) {
		return new handler(data,stack);
	}

	protected class handler extends ItemBase.handler{

		EventClass eventClass;
		PlayerData data;

		private handler(PlayerData data, ItemStack stack){
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

				if(data.equals(this.data) && source instanceof SkillDamageSource){
					double aDamage = source.getTarget().getCurrentHealth() - (source.getTarget().getCurrentHealth()/100)*reducePercent > 0 ?
							(source.getTarget().getCurrentHealth()/100)*reducePercent : source.getTarget().getCurrentHealth();
					source.getTarget().setCurrentHealth(source.getTarget().getCurrentHealth() - aDamage);
					//System.out.println("추가 데미지 : " + aDamage);

				}
			}

			@Override
			public EventPriority getPriority() {
				return EventPriority.NORMAL;
			}
		}
	}
}
