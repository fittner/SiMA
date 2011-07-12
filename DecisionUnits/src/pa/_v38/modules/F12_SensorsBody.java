/**
 * E12_SensorsBody.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:20:47
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsBWProperties;
import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;
import pa._v38.interfaces.eInterfaces;
import pa._v38.interfaces.modules.I0_5_receive;
import pa._v38.interfaces.modules.I1_4_receive;
import pa._v38.interfaces.modules.I1_4_send;
import pa._v38.tools.toText;

/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:20:47
 * 
 */
public class F12_SensorsBody extends clsModuleBase implements I0_5_receive, I1_4_send {
	public static final String P_MODULENUMBER = "12";
	
	private HashMap<eSensorExtType, clsSensorExtern> moBodyData_IN;
	private HashMap<eSensorExtType, clsSensorExtern> moBodyData_OUT;

	/**
	 * DOCUMENT (muchitsch) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:08:23
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F12_SensorsBody(String poPrefix, clsBWProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);	
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {		
		String text = "";
		
		text += toText.mapToTEXT("moBodyData_IN", moBodyData_IN);
		text += toText.mapToTEXT("moBodyData_OUT", moBodyData_OUT);

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
	 * 11.08.2009, 16:15:32
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		
		moBodyData_OUT = moBodyData_IN;
		putInterfaceData(I0_5_receive.class, moBodyData_OUT);		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:32
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I1_4(moBodyData_OUT);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:54:43
	 * 
	 * @see pa.interfaces.send.I2_3_send#send_I2_3(java.util.HashMap)
	 */
	@Override
	public void send_I1_4(HashMap<eSensorExtType, clsSensorExtern> poData) {
		((I1_4_receive)moModuleList.get(13)).receive_I1_4(poData);
		putInterfaceData(I1_4_send.class, poData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:19
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (muchitsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:19
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (muchitsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:08:30
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:09:24
	 * 
	 * @see pa.interfaces.receive._v38.I0_5_receive#receive_I0_5(java.util.HashMap)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I0_5(HashMap<eSensorExtType, clsSensorExtern> poData) {
		
		moBodyData_IN = (HashMap<eSensorExtType, clsSensorExtern>) deepCopy(poData); 
		
		//putInterfaceData(I0_5_receive.class, poData);		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "Although, modules {E39} and {E1} are collecting information on internal body values too, {E12} focuses on sensors comparable to the one from Module {E10} but which are directed inwardly. Thus, the sensors detect painful stimuli, tactile stimuli, balance and acceleration, body temperature and others.";
	}	
}
