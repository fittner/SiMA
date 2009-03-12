/*
  Summary of file:
     1. Checking compatibility of XML file with XML schema
     2. Loading DOM from XML file
     3. Load Window from DOM  
     4. Instantiate objects for Mobile & Stationary classes and call their methods for loading objects 
     
  Author:
     Nauman Qadeer
     
  Version1 date:  
     11th March 2009
*/

package bw.sim.creation.simpleXMLLoader;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Validator;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import sim.engine.SimState;
import bw.sim.creation.clsLoader;


public class clsSimpleXMLLoader extends clsLoader {

	
	// Constructor 
	
	public clsSimpleXMLLoader(SimState poSimState)
	{
		super(poSimState);
	}

	
	//Loading Bubble World
	
	public static void main(String argv[])    //  public void loadObjects() 
	{
		// Initializing array of nodelist (to put lists of elements like all Bubble list etc.)
		NodeList[] nodelist;
		nodelist = new NodeList[10];

        String xmlfileName = "S:/ARS/PA/BWv1/TestApps/src/nq_xmlreader/testxmlfile.xml";

		try {
    	
    	//Create "Schema Factory" object
    	SchemaFactory schemafactory = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
    	
    	// Define "XML Schema file"
    	Source schemafile = new StreamSource("S:/ARS/PA/BWv1/TestApps/src/nq_xmlreader/testxsdfile.xsd");
    	
    	//Create "Schema" object (from Schema Factory Object) 
    	Schema schema = schemafactory.newSchema(schemafile);
        
        // Create "Schema Validator" object
        Validator validator = schema.newValidator();
        
        // Define XML file
        File xmlfile = new File(xmlfileName);
        
        // Validate XML file against XML schema
        validator.validate(new StreamSource(xmlfileName));
           	
        // Create "Document Builder Factory" object
        DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        
        // Create "Document Builder" object (from Document Builder Factory object)
    	DocumentBuilder db = dbf.newDocumentBuilder();
    	
    	//Create DOM object of XML file
    	Document document = db.parse(xmlfile);
    
		// Normalize DOM object
		document.getDocumentElement().normalize();
		     
        // Get Values from XML file into nodelists
    	  
        nodelist[0] = document.getElementsByTagName("Bubble");
        nodelist[1]=  document.getElementsByTagName("Stone");
        nodelist[2]=  document.getElementsByTagName("Can");
        nodelist[3]=  document.getElementsByTagName("RemoteBot");
        nodelist[4]=  document.getElementsByTagName("Wall");
        nodelist[5]=  document.getElementsByTagName("Window");
      
        // Load window
        // -----------------------------------------------------------------------
      
        	System.out.println("Diplaying values for Window");
        	System.out.println("--------------------------------");        
        
        	Node currentNode = nodelist[5].item(0);  // Because Window is only one so see only first node 
 		    
        	// Convert node into element to get its child nodes
        	Element element = (Element) currentNode;
 	    
        	// get Width of Window
        	NodeList widthlist = element.getElementsByTagName("Width");
        	Element widthelement = (Element) widthlist.item(0); 	      
        	NodeList widthchildnodelist = widthelement.getChildNodes();
        	int width = new Integer(widthchildnodelist.item(0).getNodeValue());  	 		      
 		    
        	// Print Width (to verify just)
        	System.out.println("Width of Window is: " + width);
 		      
        	// get Height of Window
        	NodeList heightlist = element.getElementsByTagName("Height");
        	Element heightelement = (Element) heightlist.item(0); 	      
        	NodeList heightchildnodelist = heightelement.getChildNodes();
        	int height = new Integer(heightchildnodelist.item(0).getNodeValue());  	 		      
 		    
        	// Print Height (to verify just)
        	System.out.println("Height of Window is: " + height);     
        	System.out.println();
      
        	// Call createGrids method of super class
        	/*	super.createGrids(25,80); //   window pnWidth and pnHeight */
      
        // Load Window Ends 
        // ----------------------------------------------------------------------
         
     
        	
        // Load MobileItems (Bubbles,Stones,Cans,RemoteBot)
        // ----------------------------------------------------------------------- 
        	
        	LoadMobileItems lmi = new LoadMobileItems();
        	lmi.loadBubbles(nodelist[0].getLength(),nodelist[0]);
        	lmi.loadStones(nodelist[1].getLength(),nodelist[1]);
        	lmi.loadCans(nodelist[2].getLength(),nodelist[2]);
        	lmi.loadRemoteBot(nodelist[3].getLength(),nodelist[3]);
        	
         // Load MobileItems Ends 
         // ----------------------------------------------------------------------	
        	
        
        	
         // Load StationaryItems (Wall)
         // ----------------------------------------------------------------------- 
            	
              LoadStationaryItems lsi = new LoadStationaryItems();
              lsi.loadWorldBoundaries(nodelist[4]);
            
          // Load StationaryItems Ends 
          // ----------------------------------------------------------------------	
        
        	
        	
        	
		 }                             // End Try 
		catch (SAXException ex)        // Catching Schema miss-match error
	       {
		  System.err.println("XML file is not according to schema");
		  System.exit(1);
		  // stop parsing
		   }
    
      catch (Exception ex)            // Catching XML file validity
	       {
		  System.err.println("Error reading XML file:  " + xmlfileName);
		  System.exit(1);
		  // stop parsing
		   }
      
      
	}  // End of loadObjects() method

}   // End of clsSimpleXMLLoader class
