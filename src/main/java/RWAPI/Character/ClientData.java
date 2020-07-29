package RWAPI.Character;

import java.io.Serializable;

import RWAPI.util.ExpList;
import net.minecraft.item.Item;

public class ClientData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected boolean start = false;
	
	protected boolean EnemyStat = false;
	
	protected float MaxHealth = 100; 
	protected float CurrentHealth= 100;
	
	protected float MaxMana= 100;
	protected float CurrentMana= 100;
	
	protected float ad = 0;
	protected float ap= 0;
	protected float move = 100;
	protected int level = 0;
	protected float exp = 0;
	protected float expmax = 0;
	
	protected float regenHealth = 0;
	protected float regenMana = 0;
	
	protected float attackSpeed;
	
	protected int Gold = 0;
	
	protected String timerFlag = "";
	protected int timer = 0;
	
	protected int[] skillSet = new int[5];
	
	protected float[] cool = new float[14];
	
	protected String Enemy;

	protected float total_score;
	protected int kill;
	protected int death;
	protected int cs;
	
	public ClientData() {
		
	}
	
	public ClientData(boolean start, float playerMaxHealth, float playerCurrentHealth, float playerMaxMana,
			float playerCurrentMana, float ad, float ap, float move, int level, float exp, float regenHealth,
			float regenMana, int gold, String timerFlag, int timer, int[] skillSet,float[] cool) {
		this.start = start;
		MaxHealth = playerMaxHealth;
		CurrentHealth = playerCurrentHealth;
		MaxMana = playerMaxMana;
		CurrentMana = playerCurrentMana;
		this.ad = ad;
		this.ap = ap;
		this.move = move;
		this.level = level;
		this.regenHealth = regenHealth;
		this.regenMana = regenMana;
		Gold = gold;
		this.timerFlag = timerFlag;
		this.timer = timer;
		this.skillSet = skillSet;
		this.cool = cool;
	}
	
	public ClientData(PlayerData data, boolean start, String timerFlag, int timer) {
		this.start = start;
		MaxHealth = data.getMaxHealth();
		CurrentHealth = data.getCurrentHealth();
		MaxMana = data.getMaxMana();
		CurrentMana = data.getCurrentMana();
		this.ad = data.getAd();
		this.ap = data.getAp();
		this.move = data.getMove();
		this.level = data.getLevel();
		this.expmax = ExpList.getLevelMaxExp(level);
		this.regenHealth = data.getRegenHealth();
		this.regenMana = data.getRegenMana();
		Gold = data.getGold();
		this.timerFlag = timerFlag;
		this.timer = timer;
		//this.cool = data.cool;
		this.exp = data.getExp();
		this.attackSpeed = data.getAttackSpeed();
		this.total_score = data.getTotal_score();
		this.kill = data.getKill();
		this.death = data.getDeath();
		this.cs = data.getCs();
		
		for(int i = 0; i<5;i++) {
			skillSet[i] = Item.getIdFromItem(data.getSkill(i));
		}
	}
	
	public ClientData(EntityData data, boolean EnemyStat) {
		this.CurrentHealth = data.getCurrentHealth();
		this.MaxHealth = data.getMaxHealth();
		this.Enemy = data.getName();
		this.EnemyStat = EnemyStat;
	}
	
	public ClientData(boolean EnemyStat) {
		this.EnemyStat = EnemyStat;
	}
	
	public void setTimerFlag(String Flag) {
		this.timerFlag = Flag;
	}
	
	public void setTimer(int timer) {
		this.timer = timer;
	}
	
	public void setEnemyStat(boolean EnemyStat) {
		this.EnemyStat = EnemyStat;
	}
	
	
}

