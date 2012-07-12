/**
 * E27_GenerationOfImaginaryActions.java: DecisionUnits - pa.modules
 * 
 * @author deutsch/perner test
 * 11.08.2009, 14:55:01
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.itfInspectorGenericActivityTimeChart;
import pa._v38.interfaces.modules.I2_5_receive;
import pa._v38.interfaces.modules.I6_11_receive;
import pa._v38.interfaces.modules.I6_8_receive;
import pa._v38.interfaces.modules.I6_9_receive;
import pa._v38.interfaces.modules.I6_9_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAct;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainerPair;
import pa._v38.memorymgmt.datatypes.clsImage;
import pa._v38.memorymgmt.datatypes.clsPlan;
import pa._v38.memorymgmt.datatypes.clsPlanFragment;
import pa._v38.memorymgmt.datatypes.clsPrediction;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eAction;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.memorymgmt.enums.ePhiPosition;
import pa._v38.memorymgmt.enums.eRadius;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.storage.clsShortTermMemory;
import pa._v38.tools.clsActionTools;
import pa._v38.tools.clsEntityTools;
import pa._v38.tools.clsGoalTools;
import pa._v38.tools.clsMentalSituationTools;
import pa._v38.tools.clsMeshTools;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;
import pa._v38.tools.planningHelpers.PlanningGraph;
import pa._v38.tools.planningHelpers.PlanningNode;
import pa._v38.tools.planningHelpers.PlanningWizard;
import pa._v38.tools.planningHelpers.TestDataCreator;
import pa._v38.tools.planningHelpers.eDirection;
import pa._v38.tools.planningHelpers.eDistance;
import pa._v38.tools.planningHelpers.eEntity;
import config.clsProperties;

/**
 * DOCUMENT (perner) - Based on the goal delivered from module F26_Decision_Making, a set of action-plans is created in order to achieve the
 * goal. Each plan consists of plan-fragments. Based on images, actions a combined and the outcome of a plan-fragment respective of a plan
 * is defined. The generated action-plan is passed on the the next module F29_EvaluationOfImaginaryActions.
 * 
 * @author perner 09.10.2011
 * 
 */
