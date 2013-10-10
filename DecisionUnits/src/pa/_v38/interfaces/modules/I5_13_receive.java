/**
 * I3_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:05:44
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;
//import pa._v38.memorymgmt.datatypes.clsEmotion;


import datatypes.helpstructures.clsPair;
import du.enums.eOrgan;
import du.enums.pa.eDriveComponent;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsEmotion;
//import pa._v38.memorymgmt.enums.eEmotionType;



/**
 * Superego bans and rules are transported from F7 to F6.
 * 
 * @author deutsch
 * 11.08.2009, 14:05:44
 * 
 */
public interface I5_13_receive {
	public void receive_I5_13(ArrayList<clsPair<eDriveComponent, eOrgan>> poForbiddenDrive, ArrayList<clsDriveMesh> poData,ArrayList<clsEmotion> poEmotions);
}
