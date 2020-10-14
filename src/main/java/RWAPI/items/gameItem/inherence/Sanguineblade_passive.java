package RWAPI.items.gameItem.inherence;

import RWAPI.Character.PlayerData;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.main;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class Sanguineblade_passive extends ItemBase.inherence_handler{

    PlayerData data;
    private AxisAlignedBB boundingBox;
    boolean flag = false, buffon = false;
    double plusas, plusamorpen;

    public Sanguineblade_passive(PlayerData data, ItemStack stack, double plusas, double plusamorpen) {
        super(data, stack);
        this.data = data;
        MinecraftForge.EVENT_BUS.register(this);
        this.plusas = plusas;
        this.plusamorpen = plusamorpen;
    }

    @Override
    public void removeHandler() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void gameTimer(TickEvent.ServerTickEvent event) {
        AxisAlignedBB temp = this.data.getPlayer().getEntityBoundingBox();

        this.setEntityBoundingBox(new AxisAlignedBB(temp.minX, temp.minY, temp.minZ, temp.maxX, temp.maxY, temp.maxZ));
        List<EntityLivingBase> mini =  this.data.getPlayer().world.getEntitiesWithinAABB(EntityLivingBase.class, temp.grow(3,1,3));

        flag = false;
        for(EntityLivingBase mi : mini){
            if(mi instanceof EntityPlayer){
                if(!mi.equals(this.data.getPlayer())){
                    flag = true;
                }
            }

        }
        if(flag == true){
            if(buffon == false){
                setbuf();
            }

            buffon = true;
        }
        else{
            if(buffon == true){
                resetbuf();
            }
            buffon = false;
        }
    }

    private void setbuf(){
        this.data.setArmorpenetration(this.data.getArmorpenetration() + plusamorpen);
        this.data.setPlusAttackspeed(this.data.getPlusAttackspeed() + plusas);
    }

    private void resetbuf(){
        this.data.setArmorpenetration(this.data.getArmorpenetration() - plusamorpen);
        this.data.setPlusAttackspeed(this.data.getPlusAttackspeed() - plusas);
    }


    public void setEntityBoundingBox(AxisAlignedBB bb)
    {
        this.boundingBox = bb;
    }

}
