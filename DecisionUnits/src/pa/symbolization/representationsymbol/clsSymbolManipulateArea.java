/**
 * clsSensorManipulateArea.java: DecisionUnitInterface - decisionunit.itf.sensors
 * 
 * @author langr
 * 09.09.2009, 14:04:06
 */
package pa.symbolization.representationsymbol;

import java.lang.reflect.Method;
import java.util.ArrayList;
import decisionunit.itf.sensors.clsManipulateAreaEntry;
import decisionunit.itf.sensors.clsSensorExtern;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 09.09.2009, 14:04:06
 * 
 */
public class clsSymbolManipulateArea extends decisionunit.itf.sensors.clsManipulateArea  implements itfGetDataAccessMethods, itfSymbolManipulateArea {

	public clsSymbolManipulateArea(decisionunit.itf.sensors.clsManipulateArea poSensor) {
		super();
		
		moSensorType = poSensor.getSensorType();
		
		for (decisionunit.itf.sensors.clsSensorExtern oEntry:poSensor.getDataObjects()) {
			clsSymbolManipulateAreaEntry oE = new clsSymbolManipulateAreaEntry( (decisionunit.itf.sensors.clsManipulateAreaEntry)oEntry);
			moEntries.add(oE);
		}
	}

	public Method[] getDataAccessMethods() {
		return itfSymbolManipulateArea.class.getMethods();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.10.2009, 12:43:37
	 * 
	 * @see pa.symbolization.representationsymbol.itfSymbol#getSymbolObjects()
	 */
	@Override
	public ArrayList<itfSymbol> getSymbolObjects() {
		ArrayList<clsSensorExtern> oSE = getDataObjects();
		ArrayList<itfSymbol> oResult =  new ArrayList<itfSymbol>();
		
		for (clsSensorExtern oEntry:oSE) {
			oResult.add( new clsSymbolManipulateAreaEntry( (clsManipulateAreaEntry)oEntry) );
		}
		
		return oResult;
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.10.2009, 13:41:44
	 * 
	 * @see pa.symbolization.representationsymbol.itfSymbolEatableArea#getEntries()
	 */
	@Override
	public ArrayList<itfSymbolManipulateAreaEntry> getEntries() {
		ArrayList<clsSensorExtern> oSE = getDataObjects();
		ArrayList<itfSymbolManipulateAreaEntry> oResult =  new ArrayList<itfSymbolManipulateAreaEntry>();
		
		for (clsSensorExtern oEntry:oSE) {
			oResult.add( new clsSymbolManipulateAreaEntry( (clsManipulateAreaEntry)oEntry) );
		}
		
		return oResult;
	}	
}