/**
 * E40_NeurosymbolizationOfLibido.java: DecisionUnits - pa.modules._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:18:01
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;

import pa.interfaces._v30.eInterfaces;
import pa.interfaces.receive._v30.I1_8_receive;
import pa.interfaces.receive._v30.I1_9_receive;
import pa.interfaces.send._v30.I1_9_send;

import config.clsBWProperties;
import du.enums.eSensorIntType;
import du.itf.sensors.clsDataBase;

/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:18:01
 * 
 */
public class E40_NeurosymbolizationOfLibido extends clsModuleBase implements I1_8_receive, I1_9_send {
	public static final String P_MODULENUMBER = "40";
		
	/**
	 * DOCUMENT (muchitsch) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 17:52:37
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E40_NeurosymbolizationOfLibido(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, HashMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);	
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
		String html ="";
		
		html += "n/a";		
		
		return html;
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
	@Override
	protected void setProcessType() {mnProcessType = eProcessType.BODY;}
	@Override
	protected void setPsychicInstances() {mnPsychicInstances = ePsychicInstances.BODY;}
	@Override
	protected void setModuleNumber() {mnModuleNumber = Integer.parseInt(P_MODULENUMBER);}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:18:01
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		// TODO (muchitsch) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:18:01
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (muchitsch) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:18:01
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (muchitsch) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:18:01
	 * 
	 * @see pa.modules._v30.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I1_9(new HashMap<String, Double>());

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:47:14
	 * 
	 * @see pa.interfaces.send._v30.I1_9_send#send_I1_9(java.util.HashMap)
	 */
	@Override
	public void send_I1_9(HashMap<String, Double> poHomeostasisSymbols) {
		((I1_9_receive)moModuleList.get(41)).receive_I1_9(poHomeostasisSymbols);
		putInterfaceData(I1_9_send.class, poHomeostasisSymbols);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:47:14
	 * 
	 * @see pa.interfaces.receive._v30.I1_8_receive#receive_I1_8(java.util.HashMap)
	 */
	@Override
	public void receive_I1_8(HashMap<eSensorIntType, clsDataBase> pnData) {
		// TODO (muchitsch) - Auto-generated method stub
		
	}
}
