/**
 * E27_GenerationOfImaginaryActions.java: DecisionUnits - pa.modules
 * 
 * @author deutsch/perner test
 * 11.08.2009, 14:55:01
 */
package secondaryprocess.modules;

import general.datamanipulation.PrintTools;
import inspector.interfaces.clsTimeChartPropeties;
import inspector.interfaces.itfInspectorGenericActivityTimeChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;
import memorymgmt.enums.eSpeech;
import memorymgmt.interfaces.itfModuleMemoryAccess;
import memorymgmt.shorttermmemory.clsEnvironmentalImageMemory;
import memorymgmt.shorttermmemory.clsShortTermMemory;
import memorymgmt.storage.DT3_PsychicIntensityStorage;
import modules.interfaces.I6_8_receive;
import modules.interfaces.I6_9_receive;
import modules.interfaces.I6_9_send;
import modules.interfaces.eInterfaces;
import secondaryprocess.functionality.PlanningFunctionality;
import secondaryprocess.functionality.decisionpreparation.DecisionEngine;
import testfunctions.clsTester;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshPossibleGoal;
import base.modules.clsModuleBase;
import base.modules.clsModuleBaseKB;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;

/**
 * DOCUMENT (perner) - Based on the goal delivered from module F26_Decision_Making, a set of action-plans is created in order to achieve the
 * goal. Each plan consists of plan-fragments. Based on images, actions a combined and the outcome of a plan-fragment respective of a plan
 * is defined. The generated action-plan is passed on the the next module F29_EvaluationOfImaginaryActions.
 * 
 * @author perner 09.10.2011
 * 
 */
public class F52_GenerationOfImaginaryActions extends clsModuleBaseKB implements I6_8_receive, I6_9_send, itfInspectorGenericActivityTimeChart {

	public static final String newline = System.getProperty("line.separator");

	public static final String P_MODULENUMBER = "52";
	
    private static final String P_MODULE_STRENGTH ="MODULE_STRENGTH";
    private static final String P_INITIAL_REQUEST_INTENSITY ="INITIAL_REQUEST_INTENSITY";
                
    private double mrModuleStrength;
    private double mrInitialRequestIntensity;
	
	/** Specialized Logger for this class */
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	
//	private static final boolean m_bUseDraftPlanning = false;
//	private static final boolean m_bPrintDebugOutput = false;

	// HZ Not used up to now 16.03.2011
	private ArrayList<clsWordPresentationMeshPossibleGoal> moGoalList_IN;
	//private ArrayList<ArrayList<clsAct>> moPlanInput;

	/** DOCUMENT (wendt) - insert description; @since 31.07.2011 21:25:26 */
	//private ArrayList<clsPrediction> moExtractedPrediction_IN;

//	private clsWordPresentationMesh moPerceptionalMesh_IN;
	//private clsWordPresentationMesh moPerceptionalMesh_OUT;
	
	private ArrayList<String> moTEMPWriteLastActions = new ArrayList<String>();
	
	//private clsCodeletHandler moCodeletHandler;
	private DecisionEngine moDecisionEngine;
	
//	private ArrayList<clsWordPresentationMesh> moMotilityActions_IN;
//	private ArrayList<clsWordPresentationMesh> moImaginaryActions_IN;

	/** Associated memories out */
	//private ArrayList<clsWordPresentationMesh> moAssociatedMemories_OUT;
	private ArrayList<clsWordPresentationMeshPossibleGoal> moGoalList_OUT;
	//private ArrayList<clsPlanFragment> moAvailablePlanFragments;
	//private ArrayList<clsPlanFragment> moCurrentApplicalbePlans;

	///** generated plans */
	//private ArrayList<clsPlan> plansFromPerception = new ArrayList<clsPlan>();

	private clsShortTermMemory moShortTermMemory;
	
	private clsEnvironmentalImageMemory moEnvironmentalImageStorage;
	
	
	//private PlanningGraph plGraph;
	
	private final  DT3_PsychicIntensityStorage moPsychicEnergyStorage;
	
	private clsWordPresentationMesh moWordingToContext;
	
