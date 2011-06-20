/**
 * E15_2.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 07.10.2009, 11:18:15
 */
package pa._v30.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.List;
import config.clsBWProperties;
import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.modules.I4_1_receive;
import pa._v30.interfaces.modules.I4_2_receive;
import pa._v30.interfaces.modules.I4_3_receive;
import pa._v30.interfaces.modules.I4_3_send;
import pa._v30.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v30.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v30.storage.clsBlockedContentStorage;
import pa._v30.tools.toText;

/**
 *
 * 
 * @author deutsch
 * 07.10.2009, 11:18:15
 * 
 */
public class E36_RepressionHandler extends clsModuleBase implements I4_1_receive, I4_2_receive, I4_3_send {
	public static final String P_MODULENUMBER = "36";
	
	private clsBlockedContentStorage moBlockedContentStorage;
	private ArrayList<clsPrimaryDataStructureContainer> moPrimaryInformation; 

	
	/**
	 *
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:40:54
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E36_RepressionHandler(String poPrefix, clsBWProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, clsBlockedContentStorage poBlockedContentStorage) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		
		moPrimaryInformation = new ArrayList<clsPrimaryDataStructureContainer>();
		moBlockedContentStorage = poBlockedContentStorage;

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
		
		text += toText.valueToTEXT("moBlockedContentStorage", moBlockedContentStorage);
		text += toText.listToTEXT("moPrimaryInformation", moPrimaryInformation);
		
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
	 * 07.10.2009, 11:19:54
	 * 
	 * @see pa.interfaces.I4_1#receive_I4_1(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I4_1(List<clsPrimaryDataStructureContainer> poPIs, List<pa._v30.memorymgmt.datatypes.clsThingPresentation> poTPs, List<clsAssociationDriveMesh> poAffects) {
		moPrimaryInformation.addAll((ArrayList<clsPrimaryDataStructureContainer>)deepCopy((ArrayList<clsPrimaryDataStructureContainer>)poPIs));
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:19:54
	 * 
	 * @see pa.interfaces.I4_2#receive_I4_2(int)
	 */
	@Override
	public void receive_I4_2(ArrayList<clsPrimaryDataStructureContainer> poPIs, ArrayList<pa._v30.memorymgmt.datatypes.clsThingPresentation> poTPs, ArrayList<clsAssociationDriveMesh> poAffects) {
		//mnTest += pnData;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:46
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		mnTest++;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:46
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		
		send_I4_3(new ArrayList<clsPrimaryDataStructureContainer>());
		
		moPrimaryInformation.clear(); 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:46
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
	 * 07.10.2009, 11:20:46
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
	 * 12.07.2010, 10:52:51
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
	 * 12.07.2010, 10:52:51
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
	 * 03.03.2011, 16:22:45
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
	 * 03.03.2011, 17:29:39
	 * 
	 * @see pa.interfaces.send._v30.I4_3_send#send_I4_3(java.util.ArrayList, java.util.ArrayList)
	 */
	@Override
	public void send_I4_3(ArrayList<clsPrimaryDataStructureContainer> poPIs) {
		// 		((I4_3_receive)moEnclosingContainer).receive_I4_3(new ArrayList<clsPrimaryDataStructureContainer>());
		((I4_3_receive)moModuleList.get(6)).receive_I4_3(poPIs);
		putInterfaceData(I4_3_send.class, poPIs);
		
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
		moDescription = "Blocked contentscontents which could not pass defense mechanisms in {E6} or {E19}are sent to this module for further processing. These contents are constantly tried to be send back to the defense mechanisms to test if they are now able to pass them. By using several tools, contents are changed and ``disguised'' to make the passing more likely. For drive representations these tools include that thing presentations and their quota of affects can be split apart and sent back individually to {E6}. Another possibility is to attach itself to other, more acceptable drive representations (e.g. ones with more acceptable drive objects). Thing presentations representing perceptions are stored in the repressed contents memory. They are processed by {E35}. It has to mentioned that no contentthing presentations and/or quota of affectscan disappear. They are stored until it was possible to send them through the defense mechanisms.";
	}	
}
