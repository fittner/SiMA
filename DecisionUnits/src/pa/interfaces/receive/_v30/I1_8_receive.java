/**
 * I1_8_receive.java: DecisionUnits - pa.interfaces.receive._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:31:20
 */
package pa.interfaces.receive._v30;

import java.util.HashMap;

import pa.interfaces.I_BaseInterface;
import du.enums.eSensorIntType;
import du.itf.sensors.clsDataBase;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:31:20
 * 
 */
public interface I1_8_receive  extends I_BaseInterface {
	public void receive_I1_8(HashMap<eSensorIntType, clsDataBase> pnData);
}
