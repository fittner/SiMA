/**
 * I2_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:16:25
 */
package pa.interfaces;

import java.util.HashMap;

import decisionunit.itf.sensors.clsDataBase;
import enums.eSensorExtType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:16:25
 * 
 */
public interface I2_1 {
	public void receive_I2_1(HashMap<eSensorExtType, clsDataBase> pnData);
}
