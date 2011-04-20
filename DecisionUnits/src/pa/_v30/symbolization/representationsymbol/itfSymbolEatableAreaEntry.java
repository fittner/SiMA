/**
 * itfSymEatableAreaEntry.java: DecisionUnits - pa.symbolization.representationsymbol
 * 
 * @author deutsch
 * 19.10.2009, 19:38:07
 */
package pa._v30.symbolization.representationsymbol;

import du.enums.eDistance;
import du.enums.eShapeType;
import du.enums.eTriState;
import bfg.utils.enums.eCount;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 19.10.2009, 19:38:07
 * 
 */
public interface itfSymbolEatableAreaEntry extends itfSymbol {
	public eShapeType getShapeType();
	public eCount getNumEntitiesPresent();	
	public eTriState getIsAlive();
	public eTriState getIsConsumeable();
	public eDistance getDistance();
}
