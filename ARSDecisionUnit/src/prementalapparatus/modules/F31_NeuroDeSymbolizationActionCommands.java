/**
 * E31_NeuroDeSymbolization.java: DecisionUnits - pa.modules
 * 
 * @author brandstaetter
 * 11.08.2009, 14:59:58
 */
package prementalapparatus.modules;

import inspector.interfaces.clsTimeChartPropeties;
import inspector.interfaces.itfInspectorGenericActivityTimeChart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.SortedMap;

import properties.clsProperties;

import memorymgmt.enums.eAction;
import memorymgmt.enums.eActionType;
import modules.interfaces.I1_5_receive;
import modules.interfaces.I1_5_send;
import modules.interfaces.I2_5_receive;
import modules.interfaces.eInterfaces;
import base.datatypes.clsWordPresentationMesh;
import base.modules.clsModuleBase;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;
import du.enums.eActionMoveDirection;
import du.enums.eActionSleepIntensity;
import du.enums.eActionTurnDirection;
import du.enums.eInternalActionIntensity;
import du.itf.actions.clsActionBeat;
import du.itf.actions.clsActionCommand;
import du.itf.actions.clsActionDivide;
import du.itf.actions.clsActionDrop;
import du.itf.actions.clsActionEat;
import du.itf.actions.clsActionExcrement;
import du.itf.actions.clsActionMove;
import du.itf.actions.clsActionPickUp;
import du.itf.actions.clsActionSequence;
import du.itf.actions.clsActionSequenceFactory;
import du.itf.actions.clsActionSpeech;
import du.itf.actions.clsActionTurnVision;
//import du.itf.actions.clsActionSequenceFactory;
import du.itf.actions.clsActionSleep;
import du.itf.actions.clsActionTurn;
import secondaryprocess.datamanipulation.clsActionTools;
import secondaryprocess.datamanipulation.clsMeshTools;

/**
 * Conversion of neuro-symbols into raw data. F31_NeuroDeSymbolizationActionCommands should be just an empty function which forwards data to F32.
 * 
 * @author brandstaetter
 * 11.08.2012, 14:59:58
 * 
 */
