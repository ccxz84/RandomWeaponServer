package RWAPI.ui;

import RWAPI.init.ModItems;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.items.inventory.Inventory;
import RWAPI.items.inventory.ItemButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class Shopui extends Container{
	public int scroll = 0;
	public int scrollMax = 7;
	private Inventory inven;
	
	public Shopui(InventoryPlayer playerInv) {
		inven = new Inventory();
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
	    
	}
	
	
	
	@Override
	public Slot addSlotToContainer(Slot slotIn) {
		// TODO Auto-generated method stub
		return super.addSlotToContainer(slotIn);
	}



	public void scrollTo(int pos)
    {
        this.scroll = pos;
        inven.setLowerLimit(scroll * 6);
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
				
				if(stack == null) {
					return ItemStack.EMPTY;
				}
				itemButton(stack);
				
			}
			
			return ItemStack.EMPTY;
		}
		
		if(slotId > 65) {
			if(dragType==0) {//left click
				ItemStack stack = this.getSlot(slotId).getStack();
				
				if(stack == null) {
					System.out.println("test");
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
		DrawButton(stack,ModItems.temp.get(ModItems.temp.indexOf(stack.getItem())).down_item,187,18,ModItems.temp.get(ModItems.temp.indexOf(stack.getItem())).phase);
		addSlotToContainer(new Slot(new ItemButton(stack),0,145,97));
	}
	
	private void DrawButton(ItemStack stack, ItemBase[] down_item, int x, int y,int phase) {
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
}
