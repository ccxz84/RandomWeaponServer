package RWAPI.Character.Leesin.skills;

import RWAPI.Character.EntityData;
import RWAPI.Character.Leesin.Leesin;
import RWAPI.Character.Leesin.entity.EntityResonating;
import RWAPI.Character.Leesin.entity.EntityUmpa;
import RWAPI.Character.PlayerData;
import RWAPI.Character.CooldownHandler;
import RWAPI.Character.Skill;
import RWAPI.Character.monster.entity.AbstractMob;
import RWAPI.init.ModSkills;
import RWAPI.items.skillItem.SkillBase;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import RWAPI.util.EntityStatus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class sonicwave implements Skill {

    private Leesin _class;
    private cool handler;
    private EntityResonating resonating = null;
    private ResonatingTimer timer;

    protected final double[][] skilldamage={
            {
                    98,
                    100,
                    102,
                    104,
                    106,
                    109,
                    112,
                    115,
                    118,
                    121,
                    124,
                    128,
                    132,
                    136,
                    140,
                    144,
                    148,
                    152

            },
            {
                    80,
                    82,
                    84,
                    86,
                    88,
                    100,
                    102,
                    104,
                    106,
                    108,
                    110,
                    113,
                    116,
                    119,
                    122,
                    125,
                    128,
                    131
            }
    };
    protected final double[] skillAdcoe={
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
            0.6,
            0.6,
            0.6,
            0.6,
            0.6,
            0.6,
            0.6
    };
    protected final double[] skillApcoe={
            0,0,0,0,0,0,0,0,0,0,0,0
    };
    protected final double[][] skillcost={
            {
                    50,
                    50,
                    50,
                    50,
                    50,
                    60,
                    60,
                    60,
                    70,
                    70,
                    70,
                    80,
                    80,
                    80,
                    80,
                    80,
                    80,
                    80
            },
            {
                    50,
                    50,
                    50,
                    50,
                    50,
                    55,
                    55,
                    55,
                    60,
                    60,
                    60,
                    65,
                    65,
                    65,
                    65,
                    65,
                    65,
                    65

            }
    };
    protected final double[][] skill1coe={
            {
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
                    0.6,
                    0.6,
                    0.6,
                    0.6,
                    0.6,
                    0.6,
                    0.6
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
                    0.35,
                    0.35,
                    0.35,
                    0.35,
                    0.35,
                    0.35,
                    0.35,
                    0.35,
                    0.35,
                    0.35
            }
    };

    protected final double[] cooldown = {
            11,
            11,
            11,
            11,
            11,
            10,
            10,
            10,
            9,
            9,
            9,
            8,
            8,
            8,
            8,
            8,
            8,
            8

    };

    public sonicwave(Leesin _class){
        this._class = _class;
    }

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
        if(data.getCool(1) <= 0 && data.getSkill(1).equals(ModSkills.skill.get(ModSkills.sonicwave.SkillNumber)) && data.getCurrentMana() > skillcost[0][lv-1]){
            data.nonWorking = true;
            data.nonWorking = false;
            System.out.println(player.getName());
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[0][lv-1]));
            EntityUmpa ls = new EntityUmpa(player.world,player,(float) (skilldamage[0][lv-1]+ skillAdcoe[lv-1] * data.getAd()));
            ls.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.95f, 0);
            //ls.posY -= 1;
            ls.setNoGravity(true);
            player.world.spawnEntity(ls);

            this.handler = new cool(cooldown[lv-1],1,(EntityPlayerMP) player);
            _class.skill0(player);
        }
        else if(data.getSkill(1).equals(ModSkills.skill.get(ModSkills.resonatingstrike.SkillNumber)) && data.getCurrentMana() > skillcost[1][lv-1]){
            data.nonWorking = true;
            data.nonWorking = false;


            if(timer.resonating.getThrower() instanceof EntityPlayer) {
                PlayerData target = main.game.getPlayerData(timer.resonating.getThrower().getUniqueID());
                if(target.getStatus() != EntityStatus.ALIVE){
                    return;
                }
                ((EntityPlayerMP) player).connection.setPlayerLocation(target.getPlayer().posX, target.getPlayer().posY, target.getPlayer().posZ, player.rotationYaw, player.rotationPitch);
                timer.resonating.getThrower().attackEntityFrom(net.minecraft.util.DamageSource.causeThrownDamage(player, timer.resonating), (float)1);
                DamageSource source = DamageSource.causeSkillPhysics(data, target, (float) (skilldamage[1][lv-1]+ skill1coe[1][lv-1] * data.getAd()
                        + skill1coe[1][lv-1] * (target.getMaxHealth() - target.getCurrentHealth())));
                DamageSource.attackDamage(source,true);
                DamageSource.EnemyStatHandler.EnemyStatSetter(source);
            }else if(timer.resonating.getThrower() instanceof AbstractMob){
                AbstractMob mob = (AbstractMob) timer.resonating.getThrower();
                EntityData target = mob.getData();
                if(target.getStatus() != EntityStatus.ALIVE){
                    return;
                }
                ((EntityPlayerMP) player).connection.setPlayerLocation(mob.posX - player.getLookVec().x *1.1, mob.posY, mob.posZ - player.getLookVec().z *1.1, player.rotationYaw, player.rotationPitch);
                DamageSource source = DamageSource.causeSkillPhysics(data, target, (float) (skilldamage[1][lv-1]+ skill1coe[0][lv-1] * data.getAd()
                        + skill1coe[1][lv-1] * (target.getMaxHealth() - target.getCurrentHealth())));
                DamageSource.attackDamage(source,true);
                DamageSource.EnemyStatHandler.EnemyStatSetter(source);
            }
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[1][lv-1]));
            timer.resonating.getThrower().attackEntityFrom(net.minecraft.util.DamageSource.causeThrownDamage(player, timer.resonating), (float)1);
            timer.resonating.setDead();
            timer.stop();
            timer = null;
            data.setSkill(1, (SkillBase) ModSkills.skill.get(ModSkills.sonicwave.SkillNumber));
            System.out.println(_class);
            _class.skill0(player);
        }


    }

    @Override
    public void Skillset(EntityPlayer player) {

    }

    @Override
    public void skillEnd(EntityPlayer player) {

    }

    public void setResonatingtimer(EntityResonating resonating, EntityPlayerMP thrower){
        PlayerData data = main.game.getPlayerData(thrower.getUniqueID());
        this.handler.resonatingtime = 1;
        data.setSkill(1, (SkillBase) ModSkills.skill.get(ModSkills.resonatingstrike.SkillNumber));
        this.timer = new ResonatingTimer(3,1,thrower,resonating);
    }

    //getter
    public EntityResonating getResonating() {
        return resonating;
    }


    //setter
    public void setResonating(EntityResonating resonating) {
        this.resonating = resonating;
    }

    class cool extends CooldownHandler {
        int resonatingtime = 0;

        public cool(double cool, int id, EntityPlayerMP player) {
            super(cool, id, player);
        }

        @Override
        public void skillTimer(TickEvent.ServerTickEvent event) throws Throwable {
            if(skillTimer > cooldown) {
                data.setCool(this.id, 0);
                MinecraftForge.EVENT_BUS.unregister(this);
            }
            if(resonatingtime == 0)
                data.setCool(this.id, ((float)(cooldown-skillTimer)/40));
            skillTimer++;
        }
    }

    class ResonatingTimer extends CooldownHandler{

        EntityResonating resonating;

        public ResonatingTimer(double cool, int id, EntityPlayerMP player, EntityResonating resonating) {
            super(cool, id, player);
            this.resonating = resonating;
            // TODO Auto-generated constructor stub
        }

        @Override
        public void skillTimer(TickEvent.ServerTickEvent event) throws Throwable {
            // TODO Auto-generated method stub
            if(skillTimer > cooldown) {
                resonating.setDead();
                data.setSkill(1, (SkillBase) ModSkills.skill.get(ModSkills.sonicwave.SkillNumber));
                if(handler != null)
                    handler.resonatingtime = 0;
                MinecraftForge.EVENT_BUS.unregister(this);
            }
            data.setCool(this.id, ((float)(cooldown-skillTimer)/40));
            skillTimer++;
        }

        public void stop(){
            resonating.setDead();
            data.setSkill(1, (SkillBase) ModSkills.skill.get(ModSkills.sonicwave.SkillNumber));
            if(handler != null)
                handler.resonatingtime = 0;
            MinecraftForge.EVENT_BUS.unregister(this);
        }

    }

    public double[] getskilldamage2(){
        return this.skilldamage[1];
    }

    public double[][] getskill1coe(){
        return this.skill1coe;
    }

    @Override
    public double[] getskilldamage() {
        return this.skilldamage[0];
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

    @Override
    public double[] getcooldown() {
        return this.cooldown;
    }
}
