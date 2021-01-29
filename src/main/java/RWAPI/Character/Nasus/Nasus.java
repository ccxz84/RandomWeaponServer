package RWAPI.Character.Nasus;

import RWAPI.Character.Leesin.skills.*;
import RWAPI.Character.Nasus.skills.*;
import RWAPI.Character.PlayerClass;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.game.event.BaseEvent;
import RWAPI.game.event.StatChangeEventHandle;
import RWAPI.init.ModSkills;
import RWAPI.init.ModWeapons;
import RWAPI.main;
import RWAPI.util.ClassList;
import RWAPI.util.StatList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class Nasus extends PlayerClass{

	//private float[] cool = new float[14];
	private int stack = 0;
	private boolean fury = false;

	public Nasus(){
		default_health = 830;
		default_mana = 300;
		
		default_ad = 74;
		default_ap = 0;
		default_move = 111;
		
		default_regenHealth = 0.5f;
		default_regenMana = 0.5f;
		
		attackSpeed = 0.35f;

		default_armor = 37;
		default_magicresistance = 30;
		
		class_code = ClassList.Nasus;
		
		ClassName = "나서스";
		
		super.weapon = ModWeapons.nasus;

		matrix = new nasusMatrix();
		skillSet[0] = ModSkills.skill.get(ModSkills.souleater.SkillNumber);
		skillSet[1] = ModSkills.skill.get(ModSkills.siphoningstrike.SkillNumber);
		skillSet[2] = ModSkills.skill.get(ModSkills.wither.SkillNumber);
		skillSet[3] = ModSkills.skill.get(ModSkills.spiritfire.SkillNumber);
		skillSet[4] = ModSkills.skill.get(ModSkills.furyofthesands.SkillNumber);

		skills[0] = new souleater();
		skills[1] = new siphoningstrike(this);
		skills[2] = new wither();
		skills[3] = new spiritfire(this);
		skills[4] = new furyofthesands(this);
	}

	public void togglefury(){
		this.fury = !this.fury;
	}

	public boolean getfury(){
		return this.fury;
	}

	public void addStack(int stack){
		this.stack += stack;
		try{
			souleater skill = souleater.class.cast(skills[0]);
			skill.setstack(this.stack);
		}
		catch (ClassCastException e){
			e.printStackTrace();
		}
	}

	public int getStack(){
		return this.stack;
	}

	@Override
	public PlayerClass copyClass(){
		return new Nasus();
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
				830,
				890,
				950,
				1010,
				1070,
				1140,
				1210,
				1280,
				1360,
				1440,
				1520,
				1620,
				1720,
				1820,
				1920,
				2020,
				2120,
				2220
		};

		final double[] mana = {
				300,
				310,
				320,
				330,
				340,
				350,
				360,
				370,
				380,
				390,
				400,
				420,
				440,
				460,
				480,
				500,
				520,
				540
		};
		final double[] hregen = {
				0.5,
				0.6,
				0.7,
				0.8,
				0.9,
				1,
				1.1,
				1.2,
				1.4,
				1.6,
				1.8,
				2.1,
				2.4,
				2.7,
				3,
				3.3,
				3.6,
				3.9
		};
		final double[] mregen = {
				0.5,
				0.6,
				0.7,
				0.8,
				0.9,
				1.1,
				1.3,
				1.5,
				1.7,
				2,
				2.3,
				2.6,
				2.9,
				3.2,
				3.5,
				3.8,
				4.1,
				4.4
		};

		final double[] armor = {
				37,
				40.5,
				44,
				47.5,
				51,
				54.5,
				58,
				61.5,
				65,
				68.5,
				72,
				75.5,
				79,
				82.5,
				86,
				89.5,
				93,
				96.5
		};

		final double[] magicresistance = {
				30,
				31,
				32,
				33,
				34,
				35,
				36,
				37,
				38,
				39,
				40,
				41.25,
				42.5,
				43.75,
				45,
				46.25,
				47.5,
				48.75
		};

		final double[] ad = {
				74,
				76,
				78,
				80,
				82,
				84,
				86,
				88,
				90,
				92,
				94,
				96.5,
				99,
				101.5,
				104,
				106.5,
				109,
				111.5
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
				111,
				111,
				111,
				111,
				111,
				111,
				111,
				111,
				111,
				111,
				111,
				111,
				111,
				111,
				111,
				111,
				111,
				111
		};
		final double[] attackspeed = {
				0.35,
				0.361,
				0.372,
				0.383,
				0.394,
				0.405,
				0.416,
				0.427,
				0.438,
				0.449,
				0.46,
				0.471,
				0.482,
				0.493,
				0.504,
				0.515,
				0.526,
				0.537
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
		wither skill2 = (wither) skills[2];
		furyofthesands skill4 = (furyofthesands) skills[4];
		data.getPlayer().sendMessage(new TextComponentString("나서스 : "));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"영혼의 포식자"+
				TextFormatting.RESET +" : 나서스가 입힌 물리 피해의 "
				+ String.format("%.1f",skills[0].getskilldamage()[lv-1]) + "%를 회복합니다."));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"흡수의 일격"+
				TextFormatting.RESET +" : 나서스의 다음 기본 공격은 " +
				String.format("%.1f",skills[1].getskilldamage()[lv-1]) +"(+"+TextFormatting.LIGHT_PURPLE +String.format("%d",(this.stack)) +
				TextFormatting.RESET + ")의 물리 피해를 입힙니다. 흡수의 일격으로 적을 처치하면 흡수의 일격과 영혼의 불길의 피해가 영구적으로 6 증가합니다. 적 플레이어, 에픽 몬스터를 처치시 12가 증가합니다. 쿨타임 " +
				TextFormatting.GOLD +skills[1].getcooldown()[lv-1]+
				TextFormatting.RESET+"초  소모값 : " + skills[1].getskillcost()[lv-1] ));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"쇠약"+
				TextFormatting.RESET +" : 나서스가 바라 보고 있는 적을 향해 노화를 촉진시켜" +
				TextFormatting.DARK_PURPLE+ String.format("%.1f",skill2.getSkilldamage()[0][lv-1])+
				TextFormatting.RESET+"의 이동속도와 "+
				TextFormatting.DARK_RED+String.format("%.1f",skill2.getSkilldamage()[1][lv-1])+
				TextFormatting.RESET+"의 공격속도를 " + skill2.duration+ "초 동안 감소시킵니다. 쿨타임 : " +
				TextFormatting.GOLD +skills[2].getcooldown()[lv-1]+
				TextFormatting.RESET+"초  소모값 : " + skills[2].getskillcost()[lv-1]));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"영혼의 불길" +
				TextFormatting.RESET +" : 나서스가 대상 지역을 영혼의 불길로 태워 지역 내의 적에게 불길 표식을 남깁니다. 불길 표식이 있는 적을 공격하면 " + skills[3].getskilldamage()[lv-1] +"(+"+
				TextFormatting.BLUE +String.format("%.1f",(skills[3].getskillApcoe()[lv-1] * data.getAp()))+
				TextFormatting.RESET +")(+"+
				TextFormatting.LIGHT_PURPLE +String.format("%d",(this.stack)) +
				TextFormatting.RESET +")의 마법 피해를 입힙니다. 쿨타임 : "+
				TextFormatting.GOLD + skills[3].getcooldown()[lv-1]+
				TextFormatting.RESET+"초  소모값 : " + skills[3].getskillcost()[lv-1]));
		data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"사막의 분노" +
				TextFormatting.RESET +" : 나서스가 "+skill4.duration +"초 동안 모래 폭풍 속에서 힘을 얻어 최대 체력 " + String.format("%.1f",(skill4.getSkilldamage()[0][lv-1])) +
				" 방어력 " + skill4.getSkilldamage()[1][lv-1] +
				" 마법 저항력 " +skill4.getSkilldamage()[2][lv-1]+
				"이 증가합니다. 쿨타임 : "+
				TextFormatting.GOLD + skills[4].getcooldown()[lv-1]+
				TextFormatting.RESET+"초  소모값 : " + skills[4].getskillcost()[lv-1]));
	}
}
