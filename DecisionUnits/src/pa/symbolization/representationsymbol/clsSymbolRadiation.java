package pa.symbolization.representationsymbol;

import java.lang.reflect.Method;
import java.util.ArrayList;


/*
 * 
 * (horvath) - Radiation sensor provides just radiation intensity
 * 
 */
public class clsSymbolRadiation extends du.itf.sensors.clsRadiation  implements itfGetSymbolName, itfGetDataAccessMethods, itfSymbolRadiation {
	public clsSymbolRadiation(du.itf.sensors.clsRadiation poSensor) {
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
	public String getSymbolName() {
		return "Radiation";
	}
	
	@Override
	public String getSymbolType() {
		return "Radiation";
	}

	@Override
	public Method[] getDataAccessMethods() {
		return itfSymbolRadiation.class.getMethods();
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.10.2009, 12:34:45
	 * 
	 * @see pa.symbolization.representationsymbol.itfSymbol#getSymbolObjects()
	 */
	@Override
	public ArrayList<itfSymbol> getSymbolObjects() {
		ArrayList<itfSymbol> oRetVal = new ArrayList<itfSymbol>();
		oRetVal.add(this);
		return oRetVal;
	}	
}