	private String EffortOfSpeech;
	//private ArrayList<clsWordPresentationMesh> moPossibleInternalActionPlans;

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
	    SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, itfModuleMemoryAccess poLongTermMemory, clsShortTermMemory poShortTermMemory, clsEnvironmentalImageMemory poTempLocalizationStorage, DecisionEngine poDecisionEngine,
		DT3_PsychicIntensityStorage poPsychicEnergyStorage, clsPersonalityParameterContainer poPersonalityParameterContainer, int pnUid) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poLongTermMemory, pnUid);

        mrModuleStrength = poPersonalityParameterContainer.getPersonalityParameter("F51", P_MODULE_STRENGTH).getParameterDouble();
        mrInitialRequestIntensity =poPersonalityParameterContainer.getPersonalityParameter("F51", P_INITIAL_REQUEST_INTENSITY).getParameterDouble();

        this.moPsychicEnergyStorage = poPsychicEnergyStorage;
        this.moPsychicEnergyStorage.registerModule(mnModuleNumber, mrInitialRequestIntensity, mrModuleStrength);
		 
		//Get STM
		this.moShortTermMemory = poShortTermMemory;
		
		//Get Perceived image
		this.moEnvironmentalImageStorage = poTempLocalizationStorage;
		
		//this.moCodeletHandler = poCodeletHandler;
		moDecisionEngine = poDecisionEngine;
		
		//moAvailablePlanFragments = new ArrayList<clsPlanFragment>();

		applyProperties(poPrefix, poProp);

//		// just used to test if the planning module does not have any compile
//		// errors
//		if (m_bUseDraftPlanning) {
//			moAvailablePlanFragments = TestDataCreator.generateTestPlans_AP();
//		} else {
//			moAvailablePlanFragments = TestDataCreator.generateTestPlans_AW();
//		}
//
//		/** init planning engine */
//		plGraph = new PlanningGraph();
//		plGraph.m_bPrintDebugOutput = m_bPrintDebugOutput;
//		try {
//			/** add plans to planning engine */
//			PlanningWizard.initPlGraphWithActions(moAvailablePlanFragments, plGraph);
//			/** create connections between plans */
//			PlanningWizard.initPlGraphWithPlConnections(moAvailablePlanFragments, plGraph);
//			/** print plans to sysout */
//			PlanningWizard.printPlanningStack(plGraph);
//
//		} catch (Exception e) {
//			log.error("Error initializing planning Wizard", e);
//		}
		
		//TEST-Internal-Plans AW
		//moPossibleInternalActionPlans = addNewDecisionTaskImages();
		
	}
