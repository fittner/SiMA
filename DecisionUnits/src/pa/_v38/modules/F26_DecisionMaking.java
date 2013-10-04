/**
 * E26_DecisionMaking.java: DecisionUnits - pa.modules
 * 
 * @author kohlhauser
 * 11.08.2009, 14:51:57
 */
package pa._v38.modules;

import general.datamanipulation.PrintTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import config.clsProperties;
import config.personality_parameter.clsPersonalityParameterContainer;
import pa._v38.tools.toText;
import pa._v38.interfaces.modules.I6_13_receive;
import pa._v38.interfaces.modules.I6_3_receive;
import pa._v38.interfaces.modules.I6_7_receive;
import pa._v38.interfaces.modules.I6_2_receive;
import pa._v38.interfaces.modules.I6_8_receive;
import pa._v38.interfaces.modules.I6_8_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsAct;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshAimOfDrive;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshFeeling;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshMentalSituation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshSelectableGoal;
import pa._v38.memorymgmt.datatypes.clsWording;
import pa._v38.memorymgmt.interfaces.itfModuleMemoryAccess;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;
import pa._v38.memorymgmt.storage.DT3_PsychicEnergyStorage;
import secondaryprocess.functionality.decisionmaking.GoalHandlingFunctionality;
import secondaryprocess.functionality.decisionmaking.GoalProcessingFunctionality;
import secondaryprocess.functionality.decisionpreparation.clsDecisionEngine;
import secondaryprocess.functionality.shorttermmemory.ShortTermMemoryFunctionality;

/**
 * Demands provided by reality, drives, and Superego are merged. The result is evaluated regarding which resulting wish can be used as motive for an action tendency. The list of produced motives is ordered according to their satisability. 
 * 
 * @author kohlhauser
 * 11.08.2009, 14:51:57
 * 
 */
/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 31.07.2011, 14:13:58
 * 
 */
