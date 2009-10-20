package pa.symbolization.representationsymbol;

public class clsSymbolEatableArea extends decisionunit.itf.sensors.clsEatableArea implements itfIsContainer, itfGetMeshAttributeName, itfGetSymInterface, itfSymbolEatableArea {
	
	public clsSymbolEatableArea(decisionunit.itf.sensors.clsEatableArea poSensor) {
		super();
		
		moSensorType = poSensor.getSensorType();
		
		for (decisionunit.itf.sensors.clsEatableAreaEntry oEntry:poSensor.getEntries()) {
			clsSymbolEatableAreaEntry oE = new clsSymbolEatableAreaEntry(oEntry);
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
