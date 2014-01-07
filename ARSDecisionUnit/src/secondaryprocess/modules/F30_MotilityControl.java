/**
 * E30_MotilityControl.java: DecisionUnits - pa.modules
 * 
 * @author brandstaetter
 * 11.08.2009, 14:58:20
 */
package secondaryprocess.modules;

import inspector.interfaces.itfInspectorGenericActivityTimeChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import memorymgmt.enums.eActionType;
import memorymgmt.interfaces.itfModuleMemoryAccess;
import memorymgmt.shorttermmemory.clsShortTermMemory;
import memorymgmt.storage.DT3_PsychicEnergyStorage;
import modules.interfaces.I2_5_receive;
import modules.interfaces.I2_5_send;
import modules.interfaces.I6_11_receive;
import modules.interfaces.I6_14_receive;
import modules.interfaces.eInterfaces;
import secondaryprocess.functionality.PlanningFunctionality;
import base.datatypes.clsConcept.clsEntity;
import base.datatypes.clsEmotion;
import base.datatypes.clsWordPresentationMesh;
import base.modules.clsModuleBase;
import base.modules.clsModuleBaseKB;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;
import config.clsProperties;
import du.enums.eInternalActionIntensity;
import du.itf.actions.clsActionInvite;
import du.itf.actions.clsActionShare;
import du.itf.actions.clsInternalActionCommand;
import du.itf.actions.clsInternalActionSweat;
import du.itf.actions.itfInternalActionProcessor;


/**
 * Pass ActionCommands to F31_NeuroDeSymbolizationActionCommands. When no action command are present, do some seeking. Motoric movement can be controlled by psychic functions up to some extend. Drive inhibitiona mechanism necessary for the defense mechanismsleads to the possibility to perform behavior in rehearsal. Module {E30} uses this concept to evaluate how the submitted action plan can be realized best. The resulting action commands are forwarded to {E31}.
 * 
 * @author brandstaetter
 * 11.08.2012, 14:58:20
 * 
 */
public class F30_MotilityControl extends clsModuleBaseKB implements I6_11_receive, I2_5_send, I6_14_receive, itfInspectorGenericActivityTimeChart {
	public static final String P_MODULENUMBER = "30";
	
	private clsWordPresentationMesh moActionCommand_Input;
	private clsWordPresentationMesh moEnvironmentalPerception_IN; // AP added environmental perception
	private ArrayList<clsWordPresentationMesh> moActionCommands_Output;
	private ArrayList<clsEmotion> moEmotions_Input;
	private clsEntity moEntity;
	//private int mnCounter, lastTurnDirection, mnTurns;
	 private ArrayList<clsInternalActionCommand> moInternalActions = new ArrayList<clsInternalActionCommand>();
	private clsShortTermMemory moShortTermMemory;
	
	private clsShortTermMemory moEnvironmentalImageStorage;
	
	private final  DT3_PsychicEnergyStorage moPsychicEnergyStorage;
	private clsWordPresentationMesh moWordingToContext;
	
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	
	/**
	 * Constructor of the NeuroDeSymbolization
	 * 
	 * @author brandstaetter
	 * 03.03.2011, 17:00:42
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F30_MotilityControl(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, itfModuleMemoryAccess poLongTermMemory, clsShortTermMemory poShortTermMemory, clsShortTermMemory poTempLocalizationStorage,
			DT3_PsychicEnergyStorage poPsychicEnergyStorage) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poLongTermMemory);
		
        this.moPsychicEnergyStorage = poPsychicEnergyStorage;
        this.moPsychicEnergyStorage.registerModule(mnModuleNumber);
        
		applyProperties(poPrefix, poProp);	
		
		moActionCommands_Output = new ArrayList<clsWordPresentationMesh>(); 
		
        this.moShortTermMemory = poShortTermMemory;
        this.moEnvironmentalImageStorage = poTempLocalizationStorage;
        

	}
	
	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		String[] ignoreList = new String[] {"eDataType:UNDEFINED",
				                            "eDataType:AFFECT",
				                            "eDataType:ASSOCIATIONTEMP"};
		// simple toString output
		text += toText.valueToTEXT("moActionCommands_Input", moActionCommand_Input);
		// complex clsDumper output
		//text += "moActionCommands_Input:" + clsDumper.dump(moActionCommands_Input,3,0,ignoreList) + "\n";		

		if(moEnvironmentalPerception_IN == null)
			text += toText.valueToTEXT("moEnvironmentalPerception_IN", "null");
		else {
			// simple toString output
			text += toText.valueToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_IN.toString());
			// complex clsDumper output
			//text += "moEnvironmentalPerception_IN:" + clsDumper.dump(moEnvironmentalPerception_IN,3,0,ignoreList) + "\n";
		}
		// simple toString output
		text += toText.listToTEXT("moActionCommands_Output", moActionCommands_Output);
		 text += toText.listToTEXT("moEmotions_Input", moEmotions_Input);
		// complex clsDumper output
		//text += "moActionCommands_Output:" + clsDumper.dump(moActionCommands_Output,3,0,ignoreList) + "\n";
		return text;
	}	
	

	
	 /**
     * DOCUMENT (hinterleitner) - insert description
     *
     * @since 31.10.2013 12:54:43
     *
     * @param poInternalActionContainer
     */
    public void getBodilyReactions( itfInternalActionProcessor poInternalActionContainer) {
        
        for( clsInternalActionCommand oCmd : moInternalActions ) {
            poInternalActionContainer.call(oCmd);
        }
    }


