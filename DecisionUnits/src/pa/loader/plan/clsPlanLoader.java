/**
 * clsScenarioLoader.java: DecisionUnits - pa.loader.scenario
 * 
 * @author langr
 * 25.10.2009, 12:20:14
 */
package pa.loader.plan;

import java.util.ArrayList;
import java.util.Vector;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import pa.bfg.tools.xmltools.clsXMLAbstractImageReader;
import pa.bfg.tools.xmltools.clsXMLConfiguration;
import pa.datatypes.clsAssociation;
import pa.datatypes.clsSecondaryInformation;
import pa.datatypes.clsWordPresentation;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 25.10.2009, 12:20:14
 * 
 */
public class clsPlanLoader {

	private static String moAttributeName = "TemplatePlans"; //denotes the directory name and the first element name in xml
	private static String moNodeName = "TemplatePlan"; //denotes one single entry for an object
	private static String moStateNodeName = "State";
	private static String moTransitionNodeName = "Transition";
	private static String moActionNodeName = "Action";
	
	public static ArrayList<clsSecondaryInformation> createTemplatePlanList(String poAgentId, String poAgentGroup) {

		ArrayList<clsSecondaryInformation> oRetVal = new ArrayList<clsSecondaryInformation>();
		
		try {
			
			ArrayList<String> imageFileList = clsXMLConfiguration.getFileList(poAgentId, moAttributeName, poAgentGroup);
			for( int fileCount=0; fileCount<imageFileList.size();++fileCount)
			{
				String xmlFileName = (String)imageFileList.get(fileCount);
		        clsXMLAbstractImageReader oReader = new clsXMLAbstractImageReader(xmlFileName);
	
		        Vector<Node> oNodes  = new Vector<Node>();
				clsXMLAbstractImageReader.getNodeElementByName( (Node)oReader.getDocument().getDocumentElement(), 
						moNodeName, 1, oNodes);
			
				for(int i=0;i<oNodes.size();i++)                       
				{
					Node oScenarioNode = (Node)oNodes.get(i);
					oRetVal.add(createTemplateScenario(oScenarioNode));
			    }
			}
		} catch(Exception e) {
			System.out.println("Error reading Template Images: "+e.getMessage());
		}
		
		return oRetVal;
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 25.10.2009, 12:45:27
	 *
	 * @param scenarioNode
	 * @return
	 */
	private static clsSecondaryInformation createTemplateScenario(
			Node poScenarioNode) {

		clsPlanBaseMesh oScenarioMesh = new clsPlanBaseMesh( new clsWordPresentation() );
		readContent(poScenarioNode, oScenarioMesh);
		createStateList(poScenarioNode, oScenarioMesh);
		
		return oScenarioMesh;
	}
	
	private static void createStateList(Node poScenarioNode,
			clsPlanBaseMesh poScenarioMesh) {

		Vector<Node> oNodes  = new Vector<Node>();
		clsXMLAbstractImageReader.getSubNodesByName( poScenarioNode, 
				moStateNodeName, oNodes);
	
		for(Node oNode : oNodes)                       
		{
			clsPlanStateMesh oStateMesh = new clsPlanStateMesh(new clsWordPresentation());
			readStateContent(oNode, oStateMesh);
			createTransitions(oNode, oStateMesh);
			createActions(oNode, oStateMesh);
			
			poScenarioMesh.addState(oStateMesh);
		}
	}

	private static void createActions(Node poStateNode, clsPlanStateMesh poStateMesh) {
		Vector<Node> oNodes  = new Vector<Node>();
		clsXMLAbstractImageReader.getSubNodesByName( poStateNode, 
				moActionNodeName, oNodes);
	
		for(Node oNode : oNodes)                       
		{
			clsPlanAction oAction = new clsPlanAction(new clsWordPresentation());
			readActionContent(oNode, oAction);
			
			poStateMesh.moAssociations.add(new clsAssociation<clsSecondaryInformation>(poStateMesh, oAction));
		}
	}

	private static void createTransitions(Node poScenarioNode,
			clsPlanStateMesh poStateMesh) {
		
		Vector<Node> oNodes  = new Vector<Node>();
		clsXMLAbstractImageReader.getSubNodesByName( poScenarioNode, 
				moTransitionNodeName, oNodes);
	
		for(Node oNode : oNodes)                       
		{
			clsPlanTransition oTransition = new clsPlanTransition(new clsWordPresentation());
			readTransitionContent(oNode, oTransition);
			
			poStateMesh.moAssociations.add(new clsAssociation<clsSecondaryInformation>(poStateMesh, oTransition));
		}

	}

	private static void readTransitionContent(Node poScenarioNode,
			clsPlanTransition poTansition) {
		readContent(poScenarioNode, poTansition);
		NamedNodeMap oAttrib = poScenarioNode.getAttributes();
		poTansition.mnTargetId = Integer.parseInt(clsXMLAbstractImageReader.getAtributeValue(oAttrib,"targetid"));
		poTansition.mrMatch = Double.parseDouble(clsXMLAbstractImageReader.getAtributeValue(oAttrib,"match"));
	}


	private static void readActionContent(Node poActionNode, clsPlanAction poAction) {
		readContent(poActionNode, poAction);
		NamedNodeMap oAttrib = poActionNode.getAttributes();
		poAction.mnOnce = Boolean.parseBoolean(clsXMLAbstractImageReader.getAtributeValue(oAttrib,"once"));
		poAction.mnFinish = Boolean.parseBoolean(clsXMLAbstractImageReader.getAtributeValue(oAttrib,"finish"));
	}

	private static void readStateContent(Node poScenarioNode,
			clsPlanStateMesh poStateMesh) {
		
		readContent(poScenarioNode, poStateMesh);
		NamedNodeMap oAttrib = poScenarioNode.getAttributes();
		poStateMesh.mnId = Integer.parseInt(clsXMLAbstractImageReader.getAtributeValue(oAttrib,"id"));
		
	}

	private static void readContent(Node poTempImageNode,
			clsSecondaryInformation poSecMesh) {

		NamedNodeMap oAttrib = poTempImageNode.getAttributes();
		poSecMesh.moWP.moContentName = clsXMLAbstractImageReader.getAtributeValue(oAttrib,"name");
		poSecMesh.moWP.moContentType = clsXMLAbstractImageReader.getAtributeValue(oAttrib,"type");
		poSecMesh.moWP.moContent = clsXMLAbstractImageReader.getAtributeValue(oAttrib,"value");
	}
}
