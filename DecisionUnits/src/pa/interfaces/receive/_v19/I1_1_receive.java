/**
 * I1_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 12:13:01
 */
package pa.interfaces.receive._v19;

import java.util.HashMap;

import pa.interfaces.I_BaseInterface;

import du.enums.eSensorIntType;
import du.itf.sensors.clsDataBase;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 12:13:01
 * 
 */
@Deprecated
public interface I1_1_receive extends I_BaseInterface {
	public void receive_I1_1(HashMap<eSensorIntType, clsDataBase> pnData);
}
