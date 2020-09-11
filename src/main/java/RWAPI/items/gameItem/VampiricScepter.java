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

public class VampiricScepter extends ItemBase {

	public VampiricScepter(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[1];
		down_item[0] =ModItems.LongSword;
		
		phase = 2;
		this.name = "흡혈의 낫";
		this.gold = 900;
		refund_gold = 630;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		this.stat[0] = 15;
		this.stat[1] = 0;
		this.stat[2] = 0;
		this.stat[3] = 0;
		this.stat[4] = 0;
		this.stat[5] = 0;
		this.stat[6] = 0;
		this.stat[7] = 0;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("basic","기본 공격 시, 데미지의 10%를 회복합니다.");
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

				if(data.equals(this.data) && source instanceof AttackDamageSource){
					if(source.getAttacker().getCurrentHealth() < source.getAttacker().getMaxHealth()){
						double heal = source.getAttacker().getCurrentHealth() + (damage/100) * 10 > source.getAttacker().getMaxHealth() ?
								source.getAttacker().getMaxHealth() : source.getAttacker().getCurrentHealth() + (damage/100) * 10;
						source.getAttacker().setCurrentHealth(heal);
					}
				}
			}

			@Override
			public EventPriority getPriority() {
				return EventPriority.NORMAL;
			}
		}


	}

}
