/**
 * I1_6.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:10:42
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsDriveMesh;
 

/**
 * Transports (unchanged or adapted) drive contents which passed the defense mechanisms from F6 to the conversion module F8.
 * 
 * @author deutsch
 * 11.08.2009, 14:10:42
 * 
 */
public interface I5_18_receive {
	public void receive_I5_18(ArrayList<clsDriveMesh > poDriveList);
}
