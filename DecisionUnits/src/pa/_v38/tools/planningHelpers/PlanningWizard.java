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
import pa._v38.memorymgmt.datatypes.clsImage;
import pa._v38.memorymgmt.datatypes.clsPlan;
import pa._v38.memorymgmt.datatypes.clsPlanFragment;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;

/**
 * DOCUMENT (perner) - insert description
 * 
 * @author perner 17.07.2011, 12:11:29
 * 
 */
public class PlanningWizard {

	ArrayList<clsPlanFragment> m_availablePlans;

	/**
	 * DOCUMENT (perner) - initialized the planning-wizard with a set of available plans
	 * 
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

			/** do a strict compare in case of eat actions */
			if (singlePlan.planLabel.contains("EAT")) {
				if (compareImagesStrict(singlePlan.m_preconditionImage, searchImage)) {
					possibleCandiates.add(singlePlan);
				}
			}

			/** do loose compare by default */
			else if (compareImagesLoose(singlePlan.m_preconditionImage, searchImage)) {
				possibleCandiates.add(singlePlan);
			}
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

			ArrayList<clsPlanFragment> subsequentPlans = PlanningWizard.findPlanFragmentsBasedOnDestImg(plFragment.m_effectImage, plans);

			/** print plans */
			System.out.println("PlanningWizard::initPlGraphWithPlConnections: after plan-fragment >" + plFragment
			    + "< the following plan fragments can be executed");
			for (clsPlanFragment possiblePlans : subsequentPlans) {
				System.out.println("  PlanningWizard::initPlGraphWithPlConnections: " + possiblePlans);
			}

