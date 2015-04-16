/**
  * I3_1.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 11.08.2009, 14:05:44
 */
package modules.interfaces;

import java.util.ArrayList;

import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import primaryprocess.functionality.superegofunctionality.clsSuperEgoConflictDrive;



/**
 * Superego bans and rules are transported from F7 to F6.
 * 
 * @author deutsch
 * 11.08.2009, 14:05:44
 * 
 */
public interface I5_13_receive {
	public void receive_I5_13(ArrayList<clsSuperEgoConflictDrive> poForbiddenDrive, ArrayList<clsDriveMesh> poData,ArrayList<clsEmotion> poEmotions);
}
