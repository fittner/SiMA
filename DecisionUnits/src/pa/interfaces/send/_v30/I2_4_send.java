/**
 * I2_4.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:16:46
 */
package pa.interfaces.send._v30;

import java.util.HashMap;

import pa.enums.eSymbolExtType;
import pa.symbolization.representationsymbol.itfSymbol;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 18.05.2010, 14:16:46
 * 
 */
public interface I2_4_send {
	public void send_I2_4(HashMap<eSymbolExtType, itfSymbol> poBodyData);
}