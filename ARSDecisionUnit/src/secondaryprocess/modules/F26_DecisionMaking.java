/**
 * E26_DecisionMaking.java: DecisionUnits - pa.modules
 * 
 * @author kohlhauser
 * 11.08.2009, 14:51:57
 */
package secondaryprocess.modules;

import general.datamanipulation.PrintTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import memorymgmt.interfaces.itfModuleMemoryAccess;
import memorymgmt.shorttermmemory.clsShortTermMemory;
import memorymgmt.storage.DT3_PsychicIntensityStorage;
import modules.interfaces.I6_2_receive;
import modules.interfaces.I6_3_receive;
import modules.interfaces.I6_8_receive;
import modules.interfaces.I6_8_send;
import modules.interfaces.eInterfaces;
import pa._v38.interfaces.modules.I6_7_receive;
import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;
import secondaryprocess.datamanipulation.clsGoalManipulationTools;
import secondaryprocess.functionality.decisionmaking.GoalHandlingFunctionality;
import secondaryprocess.functionality.decisionpreparation.DecisionEngine;
import secondaryprocess.functionality.shorttermmemory.ShortTermMemoryFunctionality;
import testfunctions.clsTester;
import base.datatypes.clsAct;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshAimOfDrive;
import base.datatypes.clsWordPresentationMeshFeeling;
import base.datatypes.clsWordPresentationMeshMentalSituation;
import base.datatypes.clsWordPresentationMeshPossibleGoal;
import base.datatypes.clsWording;
import base.modules.clsModuleBase;
import base.modules.clsModuleBaseKB;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;

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
public class F26_DecisionMaking extends clsModuleBaseKB implements I6_2_receive, I6_3_receive, I6_7_receive, I6_8_send {
    
	public static final String P_MODULENUMBER = "26";
	
    private static final String P_MODULE_STRENGTH ="MODULE_STRENGTH";
    private static final String P_INITIAL_REQUEST_INTENSITY ="INITIAL_REQUEST_INTENSITY";
                
    private double mrModuleStrength;
    private double mrInitialRequestIntensity;
	
	/** Specialized Logger for this class */
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	
	public static final String P_GOAL_PASS = "NUMBER_OF_GOALS_TO_PASS";
	public static final String P_AFFECT_THRESHOLD = "AFFECT_THRESHOLD";
	public static final String P_AVOIC_INTENSITY = "AVOID_INTENSITY";
	
	private ArrayList<clsWordPresentationMeshPossibleGoal> moReachableGoalList_IN;
	/** DOCUMENT (wendt) - insert description; @since 31.07.2011 14:14:07 */
	private ArrayList<clsWordPresentationMeshPossibleGoal> moDecidedGoalList_OUT;
	
	private ArrayList<clsWordPresentationMeshAimOfDrive> moDriveGoalList_IN;
	private clsWording moSpeechList_IN;
	private clsWordPresentationMesh moPerceptionAssociations;
	/** DOCUMENT (wendt) - insert description; @since 31.07.2011 14:14:01 */
	private ArrayList<clsAct> moRuleList; 
	
	private clsShortTermMemory<clsWordPresentationMeshMentalSituation> moShortTermMemory;
	
	private final DecisionEngine moDecisionEngine;
	
	// Anxiety from F20
	private ArrayList<clsWordPresentationMeshFeeling> moFeeling_IN;
	
	
	/** Number of goals to pass */
	private int mnNumberOfGoalsToPass;
	
	/** Threshold for letting through drive goals */
	private double mrAffectThresold;	//Everything with an affect >= MEDIUM is passed through
	
	
	private int mnAvoidIntensity;
	
	private clsWordPresentationMesh moWordingToContext;
	
