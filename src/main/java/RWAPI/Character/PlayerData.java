package RWAPI.Character;

import RWAPI.Character.buff.Buff;
import RWAPI.game.event.BaseEvent;
import RWAPI.game.event.ItemChangeEventHandle;
import RWAPI.game.event.StatChangeEventHandle;
import RWAPI.init.ModBuff;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.items.skillItem.SkillBase;
import RWAPI.items.weapon.WeaponBase;
import RWAPI.main;
import RWAPI.util.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

	private ItemBase.basic_handler basicitemhandler[] = new ItemBase.basic_handler[8];
	private List<ItemBase.inherence_handler> inherenceitemhandler[] = new List[8];
	private ItemBase.usage_handler usageitemhandler[] = new ItemBase.usage_handler[8];

	private boolean firstDeath = false;

	private ClientData.dataRef<Double> baseAttackspeed = new ClientData.dataRef<Double>((double)0);
	private ClientData.dataRef<Double> plusAttackspeed = new ClientData.dataRef<Double>((double)0);

	private ClientData.dataRef<Double> baseHealthRegen = new ClientData.dataRef<Double>((double)0);
	private ClientData.dataRef<Double> plusHealthRegen = new ClientData.dataRef<Double>((double)0);

	private ClientData.dataRef<Double> baseManaRegen = new ClientData.dataRef<Double>((double)0);
	private ClientData.dataRef<Double> plusManaRegen = new ClientData.dataRef<Double>((double)0);

	private int continuouskill = 0;

	private dashtimer dashtimer;

	private boolean GrievousWounds;

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

	public boolean getGrievousWounds(){
		return this.GrievousWounds;
	}

	public int getLevel() {
		return this.data.level.getData();
	}

	public double getExp() {
		return this.data.exp.getData();
	}

	public double getRegenHealth(){
		return this.data.regenHealth.getData();
	}

	public double getRegenMana(){
		return this.data.regenHealth.getData();
	}

	public double getBaseAttackspeed(){
		return this.baseAttackspeed.getData();
	}

	public double getPlusAttackspeed(){
		return this.plusAttackspeed.getData();
	}

	public double getBaseHealthRegen(){
		return this.baseHealthRegen.getData();
	}

	public double getPlusHealthRegen(){
		return this.plusHealthRegen.getData();
	}

	public double getBaseManaRegen(){
		return this.baseManaRegen.getData();
	}

	public double getPlusManaRegen(){
		return this.plusManaRegen.getData();
	}

	public int getGold() {
		return this.data.Gold.getData();
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
		return this.data.attackSpeed.getData();
	}

	public int getContinuouskill(){
		return this.continuouskill;
	}


	/*Getter End
	 * 
	 */
	
	/*Setter
	 * 
	 */

	public void setGrievousWounds(boolean GrievousWounds){
		this.GrievousWounds = GrievousWounds;
	}

	@Override
	public void setMaxHealth(double MaxHealth) {
		double prev = this.getMaxHealth();
		super.setMaxHealth(MaxHealth);
		StatChangeEventHandle.StatChangeEvent event = new StatChangeEventHandle.StatChangeEvent(this,StatList.MAXHEALTH,data.MaxHealth, prev);
		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event, priority);
		}
	}

	@Override
	public void setCurrentHealth(double CurrentHealth) {
		double prev = this.getCurrentHealth();
		double sub = CurrentHealth - prev >= 0? (CurrentHealth - prev) * (this.GrievousWounds ? 0.6 : 1) : CurrentHealth - prev;
		super.setCurrentHealth(prev + sub);
		StatChangeEventHandle.StatChangeEvent event = new StatChangeEventHandle.StatChangeEvent(this,StatList.CURRENTHEALTH,data.CurrentHealth, prev);
		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event, priority);
		}
	}

	@Override
	public void setMaxMana(double MaxMana) {
		double prev = this.getMaxMana();
		super.setMaxMana(MaxMana);
		StatChangeEventHandle.StatChangeEvent event = new StatChangeEventHandle.StatChangeEvent(this,StatList.MAXMANA,data.MaxMana, prev);
		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event, priority);
		}
	}

	@Override
	public void setCurrentMana(double CurrentMana) {
		double prev = this.getCurrentMana();
		super.setCurrentMana(CurrentMana);
		StatChangeEventHandle.StatChangeEvent event = new StatChangeEventHandle.StatChangeEvent(this,StatList.CURRENTMANA,data.CurrentMana, prev);
		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event, priority);
		}
	}

	@Override
	public void setArmor(double armor) {
		double prev = this.getArmor();
		super.setArmor(armor);
		StatChangeEventHandle.StatChangeEvent event = new StatChangeEventHandle.StatChangeEvent(this,StatList.ARMOR,data.armor, prev);
		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event, priority);
		}
	}

	@Override
	public void setMagicresistance(double magicresistance) {
		double prev = this.getMagicresistance();
		super.setMagicresistance(magicresistance);
		StatChangeEventHandle.StatChangeEvent event = new StatChangeEventHandle.StatChangeEvent(this,StatList.MAGICRESISTANCE,data.magicresistance, prev);
		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event, priority);
		}
	}

	@Override
	public void setArmorpenetration(double armorpenetration) {
		double prev = this.getArmorpenetration();
		super.setArmorpenetration(armorpenetration);
		StatChangeEventHandle.StatChangeEvent event = new StatChangeEventHandle.StatChangeEvent(this,StatList.ARMORPENETRATION,data.armorpenetration, prev);
		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event, priority);
		}
	}

	@Override
	public void setMagicpenetration(double magicpenetration) {
		double prev = this.getMagicpenetration();
		super.setMagicpenetration(magicpenetration);
		StatChangeEventHandle.StatChangeEvent event = new StatChangeEventHandle.StatChangeEvent(this,StatList.MAGICPENETRATION,data.magicpenetration, prev);
		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event, priority);
		}
	}

	@Override
	public void setArmorpenetrationper(double armorpenetrationper) {
		double prev = this.getArmorpenetrationper();
		super.setArmorpenetrationper(armorpenetrationper);
		StatChangeEventHandle.StatChangeEvent event = new StatChangeEventHandle.StatChangeEvent(this,StatList.ARMORPENETRATIONPER,data.armorpenetrationper, prev);
		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event, priority);
		}
	}

	@Override
	public void setMagicpenetrationper(double magicpenetrationper) {
		double prev = this.getMagicpenetrationper();
		super.setMagicpenetrationper(magicpenetrationper);
		StatChangeEventHandle.StatChangeEvent event = new StatChangeEventHandle.StatChangeEvent(this,StatList.MAGICPENETRATIONPER,data.magicpenetrationper, prev);
		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event, priority);
		}
	}

	@Override
	public void setAd(double ad) {
		double prev = this.getAd();
		super.setAd(ad);
		StatChangeEventHandle.StatChangeEvent event = new StatChangeEventHandle.StatChangeEvent(this,StatList.AD,data.ad, prev);
		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event, priority);
		}
	}

	@Override
	public void setAp(double ap) {
		double prev = this.getAp();
		super.setAp(ap);
		StatChangeEventHandle.StatChangeEvent event = new StatChangeEventHandle.StatChangeEvent(this,StatList.AP,data.ap, prev);
		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event, priority);
		}
	}

	@Override
	public void setMove(double move) {
		double prev = this.getMove();
		super.setMove(move);
		StatChangeEventHandle.StatChangeEvent event = new StatChangeEventHandle.StatChangeEvent(this,StatList.MOVE,data.move, prev);
		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event, priority);
		}
	}

	@Override
	public void setSkillacc(int skillacc) {
		int prev = this.getSkillacc();
		super.setSkillacc(skillacc);
		StatChangeEventHandle.StatChangeEvent event = new StatChangeEventHandle.StatChangeEvent(this,StatList.SKILLACC,data.skillacc, prev);
		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event, priority);
		}
	}

	public void setLevel(int level) {
		this.data.level.setData(level);
	}

	public void setExp(double exp) {

		StatChangeEventHandle.StatChangeEvent event = null;
		if(this.data.level.getData() >= ExpList.maxlevel) {
			return;
		}
		if(exp >= ExpList.getLevelMaxExp(this.data.level.getData())) {
			int prev = this.getLevel();
			this.data.exp.setData(exp - ExpList.getLevelMaxExp(this.data.level.getData()));
			this.playerclass.Levelup(this);
			this.data.level.setData(this.data.level.getData()+1);
			event = new StatChangeEventHandle.StatChangeEvent(this,StatList.LEVEL,data.level, prev);
			this.data.expmax.setData(ExpList.getLevelMaxExp(this.data.level.getData()));
		}
		else {
			double prev = this.getExp();
			this.data.exp.setData(exp);
			event = new StatChangeEventHandle.StatChangeEvent(this,StatList.EXP,data.exp, prev);
		}

		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event, priority);
		}
	}

	public void setPlusHealthRegen(double plusHealthRegen){
		double prev = this.getPlusHealthRegen();
		this.plusHealthRegen.setData(plusHealthRegen);
		StatChangeEventHandle.StatChangeEvent event = new StatChangeEventHandle.StatChangeEvent(this,StatList.PLUSREGENHEALTH,this.plusHealthRegen, prev);
		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event, priority);
		}
		this.data.regenHealth.setData((this.baseHealthRegen.getData() + this.baseHealthRegen.getData() * this.plusHealthRegen.getData()));
	}

	public void setBaseHealthRegen(double baseHealthRegen){
		double prev = this.getBaseHealthRegen();
		this.baseHealthRegen.setData(baseHealthRegen);
		StatChangeEventHandle.StatChangeEvent event = new StatChangeEventHandle.StatChangeEvent(this,StatList.BASEREGENHEALTH,this.baseHealthRegen, prev);
		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event, priority);
		}
		this.data.regenHealth.setData(this.baseHealthRegen.getData() + this.baseHealthRegen.getData() * plusHealthRegen.getData());
	}

	public void setPlusManaRegen(double plusManaRegen){
		double prev = this.getPlusManaRegen();
		this.plusManaRegen.setData(plusManaRegen);
		StatChangeEventHandle.StatChangeEvent event = new StatChangeEventHandle.StatChangeEvent(this,StatList.PLUSREGENMANA,this.plusManaRegen, prev);
		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event, priority);
		}
		this.data.regenMana.setData((this.baseManaRegen.getData() + this.baseManaRegen.getData() * this.plusManaRegen.getData()));
	}

	public void setBaseManaRegen(double baseManaRegen){
		double prev = this.getBaseManaRegen();
		this.baseManaRegen.setData(baseManaRegen);
		StatChangeEventHandle.StatChangeEvent event = new StatChangeEventHandle.StatChangeEvent(this,StatList.BASEREGENMANA,this.baseAttackspeed, prev);
		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event, priority);
		}
		this.data.regenMana.setData(this.baseManaRegen.getData() + this.baseManaRegen.getData() * plusManaRegen.getData());
	}

	public void setPlusAttackspeed(double plusAttackspeed){
		double prev = this.getPlusAttackspeed();
		this.plusAttackspeed.setData(plusAttackspeed);
		StatChangeEventHandle.StatChangeEvent event = new StatChangeEventHandle.StatChangeEvent(this,StatList.PLUSATTACKSPEED,this.plusAttackspeed, prev);
		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event, priority);
		}
		this.data.attackSpeed.setData((this.baseAttackspeed.getData() + this.baseAttackspeed.getData() * this.plusAttackspeed.getData()));
		player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).setBaseValue(4.0f*this.data.attackSpeed.getData());
	}

	public void setBaseAttackspeed(double baseAttackspeed){
		double prev = this.getBaseAttackspeed();
		this.baseAttackspeed.setData(baseAttackspeed);
		StatChangeEventHandle.StatChangeEvent event = new StatChangeEventHandle.StatChangeEvent(this,StatList.BASEATTACKSPEED,this.baseAttackspeed, prev);
		for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
			main.game.getEventHandler().RunEvent(event, priority);
		}
		this.data.attackSpeed.setData(this.baseAttackspeed.getData() + this.baseAttackspeed.getData() * plusAttackspeed.getData());
		player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).setBaseValue(4.0f*this.data.attackSpeed.getData());
	}

	public void setGold(int gold) {
		this.data.Gold.setData(gold);
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
		data.total_score.setData(total_score);
	}

	public void setKill(int kill) {
		this.kill = kill;
		setTotal_score(getTotal_score() + 1);
		data.kill.setData(getKill());
	}

	public void setDeath(int death) {
		this.death = death;
		setTotal_score((float) (getTotal_score() - 0.5));
		data.death.setData(getDeath());
	}

	public void setCs(int cs, double score) {
		setTotal_score(getTotal_score()+score);
		this.cs = cs;
		data.cs.setData(getCs());
	}

	public void setKeyinputListener(){
		if(this.keyinputListener != null){
			this.keyinputListener.reset();
			this.keyinputListener = null;
		}
		this.keyinputListener = new keyinputListener(this);
	}

	public void setContinuouskill(int continuouskill){
		this.continuouskill = continuouskill;
	}

	public void setFirstDeath(boolean death){
		this.firstDeath = death;
	}

	public void setDashtimer(){
		if(this.dashtimer == null){
			this.dashtimer = new dashtimer(3, this);
		}
		else{
			this.dashtimer.settimer();
		}
	}

	public void resetDashtimer(){
		if(this.dashtimer != null){
			this.dashtimer = null;
		}
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
		super(null,100,0,0,0,150,300,player.getName(),0);
		this.player = player;
		this.data.level.setData(1);
		this.data = new ClientData(this,false,"",0);
		this.setEntity(player);
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
		this.setBaseHealthRegen(_class.default_regenHealth);
		this.setBaseManaRegen(_class.default_regenMana);
		this.setMove(_class.default_move);
		this.setMagicresistance(_class.default_magicresistance);
		this.setArmor(_class.default_armor);
		this.setGold(500);
		for(int i = 0; i< 5; i++) {
			this.setSkill(i, (SkillBase) _class.skillSet[i]);
		}
		this.weapon = _class.weapon;
		this.player.inventory.setInventorySlotContents(0, new ItemStack(weapon));
		this.player.inventory.setInventorySlotContents(9, new ItemStack(ModItems.RUBY));
	}

	public void purchaseItem(ItemStack stack){
		NBTTagCompound nbt;

		ItemStack stack1 = new ItemStack(stack.getItem());
		if(stack1.getItem() instanceof ItemBase){
			for(Class item : ModItems.NO_OVERLAP_ITEMS){
				try{
					item.cast(stack1.getItem());
					for(int j = 0; j < 9 ; j ++ ){
						try{
							boolean flag = false;
							item.cast(this.getPlayer().inventory.getStackInSlot(j).getItem());
							for(ItemBase temp : ((ItemBase) stack1.getItem()).down_item){
								if(temp.equals(this.getPlayer().inventory.getStackInSlot(j).getItem())){
									flag = true;
									break;
								}
							}
							if(!flag)
								return;
						}
						catch (Exception e){
							continue;
						}
					}
				}catch (Exception e){
					continue;
				}
			}
			ItemBase item = ((ItemBase)stack1.getItem());
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
					getPlayer().inventory.mainInventory.set(i,ItemStack.EMPTY);
				}
				this.setGold(this.getGold() - money);
				getPlayer().addItemStackToInventory(stack.copy());
				/*for(int i = 1; i < getPlayer().inventory.mainInventory.size();i++){
					if(list.contains(i) || i == 9 || !getPlayer().inventory.mainInventory.get(i).equals(ItemStack.EMPTY)){
						continue;
					}
					else{
						getPlayer().inventory.mainInventory.set(i,stack.copy());
						break;
					}
				}*/
			}

			list = new ArrayList<Integer>();
			money = item.getGold();
			recursiveCheckItem(item,list);
			for(int i : list){
				if(getPlayer().inventory.mainInventory.get(i).getItem() instanceof ItemBase){
					ItemBase invitem = (ItemBase) getPlayer().inventory.mainInventory.get(i).getItem();
					money -= invitem.getGold();
				}
			}
			nbt = stack.getTagCompound();
			if(nbt == null){
				nbt = new NBTTagCompound();
			}

			nbt.setInteger("remaingold",money);
			stack.setTagCompound(nbt);

		}
	}

	public void recursiveCheckItem(ItemBase item, List<Integer> list){
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
		if(this.dashtimer != null){
			this.dashtimer.unregist();
			this.dashtimer = null;
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
			data.setCurrentMana(data.getMaxMana());
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
			if(data.getCurrentHealth() >= data.getMaxHealth()){
				data.setCurrentHealth(data.getMaxHealth());
			}
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
				if(data.usageitemhandler[data.getPlayer().inventory.currentItem-1] != null){
					System.out.println(data.getPlayer().inventory.getStackInSlot(data.getPlayer().inventory.currentItem));
					data.usageitemhandler[data.getPlayer().inventory.currentItem-1].ItemUse(data.getPlayer().inventory.getStackInSlot(data.getPlayer().inventory.currentItem));
				}

	    		data.getPlayer().inventory.currentItem = 0;
			}
		}

		public boolean ItemOverlap(int idx){
			for(Class item : ModItems.NO_OVERLAP_ITEMS){
				try{
					item.cast(data.getPlayer().inventory.getStackInSlot(idx).getItem());
					for(int j = 0; j < 9 ; j ++ ){
						try{
							item.cast(data.getPlayer().inventory.getStackInSlot(j).getItem());
							if(idx != j){
								//System.out.println(data.getPlayer().inventory.getStackInSlot(j).getItem() + "   " + data.getPlayer().inventory.getStackInSlot(idx).getItem());
								return true;
							}
						}
						catch (Exception e){
							continue;
						}
					}
					return false;
				}catch (Exception e){
					continue;
				}
			}

			return false;
		}

		public boolean ItemOverlapEvent(ItemBase item, Class<? extends ItemBase.inherence_handler> _class){
			for(List<ItemBase.inherence_handler> handlers : this.data.inherenceitemhandler){
				if(handlers == null)
					continue;
				for(ItemBase.inherence_handler handler : handlers){
					if(_class.equals(handler.getClass())){
						return true;
					}
				}
				//System.out.println("대상 : " + handler.getClass());

			}
			return false;
		}

		@SubscribeEvent
		public void itemHandler(TickEvent.ServerTickEvent event){
			for(int i = 1; i < 9; i++){
				if(!data.getPlayer().inventory.getStackInSlot(i).getItem().equals(inven.getStackInSlot(i).getItem())){
					if(ItemOverlap(i) == true){
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

					if(!inven.getStackInSlot(i).equals(ItemStack.EMPTY)){
						if(inven.getStackInSlot(i).getItem() instanceof ItemBase){
							ItemChangeEvent(data,inven.getStackInSlot(i),true,BaseEvent.EventPriority.HIGHTEST);
							ItemChangeEvent(data,inven.getStackInSlot(i),true,BaseEvent.EventPriority.HIGH);
							ItemBase item = (ItemBase) inven.getStackInSlot(i).getItem();
							double[] stat = item.getstat();
							data.setAd(data.getAd() - stat[0]);
							data.setAp(data.getAp() - stat[1]);

							data.setMaxHealth(data.getMaxHealth() - stat[2]);
							data.setCurrentHealth(data.getCurrentHealth() - stat[2]);

							data.setMaxMana(data.getMaxMana() - stat[3]);
							data.setCurrentMana(data.getCurrentMana() - stat[3]);

							data.setArmor(data.getArmor() - stat[4]);
							data.setMagicresistance(data.getMagicresistance() - stat[5]);

							data.setMove(data.getMove() - stat[6]);
							data.setPlusAttackspeed(data.getPlusAttackspeed() - stat[7]);
							data.setPlusHealthRegen(data.getPlusHealthRegen() - stat[8]);
							data.setPlusManaRegen(data.getPlusManaRegen() - stat[9]);
							data.setArmorpenetration(data.getArmorpenetration() - stat[10]);
							data.setMagicpenetration(data.getMagicpenetration() - stat[11]);
							data.setSkillacc((int) (data.getSkillacc() - stat[12]));

							ItemChangeEvent(data,inven.getStackInSlot(i),true,BaseEvent.EventPriority.NORMAL);
							ItemChangeEvent(data,inven.getStackInSlot(i),true,BaseEvent.EventPriority.LOW);

							if(data.basicitemhandler[i-1] != null)
								data.basicitemhandler[i-1].removeHandler();
							if(data.inherenceitemhandler[i-1] != null){
								for(ItemBase.inherence_handler handler : data.inherenceitemhandler[i-1]){
									handler.removeHandler();
								}
							}
							if(data.usageitemhandler[i-1] != null)
								data.usageitemhandler[i-1].removeHandler();
							setItemhandler(i-1,data.basicitemhandler,null);
							setInherenceitemhandler(i-1,data.inherenceitemhandler,null);
							setItemhandler(i-1,data.usageitemhandler,null);
							ItemChangeEvent(data,inven.getStackInSlot(i),true,BaseEvent.EventPriority.LOWEST);
						}
					}

					if(!data.getPlayer().inventory.getStackInSlot(i).equals(ItemStack.EMPTY)){
						if(data.getPlayer().inventory.getStackInSlot(i).getItem() instanceof ItemBase){
							ItemChangeEvent(data,data.getPlayer().inventory.getStackInSlot(i),false,BaseEvent.EventPriority.HIGHTEST);
							ItemChangeEvent(data,data.getPlayer().inventory.getStackInSlot(i),false,BaseEvent.EventPriority.HIGH);
							if(!(data.getPlayer().inventory.getStackInSlot(i).getItem() instanceof ItemBase))
								data.getPlayer().inventory.setInventorySlotContents(i,ItemStack.EMPTY);
							else{
								ItemBase item = (ItemBase) data.getPlayer().inventory.mainInventory.get(i).getItem();
								double[] stat = item.getstat();
								data.setAd(data.getAd() + stat[0]);
								data.setAp(data.getAp() + stat[1]);

								data.setMaxHealth(data.getMaxHealth()+ stat[2]);
								data.setCurrentHealth(data.getCurrentHealth() + stat[2]);

								data.setMaxMana(data.getMaxMana()+ stat[3]);
								data.setCurrentMana(data.getCurrentMana() + stat[3]);

								data.setArmor(data.getArmor() + stat[4]);
								data.setMagicresistance(data.getMagicresistance() + stat[5]);

								data.setMove(data.getMove() + stat[6]);
								data.setPlusAttackspeed(data.getPlusAttackspeed() + stat[7]);
								data.setPlusHealthRegen(data.getPlusHealthRegen() + stat[8]);
								data.setPlusManaRegen(data.getPlusManaRegen() + stat[9]);
								data.setArmorpenetration(data.getArmorpenetration() + stat[10]);
								data.setMagicpenetration(data.getMagicpenetration() + stat[11]);
								data.setSkillacc((int) (data.getSkillacc() + stat[12]));

								ItemChangeEvent(data,data.getPlayer().inventory.getStackInSlot(i),false,BaseEvent.EventPriority.NORMAL);
								ItemChangeEvent(data,data.getPlayer().inventory.getStackInSlot(i),false,BaseEvent.EventPriority.LOW);
								setItemhandler(i-1, data.basicitemhandler, item.create_basic_handler(data,data.getPlayer().inventory.getStackInSlot(i)));
								setItemhandler(i-1, data.usageitemhandler, item.create_usage_handler(data,data.getPlayer().inventory.getStackInSlot(i)));

								List<ItemBase.inherence_handler> list = new ArrayList<>();
								if(item.get_inherence_handler() != null){
									for(Class<? extends ItemBase.inherence_handler> _class : item.get_inherence_handler()){
										if(ItemOverlapEvent(item,_class) == false){
											list.add(item.create_inherence_handler(data,data.getPlayer().inventory.getStackInSlot(i),_class,i));
										}
									}
								}

								setInherenceitemhandler(i-1, data.inherenceitemhandler, list);

								ItemChangeEvent(data,data.getPlayer().inventory.getStackInSlot(i),false,BaseEvent.EventPriority.LOWEST);
							}
						}
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

	public void setItemhandler(int index,ItemBase.handler[] handlers ,ItemBase.handler handler){
		handlers[index] = handler;
	}

	public void setInherenceitemhandler(int index,List<ItemBase.inherence_handler>[] handlers ,List<ItemBase.inherence_handler> handler){
		handlers[index] = handler;
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

	private class dashtimer extends Buff {

		public dashtimer(double duration, PlayerData player, double... data) {
			super(duration, player,true,false, data);
		}

		@Override
		public void BuffTimer(ServerTickEvent event) throws Throwable {
			super.BuffTimer(event);
			player.getPlayer().setSprinting(false);
		}

		@Override
		public void setEffect() {
			player.getPlayer().setSprinting(false);
			player.addBuff(this);
		}

		@Override
		public void resetEffect() {
			player.getPlayer().setSprinting(true);
			player.removeBuff(this);
			resetDashtimer();
		}

		@Override
		public ItemStack getBuffIcon() {
			return new ItemStack(ModBuff.exhaust);
		}

		public void unregist(){
			MinecraftForge.EVENT_BUS.unregister(this);
		}

		public void settimer(){
			this.timer = 0;
		}
	}
}
