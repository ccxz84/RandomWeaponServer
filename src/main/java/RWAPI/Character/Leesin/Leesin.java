package RWAPI.Character.Leesin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

import RWAPI.main;
import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerClass;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Leesin.entity.EntityTempest;
import RWAPI.Character.Leesin.entity.EntityResonating;
import RWAPI.Character.Leesin.entity.EntityStrike;
import RWAPI.Character.Leesin.entity.EntityUmpa;
import RWAPI.Character.monster.entity.AbstractMob;
import RWAPI.init.ModSkills;
import RWAPI.init.ModWeapons;
import RWAPI.items.skillItem.SkillBase;
import RWAPI.items.weapon.WeaponBase;
import RWAPI.items.weapon.leesin;
import RWAPI.util.ClassList;
import RWAPI.util.DamageSource.EnemyStatHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SPacketCustomPayload;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class Leesin extends PlayerClass{
	
	public EntityResonating resonating = null;
	public resonatingtimer timer = null; 
	private int attack = 0;
	
	//private float[] cool = new float[14];

	public Leesin(){
		default_health = 800;
		default_mana = 200;
		
		default_ad = 80;
		default_ap = 0;
		default_move = 107;
		
		default_regenHealth = 1.1f;
		default_regenMana = 5f;
		
		attackSpeed = 0.4f;
		
		class_code = ClassList.Leesin;
		
		ClassName = "리 신";
		
		super.weapon = ModWeapons.leesin;
			
		//matrix = new StatMatrix();
		matrix = new leesinMatrix();
		skillSet[0] = ModSkills.skill.get(ModSkills.flurry.SkillNumber);
		skillSet[1] = ModSkills.skill.get(ModSkills.sonicwave.SkillNumber);
		skillSet[2] = ModSkills.skill.get(ModSkills.safeguard.SkillNumber);
		skillSet[3] = ModSkills.skill.get(ModSkills.tempest.SkillNumber);
		skillSet[4] = ModSkills.skill.get(ModSkills.dragonsrage.SkillNumber);
		super.handler = new CooldownHandler[6];
	}
	
	public void skill0(EntityPlayer player) {
		PlayerData data = main.game.getPlayerData(player.getUniqueID());
		int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
		leesinMatrix matrix = (leesinMatrix) this.matrix;
		if(this.handler[0] == null) {
			this.attack = 2;
			this.handler[0] = new CooldownHandler(3, 0, (EntityPlayerMP) player) {
				double attackespeed;
				Leesin clazz;
				PlayerData data;
				
				public CooldownHandler setLeesin(Leesin _class, double attackspeed,PlayerData data) {
					this.clazz = _class;
					this.attackespeed = attackspeed;
					this.data = data;
					this.data.setAttackSpeed(this.data.getAttackSpeed() + (float)this.attackespeed);
					return this;
				}
				
				@SubscribeEvent
				public void skillTimer(ServerTickEvent event) throws Throwable {
					if(skillTimer > cooldown) {
						data.setCool(this.id, 0);
						this.data.setAttackSpeed(this.data.getAttackSpeed() - (float)this.attackespeed);
						clazz.attack = 0;
						clazz.handler[0] = null;
						MinecraftForge.EVENT_BUS.unregister(this);
						return;
					}
					//data.setCool(this.id, clazz.attack);//에러발생
					skillTimer++;
					
				}
				
				@SubscribeEvent
				public void PlayerAttackEvent(AttackEntityEvent event)
				{
					if(event.getEntityPlayer().getUniqueID().equals(player.getUniqueID())) {
						clazz.attack--;
						if(clazz.attack <= 0) {
							this.data.setAttackSpeed(this.data.getAttackSpeed() - (float)this.attackespeed);
							data.setCool(this.id, 0);
							clazz.handler[0] = null;
							MinecraftForge.EVENT_BUS.unregister(this);
						}
					}
				}
			}.setLeesin(this, matrix.skilldamage[0][lv-1], data);
		}else {
			this.attack = 2;
		}
		
	}
	
	@Override
	public void skill1(EntityPlayer player) {
		PlayerData data = main.game.getPlayerData(player.getUniqueID());
		int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
		leesinMatrix matrix = (leesinMatrix) this.matrix;
		if(data.getCool(1) <= 0 && data.getSkill(1).equals(ModSkills.skill.get(ModSkills.sonicwave.SkillNumber)) && data.getCurrentMana() > matrix.skillcost[1][lv-1]) {
			data.setCurrentMana((float) (data.getCurrentMana() - matrix.skillcost[1][lv-1]));
			EntityUmpa ls = new EntityUmpa(player.world,player,(float) (matrix.skilldamage[2][lv-1]+ matrix.skillAdcoe[1][lv-1] * data.getAd()));
			ls.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.7f, 0);
			ls.setNoGravity(true);
			player.world.spawnEntity(ls);
			
			this.handler[1] = new CooldownHandler(((leesinMatrix)matrix).cooldown[1][lv-1], 1, (EntityPlayerMP) player) {
				Leesin clazz;//에러발생
				public CooldownHandler setLeesin(Leesin _class) {
					this.clazz = _class;
					return this;
				}
				@Override
				public void skillTimer(ServerTickEvent event) throws Throwable {
					// TODO Auto-generated method stub
					super.skillTimer(event);
					if(clazz.resonating !=null) {
						data.setCool(this.id, 0);
						MinecraftForge.EVENT_BUS.unregister(this);
						data.setSkill(1, (SkillBase) ModSkills.skill.get(ModSkills.resonatingstrike.SkillNumber));
						clazz.timer = new resonatingtimer(3,1,player,clazz.resonating);
						clazz.resonating = null;
					}
				}
				
				
				
			}.setLeesin(this);
			skill0(player);
			
		}
		else if(data.getSkill(1).equals(ModSkills.skill.get(ModSkills.resonatingstrike.SkillNumber))&& data.getCurrentMana() > matrix.skillcost[2][lv-1]) {
			data.setCurrentMana((float) (data.getCurrentMana() - matrix.skillcost[2][lv-1]));
			if(timer.resonating.getThrower() instanceof EntityPlayer) {
				PlayerData target = main.game.getPlayerData(timer.resonating.getThrower().getUniqueID());
				RWAPI.util.DamageSource source = RWAPI.util.DamageSource.causeSkill(data, target, (float) (matrix.skilldamage[1][lv-1]+ matrix.skill1coe[0][lv-1] * data.getAd()
						+ matrix.skill1coe[1][lv-1] * (target.getMaxHealth() - target.getCurrentHealth())));
				RWAPI.util.DamageSource.attackDamage(source);
				EnemyStatHandler.EnemyStatSetter(source);
				((EntityPlayerMP) player).connection.setPlayerLocation(target.getPlayer().posX, target.getPlayer().posY, target.getPlayer().posZ, player.rotationYaw, player.rotationPitch);
				timer.resonating.getThrower().attackEntityFrom(DamageSource.causeThrownDamage(player, timer.resonating), (float)1);
			}else if(timer.resonating.getThrower() instanceof AbstractMob){
				AbstractMob mob = (AbstractMob) timer.resonating.getThrower();
				EntityData target = mob.getData();
				RWAPI.util.DamageSource source = RWAPI.util.DamageSource.causeSkill(data, target, (float) (matrix.skilldamage[1][lv-1]+ matrix.skill1coe[0][lv-1] * data.getAd()
						+ matrix.skill1coe[1][lv-1] * (target.getMaxHealth() - target.getCurrentHealth())));
				RWAPI.util.DamageSource.attackDamage(source);
				EnemyStatHandler.EnemyStatSetter(source);
				((EntityPlayerMP) player).connection.setPlayerLocation(mob.posX - player.getLookVec().x *1.1, mob.posY, mob.posZ - player.getLookVec().z *1.1, player.rotationYaw, player.rotationPitch);
			}
			
			timer.resonating.getThrower().attackEntityFrom(DamageSource.causeThrownDamage(player, timer.resonating), (float)1);
			timer.resonating.setDead();
			timer = null;
			data.setSkill(1, (SkillBase) ModSkills.skill.get(ModSkills.sonicwave.SkillNumber));
			this.handler[2] = new CooldownHandler(((leesinMatrix)matrix).cooldown[1][main.game.getPlayerData(player.getUniqueID()).getLevel()-1], 1, (EntityPlayerMP) player);
			skill0(player);
			if(timer != null) {
				MinecraftForge.EVENT_BUS.unregister(timer);// 에러발생
				timer = null;
			}
		}
		
	}
	
	@Override
	public void skill2(EntityPlayer player) {
		PlayerData data = main.game.getPlayerData(player.getUniqueID());
		int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
		leesinMatrix matrix = (leesinMatrix) this.matrix;
		if(data.getCool(2) <= 0 && data.getCurrentMana() > matrix.skillcost[3][lv-1]) {
			data.setCurrentMana((float) (data.getCurrentMana() - matrix.skillcost[3][lv-1]));
			this.handler[3] = new CooldownHandler(((leesinMatrix)matrix).cooldown[2][lv-1], 2, (EntityPlayerMP) player) {

				@Override
				public void skillTimer(ServerTickEvent event) throws Throwable {
					// TODO Auto-generated method stub
					if((this.skillTimer < 20)) {
						float x = (float) player.getLookVec().x;
						float y = (float) player.getLookVec().y;
						float z = (float) player.getLookVec().z;
						BlockPos pos = new BlockPos(player.posX + x * 0.5,player.posY + y * 0.5,player.posZ + z * 0.5);
						if(player.onGround && y < 0) {
							y=0;
						}
						if(!(player.world.getBlockState(pos).getBlock().equals(Blocks.AIR))&&!(player.world.getBlockState(pos).getBlock().equals(Blocks.SNOW_LAYER)) && y != 0) {
							x=0;
							y=0;
							z=0;
						}
						//player.move(MoverType.SELF, x * 0.5, y * 0.5, z * 0.5);
						player.connection.setPlayerLocation(player.posX + x * 0.5, player.posY + y * 0.5, player.posZ + z * 0.5, player.rotationYaw, player.rotationPitch);
					}
					super.skillTimer(event);
					
				}
				
			};
			skill0(player);
			
		}
	}
	
	@Override
	public void skill3(EntityPlayer player) {
		PlayerData data = main.game.getPlayerData(player.getUniqueID());
		int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
		leesinMatrix matrix = (leesinMatrix) this.matrix;
		if(data.getCool(3) <= 0 && data.getCurrentMana() > matrix.skillcost[4][lv-1]) {
			data.setCurrentMana((float) (data.getCurrentMana() - matrix.skillcost[4][lv-1]));
			EntityTempest tempest = new EntityTempest(player.world, player, (float) (matrix.skilldamage[4][lv-1]+ matrix.skillAdcoe[3][lv-1] * data.getAd()));
			tempest.setNoGravity(true);
			tempest.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.0f, 0);
			player.world.spawnEntity(tempest);
			player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10, 255,false,false));
			this.handler[4] = new CooldownHandler(((leesinMatrix)matrix).cooldown[3][lv-1], 3, (EntityPlayerMP) player);
		}
		skill0(player);
		
		
	}
	
	@Override
	public void skill4(EntityPlayer player) {
		PlayerData data = main.game.getPlayerData(player.getUniqueID());
		int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
		leesinMatrix matrix = (leesinMatrix) this.matrix;
		if(data.getCool(4) <= 0&& data.getCurrentMana() > matrix.skillcost[5][lv-1]) {
			data.setCurrentMana((float) (data.getCurrentMana() - matrix.skillcost[5][lv-1]));
			this.handler[5] = new CooldownHandler(3, 4, (EntityPlayerMP) player) {
				float skilldamage1;
				double down;
				public CooldownHandler setdamage_cool(float skilldamage,double cooldown) {
					skilldamage1 = skilldamage;
					down = cooldown;
					return this;
				}
				
				@SubscribeEvent
				public void skillTimer(ServerTickEvent event) throws Throwable {
					if(skillTimer > cooldown) {
						new CooldownHandler(down, 4, (EntityPlayerMP) player);
						MinecraftForge.EVENT_BUS.unregister(this);
					}
					data.setCool(this.id, ((float)(cooldown-skillTimer)/40));
					skillTimer++;
					
				}
				
				@SubscribeEvent
				public void PlayerAttackEvent(AttackEntityEvent event)
				{
					if(event.getEntityPlayer().getUniqueID().equals(player.getUniqueID())) {
						
						EntityLivingBase etarget = (EntityLivingBase) event.getTarget();
						etarget.knockBack(event.getEntityLiving(), 2, -event.getEntityPlayer().getLookVec().x, -event.getEntityPlayer().getLookVec().z);
						PlayerData attacker = main.game.getPlayerData(event.getEntityPlayer().getUniqueID());
						EntityData target = (etarget instanceof EntityPlayer) ? main.game.getPlayerData(etarget.getUniqueID()) : ((AbstractMob)etarget).getData();
						RWAPI.util.DamageSource sourcee = RWAPI.util.DamageSource.causeSkill(attacker, target, skilldamage1);
						RWAPI.util.DamageSource.attackDamage(sourcee);
						EnemyStatHandler.EnemyStatSetter(sourcee);
						new CooldownHandler(down, 4, (EntityPlayerMP) player);
						MinecraftForge.EVENT_BUS.unregister(this);
						
					}
				}
			}.setdamage_cool((float) (matrix.skilldamage[5][lv-1] + matrix.skillAdcoe[4][lv-1] * data.getAd() - data.getAd()),matrix.cooldown[4][lv-1]);
		}
		skill0(player);
	}
	
	@Override
	public void Levelup(PlayerData data) {
		super.Levelup(data);
	}
	
	class resonatingtimer extends CooldownHandler{
		
		EntityResonating resonating;

		public resonatingtimer(double cool, int id, EntityPlayerMP player, EntityResonating resonating) {
			super(cool, id, player);
			this.resonating = resonating;
			// TODO Auto-generated constructor stub
		}

		@Override
		public void skillTimer(ServerTickEvent event) throws Throwable {
			// TODO Auto-generated method stub
			if(skillTimer > cooldown) {
				resonating.setDead();
				data.setSkill(1, (SkillBase) ModSkills.skill.get(ModSkills.sonicwave.SkillNumber));
				new CooldownHandler(((leesinMatrix)matrix).cooldown[1][main.game.getPlayerData(player.getUniqueID()).getLevel()-1], 1, (EntityPlayerMP) player);
				MinecraftForge.EVENT_BUS.unregister(this);
			}
			data.setCool(this.id, ((float)(cooldown-skillTimer)/40));
			skillTimer++;
		}	
		
	}
	
	class leesinMatrix extends StatMatrix{
		
		public leesinMatrix() {
			super.ad = this.ad;
			super.ap = this.ap;
			super.hp = this.hp;
			super.hregen = this.hregen;
			super.mana = this.mana;
			super.move = this.move;
			super.mregen = this.mregen;
			super.attackspeed = this.attackspeed;
		}
		final double[][] cooldown = {
				{3,3,3,3,3,3,3,3,3,3,3,3},
				{8, 7.9, 7.9, 7.7, 7.4, 6.5, 6.3, 6.1, 6, 5.8, 5.6, 5},
				{12, 11.7, 11.5, 11.2, 11, 10, 10.9, 10.8, 10.5, 10.2, 10, 10},
				{10, 10, 10, 10, 10, 9, 9, 9, 9, 9, 9, 8},
				{90, 90, 80, 80, 80, 65, 65, 65, 60, 60, 60, 45}
		};
		final double[] hp = {800,
				850,
				885,
				910,
				940,
				1090,
				1140,
				1200,
				1310,
				1390,
				1440,
				1600};
		final double[] mana = {200,
				200,
				200,
				200,
				200,
				300,
				300,
				300,
				350,
				350,
				350,
				500};
		final double[] hregen = {1.1,
				1.2,
				1.5,
				1.7,
				2,
				4,
				4.2,
				4.6,
				5,
				5.1,
				5.4,
				6.5};
		final double[] mregen = {5,
				5,
				5,
				5,
				5,
				6,
				6,
				6,
				6,
				6,
				6,
				8};
		final double[] ad = {
				80,
				83,
				87,
				90,
				93,
				110,
				113,
				117,
				120,
				123,
				127,
				140
		};
		final double[] ap = {0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
		};
		final double[] move = {
				107,
				107,
				107,
				107,
				107,
				107,
				107,
				107,
				107,
				107,
				107,
				107
		};
		final double[] attackspeed = {
				0.4,
				0.43,
				0.49,
				0.53,
				0.56,
				0.65,
				0.69,
				0.72,
				0.76,
				0.79,
				0.82,
				0.9
		};
		
		final double[][] skilldamage = {
				{0.5, 0.54, 0.59, 0.6, 0.6, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.75},
				{100, 102, 104, 106, 108, 110, 112, 114, 116, 118, 120, 122},
				{110, 114, 118, 122, 124, 130, 132, 134, 136, 138, 140, 200},
				{0,0,0,0,0,0,0,0,0,0,0,0,0},
				{80, 87, 91, 94, 98, 108, 115, 122, 129, 126, 133, 145},
				{250, 250, 270, 270, 270, 330, 330, 330, 350, 350, 350, 380}
		};
		
		final double[][] skillAdcoe = {
				{0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0.4, 0.4, 0.4, 0.4, 0.4, 0.6, 0.6, 0.6, 0.6, 0.6, 0.6, 0.8},
				{0,0,0,0,0,0,0,0,0,0,0,0},
				{0.3, 0.3, 0.3, 0.3, 0.3, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.6},
				{1.2, 1.2, 1.3, 1.3, 1.3, 1.5, 1.5, 1.5, 1.6, 1.6, 1.6, 1.8}
		};
		
		final double[][] skillApcoe = {
				{0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0}	
		};
		
		final double[][] skill1coe = {
			{0.4, 0.4, 0.4, 0.4, 0.4, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
			{0.4, 0.4, 0.4, 0.4, 0.4, 0.6, 0.6, 0.6, 0.6, 0.6, 0.6, 0.7}
		};
		
		final double[][] skillcost = {
			{0,0,0,0,0,0,0,0,0,0,0,0},
			{50,50,55,55,55,70,70,70,75,75,75,90},
			{50,50,55,55,55,70,70,70,75,75,75,90},
			{40,40,40,40,40,45,45,45,45,45,45,50},
			{60,60,60,60,60,70,70,70,70,70,70,80},
			{30,30,30,30,30,35,35,35,40,40,40,45}
		};
	}
}
