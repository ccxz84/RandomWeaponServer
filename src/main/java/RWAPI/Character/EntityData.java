package RWAPI.Character;

import RWAPI.Character.buff.Buff;
import RWAPI.util.DamageSource.EnemyStatHandler;
import RWAPI.util.EntityStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EntityData implements Serializable {

	private double deathgold = 0;

	private String name = "";

	private EnemyStatHandler EnemyHandler;

	protected ClientData data = new ClientData();

	private double deathexp;
	private EntityStatus status;
	protected double kill_cs = 0;

	private List<Buff> buffList;

	protected boolean godmode = false;

	public EntityData(double MaxHealth, double ad,double deathexp,double deathgold, String name,double kill_cs) {
		this.data.MaxHealth = MaxHealth;
		this.data.CurrentHealth = this.data.MaxHealth;
		this.data.ad = ad;
		this.data.Enemy = name;
		this.deathexp = deathexp;
		this.deathgold = deathgold;
		this.name = name;
		this.status = EntityStatus.ALIVE;
		this.kill_cs = kill_cs;
		buffList = new ArrayList<Buff>();
	}

	/* Getter
	 *
	 */

	public double getMaxHealth() {
		return this.data.MaxHealth;
	}

	public double getCurrentHealth() {
		return this.data.CurrentHealth;
	}

	public double getMaxMana() {
		return this.data.MaxMana;
	}

	public double getCurrentMana() {
		return this.data.CurrentMana;
	}

	public double getAd() {
		return this.data.ad;
	}

	public double getAp() {
		return this.data.ap;
	}

	public double getMove() {
		return this.data.move;
	}

	public String getName() {
		return this.name;
	}

	public EnemyStatHandler getEnemyHandler() {
		return this.EnemyHandler;
	}

	public double getDeathExp() {
		return this.deathexp;
	}

	public double getDeattGold() {
		return this.deathgold;
	}

	public ClientData getData() {
		return this.data;
	}

	public EntityStatus getStatus(){
		return this.status;
	}

	public double getKill_cs(){
		return this.kill_cs;
	}

	public boolean getgodmod(){
		return this.godmode;
	}




	public Buff getbuff(int idx){
		return buffList.get(idx);
	}

	/*Getter End
	 *
	 */

	/*Setter
	 *
	 */

	public void setMaxHealth(double MaxHealth) {
		this.data.MaxHealth = MaxHealth;
	}

	public void setCurrentHealth(double CurrentHealth) {
		this.data.CurrentHealth = CurrentHealth;
	}

	public void setMaxMana(double MaxMana) {
		this.data.MaxMana = MaxMana;
	}

	public void setCurrentMana(double CurrentMana) {
		this.data.CurrentMana = CurrentMana;
	}

	public void setAd(double ad) {
		this.data.ad = ad;
	}

	public void setAp(double ap) {
		this.data.ap = ap;
	}

	public void setMove(double move) {
		this.data.move = move;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEnemyHandler(EnemyStatHandler EnemyHandler) {
		this.EnemyHandler = EnemyHandler;
	}

	public void setDeathExp(double deathexp) {
		this.deathexp = deathexp;
	}

	public void setDeathGold(double deathgold) {
		this.deathgold = deathgold;
	}

	public void setStatus(EntityStatus stat){
		this.status = stat;
	}

	public void setGodmode(boolean godmode){
		this.godmode = godmode;
	}

	public void addBuff(Buff buff){
		this.buffList.add(buff);
	}

	public void setBuff(int idx, Buff buff){
		this.buffList.set(idx,buff);
	}

	public void removeBuff(Buff buff){
		this.buffList.remove(buff);
	}

	/*Setter End
	 *
	 */
}
