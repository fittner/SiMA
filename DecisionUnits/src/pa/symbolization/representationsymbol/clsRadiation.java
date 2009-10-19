package pa.symbolization.representationsymbol;


/*
 * 
 * (horvath) - Radiation sensor provides just radiation intensity
 * 
 */
public class clsRadiation extends decisionunit.itf.sensors.clsRadiation  implements itfGetMeshAttributeName, itfGetSymInterface, itfSymRadiation {

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.10.2009, 20:00:50
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
	 * 19.10.2009, 20:00:50
	 * 
	 * @see pa.symbolization.representationsymbol.itfGetSymInterface#getSymInterface()
	 */
	@Override
	public String getSymInterface() {
		return "itfSymRadiation";
	}
}

