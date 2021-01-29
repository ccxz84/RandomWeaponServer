package RWAPI.Character.buff;

import RWAPI.Character.PlayerData;
import RWAPI.main;
import RWAPI.util.BuffList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public abstract class Buff {
	protected int duration;
	protected int timer = 0;
	protected PlayerData player;
	protected double [] data;
	protected boolean debuff;
	protected boolean clean;
	
	public Buff(double duration, PlayerData player, boolean debuff, boolean clean, double ... data) {
		this.duration = (int)(duration*40);
		this.player = player;
		this.data = data;
		this.debuff = debuff;
		this.clean = clean;
		setEffect();
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void BuffTimer(ServerTickEvent event) throws Throwable {
		if(timer > duration) {
			resetEffect();
			MinecraftForge.EVENT_BUS.unregister(this);
		}
		timer++;
	}
	
	public abstract void setEffect();
	public abstract void resetEffect();
	public abstract ItemStack getBuffIcon();

	public boolean isDebuff(){
		return this.debuff;
	}
	public boolean isClean(){
		return this.clean;
	}
	public double gettime(){
		int time = (duration - timer) / 40;
		if(time > 0){
			return time;
		}
		else{
			return (double)(duration - timer) / 40;
		}
	}
}
