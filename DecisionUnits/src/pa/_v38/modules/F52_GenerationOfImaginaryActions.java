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
import config.clsProperties;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.interfaces.modules.I6_8_receive;
import pa._v38.interfaces.modules.I6_9_receive;
import pa._v38.interfaces.modules.I6_9_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAct;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsImage;
import pa._v38.memorymgmt.datatypes.clsPlanFragment;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.enums.eActState;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.toText;
import pa._v38.tools.planningHelpers.PlanningGraph;
import pa._v38.tools.planningHelpers.PlanningWizard;
import pa._v38.tools.planningHelpers.eDirection;
import pa._v38.tools.planningHelpers.eDistance;
import pa._v38.tools.planningHelpers.eObjectCategorization;

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
		
		// ArrayList<clsPlanAction> moActions_Output = new
		// ArrayList<clsPlanAction>(); //never used!

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
		text += toText.listToTEXT("moActions_Output", moActions_Output);
		text += toText.listToTEXT("moGoalInput", moGoalInput);

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
			ArrayList<clsSecondaryDataStructureContainer> poGoalInput) {
		moGoalInput = (ArrayList<clsSecondaryDataStructureContainer>) deepCopy(poGoalInput);
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

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("vor gehen"),
				new clsImage(eDistance.far, eDirection.straight, eObjectCategorization.CAKE), 
				new clsImage(eDistance.close, eDirection.straight, eObjectCategorization.CAKE)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("kuchen aufnehmen"),
				new clsImage(eDistance.close, eDirection.straight, eObjectCategorization.CAKE), 
				new clsImage(eDistance.inHand, eDirection.straight, eObjectCategorization.CAKE)));
		
		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("kuchen essen"),
				new clsImage(eDistance.inHand, eDirection.straight, eObjectCategorization.CAKE), 
				new clsImage(eObjectCategorization.NONE)));
		
		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("zurück gehen"),
				new clsImage(eDistance.close, eDirection.straight, eObjectCategorization.CAKE), 
				new clsImage(eDistance.far, eDirection.straight, eObjectCategorization.CAKE)));
		
		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("rechts drehen"),
				new clsImage(eDistance.close, eDirection.right, eObjectCategorization.CAKE), 
				new clsImage(eDistance.close, eDirection.straight, eObjectCategorization.CAKE)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("links drehen"),
				new clsImage(eDirection.right, eObjectCategorization.CAKE), 
				new clsImage(eDirection.straight, eObjectCategorization.CAKE)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("kuchen suchen var1"),
				new clsImage(eObjectCategorization.NONE), 
				new clsImage(eDirection.straight, eObjectCategorization.CAKE)));
		
		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("kuchen suchen  var2"),
				new clsImage(eObjectCategorization.NONE), 
				new clsImage(eDirection.right, eObjectCategorization.CAKE)));
		
		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct("kuchen suchen  var3"),
				new clsImage(eObjectCategorization.NONE), 
				new clsImage(eDirection.left, eObjectCategorization.CAKE)));
		
		
		// TODO (perner) add generic actions like right, left without objects
		/**
		 * test
		 */		
	}
	
	//FIXME AW: @Andi, test function for F47. I don't have any memory access in F47. You may delete this as soon as your stuff works
	/**
	 * Generate test data of one act
	 *
	 * @since 20.07.2011 13:42:13
	 *
	 * @return
	 */
	private clsSecondaryDataStructureContainer getTestDataForAct() {
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
	}
	
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
	private ArrayList<clsDataStructureContainer> getAssociatedMemoriesFromPlans(ArrayList<clsSecondaryDataStructureContainer> poInput) {
		ArrayList<clsDataStructureContainer> oRetVal = new ArrayList<clsDataStructureContainer>();
		
		
		//Go through all plans/acts and extract all associated memories (WP)
		ArrayList<clsDataStructureContainer> oAssociatedMemoryList = new ArrayList<clsDataStructureContainer>();
		for (clsSecondaryDataStructureContainer oPlan : poInput) {
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
		process_draft();
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
		
		moActions_Output = new ArrayList<clsSecondaryDataStructureContainer>();
	
		moActions_Output = getActions();
		
		/* ==================================================================== */
		//FIXME AW: Testdata for the F47 interface. DELETEME
		moActions_Output.add(getTestDataForAct());
		/*=======================================================================*/
		
		//AW 20110720: This function extracts the associated memories from the plans. It has to be done here, as
		//F47 does not have any memory access. 
		//If you receive errors in this function, you may inactivate it and AW will correct the errors.
		moAssociatedMemories_OUT = getAssociatedMemoriesFromPlans(moActions_Output);
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 * 
	 * @author zeilinger 02.09.2010, 19:46:45
	 * @return
	 * 
	 */
	private ArrayList<clsSecondaryDataStructureContainer> getActions() {
		ArrayList<clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
		ArrayList<clsAct> oPlan = evaluatePlans();

		for (clsAct oAct : oPlan) {
			for (clsSecondaryDataStructure oSD : oAct.getMoAssociatedContent()) {
				if (oSD instanceof clsWordPresentation
						&& oSD.getMoContentType().equals(
								eActState.ACTION.name())) {
					// AW 20110629: Changed ArrayList<clsWordPresentation> to
					// ArrayList<clsSecondaryDataStructureContainer> in order to
					// fulfill
					// requirements of the interfaces
					clsSecondaryDataStructureContainer oPlanContainer = new clsSecondaryDataStructureContainer(
							(clsWordPresentation) oSD,
							new ArrayList<clsAssociation>());
					oRetVal.add(oPlanContainer);

					return oRetVal;
				}
			}
		}

		return oRetVal;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 * 
	 * edit-perner -> this should happen in module 29
	 * 
	 * @author zeilinger 03.09.2010, 17:19:37
	 * 
	 * @return
	 */
	@Deprecated
	private ArrayList<clsAct> evaluatePlans() {
		// HZ This method evaluates the retrieved plans. Actually this is rather
		// simple
		// as only the number of acts that are required to fulfill the plan are
		// used
		// for this evaluation (the plan with the fewest number of acts is
		// selected)
		ArrayList<clsAct> oRetVal = new ArrayList<clsAct>();

		// HZ obsolete as long asthe functionality of v30_E28 is not integrated
		// in F52
		// for(ArrayList<clsAct> oEntry : moPlanInput){
		// if((oRetVal.size() == 0) || (oRetVal.size() > oEntry.size())){
		// oRetVal = oEntry;
		// }
		// }

		return oRetVal;
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
		send_I6_9(moActions_Output, moAssociatedMemories_OUT);

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
			ArrayList<clsSecondaryDataStructureContainer> poActionCommands, ArrayList<clsDataStructureContainer> poAssociatedMemories) {
		((I6_9_receive) moModuleList.get(8)).receive_I6_9(poActionCommands, poAssociatedMemories);
		((I6_9_receive) moModuleList.get(20)).receive_I6_9(poActionCommands, poAssociatedMemories);
		((I6_9_receive) moModuleList.get(21)).receive_I6_9(poActionCommands, poAssociatedMemories);
		((I6_9_receive) moModuleList.get(29)).receive_I6_9(poActionCommands, poAssociatedMemories);
		((I6_9_receive) moModuleList.get(47)).receive_I6_9(poActionCommands, poAssociatedMemories);
		((I6_9_receive) moModuleList.get(53)).receive_I6_9(poActionCommands, poAssociatedMemories);

		putInterfaceData(I6_9_send.class, poActionCommands, poAssociatedMemories);

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
		
		
		PlanningGraph plGraph = new PlanningGraph();
		
		// add plans and connections between plans
		try {
			PlanningWizard.initPlGraphWithActions(moAvailablePlanFragments, plGraph);
			PlanningWizard.initPlGraphWithPlConnections(moAvailablePlanFragments, plGraph);

			//TODO (perner) add current available environmental situation
			ArrayList<clsPlanFragment> currentApplicalbePlanningNodes = PlanningWizard.getCurrentApplicablePlanningNodes(moAvailablePlanFragments, 
					new clsImage(eDistance.far, eDirection.straight, eObjectCategorization.CAKE));
			
			// run through applicable plans and see which results can be achieved by executing plFragment
			for (clsPlanFragment plFragment : currentApplicalbePlanningNodes) { 
				plGraph.setStartPlanningNode(plFragment);
				plGraph.breathFirstSearch();
			}
			
			
			
		} catch (Exception e) {
			System.out.println("FATAL: Planning Wizard coldn't be initialized");
		}
		
		//plGraph.setStartPlanningNode(n)
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
