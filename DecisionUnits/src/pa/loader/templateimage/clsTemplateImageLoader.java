/**
 * clsTemplateImageLoader.java: DecisionUnits - pa.loader
 * 
 * @author langr
 * 23.10.2009, 12:11:14
 */
package pa.loader.templateimage;

import java.util.ArrayList;
import java.util.Vector;

import org.w3c.dom.Node;

import bfg.tools.xmltools.clsXMLAbstractImageReader;
import bfg.tools.xmltools.clsXMLConfiguration;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 23.10.2009, 12:11:14
 * 
 */
public class clsTemplateImageLoader {

	private static String moAttributeName = "TemplateImages"; //denotes the directory name and the first element name in xml
	public static String moNodeName = "TemplateImage"; //denotes one single entry for an object
	
	public static ArrayList<itfTemplateComparable> createTemplateImageList(String poAgentId, String poAgentGroup) {

		ArrayList<itfTemplateComparable> oRetVal = new ArrayList<itfTemplateComparable>();
		
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
			System.out.println("Error reading Drives: "+e.getMessage());
		}
		
		return oRetVal;
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 23.10.2009, 14:39:17
	 *
	 * @param objTemImage
	 */
	private static itfTemplateComparable createTemplateImage(Node objTemImage) {
		itfTemplateComparable oRetVal = null;
		
		//create the TemplateImageBase and put in the fine tree!
		
		return oRetVal;
	}

}
