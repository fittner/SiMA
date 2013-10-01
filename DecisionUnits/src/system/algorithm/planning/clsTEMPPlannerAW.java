/**
 * CHANGELOG
 *
 * 26.09.2012 wendt - File created
 *
 */
package system.algorithm.planning;

import java.util.ArrayList;

import logger.clsLogger;

import org.slf4j.Logger;

import datatypes.helpstructures.clsPair;
import datatypes.helpstructures.clsTriple;
import pa._v38.memorymgmt.datahandlertools.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAct;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsImage;
import pa._v38.memorymgmt.datatypes.clsPlanFragment;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.enums.eAction;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eGoalType;
import pa._v38.memorymgmt.enums.ePhiPosition;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.memorymgmt.enums.eRadius;
import system.algorithm.planningHelpers.PlanningGraph;
import system.algorithm.planningHelpers.PlanningNode;
import system.algorithm.planningHelpers.PlanningWizard;
import system.algorithm.planningHelpers.TestDataCreator;
import system.algorithm.planningHelpers.eDirection;
import system.algorithm.planningHelpers.eDistance;
import system.algorithm.planningHelpers.eEntity;
import system.datamanipulation.clsActDataStructureTools;
import system.datamanipulation.clsActTools;
import system.datamanipulation.clsActionTools;
import system.datamanipulation.clsEntityTools;
import system.datamanipulation.clsMeshTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 26.09.2012, 11:33:05
 * 
 */
public class clsTEMPPlannerAW {
	
	private static clsTEMPPlannerAW moTEMPPlanner = null;
	
	private static final Logger log = clsLogger.getLog("planning");
	
	ArrayList<clsPlanFragment> moAvailablePlanFragments;
	PlanningGraph plGraph;
	
	private clsTEMPPlannerAW() {
		moAvailablePlanFragments = TestDataCreator.generateTestPlans_AW();

		/** init planning engine */
		plGraph = new PlanningGraph();
		plGraph.m_bPrintDebugOutput = false;
		try {
			/** add plans to planning engine */
			PlanningWizard.initPlGraphWithActions(moAvailablePlanFragments, plGraph);
			/** create connections between plans */
			PlanningWizard.initPlGraphWithPlConnections(moAvailablePlanFragments, plGraph);
			/** print plans to sysout */
			PlanningWizard.printPlanningStack(plGraph);

		} catch (Exception e) {
			log.error("FATAL initializing planning Wizard", e);
		}
		
		//moAvailablePlanFragments = poAvailablePlanFragments;
		//plGraph = poGraph;
	}
	
	/**
	 * Singleton for the Tempplanner
	 * 
	 * @since 27.09.2012 10:01:22
	 * 
	 * @return the moTEMPPlanner
	 */
	public static clsTEMPPlannerAW getPlanner() {
		if (moTEMPPlanner==null) {
			moTEMPPlanner = new clsTEMPPlannerAW();
		}  
			
		return moTEMPPlanner;
	}

	
	