public class F31_NeuroDeSymbolizationActionCommands extends clsModuleBase 
			implements I2_5_receive, I1_5_send, itfInspectorGenericActivityTimeChart  {
	public static final String P_MODULENUMBER = "31";
	
	private ArrayList<clsActionCommand> moActionCommandList_Output;
	private ArrayList<clsWordPresentationMesh> moActionCommands_Input;
	private int mnCounter, moActionBlockingTime;
	private clsWordPresentationMesh lastAction; 
	private clsWordPresentationMesh lastRealAction;
	private clsWordPresentationMesh realAction;
	private ArrayList<String> inputActionHistory;
	private ArrayList<String> realActionHistory;
	private static final boolean bUSEUNREAL = false;
	private eInternalActionIntensity moAbstractSpeech;
	private clsWordPresentationMesh moWordingToContext;
	private int mnTestCounter =0;
	
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	
	/**
	 * Constructor of NeuroDeSymbolization
	 * 
	 * @author brandstaetter
	 * 03.03.2011, 17:01:32
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F31_NeuroDeSymbolizationActionCommands(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);		
		
		mnCounter = 0;
		moActionBlockingTime = 0;
		lastAction = clsMeshTools.getNullObjectWPM();
		lastRealAction = clsMeshTools.getNullObjectWPM();
		realAction = clsMeshTools.getNullObjectWPM();
		inputActionHistory = new ArrayList<String>(); 
		realActionHistory = new ArrayList<String>(); 
		moActionCommandList_Output = new ArrayList<clsActionCommand>();
		
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
		ArrayList<String> inputActionHistoryReverse = new ArrayList<String>(inputActionHistory);
		Collections.reverse(inputActionHistoryReverse);
		ArrayList<String> realActionHistoryReverse = new ArrayList<String>(realActionHistory);		
		Collections.reverse(realActionHistoryReverse);

		String text ="";
		text += toText.listToTEXT("moActionCommands_Input", moActionCommands_Input);
		text += toText.valueToTEXT("input Action History", compactActionHistory(inputActionHistoryReverse));		
		text += toText.valueToTEXT("real Action History", compactActionHistory(realActionHistoryReverse));		
		//text += toText.valueToTEXT("lastAction", lastAction);
		//text += toText.valueToTEXT("realAction", realAction);
		//text += toText.valueToTEXT("lastRealAction", lastRealAction);
		//text += toText.valueToTEXT("mnCounter", mnCounter);
		//text += toText.valueToTEXT("moActionBlockingTime", moActionBlockingTime);
		text += toText.listToTEXT("moActionCommandList_Output", moActionCommandList_Output);
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
	 * @author brandstaetter
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.BODY;
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
		mnPsychicInstances = ePsychicInstances.BODY;
	}

	

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 11.08.2009, 16:16:54
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
	    moActionCommandList_Output.clear();
		


		
        if( moActionCommands_Input.size() > 0 ) {
			for(clsWordPresentationMesh oActionWPM : moActionCommands_Input) {
			    
				if (oActionWPM == null) 
					return;
				
				String oAction = oActionWPM.getContent();
				inputActionHistory.add(oAction.toString());
				
				//--- AW: FIXME HACK IN ORDER TO BE ABLE TO USED COMPOSED ACTIONS ---//
				
				if (lastAction.isNullObject()==false &&
						clsActionTools.getActionType(lastAction).equals(eActionType.COMPOSED_EXTERNAL)==true && 
						clsActionTools.getActionType(oActionWPM).equals(eActionType.SINGLE_INTERNAL)==true &&
						clsActionTools.getAction(oActionWPM).equals(eAction.FOCUS_ON)==false &&
						clsActionTools.getAction(oActionWPM).equals(eAction.SEND_TO_PHANTASY)==false) {
					oAction=lastAction.getContent();
				}
				
				
                if(oAction.equals(eAction.FOCUS_ON.toString()) ||
                        oAction.equals(eAction.NONE.toString()) ||
                        oAction.equals(eAction.SEND_TO_PHANTASY.toString()) ||
                        oAction.equals(eAction.FOCUS_MOVE_FORWARD.toString()) ||
                        oAction.equals(eAction.FOCUS_SEARCH1.toString()) ||
                        oAction.equals(eAction.FOCUS_TURN_LEFT.toString()) ||
                        oAction.equals(eAction.FOCUS_TURN_RIGHT.toString()) ||
                        oAction.equals(eAction.PERFORM_BASIC_ACT_ANALYSIS.toString())) {
                	realAction=lastRealAction;
                 } else {
     			    realAction=oActionWPM;//oWP.getMoContent();
     			}
                
                realActionHistory.add(realAction.getContent().toString());
                
				// mnCounter contains information for how much turns the current action is active
				if(realAction.getContent().equals(lastRealAction.getContent())) { 
					if(realAction.getContent().equals(oActionWPM.getContent()))
					  mnCounter++; 
				}
				else  {
					mnCounter = 0;
				}
			    
				log.debug(
						 "LastAction: " + lastAction.getContent() + ", " + 
						 "LastRealAction: " + lastRealAction.getContent() + ", " + 
				         "ThisAction: " + oActionWPM.getContent() + ", " + 
				         "UsedAction: " + oAction.toString() + ", " +
				         "mnCounter: " + mnCounter);
			

				// moActionBlockingTime contains number of remaining turns all new actions will be blocked 
				// currently only the action "FLEE" sets the moActionBlockingTime
				if(moActionBlockingTime>0) {
					moActionBlockingTime--;
					if(moActionBlockingTime<=0) {
						mnCounter = 0;
					}
				    return;
				}
                
				// action "FLEE" has a special position, it is the most important action
				if (oAction.equals("FLEE")) {
					if (mnCounter%90==0) {
						moActionCommandList_Output.add( clsActionSequenceFactory.getFleeSequence3(180.0f, 60) );
						mnCounter = 0;
						moActionBlockingTime=90;
						// old, simple flee sequence CB 2011-11-14
						//moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_RIGHT, 20.0));
					}
				}
				else if(oAction.equals("TURN_VISION")){
					//just for Test
					java.util.Random random = new java.util.Random();
					int rnd = random.nextInt(61) - 30;
					if( rnd <= 0)
						moActionCommandList_Output.add( new clsActionTurnVision(eActionTurnDirection.TURN_LEFT, rnd*-1) );
					else
						moActionCommandList_Output.add( new clsActionTurnVision(eActionTurnDirection.TURN_RIGHT, rnd) );
						
				}else if(oAction.equals("MOVE_FORWARD")){
					moActionCommandList_Output.add( new clsActionMove(eActionMoveDirection.MOVE_FORWARD,1.0) );
				} else if(oAction.equals("MOVE_FORWARD_SLOW")){
					moActionCommandList_Output.add( new clsActionMove(eActionMoveDirection.MOVE_FORWARD,0.2) );
				} else if(oAction.equals("STOP")){
					moActionCommandList_Output.add( new clsActionMove(eActionMoveDirection.MOVE_FORWARD,0) );
				} else if(oAction.equals("MOVE_BACKWARD")){
					moActionCommandList_Output.add( new clsActionMove(eActionMoveDirection.MOVE_BACKWARD,1.0) );
				} else if(oAction.equals("TURN_LEFT")){
					moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_LEFT, 5));
				} else if(oAction.equals("TURN_LEFT45")){
					moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_LEFT, 45.0));
				} else if(oAction.equals("TURN_LEFT90")){
					moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_LEFT, 90.0));
				} else if(oAction.equals("TURN_LEFT180")){
					moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_LEFT, 180.0));
				} else if(oAction.equals("TURN_RIGHT")){
					moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_RIGHT, 5));
				} else if(oAction.equals("TURN_RIGHT45")){
					moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_RIGHT, 45.0));
				} else if(oAction.equals("TURN_RIGHT90")){
					moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_RIGHT, 90.0));
				} else if(oAction.equals("TURN_RIGHT180")){
					moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_RIGHT, 180.0));
				} else if(oAction.equals("LOOK_AROUND")){
					moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_RIGHT, 360.0));
				} else if(oAction.equals("STRAFE_LEFT")){
					if (mnCounter%35==0) {
						moActionCommandList_Output.add( clsActionSequenceFactory.getStrafeLeftSequence(1) );
						mnCounter = 0;
					} 					
				} else if(oAction.equals("STRAFE_RIGHT")){
					if (mnCounter%35==0) {
						moActionCommandList_Output.add( clsActionSequenceFactory.getStrafeRightSequence(1) );
						mnCounter = 0;
					} 					
				} else if(oAction.equals("EAT")) {
					moActionCommandList_Output.add( new clsActionEat(1.0) );
				//} else if(oAction.equals("BITE")) {
				//		moActionCommandList_Output.add( new clsActionEat() );
				} else if (oAction.equals("BEAT")) {
				    moActionCommandList_Output.add(new clsActionBeat(1.0));
				} else if (oAction.equals("DIVIDE")) {
				    moActionCommandList_Output.add(new clsActionDivide(0.5));
				} else if (oAction.equals("SLEEP")) {
					moActionCommandList_Output.add( new clsActionSleep(eActionSleepIntensity.DEEP) );
				} else if (oAction.equals("RELAX")) {
					moActionCommandList_Output.add( new clsActionSleep(eActionSleepIntensity.DEEP) );	
				} else if (oAction.equals("DEPOSIT")) {
					moActionCommandList_Output.add( new clsActionExcrement(1) );
				} else if (oAction.equals("REPRESS")) {
					//moActionCommandList_Output.add( new clsActionExcrement(1) );
					throw new UnknownError("Action " + oAction + " should not occure in F31!");
				}
//TD 2011/04/23: commented the actions PICKUP, DROP, and DANCE. currently, they can never happen - no rules are defined
/*				else if(oAction.equals("PICK_UP")) {
					moActionCommandList_Output.add( new clsActionPickUp() );
				}
				else if(oAction.equals("DROP")) {
					moActionCommandList_Output.add( new clsActionDrop() );
				}
					else if(oAction.equals("DANCE_1")) {
						moActionCommandList_Output.add( clsActionSequenceFactory.getWalzSequence(1, 2) );
				}
				
*/				
				else if (oAction.equals("SEARCH1")) {
					//FIXME CB: This is a special hack, in order to extract the single actions from the search command, in order to control the avatar in unreal tournament
					if (bUSEUNREAL==true) {
						//if (mnCounter%75==0) {
						if (mnCounter%20==0) {
							clsActionSequence seq = clsActionSequenceFactory.getSeekingSequence(1.0f, 2);
							int numRounds = 181; //Maximum number of rounds???
							while(!seq.isComplete(numRounds)){
								numRounds--;
							}
							for(int i=0; i<numRounds; i++){
								ArrayList<clsActionCommand> commandList = seq.getCommands(i);
								while(!commandList.isEmpty()){
									moActionCommandList_Output.add(commandList.remove(0));
								}
							}
							mnCounter = 0;
						} 
					} else {
						if (mnCounter%35==0) {
							moActionCommandList_Output.add( clsActionSequenceFactory.getSeekingSequence(1.0f, 2) );
							mnCounter = 0;
						} 
					}
				} else if (oAction.equals(eAction.FOCUS_ON.toString())) {
				    //Do nothing
				} else if (oAction.equals(eAction.GOTO.toString())) {
                    //Do nothing
				} else if (oAction.equals(eAction.NONE.toString())) {
					//Do nothing
				} else if (oAction.equals(eAction.SEND_TO_PHANTASY.toString())) {
					//Do nothing
				} else if (oAction.equals(eAction.FOCUS_MOVE_FORWARD.toString())) {
					//Do nothing
				} else if (oAction.equals(eAction.FOCUS_SEARCH1.toString())) {
					//Do nothing
				} else if (oAction.equals(eAction.FOCUS_TURN_LEFT.toString())) {
					//Do nothing
				} else if (oAction.equals(eAction.FOCUS_TURN_RIGHT.toString())) {
					//Do nothing
				} else if (oAction.equals(eAction.PERFORM_BASIC_ACT_ANALYSIS.toString())) {
					
				} else if (oAction.equals(eAction.PICK_UP.toString())) {
					moActionCommandList_Output.add( new clsActionPickUp() );
				} else if (oAction.equals(eAction.SPEAK_EAT.toString())) {
                    moActionCommandList_Output.add( new clsActionSpeech<clsWordPresentationMesh>(moWordingToContext) );
				} else if (oAction.equals(eAction.DROP.toString())) {
				    moActionCommandList_Output.add(new clsActionDrop());
				}
				else {
					throw new UnknownError("Action " + oAction + " not known");
				}
				
				lastRealAction=realAction;
				lastAction=oActionWPM;//oWP.getMoContent();
			}
		} else {
			/*
			if (true) {
				if (mnCounter == 75) {
					moActionCommandList_Output.add( clsActionSequenceFactory.getSeekingSequence(1.0f, 2) );
					mnCounter = 0;
				} 
				mnCounter++;
			}
			*/
		}
		log.info("ActionCommandList: "+moActionCommandList_Output.toString());
		log.info("Action: {}", moWordingToContext);
		
		//log.debug("=== END OF SECONDARY PROCESS ===\n");
	}

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 22.11.2012, 12:06:19
	 * 
	 */
	public String compactActionHistory(ArrayList<String> actionHistory) {
		StringBuilder sb = new StringBuilder();
		String lastLine="first";
		Integer count=0;
		for (String line : actionHistory) {
			if(lastLine=="first") {
              sb.append(line);
			  count=1;
			}
			else {
			  if(lastLine==line) {
			    count++;
			  }
			  else {
			    if(count==1) {
				    sb.append(", ");
			    }
			    else {
				    sb.append(" (");
				    sb.append(count);
				    sb.append("x), ");
			    }
				sb.append(line);
                count=1;
			  }
			}
			lastLine=line;
		}
		return sb.toString();
	}

	
	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 11.08.2009, 16:16:54
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I1_5(moActionCommandList_Output, moWordingToContext);
		
	}

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 18.05.2010, 17:59:41
	 * 
	 * @see pa.interfaces.send.I8_2_send#send_I8_2(java.util.ArrayList)
	 */
	@Override
	public void send_I1_5(ArrayList<clsActionCommand> poActionCommandList, clsWordPresentationMesh moWordingToContext2) {
		((I1_5_receive)moModuleList.get(32)).receive_I1_5(poActionCommandList, moWordingToContext2);
		
		putInterfaceData(I1_5_send.class, poActionCommandList, moWordingToContext2);
	}

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 12.07.2010, 10:48:02
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
	    moActionCommandList_Output.clear();
	    //moActionCommandList_Output.add(new clsActionMove(eActionMoveDirection.MOVE_FORWARD,1.0));
	    

	    /*if(mnTestCounter%2==0){
	        moActionCommandList_Output.add(new clsActionAttackBite(1.0));
	    }
	    else if (mnTestCounter%2 == 1){
	        moActionCommandList_Output.add(new clsActionBeat(1.0));
	    }
	    mnTestCounter++;*/
	    //return;
	    if(testSequence())  return;
	}

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 12.07.2010, 10:48:02
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
	 * 07.11.2010, 11:55:48
	 * 
	 * @see pa.interfaces.itfTimeChartInformationContainer#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oRetVal = new ArrayList<Double>();
		
		double rTURN_RIGHT = 0.0;
		double rTURN_LEFT = 0.0;
		double rMOVE_FORWARD = 0.0;
		double rEAT = 0.0;
		double rSLEEP = 0.0;
		double rEXCREMENT = 0.0;
		double rSEARCH1 = 0.0;
		double rBITE = 0.0;
		double rFOCUS_ON = 0.0;
		double rSEND_TO_PHANTASY = 0.0;
		double rFOCUS_MOVEMENT = 0.0;
		double rFLEE = 0.0;
		double rUNKNOWN = 0.0;
		
		String oCurrentActionCommand = "";
		
		try {
			oCurrentActionCommand = moActionCommands_Input.get(0).getContent();
		} catch (java.lang.IndexOutOfBoundsException e) {
			//do nothing
		}
		
		if (oCurrentActionCommand.equals("TURN_RIGHT")) {
			rTURN_RIGHT = 1.0;
		} else if (oCurrentActionCommand.equals("TURN_LEFT")) {
			rTURN_LEFT = 1.0;
		} else if (oCurrentActionCommand.equals("MOVE_FORWARD")) {
			rMOVE_FORWARD = 1.0;
		} else if (oCurrentActionCommand.equals("EAT")) {
			rEAT = 1.0;
		} else if (oCurrentActionCommand.equals("SLEEP")) {
			rSLEEP = 1.0;
		} else if (oCurrentActionCommand.equals("EXCREMENT")) {		
			rEXCREMENT = 1.0;
		} else if (oCurrentActionCommand.equals("SEARCH1")) {
			rSEARCH1 = 1.0;
		} else if (oCurrentActionCommand.equals("BITE")) {
			rBITE = 1.0;
		} else if (oCurrentActionCommand.equals("FOCUS_ON")) {
			rFOCUS_ON = 1.0;
		} else if (oCurrentActionCommand.equals("SEND_TO_PHANTASY")) {
			rSEND_TO_PHANTASY = 1.0;
		} else if (oCurrentActionCommand.equals("FOCUS_MOVEMENT")) {
			rFOCUS_MOVEMENT = 1.0;
		} else if (oCurrentActionCommand.equals("FLEE")) {
			rFLEE = 1.0;
		} else {
			rUNKNOWN = 1.0;
		}
		
		oRetVal.add(rTURN_RIGHT); 
		oRetVal.add(rTURN_LEFT); 
		oRetVal.add(rMOVE_FORWARD); 
		oRetVal.add(rEAT); 
		oRetVal.add(rSLEEP);
		oRetVal.add(rEXCREMENT);
		oRetVal.add(rSEARCH1);
		oRetVal.add(rBITE);
		oRetVal.add(rFOCUS_ON);
		oRetVal.add(rSEND_TO_PHANTASY);
		oRetVal.add(rFOCUS_MOVEMENT);
		oRetVal.add(rFLEE);
		oRetVal.add(rUNKNOWN);

		return oRetVal; 
	}
	
	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 15.04.2011, 17:41:33
	 * 
	 * @see pa.interfaces.itfTimeChartInformationContainer#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oCaptions = new ArrayList<String>();
		
		oCaptions.add("TURN_RIGHT");
		oCaptions.add("TURN_LEFT");
		oCaptions.add("MOVE_FORWARD");
		oCaptions.add("EAT");
		oCaptions.add("SLEEP");
		oCaptions.add("EXCREMENT");
		oCaptions.add("SEARCH1");
		oCaptions.add("BITE");
		oCaptions.add("FOCUS_ON");
		oCaptions.add("SEND_TO_PHANTASY");
		oCaptions.add("FOCUS_MOVEMENT");
		oCaptions.add("FLEE");
		oCaptions.add("UNKNOWN");
		
		return oCaptions;
	}		
	
	
	private boolean testSequence(){
	       //TODO: Just for test. Delete this 2 lines
	    if(mnTestCounter>50)moActionCommandList_Output.add( new clsActionBeat(1.0));
	    mnTestCounter++;
	    if (true) return true;
	    
       if(mnTestCounter==5) moActionCommandList_Output.add( new clsActionDivide(0.5));
       else{
           //moActionCommandList_Output.add( new clsActionMove(eActionMoveDirection.MOVE_FORWARD,1.0));
       }
        mnTestCounter++;
	    
	    // moActionCommandList_Output.add(new clsActionBeat(1.0));
        //moActionCommandList_Output.add(new clsActionMove(eActionMoveDirection.MOVE_FORWARD,1.0));
        
        if (true) return true;
        
       if(mnTestCounter<=1) moActionCommandList_Output.add(new clsActionPickUp());
       else if(mnTestCounter<=10) moActionCommandList_Output.add( new clsActionMove(eActionMoveDirection.MOVE_FORWARD,1.0) );
       else if (mnTestCounter<=20){
            moActionCommandList_Output.add( new clsActionDrop() );
            
        }
       else{
           moActionCommandList_Output.add( new clsActionMove(eActionMoveDirection.MOVE_FORWARD,1.0));
       }
        mnTestCounter++;
	    return true;
    }
	
	
	private boolean testSequence_drop(){
           //TODO: Just for test. Delete this 2 lines
        //moActionCommandList_Output.add(new clsActionPickUp());
        //if (true) return;
       if(mnTestCounter<=1) moActionCommandList_Output.add(new clsActionPickUp());
       else if(mnTestCounter<=2) moActionCommandList_Output.add( new clsActionTurn(eActionTurnDirection.TURN_RIGHT,15));
       else if (mnTestCounter <=5) moActionCommandList_Output.add( new clsActionMove(eActionMoveDirection.MOVE_FORWARD,1.0));
       else if (mnTestCounter<=6){
            moActionCommandList_Output.add( new clsActionDrop() );
            mnTestCounter =-1;
       }
 //       }
 //      else{
 //          moActionCommandList_Output.add( new clsActionMove(eActionMoveDirection.MOVE_FORWARD,1.0));
  //     }
        mnTestCounter++;
        return true;
    }
	
	private boolean testSequenceEat(){
		moActionCommandList_Output.add(new clsActionEat(1.0));
		return true;
	}
	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 03.03.2011, 17:01:28
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
		moDescription = "Conversion of neuro-symbols into raw data.";
	}

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 23.04.2011, 11:15:51
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartAxis()
	 */
	@Override
	public String getTimeChartAxis() {
		return "Action Commands";
	}

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 23.04.2011, 11:15:51
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		return "Action Command Utilization";
	}

    /* (non-Javadoc)
     *
     * @since 29.12.2013 21:43:09
     * 
     * @see modules.interfaces.I2_5_receive#receive_I2_5(java.util.ArrayList, base.datatypes.clsWordPresentationMesh)
     */
    @Override
    public void receive_I2_5(ArrayList<clsWordPresentationMesh> poActionCommands, clsWordPresentationMesh moWordingToContext2) {
        moActionCommands_Input = (ArrayList<clsWordPresentationMesh>)deepCopy(poActionCommands);
        moWordingToContext = moWordingToContext2;
    }	
    /* (non-Javadoc)
    *
    * @since 14.05.2014 10:33:20
    * 
    * @see inspector.interfaces.itfInspectorTimeChartBase#getProperties()
    */
   @Override
   public clsTimeChartPropeties getProperties() {
       return new clsTimeChartPropeties(false);
   }
    
}
