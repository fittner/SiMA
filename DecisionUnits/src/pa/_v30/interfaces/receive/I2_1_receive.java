/**
 * I2_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:16:25
 */
package pa._v30.interfaces.receive;

import java.util.HashMap;

import pa._v30.interfaces.I_BaseInterface;

import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:16:25
 * 
 */
public interface I2_1_receive extends I_BaseInterface {
	public void receive_I2_1(HashMap<eSensorExtType, clsSensorExtern> pnData);
}
