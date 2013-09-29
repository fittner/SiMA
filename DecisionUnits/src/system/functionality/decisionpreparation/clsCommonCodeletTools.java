/**
 * CHANGELOG
 *
 * 22.09.2012 wendt - File created
 *
 */
package system.functionality.decisionpreparation;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;
import system.datamanipulation.clsMentalSituationTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 22.09.2012, 18:05:27
 * 
 */
public class clsCommonCodeletTools {
	public static clsWordPresentationMeshGoal getPreviousGoalFromShortTermMemory(clsShortTermMemory poMem) {
		return clsMentalSituationTools.getGoal(poMem.findPreviousSingleMemory());
			
	}
	
}
