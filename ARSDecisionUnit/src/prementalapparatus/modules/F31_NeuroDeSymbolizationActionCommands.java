/**
 * E31_NeuroDeSymbolization.java: DecisionUnits - pa.modules
 * 
 * @author brandstaetter
 * 11.08.2009, 14:59:58
 */
package prementalapparatus.modules;

import inspector.interfaces.itfInspectorGenericActivityTimeChart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.SortedMap;

import communication.datatypes.clsDataContainer;
import communication.datatypes.clsDataPoint;

import properties.clsProperties;

import memorymgmt.enums.eAction;
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
	
    private clsDataContainer moActionCommandList_Output;
    private ArrayList<String> moActionQueue;

	private ArrayList<clsWordPresentationMesh> moActionCommands_Input;
	private int mnCounter, moActionBlockingTime;
	private clsWordPresentationMesh lastAction; 
	private clsWordPresentationMesh lastRealAction;
	private clsWordPresentationMesh realAction;
	private ArrayList<String> inputActionHistory;
	private ArrayList<String> realActionHistory;
	private static final boolean bUSEUNREAL = false;
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
        moActionCommandList_Output = new clsDataContainer();
        moActionQueue = new ArrayList<String>();
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
        text += toText.valueToTEXT("moActionCommandList_Output", moActionCommandList_Output);
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
        moActionCommandList_Output = new clsDataContainer();
        
        if( moActionCommands_Input.size() > 0 ) {
            for(clsWordPresentationMesh oActionWPM : moActionCommands_Input) {
                
                if (oActionWPM == null) 
                    return;
                
                String oAction = oActionWPM.getContent();
                inputActionHistory.add(oAction.toString());

                
                //HACK to implement sequences. Will be unneccessary when sequences are implemented with acts
                if(oAction.equals(eAction.FOCUS_ON.toString()) ||
                        oAction.equals(eAction.FOCUS_MOVE_FORWARD.toString()) ||
                      /*  oAction.equals(eAction.NONE.toString()) ||
                        oAction.equals(eAction.SEND_TO_PHANTASY.toString()) ||
                        oAction.equals(eAction.FOCUS_SEARCH1.toString()) ||
                        oAction.equals(eAction.FOCUS_TURN_LEFT.toString()) ||
                        oAction.equals(eAction.FOCUS_TURN_RIGHT.toString()) ||
                        oAction.equals(eAction.PERFORM_BASIC_ACT_ANALYSIS.toString()) || */
                        oAction.equals(eAction.SEARCH1.toString())) {
                        
                        if(moActionQueue.size()>0){
                            oAction = moActionQueue.get(0);
                            moActionQueue.remove(0);
                        }      
                 }
                else{
                    //Clear Action Queue if real Action comes up
                    moActionQueue.clear();
                }

                if(oAction.equals("TURN_VISION")){
                    //just for Test
                    java.util.Random random = new java.util.Random();
                    int rnd = random.nextInt(61) - 30;
                    if( rnd <= 0){
                        ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
                        oAttributes.add(new clsDataPoint("DIRECTION","TURN_LEFT"));
                        oAttributes.add(new clsDataPoint("ANGLE",""+(rnd*-1)));
                        moActionCommandList_Output.addDataPoint(createAction("TURN_VISION",oAttributes));
                    }
                    else{
                          ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
                            oAttributes.add(new clsDataPoint("DIRECTION","TURN_RIGHT"));
                            oAttributes.add(new clsDataPoint("ANGLE",""+(rnd)));
                            moActionCommandList_Output.addDataPoint(createAction("TURN_VISION",oAttributes));
                    }
                        
                }else if(oAction.equals("MOVE_FORWARD")){
                    ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
                    oAttributes.add(new clsDataPoint("DIRECTION","FORWARD"));
                    oAttributes.add(new clsDataPoint("DISTANCE","1.0"));
                    moActionCommandList_Output.addDataPoint(createAction("MOVE",oAttributes));
                } else if(oAction.equals("MOVE_FORWARD_SLOW")){
                    ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
                    oAttributes.add(new clsDataPoint("DIRECTION","FORWARD"));
                    oAttributes.add(new clsDataPoint("DISTANCE","0.2"));
                    moActionCommandList_Output.addDataPoint(createAction("MOVE",oAttributes));
                } else if(oAction.equals("STOP")){
                    ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
                    oAttributes.add(new clsDataPoint("DIRECTION","FORWARD"));
                    oAttributes.add(new clsDataPoint("DISTANCE","0.0"));
                    moActionCommandList_Output.addDataPoint(createAction("MOVE",oAttributes));
                } else if(oAction.equals("MOVE_BACKWARD")){
                    ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
                    oAttributes.add(new clsDataPoint("DIRECTION","BACKWARD"));
                    oAttributes.add(new clsDataPoint("DISTANCE","1.0"));
                    moActionCommandList_Output.addDataPoint(createAction("MOVE",oAttributes));
                } else if(oAction.equals("TURN_LEFT")){
                    ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
                    oAttributes.add(new clsDataPoint("DIRECTION","TURN_LEFT"));
                    oAttributes.add(new clsDataPoint("ANGLE","10.0"));
                    moActionCommandList_Output.addDataPoint(createAction("TURN",oAttributes));
                } else if(oAction.equals("TURN_LEFT45")){
                    ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
                    oAttributes.add(new clsDataPoint("DIRECTION","TURN_LEFT"));
                    oAttributes.add(new clsDataPoint("ANGLE","45.0"));
                    moActionCommandList_Output.addDataPoint(createAction("TURN",oAttributes));

                } else if(oAction.equals("TURN_LEFT90")){
                    ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
                    oAttributes.add(new clsDataPoint("DIRECTION","TURN_LEFT"));
                    oAttributes.add(new clsDataPoint("ANGLE","90.0"));
                    moActionCommandList_Output.addDataPoint(createAction("TURN",oAttributes));

                } else if(oAction.equals("TURN_LEFT180")){
                    ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
                    oAttributes.add(new clsDataPoint("DIRECTION","TURN_LEFT"));
                    oAttributes.add(new clsDataPoint("ANGLE","180.0"));
                    moActionCommandList_Output.addDataPoint(createAction("TURN",oAttributes));

                } else if(oAction.equals("TURN_RIGHT")){
                    ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
                    oAttributes.add(new clsDataPoint("DIRECTION","TURN_RIGHT"));
                    oAttributes.add(new clsDataPoint("ANGLE","10.0"));
                    moActionCommandList_Output.addDataPoint(createAction("TURN",oAttributes));

                } else if(oAction.equals("TURN_RIGHT45")){
                    ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
                    oAttributes.add(new clsDataPoint("DIRECTION","TURN_RIGHT"));
                    oAttributes.add(new clsDataPoint("ANGLE","45.0"));
                    moActionCommandList_Output.addDataPoint(createAction("TURN",oAttributes));
                } else if(oAction.equals("TURN_RIGHT90")){
                    ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
                    oAttributes.add(new clsDataPoint("DIRECTION","TURN_RIGHT"));
                    oAttributes.add(new clsDataPoint("ANGLE","90.0"));
                    moActionCommandList_Output.addDataPoint(createAction("TURN",oAttributes));
                } else if(oAction.equals("TURN_RIGHT180")){
                    ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
                    oAttributes.add(new clsDataPoint("DIRECTION","TURN_RIGHT"));
                    oAttributes.add(new clsDataPoint("ANGLE","180.0"));
                    moActionCommandList_Output.addDataPoint(createAction("TURN",oAttributes));
                } else if(oAction.equals("LOOK_AROUND")){
                    ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
                    oAttributes.add(new clsDataPoint("DIRECTION","TURN_RIGHT"));
                    oAttributes.add(new clsDataPoint("ANGLE","360.0"));
                    moActionCommandList_Output.addDataPoint(createAction("TURN",oAttributes));
                } else if(oAction.equals("STRAFE_LEFT")){
                    /*if (mnCounter%35==0) {
                        moActionCommandList_Output.add( clsActionSequenceFactory.getStrafeLeftSequence(1) );
                        mnCounter = 0;
                    }           */      
                } else if(oAction.equals("STRAFE_RIGHT")){
                    /*if (mnCounter%35==0) {
                        moActionCommandList_Output.add( clsActionSequenceFactory.getStrafeRightSequence(1) );
                        mnCounter = 0;
                    } */                    
                } else if(oAction.equals("EAT")) {
                    ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
                    moActionCommandList_Output.addDataPoint(createAction("EAT",oAttributes));
                } else if (oAction.equals("BEAT")) {
                    ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
                    oAttributes.add(new clsDataPoint("FORCE","1.0"));
                    moActionCommandList_Output.addDataPoint(createAction("BEAT",oAttributes));
                } else if (oAction.equals("DIVIDE")) {
                    ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
                    oAttributes.add(new clsDataPoint("FACTOR","1.0"));
                    moActionCommandList_Output.addDataPoint(createAction("DIVIDE",oAttributes));
                } else if (oAction.equals("SLEEP")) {
                    ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
                    oAttributes.add(new clsDataPoint("INTENSITY","DEEP"));
                    moActionCommandList_Output.addDataPoint(createAction("SLEEP",oAttributes));

                } else if (oAction.equals("RELAX")) {
                    ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
                    oAttributes.add(new clsDataPoint("INTENSITY","DEEP"));
                    moActionCommandList_Output.addDataPoint(createAction("SLEEP",oAttributes));

                } else if (oAction.equals("DEPOSIT")) {
                    ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
                    oAttributes.add(new clsDataPoint("INTENSITY","1.0"));
                    moActionCommandList_Output.addDataPoint(createAction("EXCREMENT",oAttributes));

                } else if (oAction.equals("REPRESS")) {
                    throw new UnknownError("Action " + oAction + " should not occure in F31!");
                }
        
                else if (oAction.equals("SEARCH1")) {
                    //add first Action
                    ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
                    oAttributes.add(new clsDataPoint("DIRECTION","TURN_RIGHT"));
                    oAttributes.add(new clsDataPoint("ANGLE","20.0"));
                    moActionCommandList_Output.addDataPoint(createAction("TURN",oAttributes));
                    // add other actions to queue    
                    addSearch1SequenceToQueue();
                } else if (oAction.equals(eAction.FOCUS_ON.toString())) {
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
                    moActionCommandList_Output.addDataPoint(createAction("PICK_UP",null));

                }
                else if (oAction.equals(eAction.DROP.toString())) {
                    moActionCommandList_Output.addDataPoint(createAction("DROP",null));
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
    }
	
	   //HACK: Will be unneccessary when sequences are implemented with acts
    private void addSearch1SequenceToQueue(){

        moActionQueue.add("TURN_RIGHT");
        moActionQueue.add("TURN_LEFT");
        moActionQueue.add("TURN_LEFT");
        moActionQueue.add("TURN_RIGHT");
        moActionQueue.add("MOVE_FORWARD");
        moActionQueue.add("MOVE_FORWARD");
        moActionQueue.add("TURN_LEFT");
        moActionQueue.add("MOVE_FORWARD");
        moActionQueue.add("TURN_RIGHT");
        moActionQueue.add("MOVE_FORWARD");
        moActionQueue.add("MOVE_FORWARD");
        moActionQueue.add("MOVE_FORWARD");
        
    }
    
    private clsDataPoint createAction(String poName,ArrayList<clsDataPoint> poAttributes){
        clsDataPoint oRetVal = new clsDataPoint("ACTION_COMMAND", poName);
        oRetVal.setBufferType("EVENT");
        if(poAttributes!= null){
            for(clsDataPoint oPoint: poAttributes) oRetVal.addAssociation(oPoint);
        }
        return oRetVal;
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
		send_I1_5(moActionCommandList_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 18.05.2010, 17:59:41
	 * 
	 * @see pa.interfaces.send.I8_2_send#send_I8_2(java.util.ArrayList)
	 */
	@Override
	public void send_I1_5(clsDataContainer poActionCommandList) {
		((I1_5_receive)moModuleList.get(32)).receive_I1_5(poActionCommandList);
		
		putInterfaceData(I1_5_send.class, poActionCommandList);
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
	    moActionCommandList_Output = new clsDataContainer();
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
     //moActionCommandList_Output.add(new clsActionPickUp());
     //if (true) return;
    if(mnTestCounter<=1){
        moActionCommandList_Output.addDataPoint(createAction("PICK_UP",null));
    }

     mnTestCounter++;
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
    
}
