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
public class clsManipulateArea extends decisionunit.itf.sensors.clsManipulateArea  implements itfGetMeshAttributeName, itfGetSymInterface, itfSymManipulateArea {

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