	public ArrayList<clsWordPresentationMesh> generatePlans_AW(clsWordPresentationMesh poEnvironmentalPerception, clsWordPresentationMeshGoal poGoal) {
		ArrayList<clsWordPresentationMesh> oResult =  new ArrayList<clsWordPresentationMesh>();
		
		//ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();

		// String oPI = "PERCEIVEDIMAGE"; //This is the perceived image
		// String oRI = "IMAGE"; //This is from the memory

		// Prepare perception to the Image structure of clsImage -> based on
		// planning wizards
		ArrayList<clsImage> oPIImageStructure = preparePerception(poEnvironmentalPerception);

		// Take only the first goal
		// TODO: If plans shall be generated for more than one goals, this part
		// shall be changed
		ArrayList<clsWordPresentationMeshGoal> poReducedGoalList = new ArrayList<clsWordPresentationMeshGoal>();
		if (poGoal.isNullObject() == false) {
			poReducedGoalList.add(poGoal);
		}

		// Go through each goal
		for (clsWordPresentationMeshGoal oGoal : poReducedGoalList) {
			ArrayList<clsWordPresentationMesh> oActionContainer = new ArrayList<clsWordPresentationMesh>();

			// If no plans could be generated for this goal, it is set false,
			// else true
			boolean bActionPlanOK = false;

			//Get goal type
			eGoalType oGoalType = oGoal.getGoalType();
			
//			clsWordPresentationMesh oTopImage = clsMeshTools.getSuperStructure(clsGoalTools.getGoalObject(oGoal));
//			if (oTopImage == null) {
//				// try {
//				// throw new
//				// Exception("Error in F52: No object is allowed to be independent of an image");
//				// } catch (Exception e) {
//				// // TODO (wendt) - Auto-generated catch block
//				// e.printStackTrace();
//				// }
//
//				/** go to next goal */
//				break;
//			}

			//Perform actions according to the goal type
			
			//Goal type is an act
			if (oGoalType.equals(eGoalType.MEMORYDRIVE)) {
//				clsWordPresentationMesh oRecommendedAction = clsMeshTools.getNullObjectWPM();
//				// poPredictionList);
//				if (oRecommendedAction.isNullObject()==false) {
//					oActionContainer.add(oRecommendedAction);
//				}
//				
//
//				// If no plans could be generated for this goal, it is set
//				// false, else true
//				if (oActionContainer.isEmpty() == false) {
//					bActionPlanOK = true;
//					// continue;
//				}

			}

			// If the image is a perceived image
			if (oGoalType.equals(eGoalType.PERCEPTIONALDRIVE)==true && (bActionPlanOK == false)) {
				ArrayList<clsWordPresentationMesh> oActionFromMemoryContainerList = planFromPerception_AW(oPIImageStructure, oGoal);
				oActionContainer.addAll(oActionFromMemoryContainerList);

				// If no plans could be generated for this goal, it is set
				// false, else true
				if (oActionContainer.isEmpty() == false) {
					// oRetVal.addAll(oActionContainer);
					bActionPlanOK = true;
					// continue;
				}

			}
			
			if (oGoalType.equals(eGoalType.DRIVESOURCE)==true && (bActionPlanOK == false)) {
				//Start search
				ArrayList<clsWordPresentationMesh> oActionFromMemoryContainerList = planFromNoObject(oGoal);
				oActionContainer.addAll(oActionFromMemoryContainerList);
			}

			// If the image is just a general goal without object, then search
			if (bActionPlanOK == false) {

//				//Search
//				ArrayList<clsWordPresentationMesh> oActionFromMemoryContainerList = planFromNoObject(oGoal);
//				oActionContainer.addAll(oActionFromMemoryContainerList);
//
//				// If no plans could be generated for this goal, it is set
//				// false, else true
//				if (oActionContainer.isEmpty() == false) {
//					// oRetVal.addAll(oActionContainer);
//					bActionPlanOK = true;
//					// continue;
//				}

			}

			oResult.addAll(oActionContainer);

		}
		// then select, if the goal is connected to perception or to an
		// associated memory

		
		return oResult;
	}
	
	/**
	 * Extract the current action from a goal and return it as a WPM.
	 * 
	 * (wendt)
	 *
	 * @since 16.10.2012 22:15:33
	 *
	 * @param poGoal
	 * @return
	 */
	public ArrayList<clsWordPresentationMesh> extractRecommendedActionsFromAct(clsWordPresentationMeshGoal poGoal) {
		ArrayList<clsWordPresentationMesh> oResult = new ArrayList<clsWordPresentationMesh>();
		
		clsWordPresentationMesh oMoment = clsActDataStructureTools.getMoment(poGoal.getSupportiveDataStructure());
		
		if (oMoment.isNullObject()==false) {
			eAction oAction = clsActTools.getRecommendedAction(oMoment);
			
			if (oAction.equals(eAction.NULLOBJECT)==false) {
				oResult.add(clsActionTools.createAction(oAction));
			}
		}
		
		return oResult;
	}
	
	/**
	 * Extract clsImage from a normal Perceived Image (wendt)
	 * 
	 * @since 26.09.2011 16:35:19
	 * 
	 * @param poEnvironmentalPerception
	 * @return
	 */
	private ArrayList<clsImage> preparePerception(clsWordPresentationMesh poEnvironmentalPerception) {
		ArrayList<clsImage> oRetVal = new ArrayList<clsImage>();

		// get current environmental situation from moSContainer -> create an
		// image
		// this is necessary since the data is all added to one data-string
		ArrayList<clsImage> currentImageAllObjects = PlanningWizard.getCurrentEnvironmentalImage(poEnvironmentalPerception
		    .getMoInternalAssociatedContent());
		oRetVal.addAll(currentImageAllObjects);

		return oRetVal;
	}
	
