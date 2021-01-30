package RWAPI.items.gameItem;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Hunterstalisman_passive;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Bloodrazor extends ItemBase implements ItemBase.jungle{

    private final double expPer = 50;
    private final int plusGold = 20;
    private final int damageper = 4;
    ItemBase type;

    public Bloodrazor(String name, ItemBase type) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.ITEMS.add(this);
        down_item = new ItemBase[2];
        down_item[0] = type;
        down_item[1] = ModItems.Recurvebow;

        phase = 1;
        this.type = type;
        this.name = type.name + " : 피갈퀴손";
        this.gold = 2625;
        refund_gold = 1837;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        if (nbt == null) {
            nbt = new NBTTagCompound();
        }

        nbt.setString("basic","미니언 처치 시, "+String.format("%.0f",expPer)+"%의 경험치를 추가로 제공합니다.");
        nbt.setString("inherence","미니언 처치 시, "+plusGold+"의 골드를 추가로 지급합니다.\n"
        + "적 플레이어를 기본 공격 시, 최대 체력의 4%에 비례하는 물리 피해를 입힙니다.");
        if(type instanceof Stalkersblade){
            nbt.setString("usage", ((Stalkersblade) type).usage);
        }
        else if(type instanceof Skirmisherssaber){
            nbt.setString("usage", ((Skirmisherssaber) type).usage);
        }
        return super.initCapabilities(stack,nbt);
    }

    @Override
    protected void initstat() {
        double[] stat = {
                0,	0,	0,	0,	0,	0,	0,	0.4,	0,	0,	0,	0,	0
        };
        this.stat = stat;
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
    public ItemBase.usage_handler create_usage_handler(PlayerData data, ItemStack stack) {
        if(type instanceof Skirmisherssaber){
            return new Skirmisherssaber.usage_handler(data,stack);
        }
        else if(type instanceof Stalkersblade){
            return new Stalkersblade.usage_handler(data,stack);
        }
        return null;
    }

    @Override
    public ItemBase.basic_handler create_basic_handler(PlayerData data, ItemStack stack) {
        return new handler(data,stack);
    }

    protected class handler extends ItemBase.basic_handler{

        EventClass eventClass;
        PlayerData data;

        public handler(PlayerData data, ItemStack stack) {
            super(data, stack);
            this.data = data;
            registerAttackevent();
        }

        @Override
        public void removeHandler() {
            super.removeHandler();
            if(this.eventClass != null){
                main.game.getEventHandler().unregister(this.eventClass);
                this.eventClass = null;
            }
        }

        private void registerAttackevent() {
            this.eventClass = new EventClass(data);
            main.game.getEventHandler().register(this.eventClass);
        }

        private class EventClass extends PlayerAttackEventHandle{

            PlayerData data;

            public EventClass(PlayerData data) {
                super();
                this.data = data;
            }

            @Override
            public void EventListener(AbstractBaseEvent event) {
                PlayerAttackEvent pevent = (PlayerAttackEvent) event;
                DamageSource source = pevent.getSource();
                EntityData attacker = source.getAttacker();
                EntityData target = source.getTarget();

                if(attacker.equals(data) && source.getAttackType() == DamageSource.AttackType.ATTACK && target instanceof PlayerData){
                    DamageSource damageSource;
                    if(source.isRanged()){
                        damageSource = DamageSource.causeAttackRangedPhysics(attacker,target,(target.getMaxHealth()/100)*damageper);
                    }
                    else{
                        damageSource = DamageSource.causeAttackMeleePhysics(attacker,target,(target.getMaxHealth()/100)*damageper);
                    }
                    DamageSource.attackDamage(damageSource,false);
                    DamageSource.EnemyStatHandler.EnemyStatSetter(source);
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
