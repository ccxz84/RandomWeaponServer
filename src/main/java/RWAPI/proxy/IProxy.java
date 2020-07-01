package RWAPI.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public interface IProxy {
	void preInit (FMLPreInitializationEvent event); 					//preinit
	void init (FMLInitializationEvent event);							//init
    void postInit (FMLPostInitializationEvent event);					//postinit
    void serverStarting (FMLServerStartingEvent event);					//server stating method
}
