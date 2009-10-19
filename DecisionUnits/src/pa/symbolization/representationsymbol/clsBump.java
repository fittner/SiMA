package pa.symbolization.representationsymbol;

public class clsBump extends decisionunit.itf.sensors.clsBump implements itfGetMeshAttributeName, itfGetSymInterface, itfSymBump {
	public String getMeshAttributeName() {
		return "mnBumped"; 
	}
	
	public String getSymInterface() {
		return "itfSymBump";
	}
}
