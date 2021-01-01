package RWAPI.Character.ForceMaster.skills;

import RWAPI.Character.ForceMaster.ForceMaster;
import RWAPI.Character.ForceMaster.entity.EntityBlazingpalm;
import RWAPI.Character.ForceMaster.entity.EntityFirestorm;
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

public class blazingpalm extends formaster {

    private final int managet = 1; //열화장 내력 회복량

    private ForceMaster forceMaster;
    private passive handler;

    protected final double[] skilldamage={
            10,
            10.5,
            11,
            11.5,
            12,
            13,
            14,
            15,
            16.5,
            18,
            19.5,
            21.5,
            23.5,
            25.5,
            27.5,
            29.5,
            31.5,
            33.5
    };
    protected final double[] skillAdcoe={
            0.6,
            0.6,
            0.6,
            0.6,
            0.6,
            0.6,
            0.6,
            0.6,
            0.6,
            0.6,
            0.6,
            0.7,
            0.7,
            0.7,
            0.7,
            0.7,
            0.7,
            0.7

    };
    protected final double[] skillApcoe={
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
            0.4,
            0.4,
            0.4,
            0.4,
            0.4,
            0.4,
            0.4
    };
    protected final double[] skillcost={
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

    public blazingpalm(ForceMaster forceMaster, Item skill) {
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
        if(handler != null) {
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
        return new double[0];
    }

    @Override
    public double[] getcooldown() {
        return new double[0];
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
                if(data.getCurrentMana() <= data.getMaxMana()){
                    double mana = data.getCurrentMana() + managet >= data.getMaxMana() ? data.getMaxMana() : data.getCurrentMana() + managet;
                    data.setCurrentMana(mana);
                }
                attack = 0;
                EntityBlazingpalm palm = new EntityBlazingpalm(player.world,player, (skilldamage[lv-1]+skillAdcoe[lv-1]*data.getAd()
                        + skillApcoe[lv-1]*data.getAp()));
                palm.setNoGravity(true);
                palm.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1f, 0);
                player.world.spawnEntity(palm);
                forceMaster.skillSwitching(data,0);
            }
        }

        public float getcool() {
            double f1 = 1.2f * this.data.gettotalAttackSpeed()+0.1;
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
