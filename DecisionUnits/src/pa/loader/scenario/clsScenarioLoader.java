/**
 * clsScenarioLoader.java: DecisionUnits - pa.loader.scenario
 * 
 * @author langr
 * 25.10.2009, 12:20:14
 */
package pa.loader.scenario;

import java.util.ArrayList;
import java.util.Vector;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import pa.datatypes.clsAssociation;
import pa.datatypes.clsSecondaryInformation;
import pa.datatypes.clsWordPresentation;
import bfg.tools.xmltools.clsXMLAbstractImageReader;
import bfg.tools.xmltools.clsXMLConfiguration;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 25.10.2009, 12:20:14
 * 
 */
public class clsScenarioLoader {

	private static String moAttributeName = "TemplateScenarios"; //denotes the directory name and the first element name in xml
	private static String moNodeName = "TemplateScenario"; //denotes one single entry for an object
	private static String moStateNodeName = "State";
	private static String moTransitionNodeName = "Transition";

	
	public static ArrayList<clsSecondaryInformation> createTemplateScenarioList(String poAgentId, String poAgentGroup) {

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

		clsScenarioBaseMesh oScenarioMesh = new clsScenarioBaseMesh( new clsWordPresentation() );
		readContent(poScenarioNode, oScenarioMesh);
		createStateList(poScenarioNode, oScenarioMesh);
		
		return oScenarioMesh;
	}
	
	private static void createStateList(Node poScenarioNode,
			clsScenarioBaseMesh poScenarioMesh) {

		Vector<Node> oNodes  = new Vector<Node>();
		clsXMLAbstractImageReader.getSubNodesByName( poScenarioNode, 
				moStateNodeName, oNodes);
	
		for(Node oNode : oNodes)                       
		{
			clsScenarioStateMesh oStateMesh = new clsScenarioStateMesh(new clsWordPresentation());
			readStateContent(oNode, oStateMesh);
			createTransitions(oNode, oStateMesh);
			
			poScenarioMesh.addState(oStateMesh);
		}
	}

	private static void createTransitions(Node poScenarioNode,
			clsScenarioStateMesh poStateMesh) {
		
		Vector<Node> oNodes  = new Vector<Node>();
		clsXMLAbstractImageReader.getSubNodesByName( poScenarioNode, 
				moTransitionNodeName, oNodes);
	
		for(Node oNode : oNodes)                       
		{
			clsScenarioTransition oTransition = new clsScenarioTransition(new clsWordPresentation());
			readTransitionContent(oNode, oTransition);
			
			poStateMesh.moAssociations.add(new clsAssociation<clsSecondaryInformation>(poStateMesh, oTransition));
		}

	}


	private static void readTransitionContent(Node poScenarioNode,
			clsScenarioTransition poTansition) {
		readContent(poScenarioNode, poTansition);
		NamedNodeMap oAttrib = poScenarioNode.getAttributes();
		poTansition.moTargetId = Integer.parseInt(clsXMLAbstractImageReader.getAtributeValue(oAttrib,"targetid"));
	}


	private static void readStateContent(Node poScenarioNode,
			clsScenarioStateMesh poStateMesh) {
		
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
