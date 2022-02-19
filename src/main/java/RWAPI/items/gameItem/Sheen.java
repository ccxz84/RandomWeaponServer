package RWAPI.items.gameItem;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Lifeline_passive;
import RWAPI.items.gameItem.inherence.Spellblade_passive;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Sheen extends ItemBase {

    final int sbduration = 10;
    final double sbcootime = 1.5;
    static final int bonusdamage = 200;

    public Sheen(String name) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.ITEMS.add(this);
        down_item = new ItemBase[1];
        down_item[0] = ModItems.Sapphirecrystal;

        phase = 2;
        this.name = "광휘의 검";
        this.gold = 1050;
        refund_gold = 735;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initstat() {
        double[] stat = {
                0,	0,	0,	250,	0,	0,	0,	0,	0,	0,	0,	0,	10
        };
        this.stat = stat;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        if (nbt == null) {
            nbt = new NBTTagCompound();
        }

        nbt.setString("inherence",
                "스킬 사용 시,"+sbduration+"초 동안 주문검 효과가 발동되며 기본 공격 시, 공격력의 " +bonusdamage+"% 만큼 추가 데미지를 입힙니다.(쿨타임 "+sbcootime+"초");
        return super.initCapabilities(stack,nbt);
    }

    @Override
    public List<Class<? extends inherence_handler>> get_inherence_handler() {
        List<Class<? extends inherence_handler>> list = new ArrayList<>();
        list.add(Spellblade_passive.class);
        return list;
    }

    @Override
    public ItemBase.inherence_handler create_inherence_handler(PlayerData data, ItemStack stack, Class<? extends ItemBase.inherence_handler> _class, int idx) {
        if(_class.equals(Spellblade_passive.class)){
            try {
                return new Spellblade_passive(data,stack,idx,sbduration,sbcootime,this.getClass().getDeclaredMethod("getbonusDamage",EntityData.class,EntityData.class,boolean.class));
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
}
