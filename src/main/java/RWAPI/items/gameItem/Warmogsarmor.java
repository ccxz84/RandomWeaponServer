package RWAPI.items.gameItem;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.buff.Buff;
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
	private final double plusHregenper = 0.05;

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
				0,	0,	800,	0,	0,	0,	0,	0,	1,	0,	0,	0,0
		};
		this.stat = stat;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("basic",passivTime+"초간 피격되지 않은 경우, 최대 체력의 "+(int)plusHregenper*5+"%가 1초마다 증가합니다.");
		return super.initCapabilities(stack,nbt);
	}

	@Override
	public ItemBase.basic_handler create_basic_handler(PlayerData data, ItemStack stack) {
		return new handler(data,stack);
	}

	protected class handler extends ItemBase.basic_handler{

		EventClass eventClass;
		PlayerData data;
		buff buff;
		ItemStack stack;
		double maxhealth;

		private handler(PlayerData data, ItemStack stack){
			super(data,stack);
			this.data = data;
			this.stack = stack;
			this.buff = new buff(0,data,false,false);
			registerAttackEvent();
		}

		private void registerAttackEvent() {
			this.eventClass = new EventClass(data);
			main.game.getEventHandler().register(this.eventClass);
		}

		@Override
		public void removeHandler() {
			if(eventClass.timer != null){
				MinecraftForge.EVENT_BUS.unregister(eventClass.timer);
			}
			if(buff != null){
				buff.unregister();
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
				if(buff!= null){
					buff.unregister();
				}
				buff = new buff(0,data,false,false);
			}

			public void resetregen(){
				if(buff != null){
					buff.unregister();
					buff = null;
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

		private class buff extends Buff {

			ItemStack icon;

			public buff(double duration, PlayerData player, boolean debuff, boolean clean, double... data) {
				super(duration, player, debuff, clean, data);
				this.icon = stack.copy();
			}

			@Override
			public void BuffTimer(TickEvent.ServerTickEvent event) throws Throwable {
				++timer;
				if(timer % 40 == 0){
					double health = player.getCurrentHealth() + player.getMaxHealth() * plusHregenper >= player.getMaxHealth() ? player.getMaxHealth() : player.getCurrentHealth() + player.getMaxHealth() * plusHregenper;
					player.setCurrentHealth(health);
				}
			}

			@Override
			public void setEffect() {
				player.addBuff(this);
			}

			@Override
			public void resetEffect() {
				player.removeBuff(this);
			}

			public void unregister(){
				MinecraftForge.EVENT_BUS.unregister(this);
				resetEffect();
			}

			@Override
			public ItemStack getBuffIcon() {
				return icon;
			}
		}
	}
}
