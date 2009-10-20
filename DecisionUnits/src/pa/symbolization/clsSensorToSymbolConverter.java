/**
 * clsSensorToSymbolConverter.java: DecisionUnits - pa.symbolization
 * 
 * @author deutsch
 * 20.10.2009, 10:59:07
 */
package pa.symbolization;

import java.util.HashMap;
import java.util.Map;

import pa.enums.eSymbolExtType;
import pa.symbolization.representationsymbol.clsSymbolBump;
import pa.symbolization.representationsymbol.clsSymbolEatableArea;
import pa.symbolization.representationsymbol.clsSymbolManipulateArea;
import pa.symbolization.representationsymbol.clsSymbolPositionChange;
import pa.symbolization.representationsymbol.clsSymbolRadiation;
import pa.symbolization.representationsymbol.clsSymbolVision;
import pa.symbolization.representationsymbol.itfSymbol;
import decisionunit.itf.sensors.clsBump;
import decisionunit.itf.sensors.clsDataBase;
import decisionunit.itf.sensors.clsEatableArea;
import decisionunit.itf.sensors.clsManipulateArea;
import decisionunit.itf.sensors.clsPositionChange;
import decisionunit.itf.sensors.clsRadiation;
import decisionunit.itf.sensors.clsVision;
import enums.eSensorExtType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 20.10.2009, 10:59:07
 * 
 */
public class clsSensorToSymbolConverter {
	public static HashMap<eSymbolExtType, itfSymbol> convertExtSensorToSymbol(HashMap<eSensorExtType, clsDataBase> poSensors) {
		HashMap<eSymbolExtType, itfSymbol> oResult = new HashMap<eSymbolExtType, itfSymbol>();
		
		for (Map.Entry<eSensorExtType, clsDataBase> oEntry:poSensors.entrySet()) {
			switch(oEntry.getKey())  {
				case BUMP:oResult.put(eSymbolExtType.BUMP, new clsSymbolBump((clsBump) oEntry.getValue()));
				case POSITIONCHANGE:oResult.put(eSymbolExtType.POSITIONCHANGE, new clsSymbolPositionChange((clsPositionChange) oEntry.getValue()));
				case RADIATION:oResult.put(eSymbolExtType.RADIATION, new clsSymbolRadiation((clsRadiation) oEntry.getValue()));

				case EATABLE_AREA:oResult.put(eSymbolExtType.EATABLE_AREA, new clsSymbolEatableArea((clsEatableArea) oEntry.getValue()));
				case MANIPULATE_AREA:oResult.put(eSymbolExtType.MANIPULATE_AREA, new clsSymbolManipulateArea((clsManipulateArea) oEntry.getValue()));
				case VISION:oResult.put(eSymbolExtType.VISION, new clsSymbolVision((clsVision) oEntry.getValue()));
				case VISION_FAR:oResult.put(eSymbolExtType.VISION_FAR, new clsSymbolVision((clsVision) oEntry.getValue()));				
				case VISION_MEDIUM:oResult.put(eSymbolExtType.VISION_MEDIUM, new clsSymbolVision((clsVision) oEntry.getValue()));
				case VISION_NEAR:oResult.put(eSymbolExtType.VISION_NEAR, new clsSymbolVision((clsVision) oEntry.getValue()));				
				default: throw new java.lang.IllegalArgumentException("unknown key "+oEntry.getKey());
			}
		}
		
		return oResult;
	}
}
