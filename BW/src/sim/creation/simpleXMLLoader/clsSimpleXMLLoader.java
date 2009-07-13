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

package sim.creation.simpleXMLLoader;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import sim.creation.clsLoader;
import sim.engine.SimState;


/**
 * a proof of concept implementation for a simple xml loader. implemented by nauman
 * 
 * @deprecated
 * 
 * @author nauman
 * @author deutsch
 * 16.03.2009, 11:05:21
 * 
 */
public class clsSimpleXMLLoader extends clsLoader {

	String moXMLfileName;
	
	// Constructor 
	
	public clsSimpleXMLLoader(SimState poSimState, String xmlfileName)
	{
		super(poSimState);
		moXMLfileName = xmlfileName;
	}

	
	//Loading Bubble World
	
	public static void main(String argv[]) {
		clsSimpleXMLLoader.load(null, sim.clsBWMain.msArsPath + "/src/xml/xmlSimpleXMLLoader/config1.xml");
	}
	
	@Override
	public void loadObjects() {
		clsSimpleXMLLoader.load(this, moXMLfileName);
	}
	
	private static void load(clsSimpleXMLLoader poLoader, String xmlfileName) 
	{
		// Initializing array of nodelist (to put lists of elements like all Bubble list etc.)
		NodeList[] nodelist;
		nodelist = new NodeList[10];


		try {
	    	File file = new File(xmlfileName);
	    	
	        DocumentBuilderFactory dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
	    	dbf.setValidating(true);  

//	    	javax.xml.validation.SchemaFactory schemafactory = javax.xml.validation.SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
//	    	Source schemafile = new StreamSource("S:\\ARS\\PA\\BWv1\\ExtTools\\src\\test_xml\\test.xsd");
//	    	javax.xml.validation.Schema schema = schemafactory.newSchema(schemafile);
//	    	dbf.setSchema(schema);
	    	
	    	dbf.setFeature("http://apache.org/xml/features/validation/schema", true);
	    	dbf.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true); 
	    	
	    	dbf.setIgnoringComments(true); 
	    	dbf.setCoalescing(true); 
	    	dbf.setNamespaceAware(true); // or false, depends 
	    	dbf.setXIncludeAware(true);  // of false, depends
	    	

	    	
	    	DocumentBuilder db = dbf.newDocumentBuilder();
	    	
	    	// use db.setErrorHandler to add a new errorhandler that can throw exceptions. the default handler just writes to the system console
	    	db.setErrorHandler(new SimpleErrorHandler());
			Document document = db.parse(file);
			
			
		
    
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
      
        	if (poLoader != null) {
        	  poLoader.createGrids(width, height);
        	}
        	
        	// Call createGrids method of super class
        	/*	super.createGrids(25,80); //   window pnWidth and pnHeight */
      
        // Load Window Ends 
        // ----------------------------------------------------------------------
         
            // Load StationaryItems (Wall)
            // ----------------------------------------------------------------------- 
               	
                 LoadStationaryItems.loadWorldBoundaries(nodelist[4]);
               
             // Load StationaryItems Ends 
             // ----------------------------------------------------------------------	        	
     
        	
        // Load MobileItems (Bubbles,Stones,Cans,RemoteBot)
        // ----------------------------------------------------------------------- 
        	
        	LoadMobileItems.loadBubbles(nodelist[0].getLength(),nodelist[0]);
        	LoadMobileItems.loadStones(nodelist[1].getLength(),nodelist[1]);
        	LoadMobileItems.loadCans(nodelist[2].getLength(),nodelist[2]);
        	LoadMobileItems.loadRemoteBot(nodelist[3].getLength(),nodelist[3]);
        	
         // Load MobileItems Ends 
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
