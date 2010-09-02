/**
 * E23_ExternalPerception_focused.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:46:53
 */
package pa.modules;

import java.util.ArrayList;

import config.clsBWProperties;
import pa.clsInterfaceHandler;
import pa.datatypes.clsSecondaryInformation;
import pa.interfaces.receive.I1_7_receive;
import pa.interfaces.receive.I2_11_receive;
import pa.interfaces.receive.I2_12_receive;
import pa.interfaces.send.I2_12_send;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:46:53
 * 
 */
public class E23_ExternalPerception_focused extends clsModuleBase implements I2_11_receive, I1_7_receive, I2_12_send {

	private ArrayList<clsSecondaryInformation> moPerception_old;
	private ArrayList<clsSecondaryInformation> moDriveList_old;
	private ArrayList<clsSecondaryInformation> moFocusedPerception_Output_old;
	
	private ArrayList<clsSecondaryDataStructureContainer> moPerception; 
	private ArrayList<clsSecondaryDataStructureContainer> moDriveList; 
	private ArrayList<clsSecondaryDataStructureContainer> moFocusedPerception_Output; 
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:47:24
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E23_ExternalPerception_focused(String poPrefix,
			clsBWProperties poProp, clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
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
		mnProcessType = eProcessType.SECONDARY;
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
	 * 11.08.2009, 14:47:49
	 * 
	 * @see pa.interfaces.I2_11#receive_I2_11(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_11(ArrayList<clsSecondaryInformation> poPerception_old, ArrayList<clsSecondaryDataStructureContainer> poPerception) {
		moPerception_old = (ArrayList<clsSecondaryInformation>)this.deepCopy(poPerception_old);
		moPerception = (ArrayList<clsSecondaryDataStructureContainer>)this.deepCopy(poPerception);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:47:49
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I1_7(ArrayList<clsSecondaryInformation> poDriveList_old, ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		moDriveList_old = (ArrayList<clsSecondaryInformation>)this.deepCopy(poDriveList_old);
		moDriveList = (ArrayList<clsSecondaryDataStructureContainer>)this.deepCopy(poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:20
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		//TODO HZ 23.08.2010: Normally the perceived information has to be ordered by its priority
		//that depends on the evaluation of external and internal perception (moDriveList); 
		//
		//Actual state: no ordering! 
		moFocusedPerception_Output = moPerception;
		process_oldDT();  
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 * This method is used while adapting the model from the old datatypes (pa.datatypes) to the
	 * new ones (pa.memorymgmt.datatypes) The method has to be deleted afterwards.
	 * @author zeilinger
	 * 13.08.2010, 09:56:48
	 * @deprecated
	 */
	private void process_oldDT() {
		moFocusedPerception_Output_old = moPerception_old; //simply forward. really? cm
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:20
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		//HZ: null is a placeholder for the bjects of the type pa.memorymgmt.datatypes
		send_I2_12(moFocusedPerception_Output_old, moFocusedPerception_Output, moDriveList_old, moDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:50:35
	 * 
	 * @see pa.interfaces.send.I2_12_send#send_I2_12(java.util.ArrayList)
	 */
	@Override
	public void send_I2_12(ArrayList<clsSecondaryInformation> poFocusedPerception_old,
			   			ArrayList<clsSecondaryDataStructureContainer> poFocusedPerception,
			   			ArrayList<clsSecondaryInformation> poDriveList_old,
			   			ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		((I2_12_receive)moEnclosingContainer).receive_I2_12(moFocusedPerception_Output_old, moFocusedPerception_Output, moDriveList_old, moDriveList);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:47:20
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
	 * 12.07.2010, 10:47:20
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}
}
