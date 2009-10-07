/**
 * I1_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 12:19:57
 */
package pa.interfaces;

import java.util.HashMap;

import decisionunit.itf.sensors.clsDataBase;
import enums.eSensorIntType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 12:19:57
 * 
 */
public interface I1_2 {
	public void receive_I1_2(HashMap<eSensorIntType, clsDataBase> poHomeostasisSymbols);
}
