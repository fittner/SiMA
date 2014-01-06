/**
 * I1_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 12:13:01
 */
package modules.interfaces;

import java.util.HashMap;



import du.enums.eSensorIntType;
import du.itf.sensors.clsDataBase;

/**
 * Connection of a bodily module to its neurosymbolization module. F1->F2
 * 
 * @author deutsch
 * 11.08.2009, 12:13:01
 * 
 */
public interface I1_2_receive {
	public void receive_I1_2(HashMap<eSensorIntType, clsDataBase> pnData);
}