public class F26_DecisionMaking extends clsModuleBaseKB implements 
I6_13_receive, I6_2_receive, I6_3_receive, I6_7_receive, I6_8_send {
	public static final String P_MODULENUMBER = "26";
	
	/** Specialized Logger for this class */
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	
	public static final String P_GOAL_PASS = "NUMBER_OF_GOALS_TO_PASS";
	public static final String P_AFFECT_THRESHOLD = "AFFECT_THRESHOLD";
	public static final String P_AVOIC_INTENSITY = "AVOID_INTENSITY";
	
	/** Perception IN */
	//private clsWordPresentationMesh moPerceptionalMesh_IN;
	/** Associated Memories IN; @since 07.02.2012 15:54:51 */
	//private ArrayList<clsWordPresentationMesh> moAssociatedMemories_IN;
	/** Perception OUT */
	//private clsWordPresentationMesh moPerceptionalMesh_OUT;
	/** Associated Memories OUT; @since 07.02.2012 15:54:51 */
	//private ArrayList<clsWordPresentationMesh> moAssociatedMemories_OUT;
	/** List of drive goals IN; @since 07.02.2012 19:10:20 */
	private ArrayList<clsWordPresentationMeshSelectableGoal> moReachableGoalList_IN;
	/** DOCUMENT (wendt) - insert description; @since 31.07.2011 14:14:07 */
	private ArrayList<clsWordPresentationMeshSelectableGoal> moDecidedGoalList_OUT;
	
	private ArrayList<clsWordPresentationMeshAimOfDrive> moDriveGoalList_IN;
	private clsWording moSpeechList_IN;
	private clsWordPresentationMesh moPerceptionAssociations;
	/** DOCUMENT (wendt) - insert description; @since 31.07.2011 14:14:01 */
	private ArrayList<clsAct> moRuleList; 
	
	private clsShortTermMemory<clsWordPresentationMeshMentalSituation> moShortTermMemory;
	
	private final clsDecisionEngine moDecisionEngine;
	
	

	
	// Anxiety from F20
	private ArrayList<clsWordPresentationMeshFeeling> moFeeling_IN;
	
	
	/** Number of goals to pass */
	private int mnNumberOfGoalsToPass;
	
	/** Threshold for letting through drive goals */
	private double mrAffectThresold;	//Everything with an affect >= MEDIUM is passed through
	
	
	private int mnAvoidIntensity;
	
	private final  DT3_PsychicEnergyStorage moPsychicEnergyStorage;
	
	/**
	 * DOCUMENT (kohlhauser) - insert description 
	 * 
	 * @author kohlhauser
	 * 03.03.2011, 16:51:33
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F26_DecisionMaking(String poPrefix, clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, itfModuleMemoryAccess poLongTermMemory, clsShortTermMemory poShortTimeMemory, clsShortTermMemory poTempLocalizationStorage, clsDecisionEngine poDecisionEngine, DT3_PsychicEnergyStorage poPsychicEnergyStorage, clsPersonalityParameterContainer poPersonalityParameterContainer) throws Exception {
		
		super(poPrefix, poProp, poModuleList, poInterfaceData, poLongTermMemory);
		
		this.moPsychicEnergyStorage = poPsychicEnergyStorage;
        this.moPsychicEnergyStorage.registerModule(mnModuleNumber);
        
        
		applyProperties(poPrefix, poProp);	
		
		mnNumberOfGoalsToPass=poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_GOAL_PASS).getParameterInt();
		mrAffectThresold=poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_AFFECT_THRESHOLD).getParameterDouble();
		mnAvoidIntensity=poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_AVOIC_INTENSITY).getParameterInt();

		
		//Get short time memory
		moShortTermMemory = poShortTimeMemory;
		
		moDecisionEngine = poDecisionEngine;
		
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
		String text ="";
		
		text += toText.listToTEXT("moReachableGoalList_IN", moReachableGoalList_IN);
		//text += toText.listToTEXT("moExtractedPrediction_IN", moExtractedPrediction_IN);
		text += toText.listToTEXT("moRuleList", moRuleList);
		//text += toText.valueToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_IN);
		text += toText.listToTEXT("moDriveGoalList_IN", moDriveGoalList_IN);
		//text += toText.listToTEXT("moExtractedPrediction_OUT", moExtractedPrediction_OUT);
		
		text += toText.listToTEXT("moAnxiety_Input", moFeeling_IN);
		
		text += toText.valueToTEXT("moSpeechList", moSpeechList_IN);
		
		
		
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
	 * @author kohlhauser
	 * 11.08.2009, 16:16:33
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 * 
	 * this module sends the perception input to module E27, E28 just bypasses the information and sends an additional counter which is not used
	 *  
	 * 
	 */
	@Override
	protected void process_basic() {
	    
	    log.debug("=== module {} start ===", this.getClass().getName());
	    //1. Remove non reachable goals
	    GoalHandlingFunctionality.removeNonReachableGoals(moReachableGoalList_IN, moShortTermMemory);
	    
	    
	    //FIXME SM: This is a temp variable, which shall be replaced with real feelings
		boolean bActivatePanicInfluence = false;
		//HZ Up to now it is possible to define the goal by a clsWordPresentation only; it has to be 
		//verified if a clsSecondaryDataStructureContainer is required.
		
		//Get all potential goals
		//ArrayList<clsWordPresentationMesh> oPotentialGoals = extractReachableDriveGoals(moPerceptionalMesh_IN, moExtractedPrediction_IN);
		//Add drivedemands from potential goals, which shall be avoided
		//ArrayList<clsWordPresentationMesh> moExtendedDriveList = moGoalList_IN;
		//moExtendedDriveList.addAll(getAvoidDrives(oPotentialGoals));		//THIS PART IS DONE BY THE EMOTIONS NOW
		
		
		
		//TEMP Add influence of feelings to goal
		//clsDecisionPreparationTools.applyConsequencesOfFeelingsOnGoals(moReachableGoalList_IN, null);
		//this.log.debug("Appended feelings to goal:" + moReachableGoalList_IN.toString());

        //Sort incoming drives
        ArrayList<clsWordPresentationMeshAimOfDrive> oDriveGoalListSorted = GoalHandlingFunctionality.sortAimOfDrives(moDriveGoalList_IN);
		log.debug("Sorted incoming drive goal list: " + PrintTools.printArrayListWithLineBreaks(oDriveGoalListSorted));
		
		//Apply effects of aims of drives
		GoalHandlingFunctionality.applyDriveDemandsOnReachableGoals(this.moReachableGoalList_IN, this.moDriveGoalList_IN, mrAffectThresold);
		log.debug("Aim of drives on selectable goals applied: {}", PrintTools.printArrayListWithLineBreaks(moReachableGoalList_IN));
		
		//Apply effect of feelings on goals
		GoalHandlingFunctionality.applyFeelingsOnReachableGoals(moReachableGoalList_IN, moFeeling_IN, bActivatePanicInfluence);
		log.debug("Current feelings: {}", moFeeling_IN);
		log.debug("Current feelings on selectable goals applied: {}", PrintTools.printArrayListWithLineBreaks(moReachableGoalList_IN));
		
		//Apply social rules on goals
		GoalHandlingFunctionality.applySocialRulesOnReachableGoals(moReachableGoalList_IN, moRuleList);
	    log.debug("Social rules: {}", moRuleList);
	    log.debug("Social rules on selectable goals applied: {}", PrintTools.printArrayListWithLineBreaks(moReachableGoalList_IN));
		
		//Select the goals to be forwarded
		moDecidedGoalList_OUT = GoalHandlingFunctionality.selectSuitableReachableGoals(moReachableGoalList_IN, mnNumberOfGoalsToPass);
		
		GoalProcessingFunctionality.initStatusOfSelectedGoals(moDecisionEngine, moDecidedGoalList_OUT);
		log.info("Selected goals: {}", PrintTools.printArrayListWithLineBreaks(moDecidedGoalList_OUT));
		
		//Add the aim of drives goal to the mental situation
		ShortTermMemoryFunctionality.addUsableAimOfDrivesToMentalSituation(moDriveGoalList_IN, moDecidedGoalList_OUT, this.moShortTermMemory);
		
		try {
		    if (moDecidedGoalList_OUT.isEmpty()==true) {
	            throw new Exception("Decided goal: No goal");
	        }
		} catch (Exception e) {
		    log.error("Decided goal: No goal ", e);
		}
	}
	

	



	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 14:52:37
	 * 
	 * @see pa.interfaces.I2_13#receive_I2_13(int)
	 * 
	 * by this interface a set of reality information, filtered by E24 (reality check), is received
	 * fills moRealityPerception
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I6_7(ArrayList<clsWordPresentationMeshSelectableGoal> poReachableGoalList) {
		//moReachableGoalList_IN = (ArrayList<clsWordPresentationMesh>)this.deepCopy(poReachableGoalList); 
		moReachableGoalList_IN = poReachableGoalList; 
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 14:52:37
	 * 
	 * @see pa.interfaces.I5_5#receive_I5_5(int)
	 * 
	 * TODO cua implement
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I6_2(ArrayList<clsWordPresentationMeshFeeling> poAnxiety_Input) {
		moFeeling_IN = (ArrayList<clsWordPresentationMeshFeeling>)deepCopy(poAnxiety_Input);	
		//TODO
		
	}
	
	/* (non-Javadoc)
	 *
	 * @since 04.07.2012 10:02:08
	 * 
	 * @see pa._v38.interfaces.modules.I6_3_receive#receive_I6_3(java.util.ArrayList)
	 */
	@Override
	public void receive_I6_3(ArrayList<clsWordPresentationMeshAimOfDrive> poDriveList) {
		moDriveGoalList_IN = poDriveList; //(ArrayList<clsWordPresentationMeshGoal>)this.deepCopy(poDriveList);
	}


	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 16:16:33
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I6_8(moDecidedGoalList_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 18.05.2010, 17:55:10
	 * 
	 * @see pa.interfaces.send.I7_1_send#send_I7_1(java.util.HashMap)
	 */
	@Override
	public void send_I6_8(ArrayList<clsWordPresentationMeshSelectableGoal> poDecidedGoalList_OUT) {
		((I6_8_receive)moModuleList.get(52)).receive_I6_8(poDecidedGoalList_OUT);
		
		putInterfaceData(I6_8_send.class, poDecidedGoalList_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 12.07.2010, 10:47:36
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (kohlhauser) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 12.07.2010, 10:47:36
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (kohlhauser) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 03.03.2011, 16:51:39
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
	 * 20.04.2011, 08:46:04
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "Demands provided by reality, drives, and Superego are merged. The result is evaluated regarding which resulting wish can be used as motive for an action tendency. The list of produced motives is ordered according to their satisability.";
	}

    /* (non-Javadoc)
     *
     * @since 06.09.2013 16:13:23
     * 
     * @see pa._v38.interfaces.modules.I6_13_receive#receive_I6_13(pa._v38.memorymgmt.datatypes.clsWording)
     */
    @Override
    public void receive_I6_13(clsWording moWording) {
      moSpeechList_IN = moWording;
        
    }

    /* (non-Javadoc)
     *
     * @since 06.09.2013 16:13:23
     * 
     * @see pa._v38.interfaces.modules.I6_13_receive#receive_I6_13(pa._v38.memorymgmt.datatypes.clsWordPresentationMesh, java.util.ArrayList)
     */
    @Override
    public void receive_I6_13(clsWordPresentationMesh poPerception, ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary) {
       moPerceptionAssociations = poPerception;
        
    }



    /* (non-Javadoc)
     *
     * @since 12.09.2013 22:24:01
     * 
     * @see pa._v38.interfaces.modules.I6_13_receive#receive_I6_13(pa._v38.memorymgmt.datatypes.clsWordPresentationMesh)
     */
    @Override
    public void receive_I6_13(clsWordPresentationMesh moWording) {
        // TODO (hinterleitner) - Auto-generated method stub
        
    }


   




	
}

