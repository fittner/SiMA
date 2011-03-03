/**
 * E30_MotilityControl.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:58:20
 */
package pa.modules._v19;

import java.util.ArrayList;

import config.clsBWProperties;
import pa._v19.clsInterfaceHandler;
import pa.interfaces.receive._v19.I7_4_receive;
import pa.interfaces.receive._v19.I8_1_receive;
import pa.interfaces.send._v19.I8_1_send;
import pa.memorymgmt.datatypes.clsWordPresentation;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:58:20
 * 
 */
public class E30_MotilityControl extends clsModuleBase implements I7_4_receive, I8_1_send {

	private ArrayList<clsWordPresentation> moActionCommands_Input;
	private ArrayList<clsWordPresentation> moActionCommands_Output;

	/**
	 * 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:58:37
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E30_MotilityControl(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
		applyProperties(poPrefix, poProp);	
		
		moActionCommands_Output = new ArrayList<clsWordPresentation>(); 
	}
	
	/**
	 * @author zeilinger
	 * 02.09.2010, 20:10:34
	 * 
	 * @return the moActionCommands_Output
	 */
	public ArrayList<clsWordPresentation> getActionCommands_Output() {
		return moActionCommands_Output;
	}
	
	/**
	 * @author zeilinger
	 * 02.09.2010, 20:10:34
	 * 
	 * @return the moActionCommands_Input
	 */
	public ArrayList<clsWordPresentation> getActionCommands_Input() {
		return moActionCommands_Input;
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
		mnProcessType = eProcessType.SECONDARY;
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
		mnPsychicInstances = ePsychicInstances.EGO;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:58:46
	 * 
	 * @see pa.interfaces.I7_4#receive_I7_4(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I7_4(ArrayList<clsWordPresentation> poActionCommands) {
		moActionCommands_Input = (ArrayList<clsWordPresentation>) deepCopy(poActionCommands); 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:50
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		moActionCommands_Output = moActionCommands_Input; 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:50
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I8_1(moActionCommands_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:59:05
	 * 
	 * @see pa.interfaces.send.I8_1_send#send_I8_1(java.util.ArrayList)
	 */
	@Override
	public void send_I8_1(ArrayList<clsWordPresentation> poActionCommands) {
		((I8_1_receive)moEnclosingContainer).receive_I8_1(moActionCommands_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
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
	 * @author deutsch
	 * 12.07.2010, 10:47:57
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		
		throw new java.lang.NoSuchMethodError();
	}
}
