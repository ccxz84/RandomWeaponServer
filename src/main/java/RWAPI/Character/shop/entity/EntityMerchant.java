package RWAPI.Character.shop.entity;

import RWAPI.main;
import RWAPI.Character.Leesin.entity.EntityUmpa;
import RWAPI.init.handler.GuiHandler;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityMerchant extends EntityLiving{

	public EntityMerchant(World worldIn) {
		super(worldIn);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		// TODO Auto-generated method stub
		player.openGui(main.instance, GuiHandler.MOD_SHOP_GUI, world, (int)player.posX, (int)player.posY, (int)player.posZ);
		return super.processInteract(player, hand);
		
	}

	
}
