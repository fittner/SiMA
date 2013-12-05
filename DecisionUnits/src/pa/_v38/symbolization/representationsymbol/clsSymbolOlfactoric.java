package pa._v38.symbolization.representationsymbol;

import java.lang.reflect.Method;
import java.util.ArrayList;


import du.itf.sensors.clsOlfactoricEntry;
import du.itf.sensors.clsSensorExtern;

public class clsSymbolOlfactoric extends du.itf.sensors.clsOlfactoric implements itfGetDataAccessMethods, itfSymbolOlfactoric {
	
	public clsSymbolOlfactoric(du.itf.sensors.clsOlfactoric poSensor) {
		super();
		
		moSensorType = poSensor.getSensorType();
		
		for (clsSensorExtern oEntry:poSensor.getDataObjects()) {
			clsSymbolOlfactoricEntry oE = new clsSymbolOlfactoricEntry((clsOlfactoricEntry) oEntry);
			moEntries.add(oE);
		}		
	}

	@Override
	public Method[] getDataAccessMethods() {
		return itfSymbolEatableArea.class.getMethods();
	}


	@Override
	public ArrayList<itfSymbol> getSymbolObjects() {
		ArrayList<clsSensorExtern> oSE = getDataObjects();
		ArrayList<itfSymbol> oResult =  new ArrayList<itfSymbol>();
		
		for (clsSensorExtern oEntry:oSE) {
			oResult.add( new clsSymbolOlfactoricEntry( (clsOlfactoricEntry)oEntry) );
		}
		
		return oResult;
	}


	@Override
	public ArrayList<itfSymbolOlfactoricEntry> getEntries() {
		ArrayList<clsSensorExtern> oSE = getDataObjects();
		ArrayList<itfSymbolOlfactoricEntry> oResult =  new ArrayList<itfSymbolOlfactoricEntry>();
		
		for (clsSensorExtern oEntry:oSE) {
			oResult.add( new clsSymbolOlfactoricEntry( (clsOlfactoricEntry)oEntry) );
		}
		
		return oResult;
	}
}
