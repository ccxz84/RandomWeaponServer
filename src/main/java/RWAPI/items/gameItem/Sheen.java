package RWAPI.items.gameItem;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Lifeline_passive;
import RWAPI.items.gameItem.inherence.Spellblade_passive;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Sheen extends ItemBase {

    public Sheen(String name) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.ITEMS.add(this);
        down_item = new ItemBase[1];
        down_item[0] = ModItems.Sapphirecrystal;

        phase = 2;
        this.name = "광휘의 검";
        this.gold = 1050;
        refund_gold = 735;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initstat() {
        double[] stat = {
                0,	0,	0,	250,	0,	0,	0,	0,	0,	0,	0,	0,	10
        };
        this.stat = stat;
    }

    @Override
    public List<Class<? extends inherence_handler>> get_inherence_handler() {
        List<Class<? extends inherence_handler>> list = new ArrayList<>();
        list.add(Spellblade_passive.class);
        return list;
    }

    @Override
    public ItemBase.inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends ItemBase.inherence_handler> _class, int idx) {
        if(_class.equals(Spellblade_passive.class)){
            try {
                return new Spellblade_passive(data,stack,idx,this.getClass().getDeclaredMethod("getbonusDamage",EntityData.class,EntityData.class));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return null;

    }

    public static DamageSource getbonusDamage(EntityData attacker, EntityData target){
        DamageSource source = DamageSource.causeUnknownPhysics(attacker,target,attacker.getAd());
        return source;
    }
}