	/**
	 * @author brandstaetter
	 * 02.09.2010, 20:10:34
	 * 
	 * @return the moActionCommands_Output
	 */
	public ArrayList<clsWordPresentationMesh> getActionCommands_Output() {
		return moActionCommands_Output;
	}
	
	/**
	 * @author brandstaetter
	 * 02.09.2010, 20:10:34
	 * 
	 * @return the moActionCommands_Input
	 */
	public clsWordPresentationMesh getActionCommands_Input() {
		return moActionCommand_Input;
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
	 * @author brandstaetter
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
	 * @author brandstaetter
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
	 * @author brandstaetter
	 * 11.08.2009, 14:58:46
	 * 
	 * @see pa.interfaces.I7_4#receive_I7_4(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I6_11(clsWordPresentationMesh poActionCommands, clsWordPresentationMesh moWordingToContext2) {
		moActionCommand_Input = poActionCommands; 
		moWordingToContext = moWordingToContext2;
	}

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 11.08.2009, 16:16:50
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
	    
	    
	    
	    //Get the action if it is not an internal action
	    moActionCommands_Output = new ArrayList<clsWordPresentationMesh>();
	    clsWordPresentationMesh externalActionCommand = PlanningFunctionality.getActionOfType(moActionCommand_Input, eActionType.SINGLE_EXTERNAL);
	   
	    //SPEECH from F66
 
        
        if ((moActionCommand_Input.toString().contains("SPEAK") && ((moWordingToContext.toString().contains("YES")))) || ( moActionCommand_Input.toString().contains("GOTO")) && ((moWordingToContext.toString().contains("YES")))){
            triggerSpeechYes(moEmotions_Input);
              
            }  
        
        
        if ((moActionCommand_Input.toString().contains("SPEAK") && ((moWordingToContext.toString().contains("SHARE")))) || ( moActionCommand_Input.toString().contains("GOTO")) && ((moWordingToContext.toString().contains("SHARE")))){
            triggerSpeechShare(moEmotions_Input);
              
            } 
	   
        /*if (moActionCommand_Input.toString().contains("FOCUS") && moWordingToContext.toString().contains("INVITED")){
            triggerSpeechInvite(moEmotions_Input);
              
            } */
	    
	    if (externalActionCommand.isNullObject()==false) {
	        moActionCommands_Output.add(externalActionCommand);    
	    }
	   

	
	    
