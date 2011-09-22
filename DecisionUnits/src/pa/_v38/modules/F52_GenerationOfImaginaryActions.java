/**
 * E27_GenerationOfImaginaryActions.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:55:01
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.modules.I6_8_receive;
import pa._v38.interfaces.modules.I6_9_receive;
import pa._v38.interfaces.modules.I6_9_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAct;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainerPair;
import pa._v38.memorymgmt.datatypes.clsImage;
import pa._v38.memorymgmt.datatypes.clsPlanFragment;
import pa._v38.memorymgmt.datatypes.clsPrediction;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.tools.clsAffectTools;
import pa._v38.tools.clsDataStructureTools;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsPredictionTools;
import pa._v38.tools.toText;
import pa._v38.tools.planningHelpers.PlanningGraph;
import pa._v38.tools.planningHelpers.PlanningNode;
import pa._v38.tools.planningHelpers.PlanningWizard;
import pa._v38.tools.planningHelpers.eDirection;
import pa._v38.tools.planningHelpers.eDistance;
import pa._v38.tools.planningHelpers.eEntity;
import config.clsProperties;

/**
 * DOCUMENT (perner) - insert description
 * 
 * @author deutsch 11.08.2009, 14:55:01
 * 
 */
public class F52_GenerationOfImaginaryActions extends clsModuleBaseKB implements
		I6_8_receive, I6_9_send {
	public static final String P_MODULENUMBER = "52";

	// HZ Not used up to now 16.03.2011
	private ArrayList<clsSecondaryDataStructureContainer> moGoalInput;
	private ArrayList<ArrayList<clsAct>> moPlanInput;
	
	/** DOCUMENT (wendt) - insert description; @since 31.07.2011 21:25:26 */
	private ArrayList<clsPrediction> moExtractedPrediction_IN;
	
	private clsDataStructureContainerPair moEnvironmentalPerception_IN;
	
	private clsDataStructureContainerPair moEnvironmentalPerception_OUT;
	
	/** Associated memories in */
	private ArrayList<clsDataStructureContainer> moAssociatedMemories_IN;
	
	/** Associated memories out */
	private ArrayList<clsDataStructureContainer> moAssociatedMemories_OUT;
	
	private ArrayList<clsSecondaryDataStructureContainer> moActions_Output;

	private ArrayList<clsPlanFragment> moAvailablePlanFragments; //TD 2011/07/21 - changed name to fit coding guidelines
	
	private ArrayList<clsPlanFragment> moCurrentApplicalbePlans; //TD 2011/07/21 - changed name to fit coding guidelines

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
	public F52_GenerationOfImaginaryActions(String poPrefix,
			clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			clsKnowledgeBaseHandler poKnowledgeBaseHandler) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData,
				poKnowledgeBaseHandler);
		
		moAvailablePlanFragments = new ArrayList<clsPlanFragment>();
		
		applyProperties(poPrefix, poProp);

		// just used to test if the planning module does not have any compile errors
		generateTestData(); // TD 2011/07/26: moved to constructor. list grew by nine identical elements each iteration.
		
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
		text += toText.listToTEXT("moExtractedPrediction_IN", moExtractedPrediction_IN);
		text += toText.listToTEXT("moActions_Output", moActions_Output);
		text += toText.listToTEXT("moGoalInput", moGoalInput);
		text += toText.listToTEXT("moAssociatedMemories_OUT", moAssociatedMemories_OUT);

		return text;
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);

		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre + P_PROCESS_IMPLEMENTATION_STAGE,
				eImplementationStage.BASIC.toString());

		return oProp;
	}

	private void applyProperties(String poPrefix, clsProperties poProp) {
		// String pre = clsProperties.addDot(poPrefix);

		// nothing to do
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
	 * @author deutsch 11.08.2009, 16:16:38
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		//If the goal is a prediction, execute the default action, else use standard planning, i.e. if the goal is to use the PI
		String oPI = "PERCEIVEDIMAGE";
		String oRI = "IMAGE";
		if (moGoalInput.isEmpty()==false) {
			if (((clsWordPresentation)moGoalInput.get(0).getMoDataStructure()).getMoContent().contains(oPI)==true) {
				moActions_Output = planPerception(moEnvironmentalPerception_IN, moGoalInput);
			} else if (((clsWordPresentation)moGoalInput.get(0).getMoDataStructure()).getMoContent().contains(oRI)==true) {
				//AW: not finished expectation generation for testing, has to influence plan generation later
				moActions_Output = TestAWsExpectations(moExtractedPrediction_IN, moGoalInput);
				
			} else {
				moActions_Output = planPerception(moEnvironmentalPerception_IN, moGoalInput);
			}
			
			
		}
		
		//Pass forward the associated memories and perception
		try {
			moEnvironmentalPerception_OUT = (clsDataStructureContainerPair) moEnvironmentalPerception_IN.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		moAssociatedMemories_OUT = (ArrayList<clsDataStructureContainer>)deepCopy(moAssociatedMemories_IN);
		
		// HZ 2010.08.28
		// E27 should retrieve required acts through E28. However, it can be
		// doubted if this works without a loop between E27 and E28. In addition
		// the functionality of
		// E28 has to be discussed as it should only access the memory and
		// retrieve acts.
		// Reasons for my doubts: Actually E28 receives (like E27) the current
		// goal
		// that is formed out of a drive that should be satisfied and the object
		// that should be used to satisfy it. Now it can be searched in the
		// memory which
		// actions have to be set to be able to satisfy the drive (e.g. action
		// EAT in
		// order to NOURISH a CAKE). However, in general the required object is
		// not
		// in the right position in order to use the action on it (A cake can
		// only be eaten
		// in case it is in the eatable area). Hence other Acts have to be
		// triggered that
		// help to put the agent into the right position. These acts are not
		// part
		// of the act "eat cake". They would be accomplished before the cake can
		// be
		// eaten. Hence the plan has to be rebuild by single acts that can only
		// be
		// retrieved from the memory in case there is a loop between E27 and E28
		// or
		// E27 has a memory access on its own => E28 woul dbe senseless.
		//
		// Until this question has been solved, E28
		// is implemented to retrieve and put acts together which means that it
		// takes over
		// a kind of planning.
		
		//printData(moActions_Output, moGoalInput, moExtractedPrediction_IN);
		
	}

	/**
	 * @author zeilinger 02.09.2010, 19:48:48
	 * 
	 * @return the moActions_Output
	 */
	public ArrayList<clsSecondaryDataStructureContainer> getMoActions_Output() {
		return moActions_Output;
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
	public void receive_I6_8(
			ArrayList<clsSecondaryDataStructureContainer> poGoalInput, clsDataStructureContainerPair poEnvironmentalPerception, ArrayList<clsPrediction> poExtractedPrediction, ArrayList<clsDataStructureContainer> poAssociatedMemories) {
		moGoalInput = (ArrayList<clsSecondaryDataStructureContainer>) deepCopy(poGoalInput);
		moExtractedPrediction_IN = (ArrayList<clsPrediction>)deepCopy(poExtractedPrediction);
		moAssociatedMemories_IN = (ArrayList<clsDataStructureContainer>)deepCopy(poAssociatedMemories);
		try {
			moEnvironmentalPerception_IN = (clsDataStructureContainerPair)poEnvironmentalPerception.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		// FIXME (perner) - please create more meaningfull debbuging output 
		// (something like System.out.println("F52_GenerationOfImaginaryActions.receive_I6_8: "+poGoalInput.size());). 
		// or - much better - use the inspectors (e.g. stateToText()) for such output. if every of the 33 modules has 1-2 
		// such println lines, no one will be able to extract any meaningfull information from the flood auf console output! //TD 2011/07/19
		//System.out.println(poGoalInput.size()); 
				
	}
	
	/**
	 * Fill m_availablePlanFragments with test data.
	 *
	 * @since 19.07.2011 10:24:29
	 *
	 */
	private void generateTestData() {
		/**
		 * test test dummy to fill internal database
		 */

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("MOVE_FORWARD"),
				new clsImage(eDistance.FAR, eDirection.CENTER, eEntity.CAKE), 
				new clsImage(eDistance.MEDIUM, eDirection.CENTER, eEntity.CAKE)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("MOVE_FORWARD"),
				new clsImage(eDistance.MEDIUM, eDirection.CENTER, eEntity.CAKE), 
				new clsImage(eDistance.NEAR, eDirection.CENTER, eEntity.CAKE)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("EAT"),
				new clsImage(eDistance.NEAR, eDirection.CENTER, eEntity.CAKE), 
				new clsImage(eDistance.NEAR, eDirection.CENTER, eEntity.CAKE)));
	
		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("MOVE_BACKWARD"),
				new clsImage(eDistance.NEAR, eDirection.CENTER, eEntity.CAKE), 
				new clsImage(eDistance.MEDIUM, eDirection.CENTER, eEntity.CAKE)));
		
		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("MOVE_BACKWARD"),
				new clsImage(eDistance.MEDIUM, eDirection.CENTER, eEntity.CAKE), 
				new clsImage(eDistance.FAR, eDirection.CENTER, eEntity.CAKE)));
		
		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("TURN_LEFT"),
				new clsImage(eDirection.LEFT, eEntity.CAKE), 
				new clsImage(eDirection.MIDDLELEFT, eEntity.CAKE)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("TURN_LEFT"),
				new clsImage(eDirection.MIDDLELEFT, eEntity.CAKE), 
				new clsImage(eDirection.CENTER, eEntity.CAKE)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("TURN_RIGHT"),
				new clsImage(eDirection.RIGHT, eEntity.CAKE), 
				new clsImage(eDirection.MIDDLERIGHT, eEntity.CAKE)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("TURN_RIGHT"),
				new clsImage(eDirection.MIDDLERIGHT, eEntity.CAKE), 
				new clsImage(eDirection.CENTER, eEntity.CAKE)));
		
		
