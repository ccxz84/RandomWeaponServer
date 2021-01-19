package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class Rodofages extends ItemBase {

    public Rodofages(String name) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.ITEMS.add(this);
        down_item = new ItemBase[2];
        down_item[0] = ModItems.Catalystofaeons;
        down_item[1] = ModItems.Blastingwand;

        phase = 1;
        this.name = "영겁의 지팡이";
        this.gold = 2600;
        refund_gold = 1820;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initstat() {
        double[] stat = {
                0,	60,	300,	300,	0,	0,	0,	0,	0,	0,	0,	0,	0
        };
        this.stat = stat;
    }
}
