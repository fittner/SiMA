/**
 * D1_1.java: DecisionUnits - pa.interfaces.send._v38
 * 
 * @author deutsch
 * 09.03.2011, 17:06:30
 */
package pa._v38.interfaces.modules;

import pa._v38.memorymgmt.enums.eSexualDrives;



/**
 * Write access to libido storage (from F41).
 * 
 * @author deutsch
 * 09.03.2011, 17:06:30
 * 
 */
public interface D1_1_receive {

	public void receive_D1_1(eSexualDrives peType, double prValue);
}
