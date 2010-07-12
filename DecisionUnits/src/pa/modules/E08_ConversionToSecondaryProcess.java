/**
 * E8_ConversionToSecondaryProcess.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:11:38
 */
package pa.modules;

import java.util.ArrayList;

import config.clsBWProperties;
import pa.clsInterfaceHandler;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsPrimaryInformationMesh;
import pa.datatypes.clsSecondaryInformation;
import pa.datatypes.clsSecondaryInformationMesh;
import pa.interfaces.receive.I1_6_receive;
import pa.interfaces.receive.I1_7_receive;
import pa.interfaces.receive.I5_3_receive;
import pa.interfaces.send.I1_7_send;
import pa.interfaces.send.I5_3_send;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:11:38
 * 
 */
public class E08_ConversionToSecondaryProcess extends clsModuleBase implements I1_6_receive, I1_7_send, I5_3_send {

	public  ArrayList<clsPrimaryInformation> moDriveList_Input;
	public  ArrayList<clsSecondaryInformation> moDriveList_Output;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:11:58
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E08_ConversionToSecondaryProcess(String poPrefix,
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
	 * 11.08.2009, 14:12:33
	 * 
	 * @see pa.interfaces.I1_6#receive_I1_6(int)
	 */
	@Override
	public void receive_I1_6(ArrayList<clsPrimaryInformation> poDriveList) {
		moDriveList_Input = this.deepCopy(poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:14
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		
		moDriveList_Output = new ArrayList<clsSecondaryInformation>();
		for( clsPrimaryInformation oPriminfo : moDriveList_Input ) {
			
			if(oPriminfo instanceof clsPrimaryInformationMesh) {
				moDriveList_Output.add(new clsSecondaryInformationMesh(oPriminfo));
			}
			else if(oPriminfo instanceof clsPrimaryInformation) {
				moDriveList_Output.add(new clsSecondaryInformation(oPriminfo));
			}
			
		}
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:14
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I1_7(moDriveList_Output);
		send_I5_3(moDriveList_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:51:16
	 * 
	 * @see pa.interfaces.send.I1_7_send#send_I1_7(java.util.ArrayList)
	 */
	@Override
	public void send_I1_7(ArrayList<clsSecondaryInformation> poDriveList) {
		((I1_7_receive)moEnclosingContainer).receive_I1_7(moDriveList_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:51:16
	 * 
	 * @see pa.interfaces.send.I5_3_send#send_I5_3(java.util.ArrayList)
	 */
	@Override
	public void send_I5_3(ArrayList<clsSecondaryInformation> poDriveList) {
		((I5_3_receive)moEnclosingContainer).receive_I5_3(moDriveList_Output);	
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:45:47
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
	 * 12.07.2010, 10:45:47
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}
}
