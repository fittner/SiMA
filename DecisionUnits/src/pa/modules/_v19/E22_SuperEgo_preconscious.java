/**
 * E22_SuperEgo_preconscious.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:45:01
 */
package pa.modules._v19;

import java.util.ArrayList;

import config.clsBWProperties;
import pa._v19.clsInterfaceHandler;
import pa.interfaces.knowledgebase.itfKnowledgeBaseAccess;
import pa.interfaces.receive._v19.I1_7_receive;
import pa.interfaces.receive._v19.I2_11_receive;
import pa.interfaces.receive._v19.I3_3_receive;
import pa.interfaces.send._v19.I3_3_send;
import pa.memorymgmt.datahandler.clsDataStructureGenerator;
import pa.memorymgmt.datatypes.clsAct;
import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsWordPresentation;
import pa.memorymgmt.enums.eActState;
import pa.memorymgmt.enums.eDataType;
import pa.tools.clsPair;
import pa.tools.clsTripple;


/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:45:01
 * 
 */
public class E22_SuperEgo_preconscious extends clsModuleBase implements I1_7_receive, I2_11_receive, I3_3_send, itfKnowledgeBaseAccess {

	public ArrayList<clsSecondaryDataStructureContainer> moPerception; 
	//private ArrayList<clsSecondaryDataStructureContainer> moDriveList;  HZ - not used up to now
	public ArrayList<clsAct> moRuleList; 
	
	public ArrayList<clsPair<Double,clsDataStructureContainer>> moRetrieveResult4Inspectors;
	
