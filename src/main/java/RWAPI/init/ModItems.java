package RWAPI.init;

import java.util.ArrayList;
import java.util.List;

import RWAPI.items.Ruby;
import RWAPI.items.gameItem.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModItems {
	public static final List<ItemBase> ITEMS = new ArrayList<ItemBase>();
	public static final Class[] NO_OVERLAP_ITEMS = {
			ItemBase.shoes.class,
			Lastwhisper.class,
			Voidstaff.class,
			ItemBase.jungle.class,
			ItemBase.tear.class
	};


	public static final Item RUBY = new Ruby("baseitem");

	//phase 3
	public static final ItemBase LongSword = new LongSword("longsword");
	public static final ItemBase Dagger = new Dagger("dagger");
	public static final ItemBase Rubycrystal = new Rubycrystal("rubycrystal");
	public static final ItemBase Amplifyingtome = new Amplifyingtome("amplifyingtome");
	public static final ItemBase Sapphirecrystal = new Sapphirecrystal("sapphirecrystal");
	public static final ItemBase Needlesslylargerod = new Needlesslylargerod("needlesslylargerod");
	public static final ItemBase Bootsofspeed = new Bootsofspeed("bootsofspeed");
	public static final ItemBase Doransblade = new Doransblade("doran'sblade");
	public static final ItemBase Doransring = new Doransring("doran'sring");
	public static final ItemBase Huntersmachete = new Huntersmachete("hunter'smachete");
	public static final ItemBase Hunterstalisman = new Hunterstalisman("hunter'stalisman");
	public static final ItemBase Blastingwand = new Blastingwand("blastingWand");
	public static final ItemBase Nullmagicmantle = new Nullmagicmantle("nullmagicmantle");
	public static final ItemBase Clotharmor = new Clotharmor("clotharmor");
	public static final ItemBase Pickaxe = new Pickaxe("pickaxe");
	public static final ItemBase Faeriecharm = new Faeriecharm("faeriecharm");
	public static final ItemBase Rejuvenationbead = new Rejuvenationbead("rejuvenationbead");

	//phase 2
	public static final ItemBase Recurvebow = new Recurvebow("recurvebow");
	public static final ItemBase VampiricScepter = new VampiricScepter("vampiricscepter");
	public static final ItemBase Bilgewatercutlass = new Bilgewatercutlass("bilgewatercutlass");
	public static final ItemBase Serrateddirk = new Serrateddirk("serrateddirk");
	public static final ItemBase Caulfieldswarhammer = new Caulfieldswarhammer("caulfield'swarhammer");
	public static final ItemBase Kindlegem = new Kindlegem("kindlegem");
	public static final ItemBase Giantsbelt = new Giantsbelt("giant'sbelt");
	public static final ItemBase Crystallinebracer = new Crystallinebracer("crystallinebracer");
	public static final ItemBase Lostchapter = new Lostchapter("lostchapter");
	public static final ItemBase Bootsofswiftness = new Bootsofswiftness("bootsofswiftness");
	public static final ItemBase Berserkersgreaves = new Berserkersgreaves("berserker'sgreaves");
	public static final ItemBase Mercurystreads = new Mercurystreads("mercury'streads");
	public static final ItemBase Fiendishcodex = new Fiendishcodex("fiendishcodex");
	public static final ItemBase Chainvest = new Chainvest("chainvest");
	public static final ItemBase Bramblevest = new Bramblevest("bramblevest");
	public static final ItemBase Wardensmail = new Wardensmail("warden'smail");
	public static final ItemBase Hexdrinker = new Hexdrinker("hexdrinker");
	public static final ItemBase Lastwhisper = new Lastwhisper("lastwhisper");
	public static final ItemBase Ninjatabi = new Ninjatabi("ninjatabi");
	public static final ItemBase Spectrescowl = new Spectrescowl("spectre'scowl");
	public static final ItemBase Stinger = new Stinger("stinger");
	public static final ItemBase Skirmisherssaber = new Skirmisherssaber("skirmisherssaber");
	public static final ItemBase Stalkersblade = new Stalkersblade("stalkersblade");
	public static final ItemBase Tearofthegoddess = new Tearofthegoddess("tearofthegoddess");
	public static final ItemBase Catalystofaeons = new Catalystofaeons("catalystofaeons");
	public static final ItemBase Sheen = new Sheen("sheen");
	public static final ItemBase Phage = new Phage("phage");
	public static final ItemBase Aetherwisp = new Aetherwisp("aetherwisp");
	public static final ItemBase Glacialshroud = new Glacialshroud("glacialshroud");
	public static final ItemBase Executionerscalling = new Executionerscalling("executionerscalling");
	public static final ItemBase Oblivionorb = new Oblivionorb("oblivionorb");
	public static final ItemBase Sorcerersshoes = new Sorcerersshoes("sorcerersshoes");
	public static final ItemBase Ionianbootsoflucidity = new Ionianbootsoflucidity("ionianbootsoflucidity");

	//phase 1
	public static final ItemBase Bladeoftheruinedking = new Bladeoftheruinedking("bladeoftheruinedking");
	public static final ItemBase Youmuusghostblade = new Youmuusghostblade("youmuu'sghostblade");
	public static final ItemBase Warmogsarmor = new Warmogsarmor("warmog'sarmor");
	public static final ItemBase Deathfiregrasp = new Deathfiregrasp("deathfiregrasp");
	public static final ItemBase Rabadonsdeathcap = new Rabadonsdeathcap("rabadon'sdeathcap");
	public static final ItemBase Bansheesveil = new Bansheesveil("banshee'sveil");
	public static final ItemBase Thornmail = new Thornmail("thornmail");
	public static final ItemBase Duskbladeofdraktharr = new Duskbladeofdraktharr("duskbladeofdraktharr");
	public static final ItemBase Deadmansplate = new Deadmansplate("deadman'splate");
	public static final ItemBase Mawofmalmortius = new Mawofmalmortius("mawofmalmortius");
	public static final ItemBase Voidstaff = new Voidstaff("voidstaff");
	public static final ItemBase Sanguineblade = new Sanguineblade("sanguineblade");
	public static final ItemBase Spiritvisage = new Spiritvisage("spiritvisage");
	public static final ItemBase Nashorstooth = new Nashorstooth("nashor'stooth");
	public static final ItemBase Skirmisherssaber_cinderhulk = new Cinderhulk("skirmisherssaber-cinderhulk",Skirmisherssaber);
	public static final ItemBase Stalkersblade_cinderhulk = new Cinderhulk("stalkersblade-cinderhulk",Stalkersblade);
	public static final ItemBase Skirmisherssaber_runicechoes = new Runicechoes("skirmisherssaber-runicechoes",Skirmisherssaber);
	public static final ItemBase Stalkersblade_runicechoes = new Runicechoes("stalkersblade-runicechoes",Stalkersblade);
	public static final ItemBase Skirmisherssaber_warrior = new Warrior("skirmisherssaber-warrior",Skirmisherssaber);
	public static final ItemBase Stalkersblade_warrior = new Warrior("stalkersblade-warrior",Stalkersblade);
	public static final ItemBase Skirmisherssaber_bloodrazor  = new Bloodrazor ("skirmisherssaber-bloodrazor",Skirmisherssaber);
	public static final ItemBase Stalkersblade_bloodrazor  = new Bloodrazor ("stalkersblade-bloodrazor",Stalkersblade);
	public static final ItemBase Manamune = new Manamune("manamune");
	public static final ItemBase Archangelsstaff = new Archangelsstaff("archangelsstaff");
	public static final ItemBase Rodofages = new Rodofages("rodofages");
	public static final ItemBase Trinityforce = new Trinityforce("trinityforce");
	public static final ItemBase Lichbane = new Lichbane("lichbane");
	public static final ItemBase Iceborngauntlet = new Iceborngauntlet("iceborngauntlet");
	public static final ItemBase Muramana = new Muramana("muramana");
	public static final ItemBase Seraphsembrace = new Seraphsembrace("seraphsembrace");
	public static final ItemBase Lorddominiksregards = new Lorddominiksregards("lorddominiksregards");
	public static final ItemBase Mortalreminder = new Mortalreminder("mortalreminder");
	public static final ItemBase Morellonomicon = new Morellonomicon("morellonomicon");
}
