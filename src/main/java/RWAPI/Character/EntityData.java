package RWAPI.Character;

import RWAPI.util.DamageSource.EnemyStatHandler;
import RWAPI.util.EntityStatus;

public class EntityData {
	
	private float deathgold = 0;

	private String name = "";
	
	private EnemyStatHandler EnemyHandler;
	
	protected ClientData data = new ClientData();
	protected ClientData Enemydata;

	private float deathexp;
	private EntityStatus status;
	protected int kill_cs = 0;
	
	public EntityData(float MaxHealth, float ad,float deathexp,float deathgold, String name,int kill_cs) {
		this.data.MaxHealth = MaxHealth;
		this.data.CurrentHealth = this.data.MaxHealth;
		this.data.ad = ad;
		this.deathexp = deathexp;
		this.deathgold = deathgold;
		this.name = name;
		this.Enemydata = new ClientData(this,false);
		this.status = EntityStatus.ALIVE;
		this.kill_cs = kill_cs;
	}
	
	/* Getter
	 * 
	 */
	
	public float getMaxHealth() {
		return this.data.MaxHealth;
	}

	public float getCurrentHealth() {
		return this.data.CurrentHealth;
	}

	public float getMaxMana() {
		return this.data.MaxMana;
	}

	public float getCurrentMana() {
		return this.data.CurrentMana;
	}
	
	public float getAd() {
		return this.data.ad;
	}
	
	public float getAp() {
		return this.data.ap;
	}
	
	public float getMove() {
		return this.data.move;
	}
	
	public String getName() {
		return this.name;
	}
	
	public EnemyStatHandler getEnemyHandler() {
		return this.EnemyHandler;
	}
	
	public float getDeathExp() {
		return this.deathexp;
	}
	
	public float getDeattGold() {
		return this.deathgold;
	}
	
	public ClientData getData() {
		return this.data;
	}

	public EntityStatus getStatus(){
		return this.status;
	}

	public int getKill_cs(){
		return this.kill_cs;
	}
	
	/*Getter End
	 * 
	 */
	
	/*Setter
	 * 
	 */
	
	public void setMaxHealth(float MaxHealth) {
		this.data.MaxHealth = MaxHealth;
	}

	public void setCurrentHealth(float CurrentHealth) {
		this.data.CurrentHealth = CurrentHealth;
	}

	public void setMaxMana(float MaxMana) {
		this.data.MaxMana = MaxMana;
	}

	public void setCurrentMana(float CurrentMana) {
		this.data.CurrentMana = CurrentMana;
	}
	
	public void setAd(float ad) {
		this.data.ad = ad;
	}
	
	public void setAp(float ap) {
		this.data.ap = ap;
	}
	
	public void setMove(float move) {
		this.data.move = move;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setEnemyHandler(EnemyStatHandler EnemyHandler) {
		this.EnemyHandler = EnemyHandler;
	}
	
	public void setDeathExp(float deathexp) {
		this.deathexp = deathexp;
	}
	
	public void setDeathGold(float deathgold) {
		this.deathgold = deathgold;
	}

	public void setStatus(EntityStatus stat){
		this.status = stat;
	}
	
	/*Setter End
	 * 
	 */
}
