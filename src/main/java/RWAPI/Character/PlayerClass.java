package RWAPI.Character;

import java.util.HashMap;

import RWAPI.main;
import RWAPI.init.ModWeapons;
import RWAPI.items.skillItem.SkillBase;
import RWAPI.items.weapon.WeaponBase;
import RWAPI.util.ClassList;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public class PlayerClass implements Cloneable {
	
	public StatMatrix matrix;
	
	public ClassList class_code;
	
	public String ClassName;
	
	public float default_health;
	public float default_mana;
	
	public float default_ad;
	public float default_ap;
	public float default_move;
	
	public float default_regenHealth;
	public float default_regenMana;
	
	public float attackSpeed;
	
	public CooldownHandler handler[];
	
	protected Item[] skillSet = new Item[5];
	
	//public HashMap<Integer,CooldownHandler> cool = new HashMap<Integer,CooldownHandler>();
	
	public WeaponBase weapon;
	
	public void ActiveSkill(int num, EntityPlayer player) {
		switch(num) {
		case 1:
			skill1(player);
			break;
		case 2:
			skill2(player);
			break;
		case 3:
			skill3(player);
			break;
		case 4:
			skill4(player);
			break;
		}
	}
	
	public void skill1(EntityPlayer player) {
		
	}
	
	public void skill2(EntityPlayer player) {
		
	}
	
	public void skill3(EntityPlayer player) {
	
	}
	
	public void skill4(EntityPlayer player) {
		
	}
	
	public void Levelup(PlayerData data) {
		int lv = data.getLevel();
		data.setAd((float) (data.getAd() - matrix.ad[lv-1] + matrix.ad[lv]));
		data.setAp((float) (data.getAp() - matrix.ap[lv-1] + matrix.ap[lv]));
		data.setRegenHealth((float) (data.getRegenHealth() - matrix.hregen[lv-1] + matrix.hregen[lv]));
		data.setRegenMana((float) (data.getRegenMana() - matrix.mregen[lv-1] + matrix.mregen[lv]));
		data.setMaxHealth((float) (data.getMaxHealth() - matrix.hp[lv-1] + matrix.hp[lv]));
		data.setMaxMana((float) (data.getMaxMana() - matrix.mana[lv-1] + matrix.mana[lv]));
		data.setCurrentHealth(data.getMaxHealth());
		data.setCurrentMana(data.getMaxMana());
		data.setMove((float) (data.getMove() - matrix.move[lv-1] + matrix.move[lv]));
		data.setAttackSpeed((float) (data.getAttackSpeed() - matrix.attackspeed[lv-1] + matrix.attackspeed[lv]));
	}
	
	public class CooldownHandler{
		protected int skillTimer = 0;
		protected int cooldown;
		protected int id;
		protected EntityPlayerMP player;
		protected PlayerData data;
		
		public CooldownHandler(double cool,int id,EntityPlayerMP player) {
			this.cooldown = (int)(cool*40);
			this.id = id;
			this.player = player;
			this.data = main.game.getPlayerData(player.getUniqueID());
			MinecraftForge.EVENT_BUS.register(this);
		}

		@SubscribeEvent
		public void skillTimer(ServerTickEvent event) throws Throwable {
			if(skillTimer > cooldown) {
				data.setCool(this.id, 0);
				MinecraftForge.EVENT_BUS.unregister(this);
			}
			data.setCool(this.id, ((float)(cooldown-skillTimer)/40));
			skillTimer++;
			
		}
	}
	
	
	@Override
	public Object clone() throws CloneNotSupportedException { // public ���� �ٲ�����.
		return super.clone();
	}
	
	public abstract class StatMatrix{
		public double[] hp;
		public double[] mana;
		public double[] hregen;
		public double[] mregen;
		public double[] ad;
		public double[] ap;
		public double[] move;
		public double[] attackspeed;
	}
	
}
