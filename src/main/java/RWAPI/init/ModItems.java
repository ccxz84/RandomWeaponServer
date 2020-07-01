package RWAPI.init;

import java.util.ArrayList;
import java.util.List;

import RWAPI.items.Ruby;
import RWAPI.items.gameItem.Bilgewatercutlass;
import RWAPI.items.gameItem.Bladeoftheruinedking;
import RWAPI.items.gameItem.Dagger;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.items.gameItem.LongSword;
import RWAPI.items.gameItem.Recurvebow;
import RWAPI.items.gameItem.VampiricScepter;
import net.minecraft.item.Item;

public class ModItems {
	public static final List<Item> ITEMS = new ArrayList<Item>();
	public static final List<ItemBase> temp = new ArrayList<ItemBase>();
	
	
	public static final ItemBase RUBY = new Ruby("ruby");
	public static final ItemBase LongSword = new LongSword("longsword");
	public static final ItemBase Dagger = new Dagger("dagger");
	public static final ItemBase Recurvebow = new Recurvebow("recurvebow");
	public static final ItemBase VampiricScepter = new VampiricScepter("vampiricscepter");
	public static final ItemBase Bilgewatercutlass = new Bilgewatercutlass("bilgewatercutlass");
	public static final ItemBase Bladeoftheruinedking = new Bladeoftheruinedking("bladeoftheruinedking");
}
