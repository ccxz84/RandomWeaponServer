package RWAPI.Character.MasterYi;

import RWAPI.Character.Leesin.Leesin;
import RWAPI.Character.Leesin.skills.sonicwave;
import RWAPI.Character.MasterYi.skills.*;
import RWAPI.Character.Skill;
import RWAPI.main;
import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerClass;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Leesin.entity.EntityUmpa;
import RWAPI.Character.buff.Buff;
import RWAPI.Character.monster.entity.AbstractMob;
import RWAPI.Character.PlayerClass.StatMatrix;
import RWAPI.init.ModSkills;
import RWAPI.init.ModWeapons;
import RWAPI.util.ClassList;
import RWAPI.util.DamageSource;
import RWAPI.util.DamageSource.EnemyStatHandler;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public class MasterYi extends PlayerClass {
	
	public MasterYi(){
		default_health = 780;
		default_mana = 200;
		
		default_ad = 78;
		default_ap = 0;
		default_move = 110;
		
		default_regenHealth = 1.2f;
		default_regenMana = 2.3f;
		
		attackSpeed = 0.38f;
		
		class_code = ClassList.MasterYi;
		
		ClassName = "마스터 이";
		
		super.weapon = ModWeapons.masteryi;
		skillSet[0] = ModSkills.skill.get(ModSkills.doublestrike.SkillNumber);
		skillSet[1] = ModSkills.skill.get(ModSkills.alphastrike.SkillNumber);
		skillSet[2] = ModSkills.skill.get(ModSkills.meditation.SkillNumber);
		skillSet[3] = ModSkills.skill.get(ModSkills.wujustyle.SkillNumber);
		skillSet[4] = ModSkills.skill.get(ModSkills.highlander.SkillNumber);

		skills[0] = new doublestrike(this);
		skills[1] = new alphastrike(this);
		skills[2] = new meditation(this);
		skills[3] = new wujustyle(this);
		skills[4] = new highlander(this);

		matrix = new MasterYimatrix();
	}

	@Override
	public PlayerClass copyClass(){
		return new MasterYi();
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
	public void EndGame(EntityPlayerMP player){
		for(Skill skill : skills){
			skill.skillEnd(player);
		}
	}
	
	class MasterYimatrix extends StatMatrix{
		public MasterYimatrix() {
			super.ad = this.ad;
			super.ap = this.ap;
			super.hp = this.hp;
			super.hregen = this.hregen;
			super.mana = this.mana;
			super.move = this.move;
			super.mregen = this.mregen;
			super.attackspeed = this.attackspeed;
		}
		
		final double[] hp = {
				780,
				820,
				840,
				860,
				880,
				990,
				1030,
				1080,
				1130,
				1190,
				1250,
				1400
		};
		
		final double[] mana = {
				200,
				250,
				300,
				350,
				400,
				500,
				570,
				640,
				710,
				780,
				850,
				1000
		};
		
		final double[] hregen = {
				1.2,
				1.3,
				1.4,
				1.5,
				1.6,
				3,
				3.2,
				3.4,
				3.6,
				3.8,
				4,
				4.2
		};
		
		final double[] mregen = {
			2.3,
			2.5,
			2.9,
			3.3,
			3.7,
			5,
			5.2,
			5.6,
			6,
			6.4,
			6.9,
			9
		};
		
		final double[] ad = {
				78,
				80,
				82,
				84,
				86,
				95,
				99,
				103,
				107,
				110,
				114,
				120
		};
		
		final double[] ap = {
			0,0,0,0,0,0,0,0,0,0,0,0	
		};
		
		final double[] move = {
				110,110,110,110,110,110,110,110,110,110,110,110
		};
		final double[] attackspeed = {
			0.38,
			0.41,
			0.45,
			0.49,
			0.51,
			0.66,
			0.7,
			0.75,
			0.8,
			0.85,
			0.9,
			1.03
		};
	}

	@Override
	public void classInformation(PlayerData data) {
		int lv = data.getLevel();
		highlander wave = (highlander) skills[4];
		data.getPlayer().sendMessage(new TextComponentString("마스터 이 : "));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"2연속 공격"+
				TextFormatting.RESET +" : 매 4회 공격마다 "+
				TextFormatting.RED+ String.format("%.1f",(0.5 * data.getAd()))+
				TextFormatting.RESET + "(공격력의 50%) 의 추가 데미지를 입힙니다. 적을 처치 시, 모든 스킬의 쿨타임이 70% 감소합니다."));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"일격 필살"+
				TextFormatting.RESET +" : 마스터 이가 주변에 있는 4명의 적에게 " +
				skills[1].getskilldamage()[lv-1] +"(+"+TextFormatting.RED +String.format("%.1f",(skills[1].getskillAdcoe()[lv-1]*data.getAd())) +
				TextFormatting.RESET + ")의 데미지를 입힙니다. 주변의 적이 4명이 아닌경우 마지막 공격 대상에게 나머지 공격을 적중합니다. 쿨타임 " +
				TextFormatting.GOLD +skills[1].getcooldown()[lv-1]+
				TextFormatting.RESET+"초" ));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"명상"+
				TextFormatting.RESET +" : 마스터 이가 정신을 집중해 4초동안 "+ String.format("%.1f",(skills[2].getskilldamage()[lv-1]*160))+ "(+"+
				TextFormatting.BLUE +String.format("%.1f",(skills[2].getskillApcoe()[lv-1] * data.getAp()*160))
				+TextFormatting.RESET+") 체력을 회복합니다. 쿨타임 : " +
				TextFormatting.GOLD +skills[2].getcooldown()[lv-1]+
				TextFormatting.RESET+"초"));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"우주류 검술" +
				TextFormatting.RESET +" : 마스터 이가 5초동안 기본 공격으로 " + skills[3].getskilldamage()[lv-1] +"(+"+
				TextFormatting.RED +String.format("%.1f",(skills[3].getskillAdcoe()[lv-1] * data.getAd()))+
				TextFormatting.RESET +")의 데미지를 입힙니다. 쿨타임 : "+
				TextFormatting.GOLD + skills[3].getcooldown()[lv-1]+
				TextFormatting.RESET+"초"));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"최후의 전사" +
				TextFormatting.RESET +" : 사용 시, 이동속도가 "
				+ skills[4].getskilldamage()[lv-1] +
				"증가하고, "
				+"공격속도 "+wave.getskilldamage2()[lv-1]+" 증가합니다. 쿨타임 : "+
				TextFormatting.GOLD + skills[4].getcooldown()[lv-1]+
				TextFormatting.RESET+"초"));
	}
}
