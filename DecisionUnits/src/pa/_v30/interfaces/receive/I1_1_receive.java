/**
 * I1_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 12:13:01
 */
package pa._v30.interfaces.receive;

import java.util.HashMap;

import pa._v30.interfaces.I_BaseInterface;

import du.enums.eSensorIntType;
import du.itf.sensors.clsDataBase;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 12:13:01
 * 
 */
public interface I1_1_receive extends I_BaseInterface {
	public void receive_I1_1(HashMap<eSensorIntType, clsDataBase> pnData);
}
