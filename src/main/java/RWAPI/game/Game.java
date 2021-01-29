package RWAPI.game;


import java.lang.reflect.InvocationTargetException;
import java.util.*;

import RWAPI.Character.Skill;
import RWAPI.Character.monster.entity.*;
import RWAPI.game.event.*;
import RWAPI.main;
import RWAPI.Character.PlayerData;
import RWAPI.util.*;
import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import scala.Int;
import scala.tools.nsc.transform.SpecializeTypes;
import scala.xml.dtd.impl.Base;

import java.beans.PropertyChangeSupport;

public class Game {
	
	public static GameStatus start = GameStatus.NONE;
	
	public Timer GameTimer = null;
	public MinecraftServer server;

	private HashMap<UUID,PlayerData> playerList;
	private HashMap<UUID,PlayerData> playerReady;
	
	private String timerFlag = "";
	private int timer;
	int objecttimer = 0;
	boolean object = false;
	AbstractObject objectmob = null;

	private EventHandler event;
	
	
	//constructor
	public Game(MinecraftServer server) {
		playerList = new HashMap<UUID,PlayerData>();
		event = new EventHandler();
		this.server = server;
	}
	//
	
	
	//init
	public void initTimer(String Flag,int timer) {
		//System.out.println("InitTimer");
		timerFlag = Flag;
		this.timer = timer;
	}
	//
	//ready Player method
	public boolean addReadyPlayer(String playeruuid) {
		if(playerReady.get(UUID.fromString(playeruuid)) instanceof PlayerData) {
			playerReady.remove(UUID.fromString(playeruuid));
			return true;
		}
		return false;
	}
	public boolean removePlayer(UUID uuid) {
		if(playerReady.get(uuid) instanceof PlayerData) {
			playerReady.remove(uuid);
			return true;
		}
		return false;
	}
	public void readyPlayer() {
		
		playerReady = (HashMap<UUID, PlayerData>) playerList.clone();
	}
	//
	
	//Getter
	public HashMap<UUID,PlayerData> playerReady(){
		return playerReady;
	}
	
	public HashMap<UUID,PlayerData> player(){
		return playerList;
	}
	
