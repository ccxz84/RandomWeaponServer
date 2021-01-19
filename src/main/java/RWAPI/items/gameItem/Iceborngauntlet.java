package RWAPI.items.gameItem;

import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;

public class Iceborngauntlet extends ItemBase {

    public Iceborngauntlet(String name) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.ITEMS.add(this);
        down_item = new ItemBase[2];

        down_item[0] = ModItems.Sheen;
        down_item[1] = ModItems.Glacialshroud;

        phase = 1;
        this.name = "얼어붙은 건틀릿";
        this.gold = 2700;
        refund_gold = 1890;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initstat() {
        double[] stat = {
                0,	0,	0,	500,	65,	0,	0,	0,	0,	0,	0,	0,	20
        };
        this.stat = stat;
    }
}
