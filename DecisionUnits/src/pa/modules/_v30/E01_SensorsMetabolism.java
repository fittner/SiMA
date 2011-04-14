/**
 * E1_Homeostases.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 12:09:14
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;
import pa.interfaces._v30.eInterfaces;
import pa.interfaces.receive._v30.I0_3_receive;
import pa.interfaces.receive._v30.I1_1_receive;
import pa.interfaces.send._v30.I1_1_send;
import config.clsBWProperties;
import du.enums.eSensorIntType;
import du.itf.sensors.clsDataBase;

/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 12:09:14
 * 
 */
public class E01_SensorsMetabolism extends clsModuleBase implements I0_3_receive, I1_1_send {
	public static final String P_MODULENUMBER = "01";
	
	private HashMap<eSensorIntType, clsDataBase> moHomeostasis;
	
	/**
	 * DOCUMENT (muchitsch) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 15:52:11
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception 
	 */
	public E01_SensorsMetabolism(String poPrefix, clsBWProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, HashMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
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
		//add necessary preprocessing here
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
		send_I1_1(moHomeostasis);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:42:45
	 * 
	 * @see pa.interfaces.send.I1_1_send#send_I1_1(java.util.HashMap)
	 */
	@Override
	public void send_I1_1(HashMap<eSensorIntType, clsDataBase> poData) {
		((I1_1_receive)moModuleList.get(2)).receive_I1_1(poData);
		
		putInterfaceData(I1_1_send.class, poData);
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
	 * @see pa.modules._v30.clsModuleBase#setModuleNumber()
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
	 * @see pa.interfaces.receive._v30.I0_3_receive#receive_I0_3(java.util.List)
	 */
	@Override
	public void receive_I0_3(HashMap<eSensorIntType, clsDataBase> poData) {
		moHomeostasis = poData;
		
		putInterfaceData(I0_3_receive.class, poData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v30.clsModuleBase#stateToHTML()
	 */
	@Override
	public String stateToHTML() {		
		String html = "";
		
		html += mapToHTML("moHomeostasis", moHomeostasis);

		return html;
	}
}
