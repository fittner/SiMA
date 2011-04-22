/**
 * E34_KnowledgeAboutReality2.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 27.04.2010, 10:38:16
 */
package pa._v30.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsBWProperties;
import pa._v30.tools.clsPair;
import pa._v30.tools.toHtml;
import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.itfMinimalModelMode;
import pa._v30.interfaces.modules.I7_3_receive;
import pa._v30.interfaces.modules.I7_5_receive;
import pa._v30.interfaces.modules.I7_5_send;
import pa._v30.memorymgmt.clsKnowledgeBaseHandler;
import pa._v30.memorymgmt.datatypes.clsDataStructurePA;
import pa._v30.memorymgmt.datatypes.clsWordPresentation;

/**
 * DOCUMENT (KOHLHAUSER) - insert description 
 * 
 * @author deutsch
 * 27.04.2010, 10:38:16
 * 
 */
public class E34_KnowledgeAboutReality_2 extends clsModuleBaseKB implements itfMinimalModelMode, I7_3_receive, I7_5_send {
	public static final String P_MODULENUMBER = "34";
	
	private ArrayList<clsPair<Integer, clsDataStructurePA>> moSearchPattern;
	private boolean mnMinimalModel;
	/**
	 * DOCUMENT (KOHLHAUSER) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:57:48
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E34_KnowledgeAboutReality_2(String poPrefix, clsBWProperties poProp,
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
	 * @see pa.modules._v30.clsModuleBase#stateToHTML()
	 */
	@Override
	public String stateToHTML() {
		String html ="";
		
		html += toHtml.valueToHTML("mnMinimalModel", mnMinimalModel);
		html += toHtml.listToHTML("moSearchPattern", moSearchPattern);
		html += toHtml.valueToHTML("moKnowledgeBaseHandler", moKnowledgeBaseHandler);
		
		return html;
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
			// TODO (KOHLHAUSER) - Auto-generated method stub
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
			send_I7_5(-1);
		} else {
			send_I7_5(mnTest);
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
	public void receive_I7_3(ArrayList<clsWordPresentation> poActionCommands) {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 18:01:36
	 * 
	 * @see pa.interfaces.send.I7_6_send#send_I7_6(int)
	 */
	@Override
	public void send_I7_5(int pnData) {
		((I7_5_receive)moModuleList.get(33)).receive_I7_5(pnData);
		putInterfaceData(I7_5_send.class, pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:48:26
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (KOHLHAUSER) - Auto-generated method stub
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
		// TODO (KOHLHAUSER) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:58:01
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
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v30.clsModuleBase#setDescription()
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