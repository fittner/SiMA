/**
 * I1_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 12:13:01
 */
package pa._v38.interfaces.modules;

import java.util.HashMap;

import du.enums.eSensorIntType;
import du.itf.sensors.clsDataBase;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 18.05.2010, 12:13:01
 * 
 */
public interface I1_2_send {
	public void send_I1_2(HashMap<eSensorIntType, clsDataBase> pnData);
}
