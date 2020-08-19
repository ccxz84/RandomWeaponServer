package RWAPI.items.gameItem;

import java.util.ArrayList;
import java.util.List;

import RWAPI.main;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ItemBase extends Item{
	public ItemBase[] down_item;
	public int phase;
	protected double[] stat = new double[8];
	
	public ItemBase(String name) {
		setUnlocalizedName(name);
		setRegistryName("item/"+name);
		this.maxStackSize = 1;
		initstat();
	}

	protected void initstat(){
	}

	public double[] getstat(){
		return stat;
	}

	public handler create_handler(){
		return null;
	};

	public static abstract class handler{

		public handler(){
			MinecraftForge.EVENT_BUS.register(this);
		}

		@SubscribeEvent
		abstract public void itemHandler(TickEvent.ServerTickEvent event);

		public void removeHandler(){
			MinecraftForge.EVENT_BUS.unregister(this);
		}
	}
}
