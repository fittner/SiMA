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
 * Write access to libido storage
 * decrease both drive components
 * 1. aggressiv
 * 2. libidinous
 * 
 * @author herret
 * 15.5.2013
 * 
 */
public interface D1_3_send {
    public void send_D1_3(eDrive peType, clsPair<Double,Double> oValues);
}
