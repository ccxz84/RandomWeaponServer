package RWAPI.init;

import java.util.ArrayList;
import java.util.List;

import RWAPI.items.skillItem.SkillBase;
import RWAPI.util.ClassList;
import net.minecraft.item.Item;

public class ModSkills {
	public static final List<Item> skill = new ArrayList<Item>();
	
	public static final SkillBase flurry = new SkillBase("leesin","flurry",0);
	public static final SkillBase sonicwave = new SkillBase("leesin","sonicwave",1);
	public static final SkillBase resonatingstrike = new SkillBase("leesin","resonatingstrike",2);
	public static final SkillBase safeguard = new SkillBase("leesin","safeguard",3);
	public static final SkillBase tempest = new SkillBase("leesin","tempest",4);
	public static final SkillBase dragonsrage = new SkillBase("leesin","dragonsrage",5);
	
	public static final SkillBase doublestrike = new SkillBase("masteryi","doublestrike",6);
	public static final SkillBase alphastrike = new SkillBase("masteryi","alphastrike",7);
	public static final SkillBase meditation = new SkillBase("masteryi","meditation",8);
	public static final SkillBase wujustyle = new SkillBase("masteryi","wujustyle",9);
	public static final SkillBase highlander = new SkillBase("masteryi","highlander",10);

	public static final SkillBase blazingpalm = new SkillBase("forcemaster","blazingpalm",11);
	public static final SkillBase frostpalm = new SkillBase("forcemaster","frostpalm",12);
	public static final SkillBase firestorm = new SkillBase("forcemaster","firestorm",13);
	public static final SkillBase icecoil = new SkillBase("forcemaster","icecoil",14);
	public static final SkillBase blazingbeam = new SkillBase("forcemaster","blazingbeam",15);
	public static final SkillBase snowball = new SkillBase("forcemaster","snowball",16);
	public static final SkillBase heatwave = new SkillBase("forcemaster","heatwave",17);
	public static final SkillBase heatwave2 = new SkillBase("forcemaster","heatwave2",18);
	public static final SkillBase icerain = new SkillBase("forcemaster","icerain",19);
	public static final SkillBase inferno = new SkillBase("forcemaster","inferno",20);
	public static final SkillBase frostarmor = new SkillBase("forcemaster","frostarmor",21);
	public static final SkillBase frostarmor2 = new SkillBase("forcemaster","frostarmor2",22);
}
