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
 * Write access to libido storage
 * Increases both drive components
 * 1. aggressive
 * 2. libidinous
 * 
 * @author herret
 * 15.5.2013
 * 
 */
public interface D1_2_receive {
    public void receive_D1_2(eDrive peType, clsPair<Double,Double> oValues);
}
