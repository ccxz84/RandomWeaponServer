package RWAPI.Character.ForceMaster.entity;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.SkillEntity;
import RWAPI.Character.buff.Buff;
import RWAPI.Character.monster.entity.IMob;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySnowball extends SkillEntity {

    protected double debuffA;
    private double timer;

    public EntitySnowball(World world){
        super(world);
    }

    public EntitySnowball(World worldIn, EntityLivingBase playerin, double skilldamage, double debuff, double timer) {
        super(worldIn, playerin, skilldamage);
        this.debuffA = debuff;
        this.timer = timer;
        this.judg = 0.8d;
    }

    @Override
    public void onUpdate() {
        if(ticksExisted >15) {
            setDead();
        }
        super.onUpdate();
    }

    protected void SkillImpact(RayTraceResult result){
        if (main.game == null)
            setDead();
        if(!(result.entityHit != null && result.entityHit instanceof Entity)){
            setDead();
        }
        else if(!(result.entityHit.getUniqueID().equals(this.thrower.getUniqueID()))) {
            PlayerData attacker = main.game.getPlayerData(this.thrower.getUniqueID());
            EntityData target;
            if (result.entityHit instanceof IMob) {
                IMob mob = (IMob) result.entityHit;
                target = mob.getData();
                DamageSource source = DamageSource.causeSkillMagic(attacker, target, this.skilldamage);
                DamageSource.attackDamage(source, true);
                DamageSource.EnemyStatHandler.EnemyStatSetter(source);
                new debuff(timer,attacker.getPlayer(),target);
            } else if (result.entityHit instanceof EntityPlayer) {
                target = main.game.getPlayerData(result.entityHit.getUniqueID());
                DamageSource source = DamageSource.causeSkillMagic(attacker, target, this.skilldamage);
                DamageSource.attackDamage(source, true);
                DamageSource.EnemyStatHandler.EnemyStatSetter(source);
                new debuff(timer,attacker.getPlayer(),target);
            }
            result.entityHit.attackEntityFrom(net.minecraft.util.DamageSource.causeThrownDamage(this, this.getThrower()), (float)1);

            setDead();
        }
    }

    class debuff extends Buff {

        double minus;
        EntityData target;

        public debuff(double duration, EntityPlayerMP player, EntityData target, double... data) {
            super(duration, player, data);
            this.target = target;
            minus = (target.getMove() * (debuffA/100));
            target.setMove(target.getMove() - minus);
            target.addBuff(this);
        }

        @Override
        public void setEffect() {

        }

        @Override
        public void resetEffect() {
            target.setMove(target.getMove() + minus);
            target.removeBuff(this);
        }
    }
}
