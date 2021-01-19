package RWAPI.util.DamageSource;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.monster.entity.AbstractObject;
import RWAPI.game.event.BaseEvent;
import RWAPI.game.event.EntityDeathEventHandle;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.main;
import RWAPI.util.EntityStatus;
import RWAPI.util.GameStatus;
import RWAPI.util.NetworkUtil;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public class DamageSource {
	
	private EntityData attacker;
	private EntityData target;
	
	private double damage;

	public static DamageSource causeAttackMeleeFixed(EntityData attacker, EntityData target,double damage) {
		return new AttackFixedMeleeDamageSource(attacker,target,damage);
	}
	public static DamageSource causeAttackMeleeMagic(EntityData attacker, EntityData target,double damage) {
		return new AttackMagicMeleeDamageSource(attacker,target,damage);
	}
	public static DamageSource causeAttackMeleePhysics(EntityData attacker, EntityData target,double damage) {
		return new AttackPhysicsMeleeDamageSource(attacker,target,damage);
	}

	public static DamageSource causeUnknownMeleeFixed(EntityData attacker, EntityData target,double damage) {
		return new UnknownFixedMeleeDamageSource(attacker,target,damage);
	}
	public static DamageSource causeUnknownMeleeMagic(EntityData attacker, EntityData target,double damage) {
		return new UnknownMagicMeleeDamageSource(attacker,target,damage);
	}
	public static DamageSource causeUnknownMeleePhysics(EntityData attacker, EntityData target,double damage) {
		return new UnknownPhysicsMeleeDamageSource(attacker,target,damage);
	}

	public static DamageSource causeSkillMeleeFixed(EntityData attacker, EntityData target,double damage) {
		return new SkillFixedMeleeDamageSource(attacker,target,damage);
	}
	public static DamageSource causeSkillMeleeMagic(EntityData attacker, EntityData target,double damage) {
		return new SkillMagicMeleeDamageSource(attacker,target,damage);
	}
	public static DamageSource causeSkillMeleePhysics(EntityData attacker, EntityData target,double damage) {
		return new SkillPhysicsMeleeDamageSource(attacker,target,damage);
	}

	public static DamageSource causeAttackRangedFixed(EntityData attacker, EntityData target,double damage) {
		return new AttackFixedMeleeDamageSource(attacker,target,damage);
	}
	public static DamageSource causeAttackRangedMagic(EntityData attacker, EntityData target,double damage) {
		return new AttackMagicMeleeDamageSource(attacker,target,damage);
	}
	public static DamageSource causeAttackRangedPhysics(EntityData attacker, EntityData target,double damage) {
		return new AttackPhysicsMeleeDamageSource(attacker,target,damage);
	}

	public static DamageSource causeUnknownRangedFixed(EntityData attacker, EntityData target,double damage) {
		return new UnknownFixedMeleeDamageSource(attacker,target,damage);
	}
	public static DamageSource causeUnknownRangedMagic(EntityData attacker, EntityData target,double damage) {
		return new UnknownMagicMeleeDamageSource(attacker,target,damage);
	}
	public static DamageSource causeUnknownRangedPhysics(EntityData attacker, EntityData target,double damage) {
		return new UnknownPhysicsMeleeDamageSource(attacker,target,damage);
	}

	public static DamageSource causeSkillRangedFixed(EntityData attacker, EntityData target,double damage) {
		return new SkillFixedMeleeDamageSource(attacker,target,damage);
	}
	public static DamageSource causeSkillRangedMagic(EntityData attacker, EntityData target,double damage) {
		return new SkillMagicMeleeDamageSource(attacker,target,damage);
	}
	public static DamageSource causeSkillRangedPhysics(EntityData attacker, EntityData target,double damage) {
		return new SkillPhysicsMeleeDamageSource(attacker,target,damage);
	}

	public static void attackDamage(DamageSource source, boolean attackeventFlag)  {
		try{
			if(main.game.start == GameStatus.PRESTART)
				return;

			EntityData attacker = source.attacker;
			EntityData target = source.target;
			double damage = source.getDamage();
			target.getLock().acquire();

			if(attacker.getStatus() != EntityStatus.ALIVE || target.getStatus() != EntityStatus.ALIVE){
				target.getLock().release();
				return;
			}

			if(target.getgodmod()){
				target.getLock().release();
				return;
			}

			double hp = target.getCurrentHealth();
			if(hp <= 0){
				target.getLock().release();
				return;
			}


			for(EntityData.shield shield : target.getShieldList()){
				if(damage <= 0)
					break;
				if(shield.getShield() > 0){
					double amount = shield.getShield();
					double sub = amount - damage > 0 ? amount - damage : 0;
					damage = damage - amount > 0 ? damage - amount : 0;
					amount = sub > 0 ? sub : 0;
					target.setShield(shield,amount);
				}
			}

			hp -= damage;
			target.setCurrentHealth(hp);

			if(attackeventFlag == true)
				AttackEvent(source);

			if(target.getCurrentHealth() <= 0) {
				DeathEvent(source);
				if(attacker instanceof PlayerData) {
					((PlayerData) attacker).setGold((int) target.getDeattGold() + ((PlayerData) attacker).getGold());
					((PlayerData) attacker).setExp(target.getDeathExp() + ((PlayerData) attacker).getExp());
					if(target instanceof PlayerData){
						((PlayerData)attacker).setKill(((PlayerData)attacker).getKill()+1);
						((PlayerData)target).setDeath(((PlayerData)target).getDeath()+1);
						((PlayerData) attacker).setContinuouskill(((PlayerData) attacker).getContinuouskill() + 1);
						if(((PlayerData) target).getContinuouskill() > 1){
							main.game.server.getPlayerList().sendMessage(new TextComponentString((((PlayerData) attacker).getContinuouskill() > 1 ? ((PlayerData) attacker).getContinuouskill() +"연속 킬 !!! ": "")+(attacker.getName() + "이(가) " + target.getName() +"의 연속 "+ ((PlayerData) target).getContinuouskill()+"킬을 저지하였습니다.")
									+ " 추가 골드, 경험치 " + ((PlayerData) target).getContinuouskill() * 50));
						}
						else{
							main.game.server.getPlayerList().sendMessage(new TextComponentString((((PlayerData) attacker).getContinuouskill() > 1 ? ((PlayerData) attacker).getContinuouskill() +"연속 킬 !!! ": "")
									+attacker.getName() + "이(가) " + target.getName() +"을(를) 처치하였습니다."));
						}
						((PlayerData) attacker).setGold(((PlayerData) attacker).getGold() + ((PlayerData) target).getContinuouskill() * 50);
						((PlayerData) attacker).setExp(((PlayerData) attacker).getExp() + ((PlayerData) target).getContinuouskill() * 50);
						((PlayerData) target).setContinuouskill(0);
					}
					else if(target.getEntity() instanceof AbstractObject){
						System.out.println(target.getCurrentHealth());
						main.game.server.getPlayerList().sendMessage(new TextComponentString((((PlayerData) attacker).getContinuouskill() > 1 ? ((PlayerData) attacker).getContinuouskill() +"연속 킬 !!! ": "")
								+attacker.getName() + "이(가) " + target.getName() +"을(를) 처치하였습니다."));
						//버프설정
						((AbstractObject) target.getEntity()).setBuff((PlayerData) attacker);
						target.getEntity().setDead();
						main.game.resetObjectTimer();
						System.out.println(target.getCurrentHealth());
					}
					else{
						((PlayerData)attacker).setCs(((PlayerData)attacker).getCs() + 1, target.getKill_cs()) ;
					}
					if(((PlayerData) attacker).getKill() >= 15){
						main.game.endgame();
					}
				}
				if(target instanceof PlayerData) {
					Playerdeath(((PlayerData) target));
					((PlayerData) target).setRespawn();

				}
			}
			target.getLock().release();
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
	}

	/*public void knockBack(EntityLivingBase target, Entity entityIn, float strength, double xRatio, double zRatio){

		target.maxHurtResistantTime = 0;
		target.hurtResistantTime = 0;
		net.minecraftforge.event.entity.living.LivingKnockBackEvent event = net.minecraftforge.common.ForgeHooks.onLivingKnockBack(target, entityIn, strength, xRatio, zRatio);
		if(event.isCanceled()) return;
		strength = event.getStrength(); xRatio = event.getRatioX(); zRatio = event.getRatioZ();
		target.isAirBorne = true;
		target.velocityChanged = true;
		float f = MathHelper.sqrt(xRatio * xRatio + zRatio * zRatio);
		target.motionX /= 2.0D;
		target.motionZ /= 2.0D;
		target.motionX -= xRatio / (double)f * (double)strength;
		target.motionZ -= zRatio / (double)f * (double)strength;

		if (target.onGround)
		{
			target.motionY /= 2.0D;
			target.motionY += (double)strength;

			if (target.motionY > 0.4000000059604645D)
			{
				target.motionY = 0.4000000059604645D;
			}
		}
		if(target instanceof EntityPlayerMP){
			((EntityPlayerMP)target).connection.sendPacket(new SPacketEntityVelocity(target));
		}
		target.velocityChanged = false;
	}*/

	public double getDamage(){
		return this.damage;
	}

	private static void AttackEvent(DamageSource source){
		new Thread(){
			@Override
			public void run() {
				PlayerAttackEventHandle.PlayerAttackEvent event = new PlayerAttackEventHandle.PlayerAttackEvent(source);
				for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
					main.game.getEventHandler().RunEvent(event,priority);
				}
			}
		}.start();
	}

	private static void DeathEvent(DamageSource source) {
		EntityDeathEventHandle.EntityDeathEvent event = new EntityDeathEventHandle.EntityDeathEvent(source);
		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event,priority);
		}
	}

	private static void Playerdeath(PlayerData target) {

	}

	public DamageSource(EntityData attacker, EntityData target, double damage) {
		this.attacker = attacker;
		this.target = target;
		this.damage = damage;
	}
	
	public EntityData getAttacker() {
		return this.attacker;
	}
	
	public EntityData getTarget() {
		return this.target;
	}
	
	
	
	public static class EnemyStatHandler{
		
		final int Maxtimer = 200;
		int timer = 0;
		EntityData entity;
		EntityData target;
		
		DamageSource source;

		boolean end = false;
		
		public EnemyStatHandler(DamageSource source, EntityData entity, EntityData target) {
			this.source = source;
			this.entity = entity;
			this.target = target;
			MinecraftForge.EVENT_BUS.register(this);
		}
		
		@SubscribeEvent
		public void EnemyStat(ServerTickEvent event) {
			if(timer >= Maxtimer) {
				reset();
				source.getAttacker().setEnemyHandler(null);
				source.getTarget().setEnemyHandler(null);
				end = true;
				MinecraftForge.EVENT_BUS.unregister(this);
			}

			if(source.attacker.getCurrentHealth() <= 0 || source.target.getCurrentHealth() <= 0){
				reset();
				source.getAttacker().setEnemyHandler(null);
				source.getTarget().setEnemyHandler(null);
				end = true;
				MinecraftForge.EVENT_BUS.unregister(this);
			}
			if(end == false)
				setEnemystat();
			timer++;
			
		}

		private void reset() {
			if(entity instanceof PlayerData){
				NetworkUtil.sendTo(((PlayerData) entity).getPlayer(),null,"enemy");
			}
		}

		private void setEnemystat(){
			if(entity instanceof PlayerData){
				NetworkUtil.sendTo(((PlayerData) entity).getPlayer(),target.getData(),"enemy");
			}
		}
		
		public void DataChange(DamageSource source) {
			this.source = source;
			if(this.entity.equals(source.attacker))
				this.target = source.target;
			else
				this.target = source.attacker;
			timer = 0;
		}

		public static void EnemyStatSetter(DamageSource source) {
			if(source.attacker.getEnemyHandler() != null) {
				source.attacker.getEnemyHandler().DataChange(source);
			}
			else {
				source.attacker.setEnemyHandler(new EnemyStatHandler(source,source.attacker,source.target));
			}

			if(source.target.getEnemyHandler() != null) {
				source.target.getEnemyHandler().DataChange(source);
			}
			else{
				source.target.setEnemyHandler(new EnemyStatHandler(source,source.target,source.attacker));
			}


		}
	}

	public static abstract class PhysicDamageSource extends DamageSource implements Damage{

		public PhysicDamageSource(EntityData attacker, EntityData target, double damage) {
			super(attacker, target, damage);
		}

		@Override
		public double getDamage() {
			double armor = ((getTarget().getArmor() / 100)*(100-getAttacker().getArmorpenetrationper())) - getAttacker().getArmorpenetration() <= 0
					? 0 : ((getTarget().getArmor() / 100)*(100-getAttacker().getArmorpenetrationper())) - getAttacker().getArmorpenetration();
			double damage = super.getDamage() - super.getDamage()*(armor / (armor + 100));
			return damage;
		}
	}

	public static abstract class MagicDamageSource extends DamageSource implements Damage{

		public MagicDamageSource(EntityData attacker, EntityData target, double damage) {
			super(attacker, target, damage);
		}

		@Override
		public double getDamage() {
			double Magicresistance = ((getTarget().getMagicresistance() / 100)*(100-getAttacker().getMagicpenetrationper())) - getAttacker().getMagicpenetration() <= 0
					? 0 : ((getTarget().getMagicresistance() / 100)*(100-getAttacker().getMagicpenetrationper())) - getAttacker().getMagicpenetration();
			double damage = super.getDamage() - super.getDamage()*(Magicresistance / (Magicresistance + 100));
			return damage;
		}
	}

	public static abstract class FixedDamageSource extends DamageSource implements Damage{
		public FixedDamageSource(EntityData attacker, EntityData target, double damage) {
			super(attacker, target, damage);
		}

		@Override
		public double getDamage() {
			return super.getDamage();
		}
	}

	public interface Damage{
		DamageType getDamageType();
		AttackType getAttackType();
		boolean isRanged();
	}

	public enum DamageType{
		PHYSICS,
		MAGIC,
		FIXED
	}

	public enum AttackType{
		ATTACK,
		SKILL,
		UNKNOWN
	}
}
