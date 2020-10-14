package RWAPI.util.DamageSource;

import RWAPI.Character.EntityData;

public class SkillFixedDamageSource extends DamageSource.FixedDamageSource implements DamageSource.SkillDamage{
    public SkillFixedDamageSource(EntityData attacker, EntityData target, double damage) {
        super(attacker, target, damage);
    }
}
