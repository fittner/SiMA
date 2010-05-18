/**
 * I2_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:16:31
 */
package pa.interfaces.send;

import java.util.HashMap;

import pa.enums.eSymbolExtType;
import pa.symbolization.representationsymbol.itfSymbol;

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
