package RWAPI.Character;

import RWAPI.main;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class CooldownHandler {
    protected int skillTimer = 0;
    protected int cooldown;
    protected int id;
    protected EntityPlayerMP player;
    protected PlayerData data;
    protected int skillacc;



    public CooldownHandler(double cool,int id,EntityPlayerMP player,boolean accflag,int skillacc) {
        int icool = (int)(cool*40);
        this.skillacc = skillacc;
        if(accflag){
            this.cooldown = (int)(icool - (icool * ((double)skillacc / (100 + skillacc))));
        }
        else{
            this.cooldown = icool;
        }
        this.id = id;
        this.player = player;
        this.data = main.game.getPlayerData(player.getUniqueID());
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void skillTimer(TickEvent.ServerTickEvent event) throws Throwable {
        if(skillTimer > cooldown) {
            data.setCool(this.id, 0);
            MinecraftForge.EVENT_BUS.unregister(this);
        }
        data.setCool(this.id, ((float)(cooldown-skillTimer)/40));
        skillTimer++;

    }
}
