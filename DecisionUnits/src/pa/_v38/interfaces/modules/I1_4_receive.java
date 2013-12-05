/**
 * I2_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:16:39
 */
package pa._v38.interfaces.modules;

import java.util.HashMap;



import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;

/**
 * Connection of a bodily module to its neurosymbolization module. F12->F13
 * 
 * @author deutsch
 * 11.08.2009, 14:16:39
 * 
 */
public interface I1_4_receive {
	public void receive_I1_4(HashMap<eSensorExtType, clsSensorExtern> pnData);
}
