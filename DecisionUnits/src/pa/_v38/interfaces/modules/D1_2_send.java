/**
 * D1_1.java: DecisionUnits - pa.interfaces.send._v38
 * 
 * @author deutsch
 * 09.03.2011, 17:06:30
 */
package pa._v38.interfaces.modules;


import datatypes.helpstructures.clsPair;
import pa._v38.memorymgmt.enums.eDrive;



/**
 * Write access to libido storage
 * Increses both drive components
 * 1. aggressiv
 * 2. libidinous
 * 
 * @author herret
 * 15.5.2013
 * 
 */
public interface D1_2_send {
    public void send_D1_2(eDrive peType, clsPair<Double,Double> oValues);
}
