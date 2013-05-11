/**
 * I1_8_send.java: DecisionUnits - pa.interfaces.send._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:32:13
 */
package pa._v38.interfaces.modules;

import java.util.HashMap;

import du.enums.eSensorIntType;
import du.itf.sensors.clsDataBase;



/**
 * Connection of a bodily module to its neurosymbolization module. F39->F40
 *
 * 
 * @author deutsch
 * 03.03.2011, 15:32:13
 * 
 */
public interface I1_1_send   {
	public void send_I1_1(double prLibido, HashMap<eSensorIntType, clsDataBase> poData);
}