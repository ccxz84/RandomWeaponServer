package RWAPI.Character.MasterYi;

import RWAPI.Character.Leesin.Leesin;
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
		
		super.weapon = ModWeapons.leesin;
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
}
