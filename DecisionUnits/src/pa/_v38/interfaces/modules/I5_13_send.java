/**
 * I3_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:05:44
 */
package pa._v38.interfaces.modules;

import java.util.ArrayList;

import du.enums.eOrgan;
import du.enums.pa.eDriveComponent;

import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsEmotion;
import pa._v38.memorymgmt.enums.eEmotionType;
//import pa._v38.modules.clsEmotion;
import pa._v38.tools.clsPair;
//import pa._v38.memorymgmt.datatypes.clsEmotion;


/**
 * Superego bans and rules are transported from F7 to F6.
 * 
 * @author deutsch
 * 18.05.2010, 14:05:44
 * 
 */
public interface I5_13_send {
	public void send_I5_13(ArrayList<clsPair<eDriveComponent, eOrgan>> poForbiddenDrive, ArrayList<clsDriveMesh> poData,ArrayList<eEmotionType> poForbiddenEmotions,ArrayList<clsEmotion> poEmotions);
}
