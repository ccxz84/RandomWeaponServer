package RWAPI.util;

public enum StatList {
    MAXHEALTH(101),
    CURRENTHEALTH(102),
    MAXMANA(103),
    CURRENTMANA(104),
    ARMOR(105),
    MAGICRESISTANCE(106),
    ARMORPENETRATION(107),
    MAGICPENETRATION(108),
    ARMORPENETRATIONPER(109),
    MAGICPENETRATIONPER(110),
    AD(111),
    AP(112),
    MOVE(113),
    DEATHEXP(114),
    DEATHGOLD(115),
    STATUS(116),
    GODMOD(117),
    SKILLACC(118),
    LEVEL(119),
    EXP(120),
    BASEREGENHEALTH(121),
    BASEREGENMANA(122),
    PLUSREGENHEALTH(123),
    PLUSREGENMANA(124),
    PLUSATTACKSPEED(125),
    BASEATTACKSPEED(126),
    GOLD(127);

    private int code;

    public int getCode(){
        return this.code;
    }

    StatList(int num){
        this.code = num;
    }

    public static int[] getList(){
        int[] array = new int[values().length];
        int idx = 0;
        for(int i = 101; i < (101 + values().length); i++){
            array[idx] = i;
            ++idx;
        }
        return array;
    }
}
