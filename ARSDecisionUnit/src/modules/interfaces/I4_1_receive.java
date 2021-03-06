/**
 * I2_18_receive.java: DecisionUnits - pa.interfaces.receive._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:35:28
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsDriveMesh;

/**
 * Drive Candidates - vector of quota of affects are forwarded from F48 to F57
 * 
 * @author deutsch
 * 03.03.2011, 15:35:28
 * 
 */
public interface I4_1_receive  {
	public void receive_I4_1(ArrayList<clsDriveMesh> poDriveComponents);
}

