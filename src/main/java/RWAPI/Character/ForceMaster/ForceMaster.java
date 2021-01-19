package RWAPI.Character.ForceMaster;

import RWAPI.Character.ForceMaster.skills.*;
import RWAPI.Character.Kassadin.Kassadin;
import RWAPI.Character.Leesin.skills.flurry;
import RWAPI.Character.Leesin.skills.sonicwave;
import RWAPI.Character.PlayerClass;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.game.event.BaseEvent;
import RWAPI.game.event.ItemChangeEventHandle;
import RWAPI.game.event.StatChangeEventHandle;
import RWAPI.init.ModSkills;
import RWAPI.init.ModWeapons;
import RWAPI.items.gameItem.ItemBase;
import RWAPI.items.skillItem.SkillBase;
import RWAPI.main;
import RWAPI.util.ClassList;
import RWAPI.util.StatList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class ForceMaster extends PlayerClass {

    private formaster[] allskills = new formaster[10];
    private Item[] allskillset = new Item[10];
    private EventClass maxmanahandler,curmanahandler,managenhandler;
    private static final int manaapper = 1;
    private static final int managenapper = 100;

    public ForceMaster(){
        default_health = 680;
        default_mana = 10;

        default_ad = 64;
        default_ap = 0;
        default_move = 98;

        default_regenHealth = 0.4f;
        default_regenMana = 0.5f;

        attackSpeed = 0.2775f;

        class_code = ClassList.ForceMaster; //미완성

        ClassName = "기공사";

        default_armor = 31;
        default_magicresistance = 30;

        super.weapon = ModWeapons.formaster;

        allskills[0] = new blazingpalm(this, ModSkills.skill.get(ModSkills.blazingpalm.SkillNumber));
        allskills[1] = new firestorm(this, ModSkills.skill.get(ModSkills.firestorm.SkillNumber));
        allskills[2] = new blazingbeam(this, ModSkills.skill.get(ModSkills.blazingbeam.SkillNumber));
        allskills[3] = new heatwave(this, ModSkills.skill.get(ModSkills.heatwave.SkillNumber));
        allskills[4] = new inferno(this, ModSkills.skill.get(ModSkills.inferno.SkillNumber));
        allskills[5] = new frostpalm(this, ModSkills.skill.get(ModSkills.frostpalm.SkillNumber));
        allskills[6] = new icecoil(this, ModSkills.skill.get(ModSkills.icecoil.SkillNumber));
        allskills[7] = new snowball(this, ModSkills.skill.get(ModSkills.snowball.SkillNumber));
        allskills[8] = new icerain(this, ModSkills.skill.get(ModSkills.icerain.SkillNumber));
        allskills[9] = new frostarmor(this, ModSkills.skill.get(ModSkills.frostarmor.SkillNumber));


        allskillset[0] = ModSkills.skill.get(ModSkills.blazingpalm.SkillNumber);
        allskillset[1] = ModSkills.skill.get(ModSkills.firestorm.SkillNumber);
        allskillset[2] = ModSkills.skill.get(ModSkills.blazingbeam.SkillNumber);
        allskillset[3] = ModSkills.skill.get(ModSkills.heatwave.SkillNumber);
        allskillset[4] = ModSkills.skill.get(ModSkills.inferno.SkillNumber);

        allskillset[5] = ModSkills.skill.get(ModSkills.frostpalm.SkillNumber);
        allskillset[6] = ModSkills.skill.get(ModSkills.icecoil.SkillNumber);
        allskillset[7] = ModSkills.skill.get(ModSkills.snowball.SkillNumber);
        allskillset[8] = ModSkills.skill.get(ModSkills.icerain.SkillNumber);
        allskillset[9] = ModSkills.skill.get(ModSkills.frostarmor.SkillNumber);


        matrix = new formasterMatrix();
        skillSet[0] = ModSkills.skill.get(ModSkills.blazingpalm.SkillNumber); //미완성
        skillSet[1] = ModSkills.skill.get(ModSkills.firestorm.SkillNumber);
        skillSet[2] = ModSkills.skill.get(ModSkills.blazingbeam.SkillNumber);
        skillSet[3] = ModSkills.skill.get(ModSkills.heatwave.SkillNumber);
        skillSet[4] = ModSkills.skill.get(ModSkills.inferno.SkillNumber);
    }

    @Override
    public PlayerClass copyClass(){
        return new ForceMaster();
    }

    public void skill0(EntityPlayer player) {
    }

    @Override
    public void skill1(EntityPlayer player) {
        skills[1].skillpreExecute(player);
        skills[1].skillExecute(player);
        //skills[1].skillEnd(player);
    }

    @Override
    public void skill2(EntityPlayer player) {
        skills[2].skillpreExecute(player);
        skills[2].skillExecute(player);
    }

    @Override
    public void skill3(EntityPlayer player) {
        skills[3].skillpreExecute(player);
        skills[3].skillExecute(player);
    }

    @Override
    public void skill4(EntityPlayer player) {
        skills[4].skillpreExecute(player);
        skills[4].skillExecute(player);
    }

    @Override
    public void Levelup(PlayerData data) {
        super.Levelup(data);
    }

    @Override
    public void clickEvent(PlayerData data) {
        allskills[0].skillExecute(data.getPlayer());
    }

    @Override
    public void initSkill(PlayerData data) {
        for(int i = 0 ; i < allskills.length; i++){
            allskills[i].Skillset(data.getPlayer());
        }
        skillSwitching(data,0);

    }

    @Override
    public void preinitSkill(PlayerData data){
        this.maxmanahandler = new EventClass(data,BaseEvent.EventPriority.HIGHTEST,StatList.MAXMANA);
        this.curmanahandler = new EventClass(data,BaseEvent.EventPriority.HIGHTEST,StatList.CURRENTMANA);
        this.managenhandler = new EventClass(data,BaseEvent.EventPriority.HIGHTEST,StatList.REGENMANA);
        main.game.getEventHandler().register(this.maxmanahandler);
        main.game.getEventHandler().register(this.curmanahandler);
        main.game.getEventHandler().register(this.managenhandler);
    }

    public void skillSwitching(PlayerData data,int idx){
        int x = idx == 1 ? 0 : 1;
        for(int i = 0; i < 5; i++){
            skills[i] = allskills[idx*5+i];
            allskills[idx*5+i].switchSkill(data,i,true);
            allskills[x*5+i].switchSkill(data,i,false);
            data.setSkill(i, (SkillBase) allskills[idx*5+i].getSkill());

        }
    }

    public formaster getAllskill(int idx){
        return allskills[idx];
    }

    class formasterMatrix extends StatMatrix{

        public formasterMatrix() {
            super.ad = this.ad;
            super.ap = this.ap;
            super.hp = this.hp;
            super.hregen = this.hregen;
            super.mana = this.mana;
            super.armor = this.armor;
            super.magicresistance = this.magicresistance;
            super.move = this.move;
            super.mregen = this.mregen;
            super.attackspeed = this.attackspeed;
        }
        final double[] hp = {
                680,
                710,
                740,
                780,
                820,
                860,
                900,
                940,
                990,
                1040,
                1090,
                1140,
                1190,
                1240,
                1290,
                1340,
                1390,
                1440
        };

        final double[] mana = {
                10,
                10,
                10,
                10,
                10,
                10,
                10,
                10,
                10,
                10,
                10,
                10,
                10,
                10,
                10,
                10,
                10,
                10
        };
        final double[] hregen = {
                0.4,
                0.48,
                0.56,
                0.64,
                0.72,
                0.82,
                0.92,
                1.02,
                1.14,
                1.26,
                1.38,
                1.5,
                1.62,
                1.74,
                1.86,
                1.98,
                2.1,
                2.22,

        };
        final double[] mregen = {
                0.5,
                0.5,
                0.5,
                0.5,
                0.5,
                0.5,
                0.5,
                0.5,
                0.5,
                0.5,
                0.5,
                0.5,
                0.5,
                0.5,
                0.5,
                0.5,
                0.5,
                0.5

        };

        final double[] armor = {
                31,
                33.7,
                36.4,
                39.1,
                41.8,
                44.5,
                47.2,
                49.9,
                52.6,
                55.3,
                58,
                60.7,
                63.4,
                66.1,
                68.8,
                71.5,
                74.2,
                76.9

        };

        final double[] magicresistance = {
                30,
                30.5,
                31,
                31.5,
                32,
                32.5,
                33,
                33.5,
                34,
                34.5,
                35,
                35.5,
                36,
                36.5,
                37,
                37.5,
                38,
                38.5

        };

        final double[] ad = {
                64,
                65.5,
                67,
                68.5,
                70,
                71.5,
                73,
                74.5,
                76.5,
                78.5,
                80.5,
                83,
                85.5,
                88,
                90.5,
                93,
                95.5,
                98
        };
        final double[] ap = {
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                0
        };
        final double[] move = {
                98,
                98,
                98,
                98,
                98,
                98,
                98,
                98,
                98,
                98,
                98,
                98,
                98,
                98,
                98,
                98,
                98,
                98
        };
        final double[] attackspeed = {
                0.2775,
                0.28638,
                0.29526,
                0.30414,
                0.31302,
                0.3219,
                0.33078,
                0.33966,
                0.34854,
                0.35742,
                0.3663,
                0.3805857,
                0.3948714,
                0.4091571,
                0.4234428,
                0.4377285,
                0.4520142,
                0.4662999
        };
    }


    @Override
    public void EndGame(EntityPlayerMP player){
        for(Skill skill : skills){
            if(skill != null){
                skill.skillEnd(player);
            }
        }
        if(maxmanahandler != null){
            main.game.getEventHandler().unregister(this.maxmanahandler);
        }
        if(curmanahandler != null){
            main.game.getEventHandler().unregister(this.curmanahandler);
        }
        if(managenhandler != null){
            main.game.getEventHandler().unregister(this.managenhandler);
        }
    }

    @Override
    public void classInformation(PlayerData data) {
        int lv = data.getLevel();

        heatwave heat = (heatwave) allskills[3];
        snowball ball = (snowball) allskills[7];
        icerain rain = (icerain) allskills[8];
        data.getPlayer().sendMessage(new TextComponentString("기공사 : "));
        data.getPlayer().sendMessage(new TextComponentString("10의 마나를 1 주문력, 1의 마나 재생을 1 주문력으로 치환합니다."));
        data.getPlayer().sendMessage(new TextComponentString("화기 : "));
        data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"열화장"+
                TextFormatting.RESET +" : 사용 시, 화기 스킬을 사용가능한 상태로 변경되며, "
                + String.format("%.1f",allskills[0].getskilldamage()[lv-1]) +"(+"+TextFormatting.RED +String.format("%.1f",(allskills[0].getskillAdcoe()[lv-1]*data.getAd()))+TextFormatting.RESET + ") (+"+
        TextFormatting.BLUE +String.format("%.1f",(allskills[0].getskillApcoe()[lv-1] * data.getAp()))+
                TextFormatting.RESET +") 의 데미지를 입힙니다. 쿨타임은 공격속도에 비례합니다. 1의 내력을 회복합니다."));

        data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"화염폭발"+
                TextFormatting.RESET +" : 기공사가 화염을 폭발시켜 주변의 적에게 " +
                String.format("%.1f",allskills[1].getskilldamage()[lv-1]) +"(+"+TextFormatting.RED +String.format("%.1f",(allskills[1].getskillAdcoe()[lv-1]*data.getAd()))+TextFormatting.RESET + ") (+"+
                TextFormatting.BLUE +String.format("%.1f",(allskills[1].getskillApcoe()[lv-1] * data.getAp()))+
                TextFormatting.RESET + ")의 데미지를 입힙니다. 쿨타임 " +
                TextFormatting.GOLD +allskills[1].getcooldown()[lv-1]+
                TextFormatting.RESET+"초  소모값 : " + allskills[1].getskillcost()[lv-1] ));

        data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"폭열염포"+
                TextFormatting.RESET +" : 기공사가 화염으로 된 에너지를 전방으로 발사하여 " +
                String.format("%.1f",allskills[2].getskilldamage()[lv-1]) +"(+"+TextFormatting.RED +String.format("%.1f",(allskills[2].getskillAdcoe()[lv-1]*data.getAd()))+TextFormatting.RESET + ") (+"+
                TextFormatting.BLUE +String.format("%.1f",(allskills[2].getskillApcoe()[lv-1] * data.getAp()))+
                TextFormatting.RESET +")의 데미지를 입힙니다. 쿨타임 " +
                TextFormatting.GOLD +allskills[2].getcooldown()[lv-1]+
                TextFormatting.RESET+"초  소모값 : " + allskills[2].getskillcost()[lv-1] ));

        data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"폭염"+
                TextFormatting.RESET +" : 기공사가 현재의 위치에 폭염을 설치합니다. 또한 2초동안 " + TextFormatting.DARK_PURPLE + heat.getskilldamage()[lv-1] +
                TextFormatting.RESET +"%의 이동속도가 증가합니다."+" 쿨타임 : " +
                TextFormatting.GOLD +heat.getcooldown()[lv-1]+
                TextFormatting.RESET+"초 1의 내력을 회복합니다."));

        data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"폭염 발산"+
                TextFormatting.RESET +" : 기공사가 설치한 폭염을 폭발시켜 폭염에 있는 적에게"+
                String.format("%.1f",heat.getskilldamage1()[lv-1]) +"(+"+TextFormatting.RED +String.format("%.1f",(heat.getskillAdcoe()[lv-1]*data.getAd()))+TextFormatting.RESET + ") (+"+
                TextFormatting.BLUE +String.format("%.1f",(heat.getskillApcoe()[lv-1] * data.getAp()))+
                TextFormatting.RESET +")의 데미지를 입힙니다.  소모값 : " + heat.getskillcost1()[lv-1]));

        data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"화련장"+
                TextFormatting.RESET +" : 기공사가 화기를 집중하여 에너지를 전방으로 발사하고 " +
                String.format("%.1f",allskills[4].getskilldamage()[lv-1]) +"(+"+TextFormatting.RED +String.format("%.1f",(allskills[4].getskillAdcoe()[lv-1]*data.getAd()))+TextFormatting.RESET + ") (+"+
                TextFormatting.BLUE +String.format("%.1f",(allskills[4].getskillApcoe()[lv-1] * data.getAp()))+
                TextFormatting.RESET +")의 데미지를 입힙니다. 쿨타임 " +
                TextFormatting.GOLD +allskills[4].getcooldown()[lv-1]+
                TextFormatting.RESET+"초  소모값 : " + allskills[4].getskillcost()[lv-1]));

        data.getPlayer().sendMessage(new TextComponentString("냉기 : "));
        data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"한빙장"+
                TextFormatting.RESET +" : 사용 시, 냉기 스킬을 사용가능한 상태로 변경되며, "
                + String.format("%.1f",allskills[5].getskilldamage()[lv-1]) +"(+"+TextFormatting.RED +String.format("%.1f",(allskills[5].getskillAdcoe()[lv-1]*data.getAd()))+TextFormatting.RESET + ") (+"+
                TextFormatting.BLUE +String.format("%.1f",(allskills[5].getskillApcoe()[lv-1] * data.getAp()))+
                TextFormatting.RESET +" 의 데미지를 입힙니다. 쿨타임은 공격속도에 비례합니다. 1의 내력을 소모합니다."));

        data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"파음지"+
                TextFormatting.RESET +" : 기공사가 바라 보고 있는 곳을 얼어붙게 만들고, 주변의 적에게 " +
                String.format("%.1f",allskills[6].getskilldamage()[lv-1]) +"(+"+TextFormatting.RED +String.format("%.1f",(allskills[6].getskillAdcoe()[lv-1]*data.getAd()))+TextFormatting.RESET + ") (+"+
                TextFormatting.BLUE +String.format("%.1f",(allskills[6].getskillApcoe()[lv-1] * data.getAp()))+
                TextFormatting.RESET + ")의 데미지를 입힙니다. 데미지를 입힌 적 만큼 1의 내력을 회복합니다. 쿨타임 " +
                TextFormatting.GOLD +allskills[1].getcooldown()[lv-1]+
                TextFormatting.RESET+"초, 소모 값 없음." ));

        data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"빙하장"+
                TextFormatting.RESET +" : 기공사가 얼음을 전방으로 발사하여 " +
                String.format("%.1f",ball.getskilldamage1()[lv-1]) +"(+"+TextFormatting.RED +String.format("%.1f",(ball.getskillAdcoe()[lv-1]*data.getAd()))+TextFormatting.RESET + ") (+"+
                TextFormatting.BLUE +String.format("%.1f",(ball.getskillApcoe()[lv-1] * data.getAp()))+
                TextFormatting.RESET +")의 데미지를 입힙니다. 2초 동안 적의 이동속도를 "+ TextFormatting.DARK_PURPLE + ball.getskilldamage()[lv-1] +
                TextFormatting.RESET + "% 감소시킵니다. 쿨타임 " +
                TextFormatting.GOLD +ball.getcooldown()[lv-1]+
                TextFormatting.RESET+"초  소모값 : " + allskills[7].getskillcost()[lv-1] ));

        data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"빙백한포"+
                TextFormatting.RESET +" : 기공사가 바라 보고 있는 곳에 얼음이 쏟아져 내리고, 주변의 적에게 초당 " +
                String.format("%.1f",rain.getskilldamage()[lv-1]) +"(+"+TextFormatting.RED +String.format("%.1f",(rain.getskillAdcoe()[lv-1]*data.getAd()))+TextFormatting.RESET + ") (+"+
                TextFormatting.BLUE +String.format("%.1f",(rain.getskillApcoe()[lv-1] * data.getAp()))+
                TextFormatting.RESET + ")의 데미지를 입힙니다. 또한 범위 안에 있는 적의 이동속도를 "+ TextFormatting.DARK_PURPLE + rain.getskilldamage1()[lv-1] +
                TextFormatting.RESET +" 감소시킵니다. 쿨타임 " +
                TextFormatting.GOLD +allskills[1].getcooldown()[lv-1]+
                TextFormatting.RESET+"초  소모값 : " + allskills[8].getskillcost()[lv-1] ));

        data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"결빙공"+
                TextFormatting.RESET +" : 기공사가 3초 동안 얼음으로 몸을 감싸 움직일 수 없는 상태가 되어 자신을 지켜냅니다. 1초 당 " +
                String.format("%.1f",allskills[9].getskilldamage()[lv-1]) +"(+"+TextFormatting.RED +String.format("%.1f",(allskills[9].getskillAdcoe()[lv-1]*data.getAd()))+TextFormatting.RESET + ") (+"+
                TextFormatting.BLUE +String.format("%.1f",(allskills[9].getskillApcoe()[lv-1] * data.getAp()))+
                TextFormatting.RESET +")의 체력을 회복하며, 적에게 피해를 입지 않습니다. 3초 이내에 해빙공을 사용할 수 있으며 사용하지 않는 경우 자동으로 해제됩니다. 쿨타임 " +
                TextFormatting.GOLD +allskills[2].getcooldown()[lv-1]+
                TextFormatting.RESET+"초  소모값 : " + allskills[9].getskillcost()[lv-1] ));

        data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"해빙공"+
                TextFormatting.RESET +" : 기공사가 결빙공을 사용한 상태에서 3초 이내에 사용할 수 있습니다. 사용 시, 회복 효과와 무적효과는 해제됩니다. 사용하지 않을 시, 3초 후에 결빙공이 해제됩니다." ));
    }

    private class EventClass extends StatChangeEventHandle {
        PlayerData data;
        EventPriority priority;
        StatList code;

        public EventClass(PlayerData data, EventPriority priority, StatList code) {
            this.data = data;
            this.priority = priority;
            this.code = code;
        }

        @Override
        public void EventListener(BaseEvent.AbstractBaseEvent event) {
            StatChangeEvent sevent = (StatChangeEvent) event;
            PlayerData data = sevent.getData();

            if(this.data.equals(data) && sevent.getCode() == StatList.MAXMANA){
                double mana = sevent.getRef().getData().doubleValue() - sevent.getPrev().doubleValue() ;
                System.out.println("chmaxmana : " + mana);
                this.data.setAp(this.data.getAp() + (mana / 100)*manaapper);
                sevent.getRef().setData(sevent.getRef().getData().doubleValue() - mana);
                System.out.println(sevent.getRef().getData());
            }
            if(this.data.equals(data) && sevent.getCode() == StatList.CURRENTMANA){
                double mana = sevent.getRef().getData().doubleValue() - sevent.getPrev().doubleValue() ;
                sevent.getRef().setData(sevent.getRef().getData().doubleValue() - mana);
            }
            if(this.data.equals(data) && sevent.getCode() == StatList.REGENMANA){
                double mana = sevent.getRef().getData().doubleValue() - sevent.getPrev().doubleValue() ;
                this.data.setAp(this.data.getAp() + (mana / 100)*managenapper);
                sevent.getRef().setData(sevent.getRef().getData().doubleValue() - mana);
            }
        }

        @Override
        public EventPriority getPriority() {
            return this.priority;
        }

        @Override
        public PlayerData getPlayer() {
            return data;
        }

        @Override
        public StatList getCode() {
            return code;
        }
    }
}
