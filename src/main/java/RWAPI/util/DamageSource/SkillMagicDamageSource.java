package RWAPI.util.DamageSource;

import RWAPI.Character.EntityData;

public class SkillMagicDamageSource extends DamageSource.MagicDamageSource implements DamageSource.SkillDamage{

    public SkillMagicDamageSource(EntityData attacker, EntityData target, double skilldamage) {
        super(attacker,target,skilldamage);
    }
}
