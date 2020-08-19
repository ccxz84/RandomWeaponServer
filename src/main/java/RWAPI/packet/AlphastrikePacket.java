package RWAPI.packet;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class AlphastrikePacket implements IMessage {

    private List<Double[]> data;

    public AlphastrikePacket(){

    }

    public AlphastrikePacket(List<Double[]> data){
        this.data = data;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {
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

    public static class AlphastrikeHandler implements IMessageHandler<AlphastrikePacket, AlphastrikePacket> {

        @Override
        public AlphastrikePacket onMessage(AlphastrikePacket message, MessageContext ctx) {
            return null;
        }
    }
}
