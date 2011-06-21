/**
 * clsTemplateImagePrimaryStorage.java: DecisionUnits - pa.memory
 * 
 * @author langr
 * 24.10.2009, 23:54:45
 */
package pa._v19.memory;

import java.util.ArrayList;
import java.util.Vector;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import du.enums.eTriState;


import pa._v19.bfg.tools.xmltools.clsXMLAbstractImageReader;
import pa._v19.bfg.tools.xmltools.clsXMLConfiguration;
import pa._v19.datatypes.clsAssociation;
import pa._v19.datatypes.clsPrimaryInformation;
import pa._v19.datatypes.clsThingPresentationSingle;
import pa._v19.loader.templateimage.clsCompareOperator;
import pa._v19.loader.templateimage.clsTemplateAffect;
import pa._v19.loader.templateimage.clsTemplatePrimaryInfo;
import pa._v19.loader.templateimage.clsTemplatePrimaryMesh;
import pa._v19.loader.templateimage.eBooleanOperator;

import bfg.utils.enums.eOptional;

/**
 * 
 * @author langr
 * 24.10.2009, 23:54:45
 * 
 */
@Deprecated
public class clsTemplateImagePrimaryStorage  {

	private static String moAttributeName = "TemplateImages"; //denotes the directory name and the first element name in xml
	public static String moNodeName = "TemplateImage"; //denotes one single entry for an object
	private static String moPrimMeshName = "PrimMesh";
	private static String moPrimInfoName = "PrimInfo";
	private static String moAffectName = "Affect";
	private static String moDriveMeshName = "DriveMesh";
	
