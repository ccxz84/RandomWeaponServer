package RWAPI.packet;

import java.nio.charset.Charset;

import RWAPI.main;
import RWAPI.init.handler.GuiHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class InventoryOpenPacket implements IMessage{

	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		
		
	}
	
	public static class InventoryOpenHandler implements IMessageHandler<InventoryOpenPacket, IMessage> {

		@Override
		public IMessage onMessage(InventoryOpenPacket message, MessageContext ctx) {
			// TODO Auto-generated method stub
			EntityPlayerMP player = ctx.getServerHandler().player;
			player.openGui(main.instance, GuiHandler.MOD_INVENTORY_GUI, player.world, (int)player.posX, (int)player.posY, (int)player.posZ);
			
			return null;
		}
		
	}

}
