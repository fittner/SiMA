/**
 * E32_Actuators.java: DecisionUnits - pa.modules
 * 
 * @author brandstaetter
 * 11.08.2009, 15:00:44
 */
package prementalapparatus.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import modules.interfaces.I0_6_send;
import modules.interfaces.I1_5_receive;
import modules.interfaces.eInterfaces;
import base.datatypes.clsWordPresentationMesh;
import base.modules.clsModuleBase;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;
import config.clsProperties;
import du.itf.actions.clsActionCommand;
import du.itf.actions.itfActionProcessor;

/**
 * How the body executes action commands is defined in this module. Various motor controls are operated from here. 
 * 
 * @author brandstaetter
 * 11.08.2009, 15:00:44
 * 
 */
public class F32_Actuators extends clsModuleBase implements I1_5_receive, I0_6_send {
	public static final String P_MODULENUMBER = "32";
	
	private ArrayList<clsActionCommand> moOutputActions;
	private ArrayList<clsActionCommand> moActionCommandList_Input;
	private clsWordPresentationMesh moWordingToContext;
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	
	/**
	 * Constructor of Actuators
	 * 
	 * @author brandstaetter
	 * 03.03.2011, 17:02:05
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F32_Actuators(String poPrefix, clsProperties poProp,
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
	 * 11.08.2009, 15:01:51
	 * 
	 * @see pa.interfaces.I8_2#receive_I8_2(int)
	 */
	@SuppressWarnings("unchecked") //deepCopy can only perform an unchecked operation
	@Override
	public void receive_I1_5(ArrayList<clsActionCommand> poActionCommandList, clsWordPresentationMesh moWordingToContext2) {
		moActionCommandList_Input = (ArrayList<clsActionCommand>) deepCopy(poActionCommandList);
		moWordingToContext = moWordingToContext2;
		
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
		send_I0_6(moActionCommandList_Input, moWordingToContext);
		
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
	public void send_I0_6(ArrayList<clsActionCommand> poActionList, clsWordPresentationMesh moWordingToContext2) {
		moOutputActions = poActionList;
		moWordingToContext = moWordingToContext2;

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