package RWAPI.init;

import java.util.ArrayList;
import java.util.List;

import RWAPI.items.weapon.WeaponBase;
import net.minecraft.item.Item;

public class ModWeapons {
public static final List<Item> weapon = new ArrayList<Item>();
	
	
	public static final WeaponBase leesin = new RWAPI.items.weapon.leesin("leesin");
	public static final WeaponBase masteryi = new RWAPI.items.weapon.masteryi("masteryi");
	public static final WeaponBase formaster = new RWAPI.items.weapon.formaster("formaster");
	public static final WeaponBase nasus = new RWAPI.items.weapon.nasus("nasus");
	public static final WeaponBase kassadin = new RWAPI.items.weapon.kassadin("kassadin");
}
