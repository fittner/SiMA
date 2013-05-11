/**
 * CHANGELOG
 *
 * 03.07.2011 perner - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

/**
 * DOCUMENT (perner) - representation of an action plan <BR> 
 * holds several PlanFragments 
 * 
 * @author perner
 * 03.07.2011, 15:52:27
 * 
 */
public class clsActionPlan {
	
	private ArrayList<clsPlanFragment> m_listPlanFragments;
	
	/**
	 * DOCUMENT (perner) - insert description 
	 *
	 * @since 03.07.2011 15:52:33
	 *
	 */
	public clsActionPlan() {
		// TODO (perner) - Auto-generated constructor stub
	}

	public clsActionPlan(ArrayList<clsPlanFragment> planFr) {
		m_listPlanFragments = planFr;
	}

}