	/**
	 * Planning based on perception
	 * 
	 * @since 26.09.2011 14:20:17
	 * 
	 * @param poEnvironmentalPerception
	 * @param poGoalList
	 * @return
	 */
	private ArrayList<clsWordPresentationMesh> planFromPerception_AW(ArrayList<clsImage> poPIImageStructure, clsWordPresentationMeshGoal poGoal) {

		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();

		// get current environmental situation from moSContainer -> create an
		// image
		// ArrayList<clsImage> currentImageAllObjects =
		// PlanningWizard.getCurrentEnvironmentalImage(((clsWordPresentationMesh)
		// poEnvironmentalPerception.getSecondaryComponent().getMoDataStructure()).getMoAssociatedContent());

		// if no image of the current world-situation can be returned, we dont't
		// know where to start with planning -> search sequence
		// if (currentImageAllObjects.isEmpty()) {
		// ArrayList<clsPlanFragment> tempPlanningNodes = new
		// ArrayList<clsPlanFragment>();
		// tempPlanningNodes.add(new clsPlanFragment(new clsAct("SEARCH1"),
		// new clsImage(eEntity.NONE),
		// new clsImage(eDirection.CENTER, eEntity.CAKE)));
		// oRetVal.addAll(copyPlanFragments(tempPlanningNodes));
		// return oRetVal;
		// }

		// ArrayList<clsImage> currentImageSorted = new ArrayList<clsImage>();
		// //TODO AP: AW This loop considers the goal objects, put it where it
		// should be
		// for (clsSecondaryDataStructureContainer oGoalContainer : poGoalList)
		// {
		// String oDriveObject =
		// clsAffectTools.getDriveObjectType(((clsWordPresentation)oGoalContainer.getMoDataStructure()).getMoContent());
		//
		// for (clsImage oImage : currentImageAllObjects) {
		// if (oDriveObject.equals("ENTITY:" + oImage.m_eObj)) {
		// currentImageSorted.add(oImage);
		// }
		// }
		// }

		// Filter all object, which are not drive objects of this goal
		ArrayList<clsImage> ofilteredImages = filterForDecisionMakingGoal(poGoal, poPIImageStructure);

		// Start planning according to the remaining drive objects
		// System.out.println(currentImage.m_eDist);
		// add plans and connections between plans
		ArrayList<clsPlanFragment> currentApplicalbePlanningNodes = null;
		try {

			// check, which actions can be executed next
			currentApplicalbePlanningNodes = PlanningWizard.getCurrentApplicablePlanningNodes(moAvailablePlanFragments, ofilteredImages);

			// TODO create code for high depth plans here

			// run through applicable plans and see which results can be
			// achieved by executing plFragment
			for (clsPlanFragment plFragment : currentApplicalbePlanningNodes) {
				plGraph.setStartPlanningNode(plFragment);
				plGraph.breathFirstSearch();
			}

			// copy output -> workaround till planning works correctly
			oRetVal.addAll(copyPlanFragments(currentApplicalbePlanningNodes));

			// FIXME AP: Dead code
			ArrayList<PlanningNode> plansTemp = new ArrayList<PlanningNode>();

			for (clsPlanFragment myPlans : currentApplicalbePlanningNodes)
				plansTemp.add(myPlans);

			// output actions
			// PlanningWizard.printPlansToSysout(plansTemp , 0);
			// plGraph.m_planningResults.get(1)

		} catch (Exception e) {
			System.out.println(currentApplicalbePlanningNodes.toString());
			e.printStackTrace();
			System.out.println(getClass() + "FATAL: Planning Wizard coldn't be initialized");
		}

		// copy perception for movement control
		// moEnvironmentalPerception_OUT = moEnvironmentalPerception_IN;

		// plGraph.setStartPlanningNode(n)
		return oRetVal;
	}
	
