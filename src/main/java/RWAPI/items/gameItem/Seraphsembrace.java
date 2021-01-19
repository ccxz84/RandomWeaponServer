package RWAPI.items.gameItem;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.buff.Buff;
import RWAPI.items.gameItem.inherence.Awe_passive;
import RWAPI.items.gameItem.inherence.Lifeline_passive;
import RWAPI.util.NetworkUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Seraphsembrace extends ItemBase {

    final double baseshield = 150;
    final int shieldper = 15;
    private final int refundper = 15;
    private final int apper = 1;
    final int duration = 2;
    private final int cooltime = 120;

    public Seraphsembrace(String name) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        this.name = "대천사의 포옹";
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initstat() {
        double[] stat = {
                0,	50,	0,	1400,	0,	0,	0,	0,	0,	0,	0,	0,	20
        };
        this.stat = stat;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        if (nbt == null) {
            nbt = new NBTTagCompound();
        }

        nbt.setString("inherence","사용 시, 현재 마나의 " + shieldper +"%를 소모하고, " + baseshield +"+ 현재 마나의 " + shieldper+"% 만큼의 방어막을 "+duration+"초 동안 얻습니다." +
                "스킬 사용 시, 사용 마나의 "+ refundper+"%의 마나를 회복합니다.");
        return super.initCapabilities(stack,nbt);
    }

    @Override
    public List<Class<? extends inherence_handler>> get_inherence_handler() {
        List<Class<? extends ItemBase.inherence_handler>> list = new ArrayList<>();
        list.add(Awe_passive.class);
        list.add(Archangelsstaff.awe.class);
        return list;
    }

    @Override
    public inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends inherence_handler> _class, int idx) {
        if(_class.equals(Awe_passive.class)){
            return new Awe_passive(data,stack,0,0,0,refundper,idx);
        }
        if(_class.equals(Archangelsstaff.awe.class)){
            return new Archangelsstaff.awe(data,stack,apper);
        }
        return null;
    }

    @Override
    public usage_handler create_usage_handler(PlayerData data, ItemStack stack) {
        return new handler(data,stack);
    }

    protected class handler extends ItemBase.usage_handler{

        PlayerData data;
        buff buff;
        cool cool;
        boolean bbuff = false;

        public handler(PlayerData data, ItemStack stack) {
            super(data, stack);
            this.stack = stack;
            this.data = data;
        }

        @Override
        public void removeHandler(){
            if(buff != null){
                MinecraftForge.EVENT_BUS.unregister(buff);
                buff.resetEffect();
                buff = null;
            }
            if(cool != null){
                MinecraftForge.EVENT_BUS.unregister(cool);
                cool = null;
            }
            if(bbuff){
                NetworkUtil.setCool(stack,0);
            }
        }

        @Override
        public void ItemUse(ItemStack stack) {
            super.ItemUse(stack);
            if(cool != null){
                return;
            }
            if(buff != null){
                return;
            }

            buff = new buff(duration,data,(baseshield + ((data.getAp() / 100) * shieldper)));
            cool = new cool(cooltime, stack);
        }

        private void coolreset(){
            cool = null;
        }

        private void bufreset(){
            buff = null;
        }

        private class buff extends Buff {

            EntityData.shield shield_instance;

            public buff(double duration, PlayerData player, double... data) {
                super(duration, player, false, false, data);
            }

            @Override
            public void setEffect() {
                shield_instance = new EntityData.shield(data[0]);
                this.player.addShield(shield_instance);
                this.player.addBuff(this);
            }

            @Override
            public void resetEffect() {
                this.player.removeShield(shield_instance);
                player.removeBuff(this);
                bufreset();
            }

            @Override
            public ItemStack getBuffIcon() {
                return null;
            }
        }

        private class cool extends coolHandler{

            public cool(double timer,ItemStack stack) {
                super(timer,stack);
            }

            @Override
            public void TimerEnd() {
                NetworkUtil.setCool(stack,0);
                coolreset();
            }
        }
    }
}
