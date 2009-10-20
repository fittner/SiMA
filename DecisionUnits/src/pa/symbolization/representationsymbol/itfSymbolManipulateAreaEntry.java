/**
 * itfSymVisionEntries.java: DecisionUnits - pa.symbolization.representationsymbol
 * 
 * @author deutsch
 * 19.10.2009, 19:40:17
 */
package pa.symbolization.representationsymbol;

import java.awt.Color;

import bfg.utils.enums.eCount;
import bfg.utils.enums.eSide;
import enums.eAntennaPositions;
import enums.eEntityType;
import enums.eShapeType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 19.10.2009, 19:40:17
 * 
 */
public interface itfSymbolManipulateAreaEntry extends itfSymbol {
	public eEntityType getEntityType();
	public eShapeType getShapeType();
	public eCount getNumEntitiesPresent();
	
	public boolean getAlive();
	public Color getColor();
	public eSide getObjectPosition(); 
	public eAntennaPositions getAntennaPositionLeft();
	public eAntennaPositions getAntennaPositionRight();
}
