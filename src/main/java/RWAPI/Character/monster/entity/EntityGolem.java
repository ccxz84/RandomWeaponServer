package RWAPI.Character.monster.entity;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerClass;
import RWAPI.Character.PlayerData;
import RWAPI.Character.ai.PlayerAIHurtByTarget;
import RWAPI.Character.ai.PlayerAIHurtByTargetObject;
import RWAPI.Character.ai.PlayerAIZombieAttack;
import RWAPI.Character.ai.PlayerAIZombieAttackObject;
import RWAPI.game.event.ItemChangeEventHandle;
import RWAPI.game.event.LevelUpEventHandle;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.main;
import RWAPI.util.GameStatus;
import RWAPI.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import javax.annotation.Nullable;

public class EntityGolem extends AbstractObject{
    private static final DataParameter<Integer> attacktimer = EntityDataManager.<Integer>createKey(EntityGolem.class, DataSerializers.VARINT);
    private int attackTimer;
    public static final int armorper = 3;

    public EntityGolem(World world) {
        super(world, new EntityData(null,3700f,40f,53f,190f,300,300,"골렘",1),
                new double[]{180,4,14,14});
        this.getEntityData().setInteger("attackTimer", 0);
        if(main.game.start == GameStatus.START){
            int gametime = (Reference.GAMEITME - main.game.gettimer()) - 300 <= 0 ? 0 :  ((Reference.GAMEITME - main.game.gettimer()) - 300)/60;
            this.getData().setMaxHealth(3700+(180 * gametime));
            this.getData().setCurrentHealth(3700+(180 * gametime));
            this.getData().setAd(125f +(4 * gametime));
            this.getData().setArmor(40f +(14 * gametime));
            this.getData().setMagicresistance(53f +(14 * gametime));
        }
        this.getData().setEntity(this);
    }

    @Override
    protected boolean canDespawn() {
        if (main.game.start != GameStatus.START || (Reference.GAMEITME - main.game.gettimer()) - 300 <= 0){
            return true;
        }
        return false;
    }

    protected void initEntityAI()
    {
        this.tasks.addTask(2, new PlayerAIZombieAttackObject(this, 1.0D, false,8));
        this.tasks.addTask(2, new PlayerAIHurtByTargetObject(this, true));

        this.applyEntityAI();
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataManager().register(attacktimer, 0);
    }

    protected void applyEntityAI()
    {
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

    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if(main.game.start != GameStatus.START)
            setDead();
        if (this.attackTimer > 0)
        {
            --this.attackTimer;
            this.getDataManager().set(attacktimer,attackTimer);
        }

    }

    @Nullable
    protected ResourceLocation getLootTable()
    {
        return LootTableList.ENTITIES_IRON_GOLEM;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4d);
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
                RWAPI.util.DamageSource.DamageSource sourcee = RWAPI.util.DamageSource.DamageSource.causeAttackPhysics(attacker, data,attacker.getAd());
                RWAPI.util.DamageSource.DamageSource.attackDamage(sourcee,true);
                RWAPI.util.DamageSource.DamageSource.EnemyStatHandler.EnemyStatSetter(sourcee);
            }
        }

        return super.attackEntityFrom(source, amount);
    }

    @Override
    public void attackmob(Entity entityIn) {
        this.attackEntityAsMob(entityIn);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        // TODO Auto-generated method stub
        this.getDataManager().set(attacktimer,10);
        this.attackTimer = 10;
        if(entityIn instanceof EntityPlayer) {
            PlayerData target = main.game.getPlayerData(entityIn.getUniqueID());
            RWAPI.util.DamageSource.DamageSource source = RWAPI.util.DamageSource.DamageSource.causeAttackPhysics(data, target,data.getAd());
            RWAPI.util.DamageSource.DamageSource.attackDamage(source,true);
            RWAPI.util.DamageSource.DamageSource.EnemyStatHandler.EnemyStatSetter(source);
        }

        return super.attackEntityAsMob(entityIn);
    }

    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

    }

    @Override
    public int getGametime() {
        return ((Reference.GAMEITME - main.game.gettimer()) - 300 <= 0 ? 0 :  (Reference.GAMEITME - main.game.gettimer()) - 300)/60;
    }

    @Override
    public void setBuff(PlayerData data) {
        data.setArmor(data.getArmor() + (data.getArmor() / 100) * armorper);
        data.setMagicresistance(data.getMagicresistance() + (data.getMagicresistance() / 100) * armorper);
        main.game.getEventHandler().register(new lvEventClass(data));
        main.game.getEventHandler().register(new itemEventClass(data));
    }

    private class lvEventClass extends LevelUpEventHandle {

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
                double armor = _class.matrix.armor[data.getLevel() - 1] - _class.matrix.armor[data.getLevel() - 2];
                double magicresistance = _class.matrix.magicresistance[data.getLevel() - 1] - _class.matrix.magicresistance[data.getLevel() - 2];
                data.setArmor(data.getArmor() + (armor / 100) * armorper);
                data.setMagicresistance(data.getMagicresistance() + (magicresistance / 100) * armorper);
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
                double armor = item.getstat()[4];
                double magicresistance = item.getstat()[5];
                if(((ItemChangeEvent) event).isRemove()){
                    data.setArmor(data.getArmor() - (armor/100) * armorper);
                    data.setMagicresistance(data.getMagicresistance() - (magicresistance/100) * armorper);

                }
                else{
                    data.setArmor(data.getArmor() + (armor/100) * armorper);
                    data.setMagicresistance(data.getMagicresistance() + (magicresistance/100) * armorper);
                }
            }

        }

        @Override
        public EventPriority getPriority() {
            return EventPriority.NORMAL;
        }
    }
}
