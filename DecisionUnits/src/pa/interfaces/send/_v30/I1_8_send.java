/**
 * I1_8_send.java: DecisionUnits - pa.interfaces.send._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:32:13
 */
package pa.interfaces.send._v30;

import java.util.HashMap;

import pa.interfaces.I_BaseInterface;
import du.enums.eSensorIntType;
import du.itf.sensors.clsDataBase;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:32:13
 * 
 */
public interface I1_8_send   extends I_BaseInterface {
	public void send_I1_8(HashMap<eSensorIntType, clsDataBase> pnData);
}