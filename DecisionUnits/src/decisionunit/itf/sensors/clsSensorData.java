package decisionunit.itf.sensors;

import java.util.HashMap;



public class clsSensorData {
	private HashMap<eSensorType, clsDataBase> moSensorData;
	
	public clsSensorData() {
		moSensorData = new HashMap<eSensorType, clsDataBase>();
	}
	
	public void addSensor(eSensorType pnType, clsDataBase poData) {
		moSensorData.put(pnType, poData);
	}
	
	public clsDataBase getSensor(eSensorType pnType) {
		return moSensorData.get(pnType);
	}
}
