/**
 * I2_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:16:31
 */
package pa._v30.interfaces.receive;

import java.util.HashMap;

import pa._v30.interfaces.I_BaseInterface;
import pa._v30.symbolization.representationsymbol.itfSymbol;
import pa._v30.enums.eSymbolExtType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:16:31
 * 
 */
public interface I2_2_receive extends I_BaseInterface {
	public void receive_I2_2(HashMap<eSymbolExtType, itfSymbol> poEnvironmentalData);
}
