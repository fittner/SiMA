/**
 * I1_8_receive.java: DecisionUnits - pa.interfaces.receive._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:31:20
 */
package pa._v38.interfaces.modules;

import java.util.HashMap;

import du.enums.eSensorIntType;
import du.itf.sensors.clsDataBase;



/**
 * Connection of a bodily module to its neurosymbolization module. F39->F40
 * 
 * @author deutsch
 * 03.03.2011, 15:31:20
 * 
 */
public interface I1_1_receive  {
	public void receive_I1_1(double prLibido, HashMap<eSensorIntType, clsDataBase> poData);
}
