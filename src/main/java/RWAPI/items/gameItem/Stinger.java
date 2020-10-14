package RWAPI.items.gameItem;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.init.ModItems;
import RWAPI.main;
import RWAPI.util.DamageSource.AttackPhysicsDamageSource;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class Stinger extends ItemBase {

	private final double plusdmg = 10;

	public Stinger(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] = ModItems.Dagger;
		down_item[1] = ModItems.Dagger;
		
		phase = 2;
		this.name = "쐐기검";
		this.gold = 950;
		refund_gold = 665;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	0,	0,	0,	0,	0,	0,	0.35,	0,	0,	0,	0
		};
		this.stat = stat;
	}
}
