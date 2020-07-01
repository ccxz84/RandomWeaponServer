package RWAPI;

import RWAPI.Character.ClientData;
import RWAPI.game.Game;
import RWAPI.packet.PlayerStatMessage;
import RWAPI.packet.EnemyStatPacket.EnemyStatHandler;
import RWAPI.packet.InventoryOpenPacket.InventoryOpenHandler;
import RWAPI.packet.EnemyStatPacket;
import RWAPI.packet.InventoryOpenPacket;
import RWAPI.packet.KeyInputPacket;
import RWAPI.packet.KeyInputPacket.KeyInputHandler;
import RWAPI.packet.PlayerStatMessage.HealthStatusHandler;
import RWAPI.proxy.IProxy;
import RWAPI.util.Reference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid=Reference.MODID, name=Reference.MODNAME, version=Reference.VERSION, acceptedMinecraftVersions=Reference.ACCEPTED_MINECRAFT_VERSIONS)
public class main {
	@Instance
	public static main instance;
	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static IProxy proxy;
	
	public static Game game;
	
	public static ClientData defData = new ClientData();
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println(Reference.MODID + ":preInit");
		proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		System.out.println(Reference.MODID + ":init");
		int channel = 1;
		//network.registerMessage(HealthStatusHandler.class, PlayerStatMessage.class, channel++, Side.CLIENT);
		//network.registerMessage(HealthStatusHandler.class, PlayerStatMessage.class, channel++, Side.SERVER);
		//network.registerMessage(KeyInputHandler.class, KeyInputPacket.class, channel++, Side.SERVER);
		//network.registerMessage(InventoryOpenHandler.class, InventoryOpenPacket.class, channel++, Side.SERVER);
		//network.registerMessage(EnemyStatHandler.class, EnemyStatPacket.class, channel++, Side.CLIENT);
		//network.registerMessage(EnemyStatHandler.class, EnemyStatPacket.class, channel++, Side.SERVER);
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		System.out.println(Reference.MODID + ":postInit");
		proxy.postInit(event);
	}
	
	@EventHandler
	public void ServerLoad(FMLServerStartingEvent event) {
		proxy.serverStarting(event);
	}
}
