/**
 * I2_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:16:31
 */
package pa._v19.interfaces.receive;

import java.util.HashMap;

import pa._v19.symbolization.representationsymbol.itfSymbol;
import pa._v19.interfaces.I_BaseInterface;
import pa._v19.enums.eSymbolExtType;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:16:31
 * 
 */
@Deprecated
public interface I2_2_receive extends I_BaseInterface {
	public void receive_I2_2(HashMap<eSymbolExtType, itfSymbol> poEnvironmentalData);
}
