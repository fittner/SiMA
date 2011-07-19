/**
 * CHANGELOG
 *
 * 17.07.2011 perner - File created
 *
 */
package pa._v38.tools.planningHelpers;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsImage;
import pa._v38.memorymgmt.datatypes.clsPlanFragment;

/**
 * DOCUMENT (perner) - insert description 
 * 
 * @author perner
 * 17.07.2011, 12:11:29
 * 
 */
public class PlanningWizard {
	
	ArrayList<clsPlanFragment> m_availablePlans;
	
	
	/**
	 * DOCUMENT (perner) - initialized the planning-wizard with a set of available plans 
	 * @throws Exception 
	 *
	 * @since 17.07.2011 12:27:32
	 *
	 */
	@Deprecated
	public PlanningWizard(ArrayList<clsPlanFragment> plans) throws Exception {
		
		if (plans == null)
			throw new Exception("no plans available");
		
		m_availablePlans = plans; 
	}
	
	/**
	 * 
	 * DOCUMENT (perner) - finds a plan-fragment in the given set of member-plan-fragments based on a srcImage
	 *
	 * @since 17.07.2011 12:25:42
	 *
	 * @param srcImage
	 * @param availableImages
	 * @return
	 */
	static ArrayList<clsPlanFragment> findPlanFragmentsBasedOnDestImg(clsImage searchImage, ArrayList<clsPlanFragment> plans) {
		
		ArrayList<clsPlanFragment> possibleCandiates = new ArrayList<clsPlanFragment>();
		
		for (clsPlanFragment singlePlan : plans) {
			
			clsImage myPreImg = singlePlan.m_preconditionImage;
			clsImage myPostImg = singlePlan.m_effectImage;

			// TODO(perner) add deep and narrow search (e.g. to link turn left with a more specific image like object, direction and distance)
			if (myPreImg == searchImage)
				possibleCandiates.add(singlePlan);
		}
		
		return possibleCandiates;
	}
	
	
	
	
	/**
	 * 
	 * DOCUMENT (perner) - initialize the planning-graph engine with the connections between the plans
	 *
	 * @since 17.07.2011 14:11:11
	 *
	 * @param plans
	 * @param plGraph
	 * @return
	 * @throws Exception
	 */
	public static boolean initPlGraphWithPlConnections(ArrayList<clsPlanFragment> plans, PlanningGraph plGraph) throws Exception {
	
		if (plans == null)
			throw new Exception("no plans available");
		if (plGraph == null) 
			throw new Exception("no planning-graph available");
		
		for (clsPlanFragment plFragment : plans) {
	
			// get subsequent plans
			ArrayList<clsPlanFragment> subsequentPlans = PlanningWizard.findPlanFragmentsBasedOnDestImg(plFragment.m_effectImage, plans);
			
			// add connection for every plan with parent plan
			if (subsequentPlans != null) {
				for (clsPlanFragment subseqPlan : subsequentPlans ) {
					plGraph.connectNode(plFragment, subseqPlan);
				}
			}
			  
		}
		// not used till now
		return true;
	}
	
	/**
	 * 
	 * DOCUMENT (perner) - adds the existing action-nodes
	 *
	 * @since 17.07.2011 14:14:49
	 *
	 * @param plans
	 * @param plGraph
	 * @return
	 * @throws Exception
	 */
	public static boolean initPlGraphWithActions(ArrayList<clsPlanFragment> plans, PlanningGraph plGraph) throws Exception {
		
		if (plans == null)
			throw new Exception("no plans available");
		if (plGraph == null) 
			throw new Exception("no planning-graph available");
		
		for (clsPlanFragment singlePlan : plans)
			plGraph.addPlanningNode(singlePlan);
		
		return true;
	}

}
