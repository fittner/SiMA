/**
 * I2_18_send.java: DecisionUnits - pa.interfaces.send._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:36:22
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsDriveMesh;

/**
 * Drive Candidates - vector of quota of affects are forwarded from F48 to F57
 * 
 * @author zeilinger
 * 03.03.2011, 15:36:22
 * 
 */
public interface I4_1_send {
	public void send_I4_1(ArrayList<clsDriveMesh> poDriveComponents);
}

