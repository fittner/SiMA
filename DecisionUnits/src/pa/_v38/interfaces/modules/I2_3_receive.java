/**
 * I2_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:16:31
 */
package pa._v38.interfaces.modules;

import java.util.HashMap;


import pa._v38.symbolization.eSymbolExtType;
import pa._v38.symbolization.representationsymbol.itfSymbol;

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
