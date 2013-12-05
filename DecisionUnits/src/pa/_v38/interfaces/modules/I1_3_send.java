/**
 * I2_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:16:25
 */
package pa._v38.interfaces.modules;

import java.util.HashMap;

import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;

/**
 * Connection of a bodily module to its neurosymbolization module. F10->F11
 * 
 * @author deutsch
 * 18.05.2010, 14:16:25
 * 
 */
public interface I1_3_send {
	public void send_I1_3(HashMap<eSensorExtType, clsSensorExtern> pnData);
}
