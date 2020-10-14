package RWAPI.ui;

import RWAPI.Character.PlayerData;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.main;
import RWAPI.util.GameStatus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class InventoryUI extends Container {
	
	private InventoryPlayer inven;

	public InventoryUI(InventoryPlayer playerInv) {
		inven = playerInv;
		// Player Inventory, Slot 9-35, Slot IDs 9-35
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 51+y * 18));
			}
		}

		// Player Inventory, Slot 0-8, Slot IDs 36-44
		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 108));
		}
		//this.addSlotToContainer(new Slot(playerInv,9,0,0));
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		// TODO Auto-generated method stub
		return this.inven.isUsableByPlayer(playerIn);
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		// TODO Auto-generated method stub
		//detectAndSendChanges();
		if(slotId == 0 || slotId == 27)
			return ItemStack.EMPTY;

		if((slotId > 27 && slotId < 36)&& (main.game.start == GameStatus.PRESTART || main.game.start == GameStatus.START)){
			PlayerData data = main.game.getPlayerData(player.getUniqueID());
			if(this.inventoryItemStacks.get(slotId).getItem() instanceof ItemBase){
				ItemBase item = (ItemBase) this.inventoryItemStacks.get(slotId).getItem();
				double[] stat = item.getstat();
				if(data.getCurrentHealth() - stat[2] <= 0 || data.getCurrentMana() - stat[3] <= 0){
					return ItemStack.EMPTY;
				}
			}
		}
		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
	}
	

}
