package RWAPI.items.gameItem;

import RWAPI.Character.PlayerData;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Awe_passive;
import RWAPI.items.gameItem.inherence.Rabadonsdeathcap_passive;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Tearofthegoddess extends ItemBase implements ItemBase.tear{

    private final int plusmana = 4;
    private final int cool = 4;
    private final int maxstack = 3;
    private final int refundper = 10;

    public Tearofthegoddess(String name) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.ITEMS.add(this);
        down_item = new ItemBase[2];
        down_item[0] =ModItems.Sapphirecrystal;
        down_item[1] =ModItems.Faeriecharm;

        phase = 2;
        this.name = "여신의 눈물";
        this.gold = 850;
        refund_gold = 595;
    }

    @Override
    protected void initstat() {
        double[] stat = {
                0,	0,	0,	250,	0,	0,	0,	0,	0,	0,	0,	0,	0
        };
        this.stat = stat;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        if (nbt == null) {
            nbt = new NBTTagCompound();
        }

        nbt.setString("inherence","스킬 사용 시, 충전 기회를 소모하고 최대 마나가 " + plusmana +"씩 증가합니다. 최대 충전 기회는 "+maxstack+"회 이며 " + cool+"초 마다 1회 증가합니다.\n" +
                "스킬 사용 시, 사용 마나의 "+ refundper+"%의 마나를 회복합니다.");
        return super.initCapabilities(stack,nbt);
    }

    @Override
    public List<Class<? extends inherence_handler>> get_inherence_handler() {
        List<Class<? extends ItemBase.inherence_handler>> list = new ArrayList<>();
        list.add(Awe_passive.class);
        return list;
    }

    @Override
    public inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends inherence_handler> _class, int i) {
        if(_class.equals(Awe_passive.class)){
            return new Awe_passive(data,stack,plusmana,cool,maxstack,refundper,i);
        }
        return null;
    }
}
