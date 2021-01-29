package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class Glacialshroud extends ItemBase {

    public Glacialshroud(String name) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.ITEMS.add(this);
        down_item = new ItemBase[2];

        down_item[0] = ModItems.Clotharmor;
        down_item[1] = ModItems.Sapphirecrystal;

        phase = 2;
        this.name = "빙하의 장막";
        this.gold = 900;
        refund_gold = 630;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initstat() {
        double[] stat = {
                0,	0,	0,	250,	20,	0,	0,	0,	0,	0,	0,	0,	10
        };
        this.stat = stat;
    }
}
