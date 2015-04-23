/**
 * CHANGELOG
 *
 * 01.10.2012 wendt - File created
 *
 */
package secondaryprocess.functionality.decisionpreparation.consequencecodelets;

import java.util.ArrayList;

import secondaryprocess.functionality.decisionpreparation.clsCodelet;
import secondaryprocess.functionality.decisionpreparation.clsCodeletHandler;
import base.datatypes.clsWordPresentationMeshPossibleGoal;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 01.10.2012, 19:57:41
 * 
 */
public abstract class clsConsequenceCodelet extends clsCodelet {

	protected ArrayList<clsWordPresentationMeshPossibleGoal> moReachableGoalList;
	
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
