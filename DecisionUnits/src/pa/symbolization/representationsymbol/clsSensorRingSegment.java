/**
 * clsSensorRingSegment.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author langr
 * 09.09.2009, 13:56:28
 */
package pa.symbolization.representationsymbol;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 09.09.2009, 13:56:28
 * 
 */
public class clsSensorRingSegment extends decisionunit.itf.sensors.clsSensorRingSegment implements itfGetMeshAttributeName, itfGetSymInterface, itfSymSensorRingSegment  {

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.10.2009, 19:56:47
	 * 
	 * @see pa.symbolization.representationsymbol.itfGetMeshAttributeName#getMeshAttributeName()
	 */
	@Override
	public String getMeshAttributeName() {
		return "";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.10.2009, 19:56:47
	 * 
	 * @see pa.symbolization.representationsymbol.itfGetSymInterface#getSymInterface()
	 */
	@Override
	public String getSymInterface() {
		return "itfSymSensorRingSegment";
	}


}
