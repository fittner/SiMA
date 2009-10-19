/**
 * itfSymRadiationEntry.java: DecisionUnits - pa.symbolization.representationsymbol
 * 
 * @author deutsch
 * 19.10.2009, 19:39:37
 */
package pa.symbolization.representationsymbol;

import java.awt.Color;
import enums.eEntityType;
import enums.eShapeType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 19.10.2009, 19:39:37
 * 
 */
public interface itfSymRadiationEntry {
	public eEntityType getEntityType();
	public eShapeType getShapeType();
	public boolean getAlive();
	public Color getColor();
}
