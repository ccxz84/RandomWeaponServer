package RWAPI.Character.ForceMaster.skills;

import RWAPI.Character.ForceMaster.ForceMaster;
import RWAPI.Character.ForceMaster.entity.EntityBlazingpalm;
import RWAPI.Character.ForceMaster.entity.EntityFrostpalm;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.main;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class frostpalm extends formaster {

    private ForceMaster forceMaster;
    private passive handler;

    protected final double[] skilldamage={
            8,
            8.5,
            9,
            9.5,
            10,
            10.5,
            11,
            11.5,
            12.5,
            13.5,
            14.5,
            16,
            17.5,
            19,
            20.5,
            22,
            23.5,
            25

    };
    protected final double[] skillAdcoe={
            0.7,
            0.7,
            0.7,
            0.7,
            0.7,
            0.7,
            0.7,
            0.7,
            0.7,
            0.7,
            0.7,
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.8,
            0.8
    };
    protected final double[] skillApcoe={
            0.4,
            0.4,
            0.4,
            0.4,
            0.4,
            0.4,
            0.4,
            0.4,
            0.4,
            0.4,
            0.4,
            0.5,
            0.5,
            0.5,
            0.5,
            0.5,
            0.5,
            0.5
    };
    protected final double[] skillcost={
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1,
            1
    };

    protected final double[] cooldown = {
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

    public frostpalm(ForceMaster forceMaster, Item skill) {
        super(skill);
        this.forceMaster = forceMaster;
    }

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
        if(data.getCurrentMana() > skillcost[lv-1] && handler != null) {
            handler.attack();
        }

    }

    @Override
    public void Skillset(EntityPlayer player) {
        handler = new passive(main.game.getPlayerData(player.getUniqueID()));
    }

    @Override
    public void skillEnd(EntityPlayer player) {
        if(handler != null){
            handler.end();
            handler = null;
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

    @Override
    public void switchSkill(PlayerData data, int idx, boolean flag) {

    }

    private class passive{

        PlayerData data;
        int attack;

        private passive(PlayerData data){
            this.data = data;
            MinecraftForge.EVENT_BUS.register(this);
        }

        @SubscribeEvent
        public void formasterPassive(TickEvent.ServerTickEvent event) {
            if(getCoolStrength() != 1f){
                ++attack;
            }
        }

        public void attack(){

            if(getCoolStrength() == 1){
                EntityPlayer player = data.getPlayer();
                int lv = data.getLevel();
                data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv-1]));
                attack = 0;
                EntityFrostpalm palm = new EntityFrostpalm(player.world,player, (skilldamage[lv-1]+skillAdcoe[lv-1]*data.getAd()
                        + skillApcoe[lv-1]*data.getAp()));
                palm.setNoGravity(true);
                palm.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1f, 0);
                player.world.spawnEntity(palm);
                forceMaster.skillSwitching(data,0);
                forceMaster.skillSwitching(data,1);
            }
        }

        public float getcool() {
            double f1 = 1.2f * this.data.gettotalAttackSpeed();
            return (float)(1.0D / f1 * 20.0D);
        }

        public float getCoolStrength()
        {
            return MathHelper.clamp(((float)this.attack) / this.getcool(), 0.0F, 1.0F);
        }

        public void end(){
            MinecraftForge.EVENT_BUS.unregister(this);
        }
    }
}
