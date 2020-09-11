package RWAPI.Character.MasterYi.skills;

import RWAPI.Character.*;
import RWAPI.Character.buff.Buff;
import RWAPI.Character.monster.entity.AbstractMob;
import RWAPI.game.event.EntityDeathEventHandle;
import RWAPI.main;
import RWAPI.util.DamageSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class highlander extends MasterYiS {

    private PlayerClass _class;
    private bufftimer buft;
    private cool cool;

    public highlander(PlayerClass _class){
        this._class = _class;
    }

    protected final double[][] skilldamage={
            {30,30,40,40,40,55,55,55,65,65,65,80},
            {0.3,0.3,0.4,0.4,0.4,0.55,0.55,0.55,0.65,0.65,0.65,0.85}
    };
    protected final double[] skillAdcoe={
            0.3,0.3,0.3,0.3,0.3,0.4,0.4,0.4,0.4,0.4,0.4,0.6
    };
    protected final double[] skillApcoe={
            0,0,0,0,0,0,0,0,0,0,0,0
    };
    protected final double[] skillcost={
            100,100,150,150,150,200,200,200,250,250,250,250
    };

    protected final double[] cooldown = {
            85,85,80,80,80,75,75,75,70,70,70,60
    };

    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {
        PlayerData data = main.game.getPlayerData(player.getUniqueID());
        int lv = main.game.getPlayerData(player.getUniqueID()).getLevel();

        if(data.getCool(4) <= 0 && data.getCurrentMana() > skillcost[lv-1] && data.nonWorking == false && cool == null) {
            data.nonWorking = true;

            data.setCurrentMana((float) (data.getCurrentMana() - skillcost[lv-1]));
            cool = new cool(cooldown[lv-1],4, (EntityPlayerMP) player);
            buft = new bufftimer(7, (EntityPlayerMP) player,(float)skilldamage[0][lv-1],(float)skilldamage[1][lv-1]);
            data.nonWorking = false;
        }
    }

    @Override
    public void Skillset(EntityPlayer player) {
        buft = null;
        cool = null;
    }

    @Override
    public void skillEnd(EntityPlayer player) {

    }

    @Override
    void reduceCool() {
        if(cool != null)
            this.cool.reduceCool();
    }

    class cool extends CooldownHandler {

        public cool(double cool, int id, EntityPlayerMP player) {
            super(cool, id, player);
        }

        public void reduceCool(){
            this.skillTimer += cooldown * 0.7;
        }
    }

    class bufftimer extends Buff {

        private eventHandle handle;

        public bufftimer(double duration, EntityPlayerMP player, double... data) {
            super(duration, player, data);
        }

        @Override
        public void resetEffect() {
            // TODO Auto-generated method stub
            PlayerData data = main.game.getPlayerData(player.getUniqueID());
            data.setAttackSpeed(data.getAttackSpeed()-this.data[1]);
            data.setMove(data.getMove() - this.data[0]);
            main.game.getEventHandler().unregister(handle);
            Skillset(player);
        }

        @Override
        public void setEffect() {
            // TODO Auto-generated method stub
            PlayerData data = main.game.getPlayerData(player.getUniqueID());
            data.setAttackSpeed(this.data[1] + data.getAttackSpeed());
            handle = new eventHandle(data);
            main.game.getEventHandler().register(handle);
            data.setMove(this.data[0] + data.getMove());
        }

        private class eventHandle extends EntityDeathEventHandle {

            PlayerData data;

            private eventHandle(PlayerData data){
                this.data = data;

            }

            @Override
            public void EventListener(AbstractBaseEvent event) {
                EntityData attacker = ((EntityDeathEvent)event).getSource().getAttacker();
                EntityData target = ((EntityDeathEvent)event).getSource().getTarget();

                if(attacker.equals(data) && !(target instanceof PlayerData)){
                    System.out.println("highlander run");
                    duration += 5 * 40;
                }
            }

            @Override
            public EventPriority getPriority() {
                return EventPriority.HIGHTEST;
            }
        }

        /*@SubscribeEvent(priority = EventPriority.LOW)
        public void attack(LivingAttackEvent event) {
            if(event.getSource().getTrueSource() != null) {
                if(event.getSource().getTrueSource().equals(player) && (event.getEntityLiving() instanceof AbstractMob || event.getEntityLiving() instanceof EntityPlayer)) {
                    EntityData target = null;
                    if(event.getEntityLiving() instanceof AbstractMob) {
                        target = ((AbstractMob)event.getEntityLiving()).getData();
                    }
                    if(event.getEntityLiving() instanceof EntityPlayer){
                        target = main.game.getPlayerData(event.getEntityLiving().getUniqueID());
                    }
                    if(target != null) {

                        PlayerData attacker = main.game.getPlayerData(event.getSource().getTrueSource().getUniqueID());
                        if(target.getCurrentHealth() <= 0) {
                            this.duration += 5 * 40;
                        }
                    }
                }
            }
        }*/
    }
    @Override
    public double[] getskilldamage() {
        return this.skilldamage[0];
    }
    
    public double[] getskilldamage2(){
        return this.skilldamage[1];
    }

    @Override
    public double[] getskillAdcoe() {
        return this.skillAdcoe;
    }

    @Override
    public double[] getskillApcoe() {
        return this.skillApcoe;
    }

    @Override
    public double[] getskillcost() {
        return this.skillcost;
    }

    @Override
    public double[] getcooldown() {
        return this.cooldown;
    }

}
