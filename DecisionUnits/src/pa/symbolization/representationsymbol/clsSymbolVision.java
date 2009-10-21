package pa.symbolization.representationsymbol;

import java.lang.reflect.Method;
import java.util.ArrayList;
import decisionunit.itf.sensors.clsSensorExtern;
import decisionunit.itf.sensors.clsVisionEntry;

public class clsSymbolVision extends decisionunit.itf.sensors.clsVision implements itfIsContainer, itfGetSymbolName, itfGetDataAccessMethods, itfSymbolVision {
	
	public clsSymbolVision(decisionunit.itf.sensors.clsVision poSensor) {
		super();
		
		moSensorType = poSensor.getSensorType();
		
		for (decisionunit.itf.sensors.clsSensorExtern oEntry:poSensor.getDataObjects()) {
			clsSymbolVisionEntry oE = new clsSymbolVisionEntry( (decisionunit.itf.sensors.clsVisionEntry)oEntry);
			moEntries.add(oE);
		}		
	}
	
	
	@Override
	public String getSymbolName() {
		return null;
	}

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
