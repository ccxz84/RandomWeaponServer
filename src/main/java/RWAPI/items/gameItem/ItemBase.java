package RWAPI.items.gameItem;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import RWAPI.Character.PlayerData;
import RWAPI.init.ModItems;
import RWAPI.main;
import RWAPI.util.NetworkUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ItemBase extends Item{
	public ItemBase[] down_item;
	public int phase;
	protected double[] stat = new double[8];
	protected String name;
	protected int gold;
	protected int refund_gold;
	
	public ItemBase(String name) {
		setUnlocalizedName(name);
		setRegistryName("item/"+name);
		this.maxStackSize = 1;
		initstat();
	}

	public int getGold(){
		return gold;
	}

	protected void initstat(){
	}

	public double[] getstat(){
		return stat;
	}

	public int getRefund_gold(){
		return refund_gold;
	}

	public List<Class<? extends inherence_handler>> get_inherence_handler(){
		return null;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		byte[] bs = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(bos);
			oos.writeObject(stat);
			bs = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		nbt.setByteArray("stat", bs);
		nbt.setString("name",this.name);
		nbt.setString("gold",""+gold);
		stack.setTagCompound(nbt);
		//System.out.println("생성 스택 : " + stack + " 생성 아이템 : " + stack.getItem());
		return super.initCapabilities(stack, nbt);
	}

	public basic_handler create_basic_handler(PlayerData data, ItemStack stack){
		return null;
	};

	public inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends ItemBase.inherence_handler> _class){
		return null;
	};

	public usage_handler create_usage_handler(PlayerData data, ItemStack stack){
		return null;
	};

	public static abstract class handler{

	}

	public static abstract class basic_handler extends handler{

		public basic_handler(PlayerData data, ItemStack stack){

		}

		public void removeHandler(){

		}
	}

	public static abstract class inherence_handler extends handler{

		public inherence_handler(PlayerData data, ItemStack stack){

		}

		public void removeHandler(){

		}
	}

	public static abstract class usage_handler extends handler{

		public usage_handler(PlayerData data, ItemStack stack){

		}

		public void removeHandler(){

		}

		public void ItemUse(){

		}
	}

	public static abstract class coolHandler{
		protected int MaxTime;
		protected int currentTime = 0;
		private ItemStack stack;

		public coolHandler(double timer, ItemStack stack){
			this.MaxTime = (int) (timer * 40);
			this.stack = stack;
			MinecraftForge.EVENT_BUS.register(this);
		}

		@SubscribeEvent
		public void gameTimer(TickEvent.ServerTickEvent event) {
			currentTime++;
			NetworkUtil.setCool(stack,(MaxTime-(double)currentTime)/40);
			if(currentTime > MaxTime) {
				MinecraftForge.EVENT_BUS.unregister(this);
				TimerEnd();
			}
		}

		abstract public void TimerEnd();
	}


	public interface shoes{

	}

}
