package RWAPI.util.DamageSource;

import RWAPI.Character.EntityData;

public class UnknownPhysicsDamageSource extends DamageSource.PhysicDamageSource implements DamageSource.UnknownDamage{
    public UnknownPhysicsDamageSource(EntityData attacker, EntityData target, double damage) {
        super(attacker, target, damage);
    }
}
