package RWAPI.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import RWAPI.Character.ForceMaster.ForceMaster;
import RWAPI.Character.PlayerClass;

public enum ClassList {
	Leesin(new RWAPI.Character.Leesin.Leesin()),
	MasterYi(new RWAPI.Character.MasterYi.MasterYi()),
	Nasus(new RWAPI.Character.Nasus.Nasus()),
	Kassadin(new RWAPI.Character.Kassadin.Kassadin()),
	ForceMaster(new ForceMaster());
	
	
	PlayerClass _class;
	private static Random random = new Random(System.currentTimeMillis());
	private static List<PlayerClass> list = new ArrayList<>();
	private static int idx = 0;
	
	private ClassList(PlayerClass _class) {
		this._class = _class;
	}
	
	public PlayerClass getPlayerClass() {
		return this._class;
	}

	public static void setList(){
		list.clear();
		idx = 0;
		for(int i = 0; i < ClassList.values().length-3; i++){
			while(true){
				PlayerClass temp = values()[random.nextInt(values().length)]._class;
				if(!(temp instanceof RWAPI.Character.Nasus.Nasus || temp instanceof RWAPI.Character.Kassadin.Kassadin)){
					continue;
				}
				if(!list.contains(temp)){
					list.add(temp);
					break;
				}
			}
		}
	}
	
	public static PlayerClass getRandomClass() throws CloneNotSupportedException {
        return list.get((idx++)% values().length).copyClass();
		//return Nasus.getPlayerClass().copyClass();
    }
}
