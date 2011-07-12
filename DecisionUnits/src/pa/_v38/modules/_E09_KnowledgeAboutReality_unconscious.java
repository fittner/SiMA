/**
 * E9_KnowledgeAboutReality_unconscious.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:09:09
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
//import java.util.List;
import config.clsBWProperties;
import du.itf.actions.clsActionCommand;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
import pa._v38.interfaces.modules.I1_5_receive;
import pa._v38.interfaces.modules.I5_1_receive;
import pa._v38.interfaces.modules.eInterfaces;
//import pa._v38.interfaces.modules.I6_3_receive;
import pa._v38.interfaces.modules.I6_3_send;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;

/**
 * DOCUMENT (GELBARD) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:09:09
 * 
 */
//HZ 4.05.2011: Module is only required to transfer its functionality to v38
@Deprecated
public class _E09_KnowledgeAboutReality_unconscious extends clsModuleBaseKB implements
						I1_5_receive, I5_1_receive, I6_3_send {
	
	public static final String P_MODULENUMBER = "09";
	
	private ArrayList<clsDriveMesh> moSexualDrives;
	private ArrayList<clsPrimaryDataStructureContainer> moPrimaryInformation; 
	/**
	 * DOCUMENT (GELBARD) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:37:44
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public _E09_KnowledgeAboutReality_unconscious(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, clsKnowledgeBaseHandler poKnowledgeBaseHandler)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);
		
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
		
		text += toText.listToTEXT("moPrimaryInformation", moPrimaryInformation);
		text += toText.valueToTEXT("moKnowledgeBaseHandler", moKnowledgeBaseHandler);
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
	 * 11.08.2009, 14:10:04
	 * 
	 * @see pa.interfaces.I1_5#receive_I1_5(int)
	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public void receive_I1_5(List<clsDriveMesh> poData) {
//		moPrimaryInformation = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy((ArrayList<clsDriveMesh>)poData);
//	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:18
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
	 * 11.08.2009, 16:15:18
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
//	@Override
//	protected void send() {
//		send_I6_3(mnTest);
//	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:52:35
	 * 
	 * @see pa.interfaces.send.I6_3_send#send_I6_3(int)
	 */
//	@Override
//	public void send_I6_3(int pnData) {
//		((I6_3_receive)moModuleList.get(6)).receive_I6_3(pnData);
//		putInterfaceData(I6_3_send.class, pnData);
//	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:45:59
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
	 * 12.07.2010, 10:45:59
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
	 * 03.03.2011, 16:37:52
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
	 * 03.03.2011, 16:38:24
	 * 
	 * @see pa.interfaces.receive._v38.I2_19_receive#receive_I2_19(java.util.List)
	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public void receive_I2_19(ArrayList<clsDriveMesh> poSexualDrives) {
//		moSexualDrives = (ArrayList<clsDriveMesh>)deepCopy(poSexualDrives);
//		
//	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "This module accesses knowledge which can be used to determine if a drive representation forwarded from {E44} or {E38} can be satisfied with a certain object. Not the reality/outer world is used as reference, instead the experiences how a special drive demand can be satisfied is used.";
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 08:31:00
	 * 
	 * @see pa._v38.interfaces.modules.I6_3_send#send_I6_3(java.util.ArrayList)
	 */
	@Override
	public void send_I6_3(
			ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 08:31:00
	 * 
	 * @see pa._v38.interfaces.modules.I5_1_receive#receive_I5_1(java.util.ArrayList)
	 */
	@Override
	public void receive_I5_1(
			ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poData) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 08:31:00
	 * 
	 * @see pa._v38.interfaces.modules.I1_5_receive#receive_I1_5(java.util.ArrayList)
	 */
	@Override
	public void receive_I1_5(ArrayList<clsActionCommand> poActionCommandList) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 08:31:00
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}	
}
