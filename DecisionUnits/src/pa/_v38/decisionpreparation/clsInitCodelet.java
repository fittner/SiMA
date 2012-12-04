/**
 * CHANGELOG
 *
 * 01.10.2012 wendt - File created
 *
 */
package pa._v38.decisionpreparation;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 01.10.2012, 19:57:18
 * 
 */
public abstract class clsInitCodelet extends clsCodelet {
	
	protected ArrayList<clsWordPresentationMesh> moReachableGoalList;

	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 01.10.2012 19:58:10
	 *
	 * @param poCodeletHandler
	 */
	public clsInitCodelet(clsCodeletHandler poCodeletHandler) {
		super(poCodeletHandler);
		
		moReachableGoalList = this.moCodeletHandler.getGoalListFromF51();
		
	}



}
