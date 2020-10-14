package RWAPI.event;

import RWAPI.Character.shop.entity.EntityMerchant;
import RWAPI.items.weapon.WeaponBase;
import RWAPI.main;
import RWAPI.Character.PlayerData;
import RWAPI.Character.monster.entity.EntityMinion;
import RWAPI.util.DamageSource.DamageSource;
import RWAPI.util.EntityStatus;
import RWAPI.util.GameStatus;
import RWAPI.util.DamageSource.DamageSource.EnemyStatHandler;
import RWAPI.util.NetworkUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class GameBaseEvent{
	
	@SubscribeEvent
	public void attackEvent(LivingAttackEvent event)
	{
		if(main.game.start != GameStatus.START) {
			if(event.isCancelable())
				event.setCanceled(true);
			return;
		}
		if(event.getEntityLiving() instanceof EntityMerchant){
			if(event.isCancelable())
				event.setCanceled(true);
			return;
		}

		if(event.getEntityLiving() instanceof EntityPlayer && event.getSource().getTrueSource() instanceof EntityLivingBase){
			PlayerData target = main.game.getPlayerData(event.getEntityLiving().getUniqueID());
			target.setDashtimer();
		}

		if(event.getEntity() instanceof EntityPlayer && event.getSource().getTrueSource() instanceof EntityPlayer && !(event.getSource() instanceof EntityDamageSourceIndirect)) {
			PlayerData target = main.game.getPlayerData(event.getEntityLiving().getUniqueID());
			PlayerData attacker = main.game.getPlayerData(event.getSource().getTrueSource().getUniqueID());
			if(attacker.nonWorking == false){
				DamageSource source = DamageSource.causeAttackPhysics(attacker, target,attacker.getAd());
				DamageSource.attackDamage(source,true);
				EnemyStatHandler.EnemyStatSetter(source);
			}
		}
	}
	
	@SubscribeEvent
	public void PlayerAttackEvent(AttackEntityEvent event)
	{
		if(main.game.start != GameStatus.START) {
			event.setCanceled(true);
			return;
		}
		if(event.getEntityPlayer() instanceof EntityPlayerMP){
			EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();
			PlayerData data = main.game.getPlayerData(player.getUniqueID());
			if(data.getStatus() != EntityStatus.ALIVE){
				event.setCanceled(true);
				return;
			}
		}
	}
	
	@SubscribeEvent
	public void MobSpawnEvent(LivingSpawnEvent.CheckSpawn event)
	{
		if((event.getEntityLiving() instanceof EntitySlime) || (event.getEntityLiving() instanceof EntityMob) && (!(event.getEntityLiving() instanceof EntityMinion))) {
			event.setResult(Result.DENY);
		}
		
	}
	
	@SubscribeEvent
	public void PlayerLogin(PlayerEvent.PlayerRespawnEvent event) {
		if(main.game.start == GameStatus.PRESTART || main.game.start == GameStatus.START) {
			PlayerData data = main.game.getPlayerData(event.player.getUniqueID());
			if(data != null) {
				data.setPlayer((EntityPlayerMP) event.player);
			}
		}
	}

	@SubscribeEvent
	public void PlayerDataEvent(TickEvent.ServerTickEvent event){
		if(main.game != null && main.game.player() != null){
			for(PlayerData player : main.game.player().values()) {
				NetworkUtil.sendTo(player.getPlayer(), player.getData(), "data");
			}
		}
	}

	@SubscribeEvent
	public void PlayerDropEvent(ItemTossEvent event){
		if(event.getEntityItem().getItem().getItem() instanceof WeaponBase){
			event.getPlayer().inventory.setInventorySlotContents(0,event.getEntityItem().getItem());
			event.setCanceled(true);
		}
	}
}
