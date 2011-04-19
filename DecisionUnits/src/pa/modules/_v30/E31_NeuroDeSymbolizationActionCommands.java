/**
 * E31_NeuroDeSymbolization.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:59:58
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;
import config.clsBWProperties;
import du.enums.eActionMoveDirection;
import du.enums.eActionTurnDirection;
import du.itf.actions.clsActionCommand;
import du.itf.actions.clsActionDrop;
import du.itf.actions.clsActionEat;
import du.itf.actions.clsActionMove;
import du.itf.actions.clsActionPickUp;
import du.itf.actions.clsActionSequenceFactory;
import du.itf.actions.clsActionTurn;
import pa.interfaces._v30.eInterfaces;
import pa.interfaces._v30.itfInspectorTimeChart;
import pa.interfaces.receive._v30.I8_1_receive;
import pa.interfaces.receive._v30.I8_2_receive;
import pa.interfaces.send._v30.I8_2_send;
import pa.memorymgmt.datatypes.clsWordPresentation;

/**
 * DOCUMENT (brandstaetter) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:59:58
 * 
 */
public class E31_NeuroDeSymbolizationActionCommands extends clsModuleBase implements I8_1_receive, I8_2_send, itfInspectorTimeChart  {
	public static final String P_MODULENUMBER = "31";
	
	private ArrayList<clsActionCommand> moActionCommandList_Output;
	private ArrayList<clsWordPresentation> moActionCommands_Input;
	private int mnCounter;
	
	/**
	 * DOCUMENT (brandstaetter) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 17:01:32
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E31_NeuroDeSymbolizationActionCommands(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, HashMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);		
		
		mnCounter = 0; 
		moActionCommandList_Output = new ArrayList<clsActionCommand>();
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v30.clsModuleBase#stateToHTML()
	 */
	@Override
	public String stateToHTML() {
		String html ="";
		
		html += listToHTML("moActionCommands_Input", moActionCommands_Input);
		html += listToHTML("moActionCommandList_Output", moActionCommandList_Output);
		
		return html;
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
	
		//nothing to do
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
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
	 * @author deutsch
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
	 * @author deutsch
	 * 11.08.2009, 15:00:27
	 * 
	 * @see pa.interfaces.I8_1#receive_I8_1(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I8_1(ArrayList<clsWordPresentation> poActionCommands) {
		moActionCommands_Input = (ArrayList<clsWordPresentation>)deepCopy(poActionCommands);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:54
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		
		moActionCommandList_Output.clear();
	
		if( moActionCommands_Input.size() > 0 ) {
				for(clsWordPresentation oWP : moActionCommands_Input) {
				
					String oAction = oWP.getMoContent(); 
				
					if(oAction.equals("MOVE_FORWARD")){
						moActionCommandList_Output.add( new clsActionMove(eActionMoveDirection.MOVE_FORWARD,1.0) );
						//System.out.println("cmd: move_forward");
					}
					else if(oAction.equals("TURN_LEFT")){
						moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_LEFT, 15.0));
						//System.out.println("cmd: turn_left");
					}
					else if(oAction.equals("TURN_RIGHT")){
						moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_RIGHT, 15.0));
						//System.out.println("cmd: turn_right");
					}
					//end add
					else if(oAction.equals("PICKUP")) {
						moActionCommandList_Output.add( new clsActionPickUp() );
						//System.out.println("cmd: pickup");
					}
					else if(oAction.equals("DROP")) {
						moActionCommandList_Output.add( new clsActionDrop() );
						//System.out.println("cmd: drop");
					}
					else if(oAction.equals("EAT")) {
						moActionCommandList_Output.add( new clsActionEat() );
						//System.out.println("cmd: eat");
					}
					else if(oAction.equals("DANCE_1")) {
						moActionCommandList_Output.add( clsActionSequenceFactory.getWalzSequence(1, 2) );
						//System.out.println("cmd: dance");
					}
					else {
						throw new UnknownError("Action " + oAction + " not known");
					}
				}
		}
		else {
			if (mnCounter == 75) {
				moActionCommandList_Output.add( clsActionSequenceFactory.getSeekingSequence(1.0f, 2) );
				mnCounter = 0;
			} 
			mnCounter++;
		}
			
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:54
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I8_2(moActionCommandList_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:59:41
	 * 
	 * @see pa.interfaces.send.I8_2_send#send_I8_2(java.util.ArrayList)
	 */
	@Override
	public void send_I8_2(ArrayList<clsActionCommand> poActionCommandList) {
		((I8_2_receive)moModuleList.get(32)).receive_I8_2(poActionCommandList);
		putInterfaceData(I8_2_send.class, poActionCommandList);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:48:02
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (branstaetter) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:48:02
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (branstaetter) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();	
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
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
		} else {
			rSEEK = 1.0;
		}
		
		oRetVal.add(rTURN_RIGHT); 
		oRetVal.add(rTURN_LEFT); 
		oRetVal.add(rMOVE_FORWARD); 
		oRetVal.add(rEAT); 
		oRetVal.add(rSEEK); 

		return oRetVal; 
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
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
		
		return oCaptions;
	}		

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 17:01:28
	 * 
	 * @see pa.modules._v30.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v30.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "Conversion of neuro-symbols into raw data.";
	}		
}
