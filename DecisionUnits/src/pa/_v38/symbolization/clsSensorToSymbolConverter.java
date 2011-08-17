/**
 * clsSensorToSymbolConverter.java: DecisionUnits - pa.symbolization
 * 
 * @author deutsch
 * 20.10.2009, 10:59:07
 */
package pa._v38.symbolization;

import java.util.HashMap;
import java.util.Map;

import pa._v38.symbolization.representationsymbol.clsSymbolBump;
import pa._v38.symbolization.representationsymbol.clsSymbolEatableArea;
import pa._v38.symbolization.representationsymbol.clsSymbolManipulateArea;
import pa._v38.symbolization.representationsymbol.clsSymbolPositionChange;
import pa._v38.symbolization.representationsymbol.clsSymbolRadiation;
import pa._v38.symbolization.representationsymbol.clsSymbolVision;
import pa._v38.symbolization.representationsymbol.itfSymbol;
import du.enums.eSensorExtType;
import du.itf.sensors.clsBump;
import du.itf.sensors.clsEatableArea;
import du.itf.sensors.clsManipulateArea;
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
	public static HashMap<eSymbolExtType, itfSymbol> convertExtSensorToSymbol(HashMap<eSensorExtType, clsSensorExtern> poSensors) {
		HashMap<eSymbolExtType, itfSymbol> oResult = new HashMap<eSymbolExtType, itfSymbol>();
		
		for (Map.Entry<eSensorExtType, clsSensorExtern> oEntry:poSensors.entrySet()) {
			if (oEntry.getValue() != null) {
				switch(oEntry.getKey())  {
					case BUMP:oResult.put(eSymbolExtType.BUMP, new clsSymbolBump((clsBump) oEntry.getValue()));break;
					case POSITIONCHANGE:oResult.put(eSymbolExtType.POSITIONCHANGE, new clsSymbolPositionChange((clsPositionChange) oEntry.getValue()));break;
					case RADIATION:oResult.put(eSymbolExtType.RADIATION, new clsSymbolRadiation((clsRadiation) oEntry.getValue()));break;
	
					case EATABLE_AREA:oResult.put(eSymbolExtType.EATABLE_AREA, new clsSymbolEatableArea((clsEatableArea) oEntry.getValue()));break;
					case MANIPULATE_AREA:oResult.put(eSymbolExtType.MANIPULATE_AREA, new clsSymbolManipulateArea((clsManipulateArea) oEntry.getValue()));break;
					case VISION:oResult.put(eSymbolExtType.VISION, new clsSymbolVision((clsVision) oEntry.getValue()));break;
					case VISION_FAR:oResult.put(eSymbolExtType.VISION_FAR, new clsSymbolVision((clsVision) oEntry.getValue()));break;	
					case VISION_MEDIUM:oResult.put(eSymbolExtType.VISION_MEDIUM, new clsSymbolVision((clsVision) oEntry.getValue()));break;
					case VISION_NEAR:oResult.put(eSymbolExtType.VISION_NEAR, new clsSymbolVision((clsVision) oEntry.getValue()));break;			
					default: throw new java.lang.IllegalArgumentException("unknown key "+oEntry.getKey());
				}
			}
		}
		
		return oResult;
	}
}
