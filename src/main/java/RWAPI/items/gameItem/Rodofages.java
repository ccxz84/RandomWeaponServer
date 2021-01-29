package RWAPI.items.gameItem;

import RWAPI.Character.PlayerData;
import RWAPI.Character.buff.Buff;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Eternity_passive;
import RWAPI.util.NetworkUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class Rodofages extends ItemBase {

    private static int buff = 600;
    private static double health = 20;
    private static double mana = 10;
    private static double ap = 4;
    final int hphealper = 15, manahealper = 20;
    final int plusstack = 60;

    public Rodofages(String name) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.ITEMS.add(this);
        down_item = new ItemBase[2];
        down_item[0] = ModItems.Catalystofaeons;
        down_item[1] = ModItems.Blastingwand;

        phase = 1;
        this.name = "영겁의 지팡이";
        this.gold = 2600;
        refund_gold = 1820;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initstat() {
        double[] stat = {
                0,	60,	300,	300,	0,	0,	0,	0,	0,	0,	0,	0,	0
        };
        this.stat = stat;
    }

    @Override
    public basic_handler create_basic_handler(PlayerData data, ItemStack stack) {
        return new handler(data,stack);
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        if (nbt == null) {
            nbt = new NBTTagCompound();
        }

        nbt.setString("basic", "1분마다 체력 "+(int)health+", 마나 "+(int)mana+", 주문력 "+(int)ap+"를 획득합니다.");
        nbt.setString("inherence","적에게 입은 피해의 "+hphealper+"%가 마나로 전환됩니다. 마나를 소모하면 소모한 마나의 "+manahealper+"%가 체력으로 전환됩니다. 최대 전환량은 15를 넘지 않습니다.");
        return super.initCapabilities(stack,nbt);
    }

    @Override
    public inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends inherence_handler> _class, int idx) {
        if(_class.equals(Eternity_passive.class)){
            return new Eternity_passive(data,stack,hphealper,manahealper);
        }
        return null;
    }

    @Override
    public List<Class<? extends inherence_handler>> get_inherence_handler() {
        List<Class<? extends inherence_handler>> list = new ArrayList<>();
        list.add(Eternity_passive.class);
        return list;
    }

    protected class handler extends ItemBase.basic_handler{

        timer timer;
        PlayerData data;
        ItemStack stack;
        int statstack = 0;

        public handler(PlayerData data, ItemStack stack) {
            super(data, stack);
            this.data = data;
            this.stack = stack;
            try{
                statstack =(int) NetworkUtil.getStackData(stack,"rodofages");
            }
            catch (ClassCastException | NullPointerException e){
                statstack = 0;
            }

            registertimer();
        }

        private void registertimer() {
            timer = new timer(buff,stack);
        }

        @Override
        public void removeHandler() {
            super.removeHandler();
            if(timer != null){
                timer.breaktimer();
                timer = null;
            }
            statstack = statstack <= 10? statstack:10;
            data.setMaxHealth(data.getMaxHealth() - statstack *health);
            data.setCurrentHealth(data.getCurrentHealth() - statstack *health);
            data.setMaxMana(data.getMaxMana() - statstack *mana);
            data.setCurrentMana(data.getCurrentMana() - statstack *mana);
            data.setAp(data.getAp() - statstack *ap);
        }

        private void addstack(){
            if(statstack <= 10){
                ++statstack;
                data.setMaxHealth(data.getMaxHealth() + health);
                data.setCurrentHealth(data.getCurrentHealth() + health);
                data.setMaxMana(data.getMaxMana() + mana);
                data.setCurrentMana(data.getCurrentMana() + mana);
                data.setAp(data.getAp() + ap);
                if(statstack >= 10){
                    timer.breaktimer();;
                }
            }
        }

        private class timer{
            protected int MaxTime;
            protected int currentTime = 0;
            private ItemStack stack;

            public timer(double timer, ItemStack stack){
                this.MaxTime = (int) (timer * 40);
                this.stack = stack;
                MinecraftForge.EVENT_BUS.register(this);
            }

            @SubscribeEvent
            public void gameTimer(TickEvent.ServerTickEvent event) {
                currentTime++;
                if(currentTime % (plusstack * 40) == 0){
                    addstack();
                    NetworkUtil.setStackData(stack,statstack,"rodofages");
                }
                if(currentTime > MaxTime) {
                    MinecraftForge.EVENT_BUS.unregister(this);
                }
            }

            private void breaktimer(){
                MinecraftForge.EVENT_BUS.unregister(this);
            }
        }
    }

    private class buffClass extends Buff {

        public buffClass(double duration, PlayerData player, boolean debuff, boolean clean, double... data) {
            super(duration, player, debuff, clean, data);
        }

        @Override
        public void setEffect() {
            
        }

        @Override
        public void resetEffect() {

        }

        @Override
        public ItemStack getBuffIcon() {
            return null;
        }
    }


}
