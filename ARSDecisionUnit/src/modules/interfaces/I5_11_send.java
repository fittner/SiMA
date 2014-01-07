/**
 * I3_2.java: DecisionUnits - pa.interfaces
 * 
 * @author deutsch
 * 18.05.2010, 14:35:43
 */
package modules.interfaces;

import java.util.ArrayList;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eEmotionType;
import base.datatypes.clsEmotion;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.helpstructures.clsPair;

/**
 * Superego bans and rules are transported from F7 to F19.
 * 
 * @author deutsch
 * 18.05.2010, 14:35:43
 * 
 */
public interface I5_11_send {
	public 
    void send_I5_11(ArrayList<clsPair<eContentType, String>> poForbiddenPerceptions, clsThingPresentationMesh poPerceptionalMesh,
            ArrayList<eEmotionType> poForbiddenEmotions, ArrayList<clsEmotion> poEmotions, clsWordPresentationMesh moWordingToContext2);
}
