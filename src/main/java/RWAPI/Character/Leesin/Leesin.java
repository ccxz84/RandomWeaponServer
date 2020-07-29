package RWAPI.Character.Leesin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import RWAPI.Character.Leesin.skills.*;
import RWAPI.Character.Skill;
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

		skills[0] = new flurry(this);
		skills[1] = new sonicwave(this);
		skills[2] = new safeguard(this);
		skills[3] = new tempest(this);
		skills[4] = new dragonsrage(this);
	}

	@Override
	public PlayerClass copyClass(){
		return new Leesin();
	}
	
	public void skill0(EntityPlayer player) {
		skills[0].skillExecute(player);
	}
	
	@Override
	public void skill1(EntityPlayer player) {
		skills[1].skillpreExecute(player);
		skills[1].skillExecute(player);
		skills[1].skillEnd(player);
	}
	
	@Override
	public void skill2(EntityPlayer player) {
		skills[2].skillpreExecute(player);
		skills[2].skillExecute(player);
		skills[2].skillEnd(player);
	}
	
	@Override
	public void skill3(EntityPlayer player) {
		skills[3].skillpreExecute(player);
		skills[3].skillExecute(player);
		skills[3].skillEnd(player);
	}
	
	@Override
	public void skill4(EntityPlayer player) {
		skills[4].skillpreExecute(player);
		skills[4].skillExecute(player);
		skills[4].skillEnd(player);
	}
	
	@Override
	public void Levelup(PlayerData data) {
		super.Levelup(data);
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
	}


	@Override
	public void EndGame(EntityPlayerMP player){
		for(Skill skill : skills){
			skill.skillEnd(player);
		}
	}
}
