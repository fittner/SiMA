/**
 * I2_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:16:25
 */
package pa._v30.interfaces.modules;

import java.util.HashMap;



import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;

/**
 *
 * 
 * @author deutsch
 * 11.08.2009, 14:16:25
 * 
 */
public interface I2_1_receive {
	public void receive_I2_1(HashMap<eSensorExtType, clsSensorExtern> pnData);
}
