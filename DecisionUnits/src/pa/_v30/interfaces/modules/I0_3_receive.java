/**
 * I0_3_receive.java: DecisionUnits - pa.interfaces.receive._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:28:47
 */
package pa._v30.interfaces.modules;

import java.util.HashMap;
import du.enums.eSensorIntType;
import du.itf.sensors.clsDataBase;



/**
 *
 * 
 * @author deutsch
 * 03.03.2011, 15:28:47
 * 
 */
public interface I0_3_receive  {
	public void receive_I0_3(HashMap<eSensorIntType, clsDataBase> poData);
}