package pa._v30.symbolization.representationsymbol;

import java.lang.reflect.Method;
import java.util.ArrayList;

import du.itf.sensors.clsSensorExtern;
import du.itf.sensors.clsVisionEntry;

public class clsSymbolVision extends du.itf.sensors.clsVision implements itfGetDataAccessMethods, itfSymbolVision {
	
	public clsSymbolVision(du.itf.sensors.clsVision poSensor) {
		super();
		
		moSensorType = poSensor.getSensorType();
		
		for (du.itf.sensors.clsSensorExtern oEntry:poSensor.getDataObjects()) {
			clsSymbolVisionEntry oE = new clsSymbolVisionEntry( (du.itf.sensors.clsVisionEntry)oEntry);
			moEntries.add(oE);
		}		
	}

	@Override
	public Method[] getDataAccessMethods() {
		return itfSymbolVision.class.getMethods();
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
			oResult.add( new clsSymbolVisionEntry( (clsVisionEntry)oEntry) );
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
	public ArrayList<itfSymbolVisionEntry> getEntries() {
		ArrayList<clsSensorExtern> oSE = getDataObjects();
		ArrayList<itfSymbolVisionEntry> oResult =  new ArrayList<itfSymbolVisionEntry>();
		
		for (clsSensorExtern oEntry:oSE) {
			oResult.add( new clsSymbolVisionEntry( (clsVisionEntry)oEntry) );
		}
		
		return oResult;
	}	
}
