/**
 * CHANGELOG
 *
 * 17.07.2011 perner - File created
 *
 */
package pa._v38.tools.planningHelpers;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
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
		
		// run through plans and see which plans can be connected
		for (clsPlanFragment singlePlan : plans) {
			
//			if (myPreImg == searchImage)
			if (compareImagesLoose(singlePlan.m_preconditionImage, searchImage))
				possibleCandiates.add(singlePlan);
		}
		
		return possibleCandiates;
	}

	/**
	 * 
	 * DOCUMENT (perner) - static wrapper to compare two images (strict)
	 *
	 * @since 20.07.2011 19:22:38
	 *
	 * @param A
	 * @param B
	 * @return
	 */
	public static boolean compareImagesStrict(clsImage A, clsImage B) {
		return A.isEqualStrictTo(B);		
	}
	

	/**
	 * 
	 * DOCUMENT (perner) - static wrapper to compare two images (loose)
	 *
	 * @since 20.07.2011 19:22:38
	 *
	 * @param A
	 * @param B
	 * @return
	 */
	public static boolean compareImagesLoose(clsImage A, clsImage B) {
		return A.isEqualLooseTo(B);		
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

	
	/**
	 * 
	 * DOCUMENT (perner) - reduces the set of plans to the plans which can be applied to the current environmental situation
	 *
	 * @since 24.07.2011 12:19:08
	 *
	 * @param availablePlans all available plans
	 * @param currentEnvironmentalSituation snapshot of the current environmental situation
	 * @return set of plans that can be applied right now
	 */
	public static ArrayList<clsPlanFragment> getCurrentApplicablePlanningNodes(ArrayList<clsPlanFragment> availableFragments, clsImage currentEnvironmentalSituation) {
	
		ArrayList<clsPlanFragment> applicablePlanFragments = new ArrayList<clsPlanFragment>();
		
		// run through plan fragments and see if a plan fragment can be applied right now
		for (clsPlanFragment myFrag : availableFragments) {
			
			if (myFrag.m_preconditionImage.isEqualLooseTo(currentEnvironmentalSituation))
				applicablePlanFragments.add(myFrag);
			
		}
		return applicablePlanFragments;
	}
	
	public static clsImage getCurrentEnvironmentalImage(ArrayList<clsAssociation> perception) {
		eDistance dist;
		
		for (clsAssociation perceptionItem : perception) {
			
			if (perceptionItem instanceof clsAssociationSecondary) {
				
				clsAssociationSecondary myElem = (clsAssociationSecondary) perceptionItem;
				
				clsDataStructurePA el = myElem.getRootElement();
				String str = el.toString();
				
				int pos1 = str.indexOf("ENTITY:");
				int pos2 = str.indexOf("LOCATION:");
				int pos3 = str.toString().indexOf("|", pos2);
				
				
				if (pos1 >= 0 && pos2 >0 && pos3 > 0 && pos3 < str.length()) {
					String entity = str.substring(pos1+7, pos2-1);
					String location = str.substring(pos2+9, pos3);
					
//					if (entity != null && entity.length() > 0)
//						dist = eDistance.valueOf(entity);
					
					int l = 3;
				
				} else {
					return null;
				}
				
				int i = 3;
				
			}
		}
		
		int i = 0;
		
		
		//clsImage currentEnvironmentalSituation = new clsImage();
		
		
		return null; 
	}
}
