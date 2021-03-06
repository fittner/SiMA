/**
 * D1_1.java: DecisionUnits - pa.interfaces.send._v38
 * 
 * @author deutsch
 * 09.03.2011, 17:06:30
 */
package modules.interfaces;

import memorymgmt.enums.eDrive;
import base.datatypes.helpstructures.clsPair;





/**
 * Read access to libido storage 
 * 
 * @author deutsch
 * 09.03.2011, 17:06:30
 * 
 */
public interface D1_4_send {
    public clsPair<Double,Double> send_D1_4(eDrive oDrive);
}
