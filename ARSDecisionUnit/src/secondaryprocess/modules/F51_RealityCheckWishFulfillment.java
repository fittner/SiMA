/**
 * E24_RealityCheck.java: DecisionUnits - pa.modules
 * 
 * @author kohlhauser
 * 11.08.2009, 14:49:09
 */
package secondaryprocess.modules;

import general.datamanipulation.PrintTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import org.slf4j.Logger;

import memorymgmt.interfaces.itfModuleMemoryAccess;
import memorymgmt.shorttermmemory.clsEnvironmentalImageMemory;
import memorymgmt.shorttermmemory.clsShortTermMemory;
import memorymgmt.storage.DT3_PsychicIntensityStorage;
import modules.interfaces.I6_15_receive;
import modules.interfaces.I6_15_send;
import modules.interfaces.eInterfaces;
import pa._v38.interfaces.modules.I6_6_receive;
import pa._v38.interfaces.modules.I6_7_receive;
import pa._v38.interfaces.modules.I6_7_send;
import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;
import secondaryprocess.functionality.EffortFunctionality;
import secondaryprocess.functionality.decisionmaking.GoalConditionFunctionality;
import secondaryprocess.functionality.decisionmaking.GoalHandlingFunctionality;
import secondaryprocess.functionality.decisionpreparation.DecisionEngine;
import secondaryprocess.functionality.decisionpreparation.DecisionEngineInterface;
import secondaryprocess.functionality.shorttermmemory.EnvironmentalImageFunctionality;
import secondaryprocess.functionality.shorttermmemory.ShortTermMemoryFunctionality;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshMentalSituation;
import base.datatypes.clsWordPresentationMeshPossibleGoal;
import base.modules.clsModuleBase;
import base.modules.clsModuleBaseKB;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;

/**
 * The external world is evaluated regarding the available possibilities for drive satisfaction and which requirements arise. This is done by utilization of semantic knowledge provided by {E25} and incoming word and things presentations from {E23}. The result influences the generation of motives in {E26}. 
 * 
 * @author wendt
 * 07.05.2012, 14:49:09
 * 
 */
public class F51_RealityCheckWishFulfillment extends clsModuleBaseKB implements I6_6_receive, I6_7_send, I6_15_send {
	public static final String P_MODULENUMBER = "51";
	
    private static final String P_MODULE_STRENGTH ="MODULE_STRENGTH";
    private static final String P_INITIAL_REQUEST_INTENSITY ="INITIAL_REQUEST_INTENSITY";
                
    private double mrModuleStrength;
    private double mrInitialRequestIntensity;
	
	/** Specialized Logger for this class */
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	
	public static final String P_MOMENT_ACTIVATION_THRESHOLD = "MOMENT_ACTIVATION_THRESHOLD";
	public static final String P_MOMENT_MIN_RELEVANCE_THRESHOLD = "MOMENT_MIN_RELEVANCE_THRESHOLD";
	public static final String P_CONFIRMATION_PARTS = "CONFIRMATION_PARTS";
	public static final String P_REDUCEFACTOR_FOR_DRIVES = "REDUCEFACTOR_FOR_DRIVES";
	public static final String P_AFFECT_THRESHOLD = "AFFECT_THRESHOLD";
	
	private clsWordPresentationMesh moWordingToContext;
	/** Perception IN */
	private clsWordPresentationMesh moPerceptionalMesh_IN;
	
	private ArrayList<clsWordPresentationMeshPossibleGoal> moReachableGoalList_IN;
	
	private ArrayList<clsWordPresentationMeshPossibleGoal> moReachableGoalList_OUT;
	protected final static Logger logFim = logger.clsLogger.getLog("Fim");
	
	/** A threshold for images, which are only set moment if the match factor is higher or equal this value */
	private double mrMomentActivationThreshold;
	/** DOCUMENT (wendt) - insert description; @since 10.09.2011 16:40:06 */
	private double mrMomentMinRelevanceThreshold;
	
	/** If a third of an act is gone though, it is considered as confirmed */
	private int mnConfirmationParts;
	
	/** This factor detemines how much the drive can be reduced in an intention. If the value is 0.5, this is the minimum value of the drive, which can be reduced */
	private double mrReduceFactorForDrives;
	
	private int mnAffectThresold;
	
	/** (wendt) Goal memory; @since 24.05.2012 15:25:09 */
	private clsShortTermMemory<clsWordPresentationMeshMentalSituation> moShortTimeMemory;
	
	/** This is the storage for the localization; @since 15.11.2011 14:41:03 */
	private clsEnvironmentalImageMemory moEnvironmentalImageStorage;
	
	//private clsCodeletHandler moCodeletHandler;
	private DecisionEngineInterface moDecisionEngine;
	
	private final  DT3_PsychicIntensityStorage moPsychicEnergyStorage;
	
