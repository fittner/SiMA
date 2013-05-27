/**
 * E26_DecisionMaking.java: DecisionUnits - pa.modules
 * 
 * @author kohlhauser
 * 11.08.2009, 14:51:57
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.SortedMap;

import org.apache.log4j.Logger;

import config.clsProperties;
import config.personality_parameter.clsPersonalityParameterContainer;
import pa._v38.tools.clsImportanceTools;
import pa._v38.tools.clsGoalTools;
import pa._v38.tools.clsMentalSituationTools;
import pa._v38.tools.clsMeshTools;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;

import pa._v38.decisionpreparation.clsDecisionPreparationTools;
import pa._v38.interfaces.modules.I6_3_receive;
import pa._v38.interfaces.modules.I6_7_receive;
import pa._v38.interfaces.modules.I6_2_receive;
import pa._v38.interfaces.modules.I6_8_receive;
import pa._v38.interfaces.modules.I6_8_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.itfModuleMemoryAccess;
import pa._v38.memorymgmt.datatypes.clsAct;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshFeeling;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.enums.eAction;
import pa._v38.memorymgmt.enums.eEmotionType;
import pa._v38.memorymgmt.enums.eGoalType;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;
import pa._v38.memorymgmt.storage.DT3_PsychicEnergyStorage;

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
			 I6_2_receive, I6_3_receive, I6_7_receive, I6_8_send {
	public static final String P_MODULENUMBER = "26";
	
	/** Specialized Logger for this class */
	private Logger log = Logger.getLogger(this.getClass());
	
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
	private ArrayList<clsWordPresentationMeshGoal> moReachableGoalList_IN;
	/** DOCUMENT (wendt) - insert description; @since 31.07.2011 14:14:07 */
	private ArrayList<clsWordPresentationMeshGoal> moDecidedGoalList_OUT;
	
	private ArrayList<clsWordPresentationMeshGoal> moDriveGoalList_IN;
	
	/** DOCUMENT (wendt) - insert description; @since 31.07.2011 14:14:01 */
	private ArrayList<clsAct> moRuleList; 
	
	private clsShortTermMemory moShortTermMemory;
	
	private String moTEMPDecisionString = "";
//	/** DOCUMENT (wendt) - insert description; @since 31.07.2011 14:14:03 */
//	private clsDataStructureContainerPair moEnvironmentalPerception_IN;
//	
//	/** DOCUMENT (wendt) - insert description; @since 31.07.2011 14:14:03 */
//	private clsDataStructureContainerPair moEnvironmentalPerception_OUT;
//	
//	//AW 20110602 Added expectations, intentions and the current situation
//	/** DOCUMENT (wendt) - insert description; @since 31.07.2011 14:14:05 */
//	private ArrayList<clsPrediction> moExtractedPrediction_IN;
//
//	/** DOCUMENT (wendt) - insert description; @since 31.07.2011 14:14:05 */
//	private ArrayList<clsPrediction> moExtractedPrediction_OUT;
	
