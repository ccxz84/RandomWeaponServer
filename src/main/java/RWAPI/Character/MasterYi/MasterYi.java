package RWAPI.Character.MasterYi;

import RWAPI.Character.MasterYi.skills.*;
import RWAPI.Character.Skill;
import RWAPI.Character.PlayerClass;
import RWAPI.Character.PlayerData;
import RWAPI.init.ModSkills;
import RWAPI.init.ModWeapons;
import RWAPI.util.ClassList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class MasterYi extends PlayerClass {
	
	public MasterYi(){
		default_health = 800;
		default_mana = 200;
		
		default_ad = 76;
		default_ap = 0;
		default_move = 110;
		
		default_regenHealth = 0.6f;
		default_regenMana = 0.4f;
		
		attackSpeed = 0.35f;

		default_armor = 33;
		default_magicresistance = 32.1;
		
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
	public void initSkill(PlayerData data) {
		for(int i = 0 ; i<skills.length; i++){
			skills[i].Skillset(data.getPlayer());
		}
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
			super.armor = this.armor;
			super.magicresistance = this.magicresistance;
			super.move = this.move;
			super.mregen = this.mregen;
			super.attackspeed = this.attackspeed;
		}
		
		final double[] hp = {
				800,
				860,
				920,
				980,
				1040,
				1105,
				1170,
				1235,
				1305,
				1375,
				1445,
				1525,
				1605,
				1685,
				1765,
				1845,
				1925,
				2005
		};
		
		final double[] mana = {
				200,
				210,
				220,
				230,
				240,
				260,
				280,
				300,
				330,
				360,
				390,
				420,
				470,
				520,
				570,
				620,
				670,
				720
		};
		
		final double[] hregen = {
				0.6,
				0.7,
				0.8,
				0.9,
				1,
				1.1,
				1.25,
				1.4,
				1.6,
				1.8,
				2,
				2.25,
				2.55,
				2.85,
				3.15,
				3.45,
				3.75,
				4.05
		};
		
		final double[] mregen = {
				0.4,
				0.5,
				0.6,
				0.7,
				0.8,
				1,
				1.2,
				1.4,
				1.7,
				2,
				2.3,
				2.7,
				3.2,
				3.7,
				4.2,
				4.7,
				5.2,
				5.7
		};

		final double[] armor = {
				0.4,
				0.5,
				0.6,
				0.7,
				0.8,
				1,
				1.2,
				1.4,
				1.7,
				2,
				2.3,
				2.7,
				3.2,
				3.7,
				4.2,
				4.7,
				5.2,
				5.7
		};

		final double[] magicresistance = {
				33,
				36,
				39,
				42,
				45,
				48,
				51,
				54,
				57,
				60,
				63,
				66,
				69,
				72,
				75,
				78,
				81,
				84
		};
		
		final double[] ad = {
				76,
				78,
				80,
				82,
				84,
				87,
				90,
				93,
				97,
				101,
				105,
				109,
				113,
				117,
				121,
				125,
				129,
				133

		};
		
		final double[] ap = {
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
				0,
				0,
				0,
				0,
				0,
				0,
				0
		};
		
		final double[] move = {
				110,
				110,
				110,
				110,
				110,
				110,
				110,
				110,
				110,
				110,
				110,
				110,
				110,
				110,
				110,
				110,
				110,
				110
		};
		final double[] attackspeed = {
				0.35,
				0.36365,
				0.3773,
				0.39095,
				0.4046,
				0.41825,
				0.4319,
				0.44555,
				0.4592,
				0.47285,
				0.4865,
				0.5005,
				0.5145,
				0.5285,
				0.5425,
				0.5565,
				0.5705,
				0.5845
		};
	}

	@Override
	public void classInformation(PlayerData data) {
		int lv = data.getLevel();
		highlander wave = (highlander) skills[4];
		data.getPlayer().sendMessage(new TextComponentString("마스터 이 : "));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"2연속 공격"+
				TextFormatting.RESET +" : 매 4회 공격마다 "+
				TextFormatting.RED+ String.format("%.1f",(skills[0].getskillAdcoe()[lv-1] * data.getAd()))+
				TextFormatting.RESET + "(공격력의 "+skills[0].getskillAdcoe()[lv-1]*100+"%) 의 추가 데미지를 입힙니다. 적을 처치 시, 모든 스킬의 쿨타임이 70% 감소합니다."));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"일격 필살"+
				TextFormatting.RESET +" : 마스터 이가 주변에 있는 4명의 적에게 " +
				skills[1].getskilldamage()[lv-1] +"(+"+TextFormatting.RED +String.format("%.1f",(skills[1].getskillAdcoe()[lv-1]*data.getAd())) +
				TextFormatting.RESET + ")의 물리 피해를 입힙니다. 주변의 적이 4명이 아닌경우 마지막 공격 대상에게 나머지 공격을 적중합니다. 같은 대상을 공격 시, 피해량이 30%씩 감소합니다. 최소 피해량은 15입니다. 쿨타임 " +
				TextFormatting.GOLD +skills[1].getcooldown()[lv-1]+
				TextFormatting.RESET+"초 소모값 : " + skills[1].getskillcost()[lv-1] ));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"명상"+
				TextFormatting.RESET +" : 마스터 이가 정신을 집중해 4초동안 "+ String.format("%.1f",(skills[2].getskilldamage()[lv-1]*160))+ "(+"+
				TextFormatting.BLUE +String.format("%.1f",(skills[2].getskillApcoe()[lv-1] * data.getAp()*160))
				+TextFormatting.RESET+") 체력을 회복합니다. 추가적으로 초당 잃은 체력의 12%가 회복됩니다. 쿨타임 : " +
				TextFormatting.GOLD +skills[2].getcooldown()[lv-1]+
				TextFormatting.RESET+"초 소모값 : " + skills[1].getskillcost()[lv-1]));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"우주류 검술" +
				TextFormatting.RESET +" : 마스터 이가 5초동안 기본 공격으로 " + skills[3].getskilldamage()[lv-1] +"(+"+
				TextFormatting.RED +String.format("%.1f",(skills[3].getskillAdcoe()[lv-1] * data.getAd()))+
				TextFormatting.RESET +")의 고정 피해를 입힙니다. 쿨타임 : "+
				TextFormatting.GOLD + skills[3].getcooldown()[lv-1]+
				TextFormatting.RESET+"초 소모값 : " + skills[3].getskillcost()[lv-1]));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"최후의 전사" +
				TextFormatting.RESET +" : 사용 시, 이동속도가 "
				+ skills[4].getskilldamage()[lv-1] +
				"증가하고, "
				+"공격속도 "+wave.getskilldamage2()[lv-1]+" 증가합니다. 쿨타임 : "+
				TextFormatting.GOLD + skills[4].getcooldown()[lv-1]+
				TextFormatting.RESET+"초 소모값 : " + skills[4].getskillcost()[lv-1]));
	}
}
