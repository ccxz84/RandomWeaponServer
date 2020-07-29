package RWAPI.Character.Leesin.skills;

import RWAPI.Character.CooldownHandler;
import RWAPI.Character.PlayerClass;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class flurry implements Skill {

    private PlayerClass _class;
    private int attack = 0;
    private cool handler;

    protected final double[] skilldamage={
            0.5, 0.54, 0.59, 0.6, 0.6, 0.7, 0.7, 0.7, 0.7, 0.7, 0.7, 0.75
    };
    protected final double[] skillAdcoe={
            0,0,0,0,0,0,0,0,0,0,0,0,0
    };
    protected final double[] skillApcoe={
            0,0,0,0,0,0,0,0,0,0,0,0
    };
    protected final double[] skillcost={
            0,0,0,0,0,0,0,0,0,0,0,0
    };

    protected final double[] cooldown = {
            3,3,3,3,3,3,3,3,3,3,3,3
    };

    public flurry(PlayerClass _class){
        this._class = _class;
    }

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
        if(this.handler == null) {
            this.attack = 2;
            handler = new cool(cooldown[lv-1],0,(EntityPlayerMP) player,skilldamage[lv-1]);
        }else{
            this.attack = 2;
        }
    }

    @Override
    public void Skillset(EntityPlayer player) {

    }

    @Override
    public void skillEnd(EntityPlayer player) {

    }
    class cool extends CooldownHandler {
        double attackspeed;

        public cool(double cool, int id, EntityPlayerMP player, double attackspeed) {
            super(cool, id, player);
            this.attackspeed = attackspeed;
            this.data = main.game.getPlayerData(player.getUniqueID());
            data.setAttackSpeed(this.data.getAttackSpeed() + (float)this.attackspeed);
        }

        @SubscribeEvent
        public void skillTimer(TickEvent.ServerTickEvent event) throws Throwable {
            if(skillTimer > cooldown) {
                data.setCool(this.id, 0);
                this.data.setAttackSpeed(this.data.getAttackSpeed() - (float)this.attackspeed);
                MinecraftForge.EVENT_BUS.unregister(this);
                handler = null;
                return;
            }
            data.setCool(this.id, attack);//에러발생
            skillTimer++;
        }
        @SubscribeEvent
        public void PlayerAttackEvent(AttackEntityEvent event)
        {
            if(event.getEntityPlayer().getUniqueID().equals(player.getUniqueID())) {
                attack--;
                if(attack <= 0) {
                    this.data.setAttackSpeed(this.data.getAttackSpeed() - (float)this.attackspeed);
                    data.setCool(this.id, 0);
                    handler = null;
                    MinecraftForge.EVENT_BUS.unregister(this);
                }
            }
        }
    }
}
