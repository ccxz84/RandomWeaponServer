package RWAPI.items.gameItem.inherence;

import RWAPI.Character.PlayerData;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.util.NetworkUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Dreadnought_passive extends ItemBase.inherence_handler{

    PlayerData data;
    double walking = 0;
    int walkingstack = 0;
    double plusmove;
    ItemStack stack;
    private int idx;
    double distanceWalkedModified;

    public Dreadnought_passive(PlayerData data, ItemStack stack, double plusmove, int idx) {
        super(data, stack);
        this.stack = stack;
        this.data = data;
        this.plusmove = plusmove;
        this.idx = idx;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void walking_checkEvent(TickEvent.ServerTickEvent event) {
        if(walkingstack < 10){
            if(data.getPlayer().distanceWalkedModified != this.distanceWalkedModified ){
                walking += Math.abs(data.getPlayer().prevDistanceWalkedModified - distanceWalkedModified);
            }
            if(walkingstack < 10 && walking > 5){
                ++walkingstack;
                walking = 0;
                data.setMove(data.getMove() + plusmove);
            }
            if(!this.stack.equals(this.data.getPlayer().inventory.getStackInSlot(this.idx))){
                this.stack = this.data.getPlayer().inventory.getStackInSlot(this.idx);
            }
            NetworkUtil.setCool(stack,walkingstack);
            this.distanceWalkedModified = data.getPlayer().distanceWalkedModified;
        }
        //System.out.println("distanceWalkedModified : " + data.getPlayer().distanceWalkedModified + " prevDistanceWalkedModified : " + data.getPlayer().prevDistanceWalkedModified);
    }

    @SubscribeEvent
    public void attackEvent(LivingAttackEvent event){
        if(data.getPlayer().equals(event.getSource().getTrueSource())){
            data.setMove(data.getMove() - plusmove * walkingstack);
            walkingstack = 0;
        }
    }

    @Override
    public void removeHandler() {
        super.removeHandler();
        data.setMove(data.getMove() - plusmove * walkingstack);
        MinecraftForge.EVENT_BUS.unregister(this);
        NetworkUtil.setCool(stack,0);
    }
}
