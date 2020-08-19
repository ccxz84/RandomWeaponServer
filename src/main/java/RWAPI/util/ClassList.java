package RWAPI.util;

import java.util.Random;

import RWAPI.Character.PlayerClass;

public enum ClassList {
	Leesin(new RWAPI.Character.Leesin.Leesin()),
	MasterYi(new RWAPI.Character.MasterYi.MasterYi());
	
	
	PlayerClass _class;
	public static Random random = new Random(System.currentTimeMillis());
	
	private ClassList(PlayerClass _class) {
		this._class = _class;
	}
	
	public PlayerClass getPlayerClass() {
		return this._class;
	}
	
	public static PlayerClass getRandomClass() throws CloneNotSupportedException {
        //return (PlayerClass) values()[random.nextInt(values().length)].getPlayerClass().copyClass();
		return MasterYi.getPlayerClass().copyClass();
    }
}
