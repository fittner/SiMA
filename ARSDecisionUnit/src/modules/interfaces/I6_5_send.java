/**
 * I5_3.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:37:21
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsWordPresentationMeshAimOfDrive;

/**
 * Drive wishes are transported from F8 to F20. The contents are in the form of word presentations, thing presentations, and affects.
 * 
 * @author deutsch
 * 18.05.2010, 14:37:21
 * 
 */
public interface I6_5_send {
	public void send_I6_5(ArrayList<clsWordPresentationMeshAimOfDrive> poDriveList);
}