//
//	/***********************************************************************************************
//	 * BEGINN generic class methods below are generic class methods which are shared through all implementations of ARS-modules
//	 **********************************************************************************************/

	/*
	 * (non-Javadoc)
	 * 
	 * @author deutsch 11.08.2009, 16:16:38
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
                log.warn("System tests activated");
                for (clsWordPresentationMesh mesh : this.moGoalList_IN) {
                    
                    clsTester.getTester().exeTestCheckLooseAssociations(mesh); 
                }
            } catch (Exception e) {
                log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
            }
        }
	    
		//Load perception
		//moPerceptionalMesh_IN = this.moEnvironmentalImageStorage.getEnvironmentalImage();
		//clsWordPresentationMesh oCurrentMentalSituation = this.moShortTermMemory.findCurrentSingleMemory();
		//clsWordPresentationMeshGoal oGoal = clsMentalSituationTools.getGoal(oCurrentMentalSituation);
		
		
		
		
        // --- SET NEW PRECONDITIONS FOR ACTIONS AS WELL AS DEFAULT CONDITIONS FOR NEW GOALS --- //
        //setNewActionPreconditions(oContinuedGoal, moReachableGoalList_IN);
        //FIXME Put in F52 instead
		//Decision codelets
        //this.moDecisionEngine.generatePlan(oContinuedGoal);
		
		PlanningFunctionality.generatePlanForGoals(this.moDecisionEngine, this.moGoalList_IN);
		
//		String GoalString;
//		
//		GoalString = this.moGoalList_IN.toString();
//		
//		if   (GoalString.contains("A07_SPEAK_EAT")){
//		    
//		    CalculatePossibleWordingsBasedOnContext(); 
//		    
//		}
		
		
		
		

//		if (m_bUseDraftPlanning) {
//			process_draft();
		//} else {
			//Testfocus. If focusing was the last step, then do normal action, else do focus
			
			//=======================================================================//
			//FIXME: Remove this, when deepcopy is removed. Process the goal list
			//FIXME AW: This is a hack as deepcopy destroys the hashcode. Therefore, everything is saved in the storage and loaded on demand. In this case, getGoal is taken
			
		
		//this.moGoalList_IN = new ArrayList<clsWordPresentationMeshGoal>();
		//if (oGoal.getMoContentType().equals(eContentType.NULLOBJECT)==false) {
		//	this.moGoalList_IN.add(oGoal);
		//}
			
		//=======================================================================//
			
		// Generate actions for the top goal
		//try {
			
		    //FIXME TEMP this method should be within the decision engine in a 2 stage decision making
            //this.moDecisionEngine.getCodeletHandler().executeMatchingCodelets(this, oCurrentGoal, eCodeletType.ACTION, 1);
            

            
            
            // HACK (IH): replaced every EAT action with a SPEECH action to the test inventory
//            if(oActionWPM.getMoContent() == "EAT")
//            {
//                oActionWPM.setMoContent("SPEAK");
//            }
            // END HACK (Kivy)
            

		    
		    
		    //moGoalList_OUT = processGoals_AW(moGoalList_IN);
				
		    moGoalList_OUT = moGoalList_IN;
		    
			//--- System printout of all important action dates ---//
			log.info("Actions added to the goals " + PrintTools.printArrayListWithLineBreaks(moGoalList_OUT));
			//System.out.println("\nGoal: " +moGoalList_IN.toString() + "; Preconditions: " + clsGoalTools.getTaskStatus(moGoalList_IN.get(0)).toString() + "; Action: " + moPlans_Output.toString());
			//System.out.println("Environmental Storage: " + this.moEnvironmentalImageStorage.toString());
				
			//-----------------------------------------------------//
//		} catch (Exception e) {
//			// TODO (wendt) - Auto-generated catch block
//			e.printStackTrace();
//		} //generatePlans_AW(moPerceptionalMesh_IN, moGoalList_IN);
		//FIXME HACK AW: Generate the search pattern
			
//		//UNREAL IMAGE CORRECTION HACK
//		for (clsWordPresentationMesh oPlan : moGoalList_OUT) {
//			if (oPlan.getMoContent().equals("UNREAL_MOVE_TO")) {
//				//Extract Entity
//				clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oEntityInfo = clsEntityTools.getPosition((clsWordPresentationMesh) oPlan.getExternalAssociatedContent().get(0).getLeafElement());
//				String oNewContent = oPlan.getMoContent() + "|" + oEntityInfo.a.getMoContent() + ":" + oEntityInfo.b.toString() + ":" + oEntityInfo.c.toString();
//				oPlan.setMoContent(oNewContent);
//				//UNREAL_MOVE_TO|HEALTH:LEFT:FAR
//			}
//					
//		}
			//moPlans_Output.addAll(planSearch());

			// Pass forward the associated memories and perception
//			try {
//				moPerceptionalMesh_OUT = (clsWordPresentationMesh) moPerceptionalMesh_IN.clone();
//			} catch (CloneNotSupportedException e) {
//				e.printStackTrace();
//			}
//			moAssociatedMemories_OUT = (ArrayList<clsWordPresentationMesh>) deepCopy(moAssociatedMemories_IN);

			// printData(moActions_Output, moGoalInput,
			// moExtractedPrediction_IN);;
			
		//}
		
//		//FIXME AW: TEMP Get the last 10 goals and actions for the inspector
//		clsWordPresentationMesh oCurrentMentalSituation = this.moShortTermMemory.findCurrentSingleMemory();
//		clsWordPresentationMesh oGoal = clsMentalSituationTools.getGoal(oCurrentMentalSituation);
//		String oAction = "NONE";
//		
//		if (moGoalList_OUT.isEmpty()==false) {
//			oAction = moGoalList_OUT.get(0).getMoContent();
//		}
//		
//		if (moTEMPWriteLastActions.size()==10) {
//			moTEMPWriteLastActions.remove(0);
//		}
//		
//		//Get System time
//		Calendar oCal = Calendar.getInstance();
//		SimpleDateFormat oDateFormat = new SimpleDateFormat("HH:mm:ss");
//		moTEMPWriteLastActions.add(oDateFormat.format(oCal.getTime()) + "> " + "Goal: " + oGoal.getMoContent().toString() + "; Action: " + oAction);
		
	            
		double rRequestedPsychicIntensity = 0.0d;
		                
		double rReceivedPsychicEnergy = moPsychicEnergyStorage.send_D3_1(mnModuleNumber);
		            
		double rConsumedPsychicIntensity = rReceivedPsychicEnergy;
		            
		moPsychicEnergyStorage.informIntensityValues(mnModuleNumber, mrModuleStrength, rRequestedPsychicIntensity, rConsumedPsychicIntensity);

	}

	/**
 * DOCUMENT - insert description
 *
 * @author hinterleitner
 * @since 07.01.2014 19:32:53
 *
 */
