// package nq_xmlreader;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

public class AccessingXmlFile {
	
	public static void main(String argv[]) {

		NodeList node = null;
    	// String fileName = "S:\\ARS\\PA\\BWv1\\TestApps\\src\\nq_xmlreader\\testxmlfile.xml";
		String fileName = "C:\\TestProject\\testxmlfile.xml";

    try {
    		File file = new File(fileName);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
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