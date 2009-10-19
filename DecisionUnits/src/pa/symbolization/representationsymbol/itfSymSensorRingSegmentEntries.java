/**
 * itfSensorRingSegmentEntries.java: DecisionUnits - pa.symbolization.representationsymbol
 * 
 * @author deutsch
 * 19.10.2009, 19:39:59
 */
package pa.symbolization.representationsymbol;

import bfg.utils.enums.eCount;
import enums.eEntityType;
import enums.eShapeType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 19.10.2009, 19:39:59
 * 
 */
public interface itfSymSensorRingSegmentEntries {
	public eEntityType getEntityType();
	public eShapeType getShapeType();
	public eCount getNumEntitiesPresent();
}
