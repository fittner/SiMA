/**
 * I2_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:16:31
 */
package modules.interfaces;

import java.util.HashMap;

import prementalapparatus.symbolization.eSymbolExtType;
import prementalapparatus.symbolization.representationsymbol.itfSymbol;

/**
 * Neurosymbols derived from external sensors are transmitted from F11 to F14.
 * 
 * @author deutsch
 * 11.08.2009, 14:16:31
 * 
 */
public interface I2_3_receive {
	public void receive_I2_3(HashMap<eSymbolExtType, itfSymbol> poEnvironmentalData);
}
