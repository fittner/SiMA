/**
 * CHANGELOG
 *
 * Apr 2, 2013 herret - File created
 *
 */
package modules.interfaces;

import memorymgmt.enums.eDrive;


/**
 * write access to drive storage
 * shift drive components
 * 
 * @author herret
 * Apr 2, 2013, 4:02:47 PM
 * 
 */
public interface D1_6_send {
    public void send_D1_6(eDrive oDrive, Double oShiftFactor);
}
