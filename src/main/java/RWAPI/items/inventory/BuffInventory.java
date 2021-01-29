package RWAPI.items.inventory;

import RWAPI.Character.PlayerData;
import RWAPI.Character.buff.Buff;
import RWAPI.init.ModSkills;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.main;
import RWAPI.util.GameStatus;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BuffInventory extends TileEntity implements IInventory {

	private final NonNullList<ItemStack> inventoryContents;
	private String CusName;
	private EntityPlayer player;
	private PlayerData data;


	public BuffInventory(EntityPlayer player) {
		//this.sync = sync;
		this.inventoryContents = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
		this.player = player;
	}

	public void update(){
		List<Buff> buffList = null;
		if(this.data != null){
			buffList = data.getBuffList();
			for(int i = 0; i < 27; i++){
				ItemStack stack = ItemStack.EMPTY;
				try{
					Buff buf = buffList.get(i);
					stack = buf.getBuffIcon();
					NBTTagCompound nbt = stack.getTagCompound();
					if(nbt == null){
						nbt = new NBTTagCompound();
					}
					nbt.setBoolean("debuff",buf.isDebuff());
					nbt.setDouble("time",buf.gettime());
					stack.setTagCompound(nbt);
				}
				catch (Exception e){

				}
				try{
					this.inventoryContents.set(i,stack);
				}
				catch (NullPointerException e){
					e.printStackTrace();
				}

			}
			//리스트 돌면서 아이템칸에 세팅

		}
		else{
			if(main.game.start == GameStatus.PRESTART || main.game.start == GameStatus.START){
				data = main.game.getPlayerData(player.getUniqueID());
			}
		}
	}




	public String getCusName() {
		return CusName;
	}


	public void setCusName(String cusName) {
		CusName = cusName;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return this.hasCustomName() ? this.CusName : "container.tutorial_tile_entity";
	}

	@Override
	public boolean hasCustomName() {
		// TODO Auto-generated method stub
		return this.CusName != null && !this.CusName.equals("");
	}

	@Override
	public ITextComponent getDisplayName() {
		// TODO Auto-generated method stub
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}

	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 27;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		// TODO Auto-generated method stub
		if (index < 0 || index >= this.getSizeInventory()) {
			
	        return null;
		}
	    return this.inventoryContents.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		
		// TODO Auto-generated method stub
		if (this.getStackInSlot(index) != null) {
	        ItemStack itemstack;
	        
	        if (this.getStackInSlot(index).getMaxStackSize() <= count) {
	            itemstack = this.getStackInSlot(index);
	            this.setInventorySlotContents(index, ItemStack.EMPTY);
	            this.markDirty();
	            return itemstack;
	        } else {
	            itemstack = this.getStackInSlot(index).splitStack(count);

	            if (this.getStackInSlot(index).getMaxStackSize() <= 0) {
	                this.setInventorySlotContents(index, ItemStack.EMPTY);
	            } else {
	                //Just to show that changes happened
	                this.setInventorySlotContents(index, this.getStackInSlot(index));
	            }

	            this.markDirty();
	            return itemstack;
	        }
	    } else {
	        return null;
	    }
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		// TODO Auto-generated method stub
		int stacksize = stack.getMaxStackSize();
		if (index < 0 || index >= this.getSizeInventory())
	        return;

	    if (stack != null && stack.getMaxStackSize() > this.getInventoryStackLimit())
	    	
	    	stacksize = this.getInventoryStackLimit();
	        
	    if (stack != null && stack.getMaxStackSize() == 0)
	        stack = null;

	    this.inventoryContents.set(index, stack);
		//this.getUpdateTag();
	    this.markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void clear() {
		for (int i = 0; i <this.getSizeInventory (); i ++) 
	        this.setInventorySlotContents (i, null); 
	}


}
