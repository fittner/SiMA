package pa.symbolization.representationsymbol;

public class clsRadiationEntry extends decisionunit.itf.sensors.clsRadiationEntry implements itfGetMeshAttributeName, itfSymRadiationEntry, itfGetSymInterface {

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.10.2009, 20:10:35
	 * 
	 * @see pa.symbolization.representationsymbol.itfGetMeshAttributeName#getMeshAttributeName()
	 */
	@Override
	public String getMeshAttributeName() {
		return "mnEntityType";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.10.2009, 20:10:35
	 * 
	 * @see pa.symbolization.representationsymbol.itfGetSymInterface#getSymInterface()
	 */
	@Override
	public String getSymInterface() {
		return "itfSymRadiationEntry";
	}

	
}
