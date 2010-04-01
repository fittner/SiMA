/**
 * itfProcessSensorBody.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 15:49:15
 */
package pa.interfaces;

import java.util.HashMap;

import decisionunit.itf.sensors.clsSensorExtern;
import enums.eSensorExtType;

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
