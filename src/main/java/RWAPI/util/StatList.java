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
    REGENHEALTH(121),
    REGENMANA(122),
    PLUSATTACKSPEED(123),
    BASEATTACKSPEED(124),
    GOLD(125);

    private int code;

    public int getCode(){
        return this.code;
    }

    StatList(int num){
        this.code = num;
    }

    public static int[] getList(){
        int[] array = new int[25];
        int idx = 0;
        for(int i = 101; i < 126; i++){
            array[idx] = i;
            ++idx;
        }
        return array;
    }
}
