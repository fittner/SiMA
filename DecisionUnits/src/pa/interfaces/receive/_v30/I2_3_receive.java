/**
 * I2_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:16:39
 */
package pa.interfaces.receive._v30;

import java.util.HashMap;

import pa.interfaces.I_BaseInterface;

import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:16:39
 * 
 */
public interface I2_3_receive extends I_BaseInterface {
	public void receive_I2_3(HashMap<eSensorExtType, clsSensorExtern> pnData);
}
