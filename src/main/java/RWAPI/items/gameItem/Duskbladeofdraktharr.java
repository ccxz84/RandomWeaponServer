package RWAPI.items.gameItem;

import RWAPI.Character.CooldownHandler;
import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.buff.Buff;
import RWAPI.Character.monster.entity.IMob;
import RWAPI.init.ModItems;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import RWAPI.util.NetworkUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Duskbladeofdraktharr extends ItemBase {

	int cooltime = 20;
	int duration = 3;
	double damage = 50;
	double adcoe = 0.3;
	double slowPer = 60;

	public Duskbladeofdraktharr(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] =ModItems.Serrateddirk;
		down_item[1] =ModItems.Caulfieldswarhammer;
		
		phase = 1;
		this.name = "드락사르의 황혼검";
		this.gold = 2900;
		refund_gold = 2030;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				60,	0,	0,	0,	0,	0,	0,	0,	0,	0,	20,	0,	10

		};
		this.stat = stat;
	}
	@Override
	public ItemBase.usage_handler create_usage_handler(PlayerData data, ItemStack stack) {
		return new handler(data,stack);
	}


	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("usage","사용 후 "+duration+"초 안에 적을 공격 시, 1초 동안 이동속도를 "+String.format("%d",(int)slowPer)+"% 감소시킵니다. (쿨타임 "+cooltime+"초)");
		return super.initCapabilities(stack,nbt);
	}
	
	protected class handler extends ItemBase.usage_handler{

		PlayerData data;
		ItemStack stack;
		buff buff;
		cool cool;

		public handler(PlayerData data, ItemStack stack) {
			super(data,stack);
			this.data = data;
			this.stack = stack;
			boolean flag = NetworkUtil.getStackData(stack,"buff") != null ? (boolean)NetworkUtil.getStackData(stack,"buff"):false;
			if(flag){
				cool = new cool(cooltime,stack, data);
				NetworkUtil.setStackData(stack,false,"buff");
			}
			else if(NetworkUtil.getCool(stack) != 0){
				cool = new cool(NetworkUtil.getCool(stack),stack,data);
				NetworkUtil.setStackData(stack,false,"buff");
			}
		}

		public void removeHandler(){
			if(buff != null){
				buff = null;
			}
			if(cool != null){
				if(cool.flag == false){
					cool.setcool();
				}
				MinecraftForge.EVENT_BUS.unregister(cool);
				cool = null;
			}
		}

		private void bufreset(){
			buff = null;
		}

		private void coolreset(){
			cool = null;
		}

		@Override
		public void ItemUse(ItemStack stack){
			super.ItemUse(stack);
			if(cool != null){
				return;
			}
			cool = new cool(duration, stack, data);
			//buff = new buff(duration,data.getPlayer());
		}

		private class buff extends Buff {

			double slow;

			public buff(double duration, PlayerData player, double... data) {
				super(duration, player,true,true, data);

			}

			@Override
			public void BuffTimer(TickEvent.ServerTickEvent event) throws Throwable {
				super.BuffTimer(event);

			}

			@Override
			public void setEffect(){
				slow = player.getMove() * slowPer / 100;
				this.player.setMove(this.player.getMove() - slow);
				player.addBuff(this);
			}

			@Override
			public void resetEffect() {
				this.player.setMove(this.player.getMove() + slow);
				bufreset();
				player.removeBuff(this);
			}

			@Override
			public ItemStack getBuffIcon() {
				return new ItemStack(ModItems.Duskbladeofdraktharr);
			}
		}

		private class cool extends coolHandler{

			PlayerData data;
			boolean flag = false;

			public cool(double timer,ItemStack stack, PlayerData data) {
				super(timer,stack);
				this.data = data;
			}

			@SubscribeEvent
			public void gameTimer(TickEvent.ServerTickEvent event) {
				currentTime++;
				NetworkUtil.setCool(stack,(MaxTime-(double)currentTime)/40);
				if(currentTime > MaxTime) {
					if(flag == false){
						flag = true;
						setcool();
					}
					else{
						MinecraftForge.EVENT_BUS.unregister(this);
						TimerEnd();
					}
				}
			}

			@Override
			public void TimerEnd() {
				NetworkUtil.setCool(stack,0);
				coolreset();
			}

			@SubscribeEvent
			public void PlayerAttackEvent(AttackEntityEvent event) {
				if (event.getEntityPlayer().getUniqueID().equals(data.getPlayer().getUniqueID()) && flag == false) {
					flag = true;
					EntityLivingBase etarget = (EntityLivingBase) event.getTarget();
					PlayerData attacker = main.game.getPlayerData(event.getEntityPlayer().getUniqueID());
					EntityData target = (etarget instanceof EntityPlayer) ? main.game.getPlayerData(etarget.getUniqueID()) : ((IMob) etarget).getData();
					DamageSource sourcee = DamageSource.causeUnknownPhysics(attacker, target, damage + adcoe * attacker.getAd());
					DamageSource.attackDamage(sourcee, true);
					DamageSource.EnemyStatHandler.EnemyStatSetter(sourcee);
					if (target instanceof PlayerData)
						buff = new buff(1, (PlayerData) target);
					setcool();
				}
			}

			public void setcool(){
				currentTime = 0;
				MaxTime = cooltime * 40;
			}
		}
	}
}
