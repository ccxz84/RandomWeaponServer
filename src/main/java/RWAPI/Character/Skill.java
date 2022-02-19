package RWAPI.Character;

import net.minecraft.entity.player.EntityPlayer;

public interface Skill {

	void skillpreExecute(EntityPlayer player); //전처리 실행
	void skillExecute(EntityPlayer player); //스킬 실행
	void Skillset(EntityPlayer player); //임시함수
	void skillEnd(EntityPlayer player); //스킬 마무리
	void raiseevent(PlayerData data,double mana);
	double[] getskilldamage();
	double[] getskillAdcoe();
	double[] getskillApcoe();
	double[] getskillcost();
	double[] getcooldown();
}


