/**
 * E8_ConversionToSecondaryProcess.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:11:38
 */
package pa.modules;

import java.util.ArrayList;

import config.clsBWProperties;
import pa.clsInterfaceHandler;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsPrimaryInformationMesh;
import pa.datatypes.clsSecondaryInformation;
import pa.datatypes.clsSecondaryInformationMesh;
import pa.interfaces.knowledgebase.itfKnowledgeBaseAccess;
import pa.interfaces.receive.I1_6_receive;
import pa.interfaces.receive.I1_7_receive;
import pa.interfaces.receive.I5_3_receive;
import pa.interfaces.send.I1_7_send;
import pa.interfaces.send.I5_3_send;
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
public class E08_ConversionToSecondaryProcess extends clsModuleBase implements I1_6_receive, I1_7_send, I5_3_send, itfKnowledgeBaseAccess {

	public  ArrayList<clsPrimaryInformation> moDriveList_Input_old;
	public  ArrayList<clsSecondaryInformation> moDriveList_Output_old;
	
	public ArrayList<clsDriveMesh> moDriveList_Input; 
	public ArrayList<clsPair<clsSecondaryDataStructureContainer, clsDriveMesh>> moDriveList_Output; 
	
	/**
	 * DOCUMENT (deutsch) - insert description 
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
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I1_6(ArrayList<clsPrimaryInformation> poDriveList_old, ArrayList<clsDriveMesh> poDriveList) {
		moDriveList_Input_old = (ArrayList<clsPrimaryInformation>)deepCopy(poDriveList_old);
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
		moDriveList_Output = new ArrayList<clsPair<clsSecondaryDataStructureContainer, clsDriveMesh>>(); 
		
		for(clsDriveMesh oDriveMesh : moDriveList_Input){
			clsSecondaryDataStructureContainer oSecondaryCounterPart = getSecondaryCounterpart(oDriveMesh); 
			moDriveList_Output.add(new clsPair<clsSecondaryDataStructureContainer, clsDriveMesh>(oSecondaryCounterPart, oDriveMesh)); 
		}
		process_oldDT();	
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
	private clsSecondaryDataStructureContainer getSecondaryCounterpart(clsDriveMesh oDriveMesh) {
		//HZ 16.08.2010: Important! Before a search is initialized, the moSearchPattern ArrayLsit has to be cleaned. 
		moSearchPattern.clear(); 
		for(clsAssociation oAssociation : oDriveMesh.moAssociatedContent){
			//HZ: It will be searched for the drive context that is stored as TP in 
			//the associations that define oDriveMesh => Element A is always the root
			//element oDriveMesh, while element B is the associated context. 
			addToSearchPattern(eDataType.WP, oAssociation.moAssociationElementB);
		}
		addToSearchPattern(eDataType.WP, clsDataStructureGenerator.generateDataStructure(eDataType.AFFECT, 
												new clsPair<String, Object>(eDataType.AFFECT.toString(), oDriveMesh.getPleasure())));
		ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> oSearchResult = accessKnowledgeBase();
		return defineDriveContainer(oSearchResult);
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 15.08.2010, 16:56:45
	 *
	 * @param oSearchResult
	 * @return
	 */
	private clsSecondaryDataStructureContainer defineDriveContainer(ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> oSearchResult) {
		clsWordPresentation oSecondaryCounterpart = null; 
		String oWordPresentationContent = ""; 
		
		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry : oSearchResult){
			//HZ: 15.08.2010 Actually the first element is taken out of the list. 
			clsDataStructureContainer oSelectedMatch = oEntry.get(0).b; 
			for(clsAssociation oAssociation : oSelectedMatch.moAssociatedDataStructures){
				oWordPresentationContent += " " + ((clsWordPresentation)oAssociation.getLeafElement(oSelectedMatch.moDataStructure)).moContent + " ";
			}
		}
		oSecondaryCounterpart = (clsWordPresentation) clsDataStructureGenerator.generateDataStructure(eDataType.WP, 
														new clsPair<String,Object>(eDataType.WP.toString(),oWordPresentationContent));
		
		return new clsSecondaryDataStructureContainer(oSecondaryCounterpart, new ArrayList<clsAssociation>());
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

	/**
	 * DOCUMENT (zeilinger) - insert description
	 * This method is used while adapting the model from the old datatypes (pa.datatypes) to the
	 * new ones (pa.memorymgmt.datatypes) The method has to be deleted afterwards.
	 * @author zeilinger
	 * 13.08.2010, 09:56:48
	 * @deprecated
	 */
	private void process_oldDT() {
		moDriveList_Output_old = new ArrayList<clsSecondaryInformation>();
		for( clsPrimaryInformation oPriminfo : moDriveList_Input_old ) {
			if(oPriminfo instanceof clsPrimaryInformationMesh) {
				moDriveList_Output_old.add(new clsSecondaryInformationMesh(oPriminfo));
			}
			else if(oPriminfo instanceof clsPrimaryInformation) {
				moDriveList_Output_old.add(new clsSecondaryInformation(oPriminfo));
			}
		}
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
		send_I1_7(moDriveList_Output_old, moDriveList_Output);
		send_I5_3(moDriveList_Output_old, moDriveList_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:51:16
	 * 
	 * @see pa.interfaces.send.I1_7_send#send_I1_7(java.util.ArrayList)
	 */
	@Override
	public void send_I1_7(ArrayList<clsSecondaryInformation> poDriveList_old, ArrayList<clsPair<clsSecondaryDataStructureContainer, clsDriveMesh>> poDriveList) {
		((I1_7_receive)moEnclosingContainer).receive_I1_7(moDriveList_Output_old, moDriveList_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:51:16
	 * 
	 * @see pa.interfaces.send.I5_3_send#send_I5_3(java.util.ArrayList)
	 */
	@Override
	public void send_I5_3(ArrayList<clsSecondaryInformation> poDriveList_old, ArrayList<clsPair<clsSecondaryDataStructureContainer,clsDriveMesh>> poDriveList) {
		((I5_3_receive)moEnclosingContainer).receive_I5_3(moDriveList_Output_old, moDriveList_Output);	
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
	public ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> accessKnowledgeBase() {
		return moEnclosingContainer.moKnowledgeBaseHandler.initMemorySearch(moSearchPattern);
	}
}
