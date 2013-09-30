/**
 * CHANGELOG
 *
 * 01.10.2012 wendt - File created
 *
 */
package system.functionality.decisionpreparation.consequencecodelets;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import system.functionality.decisionpreparation.clsCodelet;
import system.functionality.decisionpreparation.clsCodeletHandler;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 01.10.2012, 19:57:41
 * 
 */
public abstract class clsConsequenceCodelet extends clsCodelet {

	protected ArrayList<clsWordPresentationMeshGoal> moReachableGoalList;
	
	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 01.10.2012 19:58:16
	 *
	 * @param poCodeletHandler
	 */
	public clsConsequenceCodelet(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
		
		moReachableGoalList = this.moCodeletHandler.getGoalListFromF51();
	}

	
}
