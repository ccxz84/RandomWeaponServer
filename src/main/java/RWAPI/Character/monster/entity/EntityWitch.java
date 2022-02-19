package RWAPI.Character.monster.entity;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerClass;
import RWAPI.Character.PlayerData;
import RWAPI.Character.ai.PlayerAIHurtByTarget;
import RWAPI.game.event.ItemChangeEventHandle;
import RWAPI.game.event.StatChangeEventHandle;
import RWAPI.init.ModPotion;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.main;
import RWAPI.util.GameStatus;
import RWAPI.util.Reference;
import RWAPI.util.StatList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityWitch extends AbstractObject implements IRangedAttackMob {

    private int explosionStrength = 0;
    private static final DataParameter<Boolean> ATTACKING = EntityDataManager.<Boolean>createKey(EntityWitch.class, DataSerializers.BOOLEAN);
    public static final int attper = 5;

    public EntityWitch(World world) {
        super(world, new EntityData(null,3600f,18f,25f,250f,300,70,"마녀",1),
                new double[]{170,5,12,5}, new double[]{3600,145,18,25});
        if(main.game.start == GameStatus.START){
            int gametime = ((Reference.GAMEITME - main.game.gettimer())/60) - 5 <= 0 ? 0 : ((Reference.GAMEITME - main.game.gettimer())/60) - 5;
            this.getData().setMaxHealth(3600+(170 * gametime));
            this.getData().setCurrentHealth(3600+(170 * gametime));
            this.getData().setAd(145f +(5 * gametime));
            this.getData().setArmor(18f +(12 * gametime));
            this.getData().setMagicresistance(25f +(5 * gametime));
        }
        this.setCustomNameTag("마녀");
        this.getData().setEntity(this);
    }

    protected void initEntityAI()
    {
        //this.tasks.addTask(2, new PlayerAIZombieAttackObject(this, 1.0D, false, 30));
        this.tasks.addTask(2, new EntityAIAttackRanged(this, 1.5D, 60, 10.0F));
        //this.tasks.addTask(1, new EntityAISwimming(this));
        //this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
        //this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        //this.tasks.addTask(6, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {

        this.targetTasks.addTask(2, new PlayerAIHurtByTarget(this, true));
    }

    @Override
    public boolean getCanSpawnHere() {
        //System.out.println("posx : " + posX + " posZ  " + posZ);
        if((posX > -295 && posX < -208) && (posZ < 93 && posZ > -186)) {
            return true;
        }

        if((posX > -222 && posX < -207) && (posZ < 93 && posZ > -172)) {
            return true;
        }

        if((posX > -214 && posX < -59) && (posZ < 103 && posZ > -335)) {
            return true;
        }

        if((posX > -58 && posX < 1) && (posZ < 103 && posZ > -210)) {
            return true;
        }

        if((posX > 2 && posX < 62) && (posZ < 103 && posZ > -182)) {
            return true;
        }

        if((posX > 63 && posX < 83) && (posZ < -99 && posZ > -208)) {
            return true;
        }

        if((posX > 63 && posX < 83) && (posZ < -99 && posZ > -208)) {
            return true;
        }

        if((posX > 84 && posX < 91) && (posZ < 103 && posZ > -98)) {
            return true;
        }

        if((posX > 84 && posX < 100 ) && (posZ < -135  && posZ > -208)) {
            return true;
        }
        if((posX > 101 && posX < 143) && (posZ < -135  && posZ > -177)) {
            return true;
        }
        if((posX > 144	 && posX < 176 ) && (posZ < -135 && posZ > -157)) {
            return true;
        }

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if(main.game.start != GameStatus.START)
            setDead();
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3d);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.1d);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1024d);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1d);
    }

    @Override
    protected boolean isValidLightLevel() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void onUpdate() {
        // TODO Auto-generated method stub
        if(data.getCurrentHealth() <= 0) {
            this.setDead();
        }
        super.onUpdate();
    }
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        // TODO Auto-generated method stub
        if(!(source instanceof EntityDamageSourceIndirect)) {
            if(source.getTrueSource() instanceof EntityPlayer) {

                PlayerData attacker = main.game.getPlayerData(source.getTrueSource().getUniqueID());
                RWAPI.util.DamageSource.DamageSource sourcee = RWAPI.util.DamageSource.DamageSource.causeAttackMeleePhysics(attacker, data,attacker.getAd());
                RWAPI.util.DamageSource.DamageSource.attackDamage(sourcee,true);
                RWAPI.util.DamageSource.DamageSource.EnemyStatHandler.EnemyStatSetter(sourcee);
            }
        }

        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void attackmob(Entity entityIn) {
        if(entityIn instanceof EntityPlayer) {
            //EntityPotion potion = new EntityPotion(this.world,this,PotionUtils.addPotionToItemStack(new ItemStack(new ItemPotion()),ModPotion.Witch_Dark_Type));
            EntityPotion potion = new EntityPotion(this.world,this,new ItemStack(new ItemPotion()));
            potion.shoot(this,this.rotationPitch,this.rotationYaw,0,3f,0);
            this.world.spawnEntity(potion);
            //effect ef = new effect(this.world, this, this.getData().getAd());
            //ef.shoot(this, this.rotationPitch, this.rotationYaw, 0f, 0.4f, 0);
            //this.world.spawnEntity(ef);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        // TODO Auto-generated method stub


        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public int getGametime() {
        return ((Reference.GAMEITME - main.game.gettimer())/60) - 5 <= 0 ? 0 : ((Reference.GAMEITME - main.game.gettimer())/60) - 5;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        double d0 = target.posY + (double)target.getEyeHeight() - 2.100000023841858D;
        double d1 = target.posX + target.motionX - this.posX;
        double d2 = d0 - this.posY;
        double d3 = target.posZ + target.motionZ - this.posZ;
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3);
        PotionType[] potionlist = new PotionType[]{ModPotion.Witch_Dark_Type,ModPotion.Witch_Fire_Type,ModPotion.Witch_Ice_Type};
        PotionType potiontype = potionlist[this.rand.nextInt(potionlist.length-1)+1];

        /*if (f >= 8.0F && !target.isPotionActive(MobEffects.SLOWNESS))
        {
            potiontype = PotionTypes.SLOWNESS;
        }
        else if (target.getHealth() >= 8.0F && !target.isPotionActive(MobEffects.POISON))
        {
            potiontype = PotionTypes.POISON;
        }
        else if (f <= 3.0F && !target.isPotionActive(MobEffects.WEAKNESS) && this.rand.nextFloat() < 0.25F)
        {
            potiontype = PotionTypes.WEAKNESS;
        }*/

        EntityPotion entitypotion = new EntityPotion(this.world, this, PotionUtils.addPotionToItemStack(new ItemStack(Items.LINGERING_POTION), potiontype));
        entitypotion.rotationPitch -= -20.0F;
        entitypotion.shoot(d1, d2 + (double)(f * 0.2F), d3, 0.75F, 8.0F);
        this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_THROW, this.getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
        this.world.spawnEntity(entitypotion);
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {

    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        compound.setString("boss", "witch");
        super.writeEntityToNBT(compound);
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

    }

    @Override
    public void setBuff(PlayerData data) {
        data.setAd(data.getAd() + (data.getAd()/100) * attper);
        data.setAp(data.getAp() + (data.getAp()/100) * attper);
        main.game.getEventHandler().register(new lvEventClass(data,StatList.AD));
        main.game.getEventHandler().register(new lvEventClass(data,StatList.AP));
    }

    private class lvEventClass extends StatChangeEventHandle {

        PlayerData data;
        StatList code;

        public lvEventClass(PlayerData data,StatList code) {
            this.data = data;
            this.code = code;
        }

        @Override
        public void EventListener(AbstractBaseEvent event) {
            StatChangeEvent sevent = ((StatChangeEvent)event);
            PlayerData data = sevent.getData();
            if(this.data.equals(data)) {
                if(this.data.equals(data) && (sevent.getCode().equals(StatList.AD) || sevent.getCode().equals(StatList.AP))){
                    double ad = sevent.getRef().getData().doubleValue() - sevent.getPrev().doubleValue() ;
                    sevent.getRef().setData(sevent.getRef().getData().doubleValue() + (ad / 100) * attper);
                }
            }
        }

        @Override
        public EventPriority getPriority() {
            return EventPriority.NORMAL;
        }

        @Override
        public PlayerData getPlayer() {
            return data;
        }

        @Override
        public StatList getCode() {
            return code;
        }
    }
}
