package RWAPI.items;

import RWAPI.Character.Leesin.entity.EntityUmpa;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.ItemBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
public class Ruby extends ItemBase {

	public Ruby (String name)
	{
		
		super(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerin, EnumHand handIn){
		
		EntityUmpa ls = new EntityUmpa(playerin.world,playerin,1);
		ls.shoot(playerin, playerin.rotationPitch, playerin.rotationYaw, 0.0F, 0.0f, 0);
		ls.setNoGravity(true);
		ls.posY -= 1.3;
		ls.posZ += 0.5;
		playerin.world.spawnEntity(ls);
		
		//playerin.inventory.addItemStackToInventory(new ItemStack(ModItems.ITEMS.get(2)));
		//playerin.openGui(main.instance, GuiHandler.MOD_SHOP_GUI, worldIn, (int)playerin.posX, (int)playerin.posY, (int)playerin.posZ);
		
		return super.onItemRightClick(worldIn, playerin, handIn);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		// TODO Auto-generated method stub
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

}