//	/** Associated memories IN */
//	//private ArrayList<clsDataStructureContainerPair> moAssociatedMemories_IN;
//	
//	/** Associated memories OUT */
//	private ArrayList<clsDataStructureContainerPair> moAssociatedMemories_OUT;
	
	// Anxiety from F20
	private ArrayList<clsWordPresentationMesh> moAnxiety_Input;
	
	private static String _Delimiter01 = ":"; 
	private static String _Delimiter02 = "||";
	private static String _Delimiter03 = "|";
	
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
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, itfModuleMemoryAccess poLongTermMemory, clsShortTermMemory poShortTimeMemory, clsShortTermMemory poTempLocalizationStorage,DT3_PsychicEnergyStorage poPsychicEnergyStorage, clsPersonalityParameterContainer poPersonalityParameterContainer) throws Exception {
		
		super(poPrefix, poProp, poModuleList, poInterfaceData, poLongTermMemory);
		
		this.moPsychicEnergyStorage = poPsychicEnergyStorage;
        this.moPsychicEnergyStorage.registerModule(mnModuleNumber);
        
		applyProperties(poPrefix, poProp);	
		
		mnNumberOfGoalsToPass=poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_GOAL_PASS).getParameterInt();
		mrAffectThresold=poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_AFFECT_THRESHOLD).getParameterDouble();
		mnAvoidIntensity=poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_AVOIC_INTENSITY).getParameterInt();

		
		//Get short time memory
		moShortTermMemory = poShortTimeMemory;
		
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
		
		text += toText.listToTEXT("moAnxiety_Input", moAnxiety_Input);
		
		text += toText.valueToTEXT("CURRENT DECISION", this.moTEMPDecisionString);
		
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
		
		//TEMP Apply effort on goal
        applyEffortOfGoal(moReachableGoalList_IN);
		

        
        
        
        
        
        //Sort incoming drives
		ArrayList<clsWordPresentationMeshGoal> oDriveGoalListSorted = clsImportanceTools.sortGoals(moDriveGoalList_IN);
		
		
		//From the list of drives, match them with the list of potential goals
		moDecidedGoalList_OUT = processGoals(moReachableGoalList_IN, oDriveGoalListSorted, moRuleList, bActivatePanicInfluence);
		
		//Add the goal to the mental situation
		if (moDecidedGoalList_OUT.isEmpty()==false) {
			addGoalToMentalSituation(moDecidedGoalList_OUT.get(0));
			
			//oResult += "\nACT: " + clsGoalTools.getSupportiveDataStructure(this).toString();
			log.info("Decided goal: " + moDecidedGoalList_OUT.get(0) + "\nSUPPORTIVE DATASTRUCTURE: " + moDecidedGoalList_OUT.get(0).getSupportiveDataStructure().toString());
			this.moTEMPDecisionString = setDecisionString(moDecidedGoalList_OUT.get(0));
			//System.out.println(moTEMPDecisionString);
			//clsLogger.jlog.debug("Preconditions: " + clsGoalTools.getTaskStatus(moDecidedGoalList_OUT.get(0)).toString());
		} else {
			log.info("Decided goal: No goal ");
		}
		
		
		
	}
	
	   /**
     * DOCUMENT (wendt) - insert description
     *
     * @since 12.02.2013 11:41:40
     *
     * @param poGoalList
     */
    private void applyEffortOfGoal(ArrayList<clsWordPresentationMeshGoal> poGoalList) {
        for (clsWordPresentationMeshGoal oGoal : poGoalList) {
            //Get the penalty for the effort
            double oImportanceValue = clsDecisionPreparationTools.calculateEffortPenalty(oGoal);
            
            oGoal.setEffortLevel(oImportanceValue);
        }
        
        log.debug("Applied effort to goals:" + poGoalList.toString());
        
    }
	
	private String setDecisionString(clsWordPresentationMeshGoal poDecidedGoal) {
		String oResult = "";
		
		//Get the Goal String
		String oGoalString = poDecidedGoal.getGoalName();
		
		//Get the goal object
		String oGoalObjectString = poDecidedGoal.getGoalObject().toString();
		
		//Get the Goal source
		String oGoalSource = "NONE"; 
		if (poDecidedGoal.checkIfConditionExists(eCondition.IS_DRIVE_SOURCE)) {
			oGoalSource = "DRIVES (Drive goal not found in perception or acts)";
		} else if (poDecidedGoal.checkIfConditionExists(eCondition.IS_MEMORY_SOURCE)) {
			oGoalSource = "ACT";
		} else if (poDecidedGoal.checkIfConditionExists(eCondition.IS_PERCEPTIONAL_SOURCE)) {
			oGoalSource = "PERCEPTION";
		}
		
		//Get the AffectLevel
		String oAffectLevel = String.valueOf(poDecidedGoal.getImportance()); //eAffectLevel.convertQuotaOfAffectToAffectLevel(clsGoalTools.getAffectLevel(poDecidedGoal)).toString();
		
		//Get Conditions
		String oGoalConditions = "";
		ArrayList<eCondition> oConditionList = poDecidedGoal.getCondition();
		for (eCondition oC : oConditionList) {
			oGoalConditions += oC.toString() + "; ";
		}				
		
		//Get the Supportive DataStructure
		String oSupportiveDataStructureString = "";
		if (poDecidedGoal.checkIfConditionExists(eCondition.IS_DRIVE_SOURCE)) {
			oSupportiveDataStructureString = poDecidedGoal.getSupportiveDataStructure().toString();
		} else if (poDecidedGoal.checkIfConditionExists(eCondition.IS_MEMORY_SOURCE)) {
			oSupportiveDataStructureString = poDecidedGoal.getSupportiveDataStructure().toString();	
		} else if (poDecidedGoal.checkIfConditionExists(eCondition.IS_PERCEPTIONAL_SOURCE)) {
			oSupportiveDataStructureString = poDecidedGoal.getSupportiveDataStructure().toString();
		}
		
		//Set the current decision string
		oResult += "============================================================================================\n";
		oResult += "[GOAL NAME]\n   " + oGoalString + "\n\n";
		oResult += "[GOAL OBJECT]\n   " + oGoalObjectString + "\n\n";
		oResult += "[GOAL SOURCE]\n   " + oGoalSource + "\n\n";
		oResult += "[IMPORTANCE/PLEASURELEVEL]\n   " + oAffectLevel + "\n\n";
		oResult += "[GOAL CONDITIONS]\n   " + oGoalConditions + "\n\n";
		oResult += "[SUPPORTIVE DATASTRUCTURE]\n   " + oSupportiveDataStructureString + "\n";
		oResult += "============================================================================================\n";
		
		return oResult;
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
	public void receive_I6_7(ArrayList<clsWordPresentationMeshGoal> poReachableGoalList) {
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
	public void receive_I6_2(ArrayList<clsWordPresentationMesh> poAnxiety_Input) {
		moAnxiety_Input = (ArrayList<clsWordPresentationMesh>)deepCopy(poAnxiety_Input);	
		//TODO
		
	}
	
	/* (non-Javadoc)
	 *
	 * @since 04.07.2012 10:02:08
	 * 
	 * @see pa._v38.interfaces.modules.I6_3_receive#receive_I6_3(java.util.ArrayList)
	 */
	@Override
	public void receive_I6_3(ArrayList<clsWordPresentationMeshGoal> poDriveList) {
		moDriveGoalList_IN = (ArrayList<clsWordPresentationMeshGoal>)this.deepCopy(poDriveList);
	}
	
	private void addGoalToMentalSituation(clsWordPresentationMeshGoal poGoal) {
		//get the ref of the current mental situation
		clsWordPresentationMesh oCurrentMentalSituation = this.moShortTermMemory.findCurrentSingleMemory();
		
		clsMentalSituationTools.setGoal(oCurrentMentalSituation, poGoal);
	}

	
	
	private ArrayList<clsWordPresentationMeshGoal> processGoals(
			ArrayList<clsWordPresentationMeshGoal> poPossibleGoalInputs, 
			ArrayList<clsWordPresentationMeshGoal> poDriveList, 
			ArrayList<clsAct> poRuleList, boolean bActivateEmotionalInfluence) {
		ArrayList<clsWordPresentationMeshGoal> oRetVal = new ArrayList<clsWordPresentationMeshGoal>();
		
		int nAddedGoals = 0;
		
		//Process emotions
		clsWordPresentationMeshGoal oPanicGoal = generatePanicGoalFromFeeling(this.moAnxiety_Input);
		if (oPanicGoal.isNullObject()==false && bActivateEmotionalInfluence==true) {
			oRetVal.add(oPanicGoal);
			log.trace("Added panic goal" + oPanicGoal);
		} else {
			//1. Process goals with Superego???

			//2. Sort the goals to get the most important goal first
			
			//3. Remove unreachable goals direct from the list
			//TODO: Make this fuction like a value funtion and not just remove if precondition
			removeNonReachableGoals(poPossibleGoalInputs);
			
			//=== Sort and evaluate them === //
			ArrayList<clsWordPresentationMeshGoal> oSortedReachableGoalList = clsGoalTools.sortAndEnhanceGoals(poPossibleGoalInputs, poDriveList, mrAffectThresold);
			
			//Add all goals to this list
			for (clsWordPresentationMeshGoal oReachableGoal : oSortedReachableGoalList) {
				if (nAddedGoals<mnNumberOfGoalsToPass) {
					oRetVal.add(oReachableGoal);
					nAddedGoals++;
				} else {
					break;
				}

			}
		}
		
		return oRetVal;
	}
	
	
	/**
	 * Search the perception or memories for goals with very strong negative affect. These object are converted to drive demands
	 * and put on the to of the priolist. This forces the agent to avoid these objects
	 * (wendt)
	 *
	 * @since 17.09.2011 08:27:41
	 *
	 * @param poPotentialGoalList
	 * @return
	 */
	private clsWordPresentationMeshGoal generatePanicGoalFromFeeling(ArrayList<clsWordPresentationMesh> poFeelingList) {
		clsWordPresentationMeshGoal oResult = clsGoalTools.getNullObjectWPM();
		
		if (poFeelingList.isEmpty()==false) {
			if (eEmotionType.valueOf(poFeelingList.get(0).getMoContent()).equals(eEmotionType.ANXIETY) ||
					eEmotionType.valueOf(poFeelingList.get(0).getMoContent()).equals(eEmotionType.CONFLICT)) {
				oResult = clsGoalTools.createGoal("PANIC", eGoalType.EMOTIONSOURCE, -1, eAction.FLEE, new ArrayList<clsWordPresentationMeshFeeling>(), clsMeshTools.getNullObjectWPM(), clsMeshTools.getNullObjectWPM());
				oResult.setCondition(eCondition.PANIC);
			}	
		}
		
		return oResult;
	}
	
	/**
	 * Remove all non reachable goals, which are kept in the STM
	 * 
	 * (wendt)
	 *
	 * @since 25.07.2012 11:39:25
	 *
	 * @param poGoalList
	 */
	private void removeNonReachableGoals(ArrayList<clsWordPresentationMeshGoal> poGoalList) {
		ListIterator<clsWordPresentationMeshGoal> Iter = poGoalList.listIterator();
		
		ArrayList<clsWordPresentationMeshGoal> oRemoveList = new ArrayList<clsWordPresentationMeshGoal>();
		
		//Get all goals from STM
		ArrayList<clsPair<Integer, clsWordPresentationMesh>> oSTMList = this.moShortTermMemory.getMoShortTimeMemory();
		for (clsPair<Integer, clsWordPresentationMesh> oSTM : oSTMList) {
			//Check if precondition GOAL_NOT_REACHABLE_EXISTS and Goal type != DRIVE_SOURCE
		    ArrayList<clsWordPresentationMesh> oTEMPLIST = clsMentalSituationTools.getExcludedGoal(oSTM.b);
		    ArrayList<clsWordPresentationMeshGoal> oExcludedGoalList = new ArrayList<clsWordPresentationMeshGoal>();
		    for (clsWordPresentationMesh oWPM : oTEMPLIST) {
		        oExcludedGoalList.add((clsWordPresentationMeshGoal) oWPM);
		    }
		    
		     
			oRemoveList.addAll(oExcludedGoalList);
//			for (clsWordPresentationMesh oExcludedGoal : oExcludedGoalList) {
//				if (clsGoalTools.checkIfConditionExists(oSTM.b, eCondition.GOAL_NOT_REACHABLE)==true) {
//					oRemoveList.add(oSTM.b);
//				}
//			}
			
		}
						
		//Find all unreachable goals from STMList
		while (Iter.hasNext()) {
			clsWordPresentationMeshGoal oGoal = Iter.next();
			
			//Check if this is one of the STM goals, which shall be removed
			for (clsWordPresentationMeshGoal oRemoveGoal : oRemoveList) {
				if (oGoal.getGoalContentIdentifier().equals(oRemoveGoal.getGoalContentIdentifier())==true) {
					//if yes, remove this goal		
					Iter.remove();
					log.debug("Non reachable goal removed: " + oGoal.toString());
				}
			}
			
		}
		
	}
	
	/**
	 * From all of the goals in the list, they shall all be merged by type, in order to pass a complete content, of what can be found in the intention.
	 * 
	 * 
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 29.07.2011 21:21:35
	 *
	 * @param oFoundGoals
	 * @return
	 */
	/*private ArrayList<clsSecondaryDataStructureContainer> mergeImageGoals(ArrayList<clsSecondaryDataStructureContainer> oFoundGoals, clsWordPresentationMesh poTopImage) {
		ArrayList<clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
		
		//Create the ImageBase. This content shall be added to all 
		String oContentBase = poTopImage.getMoContent();
		
		//For each found expression, build the goals
		for (clsSecondaryDataStructureContainer oGoal : oFoundGoals) {
			
		}
		
		return oRetVal;
		
	}*/
	

	

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
	public void send_I6_8(ArrayList<clsWordPresentationMeshGoal> poDecidedGoalList_OUT) {
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




	
}

