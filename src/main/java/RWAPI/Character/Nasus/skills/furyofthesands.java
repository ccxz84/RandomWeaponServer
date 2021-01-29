package RWAPI.Character.Nasus.skills;

import RWAPI.Character.CooldownHandler;
import RWAPI.Character.EntityData;
import RWAPI.Character.Leesin.skills.dragonsrage;
import RWAPI.Character.Nasus.Nasus;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.Character.buff.Buff;
import RWAPI.Character.monster.entity.IMob;
import RWAPI.game.event.BaseEvent;
import RWAPI.game.event.UseSkillEventHandle;
import RWAPI.init.ModItems;
import RWAPI.init.ModSkills;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class furyofthesands implements Skill {

    buff buff;
    Nasus _class;

    public final int duration = 15;

    protected final double[][] skilldamage={
            {
                    300,
                    300,
                    300,
                    300,
                    300,
                    300,
                    300,
                    300,
                    450,
                    450,
                    450,
                    600,
                    600,
                    600,
                    600,
                    600,
                    600,
                    600
            },
            {
                    40,
                    40,
                    40,
                    40,
                    40,
                    40,
                    40,
                    40,
                    55,
                    55,
                    55,
                    70,
                    70,
                    70,
                    70,
                    70,
                    70,
                    70
            },
            {
                    40,
                    40,
                    40,
                    40,
                    40,
                    40,
                    40,
                    40,
                    55,
                    55,
                    55,
                    70,
                    70,
                    70,
                    70,
                    70,
                    70,
                    70
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
            70,
            70,
            80,
            80,
            80,
            90,
            90,
            90,
            110,
            110,
            110,
            130,
            130,
            130,
            130,
            130,
            130,
            130
    };

    protected final double[] cooldown = {
            100,
            100,
            100,
            100,
            100,
            100,
            100,
            100,
            100,
            100,
            100,
            100,
            100,
            100,
            100,
            100,
            100,
            100
    };

    public furyofthesands(Nasus nasus) {
        this._class = nasus;
    }

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();

        if(data.getCool(4) <= 0 && data.getCurrentMana() > skillcost[lv-1]) {
            data.nonWorking = true;
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv - 1]));
            this.raiseevent(data,skillcost[lv-1]);
            this.buff = new buff(duration,data,false,false,skilldamage[0][lv-1],skilldamage[1][lv-1],skilldamage[2][lv-1]);
            new CooldownHandler(cooldown[lv - 1], 4, (EntityPlayerMP) player,true,data.getSkillacc());
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
        return new double[0];
    }

    @Override
    public double[] getskillAdcoe() {
        return new double[0];
    }

    @Override
    public double[] getskillApcoe() {
        return new double[0];
    }

    @Override
    public double[] getskillcost() {
        return skillcost;
    }

    @Override
    public double[] getcooldown() {
        return this.cooldown;
    }

    public double[][] getSkilldamage(){
        return this.skilldamage;
    }

    private class buff extends Buff {

        ItemStack icon;

        public buff(double duration, PlayerData player, boolean debuff, boolean clean, double... data) {
            super(duration, player, debuff, clean, data);
            icon = new ItemStack(ModSkills.furyofthesands); //바꿀것
        }

        @Override
        public void setEffect() {
            player.setMaxHealth(player.getMaxHealth() + data[0]);
            player.setCurrentHealth(player.getCurrentHealth() + data[0]);
            player.setArmor(player.getArmor() + data[1]);
            player.setMagicresistance(player.getMagicresistance() + data[2]);
            _class.togglefury();
            this.player.addBuff(this);
        }

        @Override
        public void resetEffect() {
            if(player.getCurrentHealth() >= player.getMaxHealth() - data[0])
                player.setCurrentHealth(player.getMaxHealth() - data[0]);
            player.setMaxHealth(player.getMaxHealth() - data[0]);
            player.setArmor(player.getArmor() - data[1]);
            player.setMagicresistance(player.getMagicresistance() - data[2]);
            _class.togglefury();
            this.player.removeBuff(this);
        }

        @Override
        public ItemStack getBuffIcon() {
            return icon;
        }
    }
}
