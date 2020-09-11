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
		this.stat[0] = 60;
		this.stat[1] = 0;
		this.stat[2] = 200;
		this.stat[3] = 100;
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

		nbt.setString("usage","사용 시, 5초동안 이동속도가 20 증가합니다. (쿨타임 30초)");
		return super.initCapabilities(stack,nbt);
	}

	@Override
	public ItemBase.handler create_handler(PlayerData data, ItemStack stack) {
		return new handler(data,stack);
	}

	protected class handler extends ItemBase.handler{

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
				cool = new cool(30,stack);
				NetworkUtil.setStackData(stack,false,"buff");
			}
			else if(NetworkUtil.getCool(stack) != 0){
				cool = new cool(NetworkUtil.getCool(stack),stack);
				NetworkUtil.setStackData(stack,false,"buff");
			}
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
		}

		private void bufreset(){
			buff = null;
			NetworkUtil.setStackData(stack,false,"buff");
			cool = new cool(30,stack);
		}

		private void coolreset(){
			cool = null;
		}

		public void ItemUse(){
			if(cool != null){
				return;
			}

			buff = new buff(5,data.getPlayer());
		}

		private class buff extends Buff {

			PlayerData pdata;

			public buff(double duration, EntityPlayerMP player, double... data) {
				super(duration, player, data);
				NetworkUtil.setStackData(stack,true,"buff");
			}

			@Override
			public void BuffTimer(TickEvent.ServerTickEvent event) throws Throwable {
				super.BuffTimer(event);
				NetworkUtil.setCool(stack,(this.duration - (double)this.timer)/40);
			}

			@Override
			public void setEffect(){
				this.pdata = main.game.getPlayerData(player.getUniqueID());
				this.pdata.setMove(this.pdata.getMove() + 20);
			}

			@Override
			public void resetEffect() {
				this.pdata.setMove(this.pdata.getMove() - 20);
				NetworkUtil.setCool(stack,0);
				bufreset();
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
