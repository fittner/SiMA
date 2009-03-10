package nq_xmlreader;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.*;

public class AccessingXmlFile {
	
	public static void main(String argv[]) {

		NodeList node = null;

        String fileName = "S:/ARS/PA/BWv1/TestApps/src/nq_xmlreader/testxmlfile.xml";

		// String fileName = "C:\\TestProject\\testxmlfile.xml";

    try {
    	
    	File file = new File(fileName);
    	
    	javax.xml.validation.SchemaFactory schemafactory = javax.xml.validation.SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
    	Source schemafile = new StreamSource("S:/ARS/PA/BWv1/TestApps/src/nq_xmlreader/testxsdfile.xsd");
    	javax.xml.validation.Schema schema = schemafactory.newSchema(schemafile);
        DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
    	dbf.setValidating(false); // don't use DTD, use Schema 
    	dbf.setSchema(schema); 
    	dbf.setIgnoringComments(true); 
    	dbf.setCoalescing(true); 
    	dbf.setNamespaceAware(true); // or false, depends 
    	dbf.setXIncludeAware(true);  // of false, depends 
    	
    	DocumentBuilder db = dbf.newDocumentBuilder();
    	
		Document document = db.parse(file);
		
		
		document.getDocumentElement().normalize();
		
    	
    	
		System.out.println("Root element is: "+document.getDocumentElement().getNodeName());
		node = document.getElementsByTagName("Bubble");
	    System.out.println("Information of the BubbleWorld is given below:");
	  } catch (Exception e) 
	       {
		  System.err.println("Error reading file " + fileName);
		  e.printStackTrace();
		  // stop parsing
		    }
	 
	  
	  
	  try {
		for (int i = 0; i < node.getLength(); i++) {
		   Node currentNode = node.item(i);
		    
		   
		   
		   if (currentNode.getNodeType() == Node.ELEMENT_NODE) 
		    { 
			   
		      Element element = (Element) currentNode;
		      NodeList posXElementList = element.getElementsByTagName("posX");
		      
		      
		      Element firstNameElement = (Element) posXElementList.item(0);
		      
	      
		      NodeList firstName = firstNameElement.getChildNodes();
		   	 		      
		      System.out.println("Position X of Bubble " + (i+1) + " is :" + firstName.item(0).getNodeValue());
		      
		      NodeList posYElementList = element.getElementsByTagName("posY");
		      Element lastNameElement = (Element) posYElementList.item(0);
		      NodeList lastName = lastNameElement.getChildNodes();
		      System.out.println("Position Y of Bubble " + (i+1) + " is :" + lastName.item(0).getNodeValue());
  		    }
		  }
		  } catch (Exception e) {
			  System.err.println("Error parsing Bubble ");
		    e.printStackTrace();
		  } 
	  
	 }
}