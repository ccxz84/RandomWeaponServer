package RWAPI.Character;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.UUID;

import RWAPI.items.gameItem.ItemBase;
import RWAPI.items.inventory.Inventory;
import RWAPI.main;
import RWAPI.init.ModWeapons;
import RWAPI.items.skillItem.SkillBase;
import RWAPI.items.weapon.WeaponBase;
import RWAPI.packet.EnemyStatPacket;
import RWAPI.util.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public class PlayerData extends EntityData{
	
	private String timerFlag = "";
	private int timer = 0;
	
	private Item[] skillSet = new Item[5];
	
	private PlayerClass playerclass = new PlayerClass();
	
	private EntityPlayerMP player;
	
	public WeaponBase weapon;
	public boolean nonWorking;
	public boolean recallFlag;
	private recallWait waitTimer;
	private shopTimer shopTimer;
	private deathTimer deathTimer;

	private int Respawn_time = Reference.RESPAWNFUND_TIME;

	private double total_score;
	private int kill;
	private int death;
	private int cs;

	private InventoryChangeHandle invhandler;


	private double item_hp;
	private double item_mana;
	private double item_ad;
	private double item_ap;
	private double item_move;
	private double item_hpregen;
	private double item_manaregen;

	public void setRespawn(){
		setStatus(EntityStatus.RESPANW);
		getPlayer().connection.setPlayerLocation(-56, 53, 107, getPlayer().rotationYaw, getPlayer().rotationPitch);
		getData().setTimerFlag("부활 대기 시간");
		resetPlayer();
		deathTimer = new deathTimer(this,getPlayer());
	}

	private void resetPlayer(){
		setCurrentHealth(getMaxHealth());
		setCurrentMana(getMaxMana());
	}
	
	
	
	/* Getter
	 * 
	 */

	public int getLevel() {
		return this.data.level;
	}

	public double getExp() {
		return this.data.exp;
	}

	public double getRegenHealth() {
		return this.data.regenHealth;
	}

	public double getRegenMana() {
		return this.data.regenMana;
	}

	public double getAttackSpeed() {
		return this.data.attackSpeed;
	}

	public int getGold() {
		return this.data.Gold;
	}
	
	public double getCool(int id) {
		return this.data.cool[id];
	}
	
	public Item getSkill(int id) {
		return this.skillSet[id];
	}

	public int getRespawn_time() {
		return Respawn_time;
	}

	public double getTotal_score() {
		return total_score;
	}

	public int getKill() {
		return kill;
	}

	public int getDeath() {
		return death;
	}

	public int getCs() {
		return cs;
	}

	/*Getter End
	 * 
	 */
	
	/*Setter
	 * 
	 */


	public void setLevel(int level) {
		this.data.level = level;
	}

	public void setExp(double exp) {
		if(this.data.level >= 12) {
			return;
		}
		if(exp >= ExpList.getLevelMaxExp(this.data.level)) {
			this.data.exp = exp - ExpList.getLevelMaxExp(this.data.level);
			this.playerclass.Levelup(this);
			this.data.level++;
			this.data.expmax = ExpList.getLevelMaxExp(this.data.level);
		}
		else {
			this.data.exp = exp;
		}
	}

	public void setRegenHealth(double regenHealth) {
		this.data.regenHealth = regenHealth;
	}

	public void setRegenMana(double regenMana) {
		this.data.regenMana = regenMana;
	}

	public void setAttackSpeed(double attackSpeed) {
		this.data.attackSpeed = attackSpeed;
		player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).setBaseValue(4.0f*this.data.attackSpeed);
		System.out.println(player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getBaseValue());
	}

	public void setGold(int gold) {
		this.data.Gold = gold;
	}
	
	public void setSkill(int id, SkillBase skill) {
		this.skillSet[id] = skill;
		this.data.skillSet[id] = Item.getIdFromItem(skill);
	}
	
	public void setCool(int id, float cool) {
		this.data.cool[id] = cool;
	}
	
	public void setPlayer(EntityPlayerMP player) {
		this.player = player;
	}

	public void setRecall(boolean flag){
		if(flag == true && getStatus().equals(EntityStatus.ALIVE) ){
			this.waitTimer = new recallWait(8,this, player);
		}
	}

	public void setShop(boolean flag){
		if(flag == true && getStatus().equals(EntityStatus.ALIVE)){
			this.shopTimer = new shopTimer(this,player);
			this.player.connection.setPlayerLocation(-56,53,107,this.player.rotationYaw, this.player.rotationPitch);
			this.getData().setTimerFlag("상점 시간");
			//상점으로 텔레포트
		}
	}

	public void setRespawn_time(int respawn_time) {
		Respawn_time = respawn_time;
	}

	public void setTotal_score(double total_score) {
		this.total_score = total_score;
		data.total_score = getTotal_score();
	}

	public void setKill(int kill) {
		this.kill = kill;
		setTotal_score(getTotal_score() + 1);
		data.kill = getKill();
	}

	public void setDeath(int death) {
		this.death = death;
		setTotal_score((float) (getTotal_score() - 0.5));
		data.death = getDeath();
	}

	public void setCs(int cs, double score) {
		setTotal_score(getTotal_score()+score);
		this.cs = cs;
		data.cs = cs;
	}

	public void resetInvhandler(){
		this.invhandler = new InventoryChangeHandle(this);
	}

	/*Setter End
	 * 
	 */
	
	public PlayerData(EntityPlayerMP player){
		super(100,0,150,300,player.getName(),0);
		this.player = player;
		this.data.level = 1;
		this.data = new ClientData(this,false,"",0);
	}

	public EntityPlayerMP getPlayer() {
		return this.player;
	}
	
	public PlayerClass get_class() {
		return this.playerclass;
	}
	
	public void appointed_Class(PlayerClass _class) {
		this.playerclass = _class;
		this.setMaxHealth(_class.default_health);
		this.setMaxMana(_class.default_mana);
		this.setCurrentHealth(_class.default_health);
		this.setCurrentMana(_class.default_mana);
		this.setAttackSpeed(_class.attackSpeed);
		this.setAd(_class.default_ad);
		this.setAp(_class.default_ap);
		this.setRegenHealth(_class.default_regenHealth);
		this.setRegenMana(_class.default_regenMana);
		this.setMove(_class.default_move);
		for(int i = 0; i< 5; i++) {
			this.setSkill(i, (SkillBase) _class.skillSet[i]);
		}
		this.weapon = _class.weapon;
		this.player.inventory.setInventorySlotContents(0, new ItemStack(weapon));
		this.player.inventory.setInventorySlotContents(9, new ItemStack(weapon));
	}

	public void resetgame(){
		this.invhandler.reset();
	}

	abstract class timer{
		protected int timer = 0;
		protected int time;
		protected PlayerData data;
		protected EntityPlayerMP player;

		public timer(int timer, PlayerData data, EntityPlayerMP player){
			this.time = timer * 40;
			this.data = data;
			this.player = player;
			MinecraftForge.EVENT_BUS.register(this);
		}

		@SubscribeEvent
		abstract public void skillTimer(TickEvent.ServerTickEvent event) throws Throwable;
	}

	class recallWait extends timer{
		protected int timer = 0;
		private  int time;
		private PlayerData data;
		private EntityPlayerMP player;

		private int x;
		private int y;
		private int z;

		public recallWait(int timer, PlayerData data, EntityPlayerMP player){
			super(timer, data, player);
			this.x = (int) player.posX;
			this.y = (int) player.posY;
			this.z = (int) player.posZ;
			MinecraftForge.EVENT_BUS.register(this);
		}

		@SubscribeEvent
		public void skillTimer(TickEvent.ServerTickEvent event) throws Throwable {
			if(timer > time) {
				data.setShop(true);
				setStatus(EntityStatus.SHOP);
				data.recallFlag = false;
				//집으로 이동 및 플래그 지정
				cancel();
			}
			if((int)player.posX != x || (int)player.posY != y || (int)player.posZ != z || data.nonWorking == true){
				System.out.println((int)player.posX + x );
				cancel();
			}
			timer++;

		}



		public void cancel() {
			MinecraftForge.EVENT_BUS.unregister(this);
		}
	}

	class shopTimer extends timer{

		public shopTimer(PlayerData data, EntityPlayerMP player){
			super(Reference.SHOPUSAGE_TIME * 40,data,player);

		}

		@SubscribeEvent
		public void skillTimer(TickEvent.ServerTickEvent event) throws Throwable {
			if(timer > time) {
				cancel();
				MinecraftForge.EVENT_BUS.unregister(this);
			}
			data.getData().setTimer((this.time - timer)/40);
			timer++;
		}
		
		public void cancel(){
			double[] point = spawnpoint.getRandomSpawnPoint();
			data.getPlayer().connection.setPlayerLocation(point[0], point[1], point[2], data.getPlayer().rotationYaw, data.getPlayer().rotationPitch);
			data.setStatus(EntityStatus.ALIVE);
			data.getData().setTimerFlag("게임 시간");
		}
		//상점에서 바로 나올 수 있는 기능 추가
	}

	class deathTimer extends timer {

		public deathTimer(PlayerData data, EntityPlayerMP player) {
			super(data.getRespawn_time(), data, player);
		}

		@Override
		public void skillTimer(ServerTickEvent event) throws Throwable {
			if(timer > time) {
				cancel();
				MinecraftForge.EVENT_BUS.unregister(this);
			}
			data.getData().setTimer((this.time - timer)/40);
			timer++;
		}

		public void cancel(){
			double[] point = spawnpoint.getRandomSpawnPoint();
			data.getPlayer().connection.setPlayerLocation(point[0], point[1], point[2], data.getPlayer().rotationYaw, data.getPlayer().rotationPitch);
			data.setStatus(EntityStatus.ALIVE);
			data.getData().setTimerFlag("게임 시간");
			data.setRespawn_time(data.getRespawn_time()+Reference.RESPAWNFUND_TIME);
		}
	}

	private class InventoryChangeHandle{

		InventoryPlayer inven;
		PlayerData data;

		private InventoryChangeHandle(PlayerData data){
			this.data = data;
			inven = new InventoryPlayer(data.getPlayer());
			copy();
			MinecraftForge.EVENT_BUS.register(this);
		}

		@SubscribeEvent
		public void itemHandler(TickEvent.ServerTickEvent event){
			for(int i = 1; i < 9; i++){
				if(!data.getPlayer().inventory.getStackInSlot(i).getItem().equals(inven.getStackInSlot(i).getItem())){
					System.out.println(inven.getStackInSlot(i));
					System.out.println(data.getPlayer().inventory.getStackInSlot(i));
					//System.out.println(inven.getStackInSlot(i).equals();
					if(inven.getStackInSlot(i).equals(ItemStack.EMPTY)){
						System.out.println("run");
						ItemBase item = (ItemBase) data.getPlayer().inventory.mainInventory.get(i).getItem();
						double[] stat = item.getstat();
						data.setAd(data.getAd() + stat[0]);
						data.setAp(data.getAp() + stat[1]);

						data.setMaxHealth(data.getMaxHealth()+ stat[2]);
						data.setCurrentHealth(data.getCurrentHealth() + stat[2]);

						data.setMaxMana(data.getMaxMana()+ stat[3]);
						data.setCurrentMana(data.getCurrentMana() + stat[3]);

						data.setMove(data.getMove() + stat[4]);
						data.setAttackSpeed(data.getAttackSpeed() + stat[5]);
						data.setRegenHealth(data.getRegenHealth() + stat[6]);
						data.setRegenMana(data.getRegenMana() + stat[7]);
					}
					if(data.getPlayer().inventory.mainInventory.get(i).equals(ItemStack.EMPTY)){
						//System.out.println(inven.mainInventory.get(i).getItem() + " " + data.getPlayer().inventory.mainInventory.get(i));
						ItemBase item = (ItemBase) inven.mainInventory.get(i).getItem();
						double[] stat = item.getstat();
						data.setAd(data.getAd() - stat[0]);
						data.setAp(data.getAp() - stat[1]);

						data.setMaxHealth(data.getMaxHealth() - stat[2]);
						data.setCurrentHealth(data.getCurrentHealth() - stat[2]);

						data.setMaxMana(data.getMaxMana() - stat[3]);
						data.setCurrentMana(data.getCurrentMana() - stat[3]);

						data.setMove(data.getMove() - stat[4]);
						data.setAttackSpeed(data.getAttackSpeed() - stat[5]);
						data.setRegenHealth(data.getRegenHealth() - stat[6]);
						data.setRegenMana(data.getRegenMana() - stat[7]);

					}
				}
			}
			copy();

		}

		private void copy(){
			for(int i = 1 ; i < 9; i++){
				if(data.getPlayer().inventory.getStackInSlot(i).equals(ItemStack.EMPTY)){
					this.inven.setInventorySlotContents(i,ItemStack.EMPTY);
					continue;
				}
				this.inven.setInventorySlotContents(i,data.getPlayer().inventory.getStackInSlot(i).copy());
			}
		}
		public void reset(){
			MinecraftForge.EVENT_BUS.unregister(this);
		}
	}
}
