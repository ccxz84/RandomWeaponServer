package RWAPI.init;

import RWAPI.items.gameItem.ItemBase;
import RWAPI.items.potion.PotionTypeBase;
import RWAPI.items.potion.WitchDark;
import RWAPI.items.potion.WitchFire;
import RWAPI.items.potion.WitchIce;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;

import java.util.ArrayList;
import java.util.List;

public class ModPotion {

    public static final List<Potion> POTIONS = new ArrayList<Potion>();
    public static final List<PotionType> POTIONTYPES = new ArrayList<PotionType>();


    public static final Potion Witch_Dark = new WitchDark("witch_dark",false,0x140607,0,0);
    public static final Potion Witch_Fire = new WitchFire("witch_fire",false,0x750512,0,0);
    public static final Potion Witch_Ice = new WitchIce("witch_ice",false,0x85a4d6,0,0);

    public static final PotionType Witch_Dark_Type = new PotionTypeBase("witch_dark", new PotionEffect[]{new PotionEffect(Witch_Dark,80)}).setRegistryName("witch_dark");
    public static final PotionType Witch_Fire_Type = new PotionTypeBase("witch_fire", new PotionEffect[]{new PotionEffect(Witch_Fire,80)}).setRegistryName("witch_fire");
    public static final PotionType Witch_Ice_Type = new PotionTypeBase("witch_ice", new PotionEffect[]{new PotionEffect(Witch_Ice,80)}).setRegistryName("witch_ice");

}
