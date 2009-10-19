package pa.symbolization.representationsymbol;



public class clsPositionChange extends decisionunit.itf.sensors.clsPositionChange  implements itfGetMeshAttributeName, itfSymPositionChange, itfGetSymInterface {

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.10.2009, 19:59:33
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
	 * 19.10.2009, 19:59:33
	 * 
	 * @see pa.symbolization.representationsymbol.itfGetSymInterface#getSymInterface()
	 */
	@Override
	public String getSymInterface() {
		return "itfSymPositionChange";
	}


}
