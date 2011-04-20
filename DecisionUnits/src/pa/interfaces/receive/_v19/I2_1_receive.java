/**
 * I2_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:16:25
 */
package pa.interfaces.receive._v19;

import java.util.HashMap;

import pa.interfaces.I_BaseInterface;

import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:16:25
 * 
 */
@Deprecated
public interface I2_1_receive extends I_BaseInterface {
	public void receive_I2_1(HashMap<eSensorExtType, clsSensorExtern> pnData);
}
