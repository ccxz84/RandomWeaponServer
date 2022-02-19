package RWAPI.Character.Kassadin.skills;

import RWAPI.Character.CooldownHandler;
import RWAPI.Character.EntityData;
import RWAPI.Character.ForceMaster.skills.formaster;
import RWAPI.Character.Kassadin.entity.EntityNullsphere;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.Character.buff.Buff;
import RWAPI.game.event.BaseEvent;
import RWAPI.game.event.UseSkillEventHandle;
import RWAPI.init.ModSkills;
import RWAPI.main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

public class nullsphere implements Skill {

    public final double duration = 1.5;

    protected final double[][] skilldamage={
            {
                    70,
                    72,
                    74,
                    76,
                    78,
                    80,
                    82,
                    84,
                    87,
                    90,
                    93,
                    97,
                    101,
                    105,
                    109,
                    113,
                    117,
                    121
            },
            {
                    30,
                    32,
                    34,
                    36,
                    38,
                    40,
                    42,
                    44,
                    47,
                    50,
                    53,
                    57,
                    61,
                    65,
                    69,
                    73,
                    77,
                    81
            }
    };
    protected final double[] skillAdcoe={
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
    protected final double[][] skillApcoe={
            {
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
                    0.7,
                    0.7,
                    0.7,
                    0.7,
                    0.7,
                    0.7,
                    0.7
            },
            {
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
            }
    };
    protected final double[] skillcost={
            70,
            70,
            70,
            70,
            70,
            75,
            75,
            75,
            80,
            80,
            80,
            90,
            90,
            90,
            90,
            90,
            90,
            90
    };

    protected final double[] cooldown = {
            11,
            10.95,
            10.9,
            10.85,
            10.8,
            10.75,
            10.7,
            10.65,
            10.6,
            10.55,
            10.35,
            10.15,
            9.95,
            9.75,
            9.55,
            9.35,
            9.15,
            8.95
    };

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
        if(data.getCool(1) <= 0 && data.getCurrentMana() > skillcost[lv-1]) {
            data.nonWorking = true;
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv - 1]));
            EntityNullsphere sphere = new EntityNullsphere(data.getEntity().world,data.getPlayer(),skilldamage[0][lv-1] + skillApcoe[0][lv-1] * data.getAp());
            sphere.setNoGravity(true);
            sphere.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1f, 0);
            player.world.spawnEntity(sphere);
            new buff(duration,data,false,false);
            this.raiseevent(data,skillcost[lv-1]);
            new CooldownHandler(cooldown[lv-1], 1, (EntityPlayerMP)player,true,data.getSkillacc());
            data.nonWorking = false;
        }
    }

    @Override
    public void Skillset(EntityPlayer player) {

    }

    @Override
    public void skillEnd(EntityPlayer player) {

    }

    @Override
    public void raiseevent(PlayerData data, double mana) {
        UseSkillEventHandle.UseSkillEvent event = new UseSkillEventHandle.UseSkillEvent(data,mana);
        for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
            main.game.getEventHandler().RunEvent(event,priority);
        }
    }

    @Override
    public double[] getskilldamage() {
        return this.skilldamage[0];
    }

    @Override
    public double[] getskillAdcoe() {
        return new double[0];
    }

    @Override
    public double[] getskillApcoe() {
        return skillApcoe[0];
    }

    @Override
    public double[] getskillcost() {
        return skillcost;
    }

    @Override
    public double[] getcooldown() {
        return cooldown;
    }

    public double[] getskill1apcode(){
        return this.skillApcoe[1];
    }

    public double[] getskill1damage(){
        return this.skilldamage[1];
    }

    private class buff extends Buff{

        ItemStack icon;
        EntityData.shield instance;

        public buff(double duration, PlayerData player, boolean debuff, boolean clean, double... data) {
            super(duration, player, debuff, clean, data);
            icon = new ItemStack(ModSkills.nullsphere);
        }

        @Override
        public void setEffect() {
            int lv = player.getLevel();
            player.addBuff(this);
            this.instance = new EntityData.shield(skilldamage[1][lv-1] + skillApcoe[1][lv-1] * player.getAp());
            player.addShield(this.instance);
        }

        @Override
        public void resetEffect() {
            player.removeBuff(this);
            player.removeShield(this.instance);
        }

        @Override
        public ItemStack getBuffIcon() {
            return null;
        }
    }
}
