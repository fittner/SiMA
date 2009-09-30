/**
 * I2_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:16:31
 */
package pa.interfaces;

import java.util.HashMap;

import decisionunit.itf.sensors.clsSensorExtern;
import enums.eSensorExtType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:16:31
 * 
 */
public interface I2_2 {
	public void receive_I2_2(HashMap<eSensorExtType, clsSensorExtern> poEnvironmentalData);
}
