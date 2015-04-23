/**
 * I3_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:35:43
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsDriveMesh;



/**
 * Selected drive content is forwarded from F55 to F6
 * 
 * @author deutsch
 * 11.08.2009, 14:35:43
 * 
 */
public interface I5_5_receive {
	public void receive_I5_5(ArrayList<clsDriveMesh> poData );
}
