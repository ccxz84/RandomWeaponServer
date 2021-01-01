package RWAPI.Character.Leesin;

import RWAPI.Character.Leesin.skills.*;
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

public class Leesin extends PlayerClass{

	//private float[] cool = new float[14];

	public Leesin(){
		default_health = 750;
		default_mana = 200;
		
		default_ad = 80;
		default_ap = 0;
		default_move = 104;
		
		default_regenHealth = 0.7f;
		default_regenMana = 10f;
		
		attackSpeed = 0.4f;

		default_armor = 33;
		default_magicresistance = 32.1;
		
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
			super.armor = this.armor;
			super.magicresistance = this.magicresistance;
			super.move = this.move;
			super.mregen = this.mregen;
			super.attackspeed = this.attackspeed;
		}
		final double[] hp = {
				750,
				800,
				850,
				900,
				950,
				1010,
				1070,
				1130,
				1210,
				1290,
				1370,
				1450,
				1530,
				1610,
				1690,
				1770,
				1850,
				1930
		};

		final double[] mana = {
				200,
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
				400,
				400,
				400,
				400,
				400,
				400,
				400
		};
		final double[] hregen = {
				0.7,
				0.8,
				0.9,
				1,
				1.1,
				1.3,
				1.5,
				1.7,
				2,
				2.3,
				2.6,
				3,
				3.4,
				3.7,
				4.1,
				4.5,
				4.9,
				5.4
		};
		final double[] mregen = {
				10,
				10,
				10,
				10,
				10,
				15,
				15,
				15,
				15,
				15,
				15,
				15,
				15,
				15,
				15,
				15,
				15,
				15
		};

		final double[] armor = {
				33,
				36.5,
				40,
				43.5,
				47,
				50.5,
				54,
				57.5,
				61,
				64.5,
				68,
				71.5,
				75,
				78.5,
				82,
				85.5,
				89,
				92.5

		};

		final double[] magicresistance = {
				32.1,
				33.35,
				34.6,
				35.85,
				37.1,
				38.35,
				39.6,
				40.85,
				42.1,
				43.35,
				44.6,
				45.85,
				47.1,
				48.35,
				49.6,
				50.85,
				52.1,
				53.35

		};

		final double[] ad = {
				80,
				83,
				86,
				89,
				92,
				96,
				100,
				104,
				109,
				113,
				117,
				121,
				126,
				131,
				136,
				141,
				146,
				151
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
				104,
				104,
				104,
				104,
				104,
				104,
				104,
				104,
				104,
				104,
				104,
				104,
				104,
				104,
				104,
				104,
				104,
				104
		};
		final double[] attackspeed = {
				0.4,
				0.4216,
				0.4432,
				0.4648,
				0.4864,
				0.508,
				0.5296,
				0.5512,
				0.5728,
				0.5944,
				0.616,
				0.6388,
				0.6616,
				0.6844,
				0.7072,
				0.73,
				0.7528,
				0.7756,

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
		sonicwave wave = (sonicwave) skills[1];
		flurry flurry = (flurry) skills[0];
		data.getPlayer().sendMessage(new TextComponentString("리 신 : "));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"질풍격"+
				TextFormatting.RESET +" : 스킬 사용 시, 3초 동안 2회의 기본 공격에 대해 공격속도가 "
				+ String.format("%.1f",skills[0].getskilldamage()[lv-1]) + " 만큼 증가합니다. 활성화 된 상태에서 적을 공격 시, " +
				TextFormatting.AQUA + flurry.getSkilldamage2()[lv-1]+
				TextFormatting.RESET + "의 기력이 회복됩니다."));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"음파"+
				TextFormatting.RESET +" : 리 신이 불협화음으로 된 음파를 발사하여 적에게 " +
				String.format("%.1f",skills[1].getskilldamage()[lv-1]) +"(+"+TextFormatting.RED +String.format("%.1f",(skills[1].getskillAdcoe()[lv-1]*data.getAd())) +
				TextFormatting.RESET + ")의 데미지를 입힙니다. 음파가 적에게 명중하면 3초 안에 공명의 일격을 시전할 수 있습니다. 쿨타임 " +
				TextFormatting.GOLD +skills[1].getcooldown()[lv-1]+
				TextFormatting.RESET+"초  소모값 : " + skills[1].getskillcost()[lv-1] ));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"공명의 일격"+
				TextFormatting.RESET +" : 리 신이 음파를 맞은 적에게 돌진하여 " + String.format("%.1f",wave.getskilldamage2()[lv-1]) + "(+"
				+TextFormatting.RED +String.format("%.1f",(wave.getskill1coe()[0][lv-1] * data.getAd()))+
				TextFormatting.RESET +")의 데미지를 입히고, 추가로 적 잃은 체력의 " +
				TextFormatting.DARK_RED + String.format("%.1f",(wave.getskill1coe()[1][lv-1] *100))+"%"+
				TextFormatting.RESET + "의 데미지를 입힙니다.  소모값 : " + wave.getskillcost2()[lv-1]));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"방호"+
				TextFormatting.RESET +" : 리 신이 바라보고 있는 방향으로 일정거리 순간이동 합니다. 쿨타임 : " +
				TextFormatting.GOLD +skills[2].getcooldown()[lv-1]+
				TextFormatting.RESET+"초  소모값 : " + skills[2].getskillcost()[lv-1]));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"폭풍" +
				TextFormatting.RESET +" : 리 신이 1초 동안 정신을 집중하여 바닥을 내리쳐 " + skills[3].getskilldamage()[lv-1] +"(+"+
				TextFormatting.RED +String.format("%.1f",(skills[3].getskillAdcoe()[lv-1] * data.getAd()))+
				TextFormatting.RESET +")의 데미지를 입힙니다. 정신 집중 시간에는 " +
				TextFormatting.DARK_PURPLE + "70%"+
				TextFormatting.RESET +"의 이동속도가 감소합니다. 쿨타임 : "+
				TextFormatting.GOLD + skills[3].getcooldown()[lv-1]+
				TextFormatting.RESET+"초  소모값 : " + skills[3].getskillcost()[lv-1]));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"용의 분노" +
				TextFormatting.RESET +" : 리 신이 스킬 사용 후, 2초 안에 기본 공격 시 적에게 " + skills[4].getskilldamage()[lv-1] + "(+"+
				TextFormatting.RED + String.format("%.1f",(skills[4].getskillAdcoe()[lv-1] * data.getAd()))+
				TextFormatting.RESET +")의 데미지를 입히며 뒤로 날려보냅니다. 쿨타임 : "+
				TextFormatting.GOLD + skills[4].getcooldown()[lv-1]+
				TextFormatting.RESET+"초  소모값 : " + skills[4].getskillcost()[lv-1]));
	}
}
