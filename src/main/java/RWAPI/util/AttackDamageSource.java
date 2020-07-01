package RWAPI.util;

import RWAPI.Character.EntityData;

public class AttackDamageSource extends DamageSource{
	
	public AttackDamageSource(EntityData attacker, EntityData target) {
		super(attacker,target,attacker.getAd());
	}
}
