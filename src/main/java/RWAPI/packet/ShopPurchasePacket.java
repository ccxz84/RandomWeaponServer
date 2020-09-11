package RWAPI.packet;

import RWAPI.Character.PlayerData;
import RWAPI.main;
import RWAPI.ui.Shopui;
import RWAPI.util.GameStatus;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ShopPurchasePacket implements IMessage{

    public ShopPurchasePacket(){

    }
    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {
    }

    public static class ShopPurchaseHandler implements IMessageHandler<ShopPurchasePacket,IMessage> {

        @Override
        public IMessage onMessage(ShopPurchasePacket message, MessageContext ctx) {
            System.out.println("test");
            if(ctx.getServerHandler().player.openContainer instanceof Shopui &&((main.game.start == GameStatus.START)|| (main.game.start == GameStatus.PRESTART))){
                PlayerData data = main.game.getPlayerData(ctx.getServerHandler().player.getUniqueID());
                data.purchaseItem(((Shopui) ctx.getServerHandler().player.openContainer).currentstack);
            }

            return null;
        }
    }
}
