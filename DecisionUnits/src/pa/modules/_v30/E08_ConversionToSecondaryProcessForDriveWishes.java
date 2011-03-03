/**
 * E8_ConversionToSecondaryProcess.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:11:38
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import config.clsBWProperties;
import pa.interfaces.knowledgebase.itfKnowledgeBaseAccess;
import pa.interfaces.receive._v30.I1_6_receive;
import pa.interfaces.receive._v30.I1_7_receive;
import pa.interfaces.receive._v30.I5_3_receive;
import pa.interfaces.send._v30.I1_7_send;
import pa.interfaces.send._v30.I5_3_send;
import pa.memorymgmt.clsKnowledgeBaseHandler;
import pa.memorymgmt.datahandler.clsDataStructureGenerator;
import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsWordPresentation;
import pa.memorymgmt.enums.eDataType;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:11:38
 * 
 */
public class E08_ConversionToSecondaryProcessForDriveWishes extends clsModuleBase implements 
                 I1_6_receive, I1_7_send, I5_3_send, itfKnowledgeBaseAccess {
	
    private clsKnowledgeBaseHandler moKnowledgeBaseHandler;
    public static final String P_MODULENUMBER = "08";
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:42:48
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E08_ConversionToSecondaryProcessForDriveWishes(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, clsKnowledgeBaseHandler poKnowledgeBaseHandler)
			throws Exception {
		super(poPrefix, poProp, poModuleList);
		
		moKnowledgeBaseHandler = poKnowledgeBaseHandler;
		
		applyProperties(poPrefix, poProp);
	}

	public ArrayList<clsDriveMesh> moDriveList_Input; 
	public ArrayList<clsSecondaryDataStructureContainer> moDriveList_Output; 

	
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
			oContentWP =   ((clsWordPresentation)oDM_A.getLeafElement()).moContent 
			             + ":" 
			             + ((clsWordPresentation)oAff_A.getLeafElement()).moContent; 	
			
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
		
		for(clsAssociation oAssociation : poDM.moAssociatedContent){
			//HZ: It will be searched for the drive context that is stored as TP in 
			//the associations that define oDriveMesh => Element A is always the root
			//element oDriveMesh, while element B is the associated context. 
	
				oAssDM = (clsAssociation)getWP(oAssociation.moAssociationElementB);
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
		moSearchPattern.clear(); 
		addToSearchPattern(eDataType.WP, poDataStructure); 
		
		clsAssociation oAssWP = null; 
		
		try{
			oAssWP = (clsAssociation)accessKnowledgeBase().get(0).get(0).b.moAssociatedDataStructures.get(0);
		} catch (IndexOutOfBoundsException ex1){return null;
		} catch (NullPointerException ex2){return null;}
			
		return oAssWP;  
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 15.08.2010, 13:41:53
	 *
	 * @param wp
	 * @param oDriveMesh
	 */
	@Override
	public void addToSearchPattern(eDataType poReturnType, clsDataStructurePA poSearchPattern) {
		moSearchPattern.add(new clsPair<Integer, clsDataStructurePA>(poReturnType.nBinaryValue, poSearchPattern)); 
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
		//HZ: null is a placeholder for the homeostatic information formed out of objects of the type pa.memorymgmt.datatypes 
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
		((I1_7_receive)moModuleList.get(22)).receive_I1_7(moDriveList_Output);
		((I1_7_receive)moModuleList.get(23)).receive_I1_7(moDriveList_Output);
		((I1_7_receive)moModuleList.get(26)).receive_I1_7(moDriveList_Output);
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
		((I5_3_receive)moModuleList.get(20)).receive_I5_3(moDriveList_Output);	
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
		// TODO (deutsch) - Auto-generated method stub
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
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 12.08.2010, 21:12:07
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#accessKnowledgeBase(java.util.ArrayList)
	 */
	@Override
	public HashMap<Integer,ArrayList<clsPair<Double,clsDataStructureContainer>>> accessKnowledgeBase() {
		return moKnowledgeBaseHandler.initMemorySearch(moSearchPattern);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:42:55
	 * 
	 * @see pa.modules._v30.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}
}
