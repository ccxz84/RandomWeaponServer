package RWAPI.Character.Kassadin;

import RWAPI.Character.Kassadin.skills.*;
import RWAPI.Character.Nasus.skills.*;
import RWAPI.Character.PlayerClass;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.init.ModSkills;
import RWAPI.init.ModWeapons;
import RWAPI.util.ClassList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class Kassadin extends PlayerClass{

	//private float[] cool = new float[14];

	public Kassadin(){
		default_health = 760;
		default_mana = 200;
		
		default_ad = 58.85;
		default_ap = 0;
		default_move = 109;
		
		default_regenHealth = 0.55f;
		default_regenMana = 0.5f;
		
		attackSpeed = 0.35f;

		default_armor = 30;
		default_magicresistance = 30;
		
		class_code = ClassList.Kassadin;
		
		ClassName = "카사딘";
		
		super.weapon = ModWeapons.kassadin;

		matrix = new nasusMatrix();
		skillSet[0] = ModSkills.skill.get(ModSkills.voidstone.SkillNumber);
		skillSet[1] = ModSkills.skill.get(ModSkills.nullsphere.SkillNumber);
		skillSet[2] = ModSkills.skill.get(ModSkills.netherblade.SkillNumber);
		skillSet[3] = ModSkills.skill.get(ModSkills.forcepulse.SkillNumber);
		skillSet[4] = ModSkills.skill.get(ModSkills.riftwalk.SkillNumber);

		skills[0] = new voidstone();
		skills[1] = new nullsphere();
		skills[2] = new netherblade();
		skills[3] = new forcepulse();
		skills[4] = new riftwalk();
	}

	@Override
	public PlayerClass copyClass(){
		return new Kassadin();
	}

	@Override
	public void initSkill(PlayerData data) {
		for(int i = 0 ; i<skills.length; i++){
			skills[i].Skillset(data.getPlayer());
		}
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
	public void preinitSkill(PlayerData data){

	}
	
	@Override
	public void Levelup(PlayerData data) {
		super.Levelup(data);
	}

	class nasusMatrix extends StatMatrix{

		public nasusMatrix() {
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
				760,
				820,
				880,
				940,
				1000,
				1065,
				1130,
				1195,
				1265,
				1335,
				1405,
				1480,
				1555,
				1630,
				1705,
				1780,
				1855,
				1930
		};

		final double[] mana = {
				200,
				230,
				260,
				290,
				320,
				360,
				400,
				440,
				490,
				540,
				590,
				640,
				690,
				740,
				790,
				840,
				890,
				940
		};
		final double[] hregen = {
				0.55,
				0.65,
				0.75,
				0.85,
				0.95,
				1.05,
				1.15,
				1.25,
				1.4,
				1.55,
				1.7,
				1.85,
				2,
				2.15,
				2.3,
				2.45,
				2.6,
				2.75,
		};
		final double[] mregen = {
				0.5,
				0.65,
				0.8,
				0.95,
				1.1,
				1.25,
				1.4,
				1.55,
				1.75,
				1.95,
				2.15,
				2.6,
				3.05,
				3.5,
				3.95,
				4.4,
				4.85,
				5.3
		};

		final double[] armor = {
				30,
				32,
				34,
				36,
				38,
				40,
				42,
				44,
				46,
				48,
				50,
				52,
				54,
				56,
				58,
				60,
				62,
				64
		};

		final double[] magicresistance = {
				30,
				30.5,
				31,
				31.5,
				32,
				32.5,
				33,
				33.5,
				34,
				34.5,
				35,
				35.5,
				36,
				36.5,
				37,
				37.5,
				38,
				38.5
		};

		final double[] ad = {
				58.85,
				59.85,
				60.85,
				61.85,
				62.85,
				63.85,
				64.85,
				65.85,
				66.85,
				67.85,
				68.85,
				70.35,
				71.85,
				73.35,
				74.85,
				76.35,
				77.85,
				79.35
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
				109,
				109,
				109,
				109,
				109,
				109,
				109,
				109,
				109,
				109,
				109,
				109,
				109,
				109,
				109,
				109,
				109,
				109
		};
		final double[] attackspeed = {
				0.34,
				0.35258,
				0.36516,
				0.37774,
				0.39032,
				0.4029,
				0.41548,
				0.42806,
				0.44064,
				0.45322,
				0.4658,
				0.47838,
				0.49096,
				0.50354,
				0.51612,
				0.5287,
				0.54128,
				0.55386
		};
	}


	@Override
	public void EndGame(EntityPlayerMP player){
		for(Skill skill : skills){
			skill.skillEnd(player);
		}
	}

	@Override
	public void classInformation(PlayerData data) {
		int lv = data.getLevel();
		voidstone skill0 = (voidstone) skills[0];
		nullsphere skill1 = (nullsphere) skills[1];
		netherblade skill2 = (netherblade) skills[2];
		forcepulse skill3 = (forcepulse) skills[3];
		riftwalk skill4 = (riftwalk) skills[4];
		data.getPlayer().sendMessage(new TextComponentString("카사딘 : "));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"공허석"+
				TextFormatting.RESET +" : 기본 공격 시, " + skills[0].getskilldamage()[lv-1]+ "(+"+
				TextFormatting.BLUE + String.format("%.1f",(skills[0].getskillApcoe()[lv-1] * data.getAp()))+
				TextFormatting.RESET + ")의 추가 마법피해를 입힙니다."
				+ "피격 시, " + skill0.reducephy+"%의 물리 피해, "
				+ skill0.reducemag+"%의 마법 피해를 무시합니다."));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"무의 구체"+
				TextFormatting.RESET +" : 카사딘이 공허 에너지 구체를 발사하여 적에게 " +
				String.format("%.1f",skills[1].getskilldamage()[lv-1]) +"(+"+TextFormatting.BLUE +String.format("%.1f",(skills[1].getskillApcoe()[lv-1]*data.getAp())) +
				TextFormatting.RESET + ")의 데미지를 입힙니다. 여분의 에너지는 카사딘의 몸을 감싸 " + String.format("%.1f",skill1.duration)+"초 동안"+
				String.format("%.1f",skill1.getskill1damage()[lv-1]) +"(+"+TextFormatting.BLUE +String.format("%.1f",(skill1.getskill1apcode()[lv-1]*data.getAp()))+
				TextFormatting.RESET+")의 방어막을 부여합니다. 쿨타임 " +
				TextFormatting.GOLD +skills[1].getcooldown()[lv-1]+
				TextFormatting.RESET+"초  소모값 : " + skills[1].getskillcost()[lv-1] ));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"황천의 검"+
				TextFormatting.RESET +" : 카사딘이 황천의 검을 충전하여 다음 번 기본 공격으로 " +String.format("%.1f",skill2.getskilldamage()[lv-1]) +"(+"+TextFormatting.BLUE +String.format("%.1f",(skill2.getskillApcoe()[lv-1]*data.getAp()))+
				TextFormatting.RESET+")의 데미지를 입히고, 잃은 마나의 " + String.format("%.1f",skill2.getskill1damage()[lv-1])+"%를 회복합니다. 쿨타임 : " +
				TextFormatting.GOLD +skills[2].getcooldown()[lv-1]+
				TextFormatting.RESET+"초  소모값 : " + skills[2].getskillcost()[lv-1]));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"힘의 파동" +
				TextFormatting.RESET +" : 카사딘이 힘의 파동을 시전하여 전방의 적들에게 " + skills[3].getskilldamage()[lv-1] +"(+"+
				TextFormatting.BLUE +String.format("%.1f",(skills[3].getskillApcoe()[lv-1] * data.getAp()))+
				TextFormatting.RESET +")의 마법 피해를 입힙니다. 피격 당한 대상은 "+(int)skill3.duration +"초 동안"+
				TextFormatting.DARK_PURPLE + skill3.getskill1damage()[lv-1]+
				TextFormatting.RESET +"%의 이동속도가 감소합니다. 쿨타임 : "+
				TextFormatting.GOLD + skills[3].getcooldown()[lv-1]+
				TextFormatting.RESET+"초  소모값 : " + skills[3].getskillcost()[lv-1]));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"균열 이동" +
				TextFormatting.RESET +" : 카사딘의 짧은 거리를 순간 이동하여 적에게 " + skills[4].getskilldamage()[lv-1] + "(+"+
				TextFormatting.BLUE + String.format("%.1f",(skill4.getskillapcoe()[0][lv-1] * data.getAp()))+ TextFormatting.RESET+")(+"+
				TextFormatting.AQUA + String.format("%.1f",(skill4.getskillapcoe()[1][lv-1] * data.getMaxMana()))+
				TextFormatting.RESET +")의 마법 피해를 입힙니다. "+skill4.duration +"초 안에 재사용 시, 두 배의 마나를 소모하며, "+
				skill4.getskill1damage()[lv-1] + "(+"+
				TextFormatting.BLUE + String.format("%.1f",(skill4.getskillapcoe()[2][lv-1] * data.getAp()))+
				TextFormatting.RESET +")(+"+
				TextFormatting.AQUA + String.format("%.1f",(skill4.getskillapcoe()[3][lv-1] * data.getMaxMana()))+
				TextFormatting.RESET +")의 마법 피해를 추가로 입힙니다. 최대 "+skill4.maxstack+"회 까지 중첩됩니다. 쿨타임 : "+
				TextFormatting.GOLD + skills[4].getcooldown()[lv-1]+
				TextFormatting.RESET+"초  소모값 : " + skills[4].getskillcost()[lv-1]));
	}
}
