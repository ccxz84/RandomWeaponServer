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
import net.minecraft.item.ItemStack;
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

            this.coolhandler = new cool(cooldown[lv-1],4, (EntityPlayerMP) player,this,data.getSkillacc());
            this.coolhandler.flag = false;
            this.timerhandler = new bufftimer(3, data,skilldamage[lv-1] + skillApcoe[lv-1] * data.getAp() + skillAdcoe[lv-1] * data.getAd());
            this.raiseevent(data,skillcost[lv-1]);
        }
        else if(data.getSkill(4).equals(ModSkills.skill.get(ModSkills.frostarmor2.SkillNumber))&&timerhandler != null){
            data.setGodmode(false);
            Endbuf();
            this.raiseevent(data,0);
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

        double posX, posY, posZ;

        public bufftimer(double duration, PlayerData player, double... data) {
            super(duration, player,true,false, data);
            posX = player.getPlayer().posX;
            posY = player.getPlayer().posY;
            posZ = player.getPlayer().posZ;
        }

        @Override
        public void BuffTimer(TickEvent.ServerTickEvent event) throws Throwable {
            if(timer > duration) {
                player.setGodmode(false);
                Endbuf();
            }
            timer++;
            player.getPlayer().connection.setPlayerLocation(posX,posY,posZ,player.getPlayer().rotationYaw,player.getPlayer().rotationPitch);
            player.setCool(4, ((float)(duration-timer)/40));
            if(timer % 20 == 0){
                double heal = player.getCurrentHealth() + data[0]/2 >= player.getMaxHealth() ? player.getMaxHealth() : player.getCurrentHealth() + data[0]/2;
                player.setCurrentHealth(heal);
            }
        }

        @Override
        public void setEffect() {
            player.addBuff(this);
        }

        @Override
        public void resetEffect() {
            player.nonWorking = false;
            player.getPlayer().hurtResistantTime=0;
            player.setSkill(4, (SkillBase) ModSkills.skill.get(ModSkills.frostarmor.SkillNumber));
            player.removeBuff(this);
        }

        @Override
        public ItemStack getBuffIcon() {
            return new ItemStack(ModSkills.frostarmor);
        }

        public void unregist(){
            MinecraftForge.EVENT_BUS.unregister(this);
        }
    }
}
