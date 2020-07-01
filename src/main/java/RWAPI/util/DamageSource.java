package RWAPI.util;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public class DamageSource {
	
	private EntityData attacker;
	private EntityData target;
	
	private float damage;

	public static DamageSource causeAttack(EntityData attacker, EntityData target) {
		return new AttackDamageSource(attacker,target);
	}
	
	public static DamageSource causeSkill(EntityData attacker, EntityData target, float skillDamage) {
		return new SkillDamageSource(attacker,target, skillDamage);
	}
	
	public static void attackDamage(DamageSource source) {
		
		EntityData attacker = source.attacker;
		EntityData target = source.target;
		float damage = source.damage;
		
		float hp = target.getCurrentHealth();
		hp -= damage;
		target.setCurrentHealth(hp);
		
		if(target.getCurrentHealth() <= 0) {
			if(attacker instanceof PlayerData) {
				((PlayerData) attacker).setGold((int) target.getDeattGold() + ((PlayerData) attacker).getGold());
				((PlayerData) attacker).setExp(target.getDeathExp() + ((PlayerData) attacker).getExp());
				target.setCurrentHealth(0);
			}
			if(target instanceof PlayerData) {
				((PlayerData) target).getPlayer().connection.setPlayerLocation(-56,53,107, ((PlayerData) target).getPlayer().rotationYaw, ((PlayerData) target).getPlayer().rotationPitch);
			}
			target.setCurrentHealth(0);
		}
	}
	
	public DamageSource(EntityData attacker, EntityData target, float damage) {
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
		
		DamageSource source;
		
		public EnemyStatHandler(DamageSource source) {
			this.source = source;
			MinecraftForge.EVENT_BUS.register(this);
		}
		
		@SubscribeEvent
		public void EnemyStat(ServerTickEvent event) {
			if(timer >= Maxtimer) {
				source.getAttacker().setEnemyHandler(null);
				source.getTarget().setEnemyHandler(null);
				MinecraftForge.EVENT_BUS.unregister(this);
			}
			timer++;
			
		}
		
		public void DataChange(DamageSource source) {
			this.source = source;
			timer = 0;
		}
		
		public static void EnemyStatSetter(DamageSource source) {
			if(source.attacker.getEnemyHandler() != null) {
				source.attacker.getEnemyHandler().DataChange(source);
			}
			else {
				source.attacker.setEnemyHandler(new EnemyStatHandler(source));
			}
			
			if(source.target.getEnemyHandler() != null) {
				source.target.getEnemyHandler().DataChange(source);
			}
			else{
				source.target.setEnemyHandler(new EnemyStatHandler(source));
			}
			
			
		}
		
		public EntityData getEnemyData(EntityData data) {
			if(source.attacker.equals(data)) {
				return source.target;
			}
			if(source.target.equals(data)) {
				return source.attacker;
			}
			return null;
		}
	}
}
