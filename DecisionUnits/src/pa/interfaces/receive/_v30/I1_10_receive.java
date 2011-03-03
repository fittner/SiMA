/**
 * I1_10_receive.java: DecisionUnits - pa.interfaces.receive._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:48:36
 */
package pa.interfaces.receive._v30;

import java.util.HashMap;

import pa.interfaces.I_BaseInterface;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:48:36
 * 
 */
public interface I1_10_receive  extends I_BaseInterface {
	public void receive_I1_10(HashMap<String, Double> poHomeostasisSymbols);
}