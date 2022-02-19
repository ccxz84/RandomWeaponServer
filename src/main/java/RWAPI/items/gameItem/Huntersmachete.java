package RWAPI.items.gameItem;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.EntityDeathEventHandle;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.init.ModItems;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class Huntersmachete extends ItemBase {

	private final double expPer = 50;

	public Huntersmachete(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		
		down_item = new ItemBase[0];
		
		phase = 3;
		this.name = "사냥꾼의 마체테";
		this.gold = 350;
		refund_gold = 140;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,0
		};
		this.stat = stat;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("basic","미니언 처치 시, "+String.format("%.0f",expPer)+"%의 경험치를 추가로 제공합니다.");
		return super.initCapabilities(stack,nbt);
	}

	@Override
	public ItemBase.basic_handler create_basic_handler(PlayerData data, ItemStack stack) {
		return new handler(data,stack);
	}

	protected class handler extends ItemBase.basic_handler{

		EventClass eventClass;
		PlayerData data;

		private handler(PlayerData data, ItemStack stack){
			super(data,stack);
			this.data = data;
			registerDeathEvent();
		}

		private void registerDeathEvent() {
			this.eventClass = new EventClass(data);
			main.game.getEventHandler().register(this.eventClass);
		}

		@Override
		public void removeHandler() {
			main.game.getEventHandler().unregister(this.eventClass);
		}

		private class EventClass extends EntityDeathEventHandle {
			PlayerData data;

			public EventClass(PlayerData data) {
				super();
				this.data = data;
			}

			@Override
			public void EventListener(AbstractBaseEvent event) {
				DamageSource source = ((EntityDeathEvent)event).getSource();
				double damage = source.getDamage();

				EntityData data = source.getAttacker();

				if(data.equals(this.data)){
					((PlayerData)data).setExp((source.getTarget().getDeathExp()/100) * expPer + ((PlayerData)data).getExp());
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
