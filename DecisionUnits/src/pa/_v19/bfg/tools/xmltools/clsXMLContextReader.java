/**
 * clsXMLContextReader.java: DecisionUnits - bfg.tools.xmltools
 * 
 * @author zeilinger
 * 07.10.2009, 17:14:04
 */
package pa._v19.bfg.tools.xmltools;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * 
 * @author zeilinger
 * 07.10.2009, 17:14:04
 *  @deprecated
 */
public class clsXMLContextReader {
	
	private DocumentBuilderFactory dbf = null;
	private DocumentBuilder db = null;
	private Document document = null;
	
    public clsXMLContextReader(String fileName) throws XMLException {
			//create a DocumentBuilderFactory and configure it
	    try{
				dbf = DocumentBuilderFactory.newInstance();
				dbf.setValidating(false);
				dbf.setNamespaceAware(false);
		
				//create a DocumentBuilder
				db = dbf.newDocumentBuilder();

	      document = parseFile(fileName);
			}
			catch (ParserConfigurationException e)
			{throw new XMLException("XMLException: " + e);}
	 }
	  
      public Document getDocument()
	  {
	     return document;
	  }
    
	  public Document parseFile(String xmlFile) throws XMLException {
		try{
				//parse the input file
				Document doc = db.parse(new File(xmlFile));
				return doc;
			}
			catch (SAXException e)
			{throw new XMLException("XMLException: " + e);}
			catch (IOException e)
			{throw new XMLException("XMLException: " + e);}
	  }
	  
	//recursive scan
	 public static void getNodeElementByName(Node docElement, String detectionName, Vector<Node> resultList){
	  	getNodeElementByName(docElement, detectionName, -1, resultList);
	  }

	  //optional scan (recursive or not)
	  public static void getNodeElementByName(Node docElement, String detectionName, int recursive, Vector<Node> resultList){
			//Node n = null;
			
			if (docElement.getNodeType() == Node.ELEMENT_NODE){
		      String txt = docElement.getNodeName();
		      
		      if( txt != null && detectionName == txt ) {
		        resultList.add(docElement);
		        return;
		      }
			}
		    else {
		      return;
		    }
	    if(recursive>0){
	     	NodeList nl = docElement.getChildNodes();
	  		//Node child = null;
	  		
	  		for (int i=0; i<nl.getLength(); i++){
	  			getNodeElementByName(nl.item(i), detectionName, recursive--, resultList);		
	  		}
	    }
	    return;
	  }

	  //optional scan (recursive or not)
	   public static Node getNextNodeElementByName(Node docElement, String detectionName) 
	   {
			//Node n = null;
			if (docElement.getNodeType() == Node.ELEMENT_NODE){
			  String txt = docElement.getNodeName();

		      if( txt != null && detectionName.equals(txt) ){
		        return docElement;
		      }
		    }
			else{
		      return null;
		    }
	   	
			NodeList nl = docElement.getChildNodes();
			Node child = null;
			for (int i=0; i<nl.getLength(); i++){
				child = getNextNodeElementByName(nl.item(i), detectionName);
			    
				if( child != null)
			       return child;
			}
	    return child;
	  }
	   
	 //helper to get the attribute value from an element - if not defined return an empty string
	   //---------------------------------------------------------------------------
	 	public static String getAttributeValue(NamedNodeMap poAtrib, String poName) {
	   //---------------------------------------------------------------------------

	 		String oRetVal = "";
	 		Node oNode = poAtrib.getNamedItem(poName);
	 		if(oNode != null) {
	 			oRetVal = oNode.getNodeValue();
	 		}
	 		return oRetVal;
	 	}
}
