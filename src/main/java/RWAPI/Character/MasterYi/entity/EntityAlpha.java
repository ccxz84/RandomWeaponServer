package RWAPI.Character.MasterYi.entity;

import RWAPI.Character.EntityData;
import RWAPI.Character.MasterYi.skills.alphastrike;
import RWAPI.Character.PlayerData;
import RWAPI.Character.SkillEntity;
import RWAPI.Character.monster.entity.IMob;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import RWAPI.util.NetworkUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class EntityAlpha extends SkillEntity {

    targetData targetData = null;
    List<Entity> mini;
    EntityPlayerMP player;
    int count = 0, idx = 1;

    public EntityAlpha(World world) {
        super(world);
    }

    public EntityAlpha(World worldIn, EntityLivingBase playerin, float skilldamage) {
        super(worldIn, playerin, skilldamage);
        this.posX = playerin.posX;
        this.posY = playerin.posY;
        this.posZ = playerin.posZ;
        targetData = new targetData();
        targetData.resetData();
        this.player = (EntityPlayerMP) playerin;
        targetData.setData(0,playerin);
        growHitbox();
    }

    private void growHitbox() {
        mini =  this.world.getEntitiesWithinAABB(Entity.class, this.getEntityBoundingBox().grow(2.5,0.75,2.5));
        mini.remove(this.thrower);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);

    }

    @Override
    public void onUpdate() {
        if(main.game == null){
            setDead();
            return;
        }

        if(targetData != null){
            NetworkUtil.sendToAll(targetData.getData(),"alphastrike");
        }
        //main.network.sendToAll(new AlphastrikePacket(targetData.getData()));
        if(ticksExisted > 41) {
            setDead();
        }

        if(ticksExisted == (40/alphastrike.target_num) * idx){
            hit_target();
        }
        super.onUpdate();
    }

    private void hit_target() {
        EntityData target = null;
        EntityData attacker = null;
        if(this.thrower instanceof EntityPlayer && mini != null) {
            attacker = main.game.getPlayerData(this.thrower.getUniqueID());
            if(mini.size() == 0)
                return;

            if(mini.get(count) instanceof IMob) {
                target = ((IMob) mini.get(count)).getData();
            }
            else if(mini.get(count) instanceof EntityPlayer){
                target = main.game.getPlayerData(mini.get(count).getUniqueID());
            }
            if(target != null && attacker != null && target.getCurrentHealth() > 0) {
                double damage = this.targetData.returndamage(idx,mini.get(count),this.skilldamage);
                this.targetData.setData(idx++,mini.get(count));
                DamageSource source = DamageSource.causeSkillMeleePhysics(attacker, target, damage);
                DamageSource.attackDamage(source,true);
                DamageSource.EnemyStatHandler.EnemyStatSetter(source);
                mini.get(count).attackEntityFrom(net.minecraft.util.DamageSource.causeThrownDamage(this, this.getThrower()), (float)1);
            }
            if(mini.size()-1 > count)
                count++;
        }
    }

    @Override
    public void setDead() {
        if(main.game ==null){
            super.setDead();
            return;
        }

        if(this.thrower instanceof EntityPlayer) {
            PlayerData attacker = main.game.getPlayerData(this.thrower.getUniqueID());
            attacker.nonWorking = false;
            this.targetData.resetData();
            //NetworkUtil.sendToAll(targetData.getData(), "alphastrike");
           // main.network.sendToAll(new AlphastrikePacket(targetData.getData()));
        }
        super.setDead();

    }

    class targetData{
        Entity[] Entitys = new Entity[alphastrike.target_num+1];
        message data = new message();
        //List<Double[]> data = new ArrayList<>();

        public double returndamage(int idx,Entity data,double damage){
            for(Entity entity : Entitys){
                if(data.equals(entity)){
                    damage = damage * 0.3 <= 15? 15 : damage * 0.3;
                }
            }
            return damage;
        }

        public void setData(int idx, Entity data){
            this.Entitys[idx] = data;
        }

        public void resetData(){
            for(int i =0;i<4;i++) {
                Entitys[i] = null;
                data.data.clear();
            }
        }

        public message getData(){
            data.data.clear();
            for(int i = 0;i<alphastrike.target_num+1;i++){
                if(Entitys[i] == null)
                    break;
                Double[] pos = new Double[3];
                pos[0] = new Double(Entitys[i].posX);
                pos[1] = new Double(Entitys[i].posY+1);
                pos[2] = new Double(Entitys[i].posZ);
                data.data.add(pos);
            }
            return data;
        }
    }

    public static class message extends NetworkUtil.Abstractmessage {
        private static final long serialVersionUID = 2L;
        public List<Double[]> data = new ArrayList<>();
    }
}
