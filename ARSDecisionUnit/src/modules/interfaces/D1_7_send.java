/**
 * CHANGELOG
 *
 * 12.11.2013 herret - File created
 *
 */
package modules.interfaces;

import memorymgmt.enums.eDrive;
import base.datatypes.helpstructures.clsPair;

/**
 * Returns true if the drive was satisfied in this circle
 * 
 */
public interface D1_7_send {
        public clsPair<Boolean,Boolean> send_D1_7(eDrive peDrive);

}
