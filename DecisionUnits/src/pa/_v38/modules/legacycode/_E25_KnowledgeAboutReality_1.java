/**
 * E25_KnowledgeAboutReality.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:50:27
 */
package pa._v38.modules.legacycode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsProperties;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
import pa._v38.interfaces.modules.I6_6_receive;
import pa._v38.interfaces.modules.eInterfaces;
//import pa._v38.interfaces.modules.I6_1_receive;
import pa._v38.interfaces.modules.I6_1_send;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.modules.clsModuleBase;
import pa._v38.modules.clsModuleBaseKB;
import pa._v38.modules.eImplementationStage;
import pa._v38.modules.eProcessType;
import pa._v38.modules.ePsychicInstances;

/**
 * (KOHLHAUSER) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:50:27
 * 
 */
//HZ 4.05.2011: Module is only required to transfer its functionality to v38
@Deprecated
public class _E25_KnowledgeAboutReality_1 extends clsModuleBaseKB implements I6_6_receive, I6_1_send {
	public static final String P_MODULENUMBER = "25";
	
	private ArrayList<clsPair<Integer, clsDataStructurePA>> moSearchPattern;
	//private ArrayList<clsSecondaryDataStructureContainer> moFocusedPerception;
	private boolean mnMinimalModel;
	/**
	 * (KOHLHAUSER) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:47:07
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public _E25_KnowledgeAboutReality_1(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, clsKnowledgeBaseHandler poKnowledgeBaseHandler) throws Exception {
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
		
		text += toText.valueToTEXT("mnMinimalModel", mnMinimalModel);
		text += toText.listToTEXT("moSearchPattern", moSearchPattern);
		text += toText.valueToTEXT("moKnowledgeBaseHandler", moKnowledgeBaseHandler);
		
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
		mnProcessType = eProcessType.SECONDARY;
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
	 * 11.08.2009, 14:51:09
	 * 
	 * @see pa.interfaces.I2_12#receive_I2_12(int)
	 */
	@Override
	public void receive_I6_6(ArrayList<clsSecondaryDataStructureContainer> poFocusedPerception,
			   					ArrayList<clsSecondaryDataStructureContainer> poDriveList,
			   					ArrayList<clsDataStructureContainer> poAssociatedMemoriesSecondary) {
		//never used ! moFocusedPerception_old = (ArrayList<clsSecondaryInformation>)this.deepCopy(poFocusedPerception_old);
		//never used ! moFocusedPerception = (ArrayList<clsSecondaryDataStructureContainer>) this.deepCopy(poFocusedPerception); 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:30
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		if (!mnMinimalModel) {
			//
		}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:30
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		if (mnMinimalModel) {
//			send_I6_1(-1);
		} else {
//			send_I6_1(mnTest);
		}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:51:50
	 * 
	 * @see pa.interfaces.send.I6_1_send#send_I6_1(int)
	 */
//	@Override
//	public void send_I6_1(int pnData) {
//		((I6_1_receive)moModuleList.get(24)).receive_I6_1(pnData);
//		putInterfaceData(I6_1_send.class, pnData);
//	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:47:31
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
	 * 12.07.2010, 10:47:31
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
	 * 03.03.2011, 16:47:13
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
		moDescription = "Semantic knowledge is retrieved from memory for all word and thing presentations send to these functions.";
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 08:32:39
	 * 
	 * @see pa._v38.interfaces.modules.I6_1_send#send_I6_1(java.util.ArrayList)
	 */
	@Override
	public void send_I6_1(
			ArrayList<clsSecondaryDataStructureContainer> poPerception, ArrayList<clsDataStructureContainer> poAssociatedMemoriesSecondary_OUT) {
		// 
		
	}	
	
}
