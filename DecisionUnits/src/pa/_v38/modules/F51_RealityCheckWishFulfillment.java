/**
 * E24_RealityCheck.java: DecisionUnits - pa.modules
 * 
 * @author kohlhauser
 * 11.08.2009, 14:49:09
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import config.clsProperties;
import config.personality_parameter.clsPersonalityParameterContainer;
import pa._v38.interfaces.modules.I6_3_receive;
import pa._v38.interfaces.modules.I6_6_receive;
import pa._v38.interfaces.modules.I6_7_receive;
import pa._v38.interfaces.modules.I6_7_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.memorymgmt.enums.eGoalType;
import pa._v38.memorymgmt.interfaces.itfModuleMemoryAccess;
import pa._v38.memorymgmt.shorttermmemory.clsEnvironmentalImageMemory;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;
import pa._v38.memorymgmt.storage.DT3_PsychicEnergyStorage;
import pa._v38.tools.toText;
import system.datamanipulation.clsGoalTools;
import system.datamanipulation.clsMentalSituationTools;
import system.functionality.decisionpreparation.clsDecisionEngine;

/**
 * The external world is evaluated regarding the available possibilities for drive satisfaction and which requirements arise. This is done by utilization of semantic knowledge provided by {E25} and incoming word and things presentations from {E23}. The result influences the generation of motives in {E26}. 
 * 
 * @author wendt
 * 07.05.2012, 14:49:09
 * 
 */
public class F51_RealityCheckWishFulfillment extends clsModuleBaseKB implements I6_6_receive, I6_7_send, I6_3_receive {
	public static final String P_MODULENUMBER = "51";
	
	/** Specialized Logger for this class */
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	
	public static final String P_MOMENT_ACTIVATION_THRESHOLD = "MOMENT_ACTIVATION_THRESHOLD";
	public static final String P_MOMENT_MIN_RELEVANCE_THRESHOLD = "MOMENT_MIN_RELEVANCE_THRESHOLD";
	public static final String P_CONFIRMATION_PARTS = "CONFIRMATION_PARTS";
	public static final String P_REDUCEFACTOR_FOR_DRIVES = "REDUCEFACTOR_FOR_DRIVES";
	public static final String P_AFFECT_THRESHOLD = "AFFECT_THRESHOLD";
	
	/** Perception IN */
	private clsWordPresentationMesh moPerceptionalMesh_IN;
	/** Perception OUT */
	//private clsWordPresentationMesh moPerceptionalMesh_OUT;
	/** Associated Memories OUT; @since 07.02.2012 15:54:51 */
	//private ArrayList<clsWordPresentationMesh> moAssociatedMemories_OUT;
	
	private ArrayList<clsWordPresentationMeshGoal> moReachableGoalList_IN;
	
	private ArrayList<clsWordPresentationMeshGoal> moReachableGoalList_OUT;
	/** List of drive goals OUT; @since 07.05.2012 19:10:20 */
	//private ArrayList<clsWordPresentationMesh> moGoalList_OUT;
	/** List of drive goals IN; @since 07.02.2012 19:10:20 */
	//private ArrayList<clsWordPresentationMesh> moDriveGoalList_IN;
	
	
	
//	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:57:45 */
//	private clsDataStructureContainerPair moEnvironmentalPerception_IN;  
//	/** Container of activated associated memories */
//	private ArrayList<clsDataStructureContainerPair> moAssociatedMemoriesSecondary_IN;
//	/** Associated memories out */
//	private ArrayList<clsDataStructureContainerPair> moAssociatedMemoriesSecondary_OUT;
//	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:57:49 */
//	private clsDataStructureContainerPair moEnvironmentalPerception_OUT; 
//	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:57:50 */
//	//private ArrayList<clsSecondaryDataStructureContainer> moDriveList;  //removed by HZ - not required now
//	/** A construction of an Intention, an arraylist with expectations and the current situation */
//	private ArrayList<clsPrediction> moExtractedPrediction_OUT;
	
	/** A threshold for images, which are only set moment if the match factor is higher or equal this value */
	private double mrMomentActivationThreshold;
	/** DOCUMENT (wendt) - insert description; @since 10.09.2011 16:40:06 */
	private double mrMomentMinRelevanceThreshold;
	
