package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class Faeriecharm extends ItemBase {

    public Faeriecharm(String name) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.ITEMS.add(this);
        down_item = new ItemBase[0];

        phase = 3;
        this.name = "요정의 부적";
        this.gold = 125;
        refund_gold = 87;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initstat() {
        double[] stat = {
                0,	0,	0,	0,	0,	0,	0,	0,	0,	1,	0,	0,	0
        };
        this.stat = stat;
    }
}
