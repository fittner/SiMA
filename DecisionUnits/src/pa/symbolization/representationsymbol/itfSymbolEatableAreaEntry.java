/**
 * itfSymEatableAreaEntry.java: DecisionUnits - pa.symbolization.representationsymbol
 * 
 * @author deutsch
 * 19.10.2009, 19:38:07
 */
package pa.symbolization.representationsymbol;

import bfg.utils.enums.eCount;
import enums.eShapeType;
import enums.eTriState;

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
}
