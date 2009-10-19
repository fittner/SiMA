package pa.symbolization.representationsymbol;

public class clsEatableArea extends decisionunit.itf.sensors.clsEatableArea implements itfGetMeshAttributeName, itfGetSymInterface, itfSymEatableArea {
	@Override
	public String getMeshAttributeName() {
		return null;
	}

	@Override
	public String getSymInterface() {
		return "itfSymEatableArea";
	}
}
