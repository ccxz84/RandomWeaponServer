package RWAPI.util;

import RWAPI.Character.EntityData;

public class SkillDamageSource extends DamageSource{
	public SkillDamageSource(EntityData attacker, EntityData target, double skilldamage) {
		super(attacker,target,skilldamage);
	}
}
