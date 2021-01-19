package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class Phage extends ItemBase {

    public Phage(String name) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.ITEMS.add(this);
        down_item = new ItemBase[2];
        down_item[0] = ModItems.LongSword;
        down_item[1] = ModItems.Rubycrystal;

        phase = 2;
        this.name = "탐식의 망치";
        this.gold = 1250;
        refund_gold = 875;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initstat() {
        double[] stat = {
                15,	0,	200,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0
        };
        this.stat = stat;
    }
}
