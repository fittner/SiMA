/**
 * E29_EvaluationOfImaginaryActions.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:57:10
 */
package pa._v19.modules;

import java.util.ArrayList;

import config.clsProperties;
import pa._v19.clsInterfaceHandler;
import pa._v19.interfaces.receive.I5_5_receive;
import pa._v19.interfaces.receive.I7_3_receive;
import pa._v19.interfaces.receive.I7_4_receive;
import pa._v19.interfaces.receive.I7_6_receive;
import pa._v19.interfaces.send.I7_4_send;
import pa._v19.memorymgmt.datatypes.clsWordPresentation;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:57:10
 * 
 */
@Deprecated
public class E29_EvaluationOfImaginaryActions extends clsModuleBase implements I5_5_receive, I7_3_receive, I7_6_receive, I7_4_send {

	private ArrayList<clsWordPresentation> moActionCommands_Input; 
	private ArrayList<clsWordPresentation> moActionCommands_Output; 

	/**
	 * 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:57:36
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E29_EvaluationOfImaginaryActions(String poPrefix,
			clsProperties poProp, clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
		applyProperties(poPrefix, poProp);	
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
	 * 11.08.2009, 14:57:45
	 * 
	 * @see pa.interfaces.I5_5#receive_I5_5(int)
	 */
	@Override
	public void receive_I5_5(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:57:45
	 * 
	 * @see pa.interfaces.I7_3#receive_I7_3(int)
	 */
	@SuppressWarnings("unchecked") //deepCopy can only perform an unchecked operation
	@Override
	public void receive_I7_3(ArrayList<clsWordPresentation> poActionCommands) {
		moActionCommands_Input = (ArrayList<clsWordPresentation>)deepCopy(poActionCommands);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:46
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
	 * 11.08.2009, 16:16:46
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I7_4(moActionCommands_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 27.04.2010, 10:43:32
	 * 
	 * @see pa.interfaces.I7_6#receive_I7_6(java.util.ArrayList)
	 */
	@Override
	public void receive_I7_6(int pnData) {
		
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:58:21
	 * 
	 * @see pa.interfaces.send.I7_4_send#send_I7_4(java.util.ArrayList)
	 */
	@Override
	public void send_I7_4(ArrayList<clsWordPresentation> poActionCommands) {
		((I7_4_receive)moEnclosingContainer).receive_I7_4(moActionCommands_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:47:52
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
	 * 12.07.2010, 10:47:52
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		
		throw new java.lang.NoSuchMethodError();
	}
}
