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

import pa.bfg.tools.xmltools.clsXMLContextReader;
import pa.datatypes.clsAssociationContext;
import pa.datatypes.clsThingPresentationSingle;
import statictools.clsGetARSPath;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 07.10.2009, 16:16:22|
 * 
 */
@Deprecated
public class clsContextLoader {
	public static String moFileName = "TPContext"; //denotes the directory name and the first element name in xml
	public static String moNodeName = "TPMesh"; //denotes one single entry for a drive
	public static String moContextList = "ContextList";
	public static String moContext = "Context"; 
	public static String moDescription = "Description";
	
	
	public static ArrayList<clsAssociationContext<clsThingPresentationSingle>> createContext(String poEntityType) {
		
		  String oXmlPath = clsGetARSPath.getXMLPathMemory();
		  Vector<Node>oNodes = new Vector<Node>(); 
		  ArrayList<clsAssociationContext<clsThingPresentationSingle>> oRetVal = 
			  							new ArrayList<clsAssociationContext<clsThingPresentationSingle>>();
		  try{
			  oXmlPath += moFileName + ".xml";
	    	  clsXMLContextReader oReader = new clsXMLContextReader(oXmlPath);
	    	  clsXMLContextReader.getNodeElementByName((Node)oReader.getDocument().getDocumentElement(), 
																moNodeName, 1, oNodes);
	    	  for(Node oElement : oNodes){
	    		  creatTPMesh(oElement, oRetVal, poEntityType);  	 
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
	 * 08.10.2009, 20:43:12
	 * @param element 
	 *
	 * @param retVal
	 * @param poEntityType
	 */
	private static void creatTPMesh( Node poElement,
									 ArrayList<clsAssociationContext<clsThingPresentationSingle>> poRetVal,
									 String poEntityType) {
			
			  NamedNodeMap oAtrib = poElement.getAttributes();
						    		  
	  		  if(clsXMLContextReader.getAttributeValue(oAtrib,"name").equals(poEntityType)){ 
		    		  createContextList(poRetVal, poElement);
		      }
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 07.10.2009, 20:56:45
	 * @param poRetVal 
	 *
	 * @param contextNode
	 * @return
	 */
	private static void createContextList(ArrayList<clsAssociationContext<clsThingPresentationSingle>> poRetVal, Node poContextNode) {
		Node oReaderNode;
		String oContextName; 
		String oContextType;
		Double oContextContent; 
		
		clsAssociationContext<clsThingPresentationSingle> oContext = null; 
		Vector<Node> oNodes = new Vector<Node>();
		oReaderNode = clsXMLContextReader.getNextNodeElementByName(poContextNode, moContextList);	
		clsXMLContextReader.getNodeElementByName(oReaderNode, moContext, 1, oNodes);
		try{
			for(Node element : oNodes){
				oContext = new clsAssociationContext<clsThingPresentationSingle>();
				NamedNodeMap oAtrib = element.getAttributes();
				oContextName = clsXMLContextReader.getAttributeValue(oAtrib, "type");
				oContextType = clsXMLContextReader.getAttributeValue(oAtrib, "type"); 
				oContextContent = Double.parseDouble(clsXMLContextReader.getAttributeValue(oAtrib, "affect_memory"));
				
				oContext.moAssociationContext = new clsThingPresentationSingle(oContextName, oContextType,oContextContent); 
				oContext.moWeight = Double.parseDouble(clsXMLContextReader.getAttributeValue(oAtrib, "weight"));
				
				if(oContext.moWeight>0){
					poRetVal.add(oContext); 
				}
	  	  	}
		}catch(Exception e){
			System.out.println("Wrong data type is present");
		}
	}
	
	
	
}
