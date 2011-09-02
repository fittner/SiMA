/**
 * F54_EmersionOfBlockedDriveContent.java: DecisionUnits - pa._v38.modules
 * 
 * @author zeilinger
 * 02.05.2011, 15:47:36
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.modules.I5_2_receive;
import pa._v38.interfaces.modules.I5_3_receive;
import pa._v38.interfaces.modules.I5_3_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.storage.DT2_BlockedContentStorage;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
import config.clsProperties;

/**
 * Repressed drives are attached to incoming drives.
 * According to a getBestMatch function F54 finds the repressed drive from the list of repressed drives (clsBlockedContentStoreage) which matches best the incoming drive.
 * 
 * @author gelbard
 * 02.09.2011, 00:20:36
 * 
 */
public class F54_EmersionOfBlockedDriveContent extends clsModuleBase
			implements I5_2_receive, I5_3_send{

	public static final String P_MODULENUMBER = "54";
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moDrives;
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moInput;
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 02.05.2011, 15:51:07
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @throws Exception
	 */
	public F54_EmersionOfBlockedDriveContent(String poPrefix,
			clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);

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
	 * @author zeilinger
	 * 02.05.2011, 15:51:13
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		text += toText.valueToTEXT("moInput", moInput);
		text += toText.valueToTEXT("moDrives", moDrives);
		return text;
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:15:07
	 * 
	 * @see pa._v38.interfaces.modules.I5_2_receive#receive_I5_2(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_2(
			ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poData) {
		
		moInput = (ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>) deepCopy(poData); 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:13
	 * 
	 * changed by gelbard 24.06.2011
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		DT2_BlockedContentStorage moBlockedContentStorage = new DT2_BlockedContentStorage();

		// To generate here an empty clsPhysicalRepresentation is total nonsense.
		// I (FG) think that the module F54 must be placed before the module "F57 memory traces for drives"
		// otherwise no clsPhysicalRepresentation can be generated for blocked content which emerges in F54.
		// I (FG) will talk to KD for that.
		clsThingPresentation oTP = (clsThingPresentation) clsDataStructureGenerator.generateDataStructure(eDataType.TP, new clsPair<String, Object>("NULL", "NULL")); 
		clsPhysicalRepresentation oPhR = (clsPhysicalRepresentation) oTP;
		
		
		moDrives = moInput;		
				
		clsDriveMesh oRep = moBlockedContentStorage.matchBlockedContentDrives(moInput);
		if (oRep != null) {
			moDrives.add(new clsPair<clsPhysicalRepresentation, clsDriveMesh>(oPhR, oRep));
		}
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:13
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:13
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:13
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_3(moDrives);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:13
	 * 
	 * @see pa._v38.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:13
	 * 
	 * @see pa._v38.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.ID;
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:13
	 * 
	 * @see pa._v38.modules.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:13
	 * 
	 * @see pa._v38.modules.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:15:07
	 * 
	 * @see pa._v38.interfaces.modules.I5_3_send#send_I5_3(java.util.ArrayList)
	 */
	@Override
	public void send_I5_3(
			ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poDrives) {
		
		((I5_3_receive)moModuleList.get(56)).receive_I5_3(poDrives);
		
		putInterfaceData(I5_3_send.class, poDrives);
	}
}