	/**
	 * DOCUMENT (KOHLHAUSER) - insert description 
	 * 
	 * @author kohlhauser
	 * 03.03.2011, 16:50:46
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poGoalMemory 
	 * @throws Exception
	 */
	public F51_RealityCheckWishFulfillment(String poPrefix, clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, itfModuleMemoryAccess poLongTermMemory, clsShortTermMemory poShortTimeMemory, clsEnvironmentalImageMemory poTempLocalizationStorage, DecisionEngine poDecisionEngine,
			DT3_PsychicIntensityStorage poPsychicEnergyStorage , clsPersonalityParameterContainer poPersonalityParameterContainer, int pnUid) throws Exception {

		super(poPrefix, poProp, poModuleList, poInterfaceData, poLongTermMemory, pnUid);
		
        mrModuleStrength = poPersonalityParameterContainer.getPersonalityParameter("F51", P_MODULE_STRENGTH).getParameterDouble();
        mrInitialRequestIntensity =poPersonalityParameterContainer.getPersonalityParameter("F51", P_INITIAL_REQUEST_INTENSITY).getParameterDouble();

        this.moPsychicEnergyStorage = poPsychicEnergyStorage;
        this.moPsychicEnergyStorage.registerModule(mnModuleNumber, mrInitialRequestIntensity, mrModuleStrength);
		 
		applyProperties(poPrefix, poProp);	
		
		mrMomentActivationThreshold = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_MOMENT_ACTIVATION_THRESHOLD).getParameterDouble();
		mrMomentMinRelevanceThreshold = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_MOMENT_MIN_RELEVANCE_THRESHOLD).getParameterDouble();
		mnConfirmationParts = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_CONFIRMATION_PARTS).getParameterInt();
		mrReduceFactorForDrives = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_REDUCEFACTOR_FOR_DRIVES).getParameterDouble();
		mnAffectThresold = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_AFFECT_THRESHOLD).getParameterInt();

		
		//Get short time memory
		moShortTimeMemory = poShortTimeMemory;
		//moGoalMemory = poGoalMemory;
		moEnvironmentalImageStorage = poTempLocalizationStorage;
		
		moDecisionEngine = poDecisionEngine;
		//Init with special variables from F51
		//moDecisionEngine.getCodeletHandler().initF51(moReachableGoalList_IN);
		

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
		
		text += toText.valueToTEXT("moReachableGoalList_IN", moReachableGoalList_IN);
		text += toText.valueToTEXT("Environmental Image", moEnvironmentalImageStorage.getEnvironmentalImage());
		text += toText.valueToTEXT("Enhanced Environmental Image", moEnvironmentalImageStorage.getEnhancedEnvironmentalImage());
//		text += toText.listToTEXT("moAssociatedMemoriesSecondary_IN", moAssociatedMemoriesSecondary_IN);
//		text += toText.valueToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_OUT);
		text += toText.valueToTEXT("moReachableGoalList_OUT", moReachableGoalList_OUT);
//		text += toText.listToTEXT("moDriveGoalList_IN", moDriveGoalList_IN);
		
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
	 * 11.08.2009, 16:16:25
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
	    
	    log.debug("=== module {} start ===", this.getClass().getName());
	      //=== Perform system tests ===//
        //if (clsTester.getTester().isActivated()) {
	    //ArrayList<clsWordPresentationMeshGoal> temp = JACKBAUERHACKReduceGoalList(moReachableGoalList_IN);
	    //moReachableGoalList_IN = temp;
