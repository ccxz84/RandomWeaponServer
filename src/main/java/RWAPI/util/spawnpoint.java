package RWAPI.util;

import java.util.Random;

import RWAPI.Character.PlayerClass;

public enum spawnpoint {
	point1(36,75,-24),
	point2(48,64,17),
	point3(-33,54,-109),
	point4(-5,64,-178),
	point5(-19,74,-114),
	point6(-78,64,-130),
	point7(-90,65,41),
	point8(-136,78,48),
	point9(-186,64,30),
	point10( -176,80,13),
	point11( -154,75,-6),
	point12(-127,71,-66),
	point13(-123,71,-99),
	point14( -89,74,-276),
	point15(-98,66,-209),
	point16(-256,65,-174),
	point17(-223,86,-87),
	point18(-219,80,-62),
	point19( -228,74,-49),
	point20(-218,68,-124),
	point21( -183,64,-94),
	point22(-273,118,-110),
	point23(-113,94,6),
	point24( 92,64,68);

	
	double[] point = new double[3];
	public static Random random = new Random(System.currentTimeMillis());
	
	private spawnpoint(double x, double y, double z) {
		point[0] = x;
		point[1] = y;
		point[2] = z;
	}
	
	public double[] getSpawnPoint() {
		return this.point;
	}
	
	public static double[] getRandomSpawnPoint() {
        return values()[random.nextInt(values().length)].getSpawnPoint();
    }
}
