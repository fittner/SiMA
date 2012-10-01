/**
 * CHANGELOG
 *
 * 22.09.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.storage.clsShortTermMemory;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 22.09.2012, 17:05:50
 * 
 */
public abstract class clsDecisionCodelet extends clsCodelet {

	protected ArrayList<clsWordPresentationMesh> moReachableGoalList;
	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 22.09.2012 17:15:57
	 *
	 * @param poEnvironmentalImage
	 * @param poShortTermMemory
	 */
	public clsDecisionCodelet(clsWordPresentationMesh poEnvironmentalImage, clsShortTermMemory poShortTermMemory, ArrayList<clsWordPresentationMesh> poReachableGoalList, clsCodeletHandler poCodeletHandler) {
		super(poEnvironmentalImage, poShortTermMemory, poCodeletHandler);

		moReachableGoalList = poReachableGoalList;
	}

}
