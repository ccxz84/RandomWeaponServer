package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class Trinityforce extends ItemBase {

    public Trinityforce(String name) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.ITEMS.add(this);
        down_item = new ItemBase[3];
        down_item[0] = ModItems.Phage;
        down_item[1] = ModItems.Sheen;
        down_item[2] = ModItems.Stinger;

        phase = 1;
        this.name = "삼위일체";
        this.gold = 3733;
        refund_gold = 2613;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initstat() {
        double[] stat = {
                25,	0,	250,	250,	0,	0,	10,	0.4,	0,	0,	0,	0,	20
        };
        this.stat = stat;
    }
}
