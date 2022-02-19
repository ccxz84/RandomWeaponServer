package RWAPI.Character.MasterYi.skills;

import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.game.event.BaseEvent;
import RWAPI.game.event.UseSkillEventHandle;
import RWAPI.main;
import net.minecraft.entity.player.EntityPlayer;

public abstract class MasterYiS implements Skill {
    @Override
    public void skillpreExecute(EntityPlayer player) {

    }

    @Override
    public void skillExecute(EntityPlayer player) {

    }

    @Override
    public void Skillset(EntityPlayer player) {

    }

    @Override
    public void skillEnd(EntityPlayer player) {

    }

    abstract void reduceCool();

    @Override
    public void raiseevent(PlayerData data,double mana) {
        UseSkillEventHandle.UseSkillEvent event = new UseSkillEventHandle.UseSkillEvent(data,mana);
        for(BaseEvent.EventPriority priority : BaseEvent.EventPriority.values()){
            main.game.getEventHandler().RunEvent(event,priority);
        }
    }

}
