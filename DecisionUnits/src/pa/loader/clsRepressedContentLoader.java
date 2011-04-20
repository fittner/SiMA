/**
 * clsRepressedContentLoader.java: DecisionUnits - pa.loader
 * 
 * @author langr
 * 17.10.2009, 17:05:17
 */
package pa.loader;

import java.util.ArrayList;
import java.util.Vector;

import org.w3c.dom.Node;

import du.enums.pa.eRepressedContentType;

import pa.bfg.tools.xmltools.clsXMLAbstractImageReader;
import pa.bfg.tools.xmltools.clsXMLConfiguration;
import pa.datatypes.clsPrimaryInformation;



/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 17.10.2009, 17:05:17
 * 
 */
@Deprecated
public class clsRepressedContentLoader {

	public static String moAttributeName = "RepressedContentInit"; //denotes the directory name and the first element name in xml
	public static String moNodeName = "RepressedContent"; //denotes one single entry for a drive
	public static String moEntryType = "PrimaryInfo";
	
	public static ArrayList<clsPrimaryInformation> createRepressedList(String poAgentId, String poAgentGroup) {
		
		ArrayList<clsPrimaryInformation> oRetVal = new ArrayList<clsPrimaryInformation>();
		try {
			
			ArrayList<String> imageFileList = clsXMLConfiguration.getFileList(poAgentId, moAttributeName, poAgentGroup);
			for( int fileCount=0; fileCount<imageFileList.size();++fileCount)
			{
				String xmlFileName = (String)imageFileList.get(fileCount);
		        clsXMLAbstractImageReader oReader = new clsXMLAbstractImageReader(xmlFileName);
	
		        Node oRepNode = clsXMLAbstractImageReader.getNextNodeElementByName((Node)oReader.getDocument().getDocumentElement(), moNodeName);
		        
		        Vector<Node> oNodes  = new Vector<Node>();
				clsXMLAbstractImageReader.getNodeElementByName( oRepNode, moEntryType, 1, oNodes);
			
				for(int i=0;i<oNodes.size();i++)                       
				{
					Node oDriveNode = (Node)oNodes.get(i);
	
					clsPrimaryInformation oRep = createRepressedContentInit( oDriveNode );
			  
					oRetVal.add( oRep );
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
	private static clsPrimaryInformation createRepressedContentInit(Node primaryNode) {

		clsPrimaryInformation oRetVal = clsStaticLoaderHelper.loadPrimaryInformation(primaryNode);
		
		oRetVal.moTP.meContentName = "Repressed Content";
		oRetVal.moTP.meContentType = eRepressedContentType.class.getName();
		oRetVal.moTP.moContent = eRepressedContentType.valueOf( clsXMLAbstractImageReader.getAtributeValue(primaryNode.getAttributes(), "content") );
		
		return oRetVal;
		
	}
}
