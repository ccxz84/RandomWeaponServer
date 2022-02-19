package RWAPI.Character;

import RWAPI.Character.buff.Buff;
import RWAPI.game.event.StatChangeEventHandle;
import RWAPI.util.DamageSource.DamageSource.EnemyStatHandler;
import RWAPI.util.EntityStatus;
import RWAPI.util.NetworkUtil;
import RWAPI.util.StatList;
import net.minecraft.entity.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EntityData extends NetworkUtil.Abstractmessage {

	private static final long serialVersionUID = 101L;

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

	private Semaphore lock = new Semaphore(1);

	public EntityData(Entity entity, double MaxHealth, double armor, double magicresistance, double ad, double deathexp, int deathgold, String name, double kill_cs) {
		this.entity = entity;
		this.data.MaxHealth.setData(MaxHealth);
		this.data.armor.setData(armor);
		this.data.magicresistance.setData(magicresistance);
		this.data.CurrentHealth.setData(MaxHealth);
		this.data.ad.setData(ad);
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
		return this.data.MaxHealth.getData();
	}

	public double getCurrentHealth() {
		return this.data.CurrentHealth.getData();
	}

	public double getMaxMana() {
		return this.data.MaxMana.getData();
	}

	public double getCurrentMana() {
		return this.data.CurrentMana.getData();
	}

	public double getAd() {
		return this.data.ad.getData();
	}

	public double getAp() {
		return this.data.ap.getData();
	}

	public double getMove() {
		return this.data.move.getData();
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
		return this.data.armor.getData();
	}

	public double getMagicresistance(){
		return this.data.magicresistance.getData();
	}

	public double getArmorpenetration(){
		return this.data.armorpenetration.getData();
	}

	public double getMagicpenetration(){
		return this.data.magicpenetration.getData();
	}

	public double getArmorpenetrationper(){
		return this.data.armorpenetrationper.getData();
	}

	public double getMagicpenetrationper(){
		return this.data.magicpenetrationper.getData();
	}

	public double getTotalShield(){
		return this.data.totalshield.getData();
	}

	public int getSkillacc(){
		return this.data.skillacc.getData();
	}

	public List<shield> getShieldList(){
		return this.Shieldlist;
	}

	public List<Buff> getBuffList(){
		return this.buffList;
	}

	public Semaphore getLock(){
		return this.lock;
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
		this.data.MaxHealth.setData(MaxHealth);
	}

	public void setCurrentHealth(double CurrentHealth) {
		this.data.CurrentHealth.setData(CurrentHealth);
	}

	public void setMaxMana(double MaxMana) {
		this.data.MaxMana.setData(MaxMana);
	}

	public void setCurrentMana(double CurrentMana) {
		this.data.CurrentMana.setData(CurrentMana);
	}

	public void setArmor(double armor) {
		this.data.armor.setData(armor);
	}

	public void setMagicresistance(double magicresistance) {
		this.data.magicresistance.setData(magicresistance);
	}

	public void setArmorpenetration(double armorpenetration) {
		this.data.armorpenetration.setData(armorpenetration);
	}

	public void setMagicpenetration(double magicpenetration) {
		this.data.magicpenetration.setData(magicpenetration);
	}

	public void setArmorpenetrationper(double armorpenetrationper) {
		this.data.armorpenetrationper.setData(armorpenetrationper);
	}

	public void setMagicpenetrationper(double magicpenetrationper) {
		this.data.magicpenetrationper.setData(magicpenetrationper);
	}

	public void setAd(double ad) {
		this.data.ad.setData(ad);
	}

	public void setAp(double ap) {
		this.data.ap.setData(ap);
	}

	public void setMove(double move) {
		this.data.move.setData(move);
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

	public void setSkillacc(int skillacc){
		this.data.skillacc.setData(skillacc);
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
		this.data.totalshield.setData(totalshield);
	}

	public void setData(ClientData data){
		this.data = data;
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
