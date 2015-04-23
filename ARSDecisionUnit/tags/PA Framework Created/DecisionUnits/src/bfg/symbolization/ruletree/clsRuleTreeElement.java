// File clsRuleTreeElement.java
// May 02, 2006
//

// Belongs to package
package bfg.symbolization.ruletree;

// Imports
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import bfg.symbolization.brainimages.clsContainerPerceptions;
import bfg.symbolization.brainimages.clsIdentity;
import bfg.symbolization.brainimages.clsImageAbstract;
//import pkgBrainComplexEmotion.clsContainerComplexEmotion;
import bfg.symbolization.brainimages.clsImagePerception;
import bfg.tools.xmltools.clsXMLAbstractImageReader;
import bfg.utils.enums.enumOptionalType;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 *
 */
public abstract class clsRuleTreeElement {

  public int meOptionalType = enumOptionalType.TOPT_UNDEFINED;
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
        String oOptional  = oAttributes.getNamedItem("optional").getNodeValue();
        oResult.meOptionalType = enumOptionalType.getInteger(oOptional);
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
    String oRetValue = " optionalType:"+enumOptionalType.getString(meOptionalType);
    oRetValue += " negated:"+mnNegated;
    return oRetValue;
  }

  //ABSTRACT FUNCTION DECLARATIONS
  public abstract boolean evaluateTree(clsImagePerception poImage, 
                                       clsImageAbstract poAbstractImage, 
                                       int[] poCompareResult, 
                                       //clsContainerComplexEmotion poBrainsComplexEmotions, 
                                       clsContainerPerceptions poBrainsPerceptions, 
                                       clsIdentity poBrainsIdentity
                                       );

};
