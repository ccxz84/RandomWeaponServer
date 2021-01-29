package RWAPI.items.gameItem;

import RWAPI.Character.PlayerData;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Eternity_passive;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Catalystofaeons extends ItemBase {

    final int hphealper = 15, manahealper = 20;

    public Catalystofaeons(String name) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.ITEMS.add(this);
        down_item = new ItemBase[2];
        down_item[0] = ModItems.Sapphirecrystal;
        down_item[1] = ModItems.Rubycrystal;

        phase = 2;
        this.name = "억겁의 카탈리스트";
        this.gold = 1100;
        refund_gold = 770;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initstat() {
        double[] stat = {
                0,	0,	225,	300,	0,	0,	0,	0,	0,	0,	0,	0,	0
        };
        this.stat = stat;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        if (nbt == null) {
            nbt = new NBTTagCompound();
        }

        nbt.setString("inherence","적에게 입은 피해의 "+hphealper+"%가 마나로 전환됩니다. 마나를 소모하면 소모한 마나의 "+manahealper+"%가 체력으로 전환됩니다. 최대 전환량은 15를 넘지 않습니다.");
        return super.initCapabilities(stack,nbt);
    }

    @Override
    public inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends inherence_handler> _class, int idx) {
       if(_class.equals(Eternity_passive.class)){
           return new Eternity_passive(data,stack,hphealper,manahealper);
       }
       return null;
    }

    @Override
    public List<Class<? extends inherence_handler>> get_inherence_handler() {
        List<Class<? extends inherence_handler>> list = new ArrayList<>();
        list.add(Eternity_passive.class);
        return list;
    }
}
