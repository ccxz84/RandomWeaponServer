package RWAPI.Character.monster.entity;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerClass;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.ItemChangeEventHandle;
import RWAPI.game.event.LevelUpEventHandle;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.main;
import RWAPI.util.GameStatus;
import RWAPI.util.Reference;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class EntityDragon extends AbstractObject implements IEntityMultiPart {

    public DragonPart[] dragonPartArray;
    public DragonPart dragonPartHead = new DragonPart(this, "head", (float)Math.sqrt(3.0F), (float)Math.sqrt(3.0F));
    public DragonPart dragonPartNeck = new DragonPart(this, "neck", (float)Math.sqrt(6.0F), (float)Math.sqrt(6.0F));
    public DragonPart dragonPartBody = new DragonPart(this, "body", (float)Math.sqrt(8.0F), (float)Math.sqrt(8.0F));
    public DragonPart dragonPartTail1 = new DragonPart(this, "tail", (float)Math.sqrt(4.0F), (float)Math.sqrt(4.0F));
    public DragonPart dragonPartTail2 = new DragonPart(this, "tail", (float)Math.sqrt(4.0F), (float)Math.sqrt(4.0F));
    public DragonPart dragonPartTail3 = new DragonPart(this, "tail", (float)Math.sqrt(4.0F), (float)Math.sqrt(4.0F));
    public DragonPart dragonPartWing1 = new DragonPart(this, "wing", (float)Math.sqrt(4.0F), (float)Math.sqrt(4.0F));
    public DragonPart dragonPartWing2 = new DragonPart(this, "wing", (float)Math.sqrt(4.0F), (float)Math.sqrt(4.0F));
    public boolean slowed;
    public int deathTicks;
    public int attackTicks = -1;
    public static final int hpper = 3;

    public EntityDragon(World worldIn) throws IllegalAccessException, InstantiationException {
        super(worldIn,new EntityData(null,4200f,21f,30f,200f,300,300,"드래곤",1)
        ,new double[]{240,4,13,13});
        this.dragonPartArray = new DragonPart[] {this.dragonPartHead, this.dragonPartNeck, this.dragonPartBody, this.dragonPartTail1, this.dragonPartTail2, this.dragonPartTail3, this.dragonPartWing1, this.dragonPartWing2};
        this.setHealth(this.getMaxHealth());
        this.setSize(16.0F, 8.0F);
        this.noClip = true;
        this.isImmuneToFire = true;
        this.ignoreFrustumCheck = true;
        if(main.game.start == GameStatus.START){
            int gametime = (Reference.GAMEITME - main.game.gettimer()) - 300 <= 0 ? 0 :  ((Reference.GAMEITME - main.game.gettimer()) - 300)/60;
            this.getData().setMaxHealth(4200+(240 * gametime));
            this.getData().setCurrentHealth(4200+(240 * gametime));
            this.getData().setAd(130f +(4 * gametime));
            this.getData().setArmor(21f +(13 * gametime));
            this.getData().setMagicresistance(30f +(13 * gametime));
        }
        this.addTag("드래곤");
        this.setCustomNameTag("드래곤");
        this.getData().setEntity(this);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1024D);
    }

    protected void entityInit()
    {
        super.entityInit();
    }

    protected void initEntityAI()
    {
        super.initEntityAI();
    }

    public void onLivingUpdate()
    {
        if(main.game.start == GameStatus.START){
            if(attackTicks >= 0){
                if(attackTicks % 60 == 0){
                    List<Entity> mini =  this.world.getEntitiesWithinAABB(Entity.class, this.getEntityBoundingBox().grow(6,6,6));
                    for(Entity mi : mini) {
                        EntityData target = null;
                        if(mi instanceof EntityPlayerMP) {
                            target = main.game.getPlayerData(mi.getUniqueID());
                            RWAPI.util.DamageSource.DamageSource dsource = RWAPI.util.DamageSource.DamageSource.causeUnknownMagic(this.getData(),target,this.getData().getAd()/2);
                            RWAPI.util.DamageSource.DamageSource dsource1 = RWAPI.util.DamageSource.DamageSource.causeUnknownPhysics(this.getData(),target,this.getData().getAd()/2);
                            RWAPI.util.DamageSource.DamageSource.attackDamage(dsource,false);
                            RWAPI.util.DamageSource.DamageSource.attackDamage(dsource1,false);
                            RWAPI.util.DamageSource.DamageSource.EnemyStatHandler.EnemyStatSetter(dsource);
                        }
                    }
                }
                ++attackTicks;
                if(attackTicks >= 200){
                    attackTicks = -1;
                }
            }
        }


        if (this.world.isRemote)
        {
            if (this.newPosRotationIncrements > 0)
            {
                double d5 = this.posX + (this.interpTargetX - this.posX) / (double)this.newPosRotationIncrements;
                double d0 = this.posY + (this.interpTargetY - this.posY) / (double)this.newPosRotationIncrements;
                double d1 = this.posZ + (this.interpTargetZ - this.posZ) / (double)this.newPosRotationIncrements;
                double d2 = MathHelper.wrapDegrees(this.interpTargetYaw - (double)this.rotationYaw);
                this.rotationYaw = (float)((double)this.rotationYaw + d2 / (double)this.newPosRotationIncrements);
                this.rotationPitch = (float)((double)this.rotationPitch + (this.interpTargetPitch - (double)this.rotationPitch) / (double)this.newPosRotationIncrements);
                --this.newPosRotationIncrements;
                this.setPosition(d5, d0, d1);
                this.setRotation(this.rotationYaw, this.rotationPitch);
            }

        }

        this.renderYawOffset = this.rotationYaw;
        this.dragonPartHead.width = 1.0F;
        this.dragonPartHead.height = 1.0F;
        this.dragonPartNeck.width = 3.0F;
        this.dragonPartNeck.height = 3.0F;
        this.dragonPartTail1.width = 2.0F;
        this.dragonPartTail1.height = 2.0F;
        this.dragonPartTail2.width = 2.0F;
        this.dragonPartTail2.height = 2.0F;
        this.dragonPartTail3.width = 2.0F;
        this.dragonPartTail3.height = 2.0F;
        this.dragonPartBody.height = 3.0F;
        this.dragonPartBody.width = 5.0F;
        this.dragonPartWing1.height = 2.0F;
        this.dragonPartWing1.width = 4.0F;
        this.dragonPartWing2.height = 3.0F;
        this.dragonPartWing2.width = 4.0F;
        Vec3d[] avec3d = new Vec3d[this.dragonPartArray.length];

        for (int j = 0; j < this.dragonPartArray.length; ++j)
        {
            avec3d[j] = new Vec3d(this.dragonPartArray[j].posX, this.dragonPartArray[j].posY, this.dragonPartArray[j].posZ);
        }

        float f17 = this.rotationYaw * 0.017453292F;
        float f3 = MathHelper.sin(f17);
        float f18 = MathHelper.cos(f17);
        this.dragonPartBody.onUpdate();
        this.dragonPartBody.setLocationAndAngles(this.posX + (double)(f3 * 0.5F), this.posY, this.posZ - (double)(f18 * 0.5F), 0.0F, 0.0F);
        this.dragonPartWing1.onUpdate();
        this.dragonPartWing1.setLocationAndAngles(this.posX + (double)(f18 * 4.5F), this.posY + 2.0D, this.posZ + (double)(f3 * 4.5F), 0.0F, 0.0F);
        this.dragonPartWing2.onUpdate();
        this.dragonPartWing2.setLocationAndAngles(this.posX - (double)(f18 * 4.5F), this.posY + 2.0D, this.posZ - (double)(f3 * 4.5F), 0.0F, 0.0F);
        this.dragonPartHead.onUpdate();
        this.dragonPartNeck.onUpdate();

        for (int k = 0; k < 3; ++k)
        {
            DragonPart multipartentitypart = null;

            if (k == 0)
            {
                multipartentitypart = this.dragonPartTail1;
            }

            if (k == 1)
            {
                multipartentitypart = this.dragonPartTail2;
            }

            if (k == 2)
            {
                multipartentitypart = this.dragonPartTail3;
            }
            multipartentitypart.onUpdate();
        }

        for (int l = 0; l < this.dragonPartArray.length; ++l)
        {
            this.dragonPartArray[l].prevPosX = avec3d[l].x;
            this.dragonPartArray[l].prevPosY = avec3d[l].y;
            this.dragonPartArray[l].prevPosZ = avec3d[l].z;
        }
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    public boolean attackEntityFromPart(DragonPart dragonPart, DamageSource source, float damage)
    {
        return super.attackEntityFrom(source, 0.1f);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(!(source instanceof EntityDamageSourceIndirect)) {
            System.out.println("attack");
            attackTicks = 0;
            if(source.getTrueSource() instanceof EntityPlayer) {
                float f = (this.rand.nextFloat() - 0.5F) * 8.0F;
                float f1 = (this.rand.nextFloat() - 0.5F) * 4.0F;
                float f2 = (this.rand.nextFloat() - 0.5F) * 8.0F;
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + (double)f, this.posY + 2.0D + (double)f1, this.posZ + (double)f2, 0.0D, 0.0D, 0.0D);
                PlayerData attacker = main.game.getPlayerData(source.getTrueSource().getUniqueID());
                RWAPI.util.DamageSource.DamageSource sourcee = RWAPI.util.DamageSource.DamageSource.causeAttackPhysics(attacker, data,attacker.getAd());
                RWAPI.util.DamageSource.DamageSource.attackDamage(sourcee,true);
                RWAPI.util.DamageSource.DamageSource.EnemyStatHandler.EnemyStatSetter(sourcee);
            }
        }
        return super.attackEntityFrom(source, amount);
    }

    public void onKillCommand()
    {
        this.setDead();

    }

    protected void onDeathUpdate()
    {

        ++this.deathTicks;

        if (this.deathTicks >= 180 && this.deathTicks <= 200)
        {
            float f = (this.rand.nextFloat() - 0.5F) * 8.0F;
            float f1 = (this.rand.nextFloat() - 0.5F) * 4.0F;
            float f2 = (this.rand.nextFloat() - 0.5F) * 8.0F;
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + (double)f, this.posY + 2.0D + (double)f1, this.posZ + (double)f2, 0.0D, 0.0D, 0.0D);
        }
        int i = 500;

        if (!this.world.isRemote)
        {
            if (this.deathTicks == 200)
            {
                this.setDead();
            }
        }

        this.move(MoverType.SELF, 0.0D, 0.10000000149011612D, 0.0D);
        this.rotationYaw += 20.0F;
        this.renderYawOffset = this.rotationYaw;
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

    }

    protected void despawnEntity()
    {
    }

    public Entity[] getParts()
    {
        return this.dragonPartArray;
    }

    @Override
    public int getGametime() {
        return (Reference.GAMEITME - main.game.gettimer()) - 300 <= 0 ? 0 :  ((Reference.GAMEITME - main.game.gettimer()) - 300)/60;
    }

    @Override
    public void setBuff(PlayerData data) {
        double health = (data.getMaxHealth()/100) * hpper;
        data.setMaxHealth(data.getMaxHealth() + health);
        data.setCurrentHealth(data.getCurrentHealth() + health);
        main.game.getEventHandler().register(new lvEventClass(data));
        main.game.getEventHandler().register(new itemEventClass(data));
    }

    private class lvEventClass extends LevelUpEventHandle{

        PlayerData data;

        public lvEventClass(PlayerData data) {
            super();
            this.data = data;
        }

        @Override
        public void EventListener(AbstractBaseEvent event) {
            PlayerData data = ((LevelUpEvent)event).getData();
            if(this.data.equals(data)) {
                PlayerClass _class = data.get_class();
                double health = _class.matrix.hp[data.getLevel() - 1] - _class.matrix.hp[data.getLevel() - 2];
                data.setMaxHealth(data.getMaxHealth() + (health / 100) * hpper);
                data.setCurrentHealth(data.getCurrentHealth() + (health / 100) * hpper);
            }
        }

        @Override
        public EventPriority getPriority() {
            return EventPriority.NORMAL;
        }
    }

    private class itemEventClass extends ItemChangeEventHandle {

        PlayerData data;

        public itemEventClass(PlayerData data) {
            super();
            this.data = data;
        }

        @Override
        public void EventListener(AbstractBaseEvent event) {
            PlayerData data = ((ItemChangeEvent)event).getData();
            if(this.data.equals(data)) {
                ItemStack stack = ((ItemChangeEvent) event).getStack();
                ItemBase item = (ItemBase) stack.getItem();
                double health = item.getstat()[2];
                if(((ItemChangeEvent) event).isRemove()){
                    data.setMaxHealth(data.getMaxHealth() - (health/100) * hpper);
                    data.setCurrentHealth(data.getCurrentHealth() - (health/100) * hpper);
                }
                else{
                    data.setMaxHealth(data.getMaxHealth() + (health/100) * hpper);
                    data.setCurrentHealth(data.getCurrentHealth() + (health/100) * hpper);
                }
            }

        }

        @Override
        public EventPriority getPriority() {
            return EventPriority.NORMAL;
        }
    }
}
