/**
 * I0_2_receive.java: DecisionUnits - pa.interfaces.receive._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:28:33
 */
package modules.interfaces;

import java.util.HashMap;

import du.enums.eSensorIntType;
import du.itf.sensors.clsDataBase;



/**
 * The second incoming connection to F39 originates in the erogenous zones.
 * 
 * @author deutsch
 * 03.03.2011, 15:28:33
 * 
 */
public interface I0_2_receive  {
	public void receive_I0_2(HashMap<eSensorIntType, clsDataBase> poData);
}