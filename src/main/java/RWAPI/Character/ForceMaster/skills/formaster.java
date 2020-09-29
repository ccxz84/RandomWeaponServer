package RWAPI.Character.ForceMaster.skills;

import RWAPI.Character.CooldownHandler;
import RWAPI.Character.ForceMaster.ForceMaster;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public abstract class formaster implements Skill {

    protected ForceMaster forceMaster;
    private Item skill;


    public formaster(Item skill) {
        this.skill = skill;
        // TODO Auto-generated constructor stub
    }

    abstract public void switchSkill(PlayerData data, int idx, boolean flag);

    class cool extends CooldownHandler {

        boolean flag = true;
        formaster instance;

        public cool(double cool, int id, EntityPlayerMP player, formaster instance) {
            super(cool, id, player);
            this.instance = instance;
        }

        public void unregist(){
            MinecraftForge.EVENT_BUS.unregister(this);
        }

        @Override
        public void skillTimer(TickEvent.ServerTickEvent event) throws Throwable {
            if(skillTimer > cooldown) {
                if(flag == true){
                    data.setCool(this.id, 0);
                }
                instance.skillEnd(player);
                MinecraftForge.EVENT_BUS.unregister(this);
            }
            if(flag == true)
                data.setCool(this.id, ((float)(cooldown-skillTimer)/40));
            skillTimer++;
        }

        public void switchCool(boolean flag){
            this.flag = flag;
        }
    }

    public Item getSkill(){
        return skill;
    }

    public void setSkill(Item skill){
        this.skill = skill;
    }

}
