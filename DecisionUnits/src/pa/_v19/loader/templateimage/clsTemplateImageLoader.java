/**
 * clsTemplateImageLoader.java: DecisionUnits - pa.loader
 * 
 * @author langr
 * 23.10.2009, 12:11:14
 */
package pa._v19.loader.templateimage;

import java.util.ArrayList;
import java.util.Vector;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import du.enums.eTriState;


import pa._v19.bfg.tools.xmltools.clsXMLAbstractImageReader;
import pa._v19.bfg.tools.xmltools.clsXMLConfiguration;
import pa._v19.datatypes.clsAssociation;
import pa._v19.datatypes.clsSecondaryInformation;
import pa._v19.datatypes.clsWordPresentation;

import bfg.utils.enums.eOptional;

/**
 * 
 * @author langr
 * 23.10.2009, 12:11:14
 * 
 */
@Deprecated
public class clsTemplateImageLoader {

	private static String moAttributeName = "TemplateImages"; //denotes the directory name and the first element name in xml
	public static String moNodeName = "TemplateImage"; //denotes one single entry for an object
	private static String moPrimMeshName = "PrimMesh";
	private static String moPrimInfoName = "PrimInfo";
	private static String moAffectName = "Affect";
	private static String moDriveMeshName = "DriveMesh";
	
