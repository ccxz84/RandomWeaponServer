package RWAPI.items.gameItem;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.StatChangeEventHandle;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Rage_passive;
import RWAPI.items.gameItem.inherence.Spellblade_passive;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import RWAPI.util.StatList;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Trinityforce extends ItemBase {

    final int rageduration = 2;
    final double ragemove = 7;
    final int sbduration = 10;
    final double sbcootime = 1.5;
    static final int bonusdamage = 200;
    final int moveper = 2;

    public Trinityforce(String name) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.ITEMS.add(this);
        down_item = new ItemBase[3];
        down_item[0] = ModItems.Phage;
        down_item[1] = ModItems.Sheen;
        down_item[2] = ModItems.Stinger;

        phase = 1;
        this.name = "삼위일체";
        this.gold = 3733;
        refund_gold = 2613;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initstat() {
        double[] stat = {
                25,	0,	250,	250,	0,	0,	0,	0.4,	0,	0,	0,	0,	20
        };
        this.stat = stat;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        if (nbt == null) {
            nbt = new NBTTagCompound();
        }

        nbt.setString("basic","이동속도가 "+moveper+"% 증가합니다.");
        nbt.setString("inherence","기본 공격 시, " +rageduration+ "초 동안 " +ragemove+ "의 (원거리 " +(ragemove/2)+")가 증가합니다.\n"+
                "스킬 사용 시,"+sbduration+"초 동안 주문검 효과가 발동되며 기본 공격 시, 공격력의 " +bonusdamage+"% 만큼 추가 데미지를 입힙니다.(쿨타임 "+sbcootime+"초)");
        return super.initCapabilities(stack,nbt);
    }

    @Override
    public List<Class<? extends inherence_handler>> get_inherence_handler() {
        List<Class<? extends inherence_handler>> list = new ArrayList<>();
        list.add(Rage_passive.class);
        list.add(Spellblade_passive.class);
        return list;
    }

    @Override
    public ItemBase.inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends ItemBase.inherence_handler> _class, int idx) {
        if(_class.equals(Rage_passive.class)){
            return new Rage_passive(data,stack,rageduration,ragemove);
        }
        if(_class.equals(Spellblade_passive.class)){
            try {
                return new Spellblade_passive(data,stack,idx,sbduration,sbcootime,this.getClass().getDeclaredMethod("getbonusDamage", EntityData.class,EntityData.class,boolean.class));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static DamageSource getbonusDamage(EntityData attacker, EntityData target, boolean ranged){
        if(ranged){
            return DamageSource.causeUnknownRangedPhysics(attacker,target,(attacker.getAd()/100)*bonusdamage);
        }
        else{
            return DamageSource.causeUnknownMeleePhysics(attacker,target,(attacker.getAd()/100)*bonusdamage);
        }

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

        private class EventClass extends StatChangeEventHandle{

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
