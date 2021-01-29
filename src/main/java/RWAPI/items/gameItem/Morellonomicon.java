package RWAPI.items.gameItem;

import RWAPI.Character.PlayerData;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Grievouswounds_passive;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Morellonomicon extends ItemBase {

    final int duration = 3;

    public Morellonomicon(String name) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.ITEMS.add(this);
        down_item = new ItemBase[2];
        down_item[0] =ModItems.Oblivionorb;
        down_item[1] =ModItems.Amplifyingtome;

        phase = 1;
        this.name = "모렐로노미콘";
        this.gold = 3000;
        refund_gold = 2100;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initstat() {
        double[] stat = {
                0,	70,	300,	0,	0,	0,	0,	0,	0,	0,	0,	15,	0
        };
        this.stat = stat;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        if (nbt == null) {
            nbt = new NBTTagCompound();
        }

        nbt.setString("inherence","적에게 마법 공격의 공격한 경우, " +duration+"초 동안 치유 감소 효과를 적용합니다.");
        return super.initCapabilities(stack,nbt);
    }

    @Override
    public inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends inherence_handler> _class, int idx) {
        if(_class.equals(Grievouswounds_passive.class)){
            return new Grievouswounds_passive(data,stack,duration, DamageSource.MagicDamageSource.class);
        }
        return super.create_inherence_handler(data, stack, _class, idx);
    }

    @Override
    public List<Class<? extends inherence_handler>> get_inherence_handler() {
        List<Class<? extends inherence_handler>> list = new ArrayList<>();
        list.add(Grievouswounds_passive.class);
        return list;
    }
}
