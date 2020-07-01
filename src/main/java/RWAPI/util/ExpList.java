package RWAPI.util;

import RWAPI.Character.PlayerClass;

public enum ExpList {
	LV1(0),
	LV2(150),
	LV3(220),
	LV4(270),
	LV5(320),
	LV6(600),
	LV7(700),
	LV8(800),
	LV9(900),
	LV10(1000),
	LV11(1100),
	LV12(1600),
	LV13(0);
	
	private float exp;
	
	private ExpList(float exp) {
		this.exp = exp;
	}
	
	public float getMaxExp() {
		return this.exp;
	}
	
	public static float getLevelMaxExp(int level) {
		return (int)values()[level].getMaxExp();
	}
}
