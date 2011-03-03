/**
 * I1_10_send.java: DecisionUnits - pa.interfaces.send._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:48:48
 */
package pa.interfaces.send._v30;

import java.util.HashMap;

import pa.interfaces.I_BaseInterface;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:48:48
 * 
 */
public interface I1_10_send  extends I_BaseInterface {
	public void send_I1_10(HashMap<String, Double> poHomeostasisSymbols);
}