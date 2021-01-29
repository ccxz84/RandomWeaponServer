package RWAPI.items.buffItem;

import RWAPI.init.ModBuff;
import RWAPI.init.ModSkills;
import net.minecraft.item.Item;

public class BuffBase extends Item {
    public BuffBase(String name) {
        setUnlocalizedName(name);
        setRegistryName("buff/"+name);
        this.maxStackSize = 1;
        ModBuff.buff.add(this);
    }
}
