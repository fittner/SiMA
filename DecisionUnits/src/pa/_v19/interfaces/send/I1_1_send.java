/**
 * I1_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 12:13:01
 */
package pa._v19.interfaces.send;

import java.util.HashMap;

import du.enums.eSensorIntType;
import du.itf.sensors.clsDataBase;

/**
 * 
 * 
 * @author deutsch
 * 18.05.2010, 12:13:01
 * 
 */
@Deprecated
public interface I1_1_send {
	public void send_I1_1(HashMap<eSensorIntType, clsDataBase> pnData);
}