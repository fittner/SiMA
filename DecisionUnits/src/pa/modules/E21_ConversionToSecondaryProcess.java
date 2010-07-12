/**
 * E21_ConversionToSecondaryProcess.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:38:29
 */
package pa.modules;

import java.util.ArrayList;

import config.clsBWProperties;
import pa.clsInterfaceHandler;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsPrimaryInformationMesh;
import pa.datatypes.clsSecondaryInformation;
import pa.datatypes.clsSecondaryInformationMesh;
import pa.interfaces.receive.I2_10_receive;
import pa.interfaces.receive.I2_11_receive;
import pa.interfaces.receive.I5_4_receive;
import pa.interfaces.send.I2_11_send;
import pa.interfaces.send.I5_4_send;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:38:29
 * 
 */
public class E21_ConversionToSecondaryProcess extends clsModuleBase implements I2_10_receive, I2_11_send, I5_4_send {

	private ArrayList<clsPrimaryInformation> moGrantedPerception_Input;
	private ArrayList<clsSecondaryInformation> moPerception_Output;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:38:50
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E21_ConversionToSecondaryProcess(String poPrefix,
			clsBWProperties poProp, clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
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
		mnPsychicInstances = ePsychicInstances.EGO;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:39:18
	 * 
	 * @see pa.interfaces.I2_10#receive_I2_10(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_10(ArrayList<clsPrimaryInformation> poGrantedPerception) {
		moGrantedPerception_Input = (ArrayList<clsPrimaryInformation>)this.deepCopy(poGrantedPerception);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:12
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		moPerception_Output = new ArrayList<clsSecondaryInformation>();
		for( clsPrimaryInformation oPriminfo : moGrantedPerception_Input ) {

			if(oPriminfo instanceof clsPrimaryInformationMesh) {
				moPerception_Output.add(new clsSecondaryInformationMesh(oPriminfo));
			}
			else if(oPriminfo instanceof clsPrimaryInformation) {
				moPerception_Output.add(new clsSecondaryInformation(oPriminfo));
			}
		}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:12
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_11(moPerception_Output);
		send_I5_4(moPerception_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:48:18
	 * 
	 * @see pa.interfaces.send.I2_11_send#send_I2_11(java.util.ArrayList)
	 */
	@Override
	public void send_I2_11(ArrayList<clsSecondaryInformation> poPerception) {
		((I2_11_receive)moEnclosingContainer).receive_I2_11(moPerception_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:48:18
	 * 
	 * @see pa.interfaces.send.I5_4_send#send_I5_4(java.util.ArrayList)
	 */
	@Override
	public void send_I5_4(ArrayList<clsSecondaryInformation> poPerception) {
		((I5_4_receive)moEnclosingContainer).receive_I5_4(moPerception_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:47:07
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:47:07
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

}
