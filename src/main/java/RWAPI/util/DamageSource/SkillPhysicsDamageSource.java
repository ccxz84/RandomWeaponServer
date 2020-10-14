package RWAPI.util.DamageSource;

import RWAPI.Character.EntityData;

public class SkillPhysicsDamageSource extends DamageSource.PhysicDamageSource implements DamageSource.SkillDamage{
    public SkillPhysicsDamageSource(EntityData attacker, EntityData target, double skilldamage) {
        super(attacker,target,skilldamage);
    }
}