	/** If a third of an act is gone though, it is considered as confirmed */
	private int mnConfirmationParts;
	
	/** This factor detemines how much the drive can be reduced in an intention. If the value is 0.5, this is the minimum value of the drive, which can be reduced */
	private double mrReduceFactorForDrives;
	
	private int mnAffectThresold;
	
	
	/** Short time memory */
	//private clsOLDShortTimeMemory moShortTimeMemory;
	
	/** (wendt) Goal memory; @since 24.05.2012 15:25:09 */
	private clsShortTermMemory moShortTimeMemory;
	
	/** This is the storage for the localization; @since 15.11.2011 14:41:03 */
	private clsEnvironmentalImageMemory moEnvironmentalImageStorage;
	
	//private clsCodeletHandler moCodeletHandler;
	private clsDecisionEngine moDecisionEngine;
	
	private final  DT3_PsychicEnergyStorage moPsychicEnergyStorage;
	
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
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, itfModuleMemoryAccess poLongTermMemory, clsShortTermMemory poShortTimeMemory, clsEnvironmentalImageMemory poTempLocalizationStorage, clsDecisionEngine poDecisionEngine,
			DT3_PsychicEnergyStorage poPsychicEnergyStorage , clsPersonalityParameterContainer poPersonalityParameterContainer) throws Exception {
	//public F51_RealityCheckWishFulfillment(String poPrefix, clsProperties poProp,
	//		HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		//super(poPrefix, poProp, poModuleList, poInterfaceData);
		super(poPrefix, poProp, poModuleList, poInterfaceData, poLongTermMemory);
		
		 this.moPsychicEnergyStorage = poPsychicEnergyStorage;
		 this.moPsychicEnergyStorage.registerModule(mnModuleNumber);
		 
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
		text += toText.valueToTEXT("Environmental Image", this.moEnvironmentalImageStorage.toString());
//		text += toText.listToTEXT("moAssociatedMemoriesSecondary_IN", moAssociatedMemoriesSecondary_IN);
//		text += toText.valueToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_OUT);
//		text += toText.listToTEXT("moExtractedPrediction_OUT", moExtractedPrediction_OUT);
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
	 * 11.08.2009, 14:49:45
	 * 
	 * @see pa.interfaces.I2_12#receive_I2_12(int)
	 */
	@Override
	public void receive_I6_6(clsWordPresentationMesh poPerception, 
			ArrayList<clsWordPresentationMeshGoal> poReachableGoalList, 
			ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary) {
		
		moPerceptionalMesh_IN = poPerception;
//		try {
//			moPerceptionalMesh_IN = (clsWordPresentationMesh)poPerception.clone();
//		} catch (CloneNotSupportedException e) {
//			// TODO (wendt) - Auto-generated catch block
//			e.printStackTrace();
//		}
		//moReachableGoalList_IN = (ArrayList<clsWordPresentationMesh>) deepCopy(poReachableGoalList);
		//moAssociatedMemories_IN = (ArrayList<clsWordPresentationMesh>)deepCopy(poAssociatedMemoriesSecondary);
		
		moReachableGoalList_IN = poReachableGoalList;
	}
	
	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 14:47:49
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@Override
	public void receive_I6_3(ArrayList<clsWordPresentationMeshGoal> poDriveList) {
		//moDriveGoalList_IN = (ArrayList<clsWordPresentationMesh>)this.deepCopy(poDriveList);
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
		//Get previous memory
		//clsWordPresentationMesh oPreviousMentalSituation = moShortTimeMemory.findPreviousSingleMemory();
		
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
		this.moEnvironmentalImageStorage.addNewImage(moPerceptionalMesh_IN);
		log.debug("Environmental Storage: " + moEnvironmentalImageStorage.toString());

		
		//From now, only the environmental image is used
		
		//if (moAssociatedMemories_IN.isEmpty()==false) {
		//	clsSecondarySpatialTools.createRelationalObjectMesh(moAssociatedMemories_IN.get(0));
		//}
		
		
		// --- INIT INCOMING GOALS --- //
		try {
		    log.trace("Incoming goals: " + moReachableGoalList_IN);
            this.moDecisionEngine.initIncomingGoals(moReachableGoalList_IN);
            log.trace("Incoming goals after init: " + moReachableGoalList_IN);
        } catch (Exception e) {
            // TODO (wendt) - Auto-generated catch block
            e.printStackTrace();
        }
		
		// --- INIT CONTINUED GOAL --- //
		clsWordPresentationMeshGoal oContinuedGoal = clsGoalTools.getNullObjectWPM();
		
        //Init with special variables from F51
		//TODO: It should not be necessary to poll the reachable goal list all the time
        moDecisionEngine.getCodeletHandler().initF51(moReachableGoalList_IN);
        try {
            oContinuedGoal = this.moDecisionEngine.initContinuedGoal(moReachableGoalList_IN, moShortTimeMemory);
            log.trace("Incoming goals after getting continued goal: " + moReachableGoalList_IN);
            log.trace("Continued goal: " + oContinuedGoal);
        } catch (Exception e) {
            this.log.error(e.getMessage());
        }
		
		
		
		// --- INIT GOALS --- //
		//Preprocess all new goals and assign one goal as continued goal
//		clsWordPresentationMeshGoal oContinuedGoal = clsDecisionPreparationTools.getContinuedGoal(moShortTimeMemory, moReachableGoalList_IN);
//		if (oResult.isNullObject()==false) {
//            oResult.setCondition(eCondition.IS_CONTINUED_GOAL);
//        }
//        log.debug("Continued goal:" + oResult.toString());
		
		
		// --- PROVE CONTINUOUS CONDITIONS --- //
		//Start codelets for new continuous goals 
		//proveContinousConditions(oContinuedGoal);
		
		
		// --- APPEND PREVIOUS PERFORMED ACTIONS AS CONDITIONS --- //
		//clsDecisionPreparationTools.appendPreviousActionsAsPreconditions(oContinuedGoal, moShortTimeMemory);
		
		
		// --- APPLY ACTION CONSEQUENCES ON THE CONTINUED GOAL --- //
		//applyConsequencesOfActionsOnContinuedGoal(moReachableGoalList_IN, oContinuedGoal);
		this.moDecisionEngine.analyzeContinuedGoal(oContinuedGoal);
		
		
		// --- SET NEW PRECONDITIONS FOR ACTIONS AS WELL AS DEFAULT CONDITIONS FOR NEW GOALS --- //
		//setNewActionPreconditions(oContinuedGoal, moReachableGoalList_IN);
		//FIXME Put in F52 instead
		this.moDecisionEngine.generatePlan(oContinuedGoal);
		
		// --- ADD IMPORTANCE OF FEELINGS --- //
		//applyConsequencesOfFeelingsOnGoals(moReachableGoalList_IN);
		//applyConsequencesOfFeelingsOnGoal(oContinuedGoal);
		
//		// --- ADD EFFORT VALUES TO THE AFFECT LEVEL --- //
//		applyEffortOfGoal(moReachableGoalList_IN);
		
		// --- ADD the previous goal to the goal list if not already added --- //
		this.moDecisionEngine.addContinuedGoalToGoalList(moReachableGoalList_IN, oContinuedGoal);
		
		
		// --- ADD NON REACHABLE GOALS TO THE STM --- //
		addNonReachableGoalsToSTM(moReachableGoalList_IN);
		
		
		
		moReachableGoalList_OUT = moReachableGoalList_IN;
		
		//=== Process acts ===//
		//The act is accessed through the goal.
		//Take the first act in the list and process it
		//FIXME AW: In the first step, perform only simple processing
		
		//moPerceptionalMesh_OUT = moPerceptionalMesh_IN;
		//moAssociatedMemories_OUT = moAssociatedMemories_IN;
		//moExtractedPrediction_OUT = new ArrayList<clsPrediction>();
		//moGoalList_OUT = moGoalList_IN;
		
		
		/*
		//Update short time memory from last step
		if (moShortTimeMemory==null) {
			try {
				throw new Exception("moShortTimeMemory==null");
			} catch (Exception e) {
				// TODO (wendt) - Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//System.out.print("ShortTimeMemory: " + moShortTimeMemory.toString());
		moShortTimeMemory.updateTimeSteps();
		//FIXME AW: Should anything be done with the perception here?
		
		try {
			moEnvironmentalPerception_OUT = (clsDataStructureContainerPair)moEnvironmentalPerception_IN.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		
		//Perception, add objects to the localization storage
		updateLocalization(moEnvironmentalPerception_OUT, moTempLocalizationStorage);
		
		//Check if some expectations are confirmed
		confirmExpectations(moAssociatedMemoriesSecondary_IN, mrMomentActivationThreshold, moShortTimeMemory);
		
		//Get the new predictions
		moExtractedPrediction_OUT = extractPredictions(moAssociatedMemoriesSecondary_IN);
		
		//Pass the associated memories forward
		moAssociatedMemoriesSecondary_OUT = (ArrayList<clsDataStructureContainerPair>)deepCopy((ArrayList<clsDataStructureContainerPair>)moAssociatedMemoriesSecondary_IN);
		
		//printImageText(moExtractedPrediction_OUT);*/
	}
	
	   private ArrayList<clsWordPresentationMeshGoal> JACKBAUERHACKReduceGoalList(ArrayList<clsWordPresentationMeshGoal> moReachableGoalList_IN) {
	        //Keep only Libidonous stomach with cake
	        boolean bPerceivedFound = false;
	        boolean bActFound = false;
	        boolean bDriveFound = false;
	        
	        ArrayList<clsWordPresentationMeshGoal> oReplaceList = new ArrayList<clsWordPresentationMeshGoal>();
	        
	        for (clsWordPresentationMeshGoal oG : moReachableGoalList_IN) {
	            eGoalType oGoalType = oG.getGoalType();
	            
	            if (bPerceivedFound==false && oGoalType.equals(eGoalType.PERCEPTIONALDRIVE) && oG.getGoalObject().getMoContent().equals("CAKE") && oG.getGoalName().equals("LIBIDINOUSSTOMACH")) {
	                oReplaceList.add(oG);
	                bPerceivedFound=true;
	            } else if (bActFound==false && oGoalType.equals(eGoalType.MEMORYDRIVE) && oG.getGoalObject().getMoContent().equals("CAKE") && oG.getGoalName().equals("LIBIDINOUSSTOMACH")) {
	                oReplaceList.add(oG);
	                bActFound=true;
	            } else if (bDriveFound==false && oGoalType.equals(eGoalType.DRIVESOURCE) && oG.getGoalObject().getMoContent().equals("CAKE") && oG.getGoalName().equals("LIBIDINOUSSTOMACH")) {
	                oReplaceList.add(oG);
	                bDriveFound=true;
	            }

	        }
	        
	        //moReachableGoalList_IN = oReplaceList;
	        return oReplaceList;
	        
	    }
	

	

	
	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 12.02.2013 11:41:42
	 *
	 * @param poGoalList
	 */
	private void addNonReachableGoalsToSTM(ArrayList<clsWordPresentationMeshGoal> poGoalList) {
		for (clsWordPresentationMeshGoal oGoal : poGoalList) {
			if (oGoal.checkIfConditionExists(eCondition.GOAL_NOT_REACHABLE)==true) {
				clsWordPresentationMesh oMentalSituation = this.moShortTimeMemory.findCurrentSingleMemory();
				clsMentalSituationTools.setExcludedGoal(oMentalSituation, oGoal);
				
				log.debug("Added non reachable goal to STM : " + oGoal.toString());
			}
		}
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
		send_I6_7(moReachableGoalList_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 18.05.2010, 17:51:11
	 * 
	 * @see pa.interfaces.send.I2_13_send#send_I2_13(java.util.ArrayList)
	 */
	@Override
	public void send_I6_7(ArrayList<clsWordPresentationMeshGoal> poReachableGoalList)
			{
		((I6_7_receive)moModuleList.get(26)).receive_I6_7(poReachableGoalList);
		
		putInterfaceData(I6_7_send.class, poReachableGoalList);
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

		
	
}

