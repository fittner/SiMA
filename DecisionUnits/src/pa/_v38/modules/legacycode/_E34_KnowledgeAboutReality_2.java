/**
 * E34_KnowledgeAboutReality2.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 27.04.2010, 10:38:16
 */
package pa._v38.modules.legacycode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsBWProperties;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
import pa._v38.interfaces.itfMinimalModelMode;
import pa._v38.interfaces.modules.I6_9_receive;
import pa._v38.interfaces.modules.eInterfaces;
//import pa._v38.interfaces.modules.I7_5_receive;
//import pa._v38.interfaces.modules.I7_5_send;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
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
 * 27.04.2010, 10:38:16
 * 
 */
//HZ 4.05.2011: Module is only required to transfer its functionality to v38
@Deprecated
public class _E34_KnowledgeAboutReality_2 extends clsModuleBaseKB implements itfMinimalModelMode, I6_9_receive/*, I7_5_send*/ {
	public static final String P_MODULENUMBER = "34";
	
	private ArrayList<clsPair<Integer, clsDataStructurePA>> moSearchPattern;
	private boolean mnMinimalModel;
	/**
	 * (KOHLHAUSER) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:57:48
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public _E34_KnowledgeAboutReality_2(String poPrefix, clsBWProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, clsKnowledgeBaseHandler poKnowledgeBaseHandler) throws Exception {
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
		text += toText.valueToTEXT("moKnowledgeBaseHandler", moKnowledgeBaseHandler);
		
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
	 * 27.04.2010, 10:38:37
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		if (!mnMinimalModel) {
			// 
			mnTest++;
		}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 27.04.2010, 10:38:37
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		if (mnMinimalModel) {
//			send_I7_5(-1);
		} else {
//			send_I7_5(mnTest);
		}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 27.04.2010, 10:38:37
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
	 * 27.04.2010, 10:38:37
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
	 * 27.04.2010, 10:38:37
	 * 
	 * @see pa.interfaces.I7_3#receive_I7_3(java.util.ArrayList)
	 */
	@Override
	public void receive_I6_9(ArrayList<clsSecondaryDataStructureContainer> poActionCommands) {
		// 
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 18:01:36
	 * 
	 * @see pa.interfaces.send.I7_6_send#send_I7_6(int)
	 */
//	@Override
//	public void send_I7_5(int pnData) {
//		((I7_5_receive)moModuleList.get(33)).receive_I7_5(pnData);
//		putInterfaceData(I7_5_send.class, pnData);
//	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:48:26
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
	 * 12.07.2010, 10:48:26
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
	 * 03.03.2011, 16:58:01
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

	@Override
	public void setMinimalModelMode(boolean pnMinial) {
		mnMinimalModel = pnMinial;
	}

	@Override
	public boolean getMinimalModelMode() {
		return mnMinimalModel;
	}	
}
