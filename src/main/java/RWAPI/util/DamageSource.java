package RWAPI.util;

import RWAPI.Character.ClientData;
import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.monster.entity.AbstractMob;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.main;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class DamageSource {
	
	private EntityData attacker;
	private EntityData target;
	
	private double damage;

	public static DamageSource causeAttack(EntityData attacker, EntityData target) {
		return new AttackDamageSource(attacker,target);
	}
	
	public static DamageSource causeSkill(EntityData attacker, EntityData target, double skillDamage) {
		return new SkillDamageSource(attacker,target, skillDamage);
	}
	
	public static void attackDamage(DamageSource source, boolean eventFlag) {
		
		EntityData attacker = source.attacker;
		EntityData target = source.target;
		double damage = source.damage;

		double hp = target.getCurrentHealth();
		if(hp <= 0)
			return;
		hp -= damage;
		target.setCurrentHealth(hp);
		if(eventFlag == true)
			Event(source);
		
		if(target.getCurrentHealth() <= 0) {
			target.setStatus(EntityStatus.DEATH);
			target.setCurrentHealth(0);
			if(attacker instanceof PlayerData) {
				((PlayerData) attacker).setGold((int) target.getDeattGold() + ((PlayerData) attacker).getGold());
				((PlayerData) attacker).setExp(target.getDeathExp() + ((PlayerData) attacker).getExp());
				if(target instanceof PlayerData){
					((PlayerData)attacker).setKill(((PlayerData)attacker).getKill()+1);
					((PlayerData)target).setDeath(((PlayerData)target).getDeath()+1);
				}
				else{
					((PlayerData)attacker).setCs((int) (((PlayerData)attacker).getCs()+1),target.getKill_cs()) ;
				}
			}
			if(target instanceof PlayerData) {
				Playerdeath(((PlayerData) target));
				((PlayerData) target).setRespawn();
			}
		}
	}

	private static void Event(DamageSource source){
		PlayerAttackEventHandle.PlayerAttackEvent event = new PlayerAttackEventHandle.PlayerAttackEvent(source);
		main.game.getEventHandler().RunEvent(event);
	}

	private static void Playerdeath(PlayerData target) {
		EntityPlayerMP player = target.getPlayer();

		for(int i = 1; i<9;i++) {
			ItemStack stack = player.inventory.getStackInSlot(i);
			player.dropItem(stack, true, false);
			player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
		}

		for(int i =18;i<36;i++){
			ItemStack stack = player.inventory.getStackInSlot(i);
			player.dropItem(stack, true, false);
			player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
		}

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
				/*ItemStack stack = ((PlayerData) entity).getPlayer().inventory.getStackInSlot(9);
				if(stack != null){
					NBTTagCompound nbt = stack.getTagCompound();
					if(nbt == null){
						nbt = new NBTTagCompound();
					}
					nbt.removeTag("enemy");
					stack.setTagCompound(nbt);
				}*/
			}
		}

		private void setEnemystat(){
			if(entity instanceof PlayerData){
				NetworkUtil.sendTo(((PlayerData) entity).getPlayer(),target.getData(),"enemy");
				/*byte[] bs;
				ItemStack stack = ((PlayerData) entity).getPlayer().inventory.getStackInSlot(9);
				ClientData data = target.getData();
				if(stack != null && data != null){
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					try {
						ObjectOutputStream oos = new ObjectOutputStream(bos);
						oos.writeObject(data);
						bs = bos.toByteArray();
						NBTTagCompound nbt = stack.getTagCompound();
						if(nbt == null){
							nbt = new NBTTagCompound();
						}
						nbt.setByteArray("enemy",bs);
						stack.setTagCompound(nbt);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}*/
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
}
