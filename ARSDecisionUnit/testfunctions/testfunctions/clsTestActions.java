/**
 * CHANGELOG
 *
 * 06.02.2013 wendt - File created
 *
 */
package testfunctions;

import java.util.ArrayList;

import memorymgmt.enums.eAction;
import secondaryprocess.datamanipulation.clsActionTools;
import base.datatypes.clsWordPresentationMesh;

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
