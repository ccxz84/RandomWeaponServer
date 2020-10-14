package RWAPI.util.DamageSource;

import RWAPI.Character.EntityData;

public class UnknownFixedDamageSource extends DamageSource.FixedDamageSource implements DamageSource.UnknownDamage{
    public UnknownFixedDamageSource(EntityData attacker, EntityData target, double damage) {
        super(attacker, target, damage);
    }
}
