package RWAPI.packet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import RWAPI.main;
import RWAPI.Character.ClientData;
import RWAPI.Character.PlayerData;
import RWAPI.util.GameStatus;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


public class PlayerStatMessage implements IMessage{
	
	private ClientData data;
	
	public PlayerStatMessage() {
		
	}
	
	public PlayerStatMessage(ClientData data) {
		this.data = data;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(data);
			byte[] bs = bos.toByteArray();
			buf.writeBytes(bs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static class HealthStatusHandler implements IMessageHandler<PlayerStatMessage, PlayerStatMessage>{

		@Override
		public PlayerStatMessage onMessage(PlayerStatMessage message, MessageContext ctx) {
			// TODO Auto-generated method stub
			if(main.game.start != GameStatus.START) {
				return new PlayerStatMessage(main.defData);
			}
			else if(main.game.start == GameStatus.START) {
				ClientData cdata = main.game.getPlayerData(ctx.getServerHandler().player.getUniqueID()).getData();
				//ClientData cdata = new ClientData(data,true, main.game.gettimerFlag(),main.game.gettimer());
				return new PlayerStatMessage(cdata);
			}	
			return null;
		}
		
	}
}
