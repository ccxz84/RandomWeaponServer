package RWAPI.Character.buff;

import RWAPI.main;
import RWAPI.util.BuffList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public abstract class Buff {
	public int duration;
	int timer = 0;
	EntityPlayerMP player;
	public float [] data;
	
	public Buff(double duration,EntityPlayerMP player,float ... data) {
		this.duration = (int)(duration*40);
		this.player = player;
		this.data = data;
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
}
