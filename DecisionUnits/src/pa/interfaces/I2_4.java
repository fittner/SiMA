/**
 * I2_4.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:16:46
 */
package pa.interfaces;

import java.util.HashMap;

import decisionunit.itf.sensors.clsSensorExtern;
import enums.eSensorExtType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:16:46
 * 
 */
public interface I2_4 {
	public void receive_I2_4(HashMap<eSensorExtType, clsSensorExtern> poBodyData);
}
