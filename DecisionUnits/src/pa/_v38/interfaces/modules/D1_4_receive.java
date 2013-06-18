/**
 * D1_1.java: DecisionUnits - pa.interfaces.send._v38
 * 
 * @author deutsch
 * 09.03.2011, 17:06:30
 */
package pa._v38.interfaces.modules;

import pa._v38.memorymgmt.enums.eDrive;
import pa._v38.tools.clsPair;



/**
 * Read access to libido storage 
 * 
 * @author deutsch
 * 09.03.2011, 17:06:30
 * 
 */
public interface D1_4_receive {
	public clsPair<Double,Double> receive_D1_4(eDrive oDrive);
}
