/**
 * E7_SuperEgo_unconscious.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:03:35
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import pa.interfaces.knowledgebase.itfKnowledgeBaseAccess;
import pa.interfaces.receive._v30.I1_5_receive;
import pa.interfaces.receive._v30.I2_19_receive;
import pa.interfaces.receive._v30.I2_9_receive;
import pa.interfaces.receive._v30.I3_1_receive;
import pa.interfaces.receive._v30.I3_2_receive;
import pa.interfaces.send._v30.I3_1_send;
import pa.interfaces.send._v30.I3_2_send;
import pa.memorymgmt.clsKnowledgeBaseHandler;
import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.memorymgmt.enums.eDataType;
import pa.tools.clsPair;
import config.clsBWProperties;

/**
 * DOCUMENT (GELBARD) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:03:35
 * 
 */
public class E07_InternalizedRulesHandler extends clsModuleBase implements 
							I1_5_receive, I2_9_receive, I2_19_receive, I3_1_send, I3_2_send, itfKnowledgeBaseAccess {
	public static final String P_MODULENUMBER = "07";
	
	private clsKnowledgeBaseHandler moKnowledgeBaseHandler; 
	private ArrayList<clsPair<Integer, clsDataStructurePA>> moSearchPattern;
	
	private ArrayList<clsPrimaryDataStructureContainer> moPrimaryInformation; 

	/**
	 * DOCUMENT (GELBARD) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:34:55
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E07_InternalizedRulesHandler(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, clsKnowledgeBaseHandler poKnowledgeBaseHandler)
			throws Exception {
		super(poPrefix, poProp, poModuleList);
		
		moSearchPattern = new ArrayList<clsPair<Integer,clsDataStructurePA>>();
		moKnowledgeBaseHandler = poKnowledgeBaseHandler;
		
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
		mnPsychicInstances = ePsychicInstances.SUPEREGO;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 13:46:50
	 * 
	 * @see pa.interfaces.I1_3#receive_I1_3(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I1_5(List<clsDriveMesh> poData) {
		moPrimaryInformation = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy( (ArrayList<clsDriveMesh>)poData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:05:13
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I2_9(ArrayList<clsPrimaryDataStructureContainer> poMergedPrimaryInformation) {
		
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
		mnTest++;
		
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
		send_I3_1(mnTest);
		send_I3_2(mnTest);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:50:09
	 * 
	 * @see pa.interfaces.send.I3_1_send#send_I3_1(int)
	 */
	@Override
	public void send_I3_1(int pnData) {
		((I3_1_receive)moModuleList.get(6)).receive_I3_1(mnTest);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:50:09
	 * 
	 * @see pa.interfaces.send.I3_2_send#send_I3_2(int)
	 */
	@Override
	public void send_I3_2(int pnData) {
		((I3_2_receive)moModuleList.get(19)).receive_I3_2(mnTest);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:45:41
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
	 * 12.07.2010, 10:45:41
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (GELBARD) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 19.03.2011, 08:36:59
	 *
	 * @param undefined
	 * @param poDS
	 * @param oSearchResult
	 */
	@Override
	public <E> void search(
			eDataType poDataType,
			ArrayList<E> poPattern,
			ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult) {
		
		ArrayList<clsPair<Integer, clsDataStructurePA>> oSearchPattern = new ArrayList<clsPair<Integer,clsDataStructurePA>>(); 

		createSearchPattern(poDataType, poPattern, oSearchPattern);
		accessKnowledgeBase(poSearchResult, oSearchPattern); 
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 18.03.2011, 19:04:29
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#createSearchPattern(pa.memorymgmt.enums.eDataType, java.lang.Object, java.util.ArrayList)
	 */
	@Override
	public <E> void createSearchPattern(eDataType poDataType, ArrayList<E> poList,
			ArrayList<clsPair<Integer, clsDataStructurePA>> poSearchPattern) {
		
		for (E oEntry : poList){
				if(oEntry instanceof clsDataStructurePA){
					poSearchPattern.add(new clsPair<Integer, clsDataStructurePA>(poDataType.nBinaryValue, (clsDataStructurePA)oEntry));
				}
				else if (oEntry instanceof clsPrimaryDataStructureContainer){
					poSearchPattern.add(new clsPair<Integer, clsDataStructurePA>(poDataType.nBinaryValue, ((clsPrimaryDataStructureContainer)oEntry).getMoDataStructure()));
				}
			}
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 14.03.2011, 22:34:44
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#accessKnowledgeBase(pa.tools.clsPair)
	 */
	@Override
	public void accessKnowledgeBase(ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> poSearchResult,
									ArrayList<clsPair<Integer, clsDataStructurePA>> poSearchPattern) {
		
		poSearchResult.addAll(moKnowledgeBaseHandler.initMemorySearch(poSearchPattern));
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:35:15
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
	 * 03.03.2011, 16:36:04
	 * 
	 * @see pa.interfaces.receive._v30.I2_19_receive#receive_I2_19(java.util.List)
	 */
	@Override
	public void receive_I2_19(List<clsDriveMesh> poData) {
		// TODO (GELBARD) - Auto-generated method stub
		
	}

}
