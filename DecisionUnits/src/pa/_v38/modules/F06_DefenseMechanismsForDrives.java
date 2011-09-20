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

import pa._v38.interfaces.modules.D2_3_send;
import pa._v38.interfaces.modules.I5_18_receive;
import pa._v38.interfaces.modules.I5_18_send;
import pa._v38.interfaces.modules.I5_13_receive;
import pa._v38.interfaces.modules.I5_17_receive;
import pa._v38.interfaces.modules.I5_17_send;
import pa._v38.interfaces.modules.I5_5_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAffect;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.storage.DT2_BlockedContentStorage;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
import config.clsProperties;

/**
 * Defends forbidden drives. Super-Ego (F7, F55) sends a list with forbidden drives to F06. F06 decides whether to defend the forbidden drives or not.
 * If F06 decided to defend the forbidden drives F06 chooses the defense mechanism (repression, sublimation, deferral, ...).  
 * 
 * @author gelbard
 * 15.09.2009, 14:01:06
 * 
 */
public class F06_DefenseMechanismsForDrives extends clsModuleBase implements 
					I5_5_receive, I5_13_receive, I5_18_send, I5_17_send, D2_3_send {
	public static final String P_MODULENUMBER = "06";
	
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moDriveList_Input;
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moDriveList_Output;
	private ArrayList<String> moForbiddenDrives_Input;
	private ArrayList<clsPrimaryDataStructureContainer> moRepressedRetry_Input;
	private ArrayList<clsDriveMesh> moSexualDrives;
	private ArrayList<clsPrimaryDataStructure> moQuotasOfAffect_Output = new ArrayList<clsPrimaryDataStructure>();
	
	private DT2_BlockedContentStorage moBlockedContentStorage; // storage for repressed drives and denied perceptions

	/** 
	 * 
	 * @author gelbard
	 * 15.09.2011, 16:38:57
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @param poBlockedContentStorage
	 * @throws Exception
	 */
	public F06_DefenseMechanismsForDrives(String poPrefix, clsProperties poProp, HashMap<Integer,
			clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			DT2_BlockedContentStorage poBlockedContentStorage)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		moBlockedContentStorage = poBlockedContentStorage;
		applyProperties(poPrefix, poProp);
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 15.09.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.listToTEXT("moDriveList_Input", moDriveList_Input);
		text += toText.listToTEXT("moDriveList_Output", moDriveList_Output);
		text += toText.listToTEXT("moRepressedRetry_Input", moRepressedRetry_Input);	
		text += toText.listToTEXT("moForbiddenDrives_Input", moForbiddenDrives_Input);
		text += toText.listToTEXT("moSexualDrives", moSexualDrives);
		text += toText.listToTEXT("moQuotasOfAffect_Output", moQuotasOfAffect_Output);
		text += toText.valueToTEXT("moBlockedContentStorage", moBlockedContentStorage);
		
		return text;
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
	@Override
	public void receive_I5_5(ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poData) {

	}

	/* Input from Super-Ego = E7
	 *
	 * @author deutsch
	 * 11.08.2009, 14:07:30
	 * 
	 * @see pa.interfaces.I3_1#receive_I3_1(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_13(ArrayList<String> poForbiddenDrives, ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poData) {
		moDriveList_Input = (ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>)deepCopy( (ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>)poData);
		moForbiddenDrives_Input = (ArrayList<String>) deepCopy (poForbiddenDrives);
	}

	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 15.09.2011, 16:15:00
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void process_basic() { 
		 moDriveList_Output = (ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>) deepCopy (moDriveList_Input);
		 
		 // Super-Ego requests to defend the drives moForbiddenDrives_Input
		 // For now: All the drives in moForbiddenDrives_Input are repressed.
		 // ToDo FG: Implement other defense mechanisms beside repression
		 repress_drive(moForbiddenDrives_Input);
		 
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
    protected void repress_drive(ArrayList<String> oForbiddenDrives_Input) {
    	
    	// If nothing to repress return immediately (otherwise NullPointerException)
    	if (oForbiddenDrives_Input == null ) return;
		
		// empty the list from last step otherwise list only grows
		moQuotasOfAffect_Output.clear();
		
		// Iterate over all forbidden drives
		for (String oContent : oForbiddenDrives_Input) {
				
			int i = 0;
			// search in list of incoming drives
			for(clsPair<clsPhysicalRepresentation, clsDriveMesh> oDrives : moDriveList_Output){
				// check DriveMesh
				if (oDrives.b.getMoContent().equalsIgnoreCase(oContent)){

					// drive found
				    break;
				}
				
				i++;
			}
			
			
			// if drive found	
			if (i < moDriveList_Output.size()) {
				
				// Only store the drive in blocked content storage, if there are no similar drives in blocked content storage
				if (!moBlockedContentStorage.existsMatch(moDriveList_Output.get(i).a, moDriveList_Output.get(i).b))		
					// insert DriveMesh i into BlockedContentStorage
					send_D2_3(moDriveList_Output.get(i).a, moDriveList_Output.get(i).b);
				
				// add single quotas of affect to affect only list
				clsAffect oAffect = (clsAffect) clsDataStructureGenerator.generateDataStructure(eDataType.AFFECT, new clsPair<String, Object>("AFFECT", moDriveList_Output.get(i).b.getMrPleasure())); 
				moQuotasOfAffect_Output.add(oAffect);
				
				// remove DriveMesh i from output list
				moDriveList_Output.remove(i);
			}
		}
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
		send_I5_17(moQuotasOfAffect_Output);
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
	public void send_I5_17(ArrayList<clsPrimaryDataStructure> poAffectOnlyList) {
		((I5_17_receive)moModuleList.get(20)).receive_I5_17(poAffectOnlyList);	
		putInterfaceData(I5_17_send.class, poAffectOnlyList);		
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 15.09.2011, 16:48:13
	 * 
	 * Sends blocked drive aims (clsDriveMesh) and drive objects (clsPhysicalRepresentation) to DT2_BlockedContentStorage
	 */
	@Override
	public void send_D2_3 (clsPhysicalRepresentation poDS, clsDriveMesh poDM) {
		moBlockedContentStorage.receive_D2_3(poDS, poDM);	
		putInterfaceData(D2_3_send.class, poDS, poDM);		
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
