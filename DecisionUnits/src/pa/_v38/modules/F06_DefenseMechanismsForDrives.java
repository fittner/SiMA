/**
 * E6_DefenseMechanismsForDriveContents.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:01:06
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.eInterfaces;
import pa._v38.interfaces.modules.I5_18_receive;
import pa._v38.interfaces.modules.I5_18_send;
import pa._v38.interfaces.modules.I5_13_receive;
import pa._v38.interfaces.modules.I5_17_receive;
import pa._v38.interfaces.modules.I5_17_send;
import pa._v38.interfaces.modules.I5_5_receive;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.storage.clsBlockedContentStorage;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
import config.clsBWProperties;

/**
 * DOCUMENT (GELBARD) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:01:06
 * 
 */
public class F06_DefenseMechanismsForDrives extends clsModuleBase implements 
					I5_5_receive, I5_13_receive, I5_18_send, I5_17_send {
	public static final String P_MODULENUMBER = "06";
	
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moDriveList_Input;
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moDriveList_Output;
	private ArrayList<clsPrimaryDataStructureContainer> moRepressedRetry_Input;
	private ArrayList<clsDriveMesh> moSexualDrives;

	/**
	 * DOCUMENT (GELBARD) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:38:57
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F06_DefenseMechanismsForDrives(String poPrefix,
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
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
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
	public void receive_I5_5(ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poData) {
		moDriveList_Input = (ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>)deepCopy( (ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>)poData);
	}

	/* Input from Super-Ego = E7
	 *
	 * @author deutsch
	 * 11.08.2009, 14:07:30
	 * 
	 * @see pa.interfaces.I3_1#receive_I3_1(int)
	 */
	@Override
	public void receive_I5_13(int[] poForbiddenDrive, ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poData) {
		mnTest += 1;
		
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
		//TODO HZ: Up to now the driveList is passed through (deepCopy is called in the next module); 
		//The interfaces send_I4_1 and send_I5_1 are filled with empty Lists. 
		 moDriveList_Output = moDriveList_Input;
		 
		 // Well - this is just a test for the defense mechanism "repression"
		 repress_drive(9);
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 30.06.2011, 14:43:00
	 * 
	 * This method represents the defense mechanism "repression"
	 * 
	 * ToDo (FG): For now the whole DriveMesh and the PhysicalRepresentation are repressed.
	 *            Later it could be possible to only repress the DriveMesh or only repress the PhysicalRepresentation.
	 */
    protected void repress_drive(int i) {
		clsBlockedContentStorage moBlockedContentStorage = new clsBlockedContentStorage();
		if (i < moDriveList_Output.size()) {
			
			// insert DriveMesh i into BlockedContentStorage
			moBlockedContentStorage.add(moDriveList_Output.get(i).b);
			
			// remove DriveMesh i from output list
		    moDriveList_Output.remove(i);
		}
		else
			;//ToDo (FG): write error message to log-file: "Index i is greater than size of list of drives. Was not able to repress drive."
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
		//HZ: null is a placeholder for the homeostatic information formed out of objects of the type pa._v38.memorymgmt.datatypes 
		send_I5_18(moDriveList_Output);
		send_I5_17(new ArrayList<clsPrimaryDataStructureContainer>());
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:48:13
	 * 
	 * @see pa.interfaces.send.I1_6_send#send_I1_6(java.util.ArrayList)
	 */
	@Override
	public void send_I5_18(ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poDriveList) {
		((I5_18_receive)moModuleList.get(8)).receive_I5_18(poDriveList);
		putInterfaceData(I5_18_send.class, poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:48:13
	 * 
	 * @see pa.interfaces.send.I5_1_send#send_I5_1(java.util.ArrayList)
	 */
	@Override
	public void send_I5_17(ArrayList<clsPrimaryDataStructureContainer> poAffectOnlyList) {
		((I5_17_receive)moModuleList.get(20)).receive_I5_17(poAffectOnlyList);	
		putInterfaceData(I5_17_send.class, poAffectOnlyList);		
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
		// TODO (GELBARD) - Auto-generated method stub
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
		// TODO (GELBARD) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:39:04
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "Based on information provided by {E7} and {E9}, this module decides which drive representations are allowed to become (pre-)conscious and if not which defense mechanism is to be applied. These mechanisms can split the thing presentations from their quota of affect, change the thing presentations, repress the contents until later, attach them to other contents, and more. Examples for these mechanisms are repression, intellectualization, displacement, and sublimination ({Schuster1997}). Repressed content reappears in {F54}";
	}
}
