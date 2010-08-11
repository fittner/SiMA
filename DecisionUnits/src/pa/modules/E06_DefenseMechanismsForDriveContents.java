/**
 * E6_DefenseMechanismsForDriveContents.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:01:06
 */
package pa.modules;

import java.util.ArrayList;
import java.util.List;

import pa.clsInterfaceHandler;
import pa.datatypes.clsAffectTension;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsThingPresentation;
import pa.interfaces.receive.I1_5_receive;
import pa.interfaces.receive.I1_6_receive;
import pa.interfaces.receive.I3_1_receive;
import pa.interfaces.receive.I4_1_receive;
import pa.interfaces.receive.I4_3_receive;
import pa.interfaces.receive.I5_1_receive;
import pa.interfaces.receive.I6_3_receive;
import pa.interfaces.send.I1_6_send;
import pa.interfaces.send.I4_1_send;
import pa.interfaces.send.I5_1_send;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:01:06
 * 
 */
public class E06_DefenseMechanismsForDriveContents extends clsModuleBase implements I1_5_receive, I3_1_receive, I4_3_receive, I6_3_receive, I1_6_send, I4_1_send, I5_1_send {
	ArrayList<clsPrimaryDataStructureContainer> moDriveList_Input;
	ArrayList<clsPrimaryDataStructureContainer> moDriveList_Output;
	ArrayList<clsPrimaryDataStructureContainer> moRepressedRetry_Input;
	
	ArrayList<clsPrimaryInformation> moRepressedRetry_Input_old; 
	ArrayList<clsPrimaryInformation> moDriveList_Input_old;
	ArrayList<clsPrimaryInformation> moDriveList_Output_old;
	ArrayList<clsThingPresentation> moDeniedThingPresentations_old; 
	ArrayList<clsAffectTension> moDeniedAffects_old;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:01:31
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E06_DefenseMechanismsForDriveContents(String poPrefix,
			clsBWProperties poProp, clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
		
		moDriveList_Input_old = new ArrayList<clsPrimaryInformation>();
		moDriveList_Output_old = new ArrayList<clsPrimaryInformation>();
		moDeniedThingPresentations_old = new ArrayList<clsThingPresentation>();
		moDeniedAffects_old = new ArrayList<clsAffectTension>();
		
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
	 * 11.08.2009, 13:46:50
	 * 
	 * @see pa.interfaces.I1_3#receive_I1_3(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I1_5(List<clsPrimaryInformation> poData_old, List<clsPrimaryDataStructureContainer> poData) {
		moDriveList_Input_old = (ArrayList<clsPrimaryInformation>)deepCopy( (ArrayList<clsPrimaryInformation>)poData_old);
		moDriveList_Input = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy( (ArrayList<clsPrimaryDataStructureContainer>)poData);
	}

	/* Input from Super-Ego = E7
	 *
	 * @author deutsch
	 * 11.08.2009, 14:07:30
	 * 
	 * @see pa.interfaces.I3_1#receive_I3_1(int)
	 */
	@Override
	public void receive_I3_1(int pnData) {
		mnTest += pnData;
		
	}

	/* Input from Repressed Content E15
	 *
	 * @author deutsch
	 * 11.08.2009, 14:07:30
	 * 
	 * @see pa.interfaces.I4_3#receive_I4_3(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I4_3(List<clsPrimaryInformation> poPIs_old, List<clsPrimaryDataStructureContainer> poPIs) {
		moRepressedRetry_Input_old = (ArrayList<clsPrimaryInformation>)deepCopy( (ArrayList<clsPrimaryInformation>)poPIs_old);
		moRepressedRetry_Input = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy( (ArrayList<clsPrimaryDataStructureContainer>)poPIs);
	}

	/* Input from Knowledge about Reality E9
	 *
	 * @author deutsch
	 * 11.08.2009, 14:07:36
	 * 
	 * @see pa.interfaces.I6_3#receive_I6_3(int)
	 */
	@Override
	public void receive_I6_3(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:00
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		moDriveList_Output_old = moDriveList_Input_old; //pass everything through (deepCopy is called in the next module)
		moDriveList_Output = moDriveList_Input; 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:00
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		//HZ: null is a placeholder for the homeostatic information formed out of objects of the type pa.memorymgmt.datatypes 
		send_I1_6(moDriveList_Output_old, moDriveList_Output);
		send_I4_1(moDriveList_Input_old, moDeniedThingPresentations_old, moDeniedAffects_old, new ArrayList<clsPrimaryDataStructureContainer>(), new ArrayList<pa.memorymgmt.datatypes.clsThingPresentation>(),new ArrayList<clsAssociationDriveMesh>());
		send_I5_1(moDeniedAffects_old, new ArrayList<clsPrimaryDataStructureContainer>());
		
		//FIXME (langr) - moPrimaryInformation.clear();
		moDeniedThingPresentations_old.clear();
		moDeniedAffects_old.clear();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:48:13
	 * 
	 * @see pa.interfaces.send.I1_6_send#send_I1_6(java.util.ArrayList)
	 */
	@Override
	public void send_I1_6(ArrayList<clsPrimaryInformation> poDriveList_old, ArrayList<clsPrimaryDataStructureContainer> poDriveList) {
		((I1_6_receive)moEnclosingContainer).receive_I1_6(moDriveList_Output_old, moDriveList_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:48:13
	 * 
	 * @see pa.interfaces.send.I4_1_send#send_I4_1(java.util.List, java.util.List, java.util.List)
	 */
	@Override
	public void send_I4_1(List<clsPrimaryInformation> poPIs_old, List<clsThingPresentation> poTPs_old, List<clsAffectTension> poAffects_old,
			  			List<clsPrimaryDataStructureContainer> poPIs, List<pa.memorymgmt.datatypes.clsThingPresentation> poTPs, List<clsAssociationDriveMesh> poAffects) {
		((I4_1_receive)moEnclosingContainer).receive_I4_1(moDriveList_Input_old, moDeniedThingPresentations_old, moDeniedAffects_old, new ArrayList<clsPrimaryDataStructureContainer>(), new ArrayList<pa.memorymgmt.datatypes.clsThingPresentation>(),new ArrayList<clsAssociationDriveMesh>());
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:48:13
	 * 
	 * @see pa.interfaces.send.I5_1_send#send_I5_1(java.util.ArrayList)
	 */
	@Override
	public void send_I5_1(ArrayList<clsAffectTension> poAffectOnlyList_old, ArrayList<clsPrimaryDataStructureContainer> poAffectOnlyList) {
		((I5_1_receive)moEnclosingContainer).receive_I5_1(moDeniedAffects_old, new ArrayList<clsPrimaryDataStructureContainer>());	
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:45:33
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
	 * 12.07.2010, 10:45:33
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}
}
