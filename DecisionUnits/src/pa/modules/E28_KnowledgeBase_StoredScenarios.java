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
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import config.clsBWProperties;
import pa.clsInterfaceHandler;
import pa.enums.eActState;
import pa.interfaces.knowledgebase.itfKnowledgeBaseAccess;
import pa.interfaces.receive.I6_2_receive;
import pa.interfaces.receive.I7_2_receive;
import pa.interfaces.send.I6_2_send;
import pa.memorymgmt.datahandler.clsDataStructureGenerator;
import pa.memorymgmt.datatypes.clsAct;
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
	private final Integer mnNodeLimit = 100;
	
	ArrayList<clsSecondaryDataStructureContainer> moGoal_Input; 
	ArrayList<clsAct> moPlanOutput; 
			
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
		moPlanOutput = retrieveActsForGoals(); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 28.08.2010, 11:23:14
	 * @return 
	 *
	 */
	private ArrayList<clsAct> retrieveActsForGoals() {
		//HZ retrieving ACTS from the memory strongly depend on the 
		//design of Acts => actually it has to be differenciated between 
		//the preconditions, the action and the consequences; however
		//this may change with further implementations 
		ArrayList<clsAct> oActResult = new ArrayList<clsAct>(); 

		for(clsSecondaryDataStructureContainer oCon : moGoal_Input){
			String oActualState = ((clsWordPresentation)oCon.moDataStructure).moContent; 
			//HZ not sure, but maybe a loop between E28 and E27 is required here; 
			// TODO: This is also a bit magic; ACTS are retrieved that include the actual object setup, no
			//	matter if it fits the "precondition" or the "consequence"; normally it is not the 
			//  task of the memory to introduce a filter that makes a difference between both parts; 
			// however it can be thought about alternatives and possibilities to retrieve acts
			// more efficiently. 
			oActResult = backwardChaining(oActualState); 
			
			System.out.println("plan size " + oActResult.size()); 
			
			for(clsAct oresAct : oActResult){
				System.out.println("Step " + oresAct.moContent.substring(oresAct.moContent.indexOf("ACTION") +  6, oresAct.moContent.indexOf("CONSEQUENCE")) ); 
			}
		}
		
		return oActResult; 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 31.08.2010, 10:06:02
	 *
	 * @param oGoal
	 * @param poActualState
	 * @return 
	 */
	private ArrayList<clsAct> backwardChaining(String poTargetState) {
		
		String oActualGoal; 
		String oDelimiter = "||";
		boolean oSuccess = false; 
						
		clsAct oActTemp; 
		Node parentNode; 
		Graph oGraph = new Graph();
		ArrayList<clsAct> oRetVal = new ArrayList<clsAct>(); 
		
		
		oActualGoal = getContent(poTargetState.substring(0, poTargetState.indexOf(oDelimiter)));
		oActTemp = generateAct(oActualGoal); 
		oActTemp = retrieveMatch(oActTemp).get(0); 
		parentNode = new Node(oActTemp, null); 
		oGraph.setRootNode(parentNode);
		oGraph.addNode(parentNode); 
				
		while(!oSuccess){
			
			ArrayList<Node> oChildLessNode = oGraph.getChildLessNodes(); 
			
			for(Node oNode : oChildLessNode){
					
					ArrayList<clsAct> oActTempList = new ArrayList<clsAct>();
					String oPreCondition = getPreCondition(oNode.label.moContent);  
					oActualGoal = getContent(oPreCondition); 
					oActTemp = generateAct(oActualGoal); 
					oActTempList = retrieveMatch(oActTemp); 
					oActTempList = fullMatch(oActTempList, oActTemp); 
					
					oGraph.addChildren(oNode, oActTempList); 
					oRetVal = bf_Search(poTargetState, oGraph); 
					
					if(oRetVal.size() > 0 || oActTempList.size() == 0){oSuccess = true;}
			}
		}
		
		if(oRetVal.size() == 0){
			oRetVal.add(generateAct(getContent("ACTION:RANDOMIZED")));
		}
		
		return oRetVal; 
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
	 * 01.09.2010, 10:56:57
	 *
	 * @param oTempActs
	 * @param oAct
	 * @return
	 */
	private ArrayList<clsAct> fullMatch(ArrayList<clsAct> poTempActs, clsAct poAct) {
			
			ArrayList<clsAct> oFullMatch = new ArrayList<clsAct>(); 
			String [] oConsequenceUnknown = poAct.moContent.substring(poAct.moContent.indexOf("|", poAct.moContent.indexOf("CONSEQUENCE")) + 1).split("[|]");
			
			for(clsAct oEntry : poTempActs){
					double nMS = oEntry.compareTo(poAct); 
					
					if(nMS == oConsequenceUnknown.length){				
						oFullMatch.add(oEntry); 
					}
			}
		
			return oFullMatch;
	}

	private ArrayList<clsAct> bf_Search(String poTargetState, Graph poGraph){
		
		boolean oSuccess = false;
		ArrayList<clsAct> oPath = new ArrayList<clsAct>(); 
		Queue <Node> oQueue = new LinkedList<Node>();
		oQueue.add(poGraph.getRootNode()); 
		poGraph.getRootNode().visited = true; 
		
		while(!oQueue.isEmpty()){
				Node oNode = (Node) oQueue.remove();
				Node oChild = null; 
			
				while((oChild = poGraph.getUnvisitedChildNode(oNode)) != null){
						oChild.visited = true; 
						oQueue.add(oChild); 
						oSuccess = comparePreConditions(oChild.label.moContent, poTargetState);
					   
						if(oSuccess) {
								oQueue.clear(); 
								oPath = poGraph.getPath(oChild); 
								break;
						}
				}
		} 
		poGraph.setUnvisited(); 
		
		return oPath; 
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
	private boolean comparePreConditions(String poActContent, String poActualState) {
		HashMap<String, ArrayList<String>> oState  = new HashMap<String, ArrayList<String>>(); 
				
		divideState(oState, poActContent); 
		return checkCondition(oState, poActualState); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 01.09.2010, 22:27:34
	 *
	 * @param oState
	 * @param poActualState
	 */
	private boolean checkCondition(HashMap<String, ArrayList<String>> poState, String poActualState) {
		boolean oRetVal = false; 
		
		loop:
		for(Map.Entry<String, ArrayList<String>> oEntry : poState.entrySet()){
				oRetVal = false; 
				
				for(String oCondition : oEntry.getValue()){
						if(poActualState.contains(oCondition) ){
							oRetVal = true; 
							break; 
						}
						else if(oEntry.getValue().indexOf(oCondition) == oEntry.getValue().size() - 1 ) {
							break loop; 
						}
				}
		}
		
		return oRetVal; 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 01.09.2010, 21:02:37
	 *
	 * @param poState
	 * @param poActContent
	 */
	private void divideState(HashMap<String, ArrayList<String>> poState, String poActContent) {
		String oTag = getPreCondition(poActContent);
		String [] oDividedState = oTag.split("[|]"); 

		//Divide to OR and AND relations
		for (String oCondition : oDividedState){
					String oContentType = oCondition.substring(0, oCondition.indexOf(":")); 
							
					//In case the Act includes a WP that references to a Superclass of TPMs (e. g. Entity for cake, can etc.)
					if(oContentType.equals(oCondition.substring(oCondition.indexOf(":") + 1))){
							oCondition = oContentType; 
					}
							
					if(poState.containsKey(oContentType)){
							poState.get(oContentType).add(oCondition); 
					}
					else {
							poState.put(oContentType, new ArrayList<String>(Arrays.asList(oCondition))); 
					}				
			}
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
		oContent = eActState.PRECONDITION.name() + "||" 
				   + eActState.ACTION.name() + "||" 
				   + eActState.CONSEQUENCE.name() + "|" + moContent; 
	
		return oContent;
	}
	
	private String getPreCondition(String poContent){
		
		String oRetVal = poContent.substring(poContent.indexOf("|", 
						 poContent.indexOf(eActState.PRECONDITION.name())) + 1, 
						 poContent.indexOf("||")); 

		return oRetVal;
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
	private ArrayList<clsAct> extractSearchResult(ArrayList<clsPair<Double, clsDataStructureContainer>> oResult) {
		
		ArrayList<clsAct> oActs = new ArrayList<clsAct>();
		
		for(clsPair<Double, clsDataStructureContainer> oEntry : oResult){
				oActs.add((clsAct)oEntry.b.moDataStructure);
		}
		
		return oActs;
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
	
	private class Node 
	{
	    	public clsAct label;
	       	public boolean visited=false;
	       	public boolean parent = false;
	       	public Node oParentNode = null; 
	    	
	    	public Node(clsAct poAct, Node poNode)
	    	{
	    		this.label=poAct;
	    		this.oParentNode = poNode; 
	    	}
	 }
	
	private class Graph{
		private int[][] adjMatrix;//Edges will be represented as adjacency Matrix
		private Node rootNode;
		private ArrayList<Node> nodes=new ArrayList<Node>();	
		private int size; 
		
		
		public void setRootNode(Node n)
		{
		 		this.rootNode=n;
		}
		  	
		/**
		 * DOCUMENT (zeilinger) - insert description
		 *
		 * @author zeilinger
		 * 01.09.2010, 20:52:13
		 *
		 * @param oNode
		 * @param oActTempList
		 */
		public void addChildren(Node poNode, ArrayList<clsAct> poActTempList) {
			
			for(clsAct oEntry : poActTempList){
				Node oChildNode = new Node(oEntry, poNode); 
				poNode.parent = true; 
				this.addNode(oChildNode); 
				this.connectNode(poNode, oChildNode); 
			}
		}

		/**
		 * DOCUMENT (zeilinger) - insert description
		 *
		 * @author zeilinger
		 * 01.09.2010, 12:24:40
		 *
		 */
		public void setUnvisited() {
			for(Node oNode : nodes){
				oNode.visited = false; 
			}
		}

		/**
		 * DOCUMENT (zeilinger) - insert description
		 *
		 * @author zeilinger
		 * 31.08.2010, 17:10:13
		 *
		 * @return
		 */
		public ArrayList<Node> getChildLessNodes() {
			ArrayList <Node> oRetVal = new ArrayList<E28_KnowledgeBase_StoredScenarios.Node>(); 
			
			for(Node oNode : nodes){
				if(!oNode.parent){oRetVal.add(oNode);}
			}
			
			return oRetVal; 
		}

		public Node getRootNode()
		{
				return this.rootNode;
		}
		
		public void addNode(Node n)
		{
				nodes.add(n);
		}
		
		public Node getUnvisitedChildNode(Node n)
		{
		  		int index=nodes.indexOf(n);
		  		int j=0;
		  		while(j<size)
		  		{
		  			if(adjMatrix[index][j]==1 && ((Node)nodes.get(j)).visited==false)
		  			{
		  				return (Node)nodes.get(j);
		  			}
		  			j++;
		  		}
		  		return null;
		 }
		
		public void connectNode(Node start,Node end)
		{
		  		if(adjMatrix==null)
		   		{
		   			size= mnNodeLimit;
		   			adjMatrix=new int[size][size];
		   		}
		   
		   		int startIndex=nodes.indexOf(start);
		   		int endIndex=nodes.indexOf(end);
		   		adjMatrix[startIndex][endIndex]=1;
		   		adjMatrix[endIndex][startIndex]=1;
		 }
		
		public ArrayList <clsAct> getPath(Node poNode){
			ArrayList<clsAct> oList = new ArrayList<clsAct>(Arrays.asList(poNode.label)); 
			Node oNode = poNode; 
			
			while(oNode.oParentNode != null){
				oList.add(oNode.oParentNode.label);
				oNode = oNode.oParentNode; 
			}
			
			return oList; 
		}
	}

}
