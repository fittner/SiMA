// File clsRuleTreeElement.java
// May 02, 2006
//

// Belongs to package
package pa._v19.bfg.symbolization.ruletree;

// Imports
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

import pa._v19.bfg.symbolization.brainimages.clsIdentity;
import pa._v19.bfg.tools.xmltools.clsXMLAbstractImageReader;


import du.itf.sensors.clsSensorData;
import bfg.utils.enums.eOptional;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 * @deprecated
 */
public abstract class clsRuleTreeElement {

  public eOptional meOptionalType = eOptional.UNDEFINED;
  public boolean mnNegated = false;

  //---------------------------------------------------------------------------
  public static clsRuleTreeElement createRuleTreeElement(Node poNode)
  //---------------------------------------------------------------------------
  {
    clsRuleTreeElement oResult = null;
    String oName = clsXMLAbstractImageReader.getNodeName( poNode );

    if( oName == "TreeRoot" || oName == "node")
    {
      oResult = (clsRuleTreeElement)clsRuleTreeNode.createRuleTreeNode(poNode);
    }
    else if( oName.startsWith("leaf"))
    {
      oResult = (clsRuleTreeElement)clsRuleTreeLeaf.createRuleTreeLeaf(poNode);
    }
    else
    {
      //Engine.log.println("clsRuleTreeElement::createRuleTreeElement - unknown tag name");
    }

    if( oResult != null )
    {
      NamedNodeMap oAttributes = poNode.getAttributes();

      //could be optional
      if( oAttributes.getNamedItem("optional") != null )
      {
    	oResult.meOptionalType  = eOptional.valueOf(oAttributes.getNamedItem("optional").getNodeValue());
      }

      if( oAttributes.getNamedItem("negated") != null )
      {
        String oNegated = oAttributes.getNamedItem("negated").getNodeValue();
        oResult.mnNegated = Boolean.valueOf(oNegated).booleanValue();
      }
    }
    return oResult;
  }

  //---------------------------------------------------------------------------
  @Override
  public String toString() 
  //---------------------------------------------------------------------------
  {
    String oRetValue = " optionalType:"+meOptionalType.toString();
    oRetValue += " negated:"+mnNegated;
    return oRetValue;
  }

  //ABSTRACT FUNCTION DECLARATIONS
  public abstract boolean evaluateTree(clsSensorData poPerception, 
		  							   clsIdentity poBrainsIdentity, 
		  							   int[] poCompareResult );

};
