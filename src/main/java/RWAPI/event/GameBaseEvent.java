package RWAPI.event;

import java.util.UUID;

import RWAPI.main;
import RWAPI.Character.PlayerData;
import RWAPI.Character.monster.entity.AbstractMob;
import RWAPI.Character.monster.entity.EntityMinion;
import RWAPI.util.DamageSource;
import RWAPI.util.GameStatus;
import RWAPI.util.DamageSource.EnemyStatHandler;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class GameBaseEvent{
	
	@SubscribeEvent
	public void attackEvent(LivingAttackEvent event)
	{
		if(event.getEntity() instanceof EntityPlayer && event.getSource().getTrueSource() instanceof EntityPlayer && !(event.getSource() instanceof EntityDamageSourceIndirect)) {
			PlayerData target = main.game.getPlayerData(event.getEntityLiving().getUniqueID());
			PlayerData attacker = main.game.getPlayerData(event.getSource().getTrueSource().getUniqueID());
				
			DamageSource source = DamageSource.causeAttack(attacker, target);
			DamageSource.attackDamage(source);
			EnemyStatHandler.EnemyStatSetter(source);
		}
	}
	
	@SubscribeEvent
	public void PlayerAttackEvent(AttackEntityEvent event)
	{
		if(main.game.start == GameStatus.START) {
			event.setCanceled(true);
			return;
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
	
}
