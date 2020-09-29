package RWAPI.Character.Leesin.skills;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerClass;
import RWAPI.Character.PlayerData;
import RWAPI.Character.CooldownHandler;
import RWAPI.Character.monster.entity.AbstractMob;
import RWAPI.Character.Skill;
import RWAPI.main;
import RWAPI.util.DamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Random;

public class dragonsrage implements Skill {

    private PlayerClass _class;
    private cool handler;

    protected final double[] skilldamage={
            250,
            250,
            290,
            290,
            290,
            340,
            340,
            340,
            400,
            400,
            400,
            450,
            450,
            450,
            460,
            460,
            460,
            470
    };
    protected final double[] skillAdcoe={
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.9,
            0.9,
            0.9,
            0.9,
            0.9,
            0.9,
            0.9,
            0.9,
            0.9,
            0.9

    };
    protected final double[] skillApcoe={
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0
    };
    protected final double[] skillcost={
            40,
            40,
            40,
            40,
            40,
            45,
            45,
            45,
            45,
            45,
            45,
            50,
            50,
            50,
            50,
            50,
            50,
            50
    };

    protected final double[] cooldown = {
            100,
            100,
            95,
            95,
            95,
            90,
            90,
            90,
            85,
            85,
            85,
            80,
            80,
            80,
            80,
            80,
            80,
            80

    };

    public dragonsrage(PlayerClass _class){
        this._class = _class;
    }

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
        if(data.getCool(4) <= 0&& data.getCurrentMana() > skillcost[lv-1]) {
            data.nonWorking = true;
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv-1]));
            data.nonWorking = false;
            this.handler = new cool(3, 4, (EntityPlayerMP) player,(float) (skilldamage[lv-1] + skillAdcoe[lv-1] * data.getAd() - data.getAd()),cooldown[lv-1]);
            _class.skill0(player);

        }
    }

    @Override
    public void Skillset(EntityPlayer player) {

    }

    @Override
    public void skillEnd(EntityPlayer player) {

    }
    class cool extends CooldownHandler {
        float skilldamage1;
        double cool;

        public cool(double cool, int id, EntityPlayerMP player, float skilldamage1, double waiting_time) {
            super(cool, id, player);
            this.skilldamage1 = skilldamage1;
            this.cool = waiting_time;
        }

        @Override
        public void skillTimer(TickEvent.ServerTickEvent event) throws Throwable {
            if(skillTimer > cooldown) {
                new CooldownHandler(cool, 4, (EntityPlayerMP) player);
                MinecraftForge.EVENT_BUS.unregister(this);
            }
            data.setCool(this.id, ((float)(cooldown-skillTimer)/40));
            skillTimer++;
        }

        @SubscribeEvent
        public void PlayerAttackEvent(AttackEntityEvent event)
        {
            if(event.getEntityPlayer().getUniqueID().equals(player.getUniqueID())) {
                EntityLivingBase etarget = (EntityLivingBase) event.getTarget();
                System.out.println("test");
                etarget.hurtResistantTime = 0;
                etarget.knockBack(event.getEntityLiving(), 2, -event.getEntityPlayer().getLookVec().x, -event.getEntityPlayer().getLookVec().z);
                PlayerData attacker = main.game.getPlayerData(event.getEntityPlayer().getUniqueID());
                EntityData target = (etarget instanceof EntityPlayer) ? main.game.getPlayerData(etarget.getUniqueID()) : ((AbstractMob)etarget).getData();
                RWAPI.util.DamageSource sourcee = RWAPI.util.DamageSource.causeSkill(attacker, target, skilldamage1);
                RWAPI.util.DamageSource.attackDamage(sourcee,true);
                DamageSource.EnemyStatHandler.EnemyStatSetter(sourcee);
                new CooldownHandler(cool, 4, (EntityPlayerMP) player);
                MinecraftForge.EVENT_BUS.unregister(this);

            }
        }
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
}