	public boolean getReadyStatus() {
		if(playerReady.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public PlayerData getPlayerData(UUID uuid) {
		return playerList.get(uuid);
	}
	
	public String gettimerFlag() {
		return timerFlag;
	}
	
	public int gettimer() {
		return timer;
	}

	public void resetObjectTimer(){
		this.objecttimer = 0;
		this.object = false;
	}

	public EventHandler getEventHandler(){
		return this.event;
	}
	//
	
	//setter
	public void addplayerList(PlayerData player, UUID uuid) {
		playerList.put(uuid,player);
	}
	//
	
	public void startGame() {
		// TODO Auto-generated method stub
		main.game.start = GameStatus.START;
		server.getWorld(0).getGameRules().addGameRule("doDaylightCycle", "false", GameRules.ValueType.BOOLEAN_VALUE);


		for(PlayerData player : main.game.player().values()) {
			
			double[] point = spawnpoint.getRandomSpawnPoint();
			player.getPlayer().connection.setPlayerLocation(point[0], point[1], point[2], player.getPlayer().rotationYaw, player.getPlayer().rotationPitch);
			player.getData().setTimerFlag("게임 시간");
			player.get_class().initSkill(player);
			player.setKeyinputListener();
			player.getPlayer().getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1d);
		}
		
		this.initTimer("게임 시간", Reference.GAMEITME);
		this.GameTimer = (new Timer(main.game, Reference.GAMEITME) {
			int timer = 0;
			Class<? extends AbstractObject>[] objectlist = new Class[]{EntityDragon.class, EntityWitch.class,EntityGolem.class};
			//Class<? extends AbstractObject>[] objectlist = new Class[]{EntityWitch.class, EntityWitch.class,EntityWitch.class};


			@Override
			public void gameTimer(ServerTickEvent event) {
				// TODO Auto-generated method stub
				super.gameTimer(event);
				if(currentTime == 2400){
					for(PlayerData player : main.game.player().values()) {
						player.setFirstDeath(true);
					}
				}
				for(PlayerData player : main.game.player().values()) {

					if(player.getStatus().equals(EntityStatus.ALIVE)){
						player.getData().setTimer(main.game.timer);
					}
					if(player.getCurrentHealth() < player.getMaxHealth()) {
						player.setCurrentHealth(player.getCurrentHealth() + player.getRegenHealth()/40);
					}
					if(player.getCurrentMana() < player.getMaxMana()) {
						player.setCurrentMana(player.getCurrentMana() + player.getRegenMana()/40);
					}
					player.getPlayer().getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1f * (player.getMove()/100)); //이속
					player.getPlayer().setHealth(1024f);
					player.getPlayer().getFoodStats().setFoodLevel(20);
					player.getPlayer().hurtResistantTime = 0;
					if(timer % 60 == 0) {
						player.setGold(player.getGold() + 1);
					}

				}

				if((currentTime % 12000) == 0){
					List<AbstractMob> list = server.getWorld(0).getEntities(EntityMinion.class, input -> {
						if(input instanceof AbstractMob && !(input instanceof AbstractObject)) {
							return true;
						}
						return false;
					});

					for(AbstractMob entity : list){
						double[] stat = entity.getStat();
						entity.getData().setMaxHealth(entity.getData().getMaxHealth()+(stat[0] * ((Reference.GAMEITME - main.game.gettimer())/300)));
						entity.getData().setCurrentHealth(entity.getData().getCurrentHealth()+(stat[0] * ((Reference.GAMEITME - main.game.gettimer())/300)));
						entity.getData().setAd(entity.getData().getAd() +(stat[1] * ((Reference.GAMEITME - main.game.gettimer())/300)));
						entity.getData().setArmor(entity.getData().getArmor() +(stat[2] * ((Reference.GAMEITME - main.game.gettimer())/300)));
						entity.getData().setMagicresistance(entity.getData().getMagicresistance() +(stat[3] * ((Reference.GAMEITME - main.game.gettimer())/300)));
						entity.getData().setDeathExp(entity.getData().getDeathExp() +(stat[4] * ((Reference.GAMEITME - main.game.gettimer())/300)));
						entity.getData().setDeathGold(entity.getData().getDeattGold() +((int)stat[5] * ((Reference.GAMEITME - main.game.gettimer())/300)));
					}
				}

				if((currentTime % 2400) == 0){
					List<PlayerData> killlist = new ArrayList<PlayerData>(main.game.player().values());
					List<PlayerData> totlalist = new ArrayList<PlayerData>(main.game.player().values());
					killlist.sort(new Comparator<PlayerData>() {
						@Override
						public int compare(PlayerData o1, PlayerData o2) {
							if(o1.getKill() < o2.getKill()){
								return 1;
							}
							else if(o1.getKill() == o2.getKill()){
								return 0;
							}
							else
								return -1;
						}
					});
					totlalist.sort(new Comparator<PlayerData>() {
						@Override
						public int compare(PlayerData o1, PlayerData o2) {
							if(o1.getTotal_score() < o2.getTotal_score()){
								return 1;
							}
							else if(o1.getTotal_score() == o2.getTotal_score()){
								return 0;
							}
							else
								return -1;
						}
					});
					server.getPlayerList().sendMessage(new TextComponentString("현재 킬 순위 : "));
					int i = 1;
					for(PlayerData player : killlist){
						server.getPlayerList().sendMessage(new TextComponentString(i+"위. "+player.getPlayer().getName()+ (i == 1 ? " 킬 횟수 : " +player.getKill():"")));
						i++;
					}
					server.getPlayerList().sendMessage(new TextComponentString("----------------"));
					server.getPlayerList().sendMessage(new TextComponentString("현재 점수 순위 : "));
					i = 1;
					for(PlayerData player : totlalist){
						server.getPlayerList().sendMessage(new TextComponentString(i+"위. "+player.getPlayer().getName()));
						i++;
					}

					int per = (int)Math.ceil(((double)totlalist.size()/100)*30);
					int j = 1;

					for(int x = totlalist.size()-1;x > -1 && per > 0;--x){
						PlayerData pdata = totlalist.get(x);
						pdata.setGold(pdata.getGold() + 150/j);
						pdata.getPlayer().sendMessage(new TextComponentString("하위권 보상 "+150/j+"골드를 획득하였습니다."));
						--per;
						++j;

					}
				}
				if(object == true && timer % 2400 == 0 && objectmob != null){
					double[] stat = objectmob.getStat();
					int time = objectmob.getGametime();
					objectmob.getData().setMaxHealth(objectmob.getData().getMaxHealth()+(stat[0] * time));
					objectmob.getData().setCurrentHealth(objectmob.getData().getCurrentHealth()+(stat[0] * time));
					objectmob.getData().setAd(objectmob.getData().getAd() +(stat[1] * time));
					objectmob.getData().setArmor(objectmob.getData().getArmor() +(stat[2] * time));
					objectmob.getData().setMagicresistance(objectmob.getData().getMagicresistance() +(stat[3] * time));
				}
				//object timer
				if(objecttimer >= 40 * Reference.OBJECTTIME && object == false){
					Random rand = new Random(System.currentTimeMillis());
					int num = rand.nextInt(objectlist.length-1)+1;
					try {
						objectmob = objectlist[num].getConstructor(World.class).newInstance(server.getWorld(0));
						server.getPlayerList().sendMessage(new TextComponentString(objectmob.getData().getName()+"(이)가 소환되었습니다."));
						objectmob.setLocationAndAngles(-183,64,-93,objectmob.rotationYaw,objectmob.rotationPitch);
						server.getWorld(0).spawnEntity(objectmob);
					}
					catch (Exception e){
						e.printStackTrace();
					}
					object = true;
				}
				else{
					++objecttimer;
				}
				timer++;
			}

			@Override
			public void TimerEnd() {
				// TODO Auto-generated method stub
				List<PlayerData> killlist = new ArrayList<PlayerData>(main.game.player().values());
				List<PlayerData> totlalist = new ArrayList<PlayerData>(main.game.player().values());
				killlist.sort(new Comparator<PlayerData>() {
					@Override
					public int compare(PlayerData o1, PlayerData o2) {
						if(o1.getKill() < o2.getKill()){
							return 1;
						}
						else if(o1.getKill() == o2.getKill()){
							return 0;
						}
						else
							return -1;
					}
				});
				killlist.get(0).setTotal_score(killlist.get(0).getTotal_score() + 5);
				totlalist.sort(new Comparator<PlayerData>() {
					@Override
					public int compare(PlayerData o1, PlayerData o2) {
						if(o1.getTotal_score() < o2.getTotal_score()){
							return 1;
						}
						else if(o1.getTotal_score() == o2.getTotal_score()){
							return 0;
						}
						else
							return -1;
					}
				});
				server.getPlayerList().sendMessage(new TextComponentString("최종 순위 : "));
				for(int i = 0; i < totlalist.size();i++){
					PlayerData data = totlalist.get(i);
					if(i == 0){
						data.getPlayer().connection.setPlayerLocation(Reference._1stPOS[0], Reference._1stPOS[1], Reference._1stPOS[2], data.getPlayer().rotationYaw, data.getPlayer().rotationPitch);
					}else if(i == 1){
						data.getPlayer().connection.setPlayerLocation(Reference._2ndPOS[0], Reference._2ndPOS[1], Reference._2ndPOS[2], data.getPlayer().rotationYaw, data.getPlayer().rotationPitch);
					}else if(i == 2){
						data.getPlayer().connection.setPlayerLocation(Reference._3rdPOS[0], Reference._3rdPOS[1], Reference._3rdPOS[2], data.getPlayer().rotationYaw, data.getPlayer().rotationPitch);
					}
					else{
						data.getPlayer().connection.setPlayerLocation(Reference.OTHERPOS[0], Reference.OTHERPOS[1], Reference.OTHERPOS[2], data.getPlayer().rotationYaw, data.getPlayer().rotationPitch);
					}
					server.getPlayerList().sendMessage(new TextComponentString((i+1)+"위. "+data.getPlayer().getName() + " 최종 점수 : " + String.format("%.2f",data.getTotal_score()) + (killlist.get(0).equals(data)?" 킬 순위 1등":"")));
				}

				for(PlayerData player : main.game.player().values()) {
					player.resetgame();
				}


			}
			
		});

		MinecraftForge.EVENT_BUS.register(GameTimer);
	}

