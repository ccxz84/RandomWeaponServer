package RWAPI.Character;

import java.io.Serializable;

import RWAPI.util.ExpList;
import RWAPI.util.NetworkUtil;
import net.minecraft.item.Item;
import scala.Int;

public class ClientData extends NetworkUtil.Abstractmessage {
	
	private static final long serialVersionUID = 1L;

    protected boolean start = false;
	
	protected boolean EnemyStat = false;
	
	protected dataRef<Double> MaxHealth = new dataRef<Double>((double)100);
	protected dataRef<Double> CurrentHealth = new dataRef<Double>((double)100);
	
	protected dataRef<Double> MaxMana = new dataRef<Double>((double)100);
	protected dataRef<Double> CurrentMana = new dataRef<Double>((double)100);
	
	protected dataRef<Double> ad = new dataRef<Double>((double)0);
	protected dataRef<Double> ap = new dataRef<Double>((double)0);
	protected dataRef<Double> move = new dataRef<Double>((double)100);
	protected dataRef<Integer> level = new dataRef<Integer>(0);
	protected dataRef<Double> exp = new dataRef<Double>((double)0);
	protected dataRef<Double> expmax = new dataRef<Double>((double)0);

	protected dataRef<Double> regenHealth = new dataRef<Double>((double)0);
	protected dataRef<Double> regenMana = new dataRef<Double>((double)0);

	protected dataRef<Double> armor = new dataRef<Double>((double)0);
	protected dataRef<Double> magicresistance = new dataRef<Double>((double)0);

	protected dataRef<Double> armorpenetration = new dataRef<Double>((double)0);
	protected dataRef<Double> magicpenetration = new dataRef<Double>((double)0);

	protected dataRef<Double> armorpenetrationper = new dataRef<Double>((double)0);
	protected dataRef<Double> magicpenetrationper = new dataRef<Double>((double)0);

	protected dataRef<Double> attackSpeed = new dataRef<Double>((double)0);

	protected dataRef<Double> totalshield = new dataRef<Double>((double)0);

	protected dataRef<Integer> Gold = new dataRef<Integer>(0);

	protected dataRef<Integer> skillacc = new dataRef<Integer>(0);
	
	protected String timerFlag = "";
	public int timer = 0;
	
	protected int[] skillSet = new int[5];
	
	protected double[] cool = new double[14];
	
	public String Enemy;

	protected dataRef<Double> total_score = new dataRef<Double>((double)0);
	protected dataRef<Integer> kill = new dataRef<Integer>(0);
	protected dataRef<Integer> death = new dataRef<Integer>(0);
	protected dataRef<Integer> cs = new dataRef<Integer>(0);
	
	public ClientData() {
		
	}
	
	public ClientData(boolean start, double playerMaxHealth, double playerCurrentHealth, double playerMaxMana,
			double playerCurrentMana, double ad, double ap, double move, int level, double exp, double regenHealth,
					  double regenMana, int gold, String timerFlag, int timer, int[] skillSet,double[] cool) {
		this.start = start;
		this.MaxHealth.setData(playerMaxHealth);
		this.CurrentHealth.setData(playerCurrentHealth);
		this.MaxMana.setData(playerMaxMana);
		this.CurrentMana.setData(playerCurrentMana);
		this.ad.setData(ad);
		this.ap.setData(ap);
		this.move.setData(move);
		this.level.setData(level);
		this.regenHealth.setData(regenHealth);
		this.regenMana.setData(regenMana);
		this.Gold.setData(gold);
		this.timerFlag = timerFlag;
		this.timer = timer;
		this.skillSet = skillSet;
		this.cool = cool;
	}
	
	public ClientData(PlayerData data, boolean start, String timerFlag, int timer) {
		this.start = start;
		this.MaxMana.setData(data.getMaxMana());
		this.CurrentMana.setData(data.getCurrentMana());
		this.CurrentHealth.setData(data.getCurrentHealth());
		this.MaxHealth.setData(data.getMaxHealth());
		this.ad.setData(data.getAd());
		this.ap.setData(data.getAp());
		this.move.setData(data.getMove());
		this.level.setData(data.getLevel());
		this.expmax.setData((double) ExpList.getLevelMaxExp(this.level.getData()));
		this.regenHealth.setData(data.getRegenHealth());
		this.regenMana.setData(data.getRegenMana());
		this.Gold.setData(data.getGold());
		this.timerFlag = timerFlag;
		this.timer = timer;
		//this.cool = data.cool;
		this.exp.setData(data.getExp());
		this.attackSpeed.setData(data.gettotalAttackSpeed());
		this.total_score.setData(data.getTotal_score());
		this.kill.setData(data.getKill());
		this.death.setData(data.getDeath());
		this.cs.setData(data.getCs());
		this.Enemy = data.getName();

		
		for(int i = 0; i<5;i++) {
			skillSet[i] = Item.getIdFromItem(data.getSkill(i));
		}
	}
	
	public ClientData(EntityData data, boolean EnemyStat) {
		this.CurrentHealth.setData(data.getCurrentHealth());
		this.MaxHealth.setData(data.getMaxHealth());
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
	
	public static class dataRef<T extends Number> implements Serializable{
		private static final long serialVersionUID = 9999L;
		private T data;

		public dataRef(T data){
			this.data = data;
		}

		public T getData(){
			return this.data;
		}

		public void setData(T data){
			this.data = data;
		}
	}
}

