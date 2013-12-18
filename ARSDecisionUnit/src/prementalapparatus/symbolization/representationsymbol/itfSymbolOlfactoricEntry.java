package prementalapparatus.symbolization.representationsymbol;

import du.enums.eDistance;
import du.enums.eOdor;
import du.enums.eSaliency;
import du.enums.eShapeType;
import bfg.utils.enums.eCount;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 19.10.2009, 19:38:07
 * 
 */
public interface itfSymbolOlfactoricEntry extends itfSymbol {
	public eShapeType getShapeType();
	public eCount getNumEntitiesPresent();	
	public eDistance getDistance();
	public eSaliency getIntensity();
	public eOdor getOdor();
}
