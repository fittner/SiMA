/**
 * CHANGELOG
 *
 * 12.10.2011 zottl		- Changed parameter of receive_D3_1 from int to double
 * 										- Documented receive_D3_1
 * 14.07.2011 deutsch - File created
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsDriveMesh;

/**
 * stores pleasure of the system in DT4
 * 
 * @author muchitsch
 * 14.07.2011, 15:58:23
 * 
 */
public interface D4_1_receive {
	
	
	
	public void receive_D4_1 (ArrayList<clsDriveMesh> poActualDriveCandidates);
}
