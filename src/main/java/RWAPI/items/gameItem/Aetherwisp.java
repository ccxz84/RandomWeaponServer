package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class Aetherwisp extends ItemBase {

    public Aetherwisp(String name) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.ITEMS.add(this);
        down_item = new ItemBase[1];

        down_item[0] = ModItems.Amplifyingtome;

        phase = 2;
        this.name = "에테르의 환영";
        this.gold = 850;
        refund_gold = 595;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initstat() {
        double[] stat = {
                0,	30,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0
        };
        this.stat = stat;
    }
}
