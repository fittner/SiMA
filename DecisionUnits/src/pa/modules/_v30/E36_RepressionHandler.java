/**
 * E15_2.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 07.10.2009, 11:18:15
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import config.clsBWProperties;
import pa.interfaces._v30.eInterfaces;
import pa.interfaces.receive._v30.I4_1_receive;
import pa.interfaces.receive._v30.I4_2_receive;
import pa.interfaces.receive._v30.I4_3_receive;
import pa.interfaces.send._v30.I4_3_send;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.storage.clsBlockedContentStorage;

/**
 * DOCUMENT (GELBARD) - insert description 
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
	 * DOCUMENT (GELBARD) - insert description 
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
			HashMap<Integer, clsModuleBase> poModuleList, HashMap<eInterfaces, ArrayList<Object>> poInterfaceData, clsBlockedContentStorage poBlockedContentStorage) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		
		moPrimaryInformation = new ArrayList<clsPrimaryDataStructureContainer>();
		moBlockedContentStorage = poBlockedContentStorage;

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
	 * 07.10.2009, 11:19:54
	 * 
	 * @see pa.interfaces.I4_1#receive_I4_1(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I4_1(List<clsPrimaryDataStructureContainer> poPIs, List<pa.memorymgmt.datatypes.clsThingPresentation> poTPs, List<clsAssociationDriveMesh> poAffects) {
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
	public void receive_I4_2(ArrayList<clsPrimaryDataStructureContainer> poPIs, ArrayList<pa.memorymgmt.datatypes.clsThingPresentation> poTPs, ArrayList<clsAssociationDriveMesh> poAffects) {
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
		// TODO (GELBARD) - Auto-generated method stub
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
		// TODO (GELBARD) - Auto-generated method stub
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


}
