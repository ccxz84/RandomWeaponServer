package RWAPI.proxy;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import RWAPI.main;
import RWAPI.Character.monster.entity.EntityMinion;
import RWAPI.command.ReadyCommand;
import RWAPI.command.StartCommand;
import RWAPI.event.GameBaseEvent;
import RWAPI.event.joinEvent;
import RWAPI.command.PreStartCommand;
import RWAPI.init.ModItems;
import RWAPI.init.handler.GuiHandler;
import RWAPI.init.handler.RegistryHandler;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEnd;
import net.minecraft.world.biome.BiomeHell;
import net.minecraft.world.biome.BiomeVoid;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class CommonProxy implements IProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		// TODO Auto-generated method stub
		RegistryHandler.preInitRegistries();
	}

	@Override
	public void init(FMLInitializationEvent event) {
		// TODO Auto-generated method stub
		NetworkRegistry.INSTANCE.registerGuiHandler(main.instance, new GuiHandler());
		MinecraftForge.EVENT_BUS.register(new GameBaseEvent());
		MinecraftForge.EVENT_BUS.register(new joinEvent());
		EntityRegistry.addSpawn(EntityMinion.class, 3, 10000, 10000, EnumCreatureType.MONSTER, getAllSpawnBiomes());
		
		/*for(int i = 0; i<72; i++) {
			if(i==0) {
				ModItems.temp.add(ModItems.Dagger);
				continue;
			}
			if(i==1) {
				ModItems.temp.add(ModItems.Recurvebow);
				continue;
			}
			if(i==2) {
				ModItems.temp.add(ModItems.Bladeoftheruinedking);
				continue;
			}
			if(i==4) {
				ModItems.temp.add(ModItems.Bilgewatercutlass);
				continue;
			}
			
			
			if(i==71) {
				ModItems.temp.add(ModItems.LongSword);
				continue;
			}
			ModItems.temp.add(ModItems.VampiricScepter);
		}*/
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serverStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new PreStartCommand());
		event.registerServerCommand(new ReadyCommand());
		event.registerServerCommand(new StartCommand());
		
	}
	
	private Biome[] getAllSpawnBiomes() {
        LinkedList<Biome> list = new LinkedList<>();
        Collection<Biome> biomes = ForgeRegistries.BIOMES.getValuesCollection();
        for (Biome bgb : biomes) {
            if (bgb instanceof BiomeVoid) {
                continue;
            }
            if (bgb instanceof BiomeEnd) {
                continue;
            }
            if (bgb instanceof BiomeHell) {
                continue;
            }
            if (!list.contains(bgb)) {
                list.add(bgb);
            }
        }
        return list.toArray(new Biome[0]);
    }

}
