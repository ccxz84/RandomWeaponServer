package RWAPI.util;

import RWAPI.Character.EntityData;

public class SkillDamageSource extends DamageSource{
	public SkillDamageSource(EntityData attacker, EntityData target, float skilldamage) {
		super(attacker,target,skilldamage);
	}
}
