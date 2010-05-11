// File clsRuleTreeLeaf.java
// May 02, 2006
//

// Belongs to package
package pa.bfg.symbolization.ruletree;

// Imports
//import org.w3c.dom.Node;
//import org.w3c.dom.NamedNodeMap;
//import pkgXMLTools.clsXMLAbstractImageReader;
//import pkgEnum.enumOptionalType;
//import pkgEnum.enumTypeLevelComplexEmotion;
//import pkgEnum.enumTypeComplexEmotion;
//import pkgBrainComplexEmotion.clsContainerComplexEmotion;
//import pkgBrainComplexEmotion.clsComplexEmotion;
//import pkgBrain.clsIdentity;
//import pkgBrainImages.clsImagePerception;
//import pkgBrainImages.clsImageAbstract;
//import pkgBrainImages.clsContainerPerceptions;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 *
 */
class clsLeafComplexEmotion //extends clsRuleTreeLeaf
{
//  public int mnComplexEmotionId = -1;
//  public int meTypeLevelComplexEmotion = enumTypeLevelComplexEmotion.TLEVELCEMOTION_UNDEFINED;
//
//  //---------------------------------------------------------------------------
//  public static clsRuleTreeElement create(Node poNode)
//  //---------------------------------------------------------------------------
//  {
//    clsLeafComplexEmotion oResult = new clsLeafComplexEmotion();
//    if( oResult != null )
//    {
//      oResult.mnComplexEmotionId = enumTypeComplexEmotion.getInteger(clsXMLAbstractImageReader.getTagStringValue(poNode, "ComplexEmotionID"));
//      NamedNodeMap oAttributes = poNode.getAttributes();
//      if( oAttributes.getNamedItem("level") != null )
//      {
//        String oValue  = oAttributes.getNamedItem("level").getNodeValue();
//        oResult.meTypeLevelComplexEmotion = enumTypeLevelComplexEmotion.getInteger(oValue);
//      }
//    }
//    return oResult;
//  }
//
//  //---------------------------------------------------------------------------                                    
//  public boolean evaluateTree(clsImagePerception poImage, clsImageAbstract poAbstractImage, int[] poCompareResult, clsContainerComplexEmotion poBrainsComplexEmotions, clsContainerPerceptions poBrainsPerceptions, clsIdentity poBrainsIdentity)
//  //---------------------------------------------------------------------------
//  {
//    if( meOptionalType != enumOptionalType.TOPT_OPTIONAL )
//    {
//      // for optional leafes we get a counter in the match list, but not in the list of all entries -->
//      // compareResultValue increases if optional leafs match (can lead to more than 100% )
//      poCompareResult[1]++;
//    }
//    boolean oResult = false;
//    if( compare( poBrainsComplexEmotions ) )
//    {
//      //Engine.log.println( "Match with leaf: " + this.toString() );
//      poCompareResult[0]++;
//      oResult = true;
//    }
//    return oResult;
//  }
//  
//  //---------------------------------------------------------------------------
//  public void weight(clsImagePerception pImage, clsImageAbstract aImage, clsRuleCompareResult compareResult)
//  //--------------------------------------------------------------------------- 
//  {
//    //return E_NOTIMPL; ;-)
//  }
//
//  //---------------------------------------------------------------------------
//  public boolean compare(clsContainerComplexEmotion  poComplexEmotions)
//  //---------------------------------------------------------------------------
//  {
//    //leafBubblesVisible info:
//    boolean nResult = false;
//
//    clsComplexEmotion oComplexEmotion = poComplexEmotions.get( mnComplexEmotionId );
//    if( oComplexEmotion != null )
//    {
//      if( meCompareOperator.compare(oComplexEmotion.getValue(), meTypeLevelComplexEmotion, "complexEmotion") ) 
//      {
//        nResult = true;
//      }
//    }
//    if( mnNegated )
//    {
//      nResult = !nResult;;
//    }
//    return nResult;
//  }
//
//  //---------------------------------------------------------------------------
//  public String toString() 
//  //---------------------------------------------------------------------------
//  {
//    String oRetValue = "clsLeafComplexEmotion: ";
//    oRetValue += super.toString();
//    oRetValue += " typeComplexEmotion:"+mnComplexEmotionId;
//    oRetValue += " levelComplexEmotion:"+enumTypeLevelComplexEmotion.getString(meTypeLevelComplexEmotion);
//    return oRetValue;
//  }
};


