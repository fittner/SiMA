/**
 * itfSymVisionEntries.java: DecisionUnits - pa.symbolization.representationsymbol
 * 
 * @author deutsch
 * 19.10.2009, 19:40:17
 */
package prementalapparatus.symbolization.representationsymbol;

import java.awt.Color;
import java.util.ArrayList;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 19.10.2009, 19:40:17
 * 
 */
public interface itfSymbolVisionEntry extends itfSymbol {
	public String getShapeType();
	public String getNumEntitiesPresent();
	
	public boolean getAlive();
	public Color getColor();
	public String getBrightness();
	public String getObjectPosition(); 
	public String getAntennaPositionLeft();
	public String getAntennaPositionRight();
	public String getDistance();
	public double getExactDebugX();
	public double getExactDebugY();
	public double getDebugSensorArousal();
	public itfSymbolVisionEntryAction getSymbolAction();
	public ArrayList<clsSymbolVisionEntryExpression> getExpressions();
	public boolean getCarringVariable();
}
