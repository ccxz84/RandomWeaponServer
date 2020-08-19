package RWAPI.Character;

import RWAPI.items.weapon.WeaponBase;
import RWAPI.util.ClassList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;

public class PlayerClass implements Cloneable {
	
	public StatMatrix matrix;
	
	public ClassList class_code;
	
	public String ClassName;
	
	public float default_health;
	public float default_mana;
	
	public float default_ad;
	public float default_ap;
	public float default_move;
	
	public float default_regenHealth;
	public float default_regenMana;
	
	public float attackSpeed;
	
	public CooldownHandler handler[];
	
	protected Item[] skillSet = new Item[5];

	protected Skill[] skills = new Skill[5];


	
	//public HashMap<Integer,CooldownHandler> cool = new HashMap<Integer,CooldownHandler>();
	
	public WeaponBase weapon;

	public PlayerClass copyClass(){
		return null;
	}
	
	public void ActiveSkill(int num, EntityPlayer player) {
		switch(num) {
		case 1:
			skill1(player);
			break;
		case 2:
			skill2(player);
			break;
		case 3:
			skill3(player);
			break;
		case 4:
			skill4(player);
			break;
		}
	}

	public void skill0(EntityPlayer player) {
	}

	public void skill1(EntityPlayer player) {
		
	}
	
	public void skill2(EntityPlayer player) {
		
	}
	
	public void skill3(EntityPlayer player) {
	
	}
	
	public void skill4(EntityPlayer player) {
		
	}
	
	public void Levelup(PlayerData data) {
		int lv = data.getLevel();
		data.setAd((float) (data.getAd() - matrix.ad[lv-1] + matrix.ad[lv]));
		data.setAp((float) (data.getAp() - matrix.ap[lv-1] + matrix.ap[lv]));
		data.setRegenHealth((float) (data.getRegenHealth() - matrix.hregen[lv-1] + matrix.hregen[lv]));
		data.setRegenMana((float) (data.getRegenMana() - matrix.mregen[lv-1] + matrix.mregen[lv]));
		data.setMaxHealth((float) (data.getMaxHealth() - matrix.hp[lv-1] + matrix.hp[lv]));
		data.setMaxMana((float) (data.getMaxMana() - matrix.mana[lv-1] + matrix.mana[lv]));
		data.setCurrentHealth(data.getMaxHealth());
		data.setCurrentMana(data.getMaxMana());
		data.setMove((float) (data.getMove() - matrix.move[lv-1] + matrix.move[lv]));
		data.setAttackSpeed((float) (data.getAttackSpeed() - matrix.attackspeed[lv-1] + matrix.attackspeed[lv]));
	}
	
	
	@Override
	public Object clone() throws CloneNotSupportedException { // public ���� �ٲ�����.
		return super.clone();
	}



	public abstract class StatMatrix{
		public double[] hp;
		public double[] mana;
		public double[] hregen;
		public double[] mregen;
		public double[] ad;
		public double[] ap;
		public double[] move;
		public double[] attackspeed;
	}

	public Skill getSkill(int idx) {
		return skills[idx];
	}
	public Skill[] getSkills(){
		return skills;
	}
	public void EndGame(EntityPlayerMP player){

	}
}
