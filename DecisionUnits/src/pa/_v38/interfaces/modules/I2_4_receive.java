/**
 * I2_4.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:16:46
 */
package pa._v38.interfaces.modules;

import java.util.HashMap;


import pa._v38.symbolization.representationsymbol.itfSymbol;
import pa._v38.enums.eSymbolExtType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:16:46
 * 
 */
public interface I2_4_receive {
	public void receive_I2_4(HashMap<eSymbolExtType, itfSymbol> poBodyData);
}