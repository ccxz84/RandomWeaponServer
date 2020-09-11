package RWAPI.items.gameItem;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.BaseEvent;
import RWAPI.game.event.ItemChangeEventHandle;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.init.ModItems;
import RWAPI.main;
import RWAPI.util.DamageSource;
import RWAPI.util.SkillDamageSource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class Rabadonsdeathcap extends ItemBase {

	public Rabadonsdeathcap(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] =ModItems.Needlesslylargerod;
		down_item[1] =ModItems.Needlesslylargerod;
		
		phase = 2;
		this.name = "라바돈의 죽음모자";
		this.gold = 3600;
		refund_gold = 2520;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		this.stat[0] = 0;
		this.stat[1] = 150;
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

		nbt.setString("basic","주문력이 추가로 40% 증가합니다.");
		return super.initCapabilities(stack,nbt);
	}

	@Override
	public handler create_handler(PlayerData data, ItemStack stack) {
		return new handler(data,stack);
	}

	protected class handler extends ItemBase.handler{

		EventClass eventClass1, eventClass2;
		PlayerData data;

		private handler(PlayerData data, ItemStack stack){
			super(data,stack);
			this.data = data;
			registerAttackEvent();
		}

		private void registerAttackEvent() {
			this.eventClass1 = new EventClass(data, BaseEvent.EventPriority.HIGHTEST);
			this.eventClass2 = new EventClass(data, BaseEvent.EventPriority.LOWEST);
			main.game.getEventHandler().register(this.eventClass1);
			main.game.getEventHandler().register(this.eventClass2);
		}

		@Override
		public void removeHandler() {
			main.game.getEventHandler().unregister(this.eventClass1);
			main.game.getEventHandler().unregister(this.eventClass2);
		}

		private class EventClass extends ItemChangeEventHandle {
			PlayerData data;
			EventPriority priority;

			public EventClass(PlayerData data, EventPriority priority) {
				super();
				this.data = data;
				this.priority = priority;
			}

			@Override
			public void EventListener(BaseEvent.AbstractBaseEvent event) {
				PlayerData data = ((ItemChangeEvent)event).getData();

				if(data.equals(data)){
					if(this.priority == EventPriority.HIGHTEST){
						data.setAp(data.getAp()-(data.getAp()/140) * 40);
					}
					else if(this.priority == EventPriority.LOWEST){
						data.setAp(data.getAp()+(data.getAp()/100) * 40);
					}
				}
			}

			@Override
			public EventPriority getPriority() {
				return this.priority;
			}
		}
	}
}
