package RWAPI.items.gameItem;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.game.event.PlayerAttackEventHandle;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Awe_passive;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Muramana extends ItemBase {

    final int consumemana = 3;
    final int plusdamage = 6;
    final int refundper = 15;
    private final int adper = 2;

    public Muramana(String name) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        this.name = "무라마나";
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initstat() {
        double[] stat = {
                35,	0,	0,	1000,	0,	0,	0,	0,	0,	0,	0,	0,	0
        };
        this.stat = stat;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        if (nbt == null) {
            nbt = new NBTTagCompound();
        }

        nbt.setString("inherence","기본 공격 시, 현재 마나의 " + consumemana + "%를 소비하여 현재 마나의 " + plusdamage + "%에 비례한 추가 물리피해를 입힙니다. 이 효과는 마나가 20% 이상 있을 시 활성화 됩니다.\n"+
                "스킬 사용 시, 사용 마나의 "+ refundper+"%의 마나를 회복합니다.");
        return super.initCapabilities(stack,nbt);
    }

    @Override
    public List<Class<? extends inherence_handler>> get_inherence_handler() {
        List<Class<? extends ItemBase.inherence_handler>> list = new ArrayList<>();
        list.add(shock_passive.class);
        list.add(Manamune.awe.class);
        list.add(Awe_passive.class);
        return list;
    }

    @Override
    public inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends inherence_handler> _class, int idx) {
        if(_class.equals(Awe_passive.class)){
            return new Awe_passive(data,stack,0,0,0,refundper,idx);
        }
        if(_class.equals(shock_passive.class)){
            return new shock_passive(data,stack);
        }
        if(_class.equals(Manamune.awe.class)){
            return new Manamune.awe(data,stack,adper);
        }
        return null;
    }
    public class shock_passive extends ItemBase.inherence_handler {

        private attackEventClass eventClass;
        private PlayerData data;

        public shock_passive(PlayerData data, ItemStack stack) {
            super(data, stack);
            this.data = data;
            registerattackevent();
        }

        private void registerattackevent() {
            eventClass = new attackEventClass(data);
            main.game.getEventHandler().register(eventClass);
        }

        @Override
        public void removeHandler() {
            super.removeHandler();
            if(eventClass != null){
                main.game.getEventHandler().unregister(eventClass);
            }
        }

        private class attackEventClass extends PlayerAttackEventHandle {
            PlayerData data;

            public attackEventClass(PlayerData data) {
                super();
                this.data = data;
            }

            @Override
            public void EventListener(AbstractBaseEvent event) {
                EntityData attacker = ((PlayerAttackEvent)event).getSource().getAttacker();
                EntityData target = ((PlayerAttackEvent)event).getSource().getTarget();
                if(attacker.equals(data) && ((PlayerAttackEvent)event).getSource() instanceof DamageSource.AttackDamage && target instanceof PlayerData){
                    double currentmana = attacker.getCurrentMana();
                    double maxmana = attacker.getMaxMana();
                    if(currentmana >= (maxmana * 0.2)){
                        attacker.setCurrentMana(currentmana - ((currentmana / 100)*consumemana));
                        DamageSource dsource = DamageSource.causeUnknownPhysics(attacker,target,(currentmana/100)*plusdamage);
                        DamageSource.attackDamage(dsource,false);
                        DamageSource.EnemyStatHandler.EnemyStatSetter(dsource);
                    }
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
