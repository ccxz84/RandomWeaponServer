package RWAPI.util;

import RWAPI.Character.PlayerData;
import RWAPI.main;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class NetworkUtil {
    public static void sendToAll(Abstractmessage message,String key){
        if(main.game != null && main.game.player() != null){
            for(PlayerData player : main.game.player().values()){
                byte[] bs;
                ItemStack stack = player.getPlayer().inventory.getStackInSlot(9);
                if(stack != null){
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(bos);
                        oos.writeObject(message);
                        bs = bos.toByteArray();
                        NBTTagCompound nbt = stack.getTagCompound();
                        if(nbt == null){
                            nbt = new NBTTagCompound();
                        }
                        nbt.setByteArray(key,bs);
                        stack.setTagCompound(nbt);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    public static void sendTo(EntityPlayerMP player, Abstractmessage message, String key){
        if(main.game != null && main.game.player() != null){
            byte[] bs;
            ItemStack stack = player.inventory.getStackInSlot(9);
            if(stack != null){
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(message);
                    bs = bos.toByteArray();
                    NBTTagCompound nbt = stack.getTagCompound();
                    if(nbt == null){
                        nbt = new NBTTagCompound();
                    }
                    nbt.setByteArray(key,bs);
                    stack.setTagCompound(nbt);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Abstractmessage implements Serializable {

    }
}
