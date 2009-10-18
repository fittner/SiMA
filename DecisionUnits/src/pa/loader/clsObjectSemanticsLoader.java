/**
 * clsObjectSemanticsLoader.java: DecisionUnits - pa.loader
 * 
 * @author langr
 * 17.10.2009, 20:31:27
 */
package pa.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.w3c.dom.Node;

import pa.datatypes.clsPrimaryInformation;
import bfg.tools.xmltools.clsXMLAbstractImageReader;
import bfg.tools.xmltools.clsXMLConfiguration;
import enums.eEntityType;


/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 17.10.2009, 20:31:27
 * 
 */
public class clsObjectSemanticsLoader {

	public static String moAttributeName = "ObjectSemanticsInit"; //denotes the directory name and the first element name in xml
	public static String moNodeName = "ObjectSemantics"; //denotes one single entry for a drive
	public static String moEntryType = "PrimaryInfoObject";
	public static String moDriveContentCathegoryList = "DriveContentCathegoryList";
	public static String moDriveContentCathegory = "DriveContentCathegory";
	
	public static HashMap<eEntityType, clsPrimaryInformation> createSemanticsList(String poAgentId, String poAgentGroup) {
		
		HashMap<eEntityType, clsPrimaryInformation> oRetVal = new HashMap<eEntityType, clsPrimaryInformation>();
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
					Node oObjSemNode = (Node)oNodes.get(i);
					
					Node oPrimInfoNode = clsXMLAbstractImageReader.getNextNodeElementByName(oObjSemNode, moEntryType);
	
					clsPrimaryInformation oSem = createSemanticsList( oPrimInfoNode );
					if(oRetVal.containsKey(oSem.moTP.moContent)) { //if already exist --> add the driveContentCathegories
						oRetVal.get(oSem.moTP.moContent).moTP.moDriveContentCategory.putAll(oSem.moTP.moDriveContentCategory);
					}
					else {	//add a new primary information
						oRetVal.put((eEntityType)oSem.moTP.moContent, oSem);
					}
			    }
			}
		} catch(Exception e) {
			System.out.println("Error reading Drives: "+e.getMessage());
		}
		return oRetVal;		
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 17.10.2009, 17:45:04
	 *
	 * @param driveNode
	 * @return
	 */
	private static clsPrimaryInformation createSemanticsList(Node primaryNode) {

		clsPrimaryInformation oRetVal = clsStaticLoaderHelper.loadPrimaryInformationObject(primaryNode, moDriveContentCathegoryList, moDriveContentCathegory);
		
		oRetVal.moTP.meContentName = "Entity";
		oRetVal.moTP.meContentType = eEntityType.class.getName();
		oRetVal.moTP.moContent = eEntityType.valueOf( clsXMLAbstractImageReader.getAtributeValue(primaryNode.getAttributes(), "content") );
		
		return oRetVal;
		
	}
}