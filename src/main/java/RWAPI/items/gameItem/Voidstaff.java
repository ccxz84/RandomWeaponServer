package RWAPI.items.gameItem;

import RWAPI.Character.PlayerData;
import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class Voidstaff extends ItemBase {

	private final double magicper = 40;

	public Voidstaff(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] =ModItems.Blastingwand;
		down_item[1] =ModItems.Amplifyingtome;
		
		phase = 2;
		this.name = "공허의 지팡이";
		this.gold = 2650;
		refund_gold = 1855;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				0,	75,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,0
		};
		this.stat = stat;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("basic","상대의 마법 저항력의 "+String.format("%.0f",magicper)+"%를 무시합니다.");
		return super.initCapabilities(stack,nbt);
	}

	@Override
	public basic_handler create_basic_handler(PlayerData data, ItemStack stack) {
		return new handler(data,stack);
	}

	protected class handler extends basic_handler{

		PlayerData data;

		private handler(PlayerData data, ItemStack stack){
			super(data,stack);
			this.data = data;
			this.data.setMagicpenetrationper(this.data.getMagicpenetrationper() + magicper);
		}

		@Override
		public void removeHandler() {
			super.removeHandler();
			this.data.setMagicpenetrationper(this.data.getMagicpenetrationper() - magicper);
		}
	}

}
