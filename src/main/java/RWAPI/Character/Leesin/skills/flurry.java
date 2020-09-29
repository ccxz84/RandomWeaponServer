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
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3
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
            3,
            3,
            3,
            3,
            3,
            3,
            3,
            3,
            3,
            3,
            3,
            3,
            3,
            3,
            3,
            3,
            3,
            3
    };

    protected final double[] skilldamage2={
            20,
            20,
            20,
            20,
            20,
            25,
            25,
            25,
            30,
            30,
            30,
            40,
            40,
            40,
            40,
            40,
            40,
            40
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

    @Override
    public double[] getskilldamage() {
        return this.skilldamage;
    }

    @Override
    public double[] getskillAdcoe() {
        return this.skillAdcoe;
    }

    @Override
    public double[] getskillApcoe() {
        return this.skillApcoe;
    }

    @Override
    public double[] getskillcost() {
        return this.skillcost;
    }

    @Override
    public double[] getcooldown() {
        return this.cooldown;
    }

    public double[] getSkilldamage2(){
        return this.skilldamage2;
    }

    class cool extends CooldownHandler {
        double attackspeed;

        public cool(double cool, int id, EntityPlayerMP player, double attackspeed) {
            super(cool, id, player);
            this.attackspeed = attackspeed;
            this.data = main.game.getPlayerData(player.getUniqueID());
            data.setPlusAttackspeed(this.data.getPlusAttackspeed() + this.attackspeed);
        }

        @SubscribeEvent
        public void skillTimer(TickEvent.ServerTickEvent event) throws Throwable {
            if(skillTimer > cooldown) {
                data.setCool(this.id, 0);
                this.data.setPlusAttackspeed(this.data.getPlusAttackspeed() - this.attackspeed);
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
                PlayerData data = main.game.getPlayerData(player.getUniqueID());
                int lv = data.getLevel();
                data.setCurrentMana(data.getCurrentMana() + skilldamage2[lv-1]);
                if(attack <= 0) {
                    this.data.setPlusAttackspeed(this.data.getPlusAttackspeed() - (float)this.attackspeed);
                    data.setCool(this.id, 0);
                    handler = null;
                    MinecraftForge.EVENT_BUS.unregister(this);
                }
            }
        }
    }
}
