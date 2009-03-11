package nq_xmlreader;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import org.xml.sax.SAXException;
import javax.xml.validation.*;

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
        
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(fileName));
        
        /*	dbf.setValidating(false); // don't use DTD, use Schema 
    	dbf.setSchema(schema); 
    	dbf.setIgnoringComments(true); 
    	dbf.setCoalescing(true); 
    	dbf.setNamespaceAware(true); // or false, depends 
    	dbf.setXIncludeAware(true);  // of false, depends */
    	
    	
    	DocumentBuilder db = dbf.newDocumentBuilder();
    	
		Document document = db.parse(file);
    
		
		document.getDocumentElement().normalize();
		
    	
    	
		System.out.println("Root element is: "+document.getDocumentElement().getNodeName());
		node = document.getElementsByTagName("Bubble");
	    System.out.println("Information of the BubbleWorld is given below:");
	  } catch (SAXException ex) 
	       {
		  System.err.println("XML file is not according to schema");
		  System.exit(1);
		  // stop parsing
		    }
    
      catch (Exception ex) 
	       {
		  System.err.println("Error reading XML file " + fileName);
		  System.exit(1);
		  // stop parsing
		    }
	
    
	  try {
		for (int i = 0; i < node.getLength(); i++) {
		   Node currentNode = node.item(i);
		    

		  if (currentNode.getNodeType() == Node.ELEMENT_NODE) 
		    { 
			   
		      Element element = (Element) currentNode;
		      NodeList posXElementList = element.getElementsByTagName("PosX");
		      
		   
		      
		       Element firstNameElement = (Element) posXElementList.item(0);
		      
	      
		      NodeList firstName = firstNameElement.getChildNodes();
		   	 		      
		      System.out.println("Position X of Bubble " + (i+1) + " is :" + firstName.item(0).getNodeValue());
		      
		     NodeList posYElementList = element.getElementsByTagName("PosY");
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