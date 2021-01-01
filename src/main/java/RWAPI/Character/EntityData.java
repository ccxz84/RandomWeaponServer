package RWAPI.Character;

import RWAPI.Character.buff.Buff;
import RWAPI.util.DamageSource.DamageSource.EnemyStatHandler;
import RWAPI.util.EntityStatus;
import net.minecraft.entity.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EntityData implements Serializable {

	private int deathgold = 0;

	private String name = "";

	private EnemyStatHandler EnemyHandler;

	protected ClientData data = new ClientData();

	private double deathexp;
	private EntityStatus status;
	protected double kill_cs = 0;

	private List<Buff> buffList;

	private int max_shield_code = 0;

	protected boolean godmode = false;

	private List<shield> Shieldlist;

	private Entity entity;

	public EntityData(Entity entity, double MaxHealth, double armor, double magicresistance, double ad, double deathexp, int deathgold, String name, double kill_cs) {
		this.entity = entity;
		this.data.MaxHealth = MaxHealth;
		this.data.armor = armor;
		this.data.magicresistance = magicresistance;
		this.data.CurrentHealth = this.data.MaxHealth;
		this.data.ad = ad;
		this.data.Enemy = name;
		this.deathexp = deathexp;
		this.deathgold = deathgold;
		this.name = name;
		this.status = EntityStatus.ALIVE;
		this.kill_cs = kill_cs;
		buffList = new ArrayList<Buff>();
		Shieldlist = new ArrayList<shield>();
	}

	/* Getter
	 *
	 */

	public Entity getEntity(){
		return this.entity;
	}

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

	public int getDeattGold() {
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

	public double getArmor(){
		return this.data.armor;
	}

	public double getMagicresistance(){
		return this.data.magicresistance;
	}

	public double getArmorpenetration(){
		return this.data.armorpenetration;
	}

	public double getMagicpenetration(){
		return this.data.magicpenetration;
	}

	public double getArmorpenetrationper(){
		return this.data.armorpenetrationper;
	}

	public double getMagicpenetrationper(){
		return this.data.magicpenetrationper;
	}

	public double getTotalShield(){
		return this.data.totalshield;
	}

	public List<shield> getShieldList(){
		return this.Shieldlist;
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

	public void setEntity(Entity entity){
		this.entity = entity;
	}

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

	public void setArmor(double armor) {
		this.data.armor = armor;
	}

	public void setMagicresistance(double magicresistance) {
		this.data.magicresistance = magicresistance;
	}

	public void setArmorpenetration(double armorpenetration) {
		this.data.armorpenetration = armorpenetration;
	}

	public void setMagicpenetration(double magicpenetration) {
		this.data.magicpenetration = magicpenetration;
	}

	public void setArmorpenetrationper(double armorpenetrationper) {
		this.data.armorpenetrationper = armorpenetrationper;
	}

	public void setMagicpenetrationper(double magicpenetrationper) {
		this.data.magicpenetrationper = magicpenetrationper;
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

	public void setDeathGold(int deathgold) {
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

	public void setTotalShield(double totalshield){
		this.data.totalshield = totalshield;
	}

	public void addShield(shield shield){
		++max_shield_code;
		shield.setShield_code(max_shield_code);
		setTotalShield(getTotalShield() + shield.getShield());
		this.Shieldlist.add(shield);
	}

	public void removeShield(shield shield){
		setTotalShield(getTotalShield() - shield.getShield());
		this.Shieldlist.remove(shield);
	}

	public void setShield(shield shield, double amount){
		double sub = shield.getShield() - amount;
		shield.setShield(amount);
		setTotalShield(getTotalShield()-sub);
	}


	/*Setter End
	 *
	 */

	public static class shield{
		int shield_code;
		double shield;

		public shield(double shield){
			this.shield = shield;
		}

		public double getShield() {
			return shield;
		}

		public int getShield_code() {
			return shield_code;
		}

		private void setShield_code(int shield_code){
			this.shield_code = shield_code;
		}

		private void setShield(double shield){
			this.shield = shield;
		}

	}
}
