/**
 * clsSensorToSymbolConverter.java: DecisionUnits - pa.symbolization
 * 
 * @author deutsch
 * 20.10.2009, 10:59:07
 */
package prementalapparatus.symbolization;

import java.util.HashMap;
import java.util.Map;

import communication.datatypes.clsDataContainer;
import communication.datatypes.clsDataPoint;

import prementalapparatus.symbolization.representationsymbol.clsSymbolAcoustic;
import prementalapparatus.symbolization.representationsymbol.clsSymbolBump;
import prementalapparatus.symbolization.representationsymbol.clsSymbolEatableArea;
import prementalapparatus.symbolization.representationsymbol.clsSymbolManipulateArea;
import prementalapparatus.symbolization.representationsymbol.clsSymbolOlfactoric;
import prementalapparatus.symbolization.representationsymbol.clsSymbolPositionChange;
import prementalapparatus.symbolization.representationsymbol.clsSymbolRadiation;
import prementalapparatus.symbolization.representationsymbol.clsSymbolVision;
import prementalapparatus.symbolization.representationsymbol.itfSymbol;
import du.enums.eSensorExtType;
import du.itf.sensors.clsAcoustic;
import du.itf.sensors.clsBump;
import du.itf.sensors.clsEatableArea;
import du.itf.sensors.clsManipulateArea;
import du.itf.sensors.clsOlfactoric;
import du.itf.sensors.clsPositionChange;
import du.itf.sensors.clsRadiation;
import du.itf.sensors.clsSensorExtern;
import du.itf.sensors.clsVision;

/**
 * Iterates through the list of clsSensorExtern values, and creates for every entry a clsSymbol[SensorType] entry.
 * 
 * @author deutsch
 * 20.10.2009, 10:59:07
 * 
 */
public class clsSensorToSymbolConverter {
    
    /**
     * Iterates through the list of clsSensorExtern values, and creates for every entry a clsSymbol[SensorType] entry.
     *
     * @since 27.07.2011 14:04:36
     *
     * @param poSensors HashMap&lt;eSensorExtType, clsSensorExtern&gt;
     * @return
     */
    public static HashMap<eSymbolExtType, itfSymbol> convertExtSensorToSymbol(clsDataContainer poSensors) {
        HashMap<eSymbolExtType, itfSymbol> oResult = new HashMap<eSymbolExtType, itfSymbol>();
        
        for (clsDataPoint oEntry:poSensors.getData()) {
            if (oEntry.getType() != null) {

                if(oEntry.getType().equals("VISION")){
                    oResult.put(eSymbolExtType.VISION, new clsSymbolVision(oEntry));
                }
            }
        }
    

        return oResult;
    }
}
