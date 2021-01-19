package RWAPI.items.gameItem;

import RWAPI.Character.PlayerData;
import RWAPI.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class Lastwhisper extends ItemBase {

	private final double amorper = 20;

	public Lastwhisper(String name) {
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
		down_item = new ItemBase[2];
		down_item[0] =ModItems.LongSword;
		down_item[1] =ModItems.Pickaxe;
		
		phase = 2;
		this.name = "최후의 속삭임";
		this.gold = 1500;
		refund_gold = 1050;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initstat() {
		double[] stat = {
				35,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,0
		};
		this.stat = stat;
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		if (nbt == null) {
			nbt = new NBTTagCompound();
		}

		nbt.setString("basic","상대의 방어력의 "+String.format("%.0f",amorper)+"%를 무시합니다.");
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
			this.data.setArmorpenetrationper(this.data.getArmorpenetrationper() + amorper);
		}

		@Override
		public void removeHandler() {
			super.removeHandler();
			this.data.setArmorpenetrationper(this.data.getArmorpenetrationper() - amorper);
		}
	}

}
