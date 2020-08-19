package RWAPI.game;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import RWAPI.Character.Skill;
import RWAPI.game.event.*;
import RWAPI.main;
import RWAPI.Character.PlayerData;
import RWAPI.util.ClassList;
import RWAPI.util.EntityStatus;
import RWAPI.util.GameStatus;
import RWAPI.util.spawnpoint;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import scala.Int;
import scala.xml.dtd.impl.Base;

import java.beans.PropertyChangeSupport;

public class Game {
	
	public static GameStatus start = GameStatus.NONE;
	
	public Timer GameTimer = null;
	
	private HashMap<UUID,PlayerData> playerList;
	private HashMap<UUID,PlayerData> playerReady;
	
	private String timerFlag = "";
	private int timer;

	private EventHandler event;
	
	
	//constructor
	public Game() {
		playerList = new HashMap<UUID,PlayerData>();
		event = new EventHandler();
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
		for(PlayerData player : main.game.player().values()) {
			
			double[] point = spawnpoint.getRandomSpawnPoint();
			player.getPlayer().connection.setPlayerLocation(point[0], point[1], point[2], player.getPlayer().rotationYaw, player.getPlayer().rotationPitch);
			player.getData().setTimerFlag("게임 시간");
			for(Skill skill :player.get_class().getSkills()){
				skill.Skillset(player.getPlayer());
			}
			player.resetInvhandler();
		}
		
		this.initTimer("게임 시간", 420);
		this.GameTimer = (new Timer(main.game, 420) {
			int timer = 0;

			@Override
			public void gameTimer(ServerTickEvent event) {
				// TODO Auto-generated method stub
				super.gameTimer(event);
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
					if(timer % 60 == 0) {
						player.setGold(player.getGold() + 1);
					}
					timer++;
				}
			}

			@Override
			public void TimerEnd() {
				// TODO Auto-generated method stub
				for(PlayerData player : main.game.player().values()) {
					player.resetgame();
				}
			}
			
		});

		MinecraftForge.EVENT_BUS.register(GameTimer);
	}
	
	
	public static class gameStart{
		
		private Game game;
		
		public gameStart(Game game) {
			this.game = game;
			game.initTimer("게임 대기 시간", 30);
			for(PlayerData player : main.game.player().values()) {
				player.getData().setTimerFlag("게임 대기 시간");
				player.getPlayer().inventory.clear();
				player.getPlayer().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1024);
				player.getPlayer().getFoodStats().setFoodLevel(20);
				player.getPlayer().connection.setPlayerLocation(-56,53,107, player.getPlayer().rotationYaw, player.getPlayer().rotationPitch);
			}
			game.GameTimer = (new Timer(main.game,1) {
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
		}
	}
	
	public static abstract class Timer{
		private Game game;
		
		private int MaxTime;
		private int currentTime = 0;
		
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

		public void RunEvent(BaseEvent.AbstractBaseEvent event){
			HashMap<BaseEvent.EventPriority,List<BaseEvent>> map = eventcodeList.get(event.EventCode());
			if(map != null){
				for(BaseEvent.EventPriority priority :BaseEvent.EventPriority.values()){
					List<BaseEvent> list = map.get(priority);
					for(BaseEvent bevent : list){
						bevent.EventListener(event);
					}
				}
			}
		}
	}

}