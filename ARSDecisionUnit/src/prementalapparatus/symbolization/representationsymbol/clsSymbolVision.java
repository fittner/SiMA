package prementalapparatus.symbolization.representationsymbol;

import java.lang.reflect.Method;
import java.util.ArrayList;

import communication.datatypes.clsDataPoint;

import du.enums.eSensorExtType;

public class clsSymbolVision implements itfGetDataAccessMethods, itfSymbolVision {
    protected eSensorExtType moSensorType;
    protected ArrayList<clsSymbolVisionEntry> moEntries = new ArrayList<clsSymbolVisionEntry>();


    public clsSymbolVision(clsDataPoint poSensor) {
        super();
        
        moSensorType = eSensorExtType.valueOf(poSensor.getType());
        for(clsDataPoint oEntry: poSensor.getAssociatedDataPoints()){
            if(oEntry.getType().equals("ENTITY")){
                clsSymbolVisionEntry oE = new clsSymbolVisionEntry(oEntry);
                moEntries.add(oE);
            }
        }
 
    }
    public eSensorExtType getSensorType() {
        return moSensorType;
    }
    public void setSensorType(eSensorExtType poSensorType) {
        moSensorType = poSensorType;
    }
    
    public ArrayList<clsSymbolVisionEntry> getDataObjects() {
        return moEntries;
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
		ArrayList<clsSymbolVisionEntry> oSE = getDataObjects();
		ArrayList<itfSymbol> oResult =  new ArrayList<itfSymbol>();
		
		for (clsSymbolVisionEntry oEntry:oSE) {
			oResult.add( oEntry );
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
		ArrayList<clsSymbolVisionEntry> oSE = getDataObjects();
		ArrayList<itfSymbolVisionEntry> oResult =  new ArrayList<itfSymbolVisionEntry>();
		
		for (clsSymbolVisionEntry oEntry:oSE) {
			oResult.add(oEntry );
		}
		
		return oResult;
	}	
}
