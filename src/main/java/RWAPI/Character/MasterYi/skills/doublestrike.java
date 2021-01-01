package RWAPI.Character.MasterYi.skills;

import RWAPI.Character.CooldownHandler;
import RWAPI.Character.EntityData;
import RWAPI.Character.MasterYi.MasterYi;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.Character.monster.entity.IMob;
import RWAPI.main;
import RWAPI.util.DamageSource.DamageSource;
import RWAPI.util.EntityStatus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class doublestrike implements Skill {

    private MasterYi _class;
    private MasterYiS[] skills = new MasterYiS[4];
    private doublestrikeHan handler = null;

    protected final double[] skillAdcoe={
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.3,
            0.4,
            0.4,
            0.4,
            0.4,
            0.4,
            0.4,
            0.4,
            0.4,
            0.4
    };

    public doublestrike(MasterYi _class){
        this._class = _class;

    }

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {

    }

    @Override
    public void Skillset(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        for(int i =1;i<5;i++){
            skills[i-1] = (MasterYiS) _class.getSkill(i);
        }
        data.setCool(0,0);
        handler = new doublestrikeHan(0,0, (EntityPlayerMP) player,skills);
    }

    @Override
    public void skillEnd(EntityPlayer player) {
        if(handler != null){
            handler.closeHandler();
        }

    }

    class doublestrikeHan extends CooldownHandler {

        private MasterYiS[] skills;
        private int attack;

        public doublestrikeHan(double cool, int id, EntityPlayerMP player, MasterYiS[] skills) {
            super(cool, id, player);
            this.skills = skills;
            this.attack = 0;
        }

        @Override
        public void skillTimer(TickEvent.ServerTickEvent event) throws Throwable {

        }

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public void attack(LivingAttackEvent event) {
            if(event.getSource().getTrueSource() != null) {
                if(event.getSource().getTrueSource().equals(player) && (event.getEntityLiving() instanceof IMob || event.getEntityLiving() instanceof EntityPlayer)) {
                    EntityData target = null;
                    if(event.getEntityLiving() instanceof IMob) {
                        target = ((IMob)event.getEntityLiving()).getData();
                    }
                    if(event.getEntityLiving() instanceof EntityPlayer){
                        target = main.game.getPlayerData(event.getEntityLiving().getUniqueID());
                    }
                    if(target != null && !(event.getSource() instanceof EntityDamageSourceIndirect)) {
                        attack = (attack+1) % 4;
                        PlayerData attacker = main.game.getPlayerData(event.getSource().getTrueSource().getUniqueID());
                        int lv = attacker.getLevel();
                        ((alphastrike)skills[0]).doublereducecool();
                        if((attack == 3 && target.getCurrentHealth() > 0 && target.getStatus() == EntityStatus.ALIVE)){
                            DamageSource source = DamageSource.causeAttackPhysics(attacker,target,attacker.getAd() * skillAdcoe[lv-1]);
                            attack = (attack+1) % 4;
                            ((alphastrike)skills[0]).doublereducecool();

                            DamageSource.attackDamage(source,true);
                            DamageSource.EnemyStatHandler.EnemyStatSetter(source);
                        }
                        attacker.setCool(0,attack);
                        if(target.getCurrentHealth() <= 0 && target instanceof PlayerData) {
                            for(MasterYiS skill : skills) {
                                skill.reduceCool();
                            }
                        }
                    }
                }
            }
        }

        public void closeHandler(){
            MinecraftForge.EVENT_BUS.unregister(this);
        }
    }

    @Override
    public double[] getskilldamage() {
        return null;
    }

    @Override
    public double[] getskillAdcoe() {
        return this.skillAdcoe;
    }

    @Override
    public double[] getskillApcoe() {
        return null;
    }

    @Override
    public double[] getskillcost() {
        return null;
    }

    @Override
    public double[] getcooldown() {
        return null;
    }
}
