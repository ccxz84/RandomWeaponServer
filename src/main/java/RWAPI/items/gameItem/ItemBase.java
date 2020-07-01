package RWAPI.items.gameItem;

import java.util.ArrayList;
import java.util.List;

import RWAPI.main;
import net.minecraft.item.Item;

public class ItemBase extends Item{
	public ItemBase[] down_item;
	public int phase;
	
	public ItemBase(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		this.maxStackSize = 1;
	}

}
