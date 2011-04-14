/**
 * E34_KnowledgeAboutReality2.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 27.04.2010, 10:38:16
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;
import config.clsBWProperties;
import pa.interfaces._v30.eInterfaces;
import pa.interfaces.knowledgebase.itfKnowledgeBaseAccess;
import pa.interfaces.receive._v30.I7_3_receive;
import pa.interfaces.receive._v30.I7_5_receive;
import pa.interfaces.send._v30.I7_5_send;
import pa.memorymgmt.clsKnowledgeBaseHandler;
import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsWordPresentation;
import pa.memorymgmt.enums.eDataType;
import pa.tools.clsPair;

/**
 * DOCUMENT (KOHLHAUSER) - insert description 
 * 
 * @author deutsch
 * 27.04.2010, 10:38:16
 * 
 */
public class E34_KnowledgeAboutReality_2 extends clsModuleBase implements I7_3_receive, I7_5_send, itfKnowledgeBaseAccess {
	public static final String P_MODULENUMBER = "34";
	
	private clsKnowledgeBaseHandler moKnowledgeBaseHandler; 
	private ArrayList<clsPair<Integer, clsDataStructurePA>> moSearchPattern;
	
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
			HashMap<Integer, clsModuleBase> poModuleList, HashMap<eInterfaces, ArrayList<Object>> poInterfaceData, clsKnowledgeBaseHandler poKnowledgeBaseHandler) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		
		moSearchPattern = new ArrayList<clsPair<Integer,clsDataStructurePA>>();
		moKnowledgeBaseHandler = poKnowledgeBaseHandler;
		
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
		
		html += listToHTML("moSearchPattern", moSearchPattern);
		html += valueToHTML("moKnowledgeBaseHandler", moKnowledgeBaseHandler);
		
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
		// TODO (KOHLHAUSER) - Auto-generated method stub
		
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
		send_I7_5(1);
		
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
	 * 03.03.2011, 16:58:01
	 * 
	 * @see pa.modules._v30.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}

}