			// add connection for every plan with parent plan
			if (subsequentPlans != null) {
				for (clsPlanFragment subseqPlan : subsequentPlans) {
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
	 * @param availablePlans
	 *          all available plans
	 * @param currentEnvironmentalSituation
	 *          snapshot of the current environmental situation
	 * @return set of plans that can be applied right now
	 */
	public static ArrayList<clsPlanFragment> getCurrentApplicablePlanningNodes(ArrayList<clsPlanFragment> availableFragments,
	    ArrayList<clsImage> currentEnvironmentalSituation) {

		ArrayList<clsPlanFragment> applicablePlanFragments = new ArrayList<clsPlanFragment>();

		// run through plan fragments and see if a plan fragment can be applied right now
		for (clsPlanFragment myFrag : availableFragments) {
			for (clsImage oImage : currentEnvironmentalSituation)
				if (myFrag.m_preconditionImage.isEqualLooseTo(oImage)) {
					applicablePlanFragments.add(myFrag);
				}
		}

		return applicablePlanFragments;
	}

	/**
	 * 
	 * DOCUMENT (perner) - extracts object and its position out of the string
	 * ::WP::-1:WP:ENTITY:CAKE|LOCATION:MEDIUMMIDDLELEFT|BITE:LOWPOSITIVE|NOURISH:LOWPOSITIVE|
	 * 
	 * @since 09.09.2011 08:47:58
	 * 
	 * @param perception
	 *          the image which can be used as current start image for planning
	 * @return
	 */
	public static ArrayList<clsImage> getCurrentEnvironmentalImage(ArrayList<clsAssociation> perception) {

		ArrayList<clsImage> oRetVal = new ArrayList<clsImage>();

		for (clsAssociation perceptionItem : perception) {

			if (perceptionItem instanceof clsAssociationSecondary) {

				clsAssociationSecondary myElem = (clsAssociationSecondary) perceptionItem;

				clsWordPresentationMesh el = (clsWordPresentationMesh) myElem.getLeafElement();

				// Extract object name and positions
				String oName = el.getMoContent();
				String oDistance = "";
				String oPosition = "";

				for (clsAssociation oAss : el.getExternalAssociatedContent()) {
					if (oAss.getLeafElement().getMoContentType().equals(eContentType.DISTANCE.toString())) {
						if (((clsWordPresentation) oAss.getLeafElement()).getMoContent().equals("MANIPULATEABLE")
						    || (((clsWordPresentation) oAss.getLeafElement()).getMoContent().equals("EATABLE"))) {
							oDistance = "NEAR";
							oPosition = "CENTER";
						} else {
							oDistance = ((clsWordPresentation) oAss.getLeafElement()).getMoContent();
						}
					} else if (oAss.getLeafElement().getMoContentType().equals(eContentType.POSITION.toString())) {
						oPosition = ((clsWordPresentation) oAss.getLeafElement()).getMoContent();
					}
				}

				// System.out.println("oDistance: " + oDistance + ", oDirection " + oPosition);
				oRetVal.add(new clsImage(eDistance.valueOf(oDistance), eDirection.valueOf(oPosition), eEntity.valueOf(oName)));

				// @ANDI: No more parsing...
				// String str = el.toString();
				//
				// int pos1 = str.indexOf("ENTITY:");
				// int pos2 = str.indexOf("LOCATION:");
				// int pos3 = str.toString().indexOf("|", pos2);
				//
				//
				// if (pos1 >= 0 && pos2 >0 && pos3 > 0 && pos3 < str.length()) {
				// String strEntity = str.substring(pos1+7, pos2-1);
				// String strLocation = str.substring(pos2+9, pos3);
				//
				// if (strEntity != null && strEntity.length() > 0 && strLocation != null && strLocation.length() > 0) {
				// eEntity myEntity = null;
				// eDistance myDist = null;
				// eDirection myDir = null;
				//
				// try {
				// myEntity = eEntity.valueOf(strEntity);
				// } catch (Exception e) {
				// // System.out.println("distance enum not defined for "+ strEntity);
				// }
				// try {
				//
				// StringBuffer strDistance = new StringBuffer();
				// StringBuffer strDirection = new StringBuffer();
				// splitDistanceDirection(strLocation, strDirection, strDistance);
				// myDir = eDirection.valueOf(strDirection.toString());
				// myDist = eDistance.valueOf(strDistance.toString());
				//
				// } catch (Exception e) {
				// // System.out.println("distance enum not defined for " + strLocation);
				// }
				//
				// if (myEntity != null && myDist != null && myDir != null) {
				// oRetVal.add(new clsImage(myDist, myDir, myEntity));
				// }
				// }
			}
		}

		return oRetVal;
	}

	public static void splitDistanceDirection(String strDistDir, StringBuffer sbdir, StringBuffer sbdist) {

		sbdist.delete(0, sbdist.length());
		sbdir.delete(0, sbdir.length());

		for (eDistance value : eDistance.values()) {
			int iPos = strDistDir.indexOf(value.toString());

			if (iPos >= 0 && iPos + value.toString().length() < strDistDir.length()) {

				sbdist.append(strDistDir.substring(iPos, value.toString().length()));
				sbdir.append(strDistDir.substring(value.toString().length(), strDistDir.length()));
				return;
			}
		}
	}

	public static void printPlanningStack(PlanningGraph plGraph) {
		plGraph.printPlans();
	}

	public static void printPlans(ArrayList<clsPlan> plans) {
		for (clsPlan singlePlan : plans) {
			System.out.println("PlanningWizard" + " plan: " + singlePlan);
		}

	}

	// public static void printPlansToSysout(ArrayList<PlanningNode> currentApplicalbePlanningNodes, int iIndend) {
	//
	// for (PlanningNode myPlanningNode : currentApplicalbePlanningNodes) {
	//
	// clsPlanFragment myPlan = (clsPlanFragment) myPlanningNode;
	//
	// // paint indent
	// for (int i=0;i<iIndend; i++)
	// System.out.print(" ");
	//
	// String strParentAction = "";
	// if (myPlan.parent != null && myPlan.parent.size() > 0)
	// strParentAction = ((clsPlanFragment) myPlan.parent.get(0)).m_act.m_strAction;
	//
	// System.out.println("planfragment: "+myPlan.m_act.m_strAction+", parent: "+strParentAction);
	//
	// if (myPlan.child != null && myPlan.child.size() > 0)
	// printPlansToSysout(myPlan.child, iIndend+5);
	// }
	//
	// }
}
