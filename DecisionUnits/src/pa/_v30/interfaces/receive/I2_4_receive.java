/**
 * I2_4.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:16:46
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
 * 11.08.2009, 14:16:46
 * 
 */
public interface I2_4_receive extends I_BaseInterface {
	public void receive_I2_4(HashMap<eSymbolExtType, itfSymbol> poBodyData);
}
