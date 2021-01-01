package RWAPI.items.potion;

import RWAPI.Character.PlayerData;
import RWAPI.main;
import RWAPI.util.GameStatus;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.*;

public class WitchDark extends PotionBase{
    public WitchDark(String name, boolean isBadPotion, int color, int iconIndeX, int ioncIndexY) {
        super(name, isBadPotion, color, iconIndeX, ioncIndexY);
    }

    @Override
    public boolean isInstant() {
        return true;
    }

    @Override
    public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entityLivingBaseIn, int amplifier, double health) {
        super.affectEntity(source, indirectSource, entityLivingBaseIn, amplifier, health);
        if(main.game.start == GameStatus.START){
            if(entityLivingBaseIn instanceof EntityPlayerMP){
                PlayerData data = main.game.getPlayerData(entityLivingBaseIn.getUniqueID());
                if(data != null){
                    data.getPlayer().addPotionEffect(new PotionEffect(Potion.getPotionById(15),80,2));
                }
            }
        }
    }
}
