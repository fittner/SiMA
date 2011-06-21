/**
 * itfProcessHomeostases.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 15:49:46
 */
package pa._v19.interfaces;

import java.util.HashMap;

import du.enums.eSensorIntType;
import du.itf.sensors.clsDataBase;

/**
 * 
 * @author deutsch
 * 11.08.2009, 15:49:46
 * 
 */
@Deprecated
public interface itfProcessHomeostases {
	public void receiveHomeostases(HashMap<eSensorIntType, clsDataBase> poData);
}