	public static ArrayList<clsPrimaryInformation> createTemplateImageList(String poAgentId, String poAgentGroup) {

		ArrayList<clsPrimaryInformation> oRetVal = new ArrayList<clsPrimaryInformation>();
		
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
	private static clsPrimaryInformation createTemplateImage(Node oNode) {

		clsTemplatePrimaryMesh oTempMesh = new clsTemplatePrimaryMesh( new clsThingPresentationSingle() );
		readContent(oNode, oTempMesh);
		readNodeLogic(oNode, oTempMesh);

		createSubMeshes(oNode, oTempMesh);
		createSubInfos(oNode, oTempMesh);
		createSubDrives(oNode, oTempMesh);

		return oTempMesh;
	}

	private static void createSubMeshes(Node poTempImageNode,
			clsTemplatePrimaryMesh poPrimMesh) {
        
		Vector<Node> oNodes  = new Vector<Node>();
		clsXMLAbstractImageReader.getSubNodesByName( poTempImageNode, 
				moPrimMeshName, oNodes);
	
		for(Node oNode : oNodes)                       
		{
			clsTemplatePrimaryMesh oTempMesh = new clsTemplatePrimaryMesh( new clsThingPresentationSingle() );
			readContent(oNode, oTempMesh);
			readNodeLogic(oNode, oTempMesh);
			readRelationalOperator(oNode, oTempMesh);
			
			createAffect(oNode, oTempMesh);
			
			createSubMeshes(oNode, oTempMesh);
			createSubInfos(oNode, oTempMesh);
			//createSubDrives(oNode, oTempMesh); --> driveMeshes are supposed to be on top level!
			
			poPrimMesh.moAssociations.add(new clsAssociation<clsPrimaryInformation>(poPrimMesh, oTempMesh) );
		}
	}

	private static void createSubInfos(Node poTempImageNode,
			clsTemplatePrimaryMesh poPrimMesh) {
		Vector<Node> oNodes  = new Vector<Node>();
		clsXMLAbstractImageReader.getSubNodesByName( poTempImageNode, 
				moPrimInfoName, oNodes);
	
		for(Node oNode : oNodes)                       
		{
			clsTemplatePrimaryInfo oTempPrimInfo = new clsTemplatePrimaryInfo( new clsThingPresentationSingle() );
			readPrimInfo(oNode, oTempPrimInfo);
			
			poPrimMesh.moAssociations.add(new clsAssociation<clsPrimaryInformation>(poPrimMesh, oTempPrimInfo) );
		}
	}

	private static void createSubDrives(Node poTempImageNode,
			clsTemplatePrimaryMesh poPrimMesh) {
		Vector<Node> oNodes  = new Vector<Node>();
		clsXMLAbstractImageReader.getSubNodesByName( poTempImageNode, 
				moDriveMeshName, oNodes);
	
		for(Node oNode : oNodes)                       
		{
			clsTemplatePrimaryMesh oTempMesh = new clsTemplatePrimaryMesh( new clsThingPresentationSingle() );
			readContent(oNode, oTempMesh);
			readNodeLogic(oNode, oTempMesh);
			readRelationalOperator(oNode, oTempMesh);
			
			createAffect(oNode, oTempMesh);
		
			poPrimMesh.moAssociations.add(new clsAssociation<clsPrimaryInformation>(poPrimMesh, oTempMesh) );
		}
	}

	private static void createAffect(Node node, clsTemplatePrimaryMesh tempMesh) {
		Node oNode = clsXMLAbstractImageReader.getNextNodeElementByName(node, moAffectName);
		if(oNode != null) {
			clsTemplateAffect oAffect = new clsTemplateAffect();
			NamedNodeMap oAttrib = oNode.getAttributes();
			oAffect.moValue = Double.parseDouble(clsXMLAbstractImageReader.getAtributeValue(oAttrib,"value"));
			oAffect.moCompareOperator = new clsCompareOperator( clsXMLAbstractImageReader.getAtributeValue(oAttrib,"compare") );
			tempMesh.moAffect = oAffect;
		}
	}
	

	private static void readPrimInfo(Node poNode,
			clsTemplatePrimaryInfo poPrimInfo) {

		NamedNodeMap oAttrib = poNode.getAttributes();
		poPrimInfo.moTP.meContentName = clsXMLAbstractImageReader.getAtributeValue(oAttrib,"name");
		poPrimInfo.moTP.meContentType = clsXMLAbstractImageReader.getAtributeValue(oAttrib,"type");
		poPrimInfo.moTP.moContent = clsXMLAbstractImageReader.getAtributeValue(oAttrib,"value");
		poPrimInfo.moCompareOperator = new clsCompareOperator( clsXMLAbstractImageReader.getAtributeValue(oAttrib,"compare") );
	}
	
	private static void readContent(Node poTempImageNode,
			clsTemplatePrimaryMesh poPrimMesh) {

		NamedNodeMap oAttrib = poTempImageNode.getAttributes();
		poPrimMesh.moTP.meContentName = clsXMLAbstractImageReader.getAtributeValue(oAttrib,"name");
		poPrimMesh.moTP.meContentType = clsXMLAbstractImageReader.getAtributeValue(oAttrib,"type");
		poPrimMesh.moTP.moContent = clsXMLAbstractImageReader.getAtributeValue(oAttrib,"value");
	}
	
	private static void readNodeLogic(Node poTempImageNode,
			clsTemplatePrimaryMesh poPrimMesh) {
		NamedNodeMap oAttrib = poTempImageNode.getAttributes();
		poPrimMesh.meOptional = eOptional.valueOf( clsXMLAbstractImageReader.getAtributeValue(oAttrib,"optional") );
		poPrimMesh.meOperator = eBooleanOperator.valueOf( clsXMLAbstractImageReader.getAtributeValue(oAttrib,"booleanOperator"));
		poPrimMesh.meNegated = eTriState.valueOf( clsXMLAbstractImageReader.getAtributeValue(oAttrib,"negated"));
	}
	
	private static void readRelationalOperator(Node poTempImageNode,
			clsTemplatePrimaryMesh poPrimMesh) {
		NamedNodeMap oAttrib = poTempImageNode.getAttributes();
		poPrimMesh.moCompareOperator = new clsCompareOperator( clsXMLAbstractImageReader.getAtributeValue(oAttrib,"compare") );		
	}


}