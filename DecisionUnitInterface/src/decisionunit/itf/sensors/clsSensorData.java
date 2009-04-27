package decisionunit.itf.sensors;

import java.util.HashMap;

import enums.eSensorExtType;



public class clsSensorData {
	private HashMap<eSensorExtType, clsDataBase> moSensorData;
	
	public clsSensorData() {
		moSensorData = new HashMap<eSensorExtType, clsDataBase>();
	}
	
	public void addSensor(eSensorExtType pnType, clsDataBase poData) {
		moSensorData.put(pnType, poData);
	}
	
	public clsDataBase getSensor(eSensorExtType pnType) {
		return moSensorData.get(pnType);
	}
}
