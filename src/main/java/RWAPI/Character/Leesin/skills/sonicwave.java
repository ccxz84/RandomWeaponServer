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
import RWAPI.util.DamageSource;
import net.minecraft.entity.EntityLivingBase;
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
            {100, 102, 104, 106, 108, 110, 112, 114, 116, 118, 120, 122},
            {110, 114, 118, 122, 124, 130, 132, 134, 136, 138, 140, 200}
    };
    protected final double[] skillAdcoe={
            0.4, 0.4, 0.4, 0.4, 0.4, 0.6, 0.6, 0.6, 0.6, 0.6, 0.6, 0.8
    };
    protected final double[] skillApcoe={
            0,0,0,0,0,0,0,0,0,0,0,0
    };
    protected final double[][] skillcost={
            {50,50,55,55,55,70,70,70,75,75,75,90},
            {50,50,55,55,55,70,70,70,75,75,75,90}
    };
    protected final double[][] skill1coe={
            {0.4, 0.4, 0.4, 0.4, 0.4, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5},
            {0.4, 0.4, 0.4, 0.4, 0.4, 0.6, 0.6, 0.6, 0.6, 0.6, 0.6, 0.7}
    };

    protected final double[] cooldown = {
            8, 7.9, 7.9, 7.7, 7.4, 6.5, 6.3, 6.1, 6, 5.8, 5.6, 5
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
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[0][lv-1]));
            EntityUmpa ls = new EntityUmpa(player.world,player,(float) (skilldamage[1][lv-1]+ skillAdcoe[lv-1] * data.getAd()));
            ls.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 0.7f, 0);
            ls.setNoGravity(true);
            player.world.spawnEntity(ls);

            this.handler = new cool(cooldown[lv-1],1,(EntityPlayerMP) player);
            _class.skill0(player);
        }
        else if(data.getSkill(1).equals(ModSkills.skill.get(ModSkills.resonatingstrike.SkillNumber)) && data.getCurrentMana() > skillcost[1][lv-1]){
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[1][lv-1]));
            if(timer.resonating.getThrower() instanceof EntityPlayer) {
                PlayerData target = main.game.getPlayerData(timer.resonating.getThrower().getUniqueID());
                ((EntityPlayerMP) player).connection.setPlayerLocation(target.getPlayer().posX, target.getPlayer().posY, target.getPlayer().posZ, player.rotationYaw, player.rotationPitch);
                timer.resonating.getThrower().attackEntityFrom(net.minecraft.util.DamageSource.causeThrownDamage(player, timer.resonating), (float)1);
                RWAPI.util.DamageSource source = RWAPI.util.DamageSource.causeSkill(data, target, (float) (skilldamage[1][lv-1]+ skill1coe[1][lv-1] * data.getAd()
                        + skill1coe[1][lv-1] * (target.getMaxHealth() - target.getCurrentHealth())));
                RWAPI.util.DamageSource.attackDamage(source);
                DamageSource.EnemyStatHandler.EnemyStatSetter(source);
            }else if(timer.resonating.getThrower() instanceof AbstractMob){
                AbstractMob mob = (AbstractMob) timer.resonating.getThrower();
                EntityData target = mob.getData();
                ((EntityPlayerMP) player).connection.setPlayerLocation(mob.posX - player.getLookVec().x *1.1, mob.posY, mob.posZ - player.getLookVec().z *1.1, player.rotationYaw, player.rotationPitch);
                RWAPI.util.DamageSource source = RWAPI.util.DamageSource.causeSkill(data, target, (float) (skilldamage[1][lv-1]+ skill1coe[1][lv-1] * data.getAd()
                        + skill1coe[1][lv-1] * (target.getMaxHealth() - target.getCurrentHealth())));
                RWAPI.util.DamageSource.attackDamage(source);
                DamageSource.EnemyStatHandler.EnemyStatSetter(source);
            }

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
}