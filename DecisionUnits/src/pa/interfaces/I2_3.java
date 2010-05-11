/**
 * I2_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:16:39
 */
package pa.interfaces;

import java.util.HashMap;

import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:16:39
 * 
 */
public interface I2_3 {
	public void receive_I2_3(HashMap<eSensorExtType, clsSensorExtern> pnData);
}
