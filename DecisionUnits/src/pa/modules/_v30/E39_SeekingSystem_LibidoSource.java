/**
 * E39_SeekingSystem_LibidoSource.java: DecisionUnits - pa.modules._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:16:06
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pa.interfaces._v30.eInterfaces;
import pa.interfaces.receive._v30.I0_1_receive;
import pa.interfaces.receive._v30.I0_2_receive;
import pa.interfaces.receive._v30.I1_8_receive;
import pa.interfaces.send._v30.D1_3_send;
import pa.interfaces.send._v30.I1_8_send;
import pa.storage.clsLibidoBuffer;
import config.clsBWProperties;
import du.enums.eSensorIntType;
import du.itf.sensors.clsDataBase;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:16:06
 * 
 */
public class E39_SeekingSystem_LibidoSource extends clsModuleBase implements I0_1_receive, I0_2_receive, I1_8_send, D1_3_send {
	public static final String P_MODULENUMBER = "39";
	
	private clsLibidoBuffer moLibidoBuffer;
	private double mrTempLibido;
	
	/**
	 * DOCUMENT (wendt) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 15:42:22
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception 
	 */
	public E39_SeekingSystem_LibidoSource(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, HashMap<eInterfaces, ArrayList<Object>> poInterfaceData, clsLibidoBuffer poLibidoBuffer) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		
		moLibidoBuffer = poLibidoBuffer;
		mrTempLibido = 0;
		
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
		
		html += valueToHTML("moLibidoBuffer", moLibidoBuffer);
		html += valueToHTML("mrTempLibido", mrTempLibido);		
		
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
	
	private void updateTempLibido() {
		mrTempLibido = moLibidoBuffer.send_D1_2();
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:16:06
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		 updateTempLibido();
		 mrTempLibido -= 0.01;
		// TODO (wendt) - Auto-generated method stub
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:16:06
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		 updateTempLibido();		
		// TODO (wendt) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:16:06
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		 updateTempLibido();		
		// TODO (wendt) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:16:06
	 * 
	 * @see pa.modules._v30.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I1_8(new HashMap<eSensorIntType, clsDataBase>());
		send_D1_3(mrTempLibido);
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
	 * 03.03.2011, 15:38:54
	 * 
	 * @see pa.interfaces.send._v30.I1_8_send#send_I1_8(java.util.HashMap)
	 */
	@Override
	public void send_I1_8(HashMap<eSensorIntType, clsDataBase> poData) {
		((I1_8_receive)moModuleList.get(40)).receive_I1_8(poData);
		putInterfaceData(I1_8_send.class, poData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:38:54
	 * 
	 * @see pa.interfaces.receive._v30.I0_2_receive#receive_I0_2(java.util.List)
	 */
	@Override
	public void receive_I0_2(List<Object> poData) {
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:38:54
	 * 
	 * @see pa.interfaces.receive._v30.I0_1_receive#receive_I0_1(java.util.List)
	 */
	@Override
	public void receive_I0_1(List<Object> poData) {
		// TODO (wendt) - Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:32:35
	 * 
	 * @see pa.interfaces.send._v30.D1_3_send#send_D1_3(double)
	 */
	@Override
	public void send_D1_3(double prValue) {
		moLibidoBuffer.receive_D1_3(prValue);
		
		putInterfaceData(D1_3_send.class, prValue);
	}


}
