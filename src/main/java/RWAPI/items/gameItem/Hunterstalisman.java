package RWAPI.items.gameItem;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.EntityDeathEventHandle;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.*;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Hunterstalisman extends ItemBase {

	private final int plusGold = 20;

	public Hunterstalisman(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		
		down_item = new ItemBase[0];
		
		phase = 3;
		this.name = "사냥꾼의 부적";
		this.gold = 350;
		refund_gold = 140;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	50,	20,	0,	0,	0,	0,	0.1,	0.05,	0,	0
		};
		this.stat = stat;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("inherence","미니언 처치 시, "+plusGold+"의 골드를 추가로 지급합니다.");
		return super.initCapabilities(stack,nbt);
	}

	@Override
	public List<Class<? extends inherence_handler>> get_inherence_handler() {
		List<Class<? extends inherence_handler>> list = new ArrayList<>();
		list.add(Hunterstalisman_passive.class);
		return list;
	}

	@Override
	public ItemBase.inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends ItemBase.inherence_handler> _class) {
		if(_class.equals(Hunterstalisman_passive.class)){
			return new Hunterstalisman_passive(data,stack,plusGold);
		}

		return null;

	}

	protected class handler extends ItemBase.inherence_handler{

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
					((PlayerData)data).setGold(plusGold + ((PlayerData)data).getGold());
				}
			}

			@Override
			public EventPriority getPriority() {
				return EventPriority.NORMAL;
			}
		}
	}
}
