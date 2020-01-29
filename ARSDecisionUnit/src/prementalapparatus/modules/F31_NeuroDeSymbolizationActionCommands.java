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

import communication.datatypes.clsDataContainer;
import communication.datatypes.clsDataPoint;
import properties.clsProperties;
import memorymgmt.enums.eAction;
import modules.interfaces.I1_5_receive;
import modules.interfaces.I1_5_send;
import modules.interfaces.I2_5_receive;
import modules.interfaces.eInterfaces;
import base.datatypes.clsShortTermMemoryMF;
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
	
	private clsDataPoint moPreviousActiveActions = null;
	
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
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, int pnUid)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, pnUid);
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
		oProp.setProperty(pre + P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
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

        boolean newActionAvailable=false;
        
        if( moActionCommands_Input.size() > 0) {
            for(clsWordPresentationMesh oActionWPM : moActionCommands_Input) {

                String oAction;
                if(oActionWPM!=null){
                    oAction = oActionWPM.getContent();
                    inputActionHistory.add(oAction.toString());
                    newActionAvailable = true;
                    
                    clsShortTermMemoryMF test = new clsShortTermMemoryMF(null);
                    if(test.getActualStep()>380 && this.getAgentIndex()==1 && test.getActualStep()<460 )
                    {
                        oAction="MOVE_BACKWARD";
                    }
                    
                    processActionCommand(oAction, moActionCommandList_Output, true);
                    
                }                
                
            }
        }
        if (moActionQueue.size() > 0 && !newActionAvailable) {
            
            String  oAction = moActionQueue.get(0);
            moActionQueue.remove(0);
            processActionCommand(oAction, moActionCommandList_Output, false);
            
        }
        
        log.info("ActionCommandList: "+ moActionCommandList_Output.toString());
    }
	
	private void processActionCommand(String oAction, clsDataContainer oRetVal, boolean nonQueueAction){

	    //PROCESS NON REAL ACTIONS
	    if(oAction.equals(eAction.FOCUS_ON.toString()) ||
                oAction.equals(eAction.FOCUS_MOVE_FORWARD.toString()) ||
                oAction.equals(eAction.NONE.toString()) ||
                oAction.equals(eAction.SEND_TO_PHANTASY.toString()) ||
                oAction.equals(eAction.FOCUS_SEARCH1.toString()) ||
                oAction.equals(eAction.FOCUS_TURN_LEFT.toString()) ||
                oAction.equals(eAction.FOCUS_TURN_RIGHT.toString()) ||
                oAction.equals(eAction.PERFORM_BASIC_ACT_ANALYSIS.toString())) {
                
                if(moActionQueue.size() > 0){
                    oAction = moActionQueue.get(0);
                    moActionQueue.remove(0);
                } else {
                    if(moPreviousActiveActions != null){
                       // oRetVal.addDataPoint(moPreviousActiveActions);
                    }
                    return;
                }
         }
        else if(oAction.equals(eAction.SEARCH1.toString())){
            
            if(moActionQueue.size() < 10){
                addSearch1SequenceToQueue();
            }

            oAction = moActionQueue.get(0);
            moActionQueue.remove(0);

        }
        else{
            //Clear Action Queue if real Action comes up
            if(nonQueueAction) moActionQueue.clear();
        }
        
        //START PROCESS REAL ACTIONS
/*
        if(oAction.equals(eAction.FOCUS_ON.toString()) ||
                oAction.equals(eAction.FOCUS_MOVE_FORWARD.toString()) ||
                oAction.equals(eAction.NONE.toString()) ||
                oAction.equals(eAction.SEND_TO_PHANTASY.toString()) ||
                oAction.equals(eAction.FOCUS_SEARCH1.toString()) ||
                oAction.equals(eAction.FOCUS_TURN_LEFT.toString()) ||
                oAction.equals(eAction.FOCUS_TURN_RIGHT.toString()) ||
                oAction.equals(eAction.PERFORM_BASIC_ACT_ANALYSIS.toString())){
            //do nothing
        } */
	    ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
	    clsDataPoint oNewAction = null;
	    
	    // Zhukova 
	    
	    switch(oAction) {
	    
	        case "TURN_VISION":
            //just for Test
	            java.util.Random random = new java.util.Random();
	            int rnd = random.nextInt(61) - 30;
	            if( rnd <= 0) {
	                oAttributes.add(new clsDataPoint("DIRECTION","TURN_LEFT"));
	                oAttributes.add(new clsDataPoint("ANGLE",""+(rnd*-1)));
	                oNewAction = createAction("TURN_VISION",oAttributes);
	            } else {
	                oAttributes.add(new clsDataPoint("DIRECTION","TURN_RIGHT"));
	                oAttributes.add(new clsDataPoint("ANGLE",""+(rnd)));
	                oNewAction = createAction("TURN_VISION", oAttributes);
	             }
	            break;
	            
	        case "MOVE_FORWARD":
	            oAttributes.add(new clsDataPoint("DIRECTION","FORWARD"));
	            oAttributes.add(new clsDataPoint("DISTANCE","1.0"));
	            oNewAction = createAction("MOVE", oAttributes);
	            break;

	        case"MOVE_FORWARD_SLOW":
	            oAttributes.add(new clsDataPoint("DIRECTION","FORWARD"));
	            oAttributes.add(new clsDataPoint("DISTANCE","0.2"));
	            oNewAction = createAction("MOVE", oAttributes);
	            break;
            
	        case "STOP":
	            oAttributes.add(new clsDataPoint("DIRECTION","FORWARD"));
	            oAttributes.add(new clsDataPoint("DISTANCE","0.0"));
	            oNewAction = createAction("MOVE" , oAttributes);
	            break; 
            
	        case "MOVE_BACKWARD":
	            oAttributes.add(new clsDataPoint("DIRECTION","BACKWARD"));
	            oAttributes.add(new clsDataPoint("DISTANCE","1.0"));
	            oNewAction = createAction("MOVE", oAttributes);
	            break;
	            
	        case "TURN_LEFT":
	            oAttributes.add(new clsDataPoint("DIRECTION","TURN_LEFT"));
	            oAttributes.add(new clsDataPoint("ANGLE","5.0"));
	            oNewAction = createAction("TURN", oAttributes);
	            break;
            
	        case "TURN_LEFT10":
	            oAttributes.add(new clsDataPoint("DIRECTION","TURN_LEFT"));
	            oAttributes.add(new clsDataPoint("ANGLE","10.0"));
	            oNewAction = createAction("TURN", oAttributes);
	            break;
            
	        case "TURN_LEFT45":
	            oAttributes.add(new clsDataPoint("DIRECTION","TURN_LEFT"));
	            oAttributes.add(new clsDataPoint("ANGLE","45.0"));
	            oNewAction = createAction("TURN", oAttributes);
	            break;

	        case "TURN_LEFT90":
	            oAttributes.add(new clsDataPoint("DIRECTION","TURN_LEFT"));
	            oAttributes.add(new clsDataPoint("ANGLE","90.0"));
	            oNewAction = createAction("TURN", oAttributes);
	            break;

	        case "TURN_LEFT180":
	            oAttributes.add(new clsDataPoint("DIRECTION","TURN_LEFT"));
	            oAttributes.add(new clsDataPoint("ANGLE","180.0"));
	            oNewAction = createAction("TURN", oAttributes);
	            break;
	            
	        case "TURN_RIGHT": 
	            oAttributes.add(new clsDataPoint("DIRECTION","TURN_RIGHT"));
	            oAttributes.add(new clsDataPoint("ANGLE","5.0"));
	            oRetVal.addDataPoint(createAction("TURN",oAttributes));
	            break;
	            
	        case "TURN_RIGHT10":
	            oAttributes.add(new clsDataPoint("DIRECTION","TURN_RIGHT"));
	            oAttributes.add(new clsDataPoint("ANGLE","10.0"));
	            oNewAction = createAction("TURN", oAttributes);
	            break;
	            
	        case "TURN_RIGHT45":
	            oAttributes.add(new clsDataPoint("DIRECTION","TURN_RIGHT"));
	            oAttributes.add(new clsDataPoint("ANGLE","45.0"));
	            oNewAction = createAction("TURN", oAttributes);
	            break;
	            
	        case "TURN_RIGHT90":
	            oAttributes.add(new clsDataPoint("DIRECTION","TURN_RIGHT"));
	            oAttributes.add(new clsDataPoint("ANGLE","90.0"));
	            oNewAction = createAction("TURN", oAttributes);
	            break;
	            
	        case "TURN_RIGHT180":
	            oAttributes.add(new clsDataPoint("DIRECTION","TURN_RIGHT"));
	            oAttributes.add(new clsDataPoint("ANGLE","180.0"));
	            oNewAction = createAction("TURN", oAttributes);
	            break;
	            
	        case "LOOK_AROUND":
	            oAttributes.add(new clsDataPoint("DIRECTION","TURN_RIGHT"));
	            oAttributes.add(new clsDataPoint("ANGLE","360.0"));
	            oNewAction = createAction("TURN", oAttributes);
	            break;
            
	        case "STRAFE_LEFT":
	        case "STRAFE_RIGHT":
                 
	        case "EAT":
	            oNewAction = createAction("EAT",oAttributes);
	            break;
            
	        case "BEAT":
	            oAttributes.add(new clsDataPoint("FORCE","1.0"));
	            oNewAction = createAction("BEAT", oAttributes);
	            break;
	            
	        case "DIVIDE": 
	            oAttributes.add(new clsDataPoint("FACTOR","0.5"));
	            oNewAction = createAction("DIVIDE",oAttributes);
	            break;
            
	        case "SLEEP" :
	            oAttributes.add(new clsDataPoint("INTENSITY","DEEP"));
	            oNewAction = createAction("SLEEP",oAttributes);
	            break;

	        case "RELAX": 
	            oAttributes.add(new clsDataPoint("INTENSITY","DEEP"));
	            oNewAction = createAction("SLEEP",oAttributes);
	            break;

	        case "DEPOSIT": 
	            oAttributes.add(new clsDataPoint("INTENSITY","1.0"));
	            oNewAction = createAction("EXCREMENT",oAttributes);
	            break;
	            
	        case "REPRESS":
	                throw new UnknownError("Action " + oAction + " should not occure in F31!");
	                
	        case "FOCUS_ON":
	        case "NONE":
	        case "SEND_TO_PHANTASY":
	        case "FOCUS_MOVE_FORWARD":  
	        case "FOCUS_SEARCH1":
	        case "FOCUS_TURN_LEFT":
	        case "FOCUS_TURN_RIGHT":
	        case "PERFORM_BASIC_ACT_ANALYSIS":
	        case "NULLOBJECT":
	            
	        case "PICK_UP":
	            oNewAction = createAction("PICK_UP", null);
	            break;
	            
	        case "DROP": 
	            oNewAction = createAction("DROP", null);
	            break;
	            
	        case "FLEE": 
	            throw new UnknownError("Action " + oAction + " is a complex action that has not been translated into basic actions");

	        case "GOTO":
                throw new UnknownError("Action " + oAction + " is a complex action that has not been translated into basic actions");

            case "REQUEST":
                oNewAction = createAction("REQUEST", oAttributes);
                break;    
            
            case "OBJECT_TRANSFER":
                oNewAction = createAction("REQUEST", null);
                break;       
            
            case "WAIT":
                oNewAction = createAction("WAIT", oAttributes);
                break;
                
	        default: 
	            throw new UnknownError("Action " + oAction + " not known");
	            

	    }
	       
        if(oNewAction != null) {
            oRetVal.addDataPoint(oNewAction);
            moPreviousActiveActions = oNewAction;
        }
        else if(moPreviousActiveActions != null){
            //oRetVal.addDataPoint(moPreviousActiveActions);
        }
	    /* old code with if then else structure
        if(oAction.equals("TURN_VISION")){
            //just for Test
            java.util.Random random = new java.util.Random();
            int rnd = random.nextInt(61) - 30;
            if( rnd <= 0) {
                oAttributes.add(new clsDataPoint("DIRECTION","TURN_LEFT"));
                oAttributes.add(new clsDataPoint("ANGLE",""+(rnd*-1)));
                oNewAction = createAction("TURN_VISION",oAttributes);
                //oRetVal.addDataPoint(createAction("TURN_VISION",oAttributes));
            }
            else {
                  oAttributes.add(new clsDataPoint("DIRECTION","TURN_RIGHT"));
                  oAttributes.add(new clsDataPoint("ANGLE",""+(rnd)));
                  oNewAction = createAction("TURN_VISION", oAttributes);
                  //oRetVal.addDataPoint(createAction("TURN_VISION",oAttributes));
            }
                
        } else if(oAction.equals("MOVE_FORWARD")){
            oAttributes.add(new clsDataPoint("DIRECTION","FORWARD"));
            oAttributes.add(new clsDataPoint("DISTANCE","1.0"));
            //oRetVal.addDataPoint(createAction("MOVE",oAttributes));
            oNewAction = createAction("MOVE",oAttributes);
            
        } else if(oAction.equals(eAction.NULLOBJECT.toString())){
            //do nothing
            
        } else if(oAction.equals("MOVE_FORWARD_SLOW")){
            oAttributes.add(new clsDataPoint("DIRECTION","FORWARD"));
            oAttributes.add(new clsDataPoint("DISTANCE","0.2"));
            oNewAction = createAction("MOVE",oAttributes);
            //oRetVal.addDataPoint(createAction("MOVE",oAttributes));
            
        } else if(oAction.equals("STOP")){
            oAttributes.add(new clsDataPoint("DIRECTION","FORWARD"));
            oAttributes.add(new clsDataPoint("DISTANCE","0.0"));
            oNewAction = createAction("MOVE" ,oAttributes);
            //oRetVal.addDataPoint(createAction("MOVE",oAttributes));
            
        } else if(oAction.equals("MOVE_BACKWARD")){
            oAttributes.add(new clsDataPoint("DIRECTION","BACKWARD"));
            oAttributes.add(new clsDataPoint("DISTANCE","1.0"));
            oNewAction = createAction("MOVE", oAttributes);
            //oRetVal.addDataPoint(createAction("MOVE",oAttributes));
            
        } else if(oAction.equals("TURN_LEFT")){
            //ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
            oAttributes.add(new clsDataPoint("DIRECTION","TURN_LEFT"));
            oAttributes.add(new clsDataPoint("ANGLE","5.0"));
            oNewAction = createAction("TURN", oAttributes);
            //oRetVal.addDataPoint(createAction("TURN",oAttributes));
            
        } else if(oAction.equals("TURN_LEFT10")){
            //ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
            oAttributes.add(new clsDataPoint("DIRECTION","TURN_LEFT"));
            oAttributes.add(new clsDataPoint("ANGLE","10.0"));
            oNewAction = createAction("TURN", oAttributes);
            //oRetVal.addDataPoint(createAction("TURN",oAttributes));
            
        } else if(oAction.equals("TURN_LEFT45")){
            //ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
            oAttributes.add(new clsDataPoint("DIRECTION","TURN_LEFT"));
            oAttributes.add(new clsDataPoint("ANGLE","45.0"));
            oNewAction = createAction("TURN", oAttributes);
            //oRetVal.addDataPoint(createAction("TURN",oAttributes));

        } else if(oAction.equals("TURN_LEFT90")){
            //ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
            oAttributes.add(new clsDataPoint("DIRECTION","TURN_LEFT"));
            oAttributes.add(new clsDataPoint("ANGLE","90.0"));
            oNewAction = createAction("TURN", oAttributes);
            //oRetVal.addDataPoint(createAction("TURN",oAttributes));

        } else if(oAction.equals("TURN_LEFT180")){
            //ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
            oAttributes.add(new clsDataPoint("DIRECTION","TURN_LEFT"));
            oAttributes.add(new clsDataPoint("ANGLE","180.0"));
            oNewAction = createAction("TURN", oAttributes);
            //oRetVal.addDataPoint(createAction("TURN",oAttributes));

        } else if(oAction.equals("TURN_RIGHT")){
            //ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
            oAttributes.add(new clsDataPoint("DIRECTION","TURN_RIGHT"));
            oAttributes.add(new clsDataPoint("ANGLE","5.0"));
            oRetVal.addDataPoint(createAction("TURN",oAttributes));
            
        } else if(oAction.equals("TURN_RIGHT10")){
            //ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
            oAttributes.add(new clsDataPoint("DIRECTION","TURN_RIGHT"));
            oAttributes.add(new clsDataPoint("ANGLE","10.0"));
            oNewAction = createAction("TURN", oAttributes);
            //oRetVal.addDataPoint(createAction("TURN",oAttributes));
            
        } else if(oAction.equals("TURN_RIGHT45")){
            //ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
            oAttributes.add(new clsDataPoint("DIRECTION","TURN_RIGHT"));
            oAttributes.add(new clsDataPoint("ANGLE","45.0"));
            //oRetVal.addDataPoint(createAction("TURN",oAttributes));
            oNewAction = createAction("TURN", oAttributes);
            
        } else if(oAction.equals("TURN_RIGHT90")){
            //ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
            oAttributes.add(new clsDataPoint("DIRECTION","TURN_RIGHT"));
            oAttributes.add(new clsDataPoint("ANGLE","90.0"));
            oNewAction = createAction("TURN", oAttributes);
            //oRetVal.addDataPoint(createAction("TURN",oAttributes));
            
        } else if(oAction.equals("TURN_RIGHT180")){
            //ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
            oAttributes.add(new clsDataPoint("DIRECTION","TURN_RIGHT"));
            oAttributes.add(new clsDataPoint("ANGLE","180.0"));
            oRetVal.addDataPoint(createAction("TURN",oAttributes));
            
        } else if(oAction.equals("LOOK_AROUND")){
            //ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
            oAttributes.add(new clsDataPoint("DIRECTION","TURN_RIGHT"));
            oAttributes.add(new clsDataPoint("ANGLE","360.0"));
            oNewAction = createAction("TURN", oAttributes);
            //oRetVal.addDataPoint(createAction("TURN",oAttributes));
            
        } else if(oAction.equals("STRAFE_LEFT")){
            /*if (mnCounter%35==0) {
                moActionCommandList_Output.add( clsActionSequenceFactory.getStrafeLeftSequence(1) );
                mnCounter = 0;
            }                
        } else if(oAction.equals("STRAFE_RIGHT")){
            /*if (mnCounter%35==0) {
                moActionCommandList_Output.add( clsActionSequenceFactory.getStrafeRightSequence(1) );
                mnCounter = 0;
            } */                    
       /* } else if(oAction.equals("EAT")) {
            //ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
            //oNewAction = createAction("TURN", oAttributes);
            //oRetVal.addDataPoint(createAction("EAT",oAttributes));
            
        } else if (oAction.equals("BEAT")) {
            //ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
            oAttributes.add(new clsDataPoint("FORCE","1.0"));
            //oNewAction = createAction("TURN", oAttributes);
            //oRetVal.addDataPoint(createAction("BEAT",oAttributes));
            
        } else if (oAction.equals("DIVIDE")) {
            //ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
            oAttributes.add(new clsDataPoint("FACTOR","0.5"));
            oRetVal.addDataPoint(createAction("DIVIDE",oAttributes));
            
        } else if (oAction.equals("SLEEP")) {
            //ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
            oAttributes.add(new clsDataPoint("INTENSITY","DEEP"));
            oRetVal.addDataPoint(createAction("SLEEP",oAttributes));

        } else if (oAction.equals("RELAX")) {
            //ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
            oAttributes.add(new clsDataPoint("INTENSITY","DEEP"));
            oRetVal.addDataPoint(createAction("SLEEP",oAttributes));

        } else if (oAction.equals("DEPOSIT")) {
            //ArrayList<clsDataPoint> oAttributes = new ArrayList<clsDataPoint>();
            oAttributes.add(new clsDataPoint("INTENSITY","1.0"));
            oRetVal.addDataPoint(createAction("EXCREMENT",oAttributes));

        } else if (oAction.equals("REPRESS")) {
            throw new UnknownError("Action " + oAction + " should not occure in F31!");

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
            
        }else if(oAction.equals(eAction.NULLOBJECT.toString())){
            //do nothing

        } else if (oAction.equals(eAction.PICK_UP.toString())) {
            oRetVal.addDataPoint(createAction("PICK_UP",null));

        }
        else if (oAction.equals(eAction.DROP.toString())) {
            oRetVal.addDataPoint(createAction("DROP",null));
        }
        else if (oAction.equals(eAction.FLEE.toString()) 
                || oAction.equals(eAction.GOTO.toString())) {
            throw new UnknownError("Action " + oAction + " is a complex action that has not been translated into basic actions");
        }
        else {
            throw new UnknownError("Action " + oAction + " not known");
        }
        */
        
	}
	
	   //HACK: Will be unneccessary when sequences are implemented with acts
    private void addSearch1SequenceToQueue(){

  /*      for(int i=0;i<9;i++) moActionQueue.add("TURN_RIGHT10");
        for(int i=0;i<18;i++) moActionQueue.add("TURN_LEFT10");
        for(int i=0;i<9;i++) moActionQueue.add("TURN_RIGHT10");
        for(int i=0;i<18;i++) moActionQueue.add("MOVE_FORWARD");
        for(int i=0;i<4;i++) moActionQueue.add("TURN_RIGHT10");
        for(int i=0;i<8;i++) moActionQueue.add("MOVE_FORWARD");
        for(int i=0;i<5;i++) moActionQueue.add("TURN_LEFT10");
  */      
        for(int i = 0; i < 100; i++) moActionQueue.add("MOVE_BACKWARD");
        for(int i = 0; i < 1; i++) moActionQueue.add("TURN_RIGHT45");
        for(int i = 0; i < 2; i++) moActionQueue.add("TURN_LEFT45");
        for(int i = 0; i < 1; i++) moActionQueue.add("TURN_RIGHT45");
        for(int i = 0; i < 18; i++) moActionQueue.add("MOVE_FORWARD");
        for(int i = 0; i <1; i++) moActionQueue.add("TURN_RIGHT45");
        for(int i = 0; i <8; i++) moActionQueue.add("MOVE_FORWARD");
        for(int i = 0; i <1; i++) moActionQueue.add("TURN_LEFT45");
          
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

    /* (non-Javadoc)
     *
     * @since 11.06.2014 09:43:09
     * 
     * @see inspector.interfaces.itfInspectorTimeChartBase#getProperties()
     */
    @Override
    public clsTimeChartPropeties getProperties() {
        return new clsTimeChartPropeties(true);
    }	
    
}
