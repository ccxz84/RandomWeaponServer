package RWAPI.ui;

import RWAPI.Character.PlayerData;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.items.inventory.Inventory;
import RWAPI.items.inventory.ItemButton;
import RWAPI.main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Shopui extends Container implements IContainerListener {
	public int scroll = 0;
	public int scrollMax = ModItems.ITEMS.size()%32 == 0 ? ModItems.ITEMS.size()/32:(ModItems.ITEMS.size()/32) + 1;
	private Inventory inven;
	private EntityPlayerMP player;
	private List<ItemBase> list;
	public  ItemStack currentstack;
	
	public Shopui(InventoryPlayer playerInv) {

		player = (EntityPlayerMP) playerInv.player;
		list = new ArrayList<>(ModItems.ITEMS);
		list.sort(new Comparator<ItemBase>() {
			@Override
			public int compare(ItemBase o1, ItemBase o2) {
				if(o1.getGold() < o2.getGold()){
					return -1;
				}
				else if(o1.getGold() == o2.getGold()){
					return 0;
				}
				else
					return 1;
			}
		});
		inven = new Inventory(list);
		// Player Inventory, Slot 9-35, Slot IDs 9-35

		for (int y = 0; y < 5; ++y) {
			for (int x = 0; x < 6; ++x) {
				this.addSlotToContainer(new Slot(inven, x + y * 6, 8 + x * 18, 18+y * 18));
			}
		}



		// Player Inventory, Slot 0-8, Slot IDs 36-44

		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 121+y * 18));
			}
		}

		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 179));
		}


	    //this.addSlotToContainer(new Slot(new ItemButton(ItemStack.EMPTY),0,190,18));
	    scrollTo(0);
		this.addListener(this);
	}



	public void scrollTo(int pos)
    {
        this.scroll = pos;
        inven.setLowerLimit(scroll * 6);

    }

    public void sync(){
		//System.out.println("run sync");
		for(int i = 0; i < this.inventorySlots.size();i++) {
			player.connection.sendPacket(new SPacketSetSlot(this.windowId, i, this.getInventory().get(i)));
		}
	}

	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		// TODO Auto-generated method stub
		return this.inven.isUsableByPlayer(playerIn);
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		// TODO Auto-generated method stub
		if(slotId<30) {
			
			if(dragType==1) {//right click
				
			}
			if(dragType==0) {//left click
				ItemStack stack = inven.getStackInSlot(slotId);
				
				if(stack == null || stack.equals(ItemStack.EMPTY)) {
					return ItemStack.EMPTY;
				}
				itemButton(stack);
				
			}
			
			return ItemStack.EMPTY;
		}

		if(slotId == 30 || slotId == 57)
			return ItemStack.EMPTY;
		if(slotId > 30 && slotId < 39){
			PlayerData data = main.game.getPlayerData(player.getUniqueID());
			ItemBase item = (ItemBase) this.inventoryItemStacks.get(slotId).getItem();
			double[] stat = item.getstat();
			if(data.getCurrentHealth() - stat[2] <= 0 || data.getCurrentMana() - stat[3] <= 0){
				return ItemStack.EMPTY;
			}
		}
		if(slotId > 30 && slotId < 57){
			if(dragType==1) {//right click
				if(this.inventorySlots.get(slotId).getStack().getItem() instanceof ItemBase){
					ItemBase item = (ItemBase) this.inventorySlots.get(slotId).getStack().getItem();
					PlayerData data = main.game.getPlayerData(player.getUniqueID());
					data.setGold(data.getGold() + item.getRefund_gold());
					this.inventorySlots.get(slotId).getStack().setCount(0);
				}
			}
		}
		
		if(slotId > 65) {
			if(dragType==0) {//left click
				ItemStack stack = this.getSlot(slotId).getStack();
				
				if(stack == null || stack.equals(ItemStack.EMPTY)) {
					return ItemStack.EMPTY;
				}
				itemButton(stack);
			}
			
			return ItemStack.EMPTY;
		}
		
		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}



	private void itemButton(ItemStack stack) {
		// TODO Auto-generated method stub
		int x = inventorySlots.size();
		for(int i =66;i<x;i++) {
			inventorySlots.remove(inventorySlots.get(66));
			
		}
		currentstack = stack;
		DrawButton(stack,ModItems.ITEMS.get(ModItems.ITEMS.indexOf(stack.getItem())).down_item,187,18,ModItems.ITEMS.get(ModItems.ITEMS.indexOf(stack.getItem())).phase);

		addSlotToContainer(new Slot(new ItemButton(stack),0,137,97));
		sync();
	}
	
	private void DrawButton(ItemStack stack, ItemBase[] down_item, int x, int y,int phase){
		addSlotToContainer(new Slot(new ItemButton(stack),0,x,y));
		
		if(phase == 1) {
			for(int i = 0; i < down_item.length; i++) {
				if(i == 0) {
					DrawButton(new ItemStack(down_item[i]),down_item[i].down_item,x-40,y+20,down_item[i].phase);
				}
				else if(i == 1) {
					DrawButton(new ItemStack(down_item[i]),down_item[i].down_item,x+40,y+20,down_item[i].phase);
				}
				else if(i == 2) {
					DrawButton(new ItemStack(down_item[i]),down_item[i].down_item,x,y+20,down_item[i].phase);
				}
				
			}
		}
		
		if(phase == 2) {
			if(down_item.length== 2) {
				for(int i = 0; i < down_item.length; i++) {
					DrawButton(new ItemStack(down_item[i]),down_item[i].down_item,x-10+i*20,y+20,down_item[i].phase);
				}
			}
			else if(down_item.length == 3) {
				for(int i = 0; i < down_item.length; i++) {
					DrawButton(new ItemStack(down_item[i]),down_item[i].down_item,x-40+i*40,y+20,down_item[i].phase);
				}
			}
			else if(down_item.length == 1) {
				for(int i = 0; i < down_item.length; i++) {
					DrawButton(new ItemStack(down_item[i]),down_item[i].down_item,x,y+20,down_item[i].phase);
				}
			}
			else if(down_item.length == 0) {
				return;
			}
		}
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();


		//System.out.println("test");
	}

	@Override
	public void sendAllContents(Container containerToSend, NonNullList<ItemStack> itemsList) {
		//System.out.println(containerToSend.inventorySlots.get(0));
	}

	@Override
	public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
		//System.out.println("stack : " + stack + " index : " + slotInd);

	}

	@Override
	public void sendWindowProperty(Container containerIn, int varToUpdate, int newValue) {
		//System.out.println("sendWindowProperty");
	}

	@Override
	public void sendAllWindowProperties(Container containerIn, IInventory inventory) {
		//System.out.println("sendAllWindowProperties");
	}
}
