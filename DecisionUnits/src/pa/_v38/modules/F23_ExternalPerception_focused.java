/**
 * E23_ExternalPerception_focused.java: DecisionUnits - pa.modules
 * 
 * @author kohlhauser
 * 11.08.2009, 14:46:53
 */
package pa._v38.modules;

import general.datamanipulation.PrintTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import config.clsProperties;
import datatypes.helpstructures.clsPair;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshAimOfDrive;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshMentalSituation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshPossibleGoal;
import pa._v38.interfaces.modules.I6_12_receive;
import pa._v38.interfaces.modules.I6_3_receive;
import pa._v38.interfaces.modules.I6_6_receive;
import pa._v38.interfaces.modules.I6_6_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.interfaces.itfModuleMemoryAccess;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;
import pa._v38.memorymgmt.storage.DT3_PsychicEnergyStorage;
import pa._v38.tools.toText;
import secondaryprocess.functionality.FocusFunctionality;
import secondaryprocess.functionality.decisionmaking.GoalHandlingFunctionality;
import secondaryprocess.functionality.shorttermmemory.ShortTermMemoryFunctionality;
import testfunctions.clsTester;

/**
 * The task of this module is to focus the external perception on ``important'' things. Important things are decided by the
 * plan goal, which is extracted from the Short-Term-Memory.
 * 
 * TODO (kohlhauser) - consider free energy
 * 
 * @author kohlhauser
 * 11.08.2009, 14:46:53
 * 
 */
public class F23_ExternalPerception_focused extends clsModuleBaseKB implements I6_12_receive, I6_3_receive, I6_6_send {
	public static final String P_MODULENUMBER = "23";
	
	/** Perception IN */
	private clsWordPresentationMesh moPerceptionalMesh_IN;
	/** Associated Memories IN; @since 07.02.2012 15:54:51 */
	private ArrayList<clsWordPresentationMesh> moAssociatedMemories_IN;
	/** Perception OUT */
	private clsWordPresentationMesh moPerceptionalMesh_OUT;
	/** Associated Memories OUT; @since 07.02.2012 15:54:51 */
	private ArrayList<clsWordPresentationMesh> moAssociatedMemories_OUT;
	
	private ArrayList<clsWordPresentationMeshPossibleGoal> moReachableGoalList_OUT;
	
	private ArrayList<clsWordPresentationMeshAimOfDrive> aimOfDrives;
	
//	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:55:35 */
//	private clsDataStructureContainerPair moEnvironmentalPerception_IN;
//	//AW 20110602 New input of the module
//	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:55:37 */
//	private ArrayList<clsDataStructureContainerPair> moAssociatedMemoriesSecondary_IN;
//	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:55:39 */
	//private ArrayList<clsWordPresentationMeshGoal> moDriveGoalList_IN; 
//	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:55:40 */
//	private clsDataStructureContainerPair moEnvironmentalPerception_OUT; 
//	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:56:18 */
//	private ArrayList<clsDataStructureContainerPair> moAssociatedMemoriesSecondary_OUT;
	
	private clsShortTermMemory<clsWordPresentationMeshMentalSituation> moShortTimeMemory;
	
	/** As soon as DT3 is implemented, replace this variable and value */
	private double mrAvailableFocusEnergy = 5;
	
	private final  DT3_PsychicEnergyStorage moPsychicEnergyStorage;
	private static final double P_STRONGEST_GOAL_THRESHOLD = 0.8;
	
