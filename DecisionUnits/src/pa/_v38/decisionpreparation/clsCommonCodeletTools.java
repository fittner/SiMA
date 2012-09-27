/**
 * CHANGELOG
 *
 * 22.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.storage.clsShortTermMemory;
import pa._v38.tools.clsMentalSituationTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 22.09.2012, 18:05:27
 * 
 */
public class clsCommonCodeletTools {
	public static clsWordPresentationMesh getPreviousGoalFromShortTermMemory(clsShortTermMemory poMem) {
		return clsMentalSituationTools.getGoal(poMem.findPreviousSingleMemory());
			
	}
	
}
