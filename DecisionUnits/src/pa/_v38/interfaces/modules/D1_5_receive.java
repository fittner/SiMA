/**
 * CHANGELOG
 *
 * Apr 2, 2013 herret - File created
 *
 */
package pa._v38.interfaces.modules;

import java.util.HashMap;

import pa._v38.memorymgmt.enums.eDrive;
import pa._v38.tools.clsPair;

/**
 * read access to drive storage
 * get all drive storage values
 * 
 * @author herret
 * Apr 2, 2013, 4:02:47 PM
 * 
 */
public interface D1_5_receive {
	public HashMap<eDrive,clsPair<Double,Double>> receive_D1_5();
}