	/**
	 * 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:45:42
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E22_SuperEgo_preconscious(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
		applyProperties(poPrefix, poProp);	
		
		moRuleList = new ArrayList<clsAct>();
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
		mnPsychicInstances = ePsychicInstances.SUPEREGO;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:45:50
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	//@SuppressWarnings("unchecked")
	@Override
	public void receive_I1_7(ArrayList<clsSecondaryDataStructureContainer>poDriveList) {
		//moDriveList = (ArrayList<clsSecondaryDataStructureContainer>)this.deepCopy(poDriveList);		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:45:50
	 * 
	 * @see pa.interfaces.I2_11#receive_I2_11(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_11(ArrayList<clsSecondaryDataStructureContainer> poPerception) {
		moPerception = (ArrayList<clsSecondaryDataStructureContainer>) this.deepCopy(poPerception); 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:16
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		moRuleList.clear(); 
		getRules(); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 24.09.2010, 22:29:26
	 *
	 */
	private void getRules() {
		for(clsSecondaryDataStructureContainer oCon : moPerception){
			ArrayList<clsAct> oActList = getMatchingAct(((clsWordPresentation)oCon.getMoDataStructure()).getMoContent()); 
			moRuleList.addAll(oActList); 
		}
		//TODO - do the same for Homeostatic State
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 03.09.2010, 18:56:20
	 *
	 * @param oActualGoal
	 * @return
	 */
	private ArrayList<clsAct> getMatchingAct(String poActualState) {
		String oActContent = getContent(poActualState);
		clsAct oAct = generateAct(oActContent); 
			
		return retrieveMatch(oAct); 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 29.08.2010, 12:05:33
	 *
	 * @param moContent
	 * @return
	 */
	private String getContent(String moContent) {
		String oContent = ""; 
		//Acts are retrieved by the consequence they have on the agent - hence the content String of the Act is constructed
		//here - only the consequence part is filled
		//The string looks like: "PRECONDITION||ACTION||CONSEQUENCE|NOURISH" or "PRECONDITION||ACTION||CONSEQUENCE|LOCATION:xy|"
		oContent = eActState.PRECONDITION.name()+ "|" + moContent + "||" 
				   + eActState.ACTION.name() + "||" 
				   + eActState.CONSEQUENCE.name() + "||"; 
	
		return oContent;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 01.09.2010, 16:53:58
	 *
	 * @param poActualGoal
	 * @return
	 */
	private clsAct generateAct(String poActualGoal) {
		clsAct oAct = (clsAct)clsDataStructureGenerator.generateDataStructure(eDataType.ACT, new clsTripple<String, ArrayList<clsWordPresentation>, Object>(
				eDataType.ACT.name(), new ArrayList<clsWordPresentation>(), poActualGoal));
		
		return oAct;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 28.08.2010, 11:50:46
	 *
	 * @param oActDummy
	 * @param oDummy 
	 * @return
	 */
	private ArrayList<clsAct> retrieveMatch(clsAct poDummy) {
		moSearchPattern.clear(); 
			
		addToSearchPattern(eDataType.UNDEFINED, poDummy); 
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oResult = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
		accessKnowledgeBase(oResult); 
		//The Double value in clsPair defines the matching score of the retrieved Act and the 
		//search pattern. This was ok for the primary data structures as there have not been 
		//any logic relations within the data structures. Now there exist acts with "or" and "and"
		//relations that may include many possibilities (see the acts for changing locations)
		//=> actually a lot of acts are retrieved from the memory; E27 will have to sort them out.
		//It has to be verified if this is a good solution, or if the comparison mode for the clsAct 
		//datatype has to be changed
		
		//moRetrieveResult4Inspectors = (ArrayList<clsPair<Double, clsDataStructureContainer>>) oResult.entrySet().iterator().next(); // doing this for showing the results in the inspectors, may change later
		
		//TODO dirty hack by clemens, for testing the search result display. change me later!
		try
		{
			for(ArrayList<clsPair<Double,clsDataStructureContainer>> oEntry : oResult){
				moRetrieveResult4Inspectors = oEntry;
				break;
			}
		}catch(Exception ex)
		{}
		
		return extractSearchResult(oResult);
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 30.08.2010, 15:16:31
	 * @param poTestAct 
	 *
	 * @param oResult
	 * @return
	 */
	private ArrayList<clsAct> extractSearchResult(ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> oResult) {
		
		ArrayList<clsAct> oActs = new ArrayList<clsAct>();
		
		//HZ Up to now the match-value is not taken into account of the selection process
		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry : oResult){
			for(clsPair<Double, clsDataStructureContainer> oPair : oEntry){
				clsAct oAct = (clsAct)oPair.b.getMoDataStructure(); 
				
				//FIXME - again, there has to be a different evaluation of the current Super-Ego Rules
				if(oAct.getMoContent().contains("UNPLEASURE")){
					oActs.add((clsAct)oPair.b.getMoDataStructure());
				}
			}
		}
		
		return oActs;
	}
	
//	/**
//	 * DOCUMENT (zeilinger) - insert description
//	 *
//	 * @author zeilinger
//	 * 01.09.2010, 10:56:57
//	 *
//	 * @param oTempActs
//	 * @param oAct
//	 * @return
//	 */
//	private ArrayList<clsAct> fullMatch(ArrayList<clsAct> poTempActs, clsAct poAct) {
//			
//			ArrayList<clsAct> oFullMatch = new ArrayList<clsAct>(); 
//			String [] oPreConditionUnknown = poAct.moContent.substring(poAct.moContent.indexOf("|", poAct.moContent.indexOf(eActState.PRECONDITION.name())) + 1 , poAct.moContent.indexOf(eActState.ACTION.name())).split("[|]");
//			
//			//FIXME - SHould only be done by the information representation
//			for(clsAct oEntry : poTempActs){
//					double nMS = oEntry.compareTo(poAct); 
//					
//					if(nMS == oPreConditionUnknown.length){				
//						oFullMatch.add(oEntry); 
//					}
//			}
//			return oFullMatch;
//	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:16
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I3_3(mnTest, moRuleList);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:50:01
	 * 
	 * @see pa.interfaces.send.I3_3_send#send_I3_3(int)
	 */
	@Override
	public void send_I3_3(int pnData, ArrayList<clsAct> poRuleList) {
		((I3_3_receive)moEnclosingContainer).receive_I3_3(moRuleList);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:47:13
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
	 * 12.07.2010, 10:47:13
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
	 * 12.08.2010, 20:58:44
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#accessKnowledgeBase(java.util.ArrayList)
	 */
	@Override
	public void accessKnowledgeBase(ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> poSearchResult) {
		poSearchResult.addAll(moEnclosingContainer.moKnowledgeBaseHandler.initMemorySearch(moSearchPattern));
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 16.08.2010, 10:15:54
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#addToSearchPattern(pa.memorymgmt.enums.eDataType, pa.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void addToSearchPattern(eDataType oReturnType,clsDataStructurePA poSearchPattern) {
		moSearchPattern.add(new clsPair<Integer, clsDataStructurePA>(oReturnType.nBinaryValue, poSearchPattern));
	}
}
