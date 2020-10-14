package RWAPI.util.DamageSource;

import RWAPI.Character.EntityData;

public class UnknownMagicDamageSource extends DamageSource.MagicDamageSource implements DamageSource.UnknownDamage{
    public UnknownMagicDamageSource(EntityData attacker, EntityData target, double damage) {
        super(attacker, target, damage);
    }
}