	private final  DT3_PsychicIntensityStorage moPsychicEnergyStorage;
	
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
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, itfModuleMemoryAccess poLongTermMemory, clsShortTermMemory<clsWordPresentationMeshMentalSituation> poShortTimeMemory, clsShortTermMemory poTempLocalizationStorage, DecisionEngine poDecisionEngine, DT3_PsychicIntensityStorage poPsychicEnergyStorage, clsPersonalityParameterContainer poPersonalityParameterContainer) throws Exception {
		
		super(poPrefix, poProp, poModuleList, poInterfaceData, poLongTermMemory);
		
        mrModuleStrength = poPersonalityParameterContainer.getPersonalityParameter("F26", P_MODULE_STRENGTH).getParameterDouble();
        mrInitialRequestIntensity =poPersonalityParameterContainer.getPersonalityParameter("F26", P_INITIAL_REQUEST_INTENSITY).getParameterDouble();

        this.moPsychicEnergyStorage = poPsychicEnergyStorage;
        this.moPsychicEnergyStorage.registerModule(mnModuleNumber, mrInitialRequestIntensity, mrModuleStrength);
        
        
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
	    
        //=== Perform system tests ===//
        clsTester.getTester().setActivated(false);
        if (clsTester.getTester().isActivated()) {
            try {
                log.warn("System tests activated");
                for (clsWordPresentationMesh mesh : moReachableGoalList_IN) {
                    
                    clsTester.getTester().exeTestCheckLooseAssociations(mesh); 
                }
                clsTester.getTester().setActivated(false);
            } catch (Exception e) {
                log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
            }
        }
        
        
        //Requested Psychical Intensity is get to know here. It will be used in order to run some methods. 
        //In this moment, just the method applyFeelingsOnReachableGoals() uses it. We assume that
        //the requested PsychicIntensity equals to mrInitialRequestIntensity during the entire simulation.
        //This may change in the future.

        double rReceivedPsychicEnergy = moPsychicEnergyStorage.send_D3_1(mnModuleNumber);

        
	    
	    //FIXME SM: This is a temp variable, which shall be replaced with real feelings
		boolean bActivatePanicInfluence = false;
		//HZ Up to now it is possible to define the goal by a clsWordPresentation only; it has to be 
		//verified if a clsSecondaryDataStructureContainer is required.
		
		//Get all potential goals
		//ArrayList<clsWordPresentationMesh> oPotentialGoals = extractReachableDriveGoals(moPerceptionalMesh_IN, moExtractedPrediction_IN);
		//Add drivedemands from potential goals, which shall be avoidedT
		//ArrayList<clsWordPresentationMesh> moExtendedDriveList = moGoalList_IN;
		//moExtendedDriveList.addAll(getAvoidDrives(oPotentialGoals));		//THIS PART IS DONE BY THE EMOTIONS NOW
		
		
		//TEMP Add influence of feelings to goal
		//clsDecisionPreparationTools.applyConsequencesOfFeelingsOnGoals(moReachableGoalList_IN, null);
		//this.log.debug("Appended feelings to goal:" + moReachableGoalList_IN.toString());

        //Sort incoming drives
        ArrayList<clsWordPresentationMeshAimOfDrive> oDriveGoalListSorted = GoalHandlingFunctionality.sortAimOfDrives(moDriveGoalList_IN);
		log.debug("Sorted incoming drive goal list: " + PrintTools.printArrayListWithLineBreaks(oDriveGoalListSorted));
		
		//Apply effects of aims of drives
		GoalHandlingFunctionality.applyDriveDemandsOnReachableGoals(this.moReachableGoalList_IN, this.moDriveGoalList_IN);
		log.debug("Aim of drives on selectable goals applied: {}", PrintTools.printArrayListWithLineBreaks(moReachableGoalList_IN));
		
		//Apply effect of feelings on goals
		
		GoalHandlingFunctionality.applyFeelingsOnReachableGoals(moReachableGoalList_IN, moFeeling_IN, bActivatePanicInfluence, rReceivedPsychicEnergy);
		log.debug("Current feelings: {}", moFeeling_IN);
		log.debug("Current feelings on selectable goals applied: {}", PrintTools.printArrayListWithLineBreaks(moReachableGoalList_IN));
		
	

	 
		
		//AMP: it is assumed that F26 request always the same amount of psychic intensity during all iterations. 
        double rRequestedPsychicIntensity = mrInitialRequestIntensity;
        
        
		
        //AMP it is assumed that F26 uses all the psychic intensity that receives.
        double rUsedPsychicIntensity = rReceivedPsychicEnergy;
       
        
		//AMP F26 gives to know the intensity values to the psychic intensity storage
		moPsychicEnergyStorage.informIntensityValues(mnModuleNumber, mrModuleStrength, rRequestedPsychicIntensity, rUsedPsychicIntensity);
		
		//Kollmann: apply importance value based on act-actions
		GoalHandlingFunctionality.applyAimImportanceOnReachableGoals(moReachableGoalList_IN, moDriveGoalList_IN);
		
		//Debug output - sort the list of goals by attractiveness and log it
		ArrayList <clsWordPresentationMeshPossibleGoal> oSortedList = clsGoalManipulationTools.sortAndFilterGoalsByTotalImportance(moReachableGoalList_IN, moReachableGoalList_IN.size());
		log.info("Sorted Goals:\n{}", PrintTools.printArrayListWithLineBreaks(oSortedList));
		
		//Apply social rules on goals
		GoalHandlingFunctionality.applySocialRulesOnReachableGoals(moReachableGoalList_IN, moRuleList);
	    log.debug("Social rules: {}", moRuleList);
	    log.debug("Social rules on selectable goals applied: {}", PrintTools.printArrayListWithLineBreaks(moReachableGoalList_IN));
	    
		//Select the goals to be forwarded
		moDecidedGoalList_OUT = GoalHandlingFunctionality.selectSuitableReachableGoals(moReachableGoalList_IN, mnNumberOfGoalsToPass);
		
		log.info("Selectable goals sorted: {}", PrintTools.printArrayListWithLineBreaks(moReachableGoalList_IN));
		
		//GoalProcessingFunctionality.initStatusOfSelectedGoals(moDecisionEngine, moDecidedGoalList_OUT);
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
    * @since 26.12.2013 19:33:00
    * 
    * @see pa._v38.interfaces.modules.I6_7_receive#receive_I6_7(java.util.ArrayList, pa._v38.memorymgmt.datatypes.clsWordPresentationMesh)
    */
   @Override
   public void receive_I6_7(ArrayList<clsWordPresentationMeshPossibleGoal> poReachableGoalList, clsWordPresentationMesh moWordingToContext2) {
       moReachableGoalList_IN = poReachableGoalList; 
       moWordingToContext = moWordingToContext2;
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
		send_I6_8(moDecidedGoalList_OUT, moWordingToContext);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 18.05.2010, 17:55:10
	 * 
	 * @see pa.interfaces.send.I7_1_send#send_I7_1(java.util.HashMap)
	 */
	@Override
	public void send_I6_8(ArrayList<clsWordPresentationMeshPossibleGoal> poDecidedGoalList_OUT, clsWordPresentationMesh moWordingToContext2) {
		((I6_8_receive)moModuleList.get(52)).receive_I6_8(poDecidedGoalList_OUT, moWordingToContext2);
		
		putInterfaceData(I6_8_send.class, poDecidedGoalList_OUT,moWordingToContext2);
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

  
}

