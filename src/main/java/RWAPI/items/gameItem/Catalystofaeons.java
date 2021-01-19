package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class Catalystofaeons extends ItemBase {

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
}
