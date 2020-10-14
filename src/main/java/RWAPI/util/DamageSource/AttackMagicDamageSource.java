package RWAPI.util.DamageSource;

import RWAPI.Character.EntityData;

public class AttackMagicDamageSource extends DamageSource.MagicDamageSource implements DamageSource.AttackDamage{
    public AttackMagicDamageSource(EntityData attacker, EntityData target, double damage) {
        super(attacker, target, damage);
    }
}
