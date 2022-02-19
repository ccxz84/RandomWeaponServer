package RWAPI.items.gameItem;

import RWAPI.Character.EntityData;
import RWAPI.Character.PlayerData;
import RWAPI.Character.buff.Buff;
import RWAPI.Character.monster.entity.IMob;
import RWAPI.init.ModItems;
import RWAPI.items.gameItem.inherence.Rage_passive;
import RWAPI.items.gameItem.inherence.Spellblade_passive;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import java.util.ArrayList;
import java.util.List;

public class Iceborngauntlet extends ItemBase {

    final int sbduration = 10;
    final double sbcootime = 1.5;
    final int duration = 2;
    static final int bonusdamage = 100;
    final static int minusmoveper = 30;

    public Iceborngauntlet(String name) {
        super(name);
        setCreativeTab(CreativeTabs.MATERIALS);
        ModItems.ITEMS.add(this);
        down_item = new ItemBase[2];

        down_item[0] = ModItems.Sheen;
        down_item[1] = ModItems.Glacialshroud;

        phase = 1;
        this.name = "얼어붙은 건틀릿";
        this.gold = 2700;
        refund_gold = 1890;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void initstat() {
        double[] stat = {
                0,	0,	0,	500,	65,	0,	0,	0,	0,	0,	0,	0,	20
        };
        this.stat = stat;
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        if (nbt == null) {
            nbt = new NBTTagCompound();
        }

        nbt.setString("inherence",
                "스킬 사용 시,"+sbduration+"초 동안 주문검 효과가 발동되며 기본 공격 시, 공격 대상 주변에 얼음 영역을 생성하여 "+duration+"초 동안 적을 "+minusmoveper+"% 느리게 만들고, 공격력의 " +bonusdamage+"% 만큼 추가 데미지를 입힙니다." +
                        "얼음 영역을 방어력에 비례하여 넓어집니다.(쿨타임 "+sbcootime+"초)");
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
                return new Spellblade_passive(data,stack,idx,sbduration,sbcootime,this.getClass().getDeclaredMethod("getbonusDamage", EntityData.class,EntityData.class,boolean.class));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static DamageSource getbonusDamage(EntityData attacker, EntityData target, boolean ranged){

        List<Entity> mini =  target.getEntity().world.getEntitiesWithinAABB(Entity.class, target.getEntity().getEntityBoundingBox().grow(2+(0.1*((int)attacker.getArmor()/30)),0.75,2+(0.1*((int)attacker.getArmor()/30))));
        for(Entity mi : mini) {
            if (mi instanceof EntityPlayerMP && !(mi.equals(attacker.getEntity()))) {
                PlayerData pdata = main.game.getPlayerData(mi.getUniqueID());
                new buff(2, pdata,true, true);
                DamageSource source;
                if(ranged){
                    source = DamageSource.causeUnknownRangedPhysics(attacker,pdata,(attacker.getAd()/100)*bonusdamage);
                }
                else{
                    source = DamageSource.causeUnknownMeleePhysics(attacker,pdata,(attacker.getAd()/100)*bonusdamage);
                }
                DamageSource.attackDamage(source,false);
                DamageSource.EnemyStatHandler.EnemyStatSetter(source);
            }
            if(mi instanceof IMob){
                EntityData data = ((IMob) mi).getData();
                DamageSource source;
                if(ranged){
                    source = DamageSource.causeUnknownRangedPhysics(attacker,data,(attacker.getAd()/100)*bonusdamage);
                }
                else{
                    source = DamageSource.causeUnknownMeleePhysics(attacker,data,(attacker.getAd()/100)*bonusdamage);
                }
                DamageSource.attackDamage(source,false);
                DamageSource.EnemyStatHandler.EnemyStatSetter(source);
            }
        }

        if(ranged){
            return DamageSource.causeUnknownRangedPhysics(attacker,target,(attacker.getAd()/100)*bonusdamage);
        }
        else{
            return DamageSource.causeUnknownMeleePhysics(attacker,target,(attacker.getAd()/100)*bonusdamage);
        }

    }

    public static class buff extends Buff{

        double minusmove;
        ItemStack icon = new ItemStack(ModItems.Iceborngauntlet);

        public buff(double duration, PlayerData player, boolean debuff, boolean clean, double... data) {
            super(duration, player, debuff, clean, data);
        }

        @Override
        public void setEffect() {
            minusmove = (this.player.getMove()/100)*minusmoveper;
            this.player.setMove(this.player.getMove() - minusmove);
            this.player.addBuff(this);
        }

        @Override
        public void resetEffect() {
            this.player.setMove(this.player.getMove() + minusmove);
            this.player.removeBuff(this);
        }

        @Override
        public ItemStack getBuffIcon() {
            return icon;
        }
    }
}
