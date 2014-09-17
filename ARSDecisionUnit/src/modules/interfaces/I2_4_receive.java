/**
 * I2_4.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:16:46
 */
package modules.interfaces;

import java.util.HashMap;



/**
 * Similar to I2.2, I2.4 transports neurosymbols to F14. This time, they originate from F12.
 * 
 * @author deutsch
 * 11.08.2009, 14:16:46
 * 
 */
public interface I2_4_receive {
	public void receive_I2_4(HashMap<String, Double> poBodyData);
}
