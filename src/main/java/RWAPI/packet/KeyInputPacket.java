package RWAPI.packet;

import RWAPI.Character.PlayerData;
import RWAPI.util.EntityStatus;
import org.lwjgl.input.Keyboard;

import RWAPI.main;
import RWAPI.util.GameStatus;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class KeyInputPacket implements IMessage {
	
	private int keynum;

	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		this.keynum = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}
	
	public static class KeyInputHandler implements IMessageHandler<KeyInputPacket, IMessage> {

		@Override
		public IMessage onMessage(KeyInputPacket message, MessageContext ctx) {
			// TODO Auto-generated method stub
			if(message.keynum == Keyboard.KEY_Z || message.keynum == Keyboard.KEY_X || message.keynum == Keyboard.KEY_C|| message.keynum == Keyboard.KEY_V || message.keynum == Keyboard.KEY_B || message.keynum == -1) {
				if(main.game.start == GameStatus.START) {
					PlayerData data = main.game.getPlayerData(ctx.getServerHandler().player.getUniqueID());
					data.inputKey(message.keynum);
					//.get_class().ActiveSkill(message.keynum-Keyboard.KEY_Z+1, main.game.getPlayerData(ctx.getServerHandler().player.getUniqueID()).getPlayer());
				}
			}
			if(message.keynum == Keyboard.KEY_G ){
				if(main.game.start == GameStatus.PRESTART || main.game.start == GameStatus.START) {
					PlayerData data = main.game.getPlayerData(ctx.getServerHandler().player.getUniqueID());
					data.get_class().classInformation(data);
				}
			}
			return null;
		}
		
	}
}
