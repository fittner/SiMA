/**
 * E31_NeuroDeSymbolization.java: DecisionUnits - pa.modules
 * 
 * @author brandstaetter
 * 11.08.2009, 14:59:58
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsProperties;
import du.enums.eActionMoveDirection;
import du.enums.eActionSleepIntensity;
import du.enums.eActionTurnDirection;
import du.itf.actions.clsActionCommand;
import du.itf.actions.clsActionEat;
import du.itf.actions.clsActionExcrement;
import du.itf.actions.clsActionMove;
import du.itf.actions.clsActionSequenceFactory;
//import du.itf.actions.clsActionSequenceFactory;
import du.itf.actions.clsActionSleep;
import du.itf.actions.clsActionTurn;
import pa._v38.interfaces.itfInspectorGenericActivityTimeChart;
import pa._v38.interfaces.modules.I2_5_receive;
import pa._v38.interfaces.modules.I1_5_receive;
import pa._v38.interfaces.modules.I1_5_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.tools.toText;

/**
 * Conversion of neuro-symbols into raw data. F31_NeuroDeSymbolizationActionCommands should be just an empty function which forwards data to F32.
 * 
 * @author brandstaetter
 * 11.08.2009, 14:59:58
 * 
 */
public class F31_NeuroDeSymbolizationActionCommands extends clsModuleBase 
			implements I2_5_receive, I1_5_send, itfInspectorGenericActivityTimeChart  {
	public static final String P_MODULENUMBER = "31";
	
	private ArrayList<clsActionCommand> moActionCommandList_Output;
	private ArrayList<clsWordPresentation> moActionCommands_Input;
	private int mnCounter, moActionBlockingTime;
	private String lastAction; 
	
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
		lastAction = "";
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
		String text ="";
		text += toText.listToTEXT("moActionCommands_Input", moActionCommands_Input);
		text += toText.valueToTEXT("lastAction", lastAction);
		text += toText.valueToTEXT("mnCounter", mnCounter);
		text += toText.valueToTEXT("moActionBlockingTime", moActionBlockingTime);
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
	 * 11.08.2009, 15:00:27
	 * 
	 * @see pa.interfaces.I8_1#receive_I8_1(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_5(ArrayList<clsWordPresentation> poActionCommands) {
		moActionCommands_Input = (ArrayList<clsWordPresentation>)deepCopy(poActionCommands);
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
			for(clsWordPresentation oWP : moActionCommands_Input) {
			    
				if (oWP == null) 
					return;
				
				String oAction = oWP.getMoContent(); 
				if(oAction.equals(lastAction)) { 
					mnCounter++; 
				}
				else  {
					mnCounter = 0;
				}
			
				if(moActionBlockingTime>0) {
					moActionBlockingTime--;
					if(moActionBlockingTime==0) {
						mnCounter = 0;
					}
				    return;
				}

				if (oAction.equals("FLEE")) {
					if (mnCounter%90==0) {
						moActionCommandList_Output.add( clsActionSequenceFactory.getFleeSequence3(180.0f, 60) );
						mnCounter = 0;
						moActionBlockingTime=90;
						// old, simple flee sequence CB 2011-11-14
						//moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_RIGHT, 20.0));
					}
				}
				else if(oAction.equals("MOVE_FORWARD")){
					moActionCommandList_Output.add( new clsActionMove(eActionMoveDirection.MOVE_FORWARD,1.0) );
					
				} else if(oAction.equals("MOVE_FORWARD_SLOW")){
					moActionCommandList_Output.add( new clsActionMove(eActionMoveDirection.MOVE_FORWARD,0.2) );

				} else if(oAction.equals("STOP")){
					moActionCommandList_Output.add( new clsActionMove(eActionMoveDirection.MOVE_FORWARD,0) );
					
				} else if(oAction.equals("MOVE_BACKWARD")){
					moActionCommandList_Output.add( new clsActionMove(eActionMoveDirection.MOVE_BACKWARD,1.0) );
					
				} else if(oAction.equals("TURN_LEFT")){
					moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_LEFT, 10.0));
					
				} else if(oAction.equals("TURN_LEFT45")){
					moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_LEFT, 45.0));
					
				} else if(oAction.equals("TURN_LEFT90")){
					moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_LEFT, 90.0));
					
				} else if(oAction.equals("TURN_LEFT180")){
					moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_LEFT, 180.0));
					
				} else if(oAction.equals("TURN_RIGHT")){
					moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_RIGHT, 10.0));
					
				} else if(oAction.equals("TURN_RIGHT45")){
					moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_RIGHT, 45.0));
					
				} else if(oAction.equals("TURN_RIGHT90")){
					moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_RIGHT, 90.0));
					
				} else if(oAction.equals("TURN_RIGHT180")){
					moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_RIGHT, 180.0));
					
				} else if(oAction.equals("EAT")) {
					moActionCommandList_Output.add( new clsActionEat() );
					
				} else if (oAction.equals("SLEEP")) {
					moActionCommandList_Output.add( new clsActionSleep(eActionSleepIntensity.DEEP) );
					
				} else if (oAction.equals("DEPOSIT")) {
					moActionCommandList_Output.add( new clsActionExcrement(1) );
				}
//TD 2011/04/23: commented the actions PICKUP, DROP, and DANCE. currently, they can never happen - no rules are defined
/*				else if(oAction.equals("PICKUP")) {
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
					if (mnCounter%75==0) {
						moActionCommandList_Output.add( clsActionSequenceFactory.getSeekingSequence(1.0f, 2) );
						mnCounter = 0;
					} 
				}
				else {
					throw new UnknownError("Action " + oAction + " not known");
				}
				lastAction=oWP.getMoContent();
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
	public void send_I1_5(ArrayList<clsActionCommand> poActionCommandList) {
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
		throw new java.lang.NoSuchMethodError();
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
		double rSEEK = 0.0;
		double rDEFECATE = 0.0;
		double rSLEEP = 0.0;
		
		String oCurrentActionCommand = "";
		
		try {
			oCurrentActionCommand = moActionCommands_Input.get(0).getMoContent();
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
			rDEFECATE = 1.0;			
		} else {
			rSEEK = 1.0;
		}
		
		oRetVal.add(rTURN_RIGHT); 
		oRetVal.add(rTURN_LEFT); 
		oRetVal.add(rMOVE_FORWARD); 
		oRetVal.add(rEAT); 
		oRetVal.add(rSEEK);
		oRetVal.add(rSLEEP);
		oRetVal.add(rDEFECATE);

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
		oCaptions.add("SEEK");
		oCaptions.add("SLEEP");
		oCaptions.add("EXCREMENT");
		
		return oCaptions;
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
}