//	    try {
//                clsTester.getTester().exeTestReduceGoalList(moReachableGoalList_IN);
//            } catch (Exception e) {
//                log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
//            }
//        //}
	
	    //=== Create the mental image ===
		//Test AW: Relational Meshes
		//clsSecondarySpatialTools.createRelationalObjectMesh(moPerceptionalMesh_IN);
		//Add perception to the environmental image
	    EnvironmentalImageFunctionality.addNewImageToEnvironmentalImage(moEnvironmentalImageStorage, moPerceptionalMesh_IN);
		log.debug("Environmental Storage: " + moEnvironmentalImageStorage.toString());

		
		//From now, only the environmental image is used
		
        
        // --- INIT GOALS --- //
        log.trace("Incoming goals before init: " + PrintTools.printArrayListWithLineBreaks(moReachableGoalList_IN));
        moReachableGoalList_OUT = this.moDecisionEngine.initialzeGoals(moReachableGoalList_IN);
        log.debug("Incoming goals after init: " + PrintTools.printArrayListWithLineBreaks(moReachableGoalList_OUT));
        
        //Init codelets
        this.moDecisionEngine.setInitialSettings(moReachableGoalList_OUT);
        log.debug("Incoming goals after applience of init operations: " + PrintTools.printArrayListWithLineBreaks(moReachableGoalList_OUT));
  
		// --- APPLY ACTION CONSEQUENCES ON THE CONTINUED GOAL --- //
		this.moDecisionEngine.applyConsequencesOfActionOnContinuedGoal(moReachableGoalList_OUT); //.a is the last plangoal
		
		//Apply the GOAL_NOT_REACHABLE AND COMPLETED on all other goals with the same supportive data structures
		GoalConditionFunctionality.setCommonConditionsToGoals(moReachableGoalList_OUT);		
		
		// --- ADD IMPORTANCE OF FEELINGS --- //
		//applyConsequencesOfFeelingsOnGoals(moReachableGoalList_IN);
		//applyConsequencesOfFeelingsOnGoal(oContinuedGoal);
		

		
		// --- ADD the previous goal to the goal list if not already added --- //
		//this.moDecisionEngine.addContinuedGoalToGoalList(moReachableGoalList_IN, oContinuedGoalList);
		
		// --- ADD EFFORT VALUES TO THE AFFECT LEVEL --- //
        EffortFunctionality.applyEffortOfGoal(moReachableGoalList_OUT);
        log.info("Applied efforts on selectable goals: {}", PrintTools.printArrayListWithLineBreaks(moReachableGoalList_OUT));
		
        // --- ADD NON REACHABLE GOALS TO THE STM --- //
        //1. Remove non reachable goals
        ShortTermMemoryFunctionality.addNonReachableGoalsToSTM(this.moShortTimeMemory, moReachableGoalList_OUT);
        moReachableGoalList_OUT = GoalHandlingFunctionality.removeNonReachableGoals(moReachableGoalList_OUT, this.moShortTimeMemory);
        //logFim.info("REMOVED_UNREACHABLE_GOALS:");
		log.info("Provided selectable goals: {}", PrintTools.printArrayListWithLineBreaks(moReachableGoalList_OUT));
		log.info("Provided continued goals: {}", PrintTools.printArrayListWithLineBreaks(this.moDecisionEngine.getContinuedGoals(moReachableGoalList_OUT)));
		log.info("Provided plan goal: {}", this.moDecisionEngine.getPlanGoal(moReachableGoalList_OUT));
		
          
	    double rRequestedPsychicIntensity = 0.0;
	                
	    double rReceivedPsychicEnergy = moPsychicEnergyStorage.send_D3_1(mnModuleNumber);
	            
	    double rConsumedPsychicIntensity = rReceivedPsychicEnergy;
	            
	    moPsychicEnergyStorage.informIntensityValues(mnModuleNumber, mrModuleStrength, rRequestedPsychicIntensity, rConsumedPsychicIntensity);
	}
	

	
	/* (non-Javadoc)
    *
    * @author kohlhauser
    * 11.08.2009, 16:16:25
    * 
    * @see pa.modules.clsModuleBase#send()
    */
   @Override
   protected void send() {
       //HZ: null is a placeholder for the bjects of the type pa._v38.memorymgmt.datatypes
       send_I6_7(moReachableGoalList_OUT, moWordingToContext);
       send_I6_15(moPerceptionalMesh_IN);
   }

   /* (non-Javadoc)
    *
    * @author kohlhauser
    * 18.05.2010, 17:51:11
    * 
    * @see pa.interfaces.send.I2_13_send#send_I2_13(java.util.ArrayList)
    */
   @Override
   public void send_I6_7(ArrayList<clsWordPresentationMeshPossibleGoal> poReachableGoalList, clsWordPresentationMesh moWordingToContext2) {
       ((I6_7_receive)moModuleList.get(26)).receive_I6_7(poReachableGoalList, moWordingToContext2);
       
       putInterfaceData(I6_7_send.class, poReachableGoalList, moWordingToContext2);
   }

   /* (non-Javadoc)
   *
   * @since 23.05.2015 13:55:13
   * 
   * @see modules.interfaces.I6_15_send#send_I6_15(base.datatypes.clsWordPresentationMesh)
   */
    @Override
    public void send_I6_15(clsWordPresentationMesh poFocusedPerception) {
        ((I6_15_receive)moModuleList.get(53)).receive_I6_15(poFocusedPerception);
        
        putInterfaceData(I6_15_send.class, poFocusedPerception);
    }

   
   /* (non-Javadoc)
    *
    * @author kohlhauser
    * 12.07.2010, 10:47:25
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
    * 12.07.2010, 10:47:25
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
    * 03.03.2011, 16:50:53
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
       moDescription = "The external world is evaluated regarding the available possibilities for drive satisfaction and which requirements arise. This is done by utilization of semantic knowledge provided by {E25} and incoming word and things presentations from {E23}. The result influences the generation of motives in {E26}.";
   }

/* (non-Javadoc)
 *
 * @since 27.12.2013 19:03:11
 * 
 * @see pa._v38.interfaces.modules.I6_6_receive#receive_I6_6(base.datatypes.clsWordPresentationMesh, java.util.ArrayList, java.util.ArrayList)
 */
@Override
public void receive_I6_6(clsWordPresentationMesh poPerception, ArrayList<clsWordPresentationMeshPossibleGoal> poReachableGoalList,
        clsWordPresentationMesh poContextToWording) {
    moPerceptionalMesh_IN = poPerception;    
    moReachableGoalList_IN = poReachableGoalList;
    moWordingToContext = poContextToWording;
}


  
   

}

