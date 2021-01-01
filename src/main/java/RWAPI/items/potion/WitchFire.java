package RWAPI.items.potion;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.monster.entity.EntityWitch;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import RWAPI.util.GameStatus;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class WitchFire extends PotionBase{
    public WitchFire(String name, boolean isBadPotion, int color, int iconIndeX, int ioncIndexY) {
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
            if(entityLivingBaseIn instanceof EntityPlayerMP && indirectSource instanceof EntityWitch){
                PlayerData target = main.game.getPlayerData(entityLivingBaseIn.getUniqueID());
                EntityData attacker = ((EntityWitch) indirectSource).getData();
                DamageSource dsoucre = DamageSource.causeUnknownMagic(attacker,target,attacker.getAd());
                DamageSource.attackDamage(dsoucre,false);
                DamageSource.EnemyStatHandler.EnemyStatSetter(dsoucre);
            }
        }
    }
}
