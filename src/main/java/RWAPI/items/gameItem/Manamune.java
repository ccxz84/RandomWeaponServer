package RWAPI.items.gameItem;

import RWAPI.Character.PlayerData;
import RWAPI.game.event.StatChangeEventHandle;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Awe_passive;
import RWAPI.main;
import RWAPI.util.StatList;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Manamune extends ItemBase {

    private final int plusmana = 5;
    private final int cool = 4;
    private final int maxstack = 3;
    private final int refundper = 15;
    private final int adper = 2;

    public Manamune(String name) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.ITEMS.add(this);
        down_item = new ItemBase[2];
        down_item[0] = ModItems.Tearofthegoddess;
        down_item[1] = ModItems.Pickaxe;

        phase = 1;
        this.name = "마나무네";
        this.gold = 2400;
        refund_gold = 1680;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initstat() {
        double[] stat = {
                35,	0,	0,	250,	0,	0,	0,	0,	0,	0,	0,	0,	0
        };
        this.stat = stat;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        if (nbt == null) {
            nbt = new NBTTagCompound();
        }

        nbt.setString("inherence","기본 공격이나 스킬 사용 시, 충전 기회를 소모하고 최대 마나가 " + plusmana +"씩 증가합니다. 최대 충전 기회는 "+maxstack+"회 이며 " + cool+"초 마다 1회 증가합니다.\n" +
                "스킬 사용 시, 사용 마나의 "+ refundper+"%의 마나를 회복합니다.");
        return super.initCapabilities(stack,nbt);
    }

    @Override
    public List<Class<? extends inherence_handler>> get_inherence_handler() {
        List<Class<? extends ItemBase.inherence_handler>> list = new ArrayList<>();
        list.add(Awe_passive.class);
        list.add(awe.class);
        return list;
    }

    @Override
    public inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends inherence_handler> _class, int idx) {
        if(_class.equals(Awe_passive.class)){
            return new Awe_passive(data,stack,plusmana,cool,maxstack,refundper,idx);
        }
        if(_class.equals(awe.class)){
            return new awe(data,stack,adper);
        }
        return null;
    }

    public static class awe extends ItemBase.inherence_handler{

        private EventClass eventClass;
        private PlayerData data;
        private final int adper;

        public awe(PlayerData data, ItemStack stack, int adper) {
            super(data, stack);
            this.data = data;
            this.adper = adper;
            registerstatchange();
        }

        private void registerstatchange() {
            this.eventClass = new EventClass(this.data);
            this.data.setAd(this.data.getAd() + (this.data.getMaxMana() / 100) * adper);
            main.game.getEventHandler().register(this.eventClass);
        }


        @Override
        public void removeHandler() {
            main.game.getEventHandler().unregister(this.eventClass);
            this.data.setAd(this.data.getAd() - (this.data.getMaxMana() / 100) * adper);

        }

        private class EventClass extends StatChangeEventHandle {
            PlayerData ddata;
            StatList code;

            public EventClass(PlayerData data) {
                code = StatList.MAXMANA;
                this.ddata = data;
            }


            @Override
            public void EventListener(AbstractBaseEvent event) {
                StatChangeEvent sevent = ((StatChangeEvent)event);
                PlayerData data = sevent.getData();
                if(this.ddata.equals(data) && sevent.getCode().equals(StatList.MAXMANA)){
                    double mana = sevent.getRef().getData().doubleValue() - sevent.getPrev().doubleValue() ;
                    this.ddata.setAd(this.ddata.getAd() + (mana / 100) * adper);
                }
            }

            @Override
            public EventPriority getPriority() {
                return EventPriority.NORMAL;
            }


            @Override
            public PlayerData getPlayer() {
                return ddata;
            }

            @Override
            public StatList getCode() {
                return code;
            }
        }
    }
}