	/**
	 * Planning, if no object is given
	 * 
	 * @since 26.09.2011 14:20:06
	 * 
	 * @param poGoal
	 * @return
	 */
	private ArrayList<clsWordPresentationMesh> planFromNoObject(clsWordPresentationMesh poGoal) {

		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		oRetVal.addAll(planSearch());

		return oRetVal;
	}
	
	/**
	 * Remove image parts, which are not active for this goal
	 * 
	 * @since 26.09.2011 14:54:04
	 * 
	 * @param poGoal
	 * @param poCurrentImageAllObjects
	 * @return
	 */
	private ArrayList<clsImage> filterForDecisionMakingGoal(clsWordPresentationMeshGoal poGoal, ArrayList<clsImage> poCurrentImageAllObjects) {
		ArrayList<clsImage> currentImageSorted = new ArrayList<clsImage>();

		for (clsImage oImage : poCurrentImageAllObjects) {
			clsWordPresentationMesh oGoalEntity = poGoal.getGoalObject();
			clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oGoalPosition = clsEntityTools.getPosition(oGoalEntity);
			
			
			if (oGoalEntity.getMoContent().equals(oImage.m_eObj.toString()) && 
					eDirection.getDirection(oGoalPosition.b).equals(oImage.m_eDir) && 
					eDistance.getDistance(oGoalPosition.c).equals(oImage.m_eDist)) {
				currentImageSorted.add(oImage);
			}
		}
		return currentImageSorted;
	}
	
	/**
	 * Generate a search plan (wendt)
	 * 
	 * @since 26.09.2011 14:27:40
	 * 
	 * @return
	 */
	private ArrayList<clsWordPresentationMesh> planSearch() {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();

		ArrayList<clsPlanFragment> tempPlanningNodes = new ArrayList<clsPlanFragment>();
		tempPlanningNodes.add(new clsPlanFragment(new clsAct(eAction.SEARCH1), new clsImage(eEntity.NONE), new clsImage(eDirection.CENTER, eDistance.NEAR,
		    eEntity.CAKE)));
		oRetVal.addAll(copyPlanFragments(tempPlanningNodes));

		return oRetVal;
	}
	
	private ArrayList<clsWordPresentationMesh> copyPlanFragments(ArrayList<clsPlanFragment> myPlans) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		ArrayList<clsPlanFragment> moPlans = new ArrayList<clsPlanFragment>();

		try {
			for (clsPlanFragment oP : myPlans) {
				if (oP.m_preconditionImage!=null && oP.m_preconditionImage.m_eObj!=eEntity.NONE) {
					if (oP.m_preconditionImage.m_eDist==null) {
						throw new Exception("Error: " + oP.m_preconditionImage.toString() + "is has a NULL component");
					}
				} 
				
				if (oP.m_effectImage!=null) {
					if (oP.m_effectImage.m_eDist==null && oP.m_preconditionImage.m_eObj!=eEntity.NONE) {
						throw new Exception("Error: " + oP.m_effectImage.toString() + "is has a NULL component");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for (clsPlanFragment plFr : myPlans) {
			moPlans.add(plFr);
		}

		// Convert the containers into WPM
		for (clsPlanFragment oPF : moPlans) {
			clsWordPresentationMesh oAction = clsActionTools.createAction(eAction.valueOf(oPF.m_act.m_strAction));
			
			clsWordPresentationMesh oGoalObject = clsDataStructureGenerator.generateWPM(new clsPair<eContentType, Object>(eContentType.ENTITY,oPF.m_effectImage.m_eObj.toString()), new ArrayList<clsAssociation>());
			try {
				clsMeshTools.setUniquePredicateWP(oGoalObject, eContentType.DISTANCEASSOCIATION, ePredicate.HASDISTANCE, eContentType.DISTANCE, oPF.m_effectImage.m_eDist.toString(), false);
			} catch (Exception e) {
				System.out.println(oPF.m_effectImage.toString());
				e.printStackTrace();
			}
			
			clsMeshTools.setUniquePredicateWP(oGoalObject, eContentType.DISTANCEASSOCIATION, ePredicate.HASPOSITION, eContentType.POSITION, oPF.m_effectImage.m_eDir.toString(), false);
			
			clsMeshTools.createAssociationSecondary(oAction, 2, oGoalObject, 2, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASASSOCIATION, false);
			oRetVal.add(oAction);
		}

		// add perception
		return oRetVal;
	}


	
}
