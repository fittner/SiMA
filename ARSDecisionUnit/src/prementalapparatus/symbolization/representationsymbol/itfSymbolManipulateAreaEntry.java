/**
 * itfSymVisionEntries.java: DecisionUnits - pa.symbolization.representationsymbol
 * 
 * @author deutsch
 * 19.10.2009, 19:40:17
 */
package prementalapparatus.symbolization.representationsymbol;

import java.awt.Color;

import du.enums.eAntennaPositions;
import du.enums.eDistance;
import du.enums.eShapeType;

import bfg.utils.enums.eCount;
import bfg.utils.enums.eSide;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 19.10.2009, 19:40:17
 * 
 */
public interface itfSymbolManipulateAreaEntry extends itfSymbol {
	public eShapeType getShapeType();
	public eCount getNumEntitiesPresent();
	
	public boolean getAlive();
	public Color getColor();
	public eSide getObjectPosition(); 
	public eAntennaPositions getAntennaPositionLeft();
	public eAntennaPositions getAntennaPositionRight();
	public eDistance getDistance();
}
