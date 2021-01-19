package RWAPI.items.gameItem;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.buff.Buff;
import RWAPI.Character.monster.entity.IMob;
import RWAPI.game.event.EntityDeathEventHandle;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Hunterstalisman_passive;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import RWAPI.util.NetworkUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class Skirmisherssaber extends ItemBase implements ItemBase.jungle{

    private final double expPer = 50;
    private final int plusGold = 20;
    private final double damageper = 10;
    private final int cooltime = 20;

    public Skirmisherssaber(String name) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.ITEMS.add(this);
        down_item = new ItemBase[2];
        down_item[0] =ModItems.Huntersmachete;
        down_item[1] =ModItems.Hunterstalisman;

        phase = 2;
        this.name = "척후병의 사브르";
        this.gold = 1000;
        refund_gold = 700;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initstat() {
        double[] stat = {
                0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,10
        };
        this.stat = stat;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        if (nbt == null) {
            nbt = new NBTTagCompound();
        }

        nbt.setString("basic","미니언 처치 시, "+String.format("%.0f",expPer)+"%의 경험치를 추가로 제공합니다.");
        nbt.setString("inherence","미니언 처치 시, "+plusGold+"의 골드를 추가로 지급합니다.");
        return super.initCapabilities(stack,nbt);
    }

    @Override
    public List<Class<? extends inherence_handler>> get_inherence_handler() {
        List<Class<? extends inherence_handler>> list = new ArrayList<>();
        list.add(Hunterstalisman_passive.class);
        return list;
    }

    @Override
    public ItemBase.inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends ItemBase.inherence_handler> _class, int idx) {
        if(_class.equals(Hunterstalisman_passive.class)){
            return new Hunterstalisman_passive(data,stack,plusGold);
        }

        return null;

    }

    @Override
    public ItemBase.basic_handler create_basic_handler(PlayerData data, ItemStack stack) {
        return new handler(data,stack);
    }

    @Override
    public ItemBase.usage_handler create_usage_handler(PlayerData data, ItemStack stack) {
        return new usage_handler(data,stack);
    }

    public static class usage_handler extends ItemBase.usage_handler{

        PlayerData data;
        ItemStack stack;
        cool cool;
        private final int cooltime = 20;
        private final double damageper = 10;

        public usage_handler(PlayerData data, ItemStack stack) {
            super(data,stack);
            this.data = data;
            this.stack = stack;
            boolean flag = NetworkUtil.getStackData(stack,"buff") != null ? (boolean)NetworkUtil.getStackData(stack,"buff"):false;
            if(flag){
                cool = new cool(cooltime,stack);
                NetworkUtil.setStackData(stack,false,"buff");
            }
            else if(NetworkUtil.getCool(stack) != 0){
                cool = new cool(NetworkUtil.getCool(stack),stack);
                NetworkUtil.setStackData(stack,false,"buff");
            }
        }

        public void removeHandler(){
            if(cool != null){
                MinecraftForge.EVENT_BUS.unregister(cool);
                cool = null;
            }
        }

        private void coolreset(){
            cool = null;
        }

        public void ItemUse(ItemStack stack){
            super.ItemUse(stack);
            if(cool != null){
                return;
            }

            List<Entity> mini =  data.getPlayer().world.getEntitiesWithinAABB(Entity.class, data.getPlayer().getEntityBoundingBox().grow(2.5,0.75,2.5));
            for(Entity mi : mini) {
                EntityData target = null;
                if(mi instanceof EntityPlayerMP && !(mi.equals(data.getPlayer()))) {
                    target = main.game.getPlayerData(mi.getUniqueID());
                }
                else if(mi instanceof IMob){
                    target = ((IMob) mi).getData();
                }
                if(target != null){
                    DamageSource source = DamageSource.causeUnknownMagic(data, target, (data.getAp()/100)*damageper);
                    DamageSource.attackDamage(source,true);
                    source = DamageSource.causeUnknownPhysics(data, target, (data.getAd()/100)*damageper);
                    DamageSource.attackDamage(source,true);
                    DamageSource.EnemyStatHandler.EnemyStatSetter(source);
                    mi.attackEntityFrom(net.minecraft.util.DamageSource.causeThrownDamage(data.getPlayer(), mi), (float)1);
                }
            }
            NetworkUtil.setStackData(stack,false,"buff");
            cool = new cool(cooltime,stack);
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

    protected class handler extends ItemBase.basic_handler{

        EventClass eventClass;
        PlayerData data;

        private handler(PlayerData data, ItemStack stack){
            super(data,stack);
            this.data = data;
            registerDeathEvent();
        }

        private void registerDeathEvent() {
            this.eventClass = new EventClass(data);
            main.game.getEventHandler().register(this.eventClass);
        }

        @Override
        public void removeHandler() {
            main.game.getEventHandler().unregister(this.eventClass);
        }

        private class EventClass extends EntityDeathEventHandle {
            PlayerData data;

            public EventClass(PlayerData data) {
                super();
                this.data = data;
            }

            @Override
            public void EventListener(AbstractBaseEvent event) {
                DamageSource source = ((EntityDeathEvent)event).getSource();
                double damage = source.getDamage();

                EntityData data = source.getAttacker();

                if(data.equals(this.data)){
                    ((PlayerData)data).setExp((source.getTarget().getDeathExp()/100) * expPer + ((PlayerData)data).getExp());
                }
            }

            @Override
            public EventPriority getPriority() {
                return EventPriority.NORMAL;
            }

            @Override
            public code getEventCode() {
                return code.attacker;
            }

            @Override
            public EntityData getAttacker() {
                return data;
            }

            @Override
            public EntityData getTarget() {
                return null;
            }
        }
    }
}
