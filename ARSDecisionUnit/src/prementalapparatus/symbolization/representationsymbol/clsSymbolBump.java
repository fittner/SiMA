package prementalapparatus.symbolization.representationsymbol;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class clsSymbolBump extends du.itf.sensors.clsBump implements itfGetSymbolName, itfGetDataAccessMethods, itfSymbolBump {
	public clsSymbolBump(du.itf.sensors.clsBump poSensor) {
		super();
		moSensorType = poSensor.getSensorType();
		
		mnBumped = poSensor.getBumped();
	}
	
	@Override
	public String getSymbolName() {
		return "BUMP"; 
	}
	
	@Override
	public String getSymbolType() {
		return "BUMP";
	}

	@Override
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
