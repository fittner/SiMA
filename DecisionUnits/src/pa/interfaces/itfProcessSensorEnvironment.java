/**
 * itfProcessSensorData.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 12:15:44
 */
package pa.interfaces;

import java.util.HashMap;

import decisionunit.itf.sensors.clsDataBase;
import enums.eSensorExtType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 12:15:44
 * 
 */
public interface itfProcessSensorEnvironment {
	public void receiveEnvironment(HashMap<eSensorExtType, clsDataBase> poData);
}
