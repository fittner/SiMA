/**
 * E32_Actuators.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:00:44
 */
package pa.modules;

import java.util.ArrayList;

import config.clsBWProperties;
import du.itf.actions.clsActionCommand;
import du.itf.actions.itfActionProcessor;
import pa.clsInterfaceHandler;
import pa.interfaces.itfReturnActionCommands;
import pa.interfaces.receive.I8_2_receive;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 15:00:44
 * 
 */
public class E32_Actuators extends clsModuleBase implements I8_2_receive, itfReturnActionCommands {

	private ArrayList<clsActionCommand> moActionCommandList_Input;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 15:01:43
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E32_Actuators(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
		applyProperties(poPrefix, poProp);		
		
		moActionCommandList_Input = new ArrayList<clsActionCommand>();
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
	 * 11.08.2009, 15:01:51
	 * 
	 * @see pa.interfaces.I8_2#receive_I8_2(int)
	 */
	@SuppressWarnings("unchecked") //deepCopy can only perform an unchecked operation
	@Override
	public void receive_I8_2(ArrayList<clsActionCommand> poActionCommandList) {
		moActionCommandList_Input = (ArrayList<clsActionCommand>) deepCopy(poActionCommandList);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 15:03:00
	 * 
	 * @see pa.interfaces.itfReturnActionCommands#getActionCommands()
	 */
	@Override
	public void getActionCommands(itfActionProcessor poActionContainer) {

		for( clsActionCommand oCmd : moActionCommandList_Input ) {
			poActionContainer.call(oCmd);
		}
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:59
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		mnTest++;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:59
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		// nothing to do
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:48:12
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:48:12
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

}
