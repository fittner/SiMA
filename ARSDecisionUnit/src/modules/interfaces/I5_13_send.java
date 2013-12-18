/**
 * I3_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:05:44
 */
package modules.interfaces;

import java.util.ArrayList;

import memorymgmt.enums.eEmotionType;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import primaryprocess.functionality.superegofunctionality.clsSuperEgoConflict;


/**
 * Superego bans and rules are transported from F7 to F6.
 * 
 * @author deutsch
 * 18.05.2010, 14:05:44
 * 
 */
public interface I5_13_send {
	public void send_I5_13(ArrayList<clsSuperEgoConflict> poForbiddenDrive, ArrayList<clsDriveMesh> poData,ArrayList<eEmotionType> poForbiddenEmotions,ArrayList<clsEmotion> poEmotions);
}
