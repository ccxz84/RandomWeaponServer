package RWAPI.Character;

import java.util.UUID;

import RWAPI.main;
import RWAPI.init.ModWeapons;
import RWAPI.items.skillItem.SkillBase;
import RWAPI.items.weapon.WeaponBase;
import RWAPI.packet.EnemyStatPacket;
import RWAPI.util.ExpList;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
	
	
	
	/* Getter
	 * 
	 */

	public int getLevel() {
		return this.data.level;
	}

	public float getExp() {
		return this.data.exp;
	}

	public float getRegenHealth() {
		return this.data.regenHealth;
	}

	public float getRegenMana() {
		return this.data.regenMana;
	}

	public float getAttackSpeed() {
		return this.data.attackSpeed;
	}

	public int getGold() {
		return this.data.Gold;
	}
	
	public float getCool(int id) {
		return this.data.cool[id];
	}
	
	public Item getSkill(int id) {
		return this.skillSet[id];
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

	public void setExp(float exp) {
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

	public void setRegenHealth(float regenHealth) {
		this.data.regenHealth = regenHealth;
	}

	public void setRegenMana(float regenMana) {
		this.data.regenMana = regenMana;
	}

	public void setAttackSpeed(float attackSpeed) {
		this.data.attackSpeed = attackSpeed;
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
	
	/*Setter End
	 * 
	 */
	
	public PlayerData(EntityPlayerMP player){
		super(100,0,150,300,player.getName());
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
	}
}
