/**
 * E32_Actuators.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:00:44
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;
import config.clsBWProperties;
import du.itf.actions.clsActionCommand;
import du.itf.actions.itfActionProcessor;
import pa.interfaces._v30.eInterfaces;
import pa.interfaces.receive._v30.I8_2_receive;
import pa.interfaces.send._v30.I0_6_send;

/**
 * DOCUMENT (brandstaetter) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 15:00:44
 * 
 */
public class E32_Actuators extends clsModuleBase implements I8_2_receive, I0_6_send {
	public static final String P_MODULENUMBER = "32";
	
	private ArrayList<clsActionCommand> moOutputActions;
	private ArrayList<clsActionCommand> moActionCommandList_Input;
	
	/**
	 * DOCUMENT (brandstaetter) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 17:02:05
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E32_Actuators(String poPrefix, clsBWProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, HashMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
				
		moActionCommandList_Input = new ArrayList<clsActionCommand>();
		
		applyProperties(poPrefix, poProp);	
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
	 * 11.08.2009, 16:16:59
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		//do nothing
		
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
		send_I0_6(moActionCommandList_Input);
		
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
		// TODO (branstaetter) - Auto-generated method stub
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
		// TODO (branstaetter) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 17:02:13
	 * 
	 * @see pa.modules._v30.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}

	
	public void getOutput(itfActionProcessor poActionContainer) {
		for( clsActionCommand oCmd : moOutputActions ) {
			poActionContainer.call(oCmd);
		}
				
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 19:25:36
	 * 
	 * @see pa.interfaces.send._v30.I0_6_send#send_I0_6(java.util.ArrayList)
	 */
	@Override
	public void send_I0_6(ArrayList<clsActionCommand> poActionList) {
		moOutputActions = poActionList;
		putInterfaceData(I0_6_send.class, poActionList);
		
	}


}
