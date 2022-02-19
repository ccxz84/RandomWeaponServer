package RWAPI.items.potion;

import RWAPI.init.ModPotion;
import RWAPI.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class PotionBase extends Potion {

    public PotionBase(String name, boolean isBadPotion, int color, int iconIndeX, int ioncIndexY){
        super(isBadPotion, color);
        ModPotion.POTIONS.add(this);
        setPotionName("effect." + name);
        setIconIndex(iconIndeX,ioncIndexY);
        setRegistryName(new ResourceLocation(Reference.MODID + ":" + name));
    }

    @Override
    public boolean hasStatusIcon() {
        return super.hasStatusIcon();
    }


}
