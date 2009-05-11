package decisionunit.itf.sensors;

import java.util.HashMap;

import enums.eSensorExtType;
import enums.eSensorIntType;



public class clsSensorData {
	private HashMap<eSensorExtType, clsDataBase> moSensorDataExt;
	private HashMap<eSensorIntType, clsDataBase> moSensorDataInt;
	
	public clsSensorData() {
		moSensorDataExt = new HashMap<eSensorExtType, clsDataBase>();
		moSensorDataInt = new HashMap<eSensorIntType, clsDataBase>();
	}
	
	public void addSensorExt(eSensorExtType pnType, clsDataBase poData) {
		moSensorDataExt.put(pnType, poData);
	}
	
	public clsDataBase getSensorExt(eSensorExtType pnType) {
		return moSensorDataExt.get(pnType);
	}
	
	public void addSensorInt(eSensorIntType pnType, clsDataBase poData) {
		moSensorDataInt.put(pnType, poData);
	}
	
	public clsDataBase getSensorInt(eSensorIntType pnType) {
		return moSensorDataInt.get(pnType);
	}
}
