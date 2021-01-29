package RWAPI.items.gameItem;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.init.ModItems;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Spectrescowl extends ItemBase {

	private final int passiveTime= 5;
	private final double plusHregen = 1.5;

	public Spectrescowl(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] =ModItems.Rubycrystal;
		down_item[1] =ModItems.Nullmagicmantle;
		
		phase = 2;
		this.name = "망령의 두건";
		this.gold = 1250;
		refund_gold = 875;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	250,	0,	0,	30,	0,	0,	0,	0,	0,	0,0
		};
		this.stat = stat;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("basic","피격 시,"+passiveTime+" 초 동안 체력 재생이 "+(int)plusHregen*100+"% 증가합니다.");
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
			Timer timer;

			public EventClass(PlayerData data) {
				super();
				this.data = data;
			}

			@Override
			public void EventListener(AbstractBaseEvent event) {
				DamageSource source = ((PlayerAttackEvent)event).getSource();
				double damage = source.getDamage();

				EntityData attacker = source.getAttacker();
				EntityData target = source.getTarget();

				if(target.equals(this.data) && timer == null){
					timer = new Timer(data, (int) passiveTime,this);
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

			private void resetTimer(){
				timer = null;
			}
		}

		private class Timer{
			private PlayerData data;

			private int MaxTime;
			private int currentTime = 0;
			EventClass eventClass;
			private Timer(PlayerData data, int time, EventClass eventClass){
				this.data = data;
				MaxTime = time;
				MinecraftForge.EVENT_BUS.register(this);
				this.eventClass = eventClass;
				data.setPlusHealthRegen(data.getPlusHealthRegen() + plusHregen);
			}

			@SubscribeEvent
			public void gameTimer(TickEvent.ServerTickEvent event) {
				currentTime++;
				if(currentTime > MaxTime * 40) {
					MinecraftForge.EVENT_BUS.unregister(this);
					TimerEnd();
				}
			}

			private void TimerEnd() {
				data.setPlusHealthRegen(data.getPlusHealthRegen() - plusHregen);
				eventClass.resetTimer();
			}
		}
	}
}
