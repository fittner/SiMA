/**
 * E28_KnowledgeBase_StoredScenarios.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:56:22
 */
package pa._v19.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import config.clsBWProperties;
import pa._v19.clsInterfaceHandler;
import pa._v19.interfaces.receive.I6_2_receive;
import pa._v19.interfaces.receive.I7_2_receive;
import pa._v19.interfaces.send.I6_2_send;
import pa._v19.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v19.memorymgmt.datatypes.clsAct;
import pa._v19.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v19.memorymgmt.datatypes.clsDataStructurePA;
import pa._v19.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v19.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v19.memorymgmt.datatypes.clsWordPresentation;
import pa._v19.memorymgmt.enums.eActState;
import pa._v19.memorymgmt.enums.eDataType;
import pa._v19.tools.clsPair;
import pa._v19.tools.clsTripple;
import pa._v19.interfaces.knowledgebase.itfKnowledgeBaseAccess;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:56:22
 * 
 */
@Deprecated
public class E28_KnowledgeBase_StoredScenarios extends clsModuleBase implements I7_2_receive, I6_2_send, itfKnowledgeBaseAccess {
	//TODO HZ has to be defined in a config file
	private final Integer mnNodeLimit = 20;
	
	private ArrayList<clsSecondaryDataStructureContainer> moGoal_Input; 
	private ArrayList<ArrayList<clsAct>> moPlan_Output; 
			
	/**
	 * 
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
	
	/**
	 * @author zeilinger
	 * 02.09.2010, 12:32:06
	 * 
	 * @return the moGoal_Input
	 */
	public ArrayList<clsSecondaryDataStructureContainer> getGoal_Input() {
		return moGoal_Input;
	}

