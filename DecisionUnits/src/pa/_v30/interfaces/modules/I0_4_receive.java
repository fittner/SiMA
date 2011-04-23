/**
 * I0_4_receive.java: DecisionUnits - pa.interfaces.receive._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:29:38
 */
package pa._v30.interfaces.modules;

import java.util.HashMap;
import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;



/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:29:38
 * 
 */
public interface I0_4_receive  {
	public void receive_I0_4(HashMap<eSensorExtType, clsSensorExtern> poData);
}