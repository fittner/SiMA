/**
 * clsAwareContentLoader.java: DecisionUnits - pa.loader
 * 
 * @author zeilinger
 * 20.10.2009, 15:54:49
 */
package pa.loader;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import enums.pa.eContext;
import enums.pa.eRepressedContentType;
import pa.datatypes.clsPrimaryInformation;
import pa.tools.clsTripple;
import statictools.clsGetARSPath;
import bfg.tools.xmltools.clsXMLAbstractImageReader;
import bfg.tools.xmltools.clsXMLContextReader;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 20.10.2009, 15:54:49
 * 
 */

public class clsAwareContentLoader {
	
		public static String moFileName = "TPAwareContent"; //denotes the directory name and the first element name in xml
		public static String moNodeName = "TPMesh"; //denotes one single entry for a drive
		public static String moAwareContentList = "AwareContentMatchList";
		public static String moAwareContent = "AwareContentMatch"; 
		public static String moDescription = "Description";
		
		
		public static HashMap<String,ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation,clsPrimaryInformation>>> createAwareContent() {
			
			  String oXmlPath = clsGetARSPath.getXMLPathMemory();
			  Vector<Node>oNodes = new Vector<Node>(); 
			  HashMap<String, ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation,clsPrimaryInformation>>> oRetVal 
			  					= new HashMap<String, ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation,clsPrimaryInformation>>>();
			  try{
				  oXmlPath += moFileName + ".xml";
		    	  clsXMLContextReader oReader = new clsXMLContextReader(oXmlPath);
		    	  clsXMLContextReader.getNodeElementByName((Node)oReader.getDocument().getDocumentElement(), 
																	moNodeName, 1, oNodes);
		    	  for(Node oElement : oNodes){
		    		  createTPMesh(oElement, oRetVal);  	 
		    	  }
		    	  
	 	      }catch(Exception e) {
					System.out.println("Error reading the content: "+e.getMessage());
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
		private static void createTPMesh(Node poElement, 
										HashMap<String,ArrayList<clsTripple<clsPrimaryInformation, 
										                                    clsPrimaryInformation, 
										                                    clsPrimaryInformation>>> poRetVal) {
				
				  NamedNodeMap oAtrib = poElement.getAttributes();
				  poRetVal.put(clsXMLContextReader.getAttributeValue(oAtrib,"name"), createAwareContentMatch(poElement));  		  
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
		private static ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation, clsPrimaryInformation>> createAwareContentMatch(Node poAwareContentNode) {
			Node oReaderNode;
			Vector<Node> oNodes = new Vector<Node>();
			ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation, clsPrimaryInformation>> oAwareContentList 
									= new ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation, clsPrimaryInformation>>(); 
			
			oReaderNode = clsXMLContextReader.getNextNodeElementByName(poAwareContentNode, moAwareContentList);	
			
			if(oReaderNode!= null){
				clsXMLContextReader.getNodeElementByName(oReaderNode, moAwareContent, 1, oNodes);
				try{
					for(Node element : oNodes){
						NamedNodeMap oAtrib = element.getAttributes();
						
						clsPrimaryInformation oAwareContent = clsStaticLoaderHelper.loadPrimaryInformation(element);
						oAwareContent.moTP.meContentName = "Aware Content";
						oAwareContent.moTP.meContentType = eRepressedContentType.class.getName();
						/*FIXME HZ no enumerator defined for awareContent - look at TPAwareContent.xml - AwareContent Names have to 
						 * 		be redefined*/
						oAwareContent.moTP.moContent = clsXMLAbstractImageReader.getAtributeValue(oAtrib, "content");
						
						oAwareContentList.add(new clsTripple<clsPrimaryInformation, clsPrimaryInformation, clsPrimaryInformation>
																		(getSearchContext(oAtrib), getSearchContent(oAtrib), oAwareContent));
					}
				}catch(Exception e){
					System.out.println("Wrong data type is present");
				}
			}
			return oAwareContentList; 
		}

		/**
		 * DOCUMENT (zeilinger) - insert description
		 *
		 * @author zeilinger
		 * 21.10.2009, 08:56:23
		 *
		 * @param atrib
		 * @return
		 */
		private static clsPrimaryInformation getSearchContent(NamedNodeMap poAtrib) {
			clsPrimaryInformation oSearchContent = new clsPrimaryInformation();
			
			oSearchContent.moTP.meContentName = "Repressed Content";
			oSearchContent.moTP.meContentType = eRepressedContentType.class.getName();
			oSearchContent.moTP.moContent =	eRepressedContentType.valueOf(clsXMLAbstractImageReader.
																getAtributeValue(poAtrib, "search_rep_content") );
		
			return oSearchContent;
		}

		/**
		 * DOCUMENT (zeilinger) - insert description
		 *
		 * @author zeilinger
		 * 21.10.2009, 08:55:07
		 *
		 * @param atrib
		 * @return
		 */
		private static clsPrimaryInformation getSearchContext(NamedNodeMap poAtrib) {
			clsPrimaryInformation oSearchContext = new clsPrimaryInformation();
			oSearchContext.moTP.meContentName = "Search Context";
			oSearchContext.moTP.meContentType =  clsXMLAbstractImageReader.getAtributeValue(poAtrib,"search_context"); 
			oSearchContext.moTP.moContent = eContext.valueOf(clsXMLAbstractImageReader.getAtributeValue(poAtrib,"search_context"));
			return oSearchContext;
		}
}
