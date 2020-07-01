package RWAPI.Character.MasterYi;

import RWAPI.main;
import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerClass;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Leesin.entity.EntityUmpa;
import RWAPI.Character.buff.Buff;
import RWAPI.Character.monster.entity.AbstractMob;
import RWAPI.Character.PlayerClass.CooldownHandler;
import RWAPI.Character.PlayerClass.StatMatrix;
import RWAPI.init.ModSkills;
import RWAPI.init.ModWeapons;
import RWAPI.util.ClassList;
import RWAPI.util.DamageSource;
import RWAPI.util.DamageSource.EnemyStatHandler;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public class MasterYi extends PlayerClass {

	public MasetYiHandler handler[];
	
	public MasterYi(){
		default_health = 780;
		default_mana = 200;
		
		default_ad = 78;
		default_ap = 0;
		default_move = 110;
		
		default_regenHealth = 1.2f;
		default_regenMana = 2.3f;
		
		attackSpeed = 0.38f;
		
		class_code = ClassList.MasterYi;
		
		ClassName = "마스터 이";
		
		super.weapon = ModWeapons.leesin;
		skillSet[0] = ModSkills.skill.get(ModSkills.doublestrike.SkillNumber);
		skillSet[1] = ModSkills.skill.get(ModSkills.alphastrike.SkillNumber);
		skillSet[2] = ModSkills.skill.get(ModSkills.meditation.SkillNumber);
		skillSet[3] = ModSkills.skill.get(ModSkills.wujustyle.SkillNumber);
		skillSet[4] = ModSkills.skill.get(ModSkills.highlander.SkillNumber);
		matrix = new MasterYimatrix();
		handler = new MasetYiHandler[5];
	}
	
	@Override
	public void skill1(EntityPlayer player) {
		
		
	}
	
	@Override
	public void skill2(EntityPlayer player) {
		PlayerData data = main.game.getPlayerData(player.getUniqueID());
		int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
		MasterYimatrix matrix = (MasterYimatrix) this.matrix;
		if(data.getCool(2) <= 0 && data.getCurrentMana() > matrix.skillcost[2][lv-1]) {
			data.setCurrentMana((float) (data.getCurrentMana() - matrix.skillcost[2][lv-1]));
			this.handler[2] = new MasetYiHandler(matrix.cooldown[2][lv-1], 3, (EntityPlayerMP) player);
			new Buff(4, (EntityPlayerMP) player,(float)matrix.skilldamage[2][lv-1] + ((float)matrix.skillApcoe[3][lv-1] * data.getAp())/40) {

				int x,y,z;
				PlayerData pdata;
				
				@Override
				public void setEffect() {
					// TODO Auto-generated method stub
					x = (int) player.posX;
					y = (int) player.posY;
					z = (int) player.posZ;
					pdata = main.game.getPlayerData(player.getUniqueID());
				}

				@Override
				public void resetEffect() {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void BuffTimer(ServerTickEvent event) throws Throwable {
					// TODO Auto-generated method stub
					if((int)player.posX != x || (int)player.posY != y || (int)player.posZ != z) {
						resetEffect();
						MinecraftForge.EVENT_BUS.unregister(this);
					}
					super.BuffTimer(event);
					pdata.setCurrentHealth(pdata.getCurrentHealth() + data[0]);
				}
			};
		}
	}
	
	@Override
	public void skill3(EntityPlayer player) {
		PlayerData data = main.game.getPlayerData(player.getUniqueID());
		int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
		MasterYimatrix matrix = (MasterYimatrix) this.matrix;
		if(data.getCool(3) <= 0 && data.getCurrentMana() > matrix.skillcost[3][lv-1]) {
			data.setCurrentMana((float) (data.getCurrentMana() - matrix.skillcost[3][lv-1]));
			this.handler[3] = new MasetYiHandler(matrix.cooldown[3][lv-1], 3, (EntityPlayerMP) player);
			new Buff(5, (EntityPlayerMP) player,(float)matrix.skilldamage[3][lv-1] + (float)matrix.skillAdcoe[3][lv-1] * data.getAd()) {

				@Override
				public void setEffect() {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void resetEffect() {
					// TODO Auto-generated method stub
					
				}
				
				@SubscribeEvent(priority = EventPriority.NORMAL)
				public void attack(LivingAttackEvent event) {
					if(event.getSource().getTrueSource() != null) {
						if(event.getSource().getTrueSource().equals(player) && (event.getEntityLiving() instanceof AbstractMob || event.getEntityLiving() instanceof EntityPlayer)) {
							EntityData target = null;
							if(event.getEntityLiving() instanceof AbstractMob) {
								target = ((AbstractMob)event.getEntityLiving()).getData();
							}
							if(event.getEntityLiving() instanceof EntityPlayer){
								target = main.game.getPlayerData(event.getEntityLiving().getUniqueID());
							}
							if(target != null) {
								PlayerData attacker = main.game.getPlayerData(event.getSource().getTrueSource().getUniqueID());
								DamageSource source = DamageSource.causeAttack(attacker, target);
								DamageSource.attackDamage(source);
								EnemyStatHandler.EnemyStatSetter(source);
							}
						}
					}
				}
				
			};
		}
	}
	@Override
	public void skill4(EntityPlayer player) {
		PlayerData data = main.game.getPlayerData(player.getUniqueID());
		int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
		MasterYimatrix matrix = (MasterYimatrix) this.matrix;
		if(data.getCool(4) <= 0 && data.getCurrentMana() > matrix.skillcost[4][lv-1]) {
			data.setCurrentMana((float) (data.getCurrentMana() - matrix.skillcost[4][lv-1]));
			this.handler[4] = new MasetYiHandler(matrix.cooldown[4][lv-1], 4, (EntityPlayerMP) player);
			//this.handler[4] = new MasetYiHandler(2, 4, (EntityPlayerMP) player);
			new Buff(7, (EntityPlayerMP) player,(float)matrix.skilldamage[4][lv-1],(float)matrix.skilldamage[5][lv-1]) {

				@Override	
				public void resetEffect() {
					// TODO Auto-generated method stub
					PlayerData data = main.game.getPlayerData(player.getUniqueID());
					data.setAttackSpeed(data.getAttackSpeed()-this.data[1]);
					data.setMove(data.getMove() - this.data[0]);
				}

				@Override
				public void setEffect() {
					// TODO Auto-generated method stub
					PlayerData data = main.game.getPlayerData(player.getUniqueID());
					data.setAttackSpeed(this.data[1] + data.getAttackSpeed());
					data.setMove(this.data[0] + data.getMove());
				}
				
				@SubscribeEvent(priority = EventPriority.LOW)
				public void attack(LivingAttackEvent event) {
					if(event.getSource().getTrueSource() != null) {
						if(event.getSource().getTrueSource().equals(player) && (event.getEntityLiving() instanceof AbstractMob || event.getEntityLiving() instanceof EntityPlayer)) {
							EntityData target = null;
							if(event.getEntityLiving() instanceof AbstractMob) {
								target = ((AbstractMob)event.getEntityLiving()).getData();
							}
							if(event.getEntityLiving() instanceof EntityPlayer){
								target = main.game.getPlayerData(event.getEntityLiving().getUniqueID());
							}
							if(target != null) {
								PlayerData attacker = main.game.getPlayerData(event.getSource().getTrueSource().getUniqueID());
								if(target.getCurrentHealth() <= 0) {
									this.duration += 5 * 40;
								}
							}
						}
					}
				}
			};
		}
	}
	
	class MasetYiHandler extends CooldownHandler{

		public MasetYiHandler(double cool, int id, EntityPlayerMP player) {
			super(cool, id, player);
			// TODO Auto-generated constructor stub
		}
		@SubscribeEvent(priority = EventPriority.LOWEST)
		public void attack(LivingAttackEvent event) {
			if(event.getSource().getTrueSource() != null) {
				if(event.getSource().getTrueSource().equals(player) && (event.getEntityLiving() instanceof AbstractMob || event.getEntityLiving() instanceof EntityPlayer)) {
					EntityData target = null;
					if(event.getEntityLiving() instanceof AbstractMob) {
						target = ((AbstractMob)event.getEntityLiving()).getData();
					}
					if(event.getEntityLiving() instanceof EntityPlayer){
						target = main.game.getPlayerData(event.getEntityLiving().getUniqueID());
					}
					if(target != null) {
						PlayerData attacker = main.game.getPlayerData(event.getSource().getTrueSource().getUniqueID());
						if(target.getCurrentHealth() <= 0) {
							System.out.println("run");
							this.skillTimer += cooldown * 0.7;
						}
					}
				}
			}
		}
	}
	
	class MasterYimatrix extends StatMatrix{
		public MasterYimatrix() {
			super.ad = this.ad;
			super.ap = this.ap;
			super.hp = this.hp;
			super.hregen = this.hregen;
			super.mana = this.mana;
			super.move = this.move;
			super.mregen = this.mregen;
			super.attackspeed = this.attackspeed;
		}
		
		final double[][] cooldown = {
				{0,0,0,0,0,0,0,0,0,0,0,0},
				{18,17.5,17,16.5,16,15,14.5,14,13.5,13,12,11},
				{28,27,26,25,24,22,21,20,19,18,17,15},
				{18,17.5,17,16.5,16,15,14.5,14,13.5,13,12,11},
				{85,85,80,80,80,75,75,75,70,70,70,60}
		};
		
		final double[] hp = {
				780,
				820,
				840,
				860,
				880,
				990,
				1030,
				1080,
				1130,
				1190,
				1250,
				1400
		};
		
		final double[] mana = {
				200,
				250,
				300,
				350,
				400,
				500,
				570,
				640,
				710,
				780,
				850,
				1000
		};
		
		final double[] hregen = {
				1.2,
				1.3,
				1.4,
				1.5,
				1.6,
				3,
				3.2,
				3.4,
				3.6,
				3.8,
				4,
				4.2
		};
		
		final double[] mregen = {
			2.3,
			2.5,
			2.9,
			3.3,
			3.7,
			5,
			5.2,
			5.6,
			6,
			6.4,
			6.9,
			9
		};
		
		final double[] ad = {
				78,
				80,
				82,
				84,
				86,
				95,
				99,
				103,
				107,
				110,
				114,
				120
		};
		
		final double[] ap = {
			0,0,0,0,0,0,0,0,0,0,0,0	
		};
		
		final double[] move = {
				110,110,110,110,110,110,110,110,110,110,110,110
		};
		final double[] attackspeed = {
			0.38,
			0.41,
			0.45,
			0.49,
			0.51,
			0.66,
			0.7,
			0.75,
			0.8,
			0.85,
			0.9,
			1.03
		};
		
		final double[][] skilldamage = {
				{0,0,0,0,0,0,0,0,0,0,0,0},
				{90,93,96,102,105,110,113,116,119,122,125,128},
				{1,1.1,1.2,1.3,1.4,1.6,1.7,1.8,1.9,2,2.2,2.4},
				{18,20,22,24,26,30,32,34,36,38,41,45},
				{30,30,40,40,40,55,55,55,65,65,65,80},
				{0.3,0.3,0.4,0.4,0.4,0.55,0.55,0.55,0.65,0.65,0.65,0.85}
		};
		
		final double[][] skillAdcoe = {
				{0.5,0.5,0.5,0.5,0.5,0.6,0.6,0.6,0.6,0.6,0.6,0.7},
				{1,1,1,1,1,1,1,1,1,1,1,1},
				{0,0,0,0,0,0,0,0,0,0,0,0},
				{0.3,0.3,0.3,0.3,0.3,0.4,0.4,0.4,0.4,0.4,0.4,0.6},
				{0,0,0,0,0,0,0,0,0,0,0,0}
		};
		final double[][] skillApcoe = {
				{0,0,0,0,0,0,0,0,0,0,0,0},
				{0.2,0.2,0.2,0.2,0.2,0.3,0.3,0.3,0.3,0.3,0.3,0.4},
				{0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0}	
		};
		final double[][] skillcost = {
				{0,0,0,0,0,0,0,0,0,0,0,0},
				{50,50,55,55,55,70,70,70,80,80,80,100},
				{50,50,50,50,50,70,70,70,70,70,70,80},
				{0,0,0,0,0,0,0,0,0,0,0,0},
				{100,100,150,150,150,200,200,200,250,250,250,250}
			};
	}
}
