package RWAPI.Character.monster.entity;

import RWAPI.Character.EntityData;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;

public class DragonPart extends Entity implements IMob{
    public final EntityDragon parent;
    public final String partName;

    public DragonPart(EntityDragon parent, String partName, float width, float height)
    {
        super(parent.getWorld());
        this.setSize(width, height);
        this.parent = parent;
        this.partName = partName;
        this.setData(parent.getData());
    }

    public boolean canBeCollidedWith()
    {
        return true;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }

    @Override
    protected void entityInit() {

    }

    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        //System.out.println(this.isEntityInvulnerable(source) ? false : this.parent.attackEntityFromPart(this, source, amount));
        return this.isEntityInvulnerable(source) ? false : this.parent.attackEntityFromPart(this, source, amount);
    }

    public boolean isEntityEqual(Entity entityIn)
    {
        return this == entityIn || this.parent == entityIn;
    }

    @Override
    public EntityData getData() {
        return parent.getData();
    }

    @Override
    public void setData(EntityData data) {
        this.parent.setData(data);
    }
}
