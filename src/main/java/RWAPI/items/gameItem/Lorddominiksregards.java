package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class Lorddominiksregards extends ItemBase {
    public Lorddominiksregards(String name) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.ITEMS.add(this);
        down_item = new ItemBase[2];
        down_item[0] =ModItems.Lastwhisper;
        down_item[1] =ModItems.Pickaxe;

        phase = 1;
        this.name = "도미닉 경의 인사";
        this.gold = 2800;
        refund_gold = 1960;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initstat() {
        double[] stat = {
                45,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0
        };
        this.stat = stat;
    }
}
