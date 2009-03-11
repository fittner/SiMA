/*
  Features of file:
     1. Objects are loading from XML file to Bubble world 
     2. Checking compatibility of XML file with XML schema
     
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
import org.w3c.dom.NodeList;
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
	
	public void loadObjects() 
	{
		
		NodeList nodelist = null;

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
		  	
	       } 
		catch (SAXException ex)        // Catching Schema miss-match error
	       {
		  System.err.println("XML file is not according to schema");
		  System.exit(1);
		  // stop parsing
		   }
    
      catch (Exception ex)            // Catching XML file validity
	       {
		  System.err.println("Error reading XML file " + xmlfileName);
		  System.exit(1);
		  // stop parsing
		   }
		
		//FIXME before creating any other object, call createGrid(pnWidth, pnHeight);

	}  // End of loadObjects() method

}   // End of clsSimpleXMLLoader class