public class F52_GenerationOfImaginaryActions extends clsModuleBaseKB implements I6_8_receive, I6_9_send, I6_11_receive, I2_5_receive,
    itfInspectorGenericActivityTimeChart {

	public static final String newline = System.getProperty("line.separator");

	public static final String P_MODULENUMBER = "52";
	private static final boolean m_bUseDraftPlanning = false;
	private static final boolean m_bPrintDebugOutput = false;

	// HZ Not used up to now 16.03.2011
	private ArrayList<clsWordPresentationMesh> moGoalList_IN;
	private ArrayList<ArrayList<clsAct>> moPlanInput;

	/** DOCUMENT (wendt) - insert description; @since 31.07.2011 21:25:26 */
	//private ArrayList<clsPrediction> moExtractedPrediction_IN;

	private clsWordPresentationMesh moPerceptionalMesh_IN;
	//private clsWordPresentationMesh moPerceptionalMesh_OUT;

	/** Associated memories in */
	private ArrayList<clsWordPresentationMesh> moAssociatedMemories_IN;
	
	private ArrayList<clsWordPresentation> moMotilityActions_IN;
	private ArrayList<clsWordPresentation> moImaginaryActions_IN;

	/** Associated memories out */
	//private ArrayList<clsWordPresentationMesh> moAssociatedMemories_OUT;
	private ArrayList<clsWordPresentationMesh> moPlans_Output = new ArrayList<clsWordPresentationMesh>();
	private ArrayList<clsPlanFragment> moAvailablePlanFragments;
	private ArrayList<clsPlanFragment> moCurrentApplicalbePlans;

	/** generated plans */
	private ArrayList<clsPlan> plansFromPerception = new ArrayList<clsPlan>();

	private clsShortTermMemory moShortTermMemory;
	
	private clsShortTermMemory moEnvironmentalImageStorage;
	
	
	private PlanningGraph plGraph;

	/**
	 * DOCUMENT (perner) - insert description
	 * 
	 * @author deutsch 03.03.2011, 16:55:46
	 * 
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F52_GenerationOfImaginaryActions(String poPrefix, clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList,
	    SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, clsKnowledgeBaseHandler poKnowledgeBaseHandler, clsShortTermMemory poShortTermMemory, clsShortTermMemory poTempLocalizationStorage) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);

		//Get STM
		this.moShortTermMemory = poShortTermMemory;
		
		//Get Perceived image
		this.moEnvironmentalImageStorage = poTempLocalizationStorage;
		
		moAvailablePlanFragments = new ArrayList<clsPlanFragment>();

		applyProperties(poPrefix, poProp);

		// just used to test if the planning module does not have any compile
		// errors
		if (m_bUseDraftPlanning) {
			moAvailablePlanFragments = TestDataCreator.generateTestPlans_AP();
		} else {
			moAvailablePlanFragments = TestDataCreator.generateTestPlans_AW();
		}

		/** init planning engine */
		plGraph = new PlanningGraph();
		plGraph.m_bPrintDebugOutput = m_bPrintDebugOutput;
		try {
			/** add plans to planning engine */
			PlanningWizard.initPlGraphWithActions(moAvailablePlanFragments, plGraph);
			/** create connections between plans */
			PlanningWizard.initPlGraphWithPlConnections(moAvailablePlanFragments, plGraph);
			/** print plans to sysout */
			PlanningWizard.printPlanningStack(plGraph);

		} catch (Exception e) {
			System.out.println(getClass() + "FATAL initializing planning Wizard >" + e + "< , stack-trace >");
		}

	}

	/***********************************************************************************************
	 * BEGINN generic class methods below are generic class methods which are shared through all implementations of ARS-modules
	 **********************************************************************************************/

	/*
	 * (non-Javadoc)
	 * 
	 * @author deutsch 11.08.2009, 16:16:38
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void process_basic() {

		//Load perception
		moPerceptionalMesh_IN = this.moEnvironmentalImageStorage.findCurrentSingleMemory();
		
		if (m_bUseDraftPlanning) {
			process_draft();
		} else {
			//Testfocus. If focusing was the last step, then do normal action, else do focus
			
			//=======================================================================//
			//FIXME: Remove this, when deepcopy is removed. Process the goal list
			//FIXME AW: This is a hack as deepcopy destroys the hashcode. Therefore, everything is saved in the storage and loaded on demand. In this case, getGoal is taken
			
			clsWordPresentationMesh oCurrentMentalSituation = this.moShortTermMemory.findCurrentSingleMemory();
			clsWordPresentationMesh oGoal = clsMentalSituationTools.getGoal(oCurrentMentalSituation);
			this.moGoalList_IN = new ArrayList<clsWordPresentationMesh>();
			this.moGoalList_IN.add(oGoal);
			//=======================================================================//
			
			// Generate actions for the top goal
			moPlans_Output = processGoals_AW(moPerceptionalMesh_IN, moGoalList_IN); //generatePlans_AW(moPerceptionalMesh_IN, moGoalList_IN);
			//FIXME HACK AW: Generate the search pattern
			
			//UNREAL IMAGE CORRECTION HACK
			for (clsWordPresentationMesh oPlan : moPlans_Output) {
				if (oPlan.getMoContent().equals("UNREAL_MOVE_TO")) {
					//Extract Entity
					clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oEntityInfo = clsEntityTools.getPosition((clsWordPresentationMesh) oPlan.getExternalAssociatedContent().get(0).getLeafElement());
					String oNewContent = oPlan.getMoContent() + "|" + oEntityInfo.a.getMoContent() + ":" + oEntityInfo.b.toString() + ":" + oEntityInfo.c.toString();
					oPlan.setMoContent(oNewContent);
					//UNREAL_MOVE_TO|HEALTH:LEFT:FAR
				}
					
			}
			//moPlans_Output.addAll(planSearch());

			// Pass forward the associated memories and perception
//			try {
//				moPerceptionalMesh_OUT = (clsWordPresentationMesh) moPerceptionalMesh_IN.clone();
//			} catch (CloneNotSupportedException e) {
//				e.printStackTrace();
//			}
//			moAssociatedMemories_OUT = (ArrayList<clsWordPresentationMesh>) deepCopy(moAssociatedMemories_IN);

			// printData(moActions_Output, moGoalInput,
			// moExtractedPrediction_IN);
		}

	}

	/**
	 * contains all data in order to create plans
	 * 
	 * @author perner 12.07.2010, 10:47:41
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {

		// Generate actions for the top goal
		moPlans_Output = generatePlans_AP(moPerceptionalMesh_IN, moGoalList_IN);

//		// Pass forward the associated memories and perception
//		try {
//			moPerceptionalMesh_OUT = (clsWordPresentationMesh) moPerceptionalMesh_IN.clone();
//		} catch (CloneNotSupportedException e) {
//			e.printStackTrace();
//		}
//		moAssociatedMemories_OUT = (ArrayList<clsWordPresentationMesh>) deepCopy(moAssociatedMemories_IN);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author deutsch 12.07.2010, 10:47:41
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (perner) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author deutsch 03.03.2011, 16:55:51
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author deutsch 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "By combination of the motives provided by {E26} and the experiences retrieved by {E28}, {E27} generates a set of imaginary actions. Before actions are passed to {E30} they are solely psychic contents and thus imaginary. An imaginary action (-plan) defines a more or less complex sequence of actions on how to satisfy a need based on actions taken in similar situations. ";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author deutsch 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {

		String text = "";

		text += toText.listToTEXT("moPlanInput", moPlanInput);
		//text += toText.listToTEXT("moExtractedPrediction_IN", moExtractedPrediction_IN);
		text += toText.listToTEXT("moActions_Output", moPlans_Output);
		text += toText.listToTEXT("moGoalList_IN", moGoalList_IN);
		//text += toText.listToTEXT("moAssociatedMemories_OUT", moAssociatedMemories_OUT);

		text += newline;
		text += "current generated plans:";
		text += newline;

		for (clsPlan singlePlan : plansFromPerception) {
			text += PlanningWizard.dumpPlanToString(singlePlan) + newline;
		}
		return text;
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre + P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());

		return oProp;
	}

	private void applyProperties(String poPrefix, clsProperties poProp) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author deutsch 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.SECONDARY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author deutsch 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * 
	 * @author deutsch 11.08.2009, 14:55:51
	 * 
	 * @see pa.interfaces.I7_1#receive_I7_1(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I6_8(ArrayList<clsWordPresentationMesh> poDecidedGoalList) {
		moGoalList_IN = (ArrayList<clsWordPresentationMesh>) deepCopy(poDecidedGoalList);
	}
	
	/* (non-Javadoc)
	 *
	 * @since 07.05.2012 14:28:53
	 * 
	 * @see pa._v38.interfaces.modules.I2_5_receive#receive_I2_5(java.util.ArrayList)
	 */
	@Override
	public void receive_I2_5(ArrayList<clsWordPresentation> poActionCommands) {
		moMotilityActions_IN = (ArrayList<clsWordPresentation>) deepCopy(poActionCommands); 
	}

	/* (non-Javadoc)
	 *
	 * @since 07.05.2012 14:28:54
	 * 
	 * @see pa._v38.interfaces.modules.I6_11_receive#receive_I6_11(java.util.ArrayList, pa._v38.memorymgmt.datatypes.clsWordPresentationMesh)
	 */
	@Override
	public void receive_I6_11(ArrayList<clsWordPresentationMesh> poActionCommands) {
		moImaginaryActions_IN = (ArrayList<clsWordPresentation>)deepCopy(poActionCommands);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author deutsch 11.08.2009, 16:16:38
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I6_9(moPlans_Output);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author deutsch 18.05.2010, 17:56:21
	 * 
	 * @see pa.interfaces.send.I7_3_send#send_I7_3(java.util.ArrayList)
	 */
	@Override
	public void send_I6_9(ArrayList<clsWordPresentationMesh> poActionCommands) {
		//((I6_9_receive) moModuleList.get(8)).receive_I6_9(poActionCommands, poAssociatedMemories, poEnvironmentalPerception);
		//((I6_9_receive) moModuleList.get(20)).receive_I6_9(poActionCommands, poAssociatedMemories, poEnvironmentalPerception);
		//((I6_9_receive) moModuleList.get(21)).receive_I6_9(poActionCommands, poAssociatedMemories, poEnvironmentalPerception);
		((I6_9_receive) moModuleList.get(29)).receive_I6_9(poActionCommands);
		((I6_9_receive) moModuleList.get(47)).receive_I6_9(poActionCommands);
		((I6_9_receive) moModuleList.get(53)).receive_I6_9(poActionCommands);

		putInterfaceData(I6_9_send.class, poActionCommands);

	}

	/***********************************************************************************************
	 * END generic class methods
	 **********************************************************************************************/

	/***********************************************************************************************
	 * BEGINN class specific methods (e.g. planning methods)
	 **********************************************************************************************/

	/**
	 * 
	 * DOCUMENT (perner) - generation of plans
	 * 
	 * @since 03.03.2012 10:56:06
	 * 
	 * @param poEnvironmentalPerception
	 * @param poPredictionList
	 * @param poGoalList
	 * @return
	 */
	private ArrayList<clsWordPresentationMesh> generatePlans_AP(clsWordPresentationMesh poEnvironmentalPerception, ArrayList<clsWordPresentationMesh> poGoalList) {

		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();

		// Prepare perception to the Image structure of clsImage -> based on
		// planning wizards
		ArrayList<clsImage> oPIImageStructure = preparePerception(poEnvironmentalPerception);

		// TODO: If plans shall be generated for more than one goals, this part
		// shall be changed
		ArrayList<clsWordPresentationMesh> poReducedGoalList = new ArrayList<clsWordPresentationMesh>();
		if (poGoalList.isEmpty() == false) {
			poReducedGoalList.add(poGoalList.get(0));
		}

		/** iterate over goals */
		for (clsWordPresentationMesh oGoal : poReducedGoalList) {
			ArrayList<clsWordPresentationMesh> oActionContainer = new ArrayList<clsWordPresentationMesh>();

			// If no plans could be generated for this goal, it is set false,
			// else true
			boolean bActionPlanOK = false;

			clsWordPresentationMesh oTopImage = clsMeshTools.getSuperStructure(clsGoalTools.getGoalObject(oGoal));
			if (oTopImage == null) {

				/** go to next goal */
				break;
			}

			/** handling if the image comes from memory */
			if (oTopImage.getMoContentType().equals(eContentType.RI) == true
			    && oTopImage.getMoContentType().equals(eContentType.PI) == false) {
				ArrayList<clsWordPresentationMesh> oActionFromMemoryContainerList = new ArrayList<clsWordPresentationMesh>();
				oActionContainer.addAll(oActionFromMemoryContainerList);

				// If no plans could be generated for this goal do a search
				if (oActionContainer.isEmpty() == false) {
					bActionPlanOK = true;
				}

			}

			/** handling if the image comes from perception */
			if (oTopImage.getMoContentType().equals(eContentType.PI) == true && (bActionPlanOK == false)) {
				ArrayList<clsWordPresentationMesh> oActionFromMemoryContainerList = planFromPerception_AP(oPIImageStructure, oGoal);
				oActionContainer.addAll(oActionFromMemoryContainerList);

				// If no plans could be generated for this goal do a search
				if (oActionContainer.isEmpty() == false) {
					bActionPlanOK = true;
				}

			}

			/**
			 * If the image is just a general goal without object then do a search
			 */
			// TODO add some kind of sophisticated search method here
			if (bActionPlanOK == false) {

				ArrayList<clsWordPresentationMesh> oActionFromMemoryContainerList = planFromNoObject(oGoal);
				oActionContainer.addAll(oActionFromMemoryContainerList);

				// If no plans could be generated for this goal do a search
				if (oActionContainer.isEmpty() == false) {
					bActionPlanOK = true;
				}
			}
			oRetVal.addAll(oActionContainer);

		}
		return oRetVal;
	}

	/**
	 * This is the top class for planning, where it is chosen, which plan is selected, dependent on the content of the goal
	 * 
	 * @since 26.09.2011 14:15:24
	 * 
	 * @param poEnvironmentalPerception
	 * @param poPredictionList
	 * @param poGoalList
	 * @return
	 */
	private ArrayList<clsWordPresentationMesh> generatePlans_AW(clsWordPresentationMesh poEnvironmentalPerception, ArrayList<clsWordPresentationMesh> poGoalList) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();

		// String oPI = "PERCEIVEDIMAGE"; //This is the perceived image
		// String oRI = "IMAGE"; //This is from the memory

		// Prepare perception to the Image structure of clsImage -> based on
		// planning wizards
		ArrayList<clsImage> oPIImageStructure = preparePerception(poEnvironmentalPerception);

		// Take only the first goal
		// TODO: If plans shall be generated for more than one goals, this part
		// shall be changed
		ArrayList<clsWordPresentationMesh> poReducedGoalList = new ArrayList<clsWordPresentationMesh>();
		if (poGoalList.isEmpty() == false) {
			poReducedGoalList.add(poGoalList.get(0));
		}

		// Go through each goal
		for (clsWordPresentationMesh oGoal : poReducedGoalList) {
			ArrayList<clsWordPresentationMesh> oActionContainer = new ArrayList<clsWordPresentationMesh>();

			// If no plans could be generated for this goal, it is set false,
			// else true
			boolean bActionPlanOK = false;

			clsWordPresentationMesh oTopImage = clsMeshTools.getSuperStructure(clsGoalTools.getGoalObject(oGoal));
			if (oTopImage == null) {
				// try {
				// throw new
				// Exception("Error in F52: No object is allowed to be independent of an image");
				// } catch (Exception e) {
				// // TODO (wendt) - Auto-generated catch block
				// e.printStackTrace();
				// }

				/** go to next goal */
				break;
			}

			/**
			 * what happens here? data from memory ?
			 */
			if (oTopImage.getMoContentType().equals(eContentType.RI) == true
			    && oTopImage.getMoContentType().equals(eContentType.PI) == false) {
				ArrayList<clsWordPresentationMesh> oActionFromMemoryContainerList = new ArrayList<clsWordPresentationMesh>();// planFromMemories(oGoal,
				// poPredictionList);
				oActionContainer.addAll(oActionFromMemoryContainerList);

				// If no plans could be generated for this goal, it is set
				// false, else true
				if (oActionContainer.isEmpty() == false) {
					bActionPlanOK = true;
					// continue;
				}

			}

			// If the image is a perceived image
			if (oTopImage.getMoContentType().equals(eContentType.PI) == true && (bActionPlanOK == false)) {
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

			// If the image is just a general goal without object, then search
			if (bActionPlanOK == false) {

				ArrayList<clsWordPresentationMesh> oActionFromMemoryContainerList = planFromNoObject(oGoal);
				oActionContainer.addAll(oActionFromMemoryContainerList);

				// If no plans could be generated for this goal, it is set
				// false, else true
				if (oActionContainer.isEmpty() == false) {
					// oRetVal.addAll(oActionContainer);
					bActionPlanOK = true;
					// continue;
				}

			}

			oRetVal.addAll(oActionContainer);

		}
		// then select, if the goal is connected to perception or to an
		// associated memory

		return oRetVal;
	}
	
	private ArrayList<clsWordPresentationMesh> processGoals_AW(clsWordPresentationMesh poEnvironmentalPerception, ArrayList<clsWordPresentationMesh> poGoalList) {
		ArrayList<clsWordPresentationMesh> oResult = new ArrayList<clsWordPresentationMesh>();
		
		//Check FOCUS_ON was the previous action
		clsWordPresentationMesh oPreviousMentalSituation = this.moShortTermMemory.findPreviousSingleMemory();
		clsWordPresentationMesh oPreviousAction = clsMentalSituationTools.getAction(oPreviousMentalSituation);
		
		if (oPreviousAction.getMoContent().equals(eAction.FOCUS_ON.toString())==true) {
			//If yes, then perform normal external planning
			oResult.addAll(generatePlans_AW(poEnvironmentalPerception, poGoalList));
		} else {
			//If no, create an internal plan FOCUS_ON
			clsWordPresentationMesh oNewAction = clsActionTools.createAction(eAction.FOCUS_ON.toString());
			
			//Associate it with the goal of the supported datastructures
			
			//Get the first goal
			if (poGoalList.isEmpty()==false) {
				//If the goal is taken, set the supportive data structure
				checkAndEnhanceSupportiveDataStructure(poGoalList.get(0));
				
				//Get the supportive data structure
				clsWordPresentationMesh oSupportiveDataStructure = clsGoalTools.getSupportiveDataStructure(poGoalList.get(0));
				
				//Associate this structure with the action
				clsActionTools.setSupportiveDataStructureHashCode(oNewAction, oSupportiveDataStructure);
			}
			
			oResult.add(oNewAction);
		}		
		
		return oResult;
	}
	
	private void checkAndEnhanceSupportiveDataStructure(clsWordPresentationMesh poGoal) {
		//If there is no supportive data structure, create one with the 
		clsWordPresentationMesh oSupportiveDataStructure = clsGoalTools.getSupportiveDataStructure(poGoal);
		clsWordPresentationMesh oGoalObject = clsGoalTools.getGoalObject(poGoal);
		
		if (oSupportiveDataStructure.getMoContent().equals(eContentType.NULLOBJECT.toString())) {
			//Create supportive structure
			clsGoalTools.createSupportiveDataStructureFromEntity(poGoal, oGoalObject);
		} else if (oSupportiveDataStructure.getMoContentType().equals(eContentType.PI.toString())) {
			//Replace the perceived image with the drive object
			clsGoalTools.createSupportiveDataStructureFromEntity(poGoal, clsGoalTools.getGoalObject(poGoal));
		}
		
		
		
	}

//	/**
//	 * Planning taken from remembered images
//	 * 
//	 * @since 26.09.2011 14:19:45
//	 * 
//	 * @param poGoal
//	 * @param poPredictionList
//	 * @return
//	 */
//	private ArrayList<clsSecondaryDataStructureContainer> planFromMemories(clsSecondaryDataStructureContainer poGoal,
//	    ArrayList<clsPrediction> poPredictionList) {
//		ArrayList<clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
//
//		// Get the expectations, which are associated with this goal
//		ArrayList<clsDataStructureContainerPair> oExpectationList = getExpectationFromGoal(poGoal, poPredictionList);
//		// Get the act, which is associated with the expectations
//		ArrayList<clsSecondaryDataStructureContainer> oActList = getActsFromExpectations(oExpectationList);
//
//		ArrayList<clsSecondaryDataStructureContainer> oPlanFragmentList = new ArrayList<clsSecondaryDataStructureContainer>();
//
//		for (clsSecondaryDataStructureContainer oActContainer : oActList) {
//			oPlanFragmentList.add(convertActToPlanFragment((clsAct) oActContainer.getMoDataStructure()));
//		}
//
//		oRetVal.addAll(oPlanFragmentList);
//
//		return oRetVal;
//	}

	/**
	 * Planning based on perception
	 * 
	 * @since 26.09.2011 14:20:17
	 * 
	 * @param poEnvironmentalPerception
	 * @param poGoalList
	 * @return
	 */
	private ArrayList<clsWordPresentationMesh> planFromPerception_AW(ArrayList<clsImage> poPIImageStructure, clsWordPresentationMesh poGoal) {

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
	 * Planning based on perception
	 * 
	 * @since 26.09.2011 14:20:17
	 * 
	 * @param poEnvironmentalPerception
	 * @param poGoalList
	 * @return
	 */
	private ArrayList<clsWordPresentationMesh> planFromPerception_AP(ArrayList<clsImage> poPIImageStructure, clsWordPresentationMesh poGoal) {

		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();

		// Filter all object, which are not drive objects of this goal
		ArrayList<clsImage> ofilteredImages = filterForDecisionMakingGoal(poGoal, poPIImageStructure);

		// check, which actions can be executed next
		ArrayList<clsPlanFragment> currentApplicalbePlanningNodes = PlanningWizard.getCurrentApplicablePlanningNodes(moAvailablePlanFragments,
		    ofilteredImages);

		// TODO create code for high depth plans here
		if (currentApplicalbePlanningNodes.size() > 0) {
			int i = 0;
		}

//		System.out.println(getClass() + " ********************** start to generate a plan from perception ********************** ");
//		System.out.println(getClass() + " current applicable planning planFragments >" + currentApplicalbePlanningNodes.size() + "< content >"
//		    + currentApplicalbePlanningNodes + "<");
		if (m_bPrintDebugOutput) {
			System.out.println(getClass() + " ********************** start to generate a plan from perception ********************** ");
			System.out.println(getClass() + " current applicable planning planFragments >" + currentApplicalbePlanningNodes.size()
			    + "< content >" + currentApplicalbePlanningNodes + "<");
		}
		/** reset list and store new plans */
		plansFromPerception.clear();

		/** run through applicable plans and see which results can be achieved by executing plFragment */
		for (clsPlanFragment plFragment : currentApplicalbePlanningNodes) {
//			System.out.println(getClass() + " generating plan for planFragment >" + plFragment + "<");
			if (m_bPrintDebugOutput) {
				System.out.println(getClass() + " generating plan for planFragment >" + plFragment + "<");
			}

			plGraph.setStartPlanningNode(plFragment);
			plGraph.breathFirstSearch();
			ArrayList<clsPlan> plans = plGraph.getPlans();

			if (m_bPrintDebugOutput) {
				PlanningWizard.printPlans(plans);
			}

			/** add all plans */
			for (clsPlan singlePlan : plans) {
				plansFromPerception.add(singlePlan);
			}

		}

		// copy output -> workaround till planning works correctly
		oRetVal.addAll(copyPlanFragments(currentApplicalbePlanningNodes));
		ArrayList<PlanningNode> plansTemp = new ArrayList<PlanningNode>();

		for (clsPlanFragment myPlans : currentApplicalbePlanningNodes)
			plansTemp.add(myPlans);

		// output actions
		// PlanningWizard.printPlansToSysout(plansTemp , 0);
		// plGraph.m_planningResults.get(1)

		// copy perception for movement control
		// moEnvironmentalPerception_OUT = moEnvironmentalPerception_IN;

		// plGraph.setStartPlanningNode(n)
		return oRetVal;
	}

	/**
	 * convert clsAct as defined in Protege to a clsPlanFragment (wendt)
	 * 
	 * @since 26.09.2011 16:36:04
	 * 
	 * @param poAct
	 * @return
	 */
	private clsPlanFragment convertActToPlanFragment(clsAct poAct) {
		clsPlanFragment oRetVal = null;

		String oActContent = poAct.getMoContent();
		String oNewActionCommand = getActionStringFromContent(oActContent);

		poAct.m_strAction = oNewActionCommand;

		oRetVal = new clsPlanFragment(poAct, new clsImage(), new clsImage());

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
	 * Remove image parts, which are not active for this goal
	 * 
	 * @since 26.09.2011 14:54:04
	 * 
	 * @param poGoal
	 * @param poCurrentImageAllObjects
	 * @return
	 */
	private ArrayList<clsImage> filterForDecisionMakingGoal(clsWordPresentationMesh poGoal, ArrayList<clsImage> poCurrentImageAllObjects) {
		ArrayList<clsImage> currentImageSorted = new ArrayList<clsImage>();

		for (clsImage oImage : poCurrentImageAllObjects) {
			if (clsGoalTools.getGoalObject(poGoal).getMoContent().equals(oImage.m_eObj.toString())) {
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

	/**
	 * Extract the list of secondary structure expectations from a list of predictions for a certain intention DOCUMENT (wendt) - insert
	 * description
	 * 
	 * @since 01.08.2011 16:59:46
	 * 
	 * @param poIntention
	 * @param poPrediction
	 * @return
	 */
	private ArrayList<clsDataStructureContainerPair> getExpectationFromPredictionList(clsSecondaryDataStructure poIntention,
	    ArrayList<clsPrediction> poPrediction) {
		ArrayList<clsDataStructureContainerPair> oRetVal = new ArrayList<clsDataStructureContainerPair>();

		// Check if the intention == null
		if (poIntention != null) {
			for (clsPrediction oSinglePrediction : poPrediction) {
				// If the instanceIDs are the same in the prediction, the give
				// back
				if (poIntention.getMoDSInstance_ID() == oSinglePrediction.getIntention().getSecondaryComponent().getMoDataStructure()
				    .getMoDSInstance_ID()) {
					oRetVal.addAll(oSinglePrediction.getExpectations());
					break;
				}
			}
		}

		return oRetVal;
	}

//	/**
//	 * Extracts the associated acts from a list of expectations. An expectation could have several acts associated (wendt)
//	 * 
//	 * @since 02.08.2011 10:13:28
//	 * 
//	 * @param poExpectations
//	 * @return
//	 */
//	private ArrayList<clsSecondaryDataStructureContainer> getActsFromExpectations(ArrayList<clsDataStructureContainerPair> poExpectations) {
//		ArrayList<clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
//
//		// Go through all expectations
//		for (clsDataStructureContainerPair oSContainer : poExpectations) {
//			// Check associated data structures with the act
//			for (clsAssociation oAss : oSContainer.getSecondaryComponent().getMoAssociatedDataStructures()) {
//				if (oAss instanceof clsAssociationSecondary) {
//					// If the predicate of the association is a HASASSOCIATION,
//					// then
//					if (((clsAssociationSecondary) oAss).getMoPredicate() == ePredicate.HASASSOCIATION.toString()) {
//						ArrayList<clsAssociation> oAssociatedStructures = new ArrayList<clsAssociation>();
//
//						// Check if the act is the leaf or the root element and
//						// create a new data structure container
//						if (oAss.getLeafElement() instanceof clsAct) {
//							// oRetVal.add((clsAct) oAss.getLeafElement());
//							// Add the intention
//							clsAssociationSecondary oAssSec = (clsAssociationSecondary) clsDataStructureGenerator.generateASSOCIATIONSEC(eContentType.ASSOCIATIONSECONDARY, oAss.getLeafElement(), oAss.getRootElement(), ePredicate.HASASSOCIATION.toString(), 1.0);
//							oAssociatedStructures.add(oAssSec);
//							oRetVal.add(new clsSecondaryDataStructureContainer((clsSecondaryDataStructure) oAss.getLeafElement(), oAssociatedStructures));
//						} else if (oAss.getRootElement() instanceof clsAct) {
//							clsAssociationSecondary oAssSec = (clsAssociationSecondary) clsDataStructureGenerator.generateASSOCIATIONSEC(
//									eContentType.ASSOCIATIONSECONDARY, oAss.getRootElement(), oAss.getLeafElement(), ePredicate.HASASSOCIATION.toString(), 1.0);
//							oAssociatedStructures.add(oAssSec);
//							oRetVal.add(new clsSecondaryDataStructureContainer((clsSecondaryDataStructure) oAss.getRootElement(), oAssociatedStructures));
//							// oRetVal.add((clsAct) oAss.getRootElement());
//						}
//					}
//				}
//			}
//		}
//
//		return oRetVal;
//	}

	/**
	 * DOCUMENT (wendt) - insert description
	 * 
	 * @since 16.09.2011 08:50:51
	 * 
	 * @param poContent
	 * @return
	 */
	private String getActionStringFromContent(String poContent) {
		String oRetVal = "";
		// Input Structure
		// "FORWARD|PRECONDITION|LOCATION:MANIPULATEABLE|ENTITY:ENTITY||ACTION|ACTION:MOVE_FORWARD||CONSEQUENCE|LOCATION:EATABLE|ENTITY:ENTITY|";

		// Output structure
		// MOVE_FORWARD

		String[] oParts = poContent.split("\\|");
		for (String oE : oParts) {
			if (oE.contains("ACTION:")) {
				String oActionString = "ACTION:";
				oRetVal = oE.substring(oActionString.length(), oE.length());
				break;
			}
		}

		return oRetVal;
	}

//	/**
//	 * For a goal, extract all expectations, which are bound to it through the intention (wendt)
//	 * 
//	 * @since 26.09.2011 09:00:44
//	 * 
//	 * @param poGoal
//	 * @param poPredictionList
//	 * @return
//	 */
//	private ArrayList<clsDataStructureContainerPair> getExpectationFromGoal(clsSecondaryDataStructureContainer poGoal,
//	    ArrayList<clsPrediction> poPredictionList) {
//		ArrayList<clsDataStructureContainerPair> oRetVal = new ArrayList<clsDataStructureContainerPair>();
//
//		// Get the intentionDS
//		ArrayList<clsSecondaryDataStructure> oIntentionDSList = clsDataStructureTools.getDSFromSecondaryAssInContainer(poGoal,
//		    ePredicate.HASINTENTION.toString(), false);
//		for (clsSecondaryDataStructure oIntention : oIntentionDSList) {
//			// For a certain intention, get all expectations
//			ArrayList<clsDataStructureContainerPair> oExpectations = getExpectationFromPredictionList((clsSecondaryDataStructure) oIntention,
//			    poPredictionList);
//			// Add the intention as associated memories to the acts, the
//			// intention is added to the act
//
//			oRetVal.addAll(oExpectations);
//			// getActsFromExpectations(oExpectations, oIntention)
//		}
//
//		return oRetVal;
//	}

//	/**
//	 * If a the associated content shall be a phantasy, this function is applied on the action-container. It sets a new association to a
//	 * predefined word presentation, which is asked for in F47
//	 * 
//	 * (wendt)
//	 * 
//	 * @since 16.09.2011 20:47:01
//	 * 
//	 * @param poActioncommandContainer
//	 */
//	private void setConsciousPhantasyActivation(clsSecondaryDataStructureContainer poActioncommandContainer) {
//		clsDataStructureTools.setAttributeWordPresentation(poActioncommandContainer, ePredicate.ACTIVATESPHANTASY.toString(),
//		    "ACTIVATEPHANTASY", "TRUE");
//	}

//	/**
//	 * Print goal, prediction and the actions (wendt)
//	 * 
//	 * @since 26.09.2011 16:31:19
//	 * 
//	 * @param poAction
//	 * @param poGoal
//	 * @param poPrediction
//	 */
//	private void printData(ArrayList<clsSecondaryDataStructureContainer> poAction, ArrayList<clsSecondaryDataStructureContainer> poGoal,
//	    ArrayList<clsPrediction> poPrediction) {
//		String oPrintoutPlan = "Action: ";
//
//		for (clsSecondaryDataStructureContainer oC : poAction) {
//			// AW HACK test, in order to be able to use both WP and plan
//			// fragements at the same time
//			boolean bPlanFragement = false;
//			if (oC instanceof clsPlanFragment) {
//				bPlanFragement = true;
//				// break;
//			}
//
//			if (bPlanFragement == true) {
//				oPrintoutPlan += ((clsPlanFragment) oC).m_act.m_strAction + "; ";
//			} else {
//				oPrintoutPlan += oC.getMoDataStructure().toString() + "; ";
//			}
//		}
//
//		String oPrintoutGoal = "Goal: ";
//		for (clsSecondaryDataStructureContainer oC : poGoal) {
//			oPrintoutGoal += oC.getMoDataStructure().toString() + ";";
//		}
//
//		String oPrintoutPrediction = PredictionToText(poPrediction);
//
////		System.out.println("BEGIN" + this.hashCode());
////		System.out.println(oPrintoutPrediction);
////		System.out.println(oPrintoutGoal);
////		System.out.println(oPrintoutPlan);
////		System.out.println("END");
//
//	}

//	/**
//	 * DOCUMENT (wendt) - insert description
//	 * 
//	 * @since 10.09.2011 16:29:58
//	 * 
//	 * @param poExtractedPrediction_IN
//	 */
//	private String PredictionToText(ArrayList<clsPrediction> poExtractedPrediction_IN) {
//
//		String oStepInfo = "";
//		String oMomentInfo = "Prediction: ";
//
//		for (clsPrediction oP : poExtractedPrediction_IN) {
//
//			oMomentInfo += oP.toString();
//
//			if (oP.getMoment().getPrimaryComponent() != null) {
//				double rMatch = clsDataStructureTools.getMatchValueToPI(oP.getMoment().getPrimaryComponent());
//				oMomentInfo += "|Match: " + rMatch;
//			}
//
//			if (oP.getIntention().getSecondaryComponent() != null) {
//				oMomentInfo += "|Progress: " + clsActTools.getTemporalProgress(oP.getIntention().getSecondaryComponent());
//				oMomentInfo += "|Confirm: " + clsActTools.getConfirmProgress(oP.getIntention().getSecondaryComponent());
//				oMomentInfo += "|Exp:" + clsActTools.getExpectationAlreadyConfirmed(oP.getIntention().getSecondaryComponent());
//			}
//
//			oStepInfo += oMomentInfo + "; ";
//
//		}
//		return oStepInfo;
//	}

	public ArrayList<clsWordPresentationMesh> copyPlanFragments(ArrayList<clsPlanFragment> myPlans) {
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
			clsWordPresentationMesh oAction = clsActionTools.createAction(oPF.m_act.m_strAction);
			
			clsWordPresentationMesh oGoalObject = clsDataStructureGenerator.generateWPM(new clsPair<eContentType, Object>(eContentType.ENTITY,oPF.m_effectImage.m_eObj.toString()), new ArrayList<clsAssociation>());
			try {
				clsMeshTools.setWP(oGoalObject, eContentType.DISTANCEASSOCIATION, ePredicate.HASDISTANCE, eContentType.DISTANCE, oPF.m_effectImage.m_eDist.toString());
			} catch (Exception e) {
				System.out.println(oPF.m_effectImage.toString());
				e.printStackTrace();
			}
			
			clsMeshTools.setWP(oGoalObject, eContentType.DISTANCEASSOCIATION, ePredicate.HASPOSITION, eContentType.POSITION, oPF.m_effectImage.m_eDir.toString());
			
			clsMeshTools.createAssociationSecondary(oAction, 2, oGoalObject, 2, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASASSOCIATION, false);
			oRetVal.add(oAction);
		}

		// add perception
		return oRetVal;
	}

	public ArrayList<clsWordPresentationMesh> copyPlanFragments_AP(ArrayList<clsPlanFragment> myPlans) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		ArrayList<clsPlanFragment> moPlans = new ArrayList<clsPlanFragment>();

		for (clsPlanFragment plFr : myPlans) {
			moPlans.add(plFr);
		}

		// Convert the containers into WPM
		for (clsPlanFragment oPF : moPlans) {
			clsWordPresentationMesh oAction = clsDataStructureGenerator.generateWPM(new clsPair<eContentType, Object>(eContentType.ACTION,
			    oPF.m_act.m_strAction), new ArrayList<clsAssociation>());
			oRetVal.add(oAction);
		}

		// add perception
		return oRetVal;
	}

	/***********************************************************************************************
	 * END class specific methods (e.g. planning methods)
	 **********************************************************************************************/

	/***********************************************************************************************
	 * BEGINN inspector specific functions
	 **********************************************************************************************/

	/*
	 * (non-Javadoc)
	 * 
	 * @author brandstaetter 29.11.2010, 15:00:00
	 * 
	 * @see pa.interfaces.itfTimeChartInformationContainer#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oRetVal = new ArrayList<Double>();
		double rNUM_IMAGINARY_ACTIONS = moPlans_Output.size();
		oRetVal.add(rNUM_IMAGINARY_ACTIONS);
		return oRetVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author brandstaetter 29.11.2010, 15:00:00
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartAxis()
	 */
	@Override
	public String getTimeChartAxis() {
		return "Number of Imaginary Action Commands";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author brandstaetter 29.11.2010, 15:00:00
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		return "Number of Imaginary Action Commands";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author brandstaetter 29.11.2010, 15:00:00
	 * 
	 * @see pa.interfaces.itfTimeChartInformationContainer#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oCaptions = new ArrayList<String>();
		oCaptions.add("rNUM_IMAGINARY_ACTIONS");
		return oCaptions;
	}



	/***********************************************************************************************
	 * END inspector specific functions
	 **********************************************************************************************/

}
