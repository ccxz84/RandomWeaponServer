package RWAPI.items.skillItem;

import java.util.ArrayList;
import java.util.List;

import RWAPI.main;
import RWAPI.init.ModSkills;
import RWAPI.util.ClassList;
import RWAPI.util.Reference;
import net.minecraft.item.Item;

public class SkillBase extends Item{
	public int SkillNumber;
	
	public SkillBase(String name, int SkillNumber) {
		setUnlocalizedName(name);
		setRegistryName(name);
		this.maxStackSize = 1;
		this.SkillNumber = SkillNumber;
		ModSkills.skill.add(this);
	}

}
