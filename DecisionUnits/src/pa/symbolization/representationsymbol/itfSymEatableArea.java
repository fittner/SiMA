/**
 * itfSymEatableArea.java: DecisionUnits - pa.symbolization.representationsymbol
 * 
 * @author deutsch
 * 19.10.2009, 19:37:54
 */
package pa.symbolization.representationsymbol;

import java.util.ArrayList;

import decisionunit.itf.sensors.clsEatableAreaEntry;
import decisionunit.itf.sensors.clsSensorExtern;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 19.10.2009, 19:37:54
 * 
 */
public interface itfSymEatableArea {
	public ArrayList<clsSensorExtern> getDataObjects();
	public ArrayList<clsEatableAreaEntry> getEntries();
}
