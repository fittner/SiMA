// File clsXMLAbstractImageReader.java
// May 08, 2006
//

// Belongs to package
package pa.bfg.tools.xmltools;

// Imports
import java.util.Vector;
import java.io.File;
import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
//import pkgTools.clsCloneable;


/**
 *
 * This is the class description ...
 *
 * $Revision: 644 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-06-27 17:51:30 +0200 (Mi, 27 Jun 2007) $: Date of last commit
 *
 */
public class clsXMLAbstractImageReader // extends clsCloneable 
{

  public static String moAbstractImage   = "AbstractImage";
  public static String moTargetLevels    = "TargetLevels";
  public static String moAbstractImageID = "AbstractImageID";
  public static String moTreeRoot        = "TreeRoot";

  public static String moTagID           = "ID";
  public static String moTagName         = "NAME";
  public static String moTagDescription  = "DESCR";
  public static String moTagOptional     = "optional";
  public static String moTagNegated      = "negated";
  public static String moTagBoolOperator = "booleanOperator";
  public static String moTagCompare      = "compare";

	private DocumentBuilderFactory dbf = null;
	private DocumentBuilder db = null;
  private Document document = null;

/* these variables are private and never used in this class ... td
	private Document erg = null;	//global definition for the result
									              //document - needed for the recursion

*/

  public Document getDocument()
  {
    return document;
  }