	    //moActionCommands_Output.add(moActionCommand_Input);
		
//	 // AW HACK test, in order to be able to use both WP and plan fragements at the same time
//        boolean bPlanFragement = true;
//
//        // normal use of actions -> without AW hack
//        if (bPlanFragement == true) {
//            // run over all actions and sort out the most appropriate ones
//            ArrayList<clsWordPresentationMesh> sortedActions = new ArrayList<clsWordPresentationMesh>();
//            int iCursorPos = 0;
//
//            int iIndexOfEat = -1;
//            int iIndexOfMoveForward = -1;
//            int iIndexOfFlee = -1;
//            int iIndexOfOverrideAction = -1; // stores the index of an action which should be used over all others because of interface
//                                             // I.6_2
//            
//            clsWordPresentationMesh oC = moActionCommands_Input;
//                String strAction = oC.getMoContent();
//
//                if (strAction.equalsIgnoreCase("EAT"))
//                        iIndexOfEat = iCursorPos;
//                if (strAction.equalsIgnoreCase("MOVE_FORWARD"))
//                        iIndexOfMoveForward = iCursorPos;
//                if (strAction.equalsIgnoreCase("FLEE"))
//                        iIndexOfFlee = iCursorPos;
//
//                iCursorPos++;
//            
//
//            // order of actions, use eat first, then move forward than all other actions
//            // flee is used as override action
//            if (iIndexOfOverrideAction > 0)
//                sortedActions.add(moActionCommands_Input.get(iIndexOfOverrideAction));
//            else if (iIndexOfEat > 0) // only use eat
//                sortedActions.add(moActionCommands_Input.get(iIndexOfEat));
//            else if (iIndexOfMoveForward > 0) // only use move forward
//                sortedActions.add(moActionCommands_Input.get(iIndexOfMoveForward));
//            else
//                // use all other actions
//                sortedActions = moActionCommands_Input;
//
//            // if agent feels anxiety -> flee and discard all other actions
//            //TODO CB: How shall the feelings be treated here
//            //Apply some effect of Feelings
////          if (moAnxiety_Input.size() > 0) {
////              for (Object myPred : moAnxiety_Input) {
//  //
////                  if (myPred instanceof clsSecondaryDataStructureContainer) {
////                      String strVal = myPred.toString();
//  //
////                      if (strVal.contains("ANXIETY")) {
////                          iIndexOfOverrideAction = iIndexOfFlee;
////                      }
////                  }
////              }
////          }
//          
//
//            ArrayList<clsWordPresentationMesh> moActionCommandsTemp = new ArrayList<clsWordPresentationMesh>();
//            for (clsWordPresentationMesh oC : sortedActions) {
//
//                // convert actions back to wordpresentation -> only wordpresentations are allowed to be handled over to motility control
//                // if (oC instanceof clsPlanFragment) {
//                // clsPlanFragment plFr = (clsPlanFragment) oC;
//                String strAction = oC.getMoContent(); // plFr.m_act.m_strAction;
//                clsWordPresentationMesh myWP = oC;//new clsWordPresentationMesh(new clsTriple<Integer, eDataType, String>(1, eDataType.WPM, strAction), oC.getMoAssociatedContent(), strAction);
//
//                moActionCommandsTemp.add(myWP);
//                // }
//            }

            // No nulls are allowed
//            moActionCommands_Output = new ArrayList<clsWordPresentationMesh>();
//
//            // Get the first command
//            if (moActionCommandsTemp.isEmpty() == false) {
//                moActionCommands_Output.add(moActionCommandsTemp.get(0));
//            }
//        } else {
//            moActionCommands_Output = moActionCommands_Input;
//        }		
	    
	    
//	    this.moEnvironmentalPerception_IN = this.moEnvironmentalImageStorage.findCurrentSingleMemory();
//		
//		this.moActionCommands_Output = this.moActionCommands_Input;
		//if(moActionCommands_Input.size() >= 1) {
		//	moActionCommands_Output = getWordPresentations(moActionCommands_Input);
		//}
		//else{
			//when there are no actions, we generate a random seeking sequence 
			//moActionCommands_Output = GenerateSeekingSequence();
			
			//when there are no actions, we deliver the ActionCommand SEARCH1
			//moActionCommands_Output = new ArrayList<clsWordPresentation>();
			//moActionCommands_Output.add(new clsWordPresentation(new clsTriple<Integer, eDataType, String>(-1,eDataType.WP,"SEARCH1"), "SEARCH1"));
			
			//when there are no actions, we do nothing
		//}
	    
	    
        log.info("Predicted Speech Output based on F66: {}", moWordingToContext );
        //log.info("Sprachausgabe: {}", moWordingToContext);
        log.info(moActionCommands_Output.toString());
        log.info("Action: {}", moActionCommand_Input);
    }
	
	
	
	 /**
     * DOCUMENT - create initial wording based on input
     * 
     * @author hinterleitner
     * @param moEmotions_Input2
     * @since 14.12.2013 16:47:31
     * 
     */
    private void triggerSpeechYes(ArrayList<clsEmotion> moEmotions_Input2) {
        clsInternalActionSweat test = new clsInternalActionSweat(eInternalActionIntensity.HEAVY);

        // Speech  - Yes
        moInternalActions.add(test);

    }

    private void triggerSpeechShare(ArrayList<clsEmotion> moEmotions_Input2) {
        clsActionShare testnew1 = new clsActionShare(eInternalActionIntensity.HEAVY);

        // Speech  - Share
        moInternalActions.add(testnew1);
    }

    
    private void triggerSpeechInvite(ArrayList<clsEmotion> moEmotions_Input2) {
        clsActionInvite testnew1 = new clsActionInvite(eInternalActionIntensity.HEAVY);

        // Speech  - Share
        moInternalActions.add(testnew1);
    }
  

