/**
 * I1_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 12:13:01
 */
package pa._v30.interfaces.modules;

import java.util.HashMap;



import du.enums.eSensorIntType;
import du.itf.sensors.clsDataBase;

/**
 *
 * 
 * @author deutsch
 * 11.08.2009, 12:13:01
 * 
 */
public interface I1_1_receive {
	public void receive_I1_1(HashMap<eSensorIntType, clsDataBase> pnData);
}