  //---------------------------------------------------------------------------
  public clsXMLAbstractImageReader() throws XMLException 
  //---------------------------------------------------------------------------
  {
		//create a DocumentBuilderFactory and configure it
		try 
    {
			dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			dbf.setNamespaceAware(false);
	
			//create a DocumentBuilder
			db = dbf.newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{throw new XMLException("XMLException: " + e);}
	}

  //---------------------------------------------------------------------------
  public clsXMLAbstractImageReader(String fileName) throws XMLException 
  //---------------------------------------------------------------------------
  {
		//create a DocumentBuilderFactory and configure it
		try 
    {
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

  //---------------------------------------------------------------------------
  public Document parseFile(String xmlFile) throws XMLException 
  //---------------------------------------------------------------------------
  {
		try 
    {
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
  //---------------------------------------------------------------------------
	public static void getNodeElementByName(Node docElement, String detectionName, Vector resultList) 
  //---------------------------------------------------------------------------
  {
  	getNodeElementByName(docElement, detectionName, -1, resultList);
  }

  //optional scan (recursive or not)
  //---------------------------------------------------------------------------
	public static void getNodeElementByName(Node docElement, String detectionName, int recursive, Vector resultList) 
  //---------------------------------------------------------------------------
  {
		Node n = null;
		if (docElement.getNodeType() == Node.ELEMENT_NODE) 
    {
      String txt = docElement.getNodeName();
      if( txt != null && detectionName == txt )
      {
        resultList.add(docElement);
        return;
      }
    }
    else
    {
      return;
    }
    if(recursive>0)
    {
     	NodeList nl = docElement.getChildNodes();
  		Node child = null;
  		for (int i=0; i<nl.getLength(); i++) 
      {
  			getNodeElementByName(nl.item(i), detectionName, recursive--, resultList);		
      }
    }
    return;
  }

	  //optional scan (recursive or not)
	  //---------------------------------------------------------------------------
		public static void getSubNodesByName(Node docElement, String detectionName, Vector resultList) 
	  //---------------------------------------------------------------------------
	  {
		Node n = null;

     	NodeList nl = docElement.getChildNodes();
  		Node child = null;
  		for (int i=0; i<nl.getLength(); i++) 
  		{
  			getNodeElementByName(nl.item(i), detectionName, 0, resultList);		
  		}
	    return;
	  }
	

  //optional scan (recursive or not)
  //---------------------------------------------------------------------------
	public static Node getNextNodeElementByName(Node docElement, String detectionName) 
  //---------------------------------------------------------------------------
  {
		Node n = null;
		if (docElement.getNodeType() == Node.ELEMENT_NODE) 
    {
      String txt = docElement.getNodeName();

      if( txt != null && detectionName.equals(txt) )
      {
        return docElement;
      }
    }
    else
    {
      return null;
    }
   	NodeList nl = docElement.getChildNodes();
		Node child = null;
		for (int i=0; i<nl.getLength(); i++) 
    {
			child = getNextNodeElementByName(nl.item(i), detectionName);
      if( child != null)
       return child;
    }
    return child;
  }

  //---------------------------------------------------------------------------
  public static boolean getNodeData(Node node, String[] nodeName, String[] value, NamedNodeMap attributeList, boolean[] isLeaf)
  //---------------------------------------------------------------------------
  {
    if( node == null ) return false;

		Node n = null;
		if (node.getNodeType() == Node.ELEMENT_NODE) 
    {
      nodeName[0] = node.getNodeName();
			if (node.hasAttributes()) 
      {
				attributeList = node.getAttributes();
			}
		}
    else 
    {
			return false;
		}

		NodeList nl = node.getChildNodes();
		Node child = null;

    //node with one text-element must be a 'Data-Node'
    if ( nl.getLength() == 1 && ((Node)nl.item(0)).getNodeType() == Node.TEXT_NODE) 
    {
      value[0] = ((Node)nl.item(0)).getNodeValue();
      isLeaf[0] = true;
    }
    else
    {
      isLeaf[0] = false;  
    }
    return true;
  }

  //---------------------------------------------------------------------------
  public static String getNodeName(Node node)
  //---------------------------------------------------------------------------
  {
 		Node n = null;
		if (node.getNodeType() == Node.ELEMENT_NODE) 
    {
      return node.getNodeName();
		}
    else 
    {
			return "";
		}
  }

  //returns real subnodes - not text-nodes
  //---------------------------------------------------------------------------
  public static Vector getSubNodes(Node node)
  //---------------------------------------------------------------------------
  {
    Vector retVal = new Vector();
 		if (node.getNodeType() == Node.ELEMENT_NODE) 
    {
  		NodeList nl = node.getChildNodes();
  		for (int i=0; i<nl.getLength(); i++) 
      {
        Node child = nl.item(i);
        if ( !isTextNode(child) )
        {
          retVal.add(child);
        }
  		}
    }
    return retVal;
  }

  //node with one element in it, that is a textnode is a textnode-node
  //---------------------------------------------------------------------------
  public static boolean isTextNode(Node node)
  //---------------------------------------------------------------------------
  {
    NodeList nl = node.getChildNodes();
    //this node is a text node (every line 
    if( node.getNodeType() == Node.TEXT_NODE )
    {
      return true;
    }

    //the node contains only a text node
    if ( nl.getLength() == 1 && ((Node)nl.item(0)).getNodeType() == Node.TEXT_NODE) 
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  //---------------------------------------------------------------------------
  public static int stringToInteger(String toParse)
  //---------------------------------------------------------------------------
  {
    try
    {
      return Integer.parseInt(toParse);
    }
    catch(NumberFormatException nfx)
    {
      return -1;
    }
  }

  //---------------------------------------------------------------------------
  public static String getAttributeByName(Node poNode, String poAttributeName )
  //---------------------------------------------------------------------------
  {
    String oResult = "";
    NamedNodeMap oAttributes = poNode.getAttributes();
    if( oAttributes.getNamedItem(poAttributeName) != null )
    {
      oResult = clsXMLReaderTools.getNamedItemString( oAttributes, poAttributeName);
    }
    return oResult;
  }

  //getSpecialTags (most common tags)
  //---------------------------------------------------------------------------
  public static String getTagStringValue(Node currentNode, String name)
  //---------------------------------------------------------------------------
  {
    Node retNode = null;
    retNode = getNextNodeElementByName(currentNode, name);
    boolean[] isLeaf = { false };
    String[] nodeName = {""}, value = {""};
    NamedNodeMap attributeList = null;
    getNodeData( retNode, nodeName, value, attributeList, isLeaf );
    return value[0];
  }
 
  //---------------------------------------------------------------------------
  public static int getTagUIntValue(Node currentNode, String name)
  //---------------------------------------------------------------------------
  {
    int oResult = -1;

    Node retNode = null;
    retNode = getNextNodeElementByName(currentNode, name);
    boolean[] isLeaf = { false };
    String[] nodeName = {""}, value = {""};
    NamedNodeMap attributeList = null;
    getNodeData( retNode, nodeName, value, attributeList, isLeaf );

    String oValue = value[0];
    if( oValue != null && !oValue.equals("") )
    {
      oResult = Integer.parseInt( oValue );
    }
    return oResult;
  }

  //---------------------------------------------------------------------------
  public static String getTagSubStringValue(Node currentNode, String name)
  //---------------------------------------------------------------------------
  {
    String retVal = "";
 		NodeList nl = currentNode.getChildNodes();
		Node child = null;
		
		for (int i=0; i<nl.getLength(); i++) 
    {
      retVal = getTagStringValue(nl.item(i), name);
      if( retVal.equals("") == false )
      {
        return retVal; 
      }
		}   
    return retVal;
  } 

  //---------------------------------------------------------------------------
  public static String getTagID(Node currentNode)
  //---------------------------------------------------------------------------
  {
    return getTagStringValue( currentNode, moTagID );
  }

  //---------------------------------------------------------------------------
  public static String getTagName(Node currentNode)
  //---------------------------------------------------------------------------
  {
    return getTagStringValue( currentNode, moTagName );
  }
  //---------------------------------------------------------------------------
  public static String getTagDescription(Node currentNode)
  //---------------------------------------------------------------------------
  {
    return getTagStringValue( currentNode, moTagDescription );
  }
  //---------------------------------------------------------------------------
  public static String getTagOptional(Node currentNode)
  //---------------------------------------------------------------------------
  {
    return getTagStringValue( currentNode, moTagOptional );
  }
  //---------------------------------------------------------------------------
  public static String getTagNegated(Node currentNode)
  //---------------------------------------------------------------------------
  {
    return getTagStringValue( currentNode, moTagNegated );
  }
  //---------------------------------------------------------------------------
  public static String getTagBoolOperator(Node currentNode)
  //---------------------------------------------------------------------------
  {
    return getTagStringValue( currentNode, moTagBoolOperator );
  }
  //---------------------------------------------------------------------------
  public static String getTagCompare(Node currentNode)
  //---------------------------------------------------------------------------
  {
    return getTagStringValue( currentNode, moTagCompare );
  }


  //helper to get the attribute value from an element - if not defined return an empty string
  //---------------------------------------------------------------------------
	public static String getAtributeValue(NamedNodeMap poAtrib, String poName) {
  //---------------------------------------------------------------------------

		String oRetVal = "";
		Node oNode = poAtrib.getNamedItem(poName);
		if(oNode != null) {
			oRetVal = oNode.getNodeValue();
		}
		return oRetVal;
	}


};


