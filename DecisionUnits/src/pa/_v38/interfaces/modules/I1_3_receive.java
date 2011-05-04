/**
 * I2_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:16:25
 */
package pa._v38.interfaces.modules;

import java.util.HashMap;



import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:16:25
 * 
 */
public interface I1_3_receive {
	public void receive_I1_3(HashMap<eSensorExtType, clsSensorExtern> pnData);
}
