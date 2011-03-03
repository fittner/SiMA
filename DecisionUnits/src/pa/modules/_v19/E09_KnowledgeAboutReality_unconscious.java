/**
 * E9_KnowledgeAboutReality_unconscious.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:09:09
 */
package pa.modules._v19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import config.clsBWProperties;
import pa._v19.clsInterfaceHandler;
import pa.interfaces.knowledgebase.itfKnowledgeBaseAccess;
import pa.interfaces.receive._v19.I1_5_receive;
import pa.interfaces.receive._v19.I6_3_receive;
import pa.interfaces.send._v19.I6_3_send;
import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.memorymgmt.enums.eDataType;
import pa.tools.clsPair;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:09:09
 * 
 */
public class E09_KnowledgeAboutReality_unconscious extends clsModuleBase implements I1_5_receive, I6_3_send, itfKnowledgeBaseAccess {
	ArrayList<clsPrimaryDataStructureContainer> moPrimaryInformation; 
	
	/**
	 * 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:09:57
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E09_KnowledgeAboutReality_unconscious(String poPrefix,
			clsBWProperties poProp, clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
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
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I1_5(List<clsDriveMesh> poData) {
		moPrimaryInformation = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy((ArrayList<clsDriveMesh>)poData);
	}

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
	@Override
	protected void send() {
		send_I6_3(mnTest);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:52:35
	 * 
	 * @see pa.interfaces.send.I6_3_send#send_I6_3(int)
	 */
	@Override
	public void send_I6_3(int pnData) {
		((I6_3_receive)moEnclosingContainer).receive_I6_3(mnTest);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:45:59
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
	 * 12.07.2010, 10:45:59
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 12.08.2010, 21:10:19
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
	 * 16.08.2010, 10:15:34
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#addToSearchPattern(pa.memorymgmt.enums.eDataType, pa.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void addToSearchPattern(eDataType oReturnType,
			clsDataStructurePA poSearchPattern) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}
}
