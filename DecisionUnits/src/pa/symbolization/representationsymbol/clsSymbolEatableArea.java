package pa.symbolization.representationsymbol;

import java.lang.reflect.Method;
import java.util.ArrayList;

import du.itf.sensors.clsEatableAreaEntry;
import du.itf.sensors.clsSensorExtern;

public class clsSymbolEatableArea extends du.itf.sensors.clsEatableArea implements itfGetDataAccessMethods, itfSymbolEatableArea {
	
	public clsSymbolEatableArea(du.itf.sensors.clsEatableArea poSensor) {
		super();
		
		moSensorType = poSensor.getSensorType();
		
		for (clsSensorExtern oEntry:poSensor.getDataObjects()) {
			clsSymbolEatableAreaEntry oE = new clsSymbolEatableAreaEntry((clsEatableAreaEntry) oEntry);
			moEntries.add(oE);
		}		
	}

	public Method[] getDataAccessMethods() {
		return itfSymbolEatableArea.class.getMethods();
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
			oResult.add( new clsSymbolEatableAreaEntry( (clsEatableAreaEntry)oEntry) );
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
	public ArrayList<itfSymbolEatableAreaEntry> getEntries() {
		ArrayList<clsSensorExtern> oSE = getDataObjects();
		ArrayList<itfSymbolEatableAreaEntry> oResult =  new ArrayList<itfSymbolEatableAreaEntry>();
		
		for (clsSensorExtern oEntry:oSE) {
			oResult.add( new clsSymbolEatableAreaEntry( (clsEatableAreaEntry)oEntry) );
		}
		
		return oResult;
	}
}
