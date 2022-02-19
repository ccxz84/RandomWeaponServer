package RWAPI.items.gameItem;

import RWAPI.Character.PlayerData;
import RWAPI.game.event.StatChangeEventHandle;
import RWAPI.init.ModItems;
import RWAPI.main;
import RWAPI.util.StatList;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class Aetherwisp extends ItemBase {

    final int moveper = 2;

    public Aetherwisp(String name) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.ITEMS.add(this);
        down_item = new ItemBase[1];

        down_item[0] = ModItems.Amplifyingtome;

        phase = 2;
        this.name = "에테르의 환영";
        this.gold = 850;
        refund_gold = 595;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initstat() {
        double[] stat = {
                0,	30,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0
        };
        this.stat = stat;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        if (nbt == null) {
            nbt = new NBTTagCompound();
        }

        nbt.setString("basic","이동속도가 "+moveper+"% 증가합니다.");
        return super.initCapabilities(stack,nbt);
    }

    @Override
    public ItemBase.basic_handler create_basic_handler(PlayerData data, ItemStack stack) {
        return new handler(data,stack);
    }

    protected class handler extends ItemBase.basic_handler{

        EventClass eventClass;
        PlayerData data;
        double move = 0;

        public handler(PlayerData data, ItemStack stack) {
            super(data, stack);
            this.data = data;
            move = (data.getMove()/100)*moveper;
            data.setMove(data.getMove() + move);
            registerstatchange();
        }

        private void registerstatchange() {
            eventClass = new EventClass(data);
            main.game.getEventHandler().register(eventClass);
        }

        @Override
        public void removeHandler() {
            super.removeHandler();
            main.game.getEventHandler().unregister(eventClass);
            data.setMove(data.getMove() - move);
        }

        private class EventClass extends StatChangeEventHandle {

            PlayerData data;

            private EventClass(PlayerData data){
                this.data = data;
            }

            @Override
            public PlayerData getPlayer() {
                return data;
            }

            @Override
            public StatList getCode() {
                return StatList.MOVE;
            }

            @Override
            public void EventListener(AbstractBaseEvent event) {
                StatChangeEvent sevent = (StatChangeEvent) event;

                if(sevent.getData().equals(data) && sevent.getCode() == StatList.MOVE){
                    double change = sevent.getRef().getData().doubleValue() - sevent.getPrev().doubleValue();
                    move += (change/100) * moveper;
                    sevent.getRef().setData(sevent.getRef().getData().doubleValue() + ((change/100) * moveper));
                }
            }

            @Override
            public EventPriority getPriority() {
                return EventPriority.NORMAL;
            }
        }
    }
}
