/**
 * I2_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:16:25
 */
package pa._v30.interfaces.send;

import java.util.HashMap;

import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 18.05.2010, 14:16:25
 * 
 */
public interface I2_1_send {
	public void send_I2_1(HashMap<eSensorExtType, clsSensorExtern> pnData);
}