private void        CalculatePossibleWordingsBasedOnContext() {
  //based on preselected speech statement it is decided what can actually spoken 
    if (moWordingToContext.toString().contains("EAT")) {
    EffortOfSpeech = CalculatePossibleSpeechStatements(eSpeech.SPEAK_EAT);
   
    }
    
    if (moWordingToContext.toString().contains("YES")) {
    EffortOfSpeech = CalculatePossibleSpeechStatements(eSpeech.SPEAK_YES);
    }
    
    //Is not working yet
    if (moWordingToContext.toString().contains("KNOWN")) {
        EffortOfSpeech = CalculatePossibleSpeechStatements(eSpeech.SPEAK_KNOWN);
        }
        
    if (moWordingToContext.toString().contains("INVITED")) {
        EffortOfSpeech = CalculatePossibleSpeechStatements(eSpeech.SPEAK_INVITED);
        }
        
  
}

    /**
 * DOCUMENT - insert description
 *
 * @author hinterleitner
 * @since 29.12.2013 20:16:11
 *
 * @param moWordingToContext2
 */
private static String CalculatePossibleSpeechStatements(eSpeech moWordingToContext2) {
    
    String oActionCondition = "";
    
    switch (moWordingToContext2) {
    case SPEAK_EAT:
        oActionCondition = "EAT or INVITED";
       break;
    case SPEAK_INVITED:
        oActionCondition = "INVITED or KNOWN";
       break;
    case SPEAK_KNOWN:
        oActionCondition = "KNOWN or INVITED";
       break;
    case SPEAK_YES:
        oActionCondition = "YES or NO";
       break;
                
}
    return oActionCondition;
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
		//moPlans_Output = generatePlans_AP(moPerceptionalMesh_IN, moGoalList_IN);

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

		//text += toText.listToTEXT("moPlanInput", moPlanInput);
		//text += toText.listToTEXT("moExtractedPrediction_IN", moExtractedPrediction_IN);
		text += toText.listToTEXT("moActions_Output", moGoalList_OUT);
		text += toText.listToTEXT("moGoalList_IN", moGoalList_IN);
		//text += toText.listToTEXT("moAssociatedMemories_OUT", moAssociatedMemories_OUT);

		text += toText.listToTEXT("Goals and actions", moTEMPWriteLastActions);
		
		text += newline;
		text += "current generated plans:";
		text += newline;

//		for (clsPlan singlePlan : plansFromPerception) {
//			text += PlanningWizard.dumpPlanToString(singlePlan) + newline;
//		}
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
	@Override
	public void receive_I6_8(ArrayList<clsWordPresentationMeshPossibleGoal> poDecidedGoalList, clsWordPresentationMesh moWordingToContext2) {
		//moGoalList_IN = (ArrayList<clsWordPresentationMesh>) deepCopy(poDecidedGoalList);
		moGoalList_IN = poDecidedGoalList;
	    moWordingToContext = moWordingToContext2;
		
	}
	
//	/* (non-Javadoc)
//	 *
//	 * @since 07.05.2012 14:28:53
//	 * 
//	 * @see pa._v38.interfaces.modules.I2_5_receive#receive_I2_5(java.util.ArrayList)
//	 */
//	@Override
//	public void receive_I2_5(ArrayList<clsWordPresentationMesh> poActionCommands) {
//		moMotilityActions_IN = (ArrayList<clsWordPresentationMesh>) deepCopy(poActionCommands); 
//		//moMotilityActions_IN = poActionCommands; 
//	}

//	/* (non-Javadoc)
//	 *
//	 * @since 07.05.2012 14:28:54
//	 * 
//	 * @see pa._v38.interfaces.modules.I6_11_receive#receive_I6_11(java.util.ArrayList, pa._v38.memorymgmt.datatypes.clsWordPresentationMesh)
//	 */
//	@Override
//	public void receive_I6_11(ArrayList<clsWordPresentationMesh> poActionCommands) {
//		moImaginaryActions_IN = (ArrayList<clsWordPresentationMesh>)deepCopy(poActionCommands);
//		//moImaginaryActions_IN = poActionCommands;
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
		send_I6_9(moGoalList_OUT, moWordingToContext);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @author deutsch 18.05.2010, 17:56:21
	 * 
	 * @see pa.interfaces.send.I7_3_send#send_I7_3(java.util.ArrayList)
	 */
	@Override
	public void send_I6_9(ArrayList<clsWordPresentationMeshPossibleGoal> poGoals, clsWordPresentationMesh moWordingToContext2) {
		
		((I6_9_receive) moModuleList.get(53)).receive_I6_9(poGoals, moWordingToContext2);

		putInterfaceData(I6_9_send.class, poGoals, moWordingToContext2);

	}

//	/***********************************************************************************************
//	 * END generic class methods
//	 **********************************************************************************************/
//
//	/***********************************************************************************************
//	 * BEGINN class specific methods (e.g. planning methods)
//	 **********************************************************************************************/
//
//	/**
//	 * 
//	 * DOCUMENT (perner) - generation of plans
//	 * 
//	 * @since 03.03.2012 10:56:06
//	 * 
//	 * @param poEnvironmentalPerception
//	 * @param poPredictionList
//	 * @param poGoalList
//	 * @return
//	 */
//	private ArrayList<clsWordPresentationMesh> generatePlans_AP(clsWordPresentationMesh poEnvironmentalPerception, ArrayList<clsWordPresentationMeshGoal> poGoalList) {
//
//		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
//
//		// Prepare perception to the Image structure of clsImage -> based on
//		// planning wizards
//		ArrayList<clsImage> oPIImageStructure = preparePerception(poEnvironmentalPerception);
//
//		// TODO: If plans shall be generated for more than one goals, this part
//		// shall be changed
//		ArrayList<clsWordPresentationMeshGoal> poReducedGoalList = new ArrayList<clsWordPresentationMeshGoal>();
//		if (poGoalList.isEmpty() == false) {
//			poReducedGoalList.add(poGoalList.get(0));
//		}
//
//		/** iterate over goals */
//		for (clsWordPresentationMeshGoal oGoal : poReducedGoalList) {
//			ArrayList<clsWordPresentationMesh> oActionContainer = new ArrayList<clsWordPresentationMesh>();
//
//			// If no plans could be generated for this goal, it is set false,
//			// else true
//			boolean bActionPlanOK = false;
//
//			clsWordPresentationMesh oTopImage = clsMeshTools.getSuperStructure(oGoal.getGoalObject());
//			if (oTopImage == null) {
//
//				/** go to next goal */
//				break;
//			}
//
//			/** handling if the image comes from memory */
//			if (oTopImage.getMoContentType().equals(eContentType.RI) == true
//			    && oTopImage.getMoContentType().equals(eContentType.PI) == false) {
//				ArrayList<clsWordPresentationMesh> oActionFromMemoryContainerList = new ArrayList<clsWordPresentationMesh>();
//				oActionContainer.addAll(oActionFromMemoryContainerList);
//
//				// If no plans could be generated for this goal do a search
//				if (oActionContainer.isEmpty() == false) {
//					bActionPlanOK = true;
//				}
//
//			}
//
//			/** handling if the image comes from perception */
//			if (oTopImage.getMoContentType().equals(eContentType.PI) == true && (bActionPlanOK == false)) {
//				ArrayList<clsWordPresentationMesh> oActionFromMemoryContainerList = planFromPerception_AP(oPIImageStructure, oGoal);
//				oActionContainer.addAll(oActionFromMemoryContainerList);
//
//				// If no plans could be generated for this goal do a search
//				if (oActionContainer.isEmpty() == false) {
//					bActionPlanOK = true;
//				}
//
//			}
//
//			/**
//			 * If the image is just a general goal without object then do a search
//			 */
//			// TODO add some kind of sophisticated search method here
//			if (bActionPlanOK == false) {
//
//				ArrayList<clsWordPresentationMesh> oActionFromMemoryContainerList = planFromNoObject(oGoal);
//				oActionContainer.addAll(oActionFromMemoryContainerList);
//
//				// If no plans could be generated for this goal do a search
//				if (oActionContainer.isEmpty() == false) {
//					bActionPlanOK = true;
//				}
//			}
//			oRetVal.addAll(oActionContainer);
//
//		}
//		return oRetVal;
//	}

	
//	private ArrayList<clsWordPresentationMesh> processGoals_AW(ArrayList<clsWordPresentationMeshGoal> poGoalList) throws Exception {
//		ArrayList<clsWordPresentationMesh> oResult = new ArrayList<clsWordPresentationMesh>();
//		
//		if (poGoalList.isEmpty()==false) {
//			clsWordPresentationMeshGoal oCurrentGoal = poGoalList.get(0);
//			
//			//Get actions from codelets
//			//FIXME TEMP this method should be within the decision engine in a 2 stage decision making
//			this.moDecisionEngine.getCodeletHandler().executeMatchingCodelets(this, oCurrentGoal, eCodeletType.ACTION, 1);
//			
//			//Extract action from goal
//			clsWordPresentationMesh oActionWPM = null;
//			//if (oCurrentGoal.checkIfConditionExists(eCondition.IS_NEW_CONTINUED_GOAL)) {
//			    oActionWPM = oCurrentGoal.getAssociatedPlanAction();
//			//} else {
//			    //oActionWPM = clsMeshTools.getNullObjectWPM();
//			//}
//			
//			
//			// HACK (IH): replaced every EAT action with a SPEECH action to the test inventory
//			if(oActionWPM.getMoContent() == "EAT")
//			{
//			    oActionWPM.setMoContent("SPEAK");
//			}
//			// END HACK (Kivy)
//			
//			if (oActionWPM.isNullObject()==false) { //&& oCurrentGoal.checkIfConditionExists(eCondition.IS_CONTINUED_GOAL)) {
//				oResult.add(oActionWPM);
//			} else {
//				oResult.add(clsActionTools.createAction(eAction.NONE));
//			}
//			
//		
//		} else {
//			oResult.add(clsActionTools.createAction(eAction.NONE));
//		}	
//		
//		return oResult;
//	}
	
	
//	/**
//	 * Sort all actions for the best precondition. Function: The more specialized an action is, the higher ranked. The more generalized actions are lower ranked
//	 * 
//	 * (wendt)
//	 *
//	 * @since 25.07.2012 19:49:45
//	 *
//	 * @param poActionList
//	 * @param poPreconditionStatusList
//	 * @return
//	 */
//	private ArrayList<clsWordPresentationMesh> sortMostSpecializedAction(ArrayList<clsWordPresentationMesh> poActionList, ArrayList<eCondition> poPreconditionStatusList) {
//		ArrayList<clsWordPresentationMesh> oResult = new ArrayList<clsWordPresentationMesh>();
//		
//		ArrayList<clsPair<Double, clsWordPresentationMesh>> oOpenToSortList = new ArrayList<clsPair<Double, clsWordPresentationMesh>>();
//		
//		//Go through all actions and get the number of successful preconditions 
//		for (clsWordPresentationMesh poAction : poActionList) {
//			ArrayList<eCondition> oPreconditionForActionList = clsActionTools.getPreconditions(poAction);
//			double nScore = 0;
//			
//			for (eCondition oPreconditionForAction : oPreconditionForActionList) {
//				if (poPreconditionStatusList.contains(oPreconditionForAction)==true) {
//					nScore+=1;
//				}
//			}
//			
//			oOpenToSortList.add(new clsPair<Double, clsWordPresentationMesh>(nScore, poAction));			
//		}
//		
//		ArrayList<clsPair<Double, clsWordPresentationMesh>> oSortedActionList =  clsImportanceTools.sortAndFilterRatedStructures(oOpenToSortList, -1);
//		
//		for (clsPair<Double, clsWordPresentationMesh> oAction : oSortedActionList) {
//			oResult.add(oAction.b);
//		}
//		
//		return oResult;
//	}

//	/**
//	 * Planning based on perception
//	 * 
//	 * @since 26.09.2011 14:20:17
//	 * 
//	 * @param poEnvironmentalPerception
//	 * @param poGoalList
//	 * @return
//	 */
//	private ArrayList<clsWordPresentationMesh> planFromPerception_AP(ArrayList<clsImage> poPIImageStructure, clsWordPresentationMeshGoal poGoal) {
//
//		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
//
//		// Filter all object, which are not drive objects of this goal
//		ArrayList<clsImage> ofilteredImages = filterForDecisionMakingGoal(poGoal, poPIImageStructure);
//
//		// check, which actions can be executed next
//		ArrayList<clsPlanFragment> currentApplicalbePlanningNodes = PlanningWizard.getCurrentApplicablePlanningNodes(moAvailablePlanFragments,
//		    ofilteredImages);
//
//		// TODO create code for high depth plans here
//		if (currentApplicalbePlanningNodes.size() > 0) {
//			int i = 0;
//		}
//
////		System.out.println(getClass() + " ********************** start to generate a plan from perception ********************** ");
////		System.out.println(getClass() + " current applicable planning planFragments >" + currentApplicalbePlanningNodes.size() + "< content >"
////		    + currentApplicalbePlanningNodes + "<");
//		if (m_bPrintDebugOutput) {
//			log.debug(" ********************** start to generate a plan from perception ********************** ");
//			log.debug(" current applicable planning planFragments >" + currentApplicalbePlanningNodes.size()
//			    + "< content >" + currentApplicalbePlanningNodes + "<");
//		}
//		/** reset list and store new plans */
//		plansFromPerception.clear();
//
//		/** run through applicable plans and see which results can be achieved by executing plFragment */
//		for (clsPlanFragment plFragment : currentApplicalbePlanningNodes) {
////			System.out.println(getClass() + " generating plan for planFragment >" + plFragment + "<");
//			if (m_bPrintDebugOutput) {
//				log.debug(" generating plan for planFragment >" + plFragment + "<");
//			}
//
//			plGraph.setStartPlanningNode(plFragment);
//			plGraph.breathFirstSearch();
//			ArrayList<clsPlan> plans = plGraph.getPlans();
//
//			if (m_bPrintDebugOutput) {
//				PlanningWizard.printPlans(plans);
//			}
//
//			/** add all plans */
//			for (clsPlan singlePlan : plans) {
//				plansFromPerception.add(singlePlan);
//			}
//
//		}
//
//		// copy output -> workaround till planning works correctly
//		oRetVal.addAll(copyPlanFragments(currentApplicalbePlanningNodes));
//		ArrayList<PlanningNode> plansTemp = new ArrayList<PlanningNode>();
//
//		for (clsPlanFragment myPlans : currentApplicalbePlanningNodes)
//			plansTemp.add(myPlans);
//
//		// output actions
//		// PlanningWizard.printPlansToSysout(plansTemp , 0);
//		// plGraph.m_planningResults.get(1)
//
//		// copy perception for movement control
//		// moEnvironmentalPerception_OUT = moEnvironmentalPerception_IN;
//
//		// plGraph.setStartPlanningNode(n)
//		return oRetVal;
//	}
//
//	/**
//	 * convert clsAct as defined in Protege to a clsPlanFragment (wendt)
//	 * 
//	 * @since 26.09.2011 16:36:04
//	 * 
//	 * @param poAct
//	 * @return
//	 */
//	private clsPlanFragment convertActToPlanFragment(clsAct poAct) {
//		clsPlanFragment oRetVal = null;
//
//		String oActContent = poAct.getMoContent();
//		String oNewActionCommand = getActionStringFromContent(oActContent);
//
//		poAct.m_strAction = oNewActionCommand;
//
//		oRetVal = new clsPlanFragment(poAct, new clsImage(), new clsImage());
//
//		return oRetVal;
//	}

//	/**
//	 * Planning, if no object is given
//	 * 
//	 * @since 26.09.2011 14:20:06
//	 * 
//	 * @param poGoal
//	 * @return
//	 */
//	private ArrayList<clsWordPresentationMesh> planFromNoObject(clsWordPresentationMesh poGoal) {
//
//		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
//		oRetVal.addAll(planSearch());
//
//		return oRetVal;
//	}

//	/**
//	 * Extract clsImage from a normal Perceived Image (wendt)
//	 * 
//	 * @since 26.09.2011 16:35:19
//	 * 
//	 * @param poEnvironmentalPerception
//	 * @return
//	 */
//	private ArrayList<clsImage> preparePerception(clsWordPresentationMesh poEnvironmentalPerception) {
//		ArrayList<clsImage> oRetVal = new ArrayList<clsImage>();
//
//		// get current environmental situation from moSContainer -> create an
//		// image
//		// this is necessary since the data is all added to one data-string
//		ArrayList<clsImage> currentImageAllObjects = PlanningWizard.getCurrentEnvironmentalImage(poEnvironmentalPerception
//		    .getMoInternalAssociatedContent());
//		oRetVal.addAll(currentImageAllObjects);
//
//		return oRetVal;
//	}

//	/**
//	 * Remove image parts, which are not active for this goal
//	 * 
//	 * @since 26.09.2011 14:54:04
//	 * 
//	 * @param poGoal
//	 * @param poCurrentImageAllObjects
//	 * @return
//	 */
//	private ArrayList<clsImage> filterForDecisionMakingGoal(clsWordPresentationMeshGoal poGoal, ArrayList<clsImage> poCurrentImageAllObjects) {
//		ArrayList<clsImage> currentImageSorted = new ArrayList<clsImage>();
//
//		for (clsImage oImage : poCurrentImageAllObjects) {
//			if (poGoal.getGoalObject().getMoContent().equals(oImage.m_eObj.toString())) {
//				currentImageSorted.add(oImage);
//			}
//		}
//		return currentImageSorted;
//	}

//	/**
//	 * Generate a search plan (wendt)
//	 * 
//	 * @since 26.09.2011 14:27:40
//	 * 
//	 * @return
//	 */
//	private ArrayList<clsWordPresentationMesh> planSearch() {
//		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
//
//		ArrayList<clsPlanFragment> tempPlanningNodes = new ArrayList<clsPlanFragment>();
//		tempPlanningNodes.add(new clsPlanFragment(new clsAct(eAction.SEARCH1), new clsImage(eEntity.NONE), new clsImage(eDirection.CENTER, eDistance.NEAR,
//		    eEntity.CAKE)));
//		oRetVal.addAll(copyPlanFragments(tempPlanningNodes));
//
//		return oRetVal;
//	}

//	/**
//	 * DOCUMENT (wendt) - insert description
//	 * 
//	 * @since 16.09.2011 08:50:51
//	 * 
//	 * @param poContent
//	 * @return
//	 */
//	private String getActionStringFromContent(String poContent) {
//		String oRetVal = "";
//		// Input Structure
//		// "FORWARD|PRECONDITION|LOCATION:MANIPULATEABLE|ENTITY:ENTITY||ACTION|ACTION:MOVE_FORWARD||CONSEQUENCE|LOCATION:EATABLE|ENTITY:ENTITY|";
//
//		// Output structure
//		// MOVE_FORWARD
//
//		String[] oParts = poContent.split("\\|");
//		for (String oE : oParts) {
//			if (oE.contains("ACTION:")) {
//				String oActionString = "ACTION:";
//				oRetVal = oE.substring(oActionString.length(), oE.length());
//				break;
//			}
//		}
//
//		return oRetVal;
//	}
//
//	public ArrayList<clsWordPresentationMesh> copyPlanFragments(ArrayList<clsPlanFragment> myPlans) {
//		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
//		ArrayList<clsPlanFragment> moPlans = new ArrayList<clsPlanFragment>();
//
//		try {
//			for (clsPlanFragment oP : myPlans) {
//				if (oP.m_preconditionImage!=null && oP.m_preconditionImage.m_eObj!=eEntity.NONE) {
//					if (oP.m_preconditionImage.m_eDist==null) {
//						throw new Exception("Error: " + oP.m_preconditionImage.toString() + "is has a NULL component");
//					}
//				} 
//				
//				if (oP.m_effectImage!=null) {
//					if (oP.m_effectImage.m_eDist==null && oP.m_preconditionImage.m_eObj!=eEntity.NONE) {
//						throw new Exception("Error: " + oP.m_effectImage.toString() + "is has a NULL component");
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		for (clsPlanFragment plFr : myPlans) {
//			moPlans.add(plFr);
//		}
//
//		// Convert the containers into WPM
//		for (clsPlanFragment oPF : moPlans) {
//			clsWordPresentationMesh oAction = clsActionTools.createAction(eAction.valueOf(oPF.m_act.m_strAction));
//			
//			clsWordPresentationMesh oGoalObject = clsDataStructureGenerator.generateWPM(new clsPair<eContentType, Object>(eContentType.ENTITY,oPF.m_effectImage.m_eObj.toString()), new ArrayList<clsAssociation>());
//			try {
//				clsMeshTools.setUniquePredicateWP(oGoalObject, eContentType.DISTANCEASSOCIATION, ePredicate.HASDISTANCE, eContentType.DISTANCE, oPF.m_effectImage.m_eDist.toString(), false);
//			} catch (Exception e) {
//				log.debug(oPF.m_effectImage.toString());
//				e.printStackTrace();
//			}
//			
//			clsMeshTools.setUniquePredicateWP(oGoalObject, eContentType.DISTANCEASSOCIATION, ePredicate.HASPOSITION, eContentType.POSITION, oPF.m_effectImage.m_eDir.toString(), false);
//			
//			clsMeshTools.createAssociationSecondary(oAction, 2, oGoalObject, 2, 1.0, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASASSOCIATION, false);
//			oRetVal.add(oAction);
//		}
//
//		// add perception
//		return oRetVal;
//	}
//
//	public ArrayList<clsWordPresentationMesh> copyPlanFragments_AP(ArrayList<clsPlanFragment> myPlans) {
//		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
//		ArrayList<clsPlanFragment> moPlans = new ArrayList<clsPlanFragment>();
//
//		for (clsPlanFragment plFr : myPlans) {
//			moPlans.add(plFr);
//		}
//
//		// Convert the containers into WPM
//		for (clsPlanFragment oPF : moPlans) {
//			clsWordPresentationMesh oAction = clsDataStructureGenerator.generateWPM(new clsPair<eContentType, Object>(eContentType.ACTION,
//			    oPF.m_act.m_strAction), new ArrayList<clsAssociation>());
//			oRetVal.add(oAction);
//		}
//
//		// add perception
//		return oRetVal;
//	}
//
//	/***********************************************************************************************
//	 * END class specific methods (e.g. planning methods)
//	 **********************************************************************************************/
//
//	/***********************************************************************************************
//	 * BEGINN inspector specific functions
//	 **********************************************************************************************/

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
		double rNUM_IMAGINARY_ACTIONS = moGoalList_OUT.size();
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

    /* (non-Javadoc)
    *
    * @since 14.05.2014 10:33:20
    * 
    * @see inspector.interfaces.itfInspectorTimeChartBase#getProperties()
    */
   @Override
   public clsTimeChartPropeties getProperties() {
       return new clsTimeChartPropeties(true);
   }

	/***********************************************************************************************
	 * END inspector specific functions
	 **********************************************************************************************/

}
