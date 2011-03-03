/**
 * I1_9_receive.java: DecisionUnits - pa.interfaces.receive._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:31:45
 */
package pa.interfaces.receive._v30;

import java.util.HashMap;

import pa.interfaces.I_BaseInterface;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:31:45
 * 
 */
public interface I1_9_receive  extends I_BaseInterface {
	public void receive_I1_9(HashMap<String, Double> poHomeostasisSymbols);
}