	/**
	 * DOCUMENT (KOHLHAUSER) - insert description 
	 * 
	 * @author kohlhauser
	 * 03.03.2011, 16:50:08
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F23_ExternalPerception_focused(String poPrefix, clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, itfModuleMemoryAccess poLongTermMemory, clsShortTermMemory poShortTimeMemory, clsShortTermMemory poTempLocalizationStorage,
			DT3_PsychicEnergyStorage poPsychicEnergyStorage) throws Exception {
		
		super(poPrefix, poProp, poModuleList, poInterfaceData, poLongTermMemory);

		this.moPsychicEnergyStorage = poPsychicEnergyStorage;
        this.moPsychicEnergyStorage.registerModule(mnModuleNumber);
        
		applyProperties(poPrefix, poProp);	
		
		//Get short time memory
		moShortTimeMemory = poShortTimeMemory;		
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {		
		String text = "";
		
		text += toText.valueToTEXT("moPerceptionalMesh_IN", moPerceptionalMesh_IN);
		text += toText.listToTEXT("moAssociatedMemories_IN", moAssociatedMemories_IN);
		//text += toText.listToTEXT("moDriveList", moDriveGoalList_IN);
		text += toText.valueToTEXT("moPerceptionalMesh_OUT", moPerceptionalMesh_OUT);
		//text += toText.valueToTEXT("moEnvironmentalPerception_OUT", moEnvironmentalPerception_OUT);
		//text += toText.listToTEXT("moAssociatedMemoriesSecondary_OUT", moAssociatedMemoriesSecondary_OUT);
		text += toText.valueToTEXT("mrAvailableFocusEnergy", mrAvailableFocusEnergy);
		
		return text;
	}	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);
		
		//nothing to do
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.SECONDARY;
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;
	}

	
	/* (non-Javadoc)
	 *
	 * @since 07.05.2012 14:11:16
	 * 
	 * @see pa._v38.interfaces.modules.I6_12_receive#receive_I6_12(pa._v38.memorymgmt.datatypes.clsWordPresentationMesh, java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I6_12(clsWordPresentationMesh poPerception,
			ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary) {
		
		moPerceptionalMesh_IN = poPerception;
		moAssociatedMemories_IN = poAssociatedMemoriesSecondary;
		
	}	

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 14:47:49
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@Override
	public void receive_I6_3(ArrayList<clsWordPresentationMeshAimOfDrive> poDriveList) {
	    aimOfDrives = poDriveList;
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 16:16:20
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
	    log.debug("=== module {} start ===", this.getClass().getName());
	    
	    //=== Perform system tests ===//
        clsTester.getTester().setActivated(false);
        if (clsTester.getTester().isActivated()) {
            try {
                log.warn("Systemtester activated");
                for (clsWordPresentationMesh mesh :moAssociatedMemories_IN) {
                    clsTester.getTester().exeTestCheckLooseAssociations(mesh); 
                }
            } catch (Exception e) {
                log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
            }
        }
	    
		//=== Extract all goals from perception and memories ===//
		moReachableGoalList_OUT = new ArrayList<clsWordPresentationMeshPossibleGoal>(); 
		
		//Extract all possible goals in the perception
		moReachableGoalList_OUT.addAll(GoalHandlingFunctionality.extractSelectableGoalsFromPerception(moPerceptionalMesh_IN));
		
		//Extract all possible goals from the images (memories)
		moReachableGoalList_OUT.addAll(GoalHandlingFunctionality.extractSelectableGoalsFromActs(moAssociatedMemories_IN));
		
		//Extract basic goals directly from the drives, to be used if there is nothing in perception
		moReachableGoalList_OUT.addAll(GoalHandlingFunctionality.extractSelectableGoalsFromAimOfDrives(aimOfDrives));
		
        //identifies context there and sets condition to context source 
         GoalHandlingFunctionality.extractGoalFromContext(moReachableGoalList_OUT);
         //sets Context to ContextSource for Calculation  
        // GoalHandlingFunctionality.setConditionToContextSource(moReachableGoalList_OUT);
         
		log.debug("Extracted goals : " + PrintTools.printArrayListWithLineBreaks(moReachableGoalList_OUT));
		
		
		//=== Perform system tests ===//
		clsTester.getTester().setActivated(true);
        if (clsTester.getTester().isActivated()) {
            try {
                for (clsWordPresentationMesh mesh :moAssociatedMemories_IN) {
                    clsTester.getTester().exeTestCheckLooseAssociations(mesh); 
                }
            } catch (Exception e) {
                log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
            }
        }
		
		
		
		//--- Select Goals for Perception ---//
		
		
		//Extract the goals with the strongest emotions from the perceptions
		//oFocusOnGoalList.addAll(extractStrongestPerceptiveGoals(moReachableGoalList_OUT));
		
		//--- Process actions ---//
		clsWordPresentationMesh oAction = ShortTermMemoryFunctionality.extractPreviousPlannedActionFromSTM(this.moShortTimeMemory);
		
		//Extract the goal entity from the planning
		ArrayList<clsPair<Double, clsWordPresentationMesh>> oFocusOnGoalList = new ArrayList<clsPair<Double, clsWordPresentationMesh>>();
        try {
            oFocusOnGoalList = FocusFunctionality.extractFilterEntitiesFromAction(moPerceptionalMesh_IN, oAction);
        } catch (Exception e) {
            log.error("Cannot focus", e);
        }
		
		//=== Filter the perception === //
		int nNumberOfAllowedObjects = (int)mrAvailableFocusEnergy;	//FIXME AW: What is the desexualalized energy and how many objects/unit are used.
		moPerceptionalMesh_OUT = moPerceptionalMesh_IN;
		
		//Remove all non focused objects
		FocusFunctionality.focusPerception(moPerceptionalMesh_OUT, oFocusOnGoalList, nNumberOfAllowedObjects);
		
		log.debug("List of entities to focus on: {}", oFocusOnGoalList);
		//System.out.println("====================================\nF23: Focused perception:" + moPerceptionalMesh_OUT  + "\n==============================================");
		//System.out.println("Focuslist : " + oFocusOnGoalList.toString());
		
		//TODO AW: Memories are not focused at all, only prioritized!!! Here is a concept necessary
		moAssociatedMemories_OUT = moAssociatedMemories_IN;
		
		
		//Everything in the short term memory is conscious. Here, the current mental situation is added to the STM
		//ShortTermMemoryFunctionality.createMentalSituation(this.moShortTimeMemory);
		
		
		//=========================================================//
		//TODO AW: In Focus of attention, the most relevant memories can be selected, i. e. the analysis of the current moment, intention and expectation
		//could be done here. It is also a possibility to use the complete perception for this task.
		
		//=========================================================//
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 16:16:20
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I6_6(moPerceptionalMesh_OUT, moReachableGoalList_OUT, moAssociatedMemories_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 18.05.2010, 17:50:35
	 * 
	 * @see pa.interfaces.send.I2_12_send#send_I2_12(java.util.ArrayList)
	 */
	@Override
	public void send_I6_6(clsWordPresentationMesh poFocusedPerception, ArrayList<clsWordPresentationMeshPossibleGoal> poReachableGoalList, ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary_OUT) {
		((I6_6_receive)moModuleList.get(51)).receive_I6_6(poFocusedPerception, poReachableGoalList, poAssociatedMemoriesSecondary_OUT);
		
		putInterfaceData(I6_6_send.class, poFocusedPerception, poReachableGoalList, poAssociatedMemoriesSecondary_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 12.07.2010, 10:47:20
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 12.07.2010, 10:47:20
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 03.03.2011, 16:50:13
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}
	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = " The task of this module is to focus the external perception on ``important'' things. Important things are decided by the plan goal, which is extracted from the Short-Term-Memory.";
	}

  


	
}
