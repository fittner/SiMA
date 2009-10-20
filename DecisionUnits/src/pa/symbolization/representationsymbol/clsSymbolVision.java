package pa.symbolization.representationsymbol;

public class clsSymbolVision extends decisionunit.itf.sensors.clsVision implements itfIsContainer, itfGetMeshAttributeName, itfGetSymInterface, itfSymbolVision {
	
	public clsSymbolVision(decisionunit.itf.sensors.clsVision poSensor) {
		super();
		
		moSensorType = poSensor.getSensorType();
		
		for (decisionunit.itf.sensors.clsSensorExtern oEntry:poSensor.getList()) {
			clsSymbolVisionEntry oE = new clsSymbolVisionEntry( (decisionunit.itf.sensors.clsVisionEntry)oEntry);
			moEntries.add(oE);
		}		
	}
	
	
	@Override
	public String getMeshAttributeName() {
		return null;
	}

	@Override
	public String getSymInterface() {
		return "itfSymEatableArea";
	}

}
