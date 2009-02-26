package TestApps.src.BumpTest;

import java.util.HashMap;

import sim.engine.SimState;
import sim.engine.Steppable;

public class TestEnergyList implements Steppable{
	
	static int cval;
	HashMap<String, Integer>  EnergyConsumptionTable =  new HashMap<String, Integer>();
	HashMap<String, String> SensorId =  new HashMap<String, String>();
	
	public void initializeList(){
	
	EnergyConsumptionTable.put("EnergyConsumption", 130);
	SensorId.put("EnergyConsumption", "1");
	
	EnergyConsumptionTable.put("InternalSensors", 123);
	SensorId.put("InternalSensors", "1.1");
	
	EnergyConsumptionTable.put("InternalActuators", 150);
	SensorId.put("InternalActuators","1.2");
	
	EnergyConsumptionTable.put("ExternalSensors", 170);
	SensorId.put("ExternalSensors","1.3");
	
	EnergyConsumptionTable.put("ExternalActuators", 110);
	SensorId.put("ExternalActuators","1.4");
	
	
	EnergyConsumptionTable.put("DigestiveSensor", 100);
	SensorId.put("DigestiveSensor","1.1.1");
	
	EnergyConsumptionTable.put("BloodPressureSensor", 100);
	SensorId.put("BloodPressureSensor","1.1.2");
	
	EnergyConsumptionTable.put("Abc", 130);
	SensorId.put("Abc","1.2.1");
	
	EnergyConsumptionTable.put("VisionSensor", 100);
	SensorId.put("VisionSensor","1.3.1");
	
	EnergyConsumptionTable.put("BumpSensor", 100);
	SensorId.put("BumpSensor","1.3.2");
	
	EnergyConsumptionTable.put("OdometrySensor", 100);
	SensorId.put("OdometrySensor","1.3.3");
	
	EnergyConsumptionTable.put("LeftHandActuator", 100);
	SensorId.put("LeftHandActuator","1.4.1");
	
	EnergyConsumptionTable.put("RightHandActuator", 100);
	SensorId.put("RightHandActuator","1.4.2");
	
	EnergyConsumptionTable.put("RightHandActuator", 100);
	SensorId.put("LeftHandActuator","1.4.3");
	
	EnergyConsumptionTable.put("LegActuator", 100);
	SensorId.put("LegActuator","1.4.4");
	
	}
	public TestEnergyList() {
		
		 initializeList();
		 
	}

	
	@Override
	public void step(SimState state) {
		int val;
		val=EnergyConsumptionTable.get("EnergyConsumption");
		EnergyConsumptionTable.put("EnergyConsumption", val-1);
		
		val=EnergyConsumptionTable.get("InternalSensors");
		EnergyConsumptionTable.put("InternalSensors", val-1);
		
		val=EnergyConsumptionTable.get("InternalActuators");
		EnergyConsumptionTable.put("InternalActuators", val-1);
		
		val=EnergyConsumptionTable.get("ExternalSensors");
		EnergyConsumptionTable.put("ExternalSensors", val-1);
		
		val=EnergyConsumptionTable.get("ExternalActuators");
		EnergyConsumptionTable.put("ExternalActuators", val-1);
		
	}
		
	public int getEnergyConsumptionTable(String key) {
		return EnergyConsumptionTable.get(key);
	}
	public String SensorId(String sensorkey) {
		// TODO Auto-generated method stub
		return SensorId.get(sensorkey);
	}
	
		
}
