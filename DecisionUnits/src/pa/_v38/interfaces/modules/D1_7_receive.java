/**
 * CHANGELOG
 *
 * 12.11.2013 herret - File created
 *
 */
package pa._v38.interfaces.modules;

import pa._v38.memorymgmt.enums.eDrive;
import datatypes.helpstructures.clsPair;

/**
 * Returns true if the drive was satisfied in this circle
 * 
 */
public interface D1_7_receive {
    public clsPair<Boolean,Boolean> receive_D1_7(eDrive peDrive);

}
