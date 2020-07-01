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
	point8(-136,78,48);
	
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
