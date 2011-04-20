/**
 * itfSymEatableArea.java: DecisionUnits - pa.symbolization.representationsymbol
 * 
 * @author deutsch
 * 19.10.2009, 19:37:54
 */
package pa._v19.symbolization.representationsymbol;

import java.util.ArrayList;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 19.10.2009, 19:37:54
 * 
 */
public interface itfSymbolEatableArea extends itfSymbol {
	public ArrayList<itfSymbolEatableAreaEntry> getEntries();
}
