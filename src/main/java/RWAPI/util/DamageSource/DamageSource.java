package RWAPI.util.DamageSource;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.BaseEvent;
import RWAPI.game.event.EntityDeathEventHandle;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.main;
import RWAPI.util.EntityStatus;
import RWAPI.util.GameStatus;
import RWAPI.util.NetworkUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public class DamageSource {
	
	private EntityData attacker;
	private EntityData target;
	
	private double damage;

	public static DamageSource causeAttackFixed(EntityData attacker, EntityData target,double damage) {
		return new AttackFixedDamageSource(attacker,target,damage);
	}
	public static DamageSource causeAttackMagic(EntityData attacker, EntityData target,double damage) {
		return new AttackMagicDamageSource(attacker,target,damage);
	}
	public static DamageSource causeAttackPhysics(EntityData attacker, EntityData target,double damage) {
		return new AttackPhysicsDamageSource(attacker,target,damage);
	}

	public static DamageSource causeUnknownFixed(EntityData attacker, EntityData target,double damage) {
		return new UnknownFixedDamageSource(attacker,target,damage);
	}
	public static DamageSource causeUnknownMagic(EntityData attacker, EntityData target,double damage) {
		return new UnknownMagicDamageSource(attacker,target,damage);
	}
	public static DamageSource causeUnknownPhysics(EntityData attacker, EntityData target,double damage) {
		return new UnknownPhysicsDamageSource(attacker,target,damage);
	}

	public static DamageSource causeSkillFixed(EntityData attacker, EntityData target,double damage) {
		return new SkillFixedDamageSource(attacker,target,damage);
	}
	public static DamageSource causeSkillMagic(EntityData attacker, EntityData target,double damage) {
		return new SkillMagicDamageSource(attacker,target,damage);
	}
	public static DamageSource causeSkillPhysics(EntityData attacker, EntityData target,double damage) {
		return new SkillPhysicsDamageSource(attacker,target,damage);
	}
	
	public static void attackDamage(DamageSource source, boolean attackeventFlag) {
		if(main.game.start == GameStatus.PRESTART)
			return;
		
		EntityData attacker = source.attacker;
		EntityData target = source.target;
		double damage = source.getDamage();

		if(attacker.getStatus() != EntityStatus.ALIVE && target.getStatus() != EntityStatus.ALIVE){
			return;
		}

		if(target.getgodmod()){
			return;
		}

		double hp = target.getCurrentHealth();
		if(hp <= 0)
			return;
		hp -= damage;
		target.setCurrentHealth(hp);

		if(attackeventFlag == true)
			AttackEvent(source);

		if(!(source instanceof DamageSource.UnknownDamage) && target instanceof PlayerData && attacker instanceof PlayerData){
			source.knockBack(((PlayerData) target).getPlayer(),((PlayerData) attacker).getPlayer(),0.2f, -((PlayerData) attacker).getPlayer().getLookVec().x, -((PlayerData) attacker).getPlayer().getLookVec().z);
		}
		
		if(target.getCurrentHealth() <= 0) {
			target.setStatus(EntityStatus.DEATH);
			target.setCurrentHealth(0);
			DeathEvent(source);
			if(attacker instanceof PlayerData) {
				((PlayerData) attacker).setGold((int) target.getDeattGold() + ((PlayerData) attacker).getGold());
				((PlayerData) attacker).setExp(target.getDeathExp() + ((PlayerData) attacker).getExp());
				System.out.println("source : " + source);
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
				else{
					//System.out.println("미니언 처치 골드 : "+ (15 * (main.game.gettimer()/2400)));
					((PlayerData)attacker).setGold(((PlayerData)attacker).getGold()+(15 * (main.game.gettimer()/1200))) ;
					((PlayerData)attacker).setCs(((PlayerData)attacker).getCs() + 1, 0.03) ;
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
	}

	public void knockBack(EntityLivingBase target, Entity entityIn, float strength, double xRatio, double zRatio){

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
	}

	public double getDamage(){
		return this.damage;
	}

	private static void AttackEvent(DamageSource source){
		PlayerAttackEventHandle.PlayerAttackEvent event = new PlayerAttackEventHandle.PlayerAttackEvent(source);
		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event,priority);
		}

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

	public static class PhysicDamageSource extends DamageSource{

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

	public static class MagicDamageSource extends DamageSource{

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

	public static class FixedDamageSource extends DamageSource{
		public FixedDamageSource(EntityData attacker, EntityData target, double damage) {
			super(attacker, target, damage);
		}

		@Override
		public double getDamage() {
			return super.getDamage();
		}
	}

	public interface SkillDamage{

	}

	public interface AttackDamage{

	}

	public interface UnknownDamage{

	}
}
