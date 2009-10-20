package pa.symbolization.representationsymbol;


/*
 * 
 * (horvath) - Radiation sensor provides just radiation intensity
 * 
 */
public class clsSymbolRadiation extends decisionunit.itf.sensors.clsRadiation  implements itfGetMeshAttributeName, itfGetSymInterface, itfSymbolRadiation {
	public clsSymbolRadiation(decisionunit.itf.sensors.clsRadiation poSensor) {
		super();
		moSensorType = poSensor.getSensorType();
		
		mrIntensity = poSensor.getIntensity();
	}
	
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

