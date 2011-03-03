/**
 * E25_KnowledgeAboutReality.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:50:27
 */
package pa.modules._v19;

import java.util.ArrayList;
import java.util.HashMap;

import config.clsBWProperties;
import pa._v19.clsInterfaceHandler;
import pa.interfaces.knowledgebase.itfKnowledgeBaseAccess;
import pa.interfaces.receive._v19.I2_12_receive;
import pa.interfaces.receive._v19.I6_1_receive;
import pa.interfaces.send._v19.I6_1_send;
import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa.memorymgmt.enums.eDataType;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:50:27
 * 
 */
public class E25_KnowledgeAboutReality extends clsModuleBase implements I2_12_receive, I6_1_send, itfKnowledgeBaseAccess {

	//private ArrayList<clsSecondaryDataStructureContainer> moFocusedPerception;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:50:56
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E25_KnowledgeAboutReality(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
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
	public void receive_I2_12(ArrayList<clsSecondaryDataStructureContainer> poFocusedPerception,
			   					ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
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
		mnTest++;
		
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
		send_I6_1(mnTest);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:51:50
	 * 
	 * @see pa.interfaces.send.I6_1_send#send_I6_1(int)
	 */
	@Override
	public void send_I6_1(int pnData) {
		((I6_1_receive)moEnclosingContainer).receive_I6_1(mnTest);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:47:31
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (deutsch) - Auto-generated method stub
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
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 12.08.2010, 20:59:07
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#accessKnowledgeBase(java.util.ArrayList)
	 */
	@Override
	public HashMap<Integer,ArrayList<clsPair<Double,clsDataStructureContainer>>> accessKnowledgeBase() {
		return moEnclosingContainer.moKnowledgeBaseHandler.initMemorySearch(moSearchPattern);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 16.08.2010, 10:16:04
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#addToSearchPattern(pa.memorymgmt.enums.eDataType, pa.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void addToSearchPattern(eDataType oReturnType,
			clsDataStructurePA poSearchPattern) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}
}
