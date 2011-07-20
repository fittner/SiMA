/**
 * E1_Homeostases.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 12:09:14
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.modules.I0_3_receive;
import pa._v38.interfaces.modules.I1_2_receive;
import pa._v38.interfaces.modules.I1_2_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.tools.toText;
import config.clsBWProperties;
import du.enums.eSensorIntType;
import du.itf.sensors.clsDataBase;


/**
 * Sensor of Module {F1} are collecting information on bodily functions like metabolism, blood pressure, 
 * heart beat, respiration. Thus the current state of the body and its needs is made available.
 * 
 * @author muchitsch
 * 11.08.2009, 12:09:14
 * 
 */
public class F01_SensorsMetabolism extends clsModuleBase implements I0_3_receive, I1_2_send {
	public static final String P_MODULENUMBER = "01";
	
	private HashMap<eSensorIntType, clsDataBase> moHomeostasis_IN;
	private HashMap<eSensorIntType, clsDataBase> moHomeostasis_OUT;
	
	/**
	 * basic CTOR
	 * 
	 * @author muchitsch
	 * 03.03.2011, 15:52:11
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception 
	 */
	public F01_SensorsMetabolism(String poPrefix, clsBWProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);

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
	 * 11.08.2009, 16:04:44
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		moHomeostasis_OUT = moHomeostasis_IN;
		
		putInterfaceData(I0_3_receive.class, moHomeostasis_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:04:44
	 * 
	 * @see pa.modules.clsModuleBase#transmit()
	 */
	@Override
	protected void send() {
		send_I1_2(moHomeostasis_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:42:45
	 * 
	 * @see pa.interfaces.send.I1_1_send#send_I1_1(java.util.HashMap)
	 */
	@Override
	public void send_I1_2(HashMap<eSensorIntType, clsDataBase> poData) {
		((I1_2_receive)moModuleList.get(2)).receive_I1_2(poData);
		
		putInterfaceData(I1_2_send.class, poData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:41:32
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
	 * 12.07.2010, 10:41:32
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
	 * 03.03.2011, 15:52:35
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
	 * 03.03.2011, 15:53:14
	 * 
	 * @see pa.interfaces.receive._v38.I0_3_receive#receive_I0_3(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I0_3(HashMap<eSensorIntType, clsDataBase> poData) {
		moHomeostasis_IN = (HashMap<eSensorIntType, clsDataBase>) deepCopy(poData); 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToHTML()
	 */
	@Override
	public String stateToTEXT() {		
		String html = "";
		
		html += toText.mapToTEXT("moHomeostasis_OUT", moHomeostasis_OUT);

		return html;
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
		moDescription = "Sensor of Module {E1} are collecting information on bodily functions like metabolism, blood pressure, heart beat, respiration. Thus the current state of the body and its needs is made available.";
	}
}
