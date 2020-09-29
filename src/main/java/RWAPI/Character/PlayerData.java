package RWAPI.Character;

import RWAPI.game.event.BaseEvent;
import RWAPI.game.event.ItemChangeEventHandle;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.items.skillItem.SkillBase;
import RWAPI.items.weapon.WeaponBase;
import RWAPI.main;
import RWAPI.util.*;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import org.lwjgl.input.Keyboard;

import java.util.*;

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
	private keyinputListener keyinputListener;

	private int Respawn_time = Reference.RESPAWNFUND_TIME;

	private double total_score;
	private int kill;
	private int death;
	private int cs;

	private InventoryChangeHandle invhandler;

	private ItemBase.handler itemhandler[] = new ItemBase.handler[8];

	private boolean firstDeath = false;

	private double baseAttackspeed = 0;
	private double plusAttackspeed = 0;

	public void setRespawn(){
		setStatus(EntityStatus.RESPANW);
		getPlayer().connection.setPlayerLocation(Reference.SHOPPOS[0],Reference.SHOPPOS[1],Reference.SHOPPOS[2], getPlayer().rotationYaw, getPlayer().rotationPitch);
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

	public double getBaseAttackspeed(){
		return this.baseAttackspeed;
	}

	public double getPlusAttackspeed(){
		return this.plusAttackspeed;
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

	public boolean getfirstDeath(){
		return firstDeath;
	}

	public double gettotalAttackSpeed() {
		return this.data.attackSpeed;
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
		if(this.data.level >= ExpList.values().length) {
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

	public void setPlusAttackspeed(double plusAttackspeed){
		this.plusAttackspeed = plusAttackspeed;
		this.data.attackSpeed = this.baseAttackspeed + this.baseAttackspeed * plusAttackspeed;
		player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).setBaseValue(4.0f*this.data.attackSpeed);
	}

	public void setBaseAttackspeed(double baseAttackspeed){
		this.baseAttackspeed = baseAttackspeed;
		this.data.attackSpeed = this.baseAttackspeed + this.baseAttackspeed * plusAttackspeed;
		player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).setBaseValue(4.0f*this.data.attackSpeed);
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
		if(flag == true){
			this.waitTimer = new recallWait(8,this, player);
		}
	}

	public void setShop(boolean flag){
		if(flag == true && getStatus().equals(EntityStatus.ALIVE)){
			this.shopTimer = new shopTimer(this,player);
			this.player.connection.setPlayerLocation(Reference.SHOPPOS[0],Reference.SHOPPOS[1],Reference.SHOPPOS[2],this.player.rotationYaw, this.player.rotationPitch);
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

	public void setKeyinputListener(){
		if(this.keyinputListener != null){
			this.keyinputListener.reset();
			this.keyinputListener = null;
		}
		this.keyinputListener = new keyinputListener(this);
	}

	public void setFirstDeath(boolean death){
		this.firstDeath = death;
	}

	public void inputKey(int keynum){
		if(this.keyinputListener != null){
			this.keyinputListener.inputkey(keynum);
		}
	}

	public void resetInvhandler(){
		this.invhandler = new InventoryChangeHandle(this);
	}

	private void cancelShop() {
		if(this.shopTimer != null){
			this.shopTimer.cancel();
			this.shopTimer.remove();
			this.shopTimer = null;
		}
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
		this.setBaseAttackspeed(_class.attackSpeed);
		this.setAd(_class.default_ad);
		this.setAp(_class.default_ap);
		this.setRegenHealth(_class.default_regenHealth);
		this.setRegenMana(_class.default_regenMana);
		this.setMove(_class.default_move);
		this.setGold(500);
		for(int i = 0; i< 5; i++) {
			this.setSkill(i, (SkillBase) _class.skillSet[i]);
		}
		this.weapon = _class.weapon;
		this.player.inventory.setInventorySlotContents(0, new ItemStack(weapon));
		this.player.inventory.setInventorySlotContents(9, new ItemStack(ModItems.RUBY));
	}

	public void purchaseItem(ItemStack stack){
		if(stack.getItem() instanceof ItemBase){
			ItemBase item = ((ItemBase)stack.getItem());
			int money = item.getGold();
			List<Integer> list = new ArrayList<Integer>();
			recursiveCheckItem(item,list);
			for(int i : list){
				if(getPlayer().inventory.mainInventory.get(i).getItem() instanceof ItemBase){
					ItemBase invitem = (ItemBase) getPlayer().inventory.mainInventory.get(i).getItem();
					money -= invitem.getGold();
				}
			}
			if(this.getGold() >= money){
				for(int i : list){
					getPlayer().inventory.setInventorySlotContents(i, ItemStack.EMPTY);
				}
				this.setGold(this.getGold() - money);
				getPlayer().addItemStackToInventory(stack.copy());
			}
			else{
			}

		}
	}

	private void recursiveCheckItem(ItemBase item, List<Integer> list){
		for(int i = 0; i < item.down_item.length;i++){
			ItemBase subitem = item.down_item[i];
			int idx = 0;
			boolean flag = false;
			for(ItemStack stack : this.getPlayer().inventory.mainInventory){
				if(stack.getItem().equals(subitem) && list.indexOf(idx) == -1){
					list.add(idx);
					flag = true;
					break;
				}
				idx++;
			}
			if(flag == false){
				System.out.println(item);
				recursiveCheckItem(subitem,list);
			}
		}
	}


	public void resetgame(){
		if(this.invhandler != null){
			this.invhandler.reset();
			this.invhandler = null;
		}
		if(this.keyinputListener != null){
			this.keyinputListener.reset();
			this.keyinputListener = null;
		}
		if(this.waitTimer != null){
			this.waitTimer.cancel();
			this.waitTimer = null;
		}
		if(this.shopTimer != null){
			this.shopTimer.remove();
			this.shopTimer = null;
		}
		if(this.deathTimer != null){
			this.deathTimer.remove();
			this.deathTimer = null;
		}
		if(this.get_class() != null){
			this.get_class().EndGame(this.getPlayer());
		}
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
		private int x;
		private int y;
		private int z;

		public recallWait(int timer, PlayerData data, EntityPlayerMP player){
			super(timer, data, player);
			this.x = (int) player.posX;
			this.y = (int) player.posY;
			this.z = (int) player.posZ;
			System.out.println(data == null);
			System.out.println(null == player);
			MinecraftForge.EVENT_BUS.register(this);
		}

		@SubscribeEvent
		public void skillTimer(TickEvent.ServerTickEvent event) throws Throwable {
			NetworkUtil.sendTo(player, ((double)(this.time - timer)/40)/8,"recall");
			if(timer > time) {
				NetworkUtil.sendTo(player, 0.0,"recall");
				data.setShop(true);
				setStatus(EntityStatus.SHOP);
				data.recallFlag = false;
				//집으로 이동 및 플래그 지정
				cancel();
			}
			if((int)player.posX != x || (int)player.posY != y || (int)player.posZ != z || data.nonWorking == true){
				NetworkUtil.sendTo(player, 0.0,"recall");
				cancel();
			}
			timer++;

		}


		public void cancel() {
			MinecraftForge.EVENT_BUS.unregister(this);
		}
	}

	class shopTimer extends timer{
		boolean flag = false;

		public shopTimer(PlayerData data, EntityPlayerMP player){
			super(Reference.SHOPUSAGE_TIME,data,player);
			data.setCurrentHealth(data.getMaxHealth());
		}

		@SubscribeEvent
		public void skillTimer(TickEvent.ServerTickEvent event) throws Throwable {
			if(timer > time && flag == false) {
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
			this.flag = true;
		}

		public void remove(){
			MinecraftForge.EVENT_BUS.unregister(this);
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
			if(!(data.getRespawn_time() > 60)){
				data.setRespawn_time(data.getRespawn_time()+5);
			}

		}

		public void remove(){
			MinecraftForge.EVENT_BUS.unregister(this);
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
		public void InventoryHandler(TickEvent.ServerTickEvent event){
			if(data.getPlayer().inventory.currentItem != 0){

				if(data.itemhandler[data.getPlayer().inventory.currentItem-1] != null){
					data.itemhandler[data.getPlayer().inventory.currentItem-1].ItemUse();
				}

	    		data.getPlayer().inventory.currentItem = 0;
			}
		}

		@SubscribeEvent
		public void itemHandler(TickEvent.ServerTickEvent event){
			for(int i = 1; i < 9; i++){
				if(!data.getPlayer().inventory.getStackInSlot(i).getItem().equals(inven.getStackInSlot(i).getItem())){
					if(data.getPlayer().inventory.getStackInSlot(i).getItem() instanceof ItemBase.shoes){
						boolean flag = false;
						for(int j = 0; j < 9 ; j ++ ){
							if(data.getPlayer().inventory.getStackInSlot(j).getItem() instanceof  ItemBase.shoes && i != j){
								flag = true;
								break;
							}
						}
						if(flag == true){
							for(int j = 9 ; j < 36;j++){
								if(data.getPlayer().inventory.getStackInSlot(j).equals(ItemStack.EMPTY)){
									data.getPlayer().inventory.setInventorySlotContents(j,data.getPlayer().inventory.getStackInSlot(i));
									data.getPlayer().inventory.setInventorySlotContents(i,ItemStack.EMPTY);
								}
							}
							if(!data.getPlayer().inventory.getStackInSlot(i).equals(ItemStack.EMPTY)){
								data.getPlayer().dropItem(data.getPlayer().inventory.getStackInSlot(i),true);
								data.getPlayer().inventory.setInventorySlotContents(i,ItemStack.EMPTY);
							}
							continue;
						}
					}

					if(inven.getStackInSlot(i).equals(ItemStack.EMPTY)){
						if(!(data.getPlayer().inventory.mainInventory.get(i).getItem() instanceof ItemBase))
							continue;
						ItemChangeEvent(data,data.getPlayer().inventory.mainInventory.get(i),false,BaseEvent.EventPriority.HIGHTEST);
						ItemChangeEvent(data,data.getPlayer().inventory.mainInventory.get(i),false,BaseEvent.EventPriority.HIGH);
						if(!(data.getPlayer().inventory.mainInventory.get(i).getItem() instanceof ItemBase))
							data.getPlayer().inventory.mainInventory.set(i,ItemStack.EMPTY);
						else{
							ItemBase item = (ItemBase) data.getPlayer().inventory.mainInventory.get(i).getItem();
							double[] stat = item.getstat();
							data.setAd(data.getAd() + stat[0]);
							data.setAp(data.getAp() + stat[1]);

							data.setMaxHealth(data.getMaxHealth()+ stat[2]);
							data.setCurrentHealth(data.getCurrentHealth() + stat[2]);

							data.setMaxMana(data.getMaxMana()+ stat[3]);
							data.setCurrentMana(data.getCurrentMana() + stat[3]);

							data.setMove(data.getMove() + stat[4]);
							data.setPlusAttackspeed(data.getPlusAttackspeed() + stat[5]);
							data.setRegenHealth(data.getRegenHealth() + stat[6]);
							data.setRegenMana(data.getRegenMana() + stat[7]);

							ItemChangeEvent(data,data.getPlayer().inventory.mainInventory.get(i),false,BaseEvent.EventPriority.NORMAL);
							ItemChangeEvent(data,data.getPlayer().inventory.mainInventory.get(i),false,BaseEvent.EventPriority.LOW);
							setItemhandler(i-1, item.create_handler(data,data.getPlayer().inventory.mainInventory.get(i)));
							ItemChangeEvent(data,data.getPlayer().inventory.mainInventory.get(i),false,BaseEvent.EventPriority.LOWEST);
						}
					}
					if(data.getPlayer().inventory.mainInventory.get(i).equals(ItemStack.EMPTY)){

						if(!(inven.mainInventory.get(i).getItem() instanceof ItemBase))
							continue;
						ItemChangeEvent(data,inven.mainInventory.get(i),true,BaseEvent.EventPriority.HIGHTEST);
						ItemChangeEvent(data,inven.mainInventory.get(i),true,BaseEvent.EventPriority.HIGH);
						ItemBase item = (ItemBase) inven.mainInventory.get(i).getItem();
						double[] stat = item.getstat();

						data.setAd(data.getAd() - stat[0]);
						data.setAp(data.getAp() - stat[1]);

						data.setMaxHealth(data.getMaxHealth() - stat[2]);
						data.setCurrentHealth(data.getCurrentHealth() - stat[2]);

						data.setMaxMana(data.getMaxMana() - stat[3]);
						data.setCurrentMana(data.getCurrentMana() - stat[3]);

						data.setMove(data.getMove() - stat[4]);
						data.setPlusAttackspeed(data.getPlusAttackspeed() - stat[5]);
						data.setRegenHealth(data.getRegenHealth() - stat[6]);
						data.setRegenMana(data.getRegenMana() - stat[7]);
						ItemChangeEvent(data,inven.mainInventory.get(i),true,BaseEvent.EventPriority.NORMAL);
						ItemChangeEvent(data,inven.mainInventory.get(i),true,BaseEvent.EventPriority.LOW);
						if(data.itemhandler[i-1] != null)
							data.itemhandler[i-1].removeHandler();
						setItemhandler(i-1,null);
						ItemChangeEvent(data,inven.mainInventory.get(i),true,BaseEvent.EventPriority.LOWEST);
					}
				}
			}
			copy();

		}

		private void ItemChangeEvent(PlayerData data,ItemStack stack,boolean flag, BaseEvent.EventPriority priority) {
			ItemChangeEventHandle.ItemChangeEvent event = new ItemChangeEventHandle.ItemChangeEvent(data,stack,flag);
			main.game.getEventHandler().RunEvent(event,priority);
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

	public void setItemhandler(int index, ItemBase.handler handler){
		this.itemhandler[index] = handler;
	}

	private class keyinputListener{
		private PlayerData data;
		private Queue<Integer> list;

		private keyinputListener(PlayerData data){
			this.data = data;
			list = new ArrayDeque<Integer>();
			MinecraftForge.EVENT_BUS.register(this);
		}

		public void inputkey(int keynum){
			list.add(keynum);
		}

		@SubscribeEvent
		public void keyRunEvent(TickEvent.ServerTickEvent event){

			while(!list.isEmpty()){
				int key = list.poll();
				if(key == -1 && data.getStatus() == EntityStatus.ALIVE) {
					data.get_class().clickEvent(data);
					//System.out.println(data.getPlayer().getCooledAttackStrength(0f));
				}
				else if(key == Keyboard.KEY_Z || key == Keyboard.KEY_X || key == Keyboard.KEY_C|| key == Keyboard.KEY_V && data.getStatus() == EntityStatus.ALIVE){
					data.get_class().ActiveSkill(key-Keyboard.KEY_Z+1,data.getPlayer());
				}
				else if(key ==Keyboard.KEY_B){
					if(main.game.start == GameStatus.START) {
						if(data.recallFlag != true && data.getStatus().equals(EntityStatus.ALIVE)){
							data.setRecall(true);
						}
						else if(data.getStatus().equals(EntityStatus.SHOP)){
							data.cancelShop();
						}
					}
				}
			}
		}

		public void reset(){
			MinecraftForge.EVENT_BUS.unregister(this);
		}
	}
}
