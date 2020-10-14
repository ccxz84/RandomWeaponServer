package RWAPI.util.DamageSource;

import RWAPI.Character.EntityData;

public class AttackPhysicsDamageSource extends DamageSource.PhysicDamageSource implements DamageSource.AttackDamage{
    public AttackPhysicsDamageSource(EntityData attacker, EntityData target, double damage) {
        super(attacker, target, damage);
    }
}
