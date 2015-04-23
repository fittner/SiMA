/**
 * I3_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:05:44
 */
package modules.interfaces;

import java.util.ArrayList;

import primaryprocess.functionality.superegofunctionality.clsSuperEgoConflictDrive;
import primaryprocess.functionality.superegofunctionality.clsSuperEgoConflictEmotion;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;


/**
 * Superego bans and rules are transported from F7 to F6.
 * 
 * @author deutsch
 * 18.05.2010, 14:05:44
 * 
 */
public interface I5_13_send {
	public void send_I5_13(ArrayList<clsSuperEgoConflictDrive> poForbiddenDrive, ArrayList<clsDriveMesh> poData,ArrayList<clsSuperEgoConflictEmotion> poForbiddenEmotions,ArrayList<clsEmotion> poEmotions);
}
