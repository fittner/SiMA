/**
 * clsDriveLoader.java: DecisionUnits - pa.loader
 * 
 * @author langr
 * 28.09.2009, 10:30:53
 */
package pa.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import enums.eEntityType;
import enums.eSensorIntType;
import enums.pa.eContext;
import enums.pa.eDriveContent;

import bfg.tools.xmltools.clsXMLAbstractImageReader;
import bfg.tools.xmltools.clsXMLConfiguration;

import pa.datatypes.clsAffectCandidatePart;
import pa.datatypes.clsDriveObject;
import pa.datatypes.clsLifeInstinctRatio;
import pa.datatypes.clsTPDrive;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 28.09.2009, 10:30:53
 * 
 */
public class clsDriveLoader {

	public static String moAttributeName = "TPDrives"; //denotes the directory name and the first element name in xml
	public static String moNodeName = "TPDrive"; //denotes one single entry for a drive
	public static String moDriveContentName = "Drivecontent"; 
	public static String moDriveSourceListName = "DriveSourceList";
	public static String moDriveSourceName = "DriveSource";
	public static String moLifeInstinctListName = "LifeInstinctList";
	public static String moLifeInstinctRatioName = "LifeInstinctRatio";
	public static String moDriveObjectListName = "DriveObjectList";
	public static String moDriveObjectName = "DriveObject";

	
	
	public static HashMap<eDriveContent, clsTPDrive> createDriveList(String poAgentId, String poAgentGroup) {
		
		HashMap<eDriveContent, clsTPDrive> oRetVal = new HashMap<eDriveContent, clsTPDrive>();

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
					Node oDriveNode = (Node)oNodes.get(i);
	
					clsTPDrive oDrive = createDrive( oDriveNode );
			  
					oRetVal.put( oDrive.meDriveContent, oDrive );
			    }
			}
		} catch(Exception e) {
			System.out.println("Error reading Drives: "+e.getMessage());
		}
		return oRetVal;
	}

	/**
	 * creates the single thing presentation of a drive
	 *
	 * @author langr
	 * 28.09.2009, 17:11:12
	 *
	 * @param driveNode
	 * @return
	 */
	private static clsTPDrive createDrive(Node poDriveNode) {

		clsTPDrive oRetVal = new clsTPDrive();

		oRetVal.meDriveContent = eDriveContent.valueOf( clsXMLAbstractImageReader.getTagStringValue(poDriveNode, moDriveContentName ) );
		Node oReaderNode = null;
		Vector<Node> oNodes = null;
		
		//read drive sources		
		createDriveSources(poDriveNode, oRetVal);
		
		//read life instinct list		
		createLifeInstinctList(poDriveNode, oRetVal);
		
		//read drive object list		
		createDriveObjectList(poDriveNode, oRetVal);
		
		return oRetVal;
	}

	/**
	 * read drive sources
	 *
	 * @author langr
	 * 28.09.2009, 17:51:46
	 *
	 * @param poDriveNode
	 * @param poDrive
	 */
	private static void createDriveObjectList(Node poDriveNode,
			clsTPDrive poDrive) {
		Node oReaderNode;
		Vector<Node> oNodes;
		oReaderNode = clsXMLAbstractImageReader.getNextNodeElementByName(poDriveNode, moDriveObjectListName);
		if(oReaderNode != null) {		
	        oNodes  = new Vector<Node>();
			clsXMLAbstractImageReader.getNodeElementByName( oReaderNode, 
					moDriveObjectName, 1, oNodes);
			for(Node oNode : oNodes)                       
			{
				NamedNodeMap oAtrib = oNode.getAttributes();
				clsDriveObject oDrvObj = new clsDriveObject();
				oDrvObj.meType =  eEntityType.valueOf( clsXMLAbstractImageReader.getAtributeValue(oAtrib, "type") );
				oDrvObj.meContext =  eContext.valueOf( clsXMLAbstractImageReader.getAtributeValue(oAtrib, "context") );
				
				poDrive.moDriveObjects.add(oDrvObj);
			}
		}
	}

	/**
	 * read life instinct list
	 *
	 * @author langr
	 * 28.09.2009, 17:51:22
	 *
	 * @param poDriveNode
	 * @param poDrive
	 */
	private static void createLifeInstinctList(Node poDriveNode,
			clsTPDrive poDrive) {
		Node oReaderNode;
		Vector<Node> oNodes;
		oReaderNode = clsXMLAbstractImageReader.getNextNodeElementByName(poDriveNode, moLifeInstinctListName);
		if(oReaderNode != null) {
	        oNodes  = new Vector<Node>();
			clsXMLAbstractImageReader.getNodeElementByName( oReaderNode, 
					moLifeInstinctRatioName, 1, oNodes);
			for(Node oNode : oNodes)                       
			{
				NamedNodeMap oAtrib = oNode.getAttributes();
				clsLifeInstinctRatio oRatio = new clsLifeInstinctRatio(
						eContext.valueOf( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"context") ),
						Double.parseDouble( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"oral")),
						Double.parseDouble( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"anal")),
						Double.parseDouble( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"phallic")),
						Double.parseDouble( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"genital"))
						);
				
				poDrive.moLifeInstinctRatio.add(oRatio);
			}
		}
	}

	/**
	 * read drive object list
	 *
	 * @author langr
	 * 28.09.2009, 17:50:55
	 *
	 * @param poDriveNode
	 * @param poDrive
	 */
	private static void createDriveSources(Node poDriveNode, clsTPDrive poDrive) {
		Node oReaderNode;
		Vector<Node> oNodes;
		oReaderNode = clsXMLAbstractImageReader.getNextNodeElementByName(poDriveNode, moDriveSourceListName);
		if(oReaderNode != null) {
	        oNodes  = new Vector<Node>();
			clsXMLAbstractImageReader.getNodeElementByName( oReaderNode, 
					moDriveSourceName, 1, oNodes);
			for(Node oNode : oNodes)                       
			{
				NamedNodeMap oAtrib = oNode.getAttributes();
				clsAffectCandidatePart oCandidate = new clsAffectCandidatePart();
				oCandidate.meSensorType =  eSensorIntType.valueOf( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"SensorType") );
				oCandidate.moValueType =  clsXMLAbstractImageReader.getAtributeValue(oAtrib,"ValueType");
				oCandidate.moRatio =  Double.parseDouble( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"Ratio") );
				poDrive.moAffectCandidate.add(oCandidate);
			}
		}
	}
	
}
