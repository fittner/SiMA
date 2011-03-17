/**
 * E41_Libidostasis.java: DecisionUnits - pa.modules._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:18:57
 */
package pa.modules._v30;

import java.util.HashMap;

import pa.interfaces.receive._v30.I1_10_receive;
import pa.interfaces.receive._v30.I1_9_receive;
import pa.interfaces.send._v30.D1_1_send;
import pa.interfaces.send._v30.I1_10_send;
import pa.storage.clsLibidoBuffer;

import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:18:57
 * 
 */
public class E41_Libidostasis extends clsModuleBase implements I1_9_receive, I1_10_send, D1_1_send {
	public static final String P_MODULENUMBER = "41";
	
	private clsLibidoBuffer moLibidoBuffer;
	private double mrTempLibido;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 15:47:47
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception 
	 */
	public E41_Libidostasis(String poPrefix, clsBWProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, clsLibidoBuffer poLibidoBuffer) throws Exception {
		super(poPrefix, poProp, poModuleList);
		
		moLibidoBuffer = poLibidoBuffer;
		mrTempLibido = 0;
		
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
	
	@Override
	protected void setProcessType() {mnProcessType = eProcessType.PRIMARY;}
	@Override
	protected void setPsychicInstances() {mnPsychicInstances = ePsychicInstances.ID;}
	@Override
	protected void setModuleNumber() {mnModuleNumber = Integer.parseInt(P_MODULENUMBER);}

	private void updateTempLibido() {
		mrTempLibido = moLibidoBuffer.send_D1_2();
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:19:22
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		 updateTempLibido();
		 mrTempLibido += 0.01; 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:19:22
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		 updateTempLibido();
		// TODO (deutsch) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:19:22
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		 updateTempLibido();
		// TODO (deutsch) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:19:22
	 * 
	 * @see pa.modules._v30.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I1_10(new HashMap<String, Double>());
		send_D1_1(mrTempLibido);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:49:01
	 * 
	 * @see pa.interfaces.send._v30.I1_10_send#receive_I1_10(java.util.HashMap)
	 */
	@Override
	public void send_I1_10(HashMap<String, Double> poHomeostasisSymbols) {
		((I1_10_receive)moModuleList.get(43)).receive_I1_10(poHomeostasisSymbols);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:49:01
	 * 
	 * @see pa.interfaces.receive._v30.I1_9_receive#receive_I1_9(java.util.HashMap)
	 */
	@Override
	public void receive_I1_9(HashMap<String, Double> poHomeostasisSymbols) {
		// TODO (deutsch) - Auto-generated method stub
		
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:25:10
	 * 
	 * @see pa.interfaces.send._v30.D1_1_send#send_D1_1(double)
	 */
	@Override
	public void send_D1_1(double prValue) {
		moLibidoBuffer.receive_D1_1(prValue);
	}

}
