/**
 * E1_Homeostases.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 12:09:14
 */
package prementalapparatus.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import communication.datatypes.clsDataContainer;
import communication.datatypes.clsDataPoint;

import properties.clsProperties;

import modules.interfaces.I0_3_receive;
import modules.interfaces.I1_2_receive;
import modules.interfaces.I1_2_send;
import modules.interfaces.eInterfaces;
import base.modules.clsModuleBase;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;

/**
 * Sensor of Module {F1} are collecting information on bodily functions like metabolism, blood pressure, 
 * heart beat, respiration. Thus the current state of the body and its need is made available.
 * No real processing has to be made here as the information is actually collected 
 * in pa._v38.clsProcessor.separateHomeostaticData(clsSensorData) in every step of clsProcessor.<br><br>
 * 
 *  <b>INPUT:</b><br>
 *  moHomeostasis_IN holds a map of all homoestatic values sorted by eSensorIntType as key. (IN I0.3)<br>
 *  
 *  <b>OUTPUT:</b><br>
 *  moHomeostasis_OUT holds a map of all homoestatic values sorted by eSensorIntType as key. (OUT I1.2)<br>
 * 
 * @author muchitsch
 * 11.08.2009, 12:09:14
 * 
 */
public class F01_SensorsMetabolism extends clsModuleBase implements I0_3_receive, I1_2_send {
	public static final String P_MODULENUMBER = "01";
	
    /** holds a map of all homoestatic values sorted by eSensorIntType as key. (IN I0.3) @since 27.07.2011 13:09:03 */
    private clsDataContainer moHomeostasis_IN;
    /** holds a map of all homoestatic values sorted by eSensorIntType as key. (OUT I1.2) @since 27.07.2011 13:09:03 */
    private clsDataContainer moHomeostasis_OUT;
	
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	
	/**
	 * basic constructor
	 * 
	 * @author muchitsch
	 * 03.03.2011, 15:52:11
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception 
	 */
	public F01_SensorsMetabolism(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, int pnUid) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, pnUid);

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
		log.debug("\n\n\n===START OF PRIMARY PROCESS===");
        
		for (clsDataPoint item : moHomeostasis_IN.getData()) {
		    InfluxDB.sendInflux("F"+P_MODULENUMBER,item.getType(),item.getValue());
		}
	
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
	public void send_I1_2(clsDataContainer poData) {
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
	@Override
	public void receive_I0_3(clsDataContainer poData) {
		moHomeostasis_IN = poData; 
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
		
		html += toText.valueToTEXT("moHomeostasis_OUT", moHomeostasis_OUT);

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
		moDescription = "Sensor of Module {F1} are collecting information on bodily functions like metabolism, blood pressure, heart beat, respiration. Thus the current state of the body and its needs is made available.";
	}
}