/*		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("SEARCH1"),
				new clsImage(eEntity.NONE), 
				new clsImage(eDirection.CENTER, eEntity.CAKE)));
		
		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("SEARCH2"),
				new clsImage(eEntity.NONE), 
				new clsImage(eDirection.RIGHT, eEntity.CAKE)));
		
		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("SEARCH3"),
				new clsImage(eEntity.NONE), 
				new clsImage(eDirection.LEFT, eEntity.CAKE)));
		
		*/
		
		// AP: Who added this action? -> Entity none is not defined for searches!
		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("DEPOSIT"),
				new clsImage(), 
				new clsImage()));
		
		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("SLEEP"),
				new clsImage(), 
				new clsImage()));
		
		
		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("FLEE"),
				new clsImage(), 
				new clsImage()));
		
		
		
		// TODO (perner) add generic actions like right, left without objects
		/**
		 * test
		 */		
	}
	
	/**
	 * Generate test data of one act
	 *
	 * @since 20.07.2011 13:42:13
	 *
	 * @return
	 */
	/*private clsSecondaryDataStructureContainer getTestDataForAct() {
		clsSecondaryDataStructureContainer oWPActContainer = null;
			
		//Acts are retrieved by the consequence they have on the agent - hence the content String of the Act is constructed
		//here - only the consequence part is filled
		//The string looks like: "PRECONDITION||ACTION||CONSEQUENCE|NOURISH" or "PRECONDITION||ACTION||CONSEQUENCE|LOCATION:xy|"
		String oContent = "FORWARD|PRECONDITION|LOCATION:MANIPULATEABLE|ENTITY:ENTITY||ACTION|ACTION:MOVE_FORWARD||CONSEQUENCE|LOCATION:EATABLE|ENTITY:ENTITY|";
		String oActualGoal = oContent;
		
		clsAct oAct = (clsAct)clsDataStructureGenerator.generateACT(new clsTriple <String, ArrayList<clsSecondaryDataStructure>, Object>(
				eDataType.ACT.name(), new ArrayList<clsSecondaryDataStructure>(), oActualGoal));
		
		//clsAct oAct = generateAct(oActualGoal); 
		//ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> oSearchResult = new ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>>();
		
		//ArrayList<clsPrimaryDataStructureContainer> oPerceivedImage_IN = new ArrayList<clsPrimaryDataStructureContainer>();
		ArrayList<clsAct> oActList = new ArrayList<clsAct>();
		
		oActList.add(oAct);
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = 
			new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
		
		search(eDataType.WP, oActList, oSearchResult); 
		
		if (oSearchResult.isEmpty()==false) {
			oWPActContainer = (clsSecondaryDataStructureContainer) oSearchResult.get(0).get(4).b;	//Hack
		}
		//Not safe operation but it does not matter for test data
		//oWPActContainer = (clsSecondaryDataStructureContainer) searchCompleteContainer(oWPActContainer.getMoDataStructure());
		
		return oWPActContainer;
	}*/
	
	/**
	 * From a list of plans (clsAct or other type), where there are associations to other memories in the secondary process,
	 * extract those direct associated memories as complete containers. The result is a mix of secondary process containers
	 * and primary process containers, where the link between the secondary process containers and the primary process containers
	 * is found in an clsAssociationWP
	 * 
	 * wendt
	 * @since 20.07.2011 13:42:05
	 *
	 * @param poInput
	 * @return
	 */
	/*private ArrayList<clsDataStructureContainer> getAssociatedMemoriesFromPlans(ArrayList<clsSecondaryDataStructureContainer> poActions_Output, ArrayList<clsPrediction> poPrediction) {
		ArrayList<clsDataStructureContainer> oRetVal = new ArrayList<clsDataStructureContainer>();
		
		
		//Go through all plans/acts and extract all associated memories (WP)
		ArrayList<clsDataStructureContainer> oAssociatedMemoryList = new ArrayList<clsDataStructureContainer>();
		for (clsSecondaryDataStructureContainer oPlan : poActions_Output) {
			oAssociatedMemoryList = extractAssociatedContainers(oPlan);
		}
		
		oRetVal.addAll(oAssociatedMemoryList);
		//Go through all extracted associated WP-Memories and extract their primary structure parts
		for (clsDataStructureContainer oAssociatedMemory : oAssociatedMemoryList) {
			if (oAssociatedMemory instanceof clsSecondaryDataStructureContainer) {
				oRetVal.add(extractPrimaryContainer((clsSecondaryDataStructureContainer)oAssociatedMemory));
			}
		}
		
		return oRetVal;
	}*/
	
	/**
	 * From the goal list, first, get the intention, then get the expectation and then get the action (clsAct) which is associated with it.
	 * (wendt)
	 *
	 * @since 01.08.2011 16:42:56
	 *
	 * @param poDriveGoalsInput
	 * @param poPrediction
	 * @return
	 */
	private ArrayList<clsSecondaryDataStructureContainer> getActsFromExpectation(ArrayList<clsSecondaryDataStructureContainer> poDriveGoalsInput, ArrayList<clsPrediction> poPrediction) {
		ArrayList<clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
		
		//Go through all goals
		for (clsSecondaryDataStructureContainer oGoal : poDriveGoalsInput) {
			//Get the intentionDS
			ArrayList<clsSecondaryDataStructure> oIntentionDSList = clsDataStructureTools.getDSFromSecondaryAssInContainer(oGoal, ePredicate.HASINTENTION.toString(), false);
			for (clsSecondaryDataStructure oIntention : oIntentionDSList) {
				ArrayList<clsSecondaryDataStructureContainer> oExpectations = getExpectationFromPredictionList((clsSecondaryDataStructure) oIntention, poPrediction);
				//Add the intention as associated memories to the acts, the intention is added to the act
				//for ()
				
				
				oRetVal.addAll(getActsFromExpectations(oExpectations, oIntention));
			}
		}
		
		return oRetVal;
	}
	
	/**
	 * Extract the list of secondary structure expectations from a list of predictions for a certain intention
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 01.08.2011 16:59:46
	 *
	 * @param poIntention
	 * @param poPrediction
	 * @return
	 */
	private ArrayList<clsSecondaryDataStructureContainer> getExpectationFromPredictionList(clsSecondaryDataStructure poIntention, ArrayList<clsPrediction> poPrediction) {
		ArrayList<clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
		
		if (poIntention!=null) {
			for (clsPrediction oSinglePrediction : poPrediction) {
				//If the instanceIDs are the same in the prediction, the give back 
				if (poIntention.getMoDSInstance_ID()==oSinglePrediction.getIntention().getSecondaryComponent().getMoDataStructure().getMoDSInstance_ID()) {
					for (clsDataStructureContainerPair oCPair : oSinglePrediction.getExpectations()) {
						oRetVal.add(oCPair.getSecondaryComponent());
					}
						
				}
			}
		}

		return oRetVal;
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 02.08.2011 10:13:28
	 *
	 * @param poExpectations
	 * @return
	 */
	private ArrayList<clsSecondaryDataStructureContainer> getActsFromExpectations(ArrayList<clsSecondaryDataStructureContainer> poExpectations, clsSecondaryDataStructure poIntention) {
		ArrayList<clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
		
		for (clsSecondaryDataStructureContainer oSContainer : poExpectations) {
			for (clsAssociation oAss : oSContainer.getMoAssociatedDataStructures()) {
				if (oAss instanceof clsAssociationSecondary) {
					if (((clsAssociationSecondary)oAss).getMoPredicate() == "HASASSOCIATION") {
						ArrayList<clsAssociation> oAssociatedStructures = new ArrayList<clsAssociation>();
						//oAssociatedStructures.add(oAss);
						
						if (oAss.getLeafElement() instanceof clsAct) {
							//Add the intention
							clsAssociationSecondary oAssSec = (clsAssociationSecondary) clsDataStructureGenerator.generateASSOCIATIONSEC("ASSOCIATIONSECONDARY", oAss.getLeafElement(), poIntention, "HASASSOCIATION", 1.0);
							oAssociatedStructures.add(oAssSec);
							oRetVal.add(new clsSecondaryDataStructureContainer((clsSecondaryDataStructure) oAss.getLeafElement(), oAssociatedStructures));
						} else if (oAss.getRootElement() instanceof clsAct) {
							clsAssociationSecondary oAssSec = (clsAssociationSecondary) clsDataStructureGenerator.generateASSOCIATIONSEC("ASSOCIATIONSECONDARY", oAss.getRootElement(), poIntention, "HASASSOCIATION", 1.0);
							oAssociatedStructures.add(oAssSec);
							oRetVal.add(new clsSecondaryDataStructureContainer((clsSecondaryDataStructure) oAss.getRootElement(), oAssociatedStructures));						
						}
					}
				}
			}
		}
		
		return oRetVal;
	}

	/**
	 * Temp function, extract the exact action commands from a template act
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 02.08.2011 10:14:54
	 *
	 * @param poActs
	 * @return
	 */
	private ArrayList<clsSecondaryDataStructureContainer> GetActionCommandFromAct(ArrayList<clsSecondaryDataStructureContainer> poActs) {
		ArrayList<clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
		
		for (clsSecondaryDataStructureContainer oContainer : poActs) {
			clsAct oAct = (clsAct) oContainer.getMoDataStructure();
			String oActContent = oAct.getMoContent();
			
			String oNewActionCommand = getActionFromContent(oActContent);
			oRetVal.add(new clsSecondaryDataStructureContainer(clsDataStructureGenerator.generateWP(new clsPair<String, Object>("ACTION", oNewActionCommand)), oContainer.getMoAssociatedDataStructures()));
		}
		
		return oRetVal;
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 16.09.2011 08:50:51
	 *
	 * @param poContent
	 * @return
	 */
	private String getActionFromContent(String poContent) {
		String oRetVal = "";
		//Input Structure
		//"FORWARD|PRECONDITION|LOCATION:MANIPULATEABLE|ENTITY:ENTITY||ACTION|ACTION:MOVE_FORWARD||CONSEQUENCE|LOCATION:EATABLE|ENTITY:ENTITY|";
		
		//Output structure
		//MOVE_FORWARD
		
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

	
	
	private void printData(ArrayList<clsSecondaryDataStructureContainer> poAction, ArrayList<clsSecondaryDataStructureContainer> poGoal, ArrayList<clsPrediction> poPrediction) {
		String oPrintoutPlan = "Action: ";
		
		for (clsSecondaryDataStructureContainer oC : poAction) {
			//AW HACK test, in order to be able to use both WP and plan fragements at the same time
			boolean bPlanFragement = false;
			if (oC instanceof clsPlanFragment) {
				bPlanFragement = true;
				//break;
			}
			
			if (bPlanFragement==true) {
				oPrintoutPlan += ((clsPlanFragment)oC).m_act.m_strAction + "; ";
			} else {
				oPrintoutPlan += oC.getMoDataStructure().toString() + "; ";
			}
		}
		
		String oPrintoutGoal = "Goal: ";
		for (clsSecondaryDataStructureContainer oC : poGoal) {
			oPrintoutGoal += oC.getMoDataStructure().toString() + ";";
		}
		
		String oPrintoutPrediction = PredictionToText(poPrediction);
		
		System.out.println("BEGIN");
		System.out.println(oPrintoutPrediction);
		System.out.println(oPrintoutGoal);
		System.out.println(oPrintoutPlan);
		System.out.println("END");
		
	}
	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 10.09.2011 16:29:58
	 *
	 * @param poExtractedPrediction_IN
	 */
	private String PredictionToText(ArrayList<clsPrediction> poExtractedPrediction_IN) {
		
		String oStepInfo = "";
		String oMomentInfo = "Prediction: ";
		
		for (clsPrediction oP : poExtractedPrediction_IN) {
			
			oMomentInfo += oP.toString();
			
			if (oP.getMoment().getPrimaryComponent()!=null) {
				double rMatch = clsDataStructureTools.getMatchValueToPI(oP.getMoment().getPrimaryComponent());
				oMomentInfo += "|Match: " + rMatch;
			}
			
			if (oP.getIntention().getSecondaryComponent()!=null) {
				oMomentInfo += "|Progress: " + clsPredictionTools.getTemporalProgress(oP.getIntention().getSecondaryComponent());
				oMomentInfo += "|Confirm: " + clsPredictionTools.getConfirmProgress(oP.getIntention().getSecondaryComponent());
				oMomentInfo += "|Exp:" + clsPredictionTools.getExpectationAlreadyConfirmed(oP.getIntention().getSecondaryComponent());
			}
			
			oStepInfo += oMomentInfo + "; ";
			
		}
		
		return oStepInfo;
		
		/*if (poExtractedPrediction_IN.isEmpty()==true) {
			System.out.print(oStepInfo + "nothing");
		} else {
			System.out.print(oStepInfo);
		}*/
		
	}
	
	private ArrayList<clsSecondaryDataStructureContainer> TestAWsExpectations(ArrayList<clsPrediction> poExtractedPrediction_IN, ArrayList<clsSecondaryDataStructureContainer> poGoalInput){
		ArrayList<clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
		ArrayList<clsSecondaryDataStructureContainer> oExtractedActs = new ArrayList<clsSecondaryDataStructureContainer>();
		
		
		ArrayList<clsSecondaryDataStructureContainer> oFirstGoal = new ArrayList<clsSecondaryDataStructureContainer>();
		if (poGoalInput.isEmpty()==false) {
			oFirstGoal.add(poGoalInput.get(0));
		}
		oExtractedActs = getActsFromExpectation(oFirstGoal, poExtractedPrediction_IN);
		
		ArrayList<clsSecondaryDataStructureContainer> oActions = GetActionCommandFromAct(oExtractedActs);
		
		//Set that all associated action shall not trigger any phantasies
		for (clsSecondaryDataStructureContainer oAction :  oActions) {
			//setConsciousPhantasyActivation(oAction);
		}
		
		//AW 20110720: This function extracts the associated memories from the plans. It has to be done here, as
		//F47 does not have any memory access. 
		//If you receive errors in this function, you may inactivate it and AW will correct the errors.
		//moAssociatedMemories_OUT = getAssociatedMemoriesFromPlans(oActions);
		
		oRetVal = oActions;
		
		return oRetVal;
	}
	
	/**
	 * If a the associated content shall be a phantasy, this function is applied on the action-container. It sets a new association to a predefined 
	 * word presentation, which is asked for in F47
	 * (wendt)
	 *
	 * @since 16.09.2011 20:47:01
	 *
	 * @param poActioncommandContainer
	 */
	private void setConsciousPhantasyActivation(clsSecondaryDataStructureContainer poActioncommandContainer) {
		clsDataStructureTools.setAttributeWordPresentation(poActioncommandContainer, ePredicate.ACTIVATESPHANTASY.toString(), "ACTIVATEPHANTASY", "TRUE");
	}

		
	/**
	 * DOCUMENT (zeilinger) - insert description
	 * 
	 * @author zeilinger 02.09.2010, 19:46:45
	 * @return
	 * 
	 */
//	private ArrayList<clsSecondaryDataStructureContainer> getActions() {
//		ArrayList<clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
//		ArrayList<clsAct> oPlan = evaluatePlans();
//
//		for (clsAct oAct : oPlan) {
//			for (clsSecondaryDataStructure oSD : oAct.getMoAssociatedContent()) {
//				if (oSD instanceof clsWordPresentation
//						&& oSD.getMoContentType().equals(
//								eActState.ACTION.name())) {
//					// AW 20110629: Changed ArrayList<clsWordPresentation> to
//					// ArrayList<clsSecondaryDataStructureContainer> in order to
//					// fulfill
//					// requirements of the interfaces
//					clsSecondaryDataStructureContainer oPlanContainer = new clsSecondaryDataStructureContainer(
//							(clsWordPresentation) oSD,
//							new ArrayList<clsAssociation>());
//					oRetVal.add(oPlanContainer);
//
//					return oRetVal;
//				}
//			}
//		}
//
//		return oRetVal;
//	}

//	/**
//	 * DOCUMENT (zeilinger) - insert description
//	 * 
//	 * edit-perner -> this should happen in module 29
//	 * 
//	 * @author zeilinger 03.09.2010, 17:19:37
//	 * 
//	 * @return
//	 */
//	@Deprecated
//	private ArrayList<clsAct> evaluatePlans() {
//		// HZ This method evaluates the retrieved plans. Actually this is rather
//		// simple
//		// as only the number of acts that are required to fulfill the plan are
//		// used
//		// for this evaluation (the plan with the fewest number of acts is
//		// selected)
//		ArrayList<clsAct> oRetVal = new ArrayList<clsAct>();
//
//		// HZ obsolete as long asthe functionality of v30_E28 is not integrated
//		// in F52
//		// for(ArrayList<clsAct> oEntry : moPlanInput){
//		// if((oRetVal.size() == 0) || (oRetVal.size() > oEntry.size())){
//		// oRetVal = oEntry;
//		// }
//		// }
//
//		return oRetVal;
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author deutsch 11.08.2009, 16:16:38
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I6_9(moActions_Output, moAssociatedMemories_OUT, moEnvironmentalPerception_OUT);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author deutsch 18.05.2010, 17:56:21
	 * 
	 * @see pa.interfaces.send.I7_3_send#send_I7_3(java.util.ArrayList)
	 */
	@Override
	public void send_I6_9(
			ArrayList<clsSecondaryDataStructureContainer> poActionCommands, ArrayList<clsDataStructureContainer> poAssociatedMemories, clsDataStructureContainerPair poEnvironmentalPerception) {
		((I6_9_receive) moModuleList.get(8)).receive_I6_9(poActionCommands, poAssociatedMemories, poEnvironmentalPerception);
		((I6_9_receive) moModuleList.get(20)).receive_I6_9(poActionCommands, poAssociatedMemories, poEnvironmentalPerception);
		((I6_9_receive) moModuleList.get(21)).receive_I6_9(poActionCommands, poAssociatedMemories, poEnvironmentalPerception);
		((I6_9_receive) moModuleList.get(29)).receive_I6_9(poActionCommands, poAssociatedMemories, poEnvironmentalPerception);
		((I6_9_receive) moModuleList.get(47)).receive_I6_9(poActionCommands, poAssociatedMemories, poEnvironmentalPerception);
		((I6_9_receive) moModuleList.get(53)).receive_I6_9(poActionCommands, poAssociatedMemories, poEnvironmentalPerception);

		putInterfaceData(I6_9_send.class, poActionCommands, poAssociatedMemories);

	}
	
	private ArrayList<clsSecondaryDataStructureContainer> planPerception(clsDataStructureContainerPair poEnvironmentalPerception, ArrayList<clsSecondaryDataStructureContainer> poGoalList) {

		ArrayList<clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
		
		// create dummy value here
		//moAssociatedMemories_OUT = new ArrayList<clsDataStructureContainer>();
		
		// get current environmental situation from moSContainer -> create an image
		
		ArrayList<clsImage> currentImageAllObjects = PlanningWizard.getCurrentEnvironmentalImage(((clsWordPresentationMesh) poEnvironmentalPerception.getSecondaryComponent().getMoDataStructure()).getMoAssociatedContent()); 
		
		
		// if no image of the current world-situation can be returned, we dont't know where to start with planning -> search sequence
		if (currentImageAllObjects.isEmpty()) {
			ArrayList<clsPlanFragment> tempPlanningNodes = new ArrayList<clsPlanFragment>();
			tempPlanningNodes.add(new clsPlanFragment(new clsAct("SEARCH1"),
					new clsImage(eEntity.NONE), 
					new clsImage(eDirection.CENTER, eEntity.CAKE)));
			//moActions_Output = copyPlanFragments(tempPlanningNodes);
			oRetVal.addAll(copyPlanFragments(tempPlanningNodes));
			return oRetVal;
		}
		
		
		
		
		ArrayList<clsImage> currentImageSorted = new ArrayList<clsImage>();
		//TODO AP: AW This loop considers the goal objects, put it where it should be
		for (clsSecondaryDataStructureContainer oGoalContainer : poGoalList) {
			String oDriveObject = clsAffectTools.getDriveObjectType(((clsWordPresentation)oGoalContainer.getMoDataStructure()).getMoContent());
			
			for (clsImage oImage : currentImageAllObjects) {
				if (oDriveObject.equals("ENTITY:" + oImage.m_eObj)) {
					currentImageSorted.add(oImage);
				}
			}
		}
		
		
		
//		System.out.println(currentImage.m_eDist);
		PlanningGraph plGraph = new PlanningGraph();
		// add plans and connections between plans
		try {
			PlanningWizard.initPlGraphWithActions(moAvailablePlanFragments, plGraph);
			PlanningWizard.initPlGraphWithPlConnections(moAvailablePlanFragments, plGraph);
		
			
			// check, which actions can be executed next
			ArrayList<clsPlanFragment> currentApplicalbePlanningNodes = PlanningWizard.getCurrentApplicablePlanningNodes(moAvailablePlanFragments, currentImageSorted);
			ArrayList<clsPlanFragment> sortedApplicablePlanningNodes = new ArrayList<clsPlanFragment>();
			
			// Those plan fragments must fit to the goals, else, they are not applicated
			//Remove those planning nodes, which are not conform with the goals
			for (clsDataStructureContainer oGoalContainer : poGoalList) {
				for (clsPlanFragment oPlanFragment : currentApplicalbePlanningNodes) {
					//Extract Info from Planning Node
					//oPlanFragment.m_act
					
				}
				
			}
			
			
			// run through applicable plans and see which results can be achieved by executing plFragment
			for (clsPlanFragment plFragment : currentApplicalbePlanningNodes) { 
				plGraph.setStartPlanningNode(plFragment);
				plGraph.breathFirstSearch();
			}
			
			
			// copy output -> workaround till planning works correctly
			oRetVal.addAll(copyPlanFragments(currentApplicalbePlanningNodes));
			
			
			ArrayList<PlanningNode> plansTemp = new ArrayList<PlanningNode>(); 
			
			for (clsPlanFragment myPlans : currentApplicalbePlanningNodes)
				plansTemp.add(myPlans);
			
			// output actions
//			PlanningWizard.printPlansToSysout(plansTemp , 0);
//			plGraph.m_planningResults.get(1)

			int i = 0;
		} catch (Exception e) {
			System.out.println("FATAL: Planning Wizard coldn't be initialized");
		}
		
		// copy perception for movement control
		//moEnvironmentalPerception_OUT = moEnvironmentalPerception_IN;
		
		//plGraph.setStartPlanningNode(n)
		return oRetVal;
	}

	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @author perner 12.07.2010, 10:47:41
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {

	}
	
	public ArrayList<clsSecondaryDataStructureContainer> copyPlanFragments(ArrayList<clsPlanFragment> myPlans) {
		
		ArrayList<clsSecondaryDataStructureContainer> moPlans = new ArrayList<clsSecondaryDataStructureContainer>();
		
		for (clsPlanFragment plFr : myPlans) {
			moPlans.add(plFr);
		}
		
		// add perception
		return moPlans;
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
	
	
}
