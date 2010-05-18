/**
 * E7_SuperEgo_unconscious.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:03:35
 */
package pa.modules;

import java.util.ArrayList;
import java.util.List;

import pa.clsInterfaceHandler;
import pa.datatypes.clsPrimaryInformation;
import pa.interfaces.receive.I1_5_receive;
import pa.interfaces.receive.I2_9_receive;
import pa.interfaces.receive.I3_1_receive;
import pa.interfaces.receive.I3_2_receive;
import pa.interfaces.send.I3_1_send;
import pa.interfaces.send.I3_2_send;
import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:03:35
 * 
 */
public class E07_SuperEgo_unconscious extends clsModuleBase implements I1_5_receive, I2_9_receive, I3_1_send, I3_2_send {
	ArrayList<clsPrimaryInformation> moPrimaryInformation;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:03:53
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E07_SuperEgo_unconscious(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
		applyProperties(poPrefix, poProp);		
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		// String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		//nothing to do
				
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
		mnProcessType = eProcessType.PRIMARY;
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
		mnPsychicInstances = ePsychicInstances.SUPEREGO;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 13:46:50
	 * 
	 * @see pa.interfaces.I1_3#receive_I1_3(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I1_5(List<clsPrimaryInformation> poData) {
		moPrimaryInformation = (ArrayList<clsPrimaryInformation>)deepCopy( (ArrayList<clsPrimaryInformation>)poData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:05:13
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I2_9(ArrayList<clsPrimaryInformation> poMergedPrimaryInformation) {
		
		//DEEPCOPY
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:10
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process() {
		mnTest++;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:10
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I3_1(mnTest);
		send_I3_2(mnTest);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:50:09
	 * 
	 * @see pa.interfaces.send.I3_1_send#send_I3_1(int)
	 */
	@Override
	public void send_I3_1(int pnData) {
		((I3_1_receive)moEnclosingContainer).receive_I3_1(mnTest);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:50:09
	 * 
	 * @see pa.interfaces.send.I3_2_send#send_I3_2(int)
	 */
	@Override
	public void send_I3_2(int pnData) {
		((I3_2_receive)moEnclosingContainer).receive_I3_2(mnTest);
	}

}
