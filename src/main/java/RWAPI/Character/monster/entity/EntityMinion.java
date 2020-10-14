package RWAPI.Character.monster.entity;

import RWAPI.main;
import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.ai.PlayerAIHurtByTarget;
import RWAPI.Character.ai.PlayerAIZombieAttack;
import RWAPI.util.DamageSource.DamageSource.EnemyStatHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.world.World;

public class EntityMinion extends AbstractMob{
	
	
	private static final DataParameter<Boolean> ARMS_RAISED = EntityDataManager.<Boolean>createKey(EntityMinion.class, DataSerializers.BOOLEAN);

	public EntityMinion(World worldIn) {
		super(worldIn,new EntityData(900f,10f,10f,110f,50,70,"미니언",0.03));
		// TODO Auto-generated constructor stub
	}
	
	protected void entityInit()
    {
        super.entityInit();
        this.getDataManager().register(ARMS_RAISED, Boolean.valueOf(false));
    }
	
	

	protected void initEntityAI()
    {
        this.tasks.addTask(2, new PlayerAIZombieAttack(this, 1.0D, false));
        //this.tasks.addTask(1, new EntityAISwimming(this));
        //this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
        //this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        //this.tasks.addTask(6, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
    	
        this.targetTasks.addTask(2, new PlayerAIHurtByTarget(this, true));
    }
	
	@Override
	public boolean getCanSpawnHere() {
		//System.out.println("posx : " + posX + " posZ  " + posZ);
		if((posX > -295 && posX < -208) && (posZ < 93 && posZ > -186)) {
			return true;
		}
		
		if((posX > -222 && posX < -207) && (posZ < 93 && posZ > -172)) {
			return true;
		}
		
		if((posX > -214 && posX < -59) && (posZ < 103 && posZ > -335)) {
			return true;
		}
		
		if((posX > -58 && posX < 1) && (posZ < 103 && posZ > -210)) {
			return true;
		}
		
		if((posX > 2 && posX < 62) && (posZ < 103 && posZ > -182)) {
			return true;
		}
		
		if((posX > 63 && posX < 83) && (posZ < -99 && posZ > -208)) {
			return true;
		}
		
		if((posX > 63 && posX < 83) && (posZ < -99 && posZ > -208)) {
			return true;
		}
		
		if((posX > 84 && posX < 91) && (posZ < 103 && posZ > -98)) {
			return true;
		}
		
		if((posX > 84 && posX < 100 ) && (posZ < -135  && posZ > -208)) {
			return true;
		}
		if((posX > 101 && posX < 143) && (posZ < -135  && posZ > -177)) {
			return true;
		}
		if((posX > 144	 && posX < 176 ) && (posZ < -135 && posZ > -157)) {
			return true;
		}
		
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3d);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.1d);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1024d);
    }

	public void setArmsRaised(boolean b) {
		// TODO Auto-generated method stub
		 this.getDataManager().set(ARMS_RAISED, Boolean.valueOf(b));
	}

	@Override
	protected boolean isValidLightLevel() {
		// TODO Auto-generated method stub
		return true;
		
	}

	@Override
	public void onUpdate() {
		// TODO Auto-generated method stub
		if(data.getCurrentHealth() <= 0) {
			this.setDead();
		}
		super.onUpdate();
	}
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		// TODO Auto-generated method stub
		if(!(source instanceof EntityDamageSourceIndirect)) {
			if(source.getTrueSource() instanceof EntityPlayer) {

				PlayerData attacker = main.game.getPlayerData(source.getTrueSource().getUniqueID());
				RWAPI.util.DamageSource.DamageSource sourcee = RWAPI.util.DamageSource.DamageSource.causeAttackPhysics(attacker, data,attacker.getAd());
				RWAPI.util.DamageSource.DamageSource.attackDamage(sourcee,true);
				EnemyStatHandler.EnemyStatSetter(sourcee);
			}
		}
		
		return super.attackEntityFrom(source, amount);
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		// TODO Auto-generated method stub
		if(entityIn instanceof EntityPlayer) {
			PlayerData target = main.game.getPlayerData(entityIn.getUniqueID());
			RWAPI.util.DamageSource.DamageSource source = RWAPI.util.DamageSource.DamageSource.causeAttackPhysics(data, target,data.getAd());
			RWAPI.util.DamageSource.DamageSource.attackDamage(source,true);
			EnemyStatHandler.EnemyStatSetter(source);
		}
		
		return super.attackEntityAsMob(entityIn);
	}
	
	
	
}
