/**
 * I1_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 12:13:01
 */
package pa.interfaces;

import java.util.HashMap;

import decisionunit.itf.sensors.clsDataBase;
import enums.eSensorIntType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 12:13:01
 * 
 */
public interface I1_1 {
	public void receive_I1_1(HashMap<eSensorIntType, clsDataBase> pnData);
}
