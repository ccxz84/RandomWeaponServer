package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class Lichbane extends ItemBase {

    public Lichbane(String name) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.ITEMS.add(this);
        down_item = new ItemBase[3];

        down_item[0] = ModItems.Sheen;
        down_item[1] = ModItems.Aetherwisp;
        down_item[2] = ModItems.Blastingwand;

        phase = 1;
        this.name = "리치 베인";
        this.gold = 3200;
        refund_gold = 2240;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initstat() {
        double[] stat = {
                0,	80,	0,	250,	0,	0,	0,	0,	0,	0,	0,	0,	10
        };
        this.stat = stat;
    }
}