	public static ArrayList<clsSecondaryInformation> createTemplateImageList(String poAgentId, String poAgentGroup) {

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
					Node oObjTemImage = (Node)oNodes.get(i);
					oRetVal.add(createTemplateImage(oObjTemImage));
			    }
			}
		} catch(Exception e) {
			System.out.println("Error reading Template Images: "+e.getMessage());
		}
		
		return oRetVal;
	}

	/**
	 *
	 * @author langr
	 * 23.10.2009, 14:39:17
	 *
	 * @param objTemImage
	 */
	private static clsSecondaryInformation createTemplateImage(Node oNode) {

		clsTemplateSecondaryMesh oTempMesh = new clsTemplateSecondaryMesh( new clsWordPresentation() );
		readContent(oNode, oTempMesh);
		readNodeLogic(oNode, oTempMesh);

		createSubMeshes(oNode, oTempMesh);
		createSubInfos(oNode, oTempMesh);
		createSubDrives(oNode, oTempMesh);

		return oTempMesh;
	}

	private static void createSubMeshes(Node poTempImageNode,
			clsTemplateSecondaryMesh poSecMesh) {
        
		Vector<Node> oNodes  = new Vector<Node>();
		clsXMLAbstractImageReader.getSubNodesByName( poTempImageNode, 
				moPrimMeshName, oNodes);
	
		for(Node oNode : oNodes)                       
		{
			clsTemplateSecondaryMesh oTempMesh = new clsTemplateSecondaryMesh( new clsWordPresentation() );
			readContent(oNode, oTempMesh);
			readNodeLogic(oNode, oTempMesh);
			readRelationalOperator(oNode, oTempMesh);
			
			createAffect(oNode, oTempMesh);
			
			createSubMeshes(oNode, oTempMesh);
			createSubInfos(oNode, oTempMesh);
			//createSubDrives(oNode, oTempMesh); --> driveMeshes are supposed to be on top level!
			
			poSecMesh.moAssociations.add(new clsAssociation<clsSecondaryInformation>(poSecMesh, oTempMesh) );
		}
	}

	private static void createSubInfos(Node poTempImageNode,
			clsTemplateSecondaryMesh poSecMesh) {
		Vector<Node> oNodes  = new Vector<Node>();
		clsXMLAbstractImageReader.getSubNodesByName( poTempImageNode, 
				moPrimInfoName, oNodes);
	
		for(Node oNode : oNodes)                       
		{
			clsTemplateSecondaryInfo oTempPrimInfo = new clsTemplateSecondaryInfo( new clsWordPresentation() );
			readSecInfo(oNode, oTempPrimInfo);
			
			poSecMesh.moAssociations.add(new clsAssociation<clsSecondaryInformation>(poSecMesh, oTempPrimInfo) );
		}
	}

	private static void createSubDrives(Node poTempImageNode,
			clsTemplateSecondaryMesh poSecMesh) {
		Vector<Node> oNodes  = new Vector<Node>();
		clsXMLAbstractImageReader.getSubNodesByName( poTempImageNode, 
				moDriveMeshName, oNodes);
	
		for(Node oNode : oNodes)                       
		{
			clsTemplateSecondaryMesh oTempMesh = new clsTemplateSecondaryMesh( new clsWordPresentation() );
			readContent(oNode, oTempMesh);
			readNodeLogic(oNode, oTempMesh);
			readRelationalOperator(oNode, oTempMesh);
			
			createAffect(oNode, oTempMesh);
		
			poSecMesh.moAssociations.add(new clsAssociation<clsSecondaryInformation>(poSecMesh, oTempMesh) );
		}
	}

	private static void createAffect(Node node, clsTemplateSecondaryMesh tempMesh) {
		Node oNode = clsXMLAbstractImageReader.getNextNodeElementByName(node, moAffectName);
		if(oNode != null) {
			clsTemplateAffect oAffect = new clsTemplateAffect();
			NamedNodeMap oAttrib = oNode.getAttributes();
			oAffect.moValue = Double.parseDouble(clsXMLAbstractImageReader.getAtributeValue(oAttrib,"value"));
			oAffect.moCompareOperator = new clsCompareOperator( clsXMLAbstractImageReader.getAtributeValue(oAttrib,"compare") );
			tempMesh.moAffect = oAffect;
		}
	}
	

	private static void readSecInfo(Node poNode,
			clsTemplateSecondaryInfo poSecInfo) {

		NamedNodeMap oAttrib = poNode.getAttributes();
		poSecInfo.moWP.moContentName = clsXMLAbstractImageReader.getAtributeValue(oAttrib,"name");
		poSecInfo.moWP.moContentType = clsXMLAbstractImageReader.getAtributeValue(oAttrib,"type");
		poSecInfo.moWP.moContent = clsXMLAbstractImageReader.getAtributeValue(oAttrib,"value");
		poSecInfo.moCompareOperator = new clsCompareOperator( clsXMLAbstractImageReader.getAtributeValue(oAttrib,"compare") );
	}
	
	private static void readContent(Node poTempImageNode,
			clsTemplateSecondaryMesh poSecMesh) {

		NamedNodeMap oAttrib = poTempImageNode.getAttributes();
		poSecMesh.moWP.moContentName = clsXMLAbstractImageReader.getAtributeValue(oAttrib,"name");
		poSecMesh.moWP.moContentType = clsXMLAbstractImageReader.getAtributeValue(oAttrib,"type");
		poSecMesh.moWP.moContent = clsXMLAbstractImageReader.getAtributeValue(oAttrib,"value");
	}
	
	private static void readNodeLogic(Node poTempImageNode,
			clsTemplateSecondaryMesh poSecMesh) {
		NamedNodeMap oAttrib = poTempImageNode.getAttributes();
		poSecMesh.meOptional = eOptional.valueOf( clsXMLAbstractImageReader.getAtributeValue(oAttrib,"optional") );
		poSecMesh.meOperator = eBooleanOperator.valueOf( clsXMLAbstractImageReader.getAtributeValue(oAttrib,"booleanOperator"));
		poSecMesh.meNegated = eTriState.valueOf( clsXMLAbstractImageReader.getAtributeValue(oAttrib,"negated"));
	}
	
	private static void readRelationalOperator(Node poTempImageNode,
			clsTemplateSecondaryMesh poPrimMesh) {
		NamedNodeMap oAttrib = poTempImageNode.getAttributes();
		poPrimMesh.moCompareOperator = new clsCompareOperator( clsXMLAbstractImageReader.getAtributeValue(oAttrib,"compare") );		
	}


}
