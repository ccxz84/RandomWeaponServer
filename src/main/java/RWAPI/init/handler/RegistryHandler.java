package RWAPI.init.handler;

import RWAPI.init.ModItems;
import RWAPI.init.ModPotion;
import RWAPI.init.ModSkills;
import RWAPI.init.ModWeapons;
import RWAPI.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public class RegistryHandler {
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
		event.getRegistry().registerAll(ModSkills.skill.toArray(new Item[0]));
		event.getRegistry().registerAll(ModWeapons.weapon.toArray(new Item[0]));
		event.getRegistry().register(ModItems.RUBY);
	}

	@SubscribeEvent
	public static void onPotionRegister(RegistryEvent.Register<Potion> event) {
		ForgeRegistries.POTIONS.registerAll(ModPotion.POTIONS.toArray(new Potion[0]));
		ForgeRegistries.POTION_TYPES.registerAll(ModPotion.POTIONTYPES.toArray(new PotionType[0]));
		//event.getRegistry().registerAll(ModPotion.ITEMS.toArray(new Potion[0]));
	}


	
	public static void preInitRegistries() {
		EntityInit.addRegist();
		EntityInit.registerEntities();
	}
}	
