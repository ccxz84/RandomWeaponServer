package RWAPI.util;

import RWAPI.Character.PlayerData;
import RWAPI.main;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.io.*;

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
            NBTTagList list = new NBTTagList();
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

    public static void sendTo(EntityPlayerMP player, Serializable message, String key){
        if(main.game != null && main.game.player() != null){
            byte[] bs;
            NBTTagList list = new NBTTagList();
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

    public static void setCool(ItemStack stack, double cool){
        if(main.game != null && main.game.player() != null){
            if(stack != null) {
                NBTTagCompound nbt = stack.getTagCompound();
                if(nbt == null){
                    nbt = new NBTTagCompound();
                }
                nbt.setDouble("cool",cool);
                stack.setTagCompound(nbt);
            }
        }
    }

    public static double getCool(ItemStack stack){
        if(main.game != null && main.game.player() != null){
            if(stack != null) {
                NBTTagCompound nbt = stack.getTagCompound();
                if(nbt == null){
                    return 0;
                }
                return nbt.getDouble("cool");
            }
        }
        return 0;
    }

    public static Object getStackData(ItemStack stack, String key){
        if(main.game != null && main.game.player() != null){
            Object ob = null;
            if(stack.getTagCompound() != null) {
                NBTTagCompound nbt = stack.getTagCompound();
                byte[] bs = nbt.getByteArray(key);
                if (bs.length != 0) {
                    ByteArrayInputStream bis = new ByteArrayInputStream(bs);
                    try {
                        ObjectInputStream ois = new ObjectInputStream(bis);
                        ob = ois.readObject();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            return ob;
        }
        return null;
    }

    public static void setStackData(ItemStack stack,Serializable data, String key){
        if(main.game != null && main.game.player() != null){
            if(stack != null){
                byte[] bs;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(data);
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
