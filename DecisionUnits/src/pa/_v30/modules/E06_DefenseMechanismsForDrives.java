/**
 * E6_DefenseMechanismsForDriveContents.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:01:06
 */
package pa._v30.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.List;

import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.modules.I1_5_receive;
import pa._v30.interfaces.modules.I1_6_receive;
import pa._v30.interfaces.modules.I1_6_send;
import pa._v30.interfaces.modules.I2_19_receive;
import pa._v30.interfaces.modules.I3_1_receive;
import pa._v30.interfaces.modules.I4_1_receive;
import pa._v30.interfaces.modules.I4_1_send;
import pa._v30.interfaces.modules.I4_3_receive;
import pa._v30.interfaces.modules.I5_1_receive;
import pa._v30.interfaces.modules.I5_1_send;
import pa._v30.interfaces.modules.I6_3_receive;
import pa._v30.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;
import pa._v30.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v30.tools.toText;
import config.clsBWProperties;

/**
 *
 * 
 * @author deutsch
 * 11.08.2009, 14:01:06
 * 
 */
public class E06_DefenseMechanismsForDrives extends clsModuleBase implements 
					I1_5_receive, I2_19_receive, I3_1_receive, I4_3_receive, I6_3_receive, I1_6_send, I4_1_send, I5_1_send {
	public static final String P_MODULENUMBER = "06";
	
	private ArrayList<clsDriveMesh> moDriveList_Input;
	private ArrayList<clsDriveMesh> moDriveList_Output;
	private ArrayList<clsPrimaryDataStructureContainer> moRepressedRetry_Input;
	private ArrayList<clsDriveMesh> moSexualDrives;

	/**
	 *
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:38:57
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E06_DefenseMechanismsForDrives(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);	
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v30.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.listToTEXT("moDriveList_Input", moDriveList_Input);
		text += toText.listToTEXT("moDriveList_Output", moDriveList_Output);
		text += toText.listToTEXT("moRepressedRetry_Input", moRepressedRetry_Input);	
		text += toText.listToTEXT("moSexualDrives", moSexualDrives);
		
		
		
		return text;
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
	public void receive_I4_3(ArrayList<clsPrimaryDataStructureContainer> poPIs) {
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
		//HZ: null is a placeholder for the homeostatic information formed out of objects of the type pa._v30.memorymgmt.datatypes 
		send_I1_6(moDriveList_Output);
		send_I4_1(new ArrayList<clsPrimaryDataStructureContainer>(), new ArrayList<pa._v30.memorymgmt.datatypes.clsThingPresentation>(),new ArrayList<clsAssociationDriveMesh>());
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
		((I1_6_receive)moModuleList.get(8)).receive_I1_6(poDriveList);
		putInterfaceData(I1_6_send.class, poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:48:13
	 * 
	 * @see pa.interfaces.send.I4_1_send#send_I4_1(java.util.List, java.util.List, java.util.List)
	 */
	@Override
	public void send_I4_1(List<clsPrimaryDataStructureContainer> poPIs, List<pa._v30.memorymgmt.datatypes.clsThingPresentation> poTPs, List<clsAssociationDriveMesh> poAffects) {
		((I4_1_receive)moModuleList.get(36)).receive_I4_1(poPIs, poTPs, poAffects);
		putInterfaceData(I4_1_send.class, poPIs, poTPs, poAffects);
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
		((I5_1_receive)moModuleList.get(20)).receive_I5_1(poAffectOnlyList);	
		putInterfaceData(I5_1_send.class, poAffectOnlyList);		
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

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:39:04
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
	 * 03.03.2011, 16:40:20
	 * 
	 * @see pa.interfaces.receive._v30.I2_19_receive#receive_I2_19(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_19(ArrayList<clsDriveMesh> poSexualDrives) {
		moSexualDrives = (ArrayList<clsDriveMesh>)deepCopy(poSexualDrives);
		
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v30.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "Based on information provided by {E7} and {E9}, this module decides which drive representations are allowed to become (pre-)conscious and if not which defense mechanism is to be applied. These mechanisms can split the thing presentations from their quota of affect, change the thing presentations, repress the contents until later, attach them to other contents, and more. Examples for these mechanisms are repression, intellectualization, and sublimination ({Schuster1997}). Next to evaluate newly incoming drive representations, the task of these modules is to reevaluate repressed drive representations which are sent back by module {E36}.";
	}	
}
