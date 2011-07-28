/**
 * E6_DefenseMechanismsForDriveContents.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:01:06
 */
package pa._v19.modules;

import java.util.ArrayList;
import java.util.List;

import pa._v19.clsInterfaceHandler;
import pa._v19.interfaces.receive.I1_5_receive;
import pa._v19.interfaces.receive.I1_6_receive;
import pa._v19.interfaces.receive.I3_1_receive;
import pa._v19.interfaces.receive.I4_1_receive;
import pa._v19.interfaces.receive.I4_3_receive;
import pa._v19.interfaces.receive.I5_1_receive;
import pa._v19.interfaces.receive.I6_3_receive;
import pa._v19.interfaces.send.I1_6_send;
import pa._v19.interfaces.send.I4_1_send;
import pa._v19.interfaces.send.I5_1_send;
import pa._v19.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v19.memorymgmt.datatypes.clsDriveMesh;
import pa._v19.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import config.clsProperties;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:01:06
 * 
 */
@Deprecated
public class E06_DefenseMechanismsForDriveContents extends clsModuleBase implements I1_5_receive, I3_1_receive, I4_3_receive, I6_3_receive, I1_6_send, I4_1_send, I5_1_send {
	ArrayList<clsDriveMesh> moDriveList_Input;
	ArrayList<clsDriveMesh> moDriveList_Output;
	ArrayList<clsPrimaryDataStructureContainer> moRepressedRetry_Input;
	
	/**
	 * 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:01:31
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E06_DefenseMechanismsForDriveContents(String poPrefix,
			clsProperties poProp, clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
		
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
	public void receive_I1_5(List<clsDriveMesh> poData) {
		moDriveList_Input = (ArrayList<clsDriveMesh>)deepCopy( (ArrayList<clsDriveMesh>)poData);
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
	public void receive_I4_3(List<clsPrimaryDataStructureContainer> poPIs) {
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
		// HZ: Up to now the driveList is passed through (deepCopy is called in the next module); 
		//The interfaces send_I4_1 and send_I5_1 are filled with empty Lists. 
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
		//HZ: null is a placeholder for the homeostatic information formed out of objects of the type pa._v19.memorymgmt.datatypes 
		send_I1_6(moDriveList_Output);
		send_I4_1(new ArrayList<clsPrimaryDataStructureContainer>(), new ArrayList<pa._v19.memorymgmt.datatypes.clsThingPresentation>(),new ArrayList<clsAssociationDriveMesh>());
		send_I5_1(new ArrayList<clsPrimaryDataStructureContainer>());
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:48:13
	 * 
	 * @see pa.interfaces.send.I1_6_send#send_I1_6(java.util.ArrayList)
	 */
	@Override
	public void send_I1_6(ArrayList<clsDriveMesh> poDriveList) {
		((I1_6_receive)moEnclosingContainer).receive_I1_6(moDriveList_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:48:13
	 * 
	 * @see pa.interfaces.send.I4_1_send#send_I4_1(java.util.List, java.util.List, java.util.List)
	 */
	@Override
	public void send_I4_1(List<clsPrimaryDataStructureContainer> poPIs, List<pa._v19.memorymgmt.datatypes.clsThingPresentation> poTPs, List<clsAssociationDriveMesh> poAffects) {
		((I4_1_receive)moEnclosingContainer).receive_I4_1(new ArrayList<clsPrimaryDataStructureContainer>(), new ArrayList<pa._v19.memorymgmt.datatypes.clsThingPresentation>(),new ArrayList<clsAssociationDriveMesh>());
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:48:13
	 * 
	 * @see pa.interfaces.send.I5_1_send#send_I5_1(java.util.ArrayList)
	 */
	@Override
	public void send_I5_1(ArrayList<clsPrimaryDataStructureContainer> poAffectOnlyList) {
		((I5_1_receive)moEnclosingContainer).receive_I5_1(new ArrayList<clsPrimaryDataStructureContainer>());	
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
		
		throw new java.lang.NoSuchMethodError();
	}
}
