/**
 * I2_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:16:31
 */
package pa._v30.interfaces.modules;

import java.util.HashMap;

import pa._v30.symbolization.representationsymbol.itfSymbol;
import pa._v30.enums.eSymbolExtType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 18.05.2010, 14:16:31
 * 
 */
public interface I2_2_send {
	public void send_I2_2(HashMap<eSymbolExtType, itfSymbol> poEnvironmentalData);
}