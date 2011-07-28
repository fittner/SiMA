/**
 * E32_Actuators.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:00:44
 */
package pa._v30.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsProperties;
import du.itf.actions.clsActionCommand;
import du.itf.actions.itfActionProcessor;
import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.modules.I0_6_send;
import pa._v30.interfaces.modules.I8_2_receive;
import pa._v30.tools.toText;

/**
 *
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
	 *
	 * 
	 * @author deutsch
	 * 03.03.2011, 17:02:05
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E32_Actuators(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
				
		moActionCommandList_Input = new ArrayList<clsActionCommand>();
		
		applyProperties(poPrefix, poProp);	
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v30.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.listToTEXT("moOutputActions", moOutputActions);
		text += toText.listToTEXT("moActionCommandList_Input", moActionCommandList_Input);
		
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

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v30.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "How the body executes action commands is defined in this module. Various motor controls are operated from here.";
	}	
}
