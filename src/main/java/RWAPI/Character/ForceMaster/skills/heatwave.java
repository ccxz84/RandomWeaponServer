package RWAPI.Character.ForceMaster.skills;

import RWAPI.Character.CooldownHandler;
import RWAPI.Character.ForceMaster.ForceMaster;
import RWAPI.Character.ForceMaster.entity.EntityHeatwave;
import RWAPI.Character.ForceMaster.entity.EntitytempHeatwave;
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

public class heatwave extends formaster {

    private ForceMaster forceMaster;
    private cool handler;
    private buff buff;
    double posX, posY, posZ;
    private EntitytempHeatwave wave;

    protected final double[][] skilldamage={ //이동속도 증가
            {
                    10,
                    10,
                    10,
                    10,
                    10,
                    10,
                    10,
                    10,
                    15,
                    15,
                    15,
                    15,
                    15,
                    15,
                    15,
                    15,
                    15,
                    15
            },
            {
                    70,
                    73,
                    76,
                    79,
                    82,
                    85,
                    88,
                    91,
                    95,
                    99,
                    103,
                    107,
                    111,
                    115,
                    119,
                    123,
                    127,
                    131
            }
    };
    protected final double[] skillAdcoe={
            0.25,
            0.25,
            0.25,
            0.25,
            0.25,
            0.25,
            0.25,
            0.25,
            0.25,
            0.25,
            0.25,
            0.25,
            0.25,
            0.25,
            0.25,
            0.25,
            0.25,
            0.25
    };
    protected final double[] skillApcoe={
            0.45,
            0.45,
            0.45,
            0.45,
            0.45,
            0.45,
            0.45,
            0.45,
            0.45,
            0.45,
            0.45,
            0.45,
            0.45,
            0.45,
            0.45,
            0.45,
            0.45,
            0.45
    };
    protected final double[][] skillcost={
            {
                    -1,
                            -1,
                            -1,
                            -1,
                            -1,
                            -1,
                            -1,
                            -1,
                            -1,
                            -1,
                            -1,
                            -1,
                            -1,
                            -1,
                            -1,
                            -1,
                            -1,
                            -1
            },
            {
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

            }
    };

    protected final double[] cooldown = {
            6,
            6,
            6,
            6,
            6,
            6,
            6,
            6,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5
    };

    public heatwave(ForceMaster forceMaster, Item skill) {
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

        if(data.getCool(3) <= 0 && data.getCurrentMana() > skillcost[0][lv-1]&&this.handler == null&& data.getSkill(3).equals(ModSkills.skill.get(ModSkills.heatwave.SkillNumber))) {
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[0][lv-1]));
            data.setSkill(3, (SkillBase) ModSkills.skill.get(ModSkills.heatwave2.SkillNumber));
            wave = new EntitytempHeatwave(player.world,data,this);
            wave.posX = player.posX;
            wave.posY = player.posY;
            wave.posZ = player.posZ;
            wave.setNoGravity(true);
            wave.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0f, 0);
            posX = wave.posX;
            posY = wave.posY;
            posZ = wave.posZ;
            player.world.spawnEntity(wave);
            handler = new cool(3,3, (EntityPlayerMP) player,this);
            buff = new buff(2, (EntityPlayerMP) player,skilldamage[0][lv-1]);
        }
        else if(data.getCurrentMana() > skillcost[1][lv-1]&&data.getSkill(3).equals(ModSkills.skill.get(ModSkills.heatwave2.SkillNumber))&&this.wave != null&& this.handler != null){
            waitend(true,data);
        }
    }

    public void waitend(boolean flag,PlayerData data){
        int lv = data.getLevel();
        if(this.handler != null){
            this.handler.setCool(cooldown[lv-1]);
        }
        data.setSkill(3, (SkillBase) ModSkills.skill.get(ModSkills.heatwave.SkillNumber));
        if(flag == false){
            if(this.wave != null){
                this.wave.setDead();
                this.wave = null;
            }
        }
        else{
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[1][lv-1]));
            this.wave.setDead();
            EntityHeatwave heatwave  = new EntityHeatwave(data.getPlayer().world,data.getPlayer(), (skilldamage[1][lv-1]+skillAdcoe[lv-1]*data.getAd()
                    + skillApcoe[lv-1]*data.getAp()),posX,posY,posZ);
            heatwave.setNoGravity(true);
            heatwave.shoot(data.getPlayer(), data.getPlayer().rotationPitch, data.getPlayer().rotationYaw, 0.0F, 0f, 0);
            data.getPlayer().world.spawnEntity(heatwave);
            wave = null;
        }

    }

    @Override
    public void Skillset(EntityPlayer player) {

    }

    @Override
    public void skillEnd(EntityPlayer player) {
        if(handler != null){
            handler.unregist();
            handler = null;
        }
        if(buff != null){
            buff.unregist();
            buff = null;
        }
        if(wave != null){
            wave.setDead();
            wave = null;
        }
    }

    @Override
    public double[] getskilldamage() {
        return this.skilldamage[0];
    }


    public double[] getskilldamage1() {
        return this.skilldamage[1];
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
        return this.skillcost[0];
    }
    public double[] getskillcost1() {
        return this.skillcost[1];
    }

    @Override
    public double[] getcooldown() {
        return this.cooldown;
    }

    @Override
    public void switchSkill(PlayerData data, int idx, boolean flag) {
        if(flag == true){
            if(handler != null){
                handler.switchCool(true);
            }
            else{
                data.setCool(idx, 0);
            }
        }
        else{
            if(handler != null){
                handler.switchCool(false);
            }
            else{
                data.setCool(idx, 0);
            }
        }
    }

    class cool extends CooldownHandler {

        boolean flag = true;
        formaster instance;
        PlayerData data;
        boolean waveflag = false;

        public cool(double cool, int id, EntityPlayerMP player, formaster instance) {
            super(cool, id, player);
            this.instance = instance;
            this.data = main.game.getPlayerData(player.getUniqueID());
        }

        public void unregist(){
            MinecraftForge.EVENT_BUS.unregister(this);
        }

        @Override
        public void skillTimer(TickEvent.ServerTickEvent event) throws Throwable {
            if(skillTimer > cooldown) {
                if(flag == true){
                    data.setCool(this.id, 0);
                }
                if(waveflag == false){
                    waitend(false,data);
                }
                else{
                    instance.skillEnd(player);
                    MinecraftForge.EVENT_BUS.unregister(this);
                }

            }
            if(flag == true)
                data.setCool(this.id, ((float)(cooldown-skillTimer)/40));
            skillTimer++;
        }

        public void switchCool(boolean flag){
            this.flag = flag;
        }

        public void setCool(double cool){
            this.waveflag = true;
            this.cooldown = (int) (cool * 40);
            this.skillTimer = 0;
        }
    }

    class buff extends Buff {
        PlayerData pdata;

        public buff(double duration, EntityPlayerMP player, double... data) {
            super(duration, player, data);
        }

        @Override
        public void setEffect() {
            pdata = main.game.getPlayerData(player.getUniqueID());
            pdata.setMove(pdata.getMove() + data[0]);
        }

        @Override
        public void resetEffect() {
            pdata.setMove(pdata.getMove() - data[0]);
        }

        public void unregist(){
            MinecraftForge.EVENT_BUS.unregister(this);
        }
    }
}