//    // AW 20110629 New function, which converts clsSecondaryDataStructureContainer to clsWordpresentation
//    /**
//     * convert the act to a word presentation, temp function!!! DOCUMENT (wendt) - insert description
//     * 
//     * @since 02.08.2011 09:50:37
//     * 
//     * @param poInput
//     * @return
//     */
//    private ArrayList<clsWordPresentation> getWordPresentations(ArrayList<clsWordPresentationMesh> poInput) {
//        ArrayList<clsWordPresentation> oRetVal = new ArrayList<clsWordPresentation>();
//
//        for (clsWordPresentationMesh oCont : poInput) {
//            clsWordPresentation oWP = clsDataStructureGenerator.generateWP(new clsPair<eContentType, Object>(oCont.getMoContentType(), oCont
//                    .getMoContent()));
//
//            oRetVal.add(oWP);
//        }
//
//        return oRetVal;
//    }
	
//	/**
//	 * This Method generates a simple random seeking sequence
//	 * @since 07.09.2011 14:02:14
//	 * @return
//	 */
	
	/*  // not need any more, CB 2011-11-14
	private ArrayList<clsWordPresentation> GenerateSeekingSequence(){
		double rRand1 = Math.random();
		double rRand2 = Math.random();
		ArrayList<clsWordPresentation> oActionCommands_Seeking = new ArrayList<clsWordPresentation>();
		oActionCommands_Seeking.clear();
		
		if (mnCounter == 5) {
		  if(rRand1<0.25) {
			  if(lastTurnDirection == 1) oActionCommands_Seeking.add(new clsWordPresentation(new clsTriple<Integer, eDataType, String>(-1,eDataType.WP,"Test"), "TURN_LEFT"));
			  else oActionCommands_Seeking.add(new clsWordPresentation(new clsTriple<Integer, eDataType, String>(-1,eDataType.WP,"Test"), "TURN_RIGHT"));
			  if(rRand2>Math.pow(0.999,mnTurns)) { // change turning direction
				lastTurnDirection=1-lastTurnDirection;
				mnTurns=0;
			  }
			  mnTurns++;
		  }
		  else oActionCommands_Seeking.add(new clsWordPresentation(new clsTriple<Integer, eDataType, String>(-1,eDataType.WP,"Test"), "MOVE_FORWARD"));
	      mnCounter = 0;
		}
		mnCounter++;
		
		return oActionCommands_Seeking;
	}
	*/

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 11.08.2009, 16:16:50
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_5(moActionCommands_Output, moWordingToContext);
		
	}

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 18.05.2010, 17:59:05
	 * 
	 * @see pa.interfaces.send.I8_1_send#send_I8_1(java.util.ArrayList)
	 */
	@Override
	public void send_I2_5(ArrayList<clsWordPresentationMesh> poActionCommands, clsWordPresentationMesh moWordingToContext) {
		((I2_5_receive)moModuleList.get(31)).receive_I2_5(poActionCommands, moWordingToContext);
		//((I2_5_receive)moModuleList.get(52)).receive_I2_5(poActionCommands);
		
		putInterfaceData(I2_5_send.class, poActionCommands, moWordingToContext);
		
	}

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 12.07.2010, 10:47:57
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 12.07.2010, 10:47:57
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		throw new java.lang.NoSuchMethodError();
	}
	
	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 29.11.2010, 11:00:00
	 * 
	 * @see pa.interfaces.itfTimeChartInformationContainer#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oRetVal = new ArrayList<Double>();
		double rNUM_INPUT_ACTIONS = 1;
		oRetVal.add(rNUM_INPUT_ACTIONS); 
		return oRetVal; 
	}
	
	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 29.11.2010, 11:00:00
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartAxis()
	 */
	@Override
	public String getTimeChartAxis() {
		return "Number of Incoming Action Commands";
	}

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 29.11.2010, 11:00:00
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		return "Number of Incoming Action Commands";
	}	
	
	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 29.11.2010, 11:00:00
	 * 
	 * @see pa.interfaces.itfTimeChartInformationContainer#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oCaptions = new ArrayList<String>();
		oCaptions.add("NUM_INPUT_ACTIONS");
		return oCaptions;
	}	

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 03.03.2011, 17:00:47
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}
	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "Motoric movement can be controlled by psychic functions up to some extend. Drive inhibitiona mechanism necessary for the defense mechanismsleads to the possibility to perform behavior in rehearsal. Module {E30} uses this concept to evaluate how the submitted action plan can be realized best. The resulting action commands are forwarded to {E31}. ";
	}

	  /* (non-Javadoc)
    *
    * @since Jul 5, 2013 2:44:20 PM
    * 
    * @see pa._v38.interfaces.modules.I6_14_receive#receive_I6_14(java.util.ArrayList)
    */
   @SuppressWarnings("unchecked")
   @Override
   public void receive_I6_14(ArrayList<clsEmotion> poEmotions_Input) {
       // TODO (schaat) - Auto-generated method stub
       moEmotions_Input =  (ArrayList<clsEmotion>) deepCopy(poEmotions_Input);
   }
   

    }		

