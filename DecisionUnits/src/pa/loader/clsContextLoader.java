/**
 * clsContextLoader.java: DecisionUnits - pa.loader
 * 
 * @author zeilinger
 * 07.10.2009, 16:16:22
 */
package pa.loader;

import java.util.ArrayList;
import java.util.Vector;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import pa.datatypes.clsAssociationContext;
import pa.datatypes.clsThingPresentationSingle;
import statictools.clsGetARSPath;
import bfg.tools.xmltools.clsXMLContextReader;
import enums.pa.eContext;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 07.10.2009, 16:16:22|
 * 
 */
public class clsContextLoader {
	public static String moAttributeName = "TPMeshes"; //denotes the directory name and the first element name in xml
	public static String moNodeName = "TPMesh"; //denotes one single entry for a drive
	public static String moContextList = "ContextList";
	public static String moContext = "Context"; 
	public static String moDescription = "Description";
	
	
	public static ArrayList<clsAssociationContext<clsThingPresentationSingle>> createContext(String poEntityType) {
		
		  String xmlFileName = clsGetARSPath.getXMLPathMemory(); 
		  Vector<Node>oNodes = new Vector<Node>(); 
		  ArrayList<clsAssociationContext<clsThingPresentationSingle>> oRetVal = 
			  							new ArrayList<clsAssociationContext<clsThingPresentationSingle>>();
		  
	      try{
	    	  clsXMLContextReader oReader = new clsXMLContextReader(xmlFileName);
	    	  clsXMLContextReader.getNodeElementByName((Node)oReader.getDocument().getDocumentElement(), 
																moNodeName, 1, oNodes);
	    	  
	    	  for(Node element : oNodes){
	    		  Node oContextNode = element; 
	    		  clsAssociationContext <clsThingPresentationSingle> oContext = createContextList(oContextNode);
	    		  oRetVal.add(oContext); 
	    	  }
 	      }catch(Exception e) {
				System.out.println("Error reading the context: "+e.getMessage());
	  	 }
		
		return oRetVal;
	}
		
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 07.10.2009, 20:56:45
	 *
	 * @param contextNode
	 * @return
	 */
	private static clsAssociationContext<clsThingPresentationSingle> createContextList(Node poContextNode) {
		Node oReaderNode;
		clsAssociationContext<clsThingPresentationSingle> oRetVal = null; 
		Vector<Node> oNodes = new Vector<Node>();
		oReaderNode = clsXMLContextReader.getNextNodeElementByName(poContextNode, moContextList);	
		clsXMLContextReader.getNodeElementByName(oReaderNode, moContext, 1, oNodes);
		
		for(Node element : oNodes){
			oRetVal = new clsAssociationContext<clsThingPresentationSingle>();
			NamedNodeMap oAtrib = element.getAttributes();
			oRetVal.moAssociationContext.meContentType = eContext.valueOf(clsXMLContextReader.getAttributeValue(oAtrib, "type")).toString(); 
			oRetVal.moAssociationContext.moContent = Double.parseDouble(clsXMLContextReader.getAttributeValue(oAtrib, "pleasure")); 
			oRetVal.moWeight = Double.parseDouble(clsXMLContextReader.getAttributeValue(oAtrib, "weight"));
  	  	}
		
		return oRetVal;
	}
}
