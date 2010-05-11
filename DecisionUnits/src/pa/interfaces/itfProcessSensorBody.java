/**
 * itfProcessSensorBody.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 15:49:15
 */
package pa.interfaces;

import java.util.HashMap;

import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 15:49:15
 * 
 */
public interface itfProcessSensorBody {
	public void receiveBody(HashMap<eSensorExtType, clsSensorExtern> poData);
}
