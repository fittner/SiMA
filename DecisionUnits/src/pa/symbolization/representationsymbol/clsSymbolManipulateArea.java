/**
 * clsSensorManipulateArea.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author langr
 * 09.09.2009, 14:04:06
 */
package pa.symbolization.representationsymbol;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 09.09.2009, 14:04:06
 * 
 */
public class clsSymbolManipulateArea extends decisionunit.itf.sensors.clsManipulateArea  implements itfIsContainer, itfGetMeshAttributeName, itfGetSymInterface, itfSymbolManipulateArea {

	public clsSymbolManipulateArea(decisionunit.itf.sensors.clsManipulateArea poSensor) {
		super();
		
		moSensorType = poSensor.getSensorType();
		
		for (decisionunit.itf.sensors.clsSensorExtern oEntry:poSensor.getList()) {
			clsSymbolManipulateAreaEntry oE = new clsSymbolManipulateAreaEntry( (decisionunit.itf.sensors.clsManipulateAreaEntry)oEntry);
			moEntries.add(oE);
		}
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.10.2009, 19:53:18
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
	 * 19.10.2009, 19:53:18
	 * 
	 * @see pa.symbolization.representationsymbol.itfGetSymInterface#getSymInterface()
	 */
	@Override
	public String getSymInterface() {
		return "itfSymManipulateArea";
	}

}