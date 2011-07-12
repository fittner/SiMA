/**
 * E32_Actuators.java: DecisionUnits - pa.modules
 * 
 * @author brandstaetter
 * 11.08.2009, 15:00:44
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsBWProperties;
import du.itf.actions.clsActionCommand;
import du.itf.actions.itfActionProcessor;
import pa._v38.interfaces.modules.I0_6_send;
import pa._v38.interfaces.modules.I1_5_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.tools.toText;

/**
 * DOCUMENT (brandstaetter) - insert description 
 * 
 * @author brandstaetter
 * 11.08.2009, 15:00:44
 * 
 */
public class F32_Actuators extends clsModuleBase implements I1_5_receive, I0_6_send {
	public static final String P_MODULENUMBER = "32";
	
	private ArrayList<clsActionCommand> moOutputActions;
	private ArrayList<clsActionCommand> moActionCommandList_Input;
	
	/**
	 * DOCUMENT (brandstaetter) - insert description 
	 * 
	 * @author brandstaetter
	 * 03.03.2011, 17:02:05
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F32_Actuators(String poPrefix, clsBWProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
				
		moActionCommandList_Input = new ArrayList<clsActionCommand>();
		
		applyProperties(poPrefix, poProp);	
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
		
		text += toText.listToTEXT("moOutputActions", moOutputActions);
		text += toText.listToTEXT("moActionCommandList_Input", moActionCommandList_Input);
		
		return text;
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
	 * 11.08.2009, 15:01:51
	 * 
	 * @see pa.interfaces.I8_2#receive_I8_2(int)
	 */
	@SuppressWarnings("unchecked") //deepCopy can only perform an unchecked operation
	@Override
	public void receive_I1_5(ArrayList<clsActionCommand> poActionCommandList) {
		moActionCommandList_Input = (ArrayList<clsActionCommand>) deepCopy(poActionCommandList);
		
	}


	/* (non-Javadoc)
	 *
	 * @author brandstaetter
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
	 * @author brandstaetter
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
	 * @author brandstaetter
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
	 * @author brandstaetter
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
	 * @author brandstaetter
	 * 03.03.2011, 17:02:13
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
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
	 * @author brandstaetter
	 * 03.03.2011, 19:25:36
	 * 
	 * @see pa.interfaces.send._v38.I0_6_send#send_I0_6(java.util.ArrayList)
	 */
	@Override
	public void send_I0_6(ArrayList<clsActionCommand> poActionList) {
		moOutputActions = poActionList;

		putInterfaceData(I0_6_send.class, poActionList);
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
		moDescription = "How the body executes action commands is defined in this module. Various motor controls are operated from here.";
	}	
}
