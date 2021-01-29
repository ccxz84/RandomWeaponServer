package RWAPI.items.gameItem;

import RWAPI.Character.PlayerData;
import RWAPI.Character.buff.Buff;
import RWAPI.init.ModItems;
import RWAPI.main;
import RWAPI.util.NetworkUtil;
import com.google.common.graph.Network;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Youmuusghostblade extends ItemBase {

	private final int duration = 10;
	private final double plusMove = 25;
	private final int cooltime = 20;

	public Youmuusghostblade (String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] =ModItems.Serrateddirk;
		down_item[1] =ModItems.Caulfieldswarhammer;
		
		phase = 1;
		this.name = "요우무의 유령검";
		this.gold = 2900;
		refund_gold = 2030;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				60,	0,	0,	0,	0,	0,	0,	0,	0,	0,	15,	0,	10

		};
		this.stat = stat;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("usage","사용 시, "+duration+"초동안 이동속도가 "+String.format("%.0f",plusMove)+" 증가합니다. (쿨타임 "+cooltime+"초)");
		return super.initCapabilities(stack,nbt);
	}

	@Override
	public ItemBase.usage_handler create_usage_handler(PlayerData data, ItemStack stack) {
		return new handler(data,stack);
	}

	protected class handler extends ItemBase.usage_handler{

		PlayerData data;
		buff buff;
		cool cool;
		boolean bbuff = false;

		public handler(PlayerData data, ItemStack stack) {
			super(data,stack);
			this.data = data;
			this.stack = stack;
			boolean flag = NetworkUtil.getStackData(stack,"buff") != null ? (boolean)NetworkUtil.getStackData(stack,"buff"):false;
			if(NetworkUtil.getCool(stack) != 0){
				cool = new cool(NetworkUtil.getCool(stack),stack);
			}
			/*if(flag){
				cool = new cool(cooltime,stack);
				NetworkUtil.setStackData(stack,false,"buff");
			}
			else if(NetworkUtil.getCool(stack) != 0){
				cool = new cool(NetworkUtil.getCool(stack),stack);
			}*/
		}

		public void removeHandler(){
			if(buff != null){
				MinecraftForge.EVENT_BUS.unregister(buff);
				buff.resetEffect();
				buff = null;
			}
			if(cool != null){
				MinecraftForge.EVENT_BUS.unregister(cool);
				cool = null;
			}
			if(bbuff){
				NetworkUtil.setCool(stack,0);
			}
		}

		private void bufreset(){
			buff = null;
			cool = new cool(cooltime,stack);
		}

		private void coolreset(){
			cool = null;
		}

		public void ItemUse(){
			if(cool != null){
				return;
			}

			buff = new buff(duration,data);
		}

		@Override
		public void ItemUse(ItemStack stack) {
			super.ItemUse(stack);
			if(cool != null){
				return;
			}
			if(buff != null){
				return;
			}

			buff = new buff(duration,data);
		}

		private class buff extends Buff {

			public buff(double duration, PlayerData player, double... data) {
				super(duration, player,false,false, data);
				bbuff = true;
			}

			@Override
			public void BuffTimer(TickEvent.ServerTickEvent event) throws Throwable {
				super.BuffTimer(event);
				NetworkUtil.setCool(stack,(this.duration - (double)this.timer)/40);
			}

			@Override
			public void setEffect(){
				this.player.setMove(this.player.getMove() + plusMove);
				this.player.addBuff(this);
			}

			@Override
			public void resetEffect() {
				this.player.setMove(this.player.getMove() - plusMove);
				NetworkUtil.setCool(stack,0);
				bufreset();
				this.player.removeBuff(this);
				bbuff = false;
			}

			@Override
			public ItemStack getBuffIcon() {
				return new ItemStack(stack.getItem());
			}
		}

		private class cool extends coolHandler{

			public cool(double timer,ItemStack stack) {
				super(timer,stack);
			}

			@Override
			public void TimerEnd() {
				NetworkUtil.setCool(stack,0);
				coolreset();
			}
		}
	}
}
