/**
 * I2_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:16:39
 */
package pa._v38.interfaces.modules;

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
public interface I1_4_send {
	public void send_I1_4(HashMap<eSensorExtType, clsSensorExtern> pnData);
}