	public void endgame() {
		this.GameTimer.TimerEnd();
	}


	public static class gameStart{
		
		private Game game;
		
		public gameStart(Game game) {
			this.game = game;
			game.initTimer("게임 대기 시간", Reference.GAMEWAITTIME);
			for(PlayerData player : main.game.player().values()) {
				player.getData().setTimerFlag("게임 대기 시간");
				player.getPlayer().inventory.clear();
				player.getPlayer().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1024);
				player.getPlayer().getFoodStats().setFoodLevel(20);
				player.getPlayer().connection.setPlayerLocation(Reference.SHOPPOS[0],Reference.SHOPPOS[1],Reference.SHOPPOS[2], player.getPlayer().rotationYaw, player.getPlayer().rotationPitch);
				player.resetInvhandler();

			}
			game.GameTimer = (new Timer(main.game,Reference.GAMEWAITTIME) {
				@Override
				public void gameTimer(ServerTickEvent event) {
					// TODO Auto-generated method stub
					for(PlayerData player : main.game.player().values()) {
						player.getData().setTimer(main.game.timer);
					}
					super.gameTimer(event);
				}

					public void TimerEnd() {
						game.startGame();
					}
			});
			MinecraftForge.EVENT_BUS.register(game.GameTimer);
		}
		
		
		public void appointedClass(PlayerData data) throws CloneNotSupportedException {
			data.appointed_Class(ClassList.getRandomClass());
			data.get_class().preinitSkill(data);
		}
	}
	
	public static abstract class Timer{
		private Game game;
		
		private int MaxTime;
		protected int currentTime = 0;
		
		public Timer(Game game, int Timer) {
			this.game = game;
			this.MaxTime = Timer;
			MinecraftForge.EVENT_BUS.register(this);
		}
		
		@SubscribeEvent
		public void gameTimer(TickEvent.ServerTickEvent event) {
			main.game.timer = this.MaxTime - (currentTime/40);
			currentTime++;
			if(currentTime > MaxTime * 40) {
				MinecraftForge.EVENT_BUS.unregister(this);
				TimerEnd();
			}
		}

		public void reset(){
			for(PlayerData player : main.game.player().values()) {
				player.resetgame();
			}
			MinecraftForge.EVENT_BUS.unregister(this);
		}
		
		abstract public void TimerEnd();
	}

	public static class EventHandler {
		HashMap<Integer, HashMap<BaseEvent.EventPriority,List<BaseEvent>>> eventcodeList = new HashMap<Integer, HashMap<BaseEvent.EventPriority,List<BaseEvent>>>();

		public EventHandler(){
			HashMap<BaseEvent.EventPriority,List<BaseEvent>> map = new HashMap<BaseEvent.EventPriority,List<BaseEvent>>();
			map.put(BaseEvent.EventPriority.HIGHTEST,new ArrayList<BaseEvent>());
			map.put(BaseEvent.EventPriority.HIGH,new ArrayList<BaseEvent>());
			map.put(BaseEvent.EventPriority.NORMAL,new ArrayList<BaseEvent>());
			map.put(BaseEvent.EventPriority.LOW,new ArrayList<BaseEvent>());
			map.put(BaseEvent.EventPriority.LOWEST,new ArrayList<BaseEvent>());
			eventcodeList.put(1,map);
			map = new HashMap<BaseEvent.EventPriority,List<BaseEvent>>();
			map.put(BaseEvent.EventPriority.HIGHTEST,new ArrayList<BaseEvent>());
			map.put(BaseEvent.EventPriority.HIGH,new ArrayList<BaseEvent>());
			map.put(BaseEvent.EventPriority.NORMAL,new ArrayList<BaseEvent>());
			map.put(BaseEvent.EventPriority.LOW,new ArrayList<BaseEvent>());
			map.put(BaseEvent.EventPriority.LOWEST,new ArrayList<BaseEvent>());
			eventcodeList.put(2,map);
			map = new HashMap<BaseEvent.EventPriority,List<BaseEvent>>();
			map.put(BaseEvent.EventPriority.HIGHTEST,new ArrayList<BaseEvent>());
			map.put(BaseEvent.EventPriority.HIGH,new ArrayList<BaseEvent>());
			map.put(BaseEvent.EventPriority.NORMAL,new ArrayList<BaseEvent>());
			map.put(BaseEvent.EventPriority.LOW,new ArrayList<BaseEvent>());
			map.put(BaseEvent.EventPriority.LOWEST,new ArrayList<BaseEvent>());
			eventcodeList.put(3,map);
			map = new HashMap<BaseEvent.EventPriority,List<BaseEvent>>();
			map.put(BaseEvent.EventPriority.HIGHTEST,new ArrayList<BaseEvent>());
			map.put(BaseEvent.EventPriority.HIGH,new ArrayList<BaseEvent>());
			map.put(BaseEvent.EventPriority.NORMAL,new ArrayList<BaseEvent>());
			map.put(BaseEvent.EventPriority.LOW,new ArrayList<BaseEvent>());
			map.put(BaseEvent.EventPriority.LOWEST,new ArrayList<BaseEvent>());
			eventcodeList.put(4,map);
		}

		public void register(BaseEvent eventObject){
			HashMap<BaseEvent.EventPriority,List<BaseEvent>> map = eventcodeList.get(eventObject.EventCode());
			if(map != null){
				List<BaseEvent> list = map.get(eventObject.getPriority());
				if(list != null){
					list.add(eventObject);
				}
			}
		}

		public void unregister(BaseEvent eventObject){
			HashMap<BaseEvent.EventPriority,List<BaseEvent>> map = eventcodeList.get(eventObject.EventCode());
			if(map != null){
				List<BaseEvent> list = map.get(eventObject.getPriority());
				if(list != null){
					list.remove(eventObject);
				}
			}
		}

		public void RunEvent(BaseEvent.AbstractBaseEvent event, BaseEvent.EventPriority priority){
			HashMap<BaseEvent.EventPriority,List<BaseEvent>> map = eventcodeList.get(event.EventCode());
			if(map != null){
				List<BaseEvent> list = map.get(priority);
				for(BaseEvent bevent : list){

					bevent.EventListener(event);
				}
			}
		}
	}

}