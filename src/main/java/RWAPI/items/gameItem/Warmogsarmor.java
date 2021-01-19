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

public class Warmogsarmor extends ItemBase {

	private final int passivTime = 3;
	private final double plusHregen = 20;

	public Warmogsarmor(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[3];
		down_item[0] =ModItems.Rubycrystal;
		down_item[1] =ModItems.Giantsbelt;
		down_item[2] =ModItems.Crystallinebracer;
		
		phase = 1;
		this.name = "워모그의 갑옷";
		this.gold = 3200;
		refund_gold = 2240;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	800,	0,	0,	0,	0,	0,	10,	0,	0,	0,0
		};
		this.stat = stat;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("basic",passivTime+"초간 피격되지 않은 경우, 체력 재생이 "+String.format("%.1f",plusHregen)+" 증가합니다.");
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
			if(eventClass.timer == null){
				data.setRegenHealth(data.getRegenHealth()-15);
			}
			else{
				MinecraftForge.EVENT_BUS.unregister(eventClass.timer);
			}
			main.game.getEventHandler().unregister(this.eventClass);
		}

		private class EventClass extends PlayerAttackEventHandle {
			PlayerData data;
			Timer timer;

			public EventClass(PlayerData data) {
				super();
				this.data = data;
				timer = new Timer(data, 3,this);
			}

			@Override
			public void EventListener(AbstractBaseEvent event) {
				DamageSource source = ((PlayerAttackEvent)event).getSource();
				double damage = source.getDamage();

				EntityData attacker = source.getAttacker();
				EntityData target = source.getTarget();

				if(target.equals(this.data) || attacker instanceof PlayerData){
					if(timer == null){
						timer = new Timer(data, passivTime,this);
						resetregen();
					}
					else{
						timer.currentTime = 0;
					}
				}
			}

			public void setregen(){
				data.setRegenHealth(data.getRegenHealth() + plusHregen);
			}

			public void resetregen(){
				data.setRegenHealth(data.getRegenHealth() - plusHregen);
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
				setregen();
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
				this.eventClass = eventClass;
				MinecraftForge.EVENT_BUS.register(this);
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
				eventClass.resetTimer();
			}
		}
	}
}
