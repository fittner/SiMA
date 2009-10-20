package pa.symbolization.representationsymbol;



public class clsSymbolPositionChange extends decisionunit.itf.sensors.clsPositionChange  implements itfGetMeshAttributeName, itfSymbolPositionChange, itfGetSymInterface {
	public clsSymbolPositionChange(decisionunit.itf.sensors.clsPositionChange poSensor) {
		super();
		moSensorType = poSensor.getSensorType();
		
		a = poSensor.getA();
		x = poSensor.getX();
		y = poSensor.getY();
	}
	
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
