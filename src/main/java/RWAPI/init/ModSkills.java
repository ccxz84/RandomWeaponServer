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
}
