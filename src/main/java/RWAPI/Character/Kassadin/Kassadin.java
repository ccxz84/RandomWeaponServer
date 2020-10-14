package RWAPI.Character.Kassadin;

import RWAPI.Character.Leesin.Leesin;
import RWAPI.Character.Leesin.skills.*;
import RWAPI.Character.PlayerClass;
import RWAPI.Character.PlayerData;
import RWAPI.Character.Skill;
import RWAPI.init.ModSkills;
import RWAPI.init.ModWeapons;
import RWAPI.util.ClassList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class Kassadin extends PlayerClass {

    public Kassadin(){
        default_health = 800;
        default_mana = 200;

        default_ad = 80;
        default_ap = 0;
        default_move = 107;

        default_regenHealth = 1.1f;
        default_regenMana = 5f;

        attackSpeed = 0.4f;

        //class_code = ClassList.Leesin;

        ClassName = "리 신";

        super.weapon = ModWeapons.leesin;

        //matrix = new StatMatrix();
        matrix = new Kassadin.kassadinMatrix();
        skillSet[0] = ModSkills.skill.get(ModSkills.flurry.SkillNumber);
        skillSet[1] = ModSkills.skill.get(ModSkills.sonicwave.SkillNumber);
        skillSet[2] = ModSkills.skill.get(ModSkills.safeguard.SkillNumber);
        skillSet[3] = ModSkills.skill.get(ModSkills.tempest.SkillNumber);
        skillSet[4] = ModSkills.skill.get(ModSkills.dragonsrage.SkillNumber);

        /*skills[0] = new flurry(this);
        skills[1] = new sonicwave(this);
        skills[2] = new safeguard(this);
        skills[3] = new tempest(this);
        skills[4] = new dragonsrage(this);*/
    }

    @Override
    public PlayerClass copyClass(){
        return new Kassadin();
    }

    public void skill0(EntityPlayer player) {
        skills[0].skillExecute(player);
    }

    @Override
    public void skill1(EntityPlayer player) {
        skills[1].skillpreExecute(player);
        skills[1].skillExecute(player);
        skills[1].skillEnd(player);
    }

    @Override
    public void skill2(EntityPlayer player) {
        skills[2].skillpreExecute(player);
        skills[2].skillExecute(player);
        skills[2].skillEnd(player);
    }

    @Override
    public void skill3(EntityPlayer player) {
        skills[3].skillpreExecute(player);
        skills[3].skillExecute(player);
        skills[3].skillEnd(player);
    }

    @Override
    public void skill4(EntityPlayer player) {
        skills[4].skillpreExecute(player);
        skills[4].skillExecute(player);
        skills[4].skillEnd(player);
    }

    @Override
    public void Levelup(PlayerData data) {
        super.Levelup(data);
    }

    class kassadinMatrix extends StatMatrix{

        public kassadinMatrix() {
            super.ad = this.ad;
            super.ap = this.ap;
            super.hp = this.hp;
            super.hregen = this.hregen;
            super.mana = this.mana;
            super.move = this.move;
            super.mregen = this.mregen;
            super.attackspeed = this.attackspeed;
        }
        final double[] hp = {800,
                850,
                885,
                910,
                940,
                1090,
                1140,
                1200,
                1310,
                1390,
                1440,
                1600};
        final double[] mana = {200,
                200,
                200,
                200,
                200,
                300,
                300,
                300,
                350,
                350,
                350,
                500};
        final double[] hregen = {1.1,
                1.2,
                1.5,
                1.7,
                2,
                4,
                4.2,
                4.6,
                5,
                5.1,
                5.4,
                6.5};
        final double[] mregen = {5,
                5,
                5,
                5,
                5,
                6,
                6,
                6,
                6,
                6,
                6,
                8};
        final double[] ad = {
                80,
                83,
                87,
                90,
                93,
                110,
                113,
                117,
                120,
                123,
                127,
                140
        };
        final double[] ap = {0,
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
        };
        final double[] move = {
                107,
                107,
                107,
                107,
                107,
                107,
                107,
                107,
                107,
                107,
                107,
                107
        };
        final double[] attackspeed = {
                0.4,
                0.43,
                0.49,
                0.53,
                0.56,
                0.65,
                0.69,
                0.72,
                0.76,
                0.79,
                0.82,
                0.9
        };
    }


    @Override
    public void EndGame(EntityPlayerMP player){
        for(Skill skill : skills){
            skill.skillEnd(player);
        }
    }

    @Override
    public void classInformation(PlayerData data) {
        int lv = data.getLevel();
        sonicwave wave = (sonicwave) skills[1];
        flurry flurry = (flurry) skills[0];
        data.getPlayer().sendMessage(new TextComponentString("리 신 : "));
        data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"질풍격"+
                TextFormatting.RESET +" : 스킬 사용 시, 3초 동안 2회의 기본 공격에 대해 공격속도가 "
                + String.format("%.1f",skills[0].getskilldamage()[lv-1]) + " 만큼 증가합니다. 활성화 된 상태에서 적을 공격 시, " +
                TextFormatting.AQUA + flurry.getSkilldamage2()[lv-1]+
                TextFormatting.RESET + "의 기력이 회복됩니다."));
        data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"음파"+
                TextFormatting.RESET +" : 리 신이 불협화음으로 된 음파를 발사하여 적에게 " +
                String.format("%.1f",skills[1].getskilldamage()[lv-1]) +"(+"+TextFormatting.RED +String.format("%.1f",(skills[1].getskillAdcoe()[lv-1]*data.getAd())) +
                TextFormatting.RESET + ")의 데미지를 입힙니다. 음파가 적에게 명중하면 3초 안에 공명의 일격을 시전할 수 있습니다. 쿨타임 " +
                TextFormatting.GOLD +skills[1].getcooldown()[lv-1]+
                TextFormatting.RESET+"초" ));
        data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"공명의 일격"+
                TextFormatting.RESET +" : 리 신이 음파를 맞은 적에게 돌진하여 " + String.format("%.1f",wave.getskilldamage2()[lv-1]) + "(+"
                +TextFormatting.RED +String.format("%.1f",(wave.getskill1coe()[0][lv-1] * data.getAd()))+
                TextFormatting.RESET +")의 데미지를 입히고, 추가로 적 최대체력의 " +
                TextFormatting.DARK_RED + String.format("%.1f",(wave.getskill1coe()[1][lv-1] *100))+"%"+
                TextFormatting.RESET + "의 데미지를 입힙니다."));
        data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"방호"+
                TextFormatting.RESET +" : 리 신이 바라보고 있는 방향으로 일정거리 순간이동 합니다. 쿨타임 : " +
                TextFormatting.GOLD +skills[2].getcooldown()[lv-1]+
                TextFormatting.RESET+"초"));
        data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"폭풍" +
                TextFormatting.RESET +" : 리 신이 1초 동안 정신을 집중하여 바닥을 내리쳐 " + skills[3].getskilldamage()[lv-1] +"(+"+
                TextFormatting.RED +String.format("%.1f",(skills[3].getskillAdcoe()[lv-1] * data.getAd()))+
                TextFormatting.RESET +")의 데미지를 입힙니다. 정신 집중 시간에는 " +
                TextFormatting.DARK_PURPLE + "70%"+
                TextFormatting.RESET +"의 이동속도가 감소합니다. 쿨타임 : "+
                TextFormatting.GOLD + skills[3].getcooldown()[lv-1]+
                TextFormatting.RESET+"초"));
        data.getPlayer().sendMessage(new TextComponentString(TextFormatting.YELLOW +"용의 분노" +
                TextFormatting.RESET +" : 리 신이 스킬 사용 후, 2초 안에 기본 공격 시 적에게 " + skills[4].getskilldamage()[lv-1] + "(+"+
                TextFormatting.RED + String.format("%.1f",(skills[4].getskillAdcoe()[lv-1] * data.getAd()))+
                TextFormatting.RESET +")의 데미지를 입히며 뒤로 날려보냅니다. 쿨타임 : "+
                TextFormatting.GOLD + skills[4].getcooldown()[lv-1]+
                TextFormatting.RESET+"초"));
    }
}
