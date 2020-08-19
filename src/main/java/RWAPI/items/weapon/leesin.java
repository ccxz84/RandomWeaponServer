package RWAPI.items.weapon;

import RWAPI.init.ModWeapons;
import RWAPI.util.ClassList;
import RWAPI.util.Reference;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class leesin extends WeaponBase{

	public leesin(String name) {
		super(ToolMaterial.DIAMOND,name);
		setCreativeTab(CreativeTabs.MATERIALS);
		this.ClassCode = ClassList.Leesin;
		ModWeapons.weapon.add(this);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		tooltip.add("테스트");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt == null){
			nbt = new NBTTagCompound();
			System.out.println("create");
		}
		if(nbt.hasKey("test")){

			nbt.setInteger("test",nbt.getInteger("test")+1);
			System.out.println(nbt.getInteger("test"));
		}
		else{
			nbt.setInteger("test",1);
			System.out.println("create test");
		}
		stack.setTagCompound(nbt);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}
