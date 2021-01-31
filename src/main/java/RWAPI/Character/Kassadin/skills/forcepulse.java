package RWAPI.Character.Kassadin.skills;

import RWAPI.Character.*;
import RWAPI.Character.Kassadin.entity.EntityForcepulse;
import RWAPI.Character.Kassadin.entity.EntityNullsphere;
import RWAPI.Character.buff.Buff;
import RWAPI.Character.monster.entity.IMob;
import RWAPI.game.event.BaseEvent;
import RWAPI.game.event.UseSkillEventHandle;
import RWAPI.init.ModSkills;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class forcepulse implements Skill {

    public final double duration = 1;

    protected final double[][] skilldamage={
            {
                    80,
                    81,
                    82,
                    83,
                    84,
                    85,
                    86,
                    87,
                    88,
                    89,
                    90,
                    92,
                    94,
                    96,
                    98,
                    100,
                    102,
                    104
            },
            {
                    50,
                    50,
                    50,
                    50,
                    50,
                    50,
                    50,
                    50,
                    50,
                    50,
                    50,
                    60,
                    60,
                    60,
                    60,
                    60,
                    60,
                    60
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
    };
    protected final double[] skillcost={
            60,
            60,
            60,
            60,
            60,
            65,
            65,
            65,
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
    };

    protected final double[] cooldown = {
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
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

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();
        float x = (float) player.getLookVec().x;
        float y = (float) player.getLookVec().y;
        float z = (float) player.getLookVec().z;
        if(data.getCool(3) <= 0 && data.getCurrentMana() > skillcost[lv-1]) {
            data.nonWorking = true;
            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv - 1]));
            EntityForcepulse sphere = new EntityForcepulse(data.getEntity().world,data.getPlayer(),0);
            sphere.setNoGravity(true);
            sphere.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 5f, 0);
            player.world.spawnEntity(sphere);
            List<Entity> entities = data.getPlayer().world.getEntitiesWithinAABBExcludingEntity(player,new AxisAlignedBB(
                    player.posX + (x >0 ? -1 : 1) ,player.posY,player.posZ+ (z >0 ? -1 : 1)
                    ,player.posX + (x >0 ? 4 : -4),player.posY,player.posZ+ (z >0 ? 4 : -4)).grow(0,0.75,0));
            for(Entity entity : entities){
                EntityData target = null;
                if(entity instanceof EntityPlayerMP){
                    target = main.game.getPlayerData(entity.getUniqueID());
                    new buff(duration, (PlayerData) target,false,false,skilldamage[1][lv-1]);
                }
                else if(entity instanceof IMob){
                    target = ((IMob) entity).getData();
                }
                if(target != null) {
                    DamageSource source = DamageSource.causeSkillRangedMagic(data, target, skilldamage[0][lv - 1] + skillApcoe[lv - 1] * data.getAp());
                    DamageSource.attackDamage(source, true);
                    DamageSource.EnemyStatHandler.EnemyStatSetter(source);
                    target.getEntity().attackEntityFrom(net.minecraft.util.DamageSource.causeThrownDamage(new SkillEntity(data.getEntity().world), data.getEntity()), (float) 1);
                }
            }
            this.raiseevent(data,skillcost[lv-1]);
            new CooldownHandler(cooldown[lv-1], 3, (EntityPlayerMP)player,true,data.getSkillacc());
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
        return skilldamage[0];
    }

    @Override
    public double[] getskillAdcoe() {
        return new double[0];
    }

    @Override
    public double[] getskillApcoe() {
        return skillApcoe;
    }

    @Override
    public double[] getskillcost() {
        return skillcost;
    }

    @Override
    public double[] getcooldown() {
        return cooldown;
    }

    public double[] getskill1damage(){
        return this.skilldamage[1];
    }

    private class buff extends Buff {

        ItemStack icon;
        double minusmove;

        public buff(double duration, PlayerData player, boolean debuff, boolean clean, double... data) {
            super(duration, player, debuff, clean, data);
            icon = new ItemStack(ModSkills.nullsphere);
        }

        @Override
        public void setEffect() {
            minusmove = (player.getMove()/100) * (100 -data[0]);
            player.addBuff(this);
            player.setMove(player.getMove() - minusmove);
        }

        @Override
        public void resetEffect() {
            player.removeBuff(this);
            player.setMove(player.getMove() + minusmove);
        }

        @Override
        public ItemStack getBuffIcon() {
            return null;
        }
    }
}
