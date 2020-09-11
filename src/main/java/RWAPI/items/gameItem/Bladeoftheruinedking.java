package RWAPI.items.gameItem;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.init.ModItems;
import RWAPI.main;
import RWAPI.util.AttackDamageSource;
import RWAPI.util.DamageSource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Bladeoftheruinedking extends ItemBase {

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

		nbt.setString("basic","기본 공격 시, 데미지의 15% 회복합니다.\n대상의 최대 체력의 8%에 해당하는 추가 피해를 입힙니다.");
		return super.initCapabilities(stack,nbt);
	}

	@Override
	protected void initstat() {
		this.stat[0] = 40;
		this.stat[5] = 0.4;
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

				if(data.equals(this.data) && source instanceof AttackDamageSource){
					if(source.getAttacker().getCurrentHealth() < source.getAttacker().getMaxHealth()){
						double heal = source.getAttacker().getCurrentHealth() + (damage/100) * 10 > source.getAttacker().getMaxHealth() ?
								source.getAttacker().getMaxHealth() : source.getAttacker().getCurrentHealth() + (damage/100) * 10;
						source.getAttacker().setCurrentHealth(heal);
					}
					double aDamage = source.getTarget().getCurrentHealth() - (source.getTarget().getMaxHealth()/100)*8 > 0 ?
							(source.getTarget().getMaxHealth()/100)*8 : source.getTarget().getCurrentHealth();
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
