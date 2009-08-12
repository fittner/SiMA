/**
 * itfProcessHomeostases.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 15:49:46
 */
package pa.interfaces;

import java.util.HashMap;

import decisionunit.itf.sensors.clsDataBase;
import enums.eSensorIntType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 15:49:46
 * 
 */
public interface itfProcessHomeostases {
	public void receiveHomeostases(HashMap<eSensorIntType, clsDataBase> poData);
}
