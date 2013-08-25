/**
 * itfSymbolAcoustic.java: DecisionUnits - pa.symbolization.representationsymbol
 * 
 * @author hi
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
import du.enums.eSaliency;

/**
 * DOCUMENT (hi) - insert description 
 * 
 * @author hi
 * 
 */
public interface itfSymbolAcoustic extends itfSymbol {
    public eShapeType getShapeType();
    public eCount getNumEntitiesPresent();
    
    public boolean getAlive();
    public Color getColor();
    public eSaliency getBrightness();
    public eSide getObjectPosition(); 
    public eAntennaPositions getAntennaPositionLeft();
    public eAntennaPositions getAntennaPositionRight();
    public eDistance getDistance();
    public double getExactDebugX();
    public double getExactDebugY();
    public double getDebugSensorArousal();
    public ArrayList<ePercievedActionType> getPerceivedAction();
    /**
     * DOCUMENT (hinterleitner) - insert description
     *
     * @since 24.08.2013 15:21:26
     *
     * @return
     */
    ArrayList<itfSymbolAcousticEntry> getEntries();
}
