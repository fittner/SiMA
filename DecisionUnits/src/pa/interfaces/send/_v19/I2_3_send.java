/**
 * I2_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:16:39
 */
package pa.interfaces.send._v19;

import java.util.HashMap;

import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 18.05.2010, 14:16:39
 * 
 */
public interface I2_3_send {
	public void send_I2_3(HashMap<eSensorExtType, clsSensorExtern> pnData);
}
