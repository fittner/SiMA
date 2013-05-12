/**
 * itfSymVisionEntries.java: DecisionUnits - pa.symbolization.representationsymbol
 * 
 * @author deutsch
 * 19.10.2009, 19:40:17
 */
package pa._v38.symbolization.representationsymbol;

import java.awt.Color;
import java.util.ArrayList;

import du.enums.eAntennaPositions;
import du.enums.eDistance;
import du.enums.eShapeType;

import bfg.utils.enums.eCount;
import bfg.utils.enums.eSide;
import bfg.utils.enums.ePercievedActionType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 19.10.2009, 19:40:17
 * 
 */
public interface itfSymbolVisionEntry extends itfSymbol {
	public eShapeType getShapeType();
	public eCount getNumEntitiesPresent();
	
	public boolean getAlive();
	public Color getColor();
	public eSide getObjectPosition(); 
	public eAntennaPositions getAntennaPositionLeft();
	public eAntennaPositions getAntennaPositionRight();
	public eDistance getDistance();
	public double getExactDebugX();
	public double getExactDebugY();
	public double getDebugSensorArousal();
	public ArrayList<ePercievedActionType> getPerceivedAction();
}
