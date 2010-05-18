/**
 * E31_NeuroDeSymbolization.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:59:58
 */
package pa.modules;

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
import pa.clsInterfaceHandler;
import pa.interfaces.receive.I8_1_receive;
import pa.interfaces.receive.I8_2_receive;
import pa.interfaces.send.I8_2_send;
import pa.loader.plan.clsPlanAction;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:59:58
 * 
 */
public class E31_NeuroDeSymbolization extends clsModuleBase implements I8_1_receive, I8_2_send {

	private ArrayList<clsPlanAction> moActionCommands_Input;
	private ArrayList<clsActionCommand> moActionCommandList_Output;

	private int mnCounter = 0;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
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
		// String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		//nothing to do
				
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
	public void receive_I8_1(ArrayList<clsPlanAction> poActionCommands) {
		moActionCommands_Input = (ArrayList<clsPlanAction>)deepCopy(poActionCommands);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:54
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process() {
		
		moActionCommandList_Output.clear();
		
		if( moActionCommands_Input.size() > 0 ) {
			
		for(clsPlanAction oAction : moActionCommands_Input) {
			
			if(oAction.moWP.moContent.equals("MOVE_FORWARD")) {
				
				moActionCommandList_Output.add( new clsActionMove(eActionMoveDirection.MOVE_FORWARD,1.0) );
				//System.out.println("cmd: move_forward");
			}
			//added by SK
			else if (oAction.moWP.moContent.equals("TURN_LEFT"))
			{
				moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_LEFT, 1.0));
				//System.out.println("cmd: turn_left");
			}
			else if (oAction.moWP.moContent.equals("TURN_RIGHT"))
			{
				moActionCommandList_Output.add(new clsActionTurn(eActionTurnDirection.TURN_RIGHT, 1.0));
				//System.out.println("cmd: turn_right");
			}
			//end add
			else if(oAction.moWP.moContent.equals("PICKUP")) {
				
				moActionCommandList_Output.add( new clsActionPickUp() );
				//System.out.println("cmd: pickup");
			}
			else if(oAction.moWP.moContent.equals("DROP")) {
				
				moActionCommandList_Output.add( new clsActionDrop() );
				//System.out.println("cmd: drop");
				
			}
			else if(oAction.moWP.moContent.equals("EAT")) {
				
				moActionCommandList_Output.add( new clsActionEat() );
				//System.out.println("cmd: eat");
				
			}
			else if(oAction.moWP.moContent.equals("DANCE_1")) {
				
				moActionCommandList_Output.add( clsActionSequenceFactory.getWalzSequence(2,2) );
				//System.out.println("cmd: dance");
				
			}
		}
		}
		else {
			if( mnCounter > 50) {
				moActionCommandList_Output.add( clsActionSequenceFactory.getSeekingSequence(1f,2) );
				mnCounter = 0;
			}
			mnCounter++;
			//System.out.println("cmd: seek");
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

}
