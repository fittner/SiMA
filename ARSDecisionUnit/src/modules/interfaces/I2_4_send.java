/**
 * I2_4.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:16:46
 */
package modules.interfaces;

import java.util.HashMap;


/**
 * Similar to I2.2, I2.4 transports neurosymbols to F14. This time, they originate from F12.
 * 
 * @author deutsch
 * 18.05.2010, 14:16:46
 * 
 */
public interface I2_4_send {
	public void send_I2_4(HashMap<String, Double> poBodyData);
}
