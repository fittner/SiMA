/**
 * clsDriveLoader.java: DecisionUnits - pa.loader
 * 
 * @author langr
 * 28.09.2009, 10:30:53
 */
package pa.loader;

import java.util.ArrayList;
import java.util.Vector;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import du.enums.eEntityType;
import du.enums.pa.eContext;
import du.enums.pa.eDriveContent;



import pa.bfg.tools.xmltools.clsXMLAbstractImageReader;
import pa.bfg.tools.xmltools.clsXMLConfiguration;
import pa.datatypes.clsDriveObject;
import pa.datatypes.clsDriveContentCategories;
import pa.enums.eDriveType;
import pa.tools.clsPair;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 28.09.2009, 10:30:53
 * 
 */
@Deprecated
public class clsDriveLoader {

	public static String moAttributeName = "TPDrives"; //denotes the directory name and the first element name in xml
	public static String moPairName = "DrivePair"; //denotes one single entry for a drive
	public static String moNodeName = "TPDrive"; //denotes one single entry for a drive
	public static String moDriveContentName = "Drivecontent"; 
	public static String moDriveSourceListName = "DriveSourceList";
	public static String moDriveSourceName = "DriveSource";
	public static String moLifeInstinctListName = "LifeInstinctList";
	public static String moLifeInstinctRatioName = "LifeInstinctRatio";
	public static String moDriveObjectListName = "DriveObjectList";
	public static String moDriveObjectName = "DriveObject";
	public static String moDriveTypeName = "DriveType";

	public static ArrayList<clsPair<clsTemplateDrive, clsTemplateDrive>> createDriveList(String poAgentId, String poAgentGroup) {
		
		ArrayList<clsPair<clsTemplateDrive, clsTemplateDrive>> oRetVal = new ArrayList<clsPair<clsTemplateDrive, clsTemplateDrive>>();

		try {
			
			ArrayList<String> imageFileList = clsXMLConfiguration.getFileList(poAgentId, moAttributeName, poAgentGroup);
			for( int fileCount=0; fileCount<imageFileList.size();++fileCount)
			{
				String xmlFileName = (String)imageFileList.get(fileCount);
		        clsXMLAbstractImageReader oReader = new clsXMLAbstractImageReader(xmlFileName);
	
		        Vector<Node> oNodes  = new Vector<Node>();
				clsXMLAbstractImageReader.getNodeElementByName( (Node)oReader.getDocument().getDocumentElement(), 
						moPairName, 1, oNodes);
			
				for(int i=0;i<oNodes.size();i++)                       
				{
					Node oDriveNode = (Node)oNodes.get(i);
	
					clsPair<clsTemplateDrive, clsTemplateDrive> oPair = createDrivePair( oDriveNode);
			  
					oRetVal.add( oPair );
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
	private static clsPair<clsTemplateDrive, clsTemplateDrive> createDrivePair(Node poDriveNode) {

		clsPair<clsTemplateDrive, clsTemplateDrive> oRetVal = new clsPair<clsTemplateDrive, clsTemplateDrive>(new clsTemplateDrive(), new clsTemplateDrive());
		
		Vector<Node> oNodes  = new Vector<Node>();
		clsXMLAbstractImageReader.getSubNodesByName(poDriveNode, moNodeName, oNodes);

		for(int i=0; i<oNodes.size(); i++) {

			clsTemplateDrive oTempDrive = createDrive( oNodes.get(i) );

			if(oTempDrive.meDriveType == eDriveType.LIFE) {
				oRetVal.a = oTempDrive;
			}
			else {
				oRetVal.b = oTempDrive;
			}
			
			if(i==1) {
				break;
			}
		}
		return oRetVal;
	}

	public static clsTemplateDrive createDrive(Node poDriveNode) {
		
		clsTemplateDrive oRetVal = new clsTemplateDrive();
//		NamedNodeMap oAtrib = poDriveNode.getAttributes();
//		oRetVal.moName =  clsXMLAbstractImageReader.getAtributeValue(oAtrib,"name");
		oRetVal.moName = "Drive";
		
		oRetVal.meDriveContent = eDriveContent.valueOf( clsXMLAbstractImageReader.getTagStringValue(poDriveNode, moDriveContentName ) );
		oRetVal.meDriveType = eDriveType.valueOf( clsXMLAbstractImageReader.getTagStringValue(poDriveNode, moDriveTypeName ) );
		//Node oReaderNode = null;
		//Vector<Node> oNodes = null;
		
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
			clsTemplateDrive poDrive) {
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
			clsTemplateDrive poDrive) {
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
				clsDriveContentCategories oRatio = new clsDriveContentCategories(
						Double.parseDouble( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"oral")),
						Double.parseDouble( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"anal")),
						Double.parseDouble( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"phallic")),
						Double.parseDouble( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"genital"))
						);
				
				eContext eCurrentCont = eContext.valueOf( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"context") );
				poDrive.moDriveContentRatio.put(eCurrentCont, oRatio);
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
	private static void createDriveSources(Node poDriveNode, clsTemplateDrive poDrive) {
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
				clsAffectCandidateDefinition oCandidate = new clsAffectCandidateDefinition();
				oCandidate.moSensorType =  clsXMLAbstractImageReader.getAtributeValue(oAtrib,"SensorType");
				oCandidate.mrRatio =  Double.parseDouble( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"Ratio") );
				oCandidate.mrMaxValue =  Double.parseDouble( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"MaxValue") );
				
				if( oAtrib.getNamedItem("Inverse") != null ) {
					oCandidate.mnInverse =  Boolean.parseBoolean( clsXMLAbstractImageReader.getAtributeValue(oAtrib,"Inverse") );
				}
				
				poDrive.moAffectCandidate.add(oCandidate);
			}
		}
	}
	
}
