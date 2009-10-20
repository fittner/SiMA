package pa.symbolization.representationsymbol;

public class clsSymbolBump extends decisionunit.itf.sensors.clsBump implements itfGetMeshAttributeName, itfGetSymInterface, itfSymbolBump {
	public clsSymbolBump(decisionunit.itf.sensors.clsBump poSensor) {
		super();
		moSensorType = poSensor.getSensorType();
		
		mnBumped = poSensor.getBumped();
	}
	
	public String getMeshAttributeName() {
		return "mnBumped"; 
	}
	
	public String getSymInterface() {
		return "itfSymBump";
	}
}
