package RWAPI.Character.ai;

import RWAPI.Character.monster.entity.EntityMinion;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayerMP;

public class PlayerAIHurtByTarget extends EntityAITarget{
	
	private EntityLiving entity;

	public PlayerAIHurtByTarget(EntityLiving creature, boolean checkSight) {
		super((EntityCreature) creature, checkSight);
		this.entity = creature;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean shouldExecute() {
		// TODO Auto-generated method stub
		
		if(entity.getAttackingEntity() instanceof EntityPlayerMP) {
			entity.setAttackTarget(entity.getAttackingEntity());
		}
		if(entity.getAttackingEntity() == null) {
			entity.setAttackTarget(null);
		}
		return false;
	}

}
