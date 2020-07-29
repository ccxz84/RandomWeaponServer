package RWAPI.event;

import RWAPI.main;
import RWAPI.Character.PlayerData;
import RWAPI.packet.*;
import RWAPI.packet.EnemyStatPacket.EnemyStatHandler;
import RWAPI.packet.InventoryOpenPacket.InventoryOpenHandler;
import RWAPI.packet.KeyInputPacket.KeyInputHandler;
import RWAPI.packet.PlayerStatMessage.HealthStatusHandler;
import RWAPI.util.GameStatus;
import RWAPI.util.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class joinEvent {
	
	@SubscribeEvent
	public void PlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
		try {
			int channel = 1;
			SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(event.player.getName());
			network.registerMessage(HealthStatusHandler.class, PlayerStatMessage.class, channel++, Side.CLIENT);
			network.registerMessage(HealthStatusHandler.class, PlayerStatMessage.class, channel++, Side.SERVER);
			network.registerMessage(KeyInputHandler.class, KeyInputPacket.class, channel++, Side.SERVER);
			network.registerMessage(InventoryOpenHandler.class, InventoryOpenPacket.class, channel++, Side.SERVER);
			network.registerMessage(EnemyStatHandler.class, EnemyStatPacket.class, channel++, Side.CLIENT);
			network.registerMessage(EnemyStatHandler.class, EnemyStatPacket.class, channel++, Side.SERVER);

		}
		catch (Exception e){
			System.out.println("already regist channel");

		}
		
		if(main.game.start == GameStatus.PRESTART || main.game.start == GameStatus.START) {
			PlayerData data = main.game.getPlayerData(event.player.getUniqueID());
			if(data != null) {
				data.setPlayer((EntityPlayerMP) event.player);
			}
		}
	}
}
