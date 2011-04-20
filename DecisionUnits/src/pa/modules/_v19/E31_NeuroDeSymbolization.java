/**
 * E31_NeuroDeSymbolization.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:59:58
 */
package pa.modules._v19;

import java.util.ArrayList;

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
import pa._v19.clsInterfaceHandler;
import pa.interfaces._v19.itfTimeChartInformationContainer;
import pa.interfaces.receive._v19.I8_1_receive;
import pa.interfaces.receive._v19.I8_2_receive;
import pa.interfaces.send._v19.I8_2_send;
import pa.memorymgmt.datatypes.clsWordPresentation;
import pa.tools.clsPair;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:59:58
 * 
 */
@Deprecated
public class E31_NeuroDeSymbolization extends clsModuleBase implements I8_1_receive, I8_2_send, itfTimeChartInformationContainer  {

	private ArrayList<clsActionCommand> moActionCommandList_Output;
	private ArrayList<clsWordPresentation> moActionCommands_Input;
	
	private int mnCounter = 0;
	
	/**
	 * 
	 * 
	 * @author deutsch
	 * 11.08.2009, 15:00:18
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E31_NeuroDeSymbolization(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
		applyProperties(poPrefix, poProp);		
		
		moActionCommandList_Output = new ArrayList<clsActionCommand>();
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
		//process_oldDT();
		mnCounter++; 
		if( moActionCommands_Input.size() > 0 ) {
				for(clsWordPresentation oWP : moActionCommands_Input) {
				
					String oAction = oWP.getMoContent(); 
				
					if(oAction.equals("MOVE_FORWARD")){
						moActionCommandList_Output.add( new clsActionMove(eActionMoveDirection.MOVE_FORWARD,1.0) );
						//System.out.println("cmd: move_forward");
					}
					else if(oAction.equals("TURN_LEFT")){
						moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_LEFT, 1.0));
						//System.out.println("cmd: turn_left");
					}
					else if(oAction.equals("TURN_RIGHT")){
						moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_RIGHT, 1.0));
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
			
			if(mnCounter > 100){
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
		((I8_2_receive)moEnclosingContainer).receive_I8_2(moActionCommandList_Output);
		
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
	public ArrayList<clsPair<String, Double>> getTimeChartData() {
	ArrayList<clsPair<String, Double>> oRetVal = new ArrayList<clsPair<String, Double>>();
		
		oRetVal.add(new clsPair<String, Double>("TURN_RIGHT", 0.0)); 
		oRetVal.add(new clsPair<String, Double>("TURN_LEFT", 0.0));
		oRetVal.add(new clsPair<String, Double>("MOVE_FORWARD", 0.0));
		oRetVal.add(new clsPair<String, Double>("EAT", 0.0));
		oRetVal.add(new clsPair<String, Double>("SEEK", 0.0));
		
		for(clsPair<String, Double> oPair : oRetVal){
			if(moActionCommands_Input.size() > 0){
				
				if(oPair.a.equals(moActionCommands_Input.get(0).getMoContent())){
					oPair.b = 1.0; 
				}
			}
			else {
				if(oPair.a.equals("SEEK")){
					oPair.b = 1.0; 
				}
			}
		}
		
		return oRetVal; 
	}
}
