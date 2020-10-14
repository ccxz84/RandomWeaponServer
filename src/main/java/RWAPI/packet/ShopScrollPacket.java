package RWAPI.packet;

import RWAPI.ui.Shopui;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ShopScrollPacket implements IMessage{

    int num,windowid;

    public ShopScrollPacket(){

    }
    @Override
    public void fromBytes(ByteBuf buf) {
        num = buf.readInt();
        windowid = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class ShopScrollHandler implements IMessageHandler<ShopScrollPacket,IMessage> {

        @Override
        public IMessage onMessage(ShopScrollPacket message, MessageContext ctx) {

            if(ctx.getServerHandler().player.openContainer.windowId == message.windowid && ctx.getServerHandler().player.openContainer instanceof Shopui){
                ((Shopui) ctx.getServerHandler().player.openContainer).scrollTo(message.num);
                ((Shopui) ctx.getServerHandler().player.openContainer).sync();
            }

            return null;
        }
    }
}
