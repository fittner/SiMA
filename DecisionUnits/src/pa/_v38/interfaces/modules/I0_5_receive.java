/**
 * I0_5_receive.java: DecisionUnits - pa.interfaces.receive._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:30:03
 */
package pa._v38.interfaces.modules;

import java.util.HashMap;
import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;



/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:30:03
 * 
 */
public interface I0_5_receive  {
	public void receive_I0_5(HashMap<eSensorExtType, clsSensorExtern> poData);
}