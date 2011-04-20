/**
 * E8_ConversionToSecondaryProcess.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:11:38
 */
package pa._v19.modules;

import java.util.ArrayList;
import java.util.Arrays;

import config.clsBWProperties;
import pa._v19.clsInterfaceHandler;
import pa._v19.interfaces.receive.I1_6_receive;
import pa._v19.interfaces.receive.I1_7_receive;
import pa._v19.interfaces.receive.I5_3_receive;
import pa._v19.interfaces.send.I1_7_send;
import pa._v19.interfaces.send.I5_3_send;
import pa._v19.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v19.memorymgmt.datatypes.clsAssociation;
import pa._v19.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v19.memorymgmt.datatypes.clsDataStructurePA;
import pa._v19.memorymgmt.datatypes.clsDriveMesh;
import pa._v19.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v19.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v19.memorymgmt.datatypes.clsWordPresentation;
import pa._v19.memorymgmt.enums.eDataType;
import pa._v19.tools.clsPair;
import pa._v19.interfaces.knowledgebase.itfKnowledgeBaseAccess;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:11:38
 * 
 */
@Deprecated
public class E08_ConversionToSecondaryProcess extends clsModuleBase implements I1_6_receive, I1_7_send, I5_3_send, itfKnowledgeBaseAccess {

	public ArrayList<clsDriveMesh> moDriveList_Input; 
	public ArrayList<clsSecondaryDataStructureContainer> moDriveList_Output; 
	
	/**
	 * 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:11:58
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E08_ConversionToSecondaryProcess(String poPrefix,
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
	 * 11.08.2009, 14:12:33
	 * 
	 * @see pa.interfaces.I1_6#receive_I1_6(int)
	 */
	@SuppressWarnings("unchecked") //deepcopy can perform unchecked copy only
	@Override
	public void receive_I1_6(ArrayList<clsDriveMesh> poDriveList) {
		moDriveList_Input = (ArrayList<clsDriveMesh>)deepCopy(poDriveList); 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:14
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		moDriveList_Output = new ArrayList<clsSecondaryDataStructureContainer>(); 
		
		for(clsDriveMesh oDM : moDriveList_Input){
			convertToSecondary(oDM);  
		}
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 15.08.2010, 13:08:29
	 *
	 * @param oDriveMesh
	 * @return
	 */
	private void convertToSecondary(clsDriveMesh poDM) throws NullPointerException{
		//HZ 16.08.2010: Important! Before a search is initialized, the moSearchPattern ArrayLsit has to be cleaned. 
		clsAssociation oDM_A = null; 
		clsAssociation oAff_A = null;
		clsWordPresentation oResWP = null; 
		clsSecondaryDataStructureContainer oSec_CON = null; 
		String oContentWP = ""; 
		
		try{
			
			oDM_A = getDMWP(poDM);  
			oAff_A = getAffectWP(poDM); 
			oContentWP =   ((clsWordPresentation)oDM_A.getLeafElement()).getMoContent() 
			             + ":" 
			             + ((clsWordPresentation)oAff_A.getLeafElement()).getMoContent(); 	
			
			oResWP = (clsWordPresentation)clsDataStructureGenerator.generateDataStructure(eDataType.WP, new clsPair<String, Object>(eDataType.WP.name(), oContentWP)); 
			oSec_CON = new clsSecondaryDataStructureContainer(oResWP, new ArrayList<clsAssociation>(Arrays.asList(oDM_A, oAff_A)));
			moDriveList_Output.add(oSec_CON);
		
		} catch (IndexOutOfBoundsException ex1){ //tbd;
		} catch (NullPointerException ex2){/*tbd;*/}
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 23.08.2010, 21:20:30
	 *
	 * @param oDM
	 * @return
	 */
	private clsAssociation getDMWP(clsDriveMesh poDM) {
		clsAssociation oAssDM = null; 
		
		for(clsAssociation oAssociation : poDM.getMoAssociatedContent()){
			//HZ: It will be searched for the drive context that is stored as TP in 
			//the associations that define oDriveMesh => Element A is always the root
			//element oDriveMesh, while element B is the associated context. 
	
				oAssDM = (clsAssociation)getWP(oAssociation.getMoAssociationElementB());
		}
		
		return oAssDM;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 23.08.2010, 21:19:19
	 *
	 * @param oDM
	 * @return
	 */
	private clsAssociation getAffectWP(clsDriveMesh poDM) {
		clsDataStructurePA oAffect = null; 
		oAffect = clsDataStructureGenerator.generateDataStructure(eDataType.AFFECT, new clsPair<String, Object>(eDataType.AFFECT.toString(), poDM.getPleasure()));
		return (clsAssociation)getWP(oAffect); 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 23.08.2010, 21:24:38
	 *
	 * @param poDataStructure
	 * @return
	 */
	private clsAssociation getWP(clsDataStructurePA poDataStructure){
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult 
			= new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
		
		search(eDataType.WP, new ArrayList<clsDataStructurePA>(Arrays.asList(poDataStructure)), oSearchResult); 
		clsAssociation oAssWP = null; 
		
		try{
			oAssWP = (clsAssociation)oSearchResult.get(0).get(0).b.getMoAssociatedDataStructures().get(0);
		} catch (IndexOutOfBoundsException ex1){return null;
		} catch (NullPointerException ex2){return null;}
			
		return oAssWP;  
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:14
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		//HZ: null is a placeholder for the homeostatic information formed out of objects of the type pa._v19.memorymgmt.datatypes 
		send_I1_7(moDriveList_Output);
		send_I5_3(moDriveList_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:51:16
	 * 
	 * @see pa.interfaces.send.I1_7_send#send_I1_7(java.util.ArrayList)
	 */
	@Override
	public void send_I1_7(ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		((I1_7_receive)moEnclosingContainer).receive_I1_7(moDriveList_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:51:16
	 * 
	 * @see pa.interfaces.send.I5_3_send#send_I5_3(java.util.ArrayList)
	 */
	@Override
	public void send_I5_3(ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		((I5_3_receive)moEnclosingContainer).receive_I5_3(moDriveList_Output);	
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:45:47
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
	 * 12.07.2010, 10:45:47
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		
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
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#createSearchPattern(pa._v19.memorymgmt.enums.eDataType, java.lang.Object, java.util.ArrayList)
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
		
		poSearchResult.addAll(moEnclosingContainer.moKnowledgeBaseHandler.initMemorySearch(poSearchPattern));
	}
}
