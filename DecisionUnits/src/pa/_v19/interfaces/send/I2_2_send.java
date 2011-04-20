/**
 * I2_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:16:31
 */
package pa._v19.interfaces.send;

import java.util.HashMap;

import pa._v19.symbolization.representationsymbol.itfSymbol;
import pa._v19.enums.eSymbolExtType;

/**
 * 
 * 
 * @author deutsch
 * 18.05.2010, 14:16:31
 * 
 */
@Deprecated
public interface I2_2_send {
	public void send_I2_2(HashMap<eSymbolExtType, itfSymbol> poEnvironmentalData);
}
