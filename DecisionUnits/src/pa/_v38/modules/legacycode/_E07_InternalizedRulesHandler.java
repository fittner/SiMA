/**
 * E7_SuperEgo_unconscious.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:03:35
 */
package pa._v38.modules.legacycode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
//import java.util.List;

import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
//import pa._v38.interfaces.modules.I1_5_receive;
import pa._v38.interfaces.modules.I5_1_receive;
import pa._v38.interfaces.modules.I5_10_receive;
import pa._v38.interfaces.modules.eInterfaces;
//import pa._v38.interfaces.modules.I5_13_receive;
import pa._v38.interfaces.modules.I5_13_send;
//import pa._v38.interfaces.modules.I5_11_receive;
import pa._v38.interfaces.modules.I5_11_send;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.modules.clsModuleBase;
import pa._v38.modules.clsModuleBaseKB;
import pa._v38.modules.eImplementationStage;
import pa._v38.modules.eProcessType;
import pa._v38.modules.ePsychicInstances;
import config.clsBWProperties;

/**
 * (GELBARD) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:03:35
 * 
 */
//HZ 4.05.2011: Module is only required to transfer its functionality to v38
@Deprecated
public class _E07_InternalizedRulesHandler extends clsModuleBaseKB implements 
								/*I1_5_receive,*/ I5_10_receive, I5_1_receive, I5_13_send, I5_11_send {
	public static final String P_MODULENUMBER = "07";
	
	private ArrayList<clsPair<Integer, clsDataStructurePA>> moSearchPattern;
	
	private ArrayList<clsPrimaryDataStructureContainer> moPrimaryInformation; 
	private ArrayList<clsDriveMesh> moSexualDrives;
	
	private boolean mnMinimalModel;

	/**
	 * (GELBARD) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:34:55
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public _E07_InternalizedRulesHandler(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, clsKnowledgeBaseHandler poKnowledgeBaseHandler)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);
		
		moSearchPattern = new ArrayList<clsPair<Integer,clsDataStructurePA>>();
		
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
		
		text += toText.valueToTEXT("mnMinimalModel", mnMinimalModel);
		text += toText.listToTEXT("moSearchPattern", moSearchPattern);
		text += toText.listToTEXT("moPrimaryInformation", moPrimaryInformation);		
		text += toText.valueToTEXT("moKnowledgeBaseHandler", moKnowledgeBaseHandler);
		text += toText.valueToTEXT("moSexualDrives", moSexualDrives);
		
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
		mnMinimalModel = false;
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
		mnPsychicInstances = ePsychicInstances.SUPEREGO;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 13:46:50
	 * 
	 * @see pa.interfaces.I1_3#receive_I1_3(int)
	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public void receive_I1_5(List<clsDriveMesh> poData) {
//		moPrimaryInformation = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy( (ArrayList<clsDriveMesh>)poData);
//	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:05:13
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I5_10(clsPrimaryDataStructureContainer poMergedPrimaryInformation, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		
		//DEEPCOPY
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:10
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		if (!mnMinimalModel) {		
			mnTest++;
		}
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:10
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		if (mnMinimalModel) {
//			send_I3_1(-1);
//			send_I3_2(-1);
		} else {
//			send_I3_1(mnTest);
//			send_I3_2(mnTest);			
		}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:50:09
	 * 
	 * @see pa.interfaces.send.I3_1_send#send_I3_1(int)
	 */
//	@Override
//	public void send_I3_1(int pnData) {
//		((I5_13_receive)moModuleList.get(6)).receive_I3_1(pnData);
//		putInterfaceData(I5_13_send.class, pnData);
//	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:50:09
	 * 
	 * @see pa.interfaces.send.I3_2_send#send_I3_2(int)
	 */
//	@Override
//	public void send_I3_2(int pnData) {
//		((I5_11_receive)moModuleList.get(19)).receive_I3_2(pnData);
//		putInterfaceData(I5_11_send.class, pnData);
//	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:45:41
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// 
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:45:41
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// 
		throw new java.lang.NoSuchMethodError();
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:35:15
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
	 * 03.03.2011, 16:36:04
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
		moDescription = "Rules which are only accessible to functions of the Superego are used to evaluate the incoming drive demands and perceptions. Three possible decisions can be made for each incoming information: they can be passed on without any changes, they can be passed forward but certain changes have to be made, and these contents are not allowed to pass at all. If the evaluated contents qualify for one of the latter two possibilities - a conflict occurs - defense mechanisms have to deal with them. ";
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 08:27:54
	 * 
	 * @see pa._v38.interfaces.modules.I5_1_receive#receive_I5_1(java.util.ArrayList)
	 */
	@Override
	public void receive_I5_1(
			ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poData) {
		// 
		
	}

	/* (non-Javadoc)
	 *
	 * @since 12.07.2011 17:11:41
	 * 
	 * @see pa._v38.interfaces.modules.I5_11_send#send_I5_11(java.util.ArrayList, pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer, java.util.ArrayList)
	 */
	@Override
	public void send_I5_11(
			ArrayList<clsPair<String, String>> poForbiddenPerceptions,
			clsPrimaryDataStructureContainer poEnvironmentalPerception,
			ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		// 
		
	}

	/* (non-Javadoc)
	 *
	 * @since 12.07.2011 17:11:41
	 * 
	 * @see pa._v38.interfaces.modules.I5_13_send#send_I5_13(java.util.ArrayList, java.util.ArrayList)
	 */
	@Override
	public void send_I5_13(ArrayList<String> poForbiddenDrive,
			ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poData) {
		// 
		
	}	
	
}
