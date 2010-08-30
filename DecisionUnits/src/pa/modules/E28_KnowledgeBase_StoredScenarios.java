/**
 * E28_KnowledgeBase_StoredScenarios.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:56:22
 */
package pa.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import config.clsBWProperties;
import pa.clsInterfaceHandler;
import pa.interfaces.knowledgebase.itfKnowledgeBaseAccess;
import pa.interfaces.receive.I6_2_receive;
import pa.interfaces.receive.I7_2_receive;
import pa.interfaces.send.I6_2_send;
import pa.memorymgmt.datahandler.clsDataStructureGenerator;
import pa.memorymgmt.datatypes.clsAct;
import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsWordPresentation;
import pa.memorymgmt.enums.eDataType;
import pa.tools.clsPair;
import pa.tools.clsTripple;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:56:22
 * 
 */
public class E28_KnowledgeBase_StoredScenarios extends clsModuleBase implements I7_2_receive, I6_2_send, itfKnowledgeBaseAccess {

	ArrayList<clsSecondaryDataStructureContainer> moGoal_Input; 
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:56:40
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E28_KnowledgeBase_StoredScenarios(String poPrefix,
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
	 * 11.08.2009, 14:56:47
	 * 
	 * @see pa.interfaces.I7_2#receive_I7_2(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I7_2(int pnData, ArrayList<clsSecondaryDataStructureContainer> poGoal_Input) {
		mnTest += pnData;
		moGoal_Input = (ArrayList<clsSecondaryDataStructureContainer>)deepCopy(poGoal_Input);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:42
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 * 
	 * does nothing, only a counter is sent to the next module
	 * 
	 */
	@Override
	protected void process_basic() {
		mnTest++;
		//HZ It has to be discussed if E28 is required! In case it remains,
		//a loop between E27 and E28 is required; otherwise it is a problem to construct "plans"
		//"retrieveActs()" was introduced to retrieve acts from the memory, according to the 
		//actual situation. As long as the question is not solved if the loop is required,
		//E28 is not triggered; In the meantime a memory access is introduced in E27 
		retrieveActsForGoals(); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 28.08.2010, 11:23:14
	 *
	 */
	private void retrieveActsForGoals() {
		//HZ retrieving ACTS from the memory strongly depend on the 
		//design of Acts => actually it has to be differenciated between 
		//the preconditions, the action and the consequences; however
		//this may change with further implementations 
		ArrayList<clsAct> oActRes = new ArrayList<clsAct>(); 

		for(clsSecondaryDataStructureContainer oCon : moGoal_Input){
			ArrayList<clsWordPresentation> oListWP = extractWP(oCon.moAssociatedDataStructures);
			String oActualState = ((clsWordPresentation)oCon.moDataStructure).moContent; 
						
			//HZ not sure, but maybe a loop between E28 and E27 is required here; 
			// TODO: This is also a bit magic; ACTS are retrieved that include the actual object setup, no
			//	matter if it fits the "precondition" or the "consequence"; normally it is not the 
			//  task of the memory to introduce a filter that makes a difference between both parts; 
			// however it can be thought about alternatives and possibilities to retrieve acts
			// more efficiently. 
			oActRes = retrieveActs(oActualState.substring(0, oActualState.indexOf("||"))); 
		}
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 29.08.2010, 15:57:01
	 *
	 * @param oActDummy
	 * @return
	 */
	private ArrayList<clsAct> retrieveActs(String poActualState) {
		clsAct oActDummy = null;
		boolean oComplete = false; 
		String oContentDummy = ""; 
		ArrayList<clsAct> oActResult = new ArrayList<clsAct>(); 
		
		oContentDummy = getContent(poActualState); 
		
		//HZ ABBRUCHBEDINGUNG! - has to be changed
		int i = 0; 		
		
		while(oComplete != true){
			oActDummy = (clsAct)clsDataStructureGenerator.generateDataStructure(eDataType.ACT, 
			           new clsTripple<String, ArrayList<clsWordPresentation>, Object>(eDataType.ACT.name(), new ArrayList<clsWordPresentation>(), oContentDummy));
			try {
				clsAct oResult = retrieveActMatch(oActDummy); 
				oActResult.add(0, oResult); 
				oComplete = comparePreconditions(oResult.moContent, poActualState);
				oContentDummy = getContent(oResult.moContent.substring(oResult.moContent.lastIndexOf("CONSEQUENCE"))); 
			}catch(NullPointerException ex) {/*TODO tbd*/}
			
			//TODO HZ Should avoid an endless loop! DIRTY
			if(i++>10){break;}
	    }
				
		return oActResult;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 29.08.2010, 16:16:46
	 *
	 * @param moContent
	 * @param poContent
	 * @return
	 */
	private boolean comparePreconditions(String poActContent, String poActualState) {
		String oTag = "PRECONDITION"; 
		HashMap<String, ArrayList<String>> oState  = new HashMap<String, ArrayList<String>>(); 
		String [] oDividedState = poActContent.substring(poActContent.indexOf(oTag) + oTag.length(), poActContent.indexOf("||")).split("[|]");
		
		//Divide to OR and AND relations
		for (String oCondition : oDividedState){
			if(!oCondition.equals("") && !oCondition.substring(0, oCondition.indexOf(":")).equals("RELATION")){
				String oContentType = oCondition.substring(0, oCondition.indexOf(":")); 
				
				if(oState.containsKey(oContentType)){
					oState.get(oContentType).add(oCondition); 
				}
				else {
					oState.put(oContentType, new ArrayList<String>(Arrays.asList(oCondition))); 
				}				
			}
		}
		
		for(Map.Entry<String, ArrayList<String>> oEntry : oState.entrySet()){
			for(String oCondition : oEntry.getValue()){
				if(!poActualState.contains(oCondition) && oEntry.getValue().indexOf(oCondition)==(oEntry.getValue().size()-1)){
					return false; 
				}
			}
		}
	
		return true;
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
		oContent = "PRECONDITION||ACTION||CONSEQUENCE|" + moContent; 
		return oContent;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 28.08.2010, 11:47:38
	 *
	 * @param moAssociatedDataStructures
	 * @return
	 */
	private ArrayList<clsWordPresentation> extractWP(ArrayList<clsAssociation> poAssociatedDataStructures) {
		ArrayList<clsWordPresentation> oListWP = new ArrayList<clsWordPresentation>(); 
		
		for(clsAssociation oAss : poAssociatedDataStructures){
			oListWP.add((clsWordPresentation)oAss.getLeafElement()); 
		}
		return oListWP;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 28.08.2010, 11:50:46
	 *
	 * @param oActDummy
	 * @return
	 */
	private clsAct retrieveActMatch(clsAct oActDummy) {
		moSearchPattern.clear(); 
			
		addToSearchPattern(eDataType.UNDEFINED, oActDummy); 
		//As only one search pattern was send to the memory, the returning hashmap
		//has only one entry that has the key 0 => hence this entry can be directly
		//read out of the Hashmap
		ArrayList<clsPair<Double,clsDataStructureContainer>> oResult = accessKnowledgeBase().get(0); 
		//The Double value in clsPair defines the matching score of the retrieved Act and the 
		//search pattern. This was ok for the primary data structures as there have not been 
		//any logic relations within the data structures. Now there exist acts with "or" and "and"
		//relations that may include many possibilities (see the acts for changing locations)
		//=> actually a lot of acts are retrieved from the memory; E27 will have to sort them out.
		//It has to be verified if this is a good solution, or if the comparison mode for the clsAct 
		//datatype has to be changed
		
		return (clsAct)oResult.get(0).b.moDataStructure; 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:42
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I6_2(mnTest);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:56:52
	 * 
	 * @see pa.interfaces.send.I6_2_send#send_I6_2(int)
	 */
	@Override
	public void send_I6_2(int pnData) {
		((I6_2_receive)moEnclosingContainer).receive_I6_2(mnTest);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:47:46
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
	 * 12.07.2010, 10:47:46
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
	 * 12.08.2010, 21:11:20
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
	 * 16.08.2010, 10:16:09
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#addToSearchPattern(pa.memorymgmt.enums.eDataType, pa.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void addToSearchPattern(eDataType oReturnType, clsDataStructurePA poSearchPattern) {
		moSearchPattern.add(new clsPair<Integer, clsDataStructurePA>(oReturnType.nBinaryValue, poSearchPattern)); 		
	}

}