	/**
	 * @author zeilinger
	 * 02.09.2010, 12:32:06
	 * 
	 * @return the moPlan_Output
	 */
	public ArrayList<ArrayList<clsAct>> getPlan_Output() {
		return moPlan_Output;
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
	public void receive_I7_2(ArrayList<clsSecondaryDataStructureContainer> poGoal_Input) {
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
		//HZ It has to be discussed if E28 is required! In case it remains,
		//a loop between E27 and E28 is required; otherwise it is a problem to construct "plans"
		//"retrieveActs()" was introduced to retrieve acts from the memory, according to the 
		//actual situation. As long as the question is not solved if the loop is required,
		//E28 is not triggered; In the meantime a memory access is introduced in E27 
		moPlan_Output = retrieveActsForGoals(); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 28.08.2010, 11:23:14
	 * @return 
	 *
	 */
	private ArrayList<ArrayList<clsAct>> retrieveActsForGoals() {
		//HZ retrieving ACTS from the memory strongly depend on the 
		//design of Acts => actually it has to be differenciated between 
		//the preconditions, the action and the consequences; however
		//this may change with further implementations 
		ArrayList<ArrayList<clsAct>> oResult = new ArrayList<ArrayList<clsAct>>(); 

		for(clsSecondaryDataStructureContainer oCon : moGoal_Input){
			String oActualState = ((clsWordPresentation)oCon.getMoDataStructure()).getMoContent(); 
			ArrayList<ArrayList<clsAct>> oActPlans = new ArrayList<ArrayList<clsAct>>(); 
		    //HZ not sure, but maybe a loop between E28 and E27 is required here; 
			// TODO: This is also a bit magic; ACTS are retrieved that include the actual object setup, no
			//	matter if it fits the "precondition" or the "consequence"; normally it is not the 
			//  task of the memory to introduce a filter that makes a difference between both parts; 
			// however it can be thought about alternatives and possibilities to retrieve acts
			// more efficiently. 
			oActPlans = backwardChaining(oActualState); 
			oResult.addAll(oActPlans); 
		}
		
		return oResult; 
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
	private ArrayList<ArrayList<clsAct>> backwardChaining(String poTargetState) {
		
		ArrayList<ArrayList<clsAct>> oRetVal = new ArrayList<ArrayList<clsAct>>(); 
		String oDelimiter = "||";
		boolean oSuccess = false; 
						
		String oPreCondition = poTargetState.substring(0, poTargetState.indexOf(oDelimiter)); 
		ArrayList<clsAct> oActList = getMatchingAct(oPreCondition); 
		
		for(clsAct oAct : oActList){
			ArrayList<clsAct> oSequence; 	
			Graph oGraph = initGraph(oAct); 
			
			// In case the final state of the plan is reached,the actual
			// act will match with the TargetState. This has to be 
			// controlled before the while loop - otherwise the matching plan
			// will not be found. The reason for this is the shifting between 
			// act-precondition and act-consequence when the child nodes are 
			// read
			oSuccess = comparePreConditions(oAct.getMoContent(), poTargetState);
			if(oSuccess){oRetVal.add(new ArrayList<clsAct>(Arrays.asList(oAct)));}
			
			//If the current act does not match with the targetState => 
			//search for the child nodes until oSuccess == true or
			//the graph succeeds the node limit. 
			while(!oSuccess){
				
				ArrayList<Node> oChildLessNode = oGraph.getChildLessNodes(); 
				
				for(Node oNode : oChildLessNode){
						ArrayList<clsAct> oActTempList = new ArrayList<clsAct>();
						oPreCondition = getPreCondition(oNode.label.getMoContent());  
					    oActTempList = getMatchingAct(oPreCondition); 
						
					    oGraph.addChildren(oNode, oActTempList); 
					    oSequence = bf_Search(poTargetState, oGraph); 
						
						if(oSequence.size() > 0){
							oSuccess = true;
							oRetVal.add(oSequence); 
						}
						if(oActTempList.size() == 0){
							oSuccess = true;
						}
				}
			 }
		}
		
		return oRetVal; 
	}
		
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 03.09.2010, 19:26:20
	 *
	 * @param oAct
	 * @return
	 */
	private Graph initGraph(clsAct poAct) {
		Graph oGraph = new Graph();
		Node parentNode = new Node(poAct, null); 
		oGraph.setRootNode(parentNode);
		oGraph.addNode(parentNode);
		
		return oGraph;
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
	private ArrayList<clsAct> getMatchingAct(String poPreCondition) {
		String oActualGoal = getContent(poPreCondition);
		clsAct oAct = generateAct(oActualGoal); 
		ArrayList<clsAct> oList = retrieveMatch(oAct); 
		
		return fullMatch(oList, oAct); 
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
			String [] oConsequenceUnknown = poAct.getMoContent().substring(poAct.getMoContent().indexOf("|", poAct.getMoContent().indexOf("CONSEQUENCE")) + 1).split("[|]");
			
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
		
		label: while(!oQueue.isEmpty()){
				Node oNode = (Node) oQueue.remove();
				Node oChild = null; 
			
				while((oChild = poGraph.getUnvisitedChildNode(oNode)) != null){
						oChild.visited = true; 
						oQueue.add(oChild); 
						oSuccess = comparePreConditions(oChild.label.getMoContent(), poTargetState);
					   
						if(oSuccess) {
								oQueue.clear(); 
								oPath = poGraph.getPath(oChild); 
								break;
						}
						
						if(poGraph.nodes.size() == mnNodeLimit){break label;}
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
	
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = 
				new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
		
		search(eDataType.UNDEFINED, new ArrayList<clsAct>(Arrays.asList(poDummy)), oSearchResult); 
		//The Double value in clsPair defines the matching score of the retrieved Act and the 
		//search pattern. This was ok for the primary data structures as there have not been 
		//any logic relations within the data structures. Now there exist acts with "or" and "and"
		//relations that may include many possibilities (see the acts for changing locations)
		//=> actually a lot of acts are retrieved from the memory; E27 will have to sort them out.
		//It has to be verified if this is a good solution, or if the comparison mode for the clsAct 
		//datatype has to be changed
		return extractSearchResult(oSearchResult);
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
				oActs.add((clsAct)oPair.b.getMoDataStructure());
			}
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
		send_I6_2(moPlan_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:56:52
	 * 
	 * @see pa.interfaces.send.I6_2_send#send_I6_2(int)
	 */
	@Override
	public void send_I6_2(ArrayList<ArrayList<clsAct>> poPlanOutput) {
		((I6_2_receive)moEnclosingContainer).receive_I6_2(moPlan_Output);
		
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
