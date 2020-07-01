package RWAPI.packet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import RWAPI.main;
import RWAPI.Character.ClientData;
import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.util.GameStatus;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class EnemyStatPacket implements IMessage{
	
	private ClientData data;
	
	public EnemyStatPacket() {
		
	}
	
	public EnemyStatPacket(ClientData data) {
		
		this.data = data;
	}
	
	public EnemyStatPacket(EntityData data, boolean EnemyStat) {
		this.data = new ClientData(data, EnemyStat);
	}
	
	public EnemyStatPacket(boolean EnemyStat) {
		this.data = new ClientData(EnemyStat);
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
	public static class EnemyStatHandler implements IMessageHandler<EnemyStatPacket, EnemyStatPacket>{

		@Override
		public EnemyStatPacket onMessage(EnemyStatPacket message, MessageContext ctx) {
			// TODO Auto-generated method stub
			if(main.game.start != GameStatus.START) {
				return new EnemyStatPacket(false);
			}
			else if(main.game.start == GameStatus.START) {
				PlayerData data = main.game.getPlayerData(ctx.getServerHandler().player.getUniqueID());
				
				if(data.getEnemyHandler() != null) {
					return new EnemyStatPacket(data.getEnemyHandler().getEnemyData(data),true);
					
				}
				return new EnemyStatPacket(false);
			}
			return null;
		}
		
	}

}
