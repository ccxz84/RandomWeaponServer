package RWAPI.util;

import RWAPI.Character.PlayerClass;

public enum ExpList {
	LV1(0),
	LV2(150),
	LV3(350),
	LV4(450),
	LV5(550),
	LV6(700),
	LV7(850),
	LV8(950),
	LV9(1050),
	LV10(1150),
	LV11(1400),
	LV12(1500),
	LV13(1600),
	LV14(1700),
	LV15(1800),
	LV16(1900),
	LV17(2000),
	LV18(2100),
	LV19(0);
	
	private double exp;

	public static final int maxlevel = 18;
	
	private ExpList(double exp) {
		this.exp = exp;
	}
	
	public double getMaxExp() {
		return this.exp;
	}
	
	public static double getLevelMaxExp(int level) {
		return values()[level].getMaxExp();
	}
}
