/**
 * I2_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:16:25
 */
package pa.interfaces.send._v19;

import java.util.HashMap;

import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;

/**
 * 
 * 
 * @author deutsch
 * 18.05.2010, 14:16:25
 * 
 */
public interface I2_1_send {
	public void send_I2_1(HashMap<eSensorExtType, clsSensorExtern> pnData);
}
