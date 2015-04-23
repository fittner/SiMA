/**
 * F54_EmersionOfBlockedDriveContent.java: DecisionUnits - pa._v38.modules
 * 
 * @author zeilinger
 * 02.05.2011, 15:47:36
 */
package primaryprocess.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.storage.DT2_BlockedContentStorage;
import modules.interfaces.I5_2_receive;
import modules.interfaces.I5_3_receive;
import modules.interfaces.I5_3_send;
import modules.interfaces.eInterfaces;
import properties.clsProperties;
import base.datahandlertools.clsDataStructureGenerator;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsPhysicalRepresentation;
import base.datatypes.clsThingPresentation;
import base.datatypes.helpstructures.clsPair;
import base.modules.clsModuleBase;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;

/**
 * Repressed drives are attached to incoming drives.
 * According to a getBestMatch function F54 finds the repressed drive from the list of repressed drives (clsBlockedContentStoreage) which matches best the incoming drive.
 * 
 * @author gelbard
 * 07.05.2012, 00:20:36
 * 
 */
public class F54_EmersionOfBlockedDriveContent extends clsModuleBase
			implements I5_2_receive, I5_3_send{

	public static final String P_MODULENUMBER = "54";
	private ArrayList<clsDriveMesh> moDrives;
	private ArrayList<clsDriveMesh> moInput;
	
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	
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
		text += toText.listToTEXT("moInput", moInput);
		text += toText.listToTEXT("moDrives", moDrives);
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
			ArrayList<clsDriveMesh> poData) {
		
		moInput = (ArrayList<clsDriveMesh>) deepCopy(poData); 
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
		clsThingPresentation oTP = (clsThingPresentation) clsDataStructureGenerator.generateDataStructure(eDataType.TP, new clsPair<eContentType, Object>(eContentType.NULLOBJECT, eContentType.NULLOBJECT)); 
		clsPhysicalRepresentation oPhR = (clsPhysicalRepresentation) oTP;
		
		
		moDrives = moInput;		
				
		clsDriveMesh oRep = moBlockedContentStorage.matchBlockedContentDrives(moInput);
		if (oRep != null) {
			moDrives.add(oRep);
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
			ArrayList<clsDriveMesh> poDrives) {
		
		((I5_3_receive)moModuleList.get(56)).receive_I5_3(poDrives);
		((I5_3_receive)moModuleList.get(63)).receive_I5_3(poDrives);
		
		putInterfaceData(I5_3_send.class, poDrives);
	}
}
