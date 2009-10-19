package pa.symbolization.representationsymbol;

public class clsVision extends decisionunit.itf.sensors.clsVision implements itfGetMeshAttributeName, itfGetSymInterface, itfSymVision {
	@Override
	public String getMeshAttributeName() {
		return null;
	}

	@Override
	public String getSymInterface() {
		return "itfSymEatableArea";
	}

}
