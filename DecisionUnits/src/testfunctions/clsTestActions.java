/**
 * CHANGELOG
 *
 * 06.02.2013 wendt - File created
 *
 */
package testfunctions;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eAction;
import secondaryprocess.datamanipulation.clsActionTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 06.02.2013, 16:47:50
 * 
 */
public class clsTestActions {
	
	public static void replaceAction (ArrayList<clsWordPresentationMesh> poCurrentActionList, eAction poTestAction) {
		poCurrentActionList.clear();
		
		clsWordPresentationMesh oTestAction = clsActionTools.createAction(poTestAction);
		poCurrentActionList.add(oTestAction);
	}
}
