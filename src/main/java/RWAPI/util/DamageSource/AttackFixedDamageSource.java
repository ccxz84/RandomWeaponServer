package RWAPI.util.DamageSource;

import RWAPI.Character.EntityData;

public class AttackFixedDamageSource extends DamageSource.FixedDamageSource implements DamageSource.AttackDamage {
    public AttackFixedDamageSource(EntityData attacker, EntityData target, double damage) {
        super(attacker, target, damage);
    }
}
