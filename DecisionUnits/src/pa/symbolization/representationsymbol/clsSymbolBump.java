package pa.symbolization.representationsymbol;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class clsSymbolBump extends decisionunit.itf.sensors.clsBump implements itfGetSymbolName, itfGetDataAccessMethods, itfSymbolBump {
	public clsSymbolBump(decisionunit.itf.sensors.clsBump poSensor) {
		super();
		moSensorType = poSensor.getSensorType();
		
		mnBumped = poSensor.getBumped();
	}
	
	public String getSymbolName() {
		return "Bump"; 
	}

	public Method[] getDataAccessMethods() {
		return itfSymbolBump.class.getMethods();
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
