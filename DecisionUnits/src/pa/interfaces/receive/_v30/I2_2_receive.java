/**
 * I2_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:16:31
 */
package pa.interfaces.receive._v30;

import java.util.HashMap;

import pa.enums.eSymbolExtType;
import pa.interfaces.I_BaseInterface;
import pa.symbolization.representationsymbol.itfSymbol;

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
