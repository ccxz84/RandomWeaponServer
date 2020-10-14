package RWAPI.Character.ForceMaster.skills;

import RWAPI.Character.ForceMaster.ForceMaster;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.Character.buff.Buff;
import RWAPI.init.ModSkills;
import RWAPI.items.skillItem.SkillBase;
import RWAPI.main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class frostarmor extends formaster {

    private ForceMaster forceMaster;
    private cool coolhandler;
    private bufftimer timerhandler;

    private final int buftime = 3;

    protected final double[] skilldamage={ //1초 회복량
            100,
            100,
            130,
            130,
            130,
            160,
            160,
            160,
            190,
            190,
            190,
            220,
            220,
            220,
            250,
            250,
            250,
            250

    };
    protected final double[] skillAdcoe={
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1,
            0.1
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

    protected final double[] cooldown = {
            120,
            120,
            115,
            115,
            115,
            110,
            110,
            110,
            110,
            110,
            110,
            100,
            100,
            100,
            100,
            100,
            100,
            100
    };

    public frostarmor(ForceMaster forceMaster, Item skill) {
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
        if(data.getCool(4) <= 0 && data.getSkill(4).equals(ModSkills.skill.get(ModSkills.frostarmor.SkillNumber)) && data.getCurrentMana() > skillcost[lv-1]){
            data.nonWorking = true;
            data.setGodmode(true);
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv-1]));
            data.setSkill(4, (SkillBase) ModSkills.skill.get(ModSkills.frostarmor2.SkillNumber));

            this.coolhandler = new cool(cooldown[lv-1],4, (EntityPlayerMP) player,this);
            this.coolhandler.flag = false;
            this.timerhandler = new bufftimer(3, (EntityPlayerMP) player,skilldamage[lv-1] + skillApcoe[lv-1] * data.getAp() + skillAdcoe[lv-1] * data.getAd());
        }
        else if(data.getSkill(4).equals(ModSkills.skill.get(ModSkills.frostarmor2.SkillNumber))&&timerhandler != null){
            data.setGodmode(false);
            Endbuf();
        }
    }

    private void Endbuf(){
        if(this.timerhandler != null){
            this.timerhandler.resetEffect();
            this.timerhandler.unregist();
            this.timerhandler = null;
            if(this.coolhandler != null){
                this.coolhandler.flag = true;
            }
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

    @Override
    public void switchSkill(PlayerData data, int idx, boolean flag) {
        if(flag == true){
            if(coolhandler != null){
                coolhandler.switchCool(true);
            }
            else{
                data.setCool(idx, 0);
            }
        }
        else{
            if(coolhandler != null){
                coolhandler.switchCool(false);
            }
            else{
                data.setCool(idx, 0);
            }
        }
    }

    class bufftimer extends Buff {

        PlayerData pdata;
        double posX, posY, posZ;

        public bufftimer(double duration, EntityPlayerMP player, double... data) {
            super(duration, player, data);
            posX = player.posX;
            posY = player.posY;
            posZ = player.posZ;
        }

        @Override
        public void BuffTimer(TickEvent.ServerTickEvent event) throws Throwable {
            if(timer > duration) {
                pdata.setGodmode(false);
                Endbuf();
            }
            timer++;
            pdata.getPlayer().connection.setPlayerLocation(posX,posY,posZ,pdata.getPlayer().rotationYaw,pdata.getPlayer().rotationPitch);
            pdata.setCool(4, ((float)(duration-timer)/40));
            if(timer % 20 == 0){
                double heal = pdata.getCurrentHealth() + data[0]/2 >= pdata.getMaxHealth() ? pdata.getMaxHealth() : pdata.getCurrentHealth() + data[0]/2;
                pdata.setCurrentHealth(heal);
            }
        }

        @Override
        public void setEffect() {
            pdata = main.game.getPlayerData(player.getUniqueID());
        }

        @Override
        public void resetEffect() {
            pdata.nonWorking = false;
            pdata.getPlayer().hurtResistantTime=0;
            pdata.setSkill(4, (SkillBase) ModSkills.skill.get(ModSkills.frostarmor.SkillNumber));
        }

        public void unregist(){
            MinecraftForge.EVENT_BUS.unregister(this);
        }
    }
}
