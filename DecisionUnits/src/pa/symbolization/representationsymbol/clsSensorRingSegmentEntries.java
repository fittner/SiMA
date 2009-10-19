package pa.symbolization.representationsymbol;

public abstract class clsSensorRingSegmentEntries extends decisionunit.itf.sensors.clsSensorRingSegmentEntries implements itfGetMeshAttributeName, itfSymSensorRingSegmentEntries, itfGetSymInterface {

	@Override
	public String getMeshAttributeName() {
		return "mnEntityType";
	}

	@Override
	public String getSymInterface() {
		return "itfSymSensorRingSegmentEntries";
	}
}
