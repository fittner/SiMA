/**
 * E8_ConversionToSecondaryProcess.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:11:38
 */
package pa._v30.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsBWProperties;
import pa._v30.tools.clsPair;
import pa._v30.tools.toHtml;
import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.modules.I1_6_receive;
import pa._v30.interfaces.modules.I1_7_receive;
import pa._v30.interfaces.modules.I1_7_send;
import pa._v30.interfaces.modules.I5_3_receive;
import pa._v30.interfaces.modules.I5_3_send;
import pa._v30.memorymgmt.clsKnowledgeBaseHandler;
import pa._v30.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v30.memorymgmt.datatypes.clsAssociation;
import pa._v30.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v30.memorymgmt.datatypes.clsDataStructurePA;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;
import pa._v30.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v30.memorymgmt.datatypes.clsWordPresentation;
import pa._v30.memorymgmt.enums.eDataType;
import statictools.clsExceptionUtils;

/**
 * DOCUMENT (KOHLHAUSER) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:11:38
 * 
 */
public class E08_ConversionToSecondaryProcessForDriveWishes extends clsModuleBaseKB implements 
                 I1_6_receive, I1_7_send, I5_3_send {
	
	public static final String P_MODULENUMBER = "08";
	
	private ArrayList<clsDriveMesh> moDriveList_Input; 
	private ArrayList<clsSecondaryDataStructureContainer> moDriveList_Output; 

	/**
	 * DOCUMENT (KOHLHAUSER) - insert description 
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
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, clsKnowledgeBaseHandler poKnowledgeBaseHandler)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);
		
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
		
		html += toHtml.listToHTML("moDriveList_Input", moDriveList_Input);
		html += toHtml.listToHTML("moDriveList_Output", moDriveList_Output);		
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
		ArrayList<clsAssociation> oDM_A = new ArrayList<clsAssociation>(); 
		ArrayList<clsAssociation> oAff_A = new ArrayList<clsAssociation>();
		
		moDriveList_Output = new ArrayList<clsSecondaryDataStructureContainer>(); 
		oDM_A = getDMWP(); 
		oAff_A = getAffectWP(); 
		generateSecondaryContainer(oDM_A, oAff_A); 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 19.03.2011, 10:01:29
	 *
	 * @param oDM_A
	 * @param oAff_A
	 */
	private void generateSecondaryContainer(ArrayList<clsAssociation> oDM_A, ArrayList<clsAssociation> oAff_A) {
		
		for(int index = 0; index < moDriveList_Input.size(); index++){
			try {
			String oContentWP = ((clsWordPresentation)oDM_A.get(index).getLeafElement()).getMoContent() 
									+ ":" 
									+ ((clsWordPresentation)oAff_A.get(index).getLeafElement()).getMoContent();
			clsWordPresentation oResWP = (clsWordPresentation)clsDataStructureGenerator.generateDataStructure(eDataType.WP, 
										 new clsPair<String, Object>(eDataType.WP.name(), oContentWP));
			clsSecondaryDataStructureContainer oCon =  new clsSecondaryDataStructureContainer(oResWP, 
										 new ArrayList<clsAssociation>(Arrays.asList(oDM_A.get(index), oAff_A.get(index))));
			moDriveList_Output.add(oCon);
			} catch (java.lang.IndexOutOfBoundsException e) {
				System.out.println(clsExceptionUtils.getCustomStackTrace(e)); //FIXME (Zeilinger): protege data structure is not complete. oDM_A is missing entries for sleep and relax. i have tried everything ... pleasse HEL!!! TD 2011/04/22
			}
		}
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 19.03.2011, 09:17:07
	 *
	 * @return
	 */
	private ArrayList<clsAssociation> getDMWP() {
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
		ArrayList<clsDataStructurePA> oPattern = new ArrayList<clsDataStructurePA>();
		ArrayList<clsAssociation> oRetVal = new ArrayList<clsAssociation>(); 
		
		extractAssociatedElement(oPattern); 
		search(eDataType.WP, oPattern, oSearchResult);
		extractAssociations(oRetVal, oSearchResult);
		
		return oRetVal;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 19.03.2011, 09:39:27
	 *
	 * @param oPattern
	 */
	private void extractAssociatedElement(ArrayList<clsDataStructurePA> poPattern) {
		for(clsDriveMesh oEntry : moDriveList_Input){
			for(clsAssociation oAssociation : oEntry.getMoAssociatedContent()){
				poPattern.add(oAssociation.getMoAssociationElementB()); 
			}
		}
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 19.03.2011, 09:40:18
	 *
	 * @param oRetVal
	 * @param oSearchResult
	 */
	private void extractAssociations(ArrayList<clsAssociation> poRetVal,
			ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult) {
		
			for(ArrayList<clsPair<Double,clsDataStructureContainer>> oEntry : poSearchResult){
				if(oEntry.size() > 0){
					clsPair <Double,clsDataStructureContainer> oBestMatch = oEntry.get(0);
					poRetVal.addAll(oBestMatch.b.getMoAssociatedDataStructures()); 
			}
		}
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 19.03.2011, 09:41:39
	 *
	 * @return
	 */
	private ArrayList<clsAssociation> getAffectWP() {
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
		ArrayList<clsDataStructurePA> oPattern = new ArrayList<clsDataStructurePA>();
		ArrayList<clsAssociation> oRetVal = new ArrayList<clsAssociation>(); 

		extractAffect(oPattern);
		search(eDataType.WP, oPattern, oSearchResult); 
		extractAssociations(oRetVal, oSearchResult);
		
		return oRetVal;
	}

	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 19.03.2011, 09:59:26
	 *
	 * @param oPattern
	 */
	private void extractAffect(ArrayList<clsDataStructurePA> poPattern) {
		for(clsDriveMesh oEntry : moDriveList_Input){
			clsDataStructurePA oAffect = clsDataStructureGenerator.generateDataStructure(eDataType.AFFECT, 
					new clsPair<String, Object>(eDataType.AFFECT.toString(), oEntry.getPleasure()));
			
			poPattern.add(oAffect);
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
		//HZ: null is a placeholder for the homeostatic information formed out of objects of the type pa._v30.memorymgmt.datatypes 
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
		((I1_7_receive)moModuleList.get(22)).receive_I1_7(poDriveList);
		((I1_7_receive)moModuleList.get(23)).receive_I1_7(poDriveList);
		((I1_7_receive)moModuleList.get(26)).receive_I1_7(poDriveList);
		
		putInterfaceData(I1_7_send.class, poDriveList);
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
		((I5_3_receive)moModuleList.get(20)).receive_I5_3(poDriveList);	
		
		putInterfaceData(I5_3_send.class, poDriveList);		
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
		// TODO (KOHLHAUSER) - Auto-generated method stub
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
		// TODO (KOHLHAUSER) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
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

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v30.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "For the incoming thing presentations fitting word presentations are selected from memory. The whole packagething presentations, word presentations, and quota of affectsare now converted into a form which can be used by secondary process modules. The drive contents are now drive wishes. ";
	}	
}
