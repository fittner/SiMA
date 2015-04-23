// File clsRuleTreeLeaf.java
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
import bfg.symbolization.brainimages.clsImagePerception;
import bfg.tools.xmltools.clsXMLAbstractImageReader;
import bfg.utils.enums.enumOptionalType;
import bfg.utils.enums.enumTypeEmotion;
import bfg.utils.enums.enumTypeLevelEmotion;

//import pkgBrainEmotion.clsEmotion;
//import pkgBrainEmotion.clsContainerEmotion;

/**
 *
 * This is the class description ...
 *
 * $Revision: 791 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-07-20 04:09:01 +0200 (Fr, 20 Jul 2007) $: Date of last commit
 *
 */
class clsLeafEmotion extends clsRuleTreeLeaf
{
  public int meTypeEmotion; //enumTypeEmotion.TEMOTION_UNDEFINED;
  public int meTypeLevelEmotion = enumTypeLevelEmotion.TLEVELEMOTION_UNDEFINED;

  //---------------------------------------------------------------------------
  public static clsRuleTreeElement create(Node poNode)
  //---------------------------------------------------------------------------
  {
    clsLeafEmotion oResult = new clsLeafEmotion();
    if( oResult != null )
    {
      oResult.meTypeEmotion      = enumTypeEmotion.getInteger(clsXMLAbstractImageReader.getTagStringValue(poNode, "emotion"));
      NamedNodeMap oAttributes = poNode.getAttributes();
      if( oAttributes.getNamedItem("level") != null )
      {
        String oValue  = oAttributes.getNamedItem("level").getNodeValue();
        oResult.meTypeLevelEmotion = enumTypeLevelEmotion.getInteger(oValue);
      }
    }
    return oResult;
  }

  //---------------------------------------------------------------------------                                    
  @Override
  public boolean evaluateTree(clsImagePerception poImage, clsImageAbstract poAbstractImage, int[] poCompareResult, /*clsContainerComplexEmotion poBrainsComplexEmotions,*/ clsContainerPerceptions poBrainsPerceptions, clsIdentity poBrainsIdentity  )
  //---------------------------------------------------------------------------
  {
    if( meOptionalType != enumOptionalType.TOPT_OPTIONAL )
    {
      // for optional leafes we get a counter in the match list, but not in the list of all entries -->
      // compareResultValue increases if optional leafs match (can lead to more than 100% )
      poCompareResult[1]++;
    }
    boolean oResult = false;
//    if( compare( poImage.moEmotionList ) )
//    {
//      //Engine.log.println( "Match with leaf: " + this.toString() );
//      poCompareResult[0]++;
//      oResult = true;
//    }
    return oResult;
  }
  
  //---------------------------------------------------------------------------
  @Override
  public void weight(clsImagePerception pImage, clsImageAbstract aImage, clsRuleCompareResult compareResult)
  //--------------------------------------------------------------------------- 
  {
    //return E_NOTIMPL; ;-)
  }

//  //---------------------------------------------------------------------------
//  public boolean compare(clsContainerEmotion poEmotionList)
//  //---------------------------------------------------------------------------
//  {
//    //leafBubblesVisible info:
//    boolean nResult = false;
//
//    Set oTemp = poEmotionList.getKeySet();
//    Iterator oI = oTemp.iterator();
//    
//    while (oI.hasNext()) 
//    {
//      clsEmotion oEmotion = (clsEmotion)poEmotionList.get((Integer)oI.next());
//
//      if( meTypeEmotion != oEmotion.getType() )
//      {
//        continue;
//      }
//      if(  !meCompareOperator.compareInteger(oEmotion.getLevel(), meTypeLevelEmotion) ) {
//        continue; 
//      }
//      nResult = true;
//      break;  //not weighted now, if more than one perception exists!
//    }
//    if( mnNegated )
//    {
//      nResult = !nResult;;
//    }
//    return nResult;
//  }

  //---------------------------------------------------------------------------
  @Override
  public String toString() 
  //---------------------------------------------------------------------------
  {
    String oRetValue = "clsLeafEmotion: ";
    oRetValue += super.toString();
    oRetValue += " typeEmotion:"+enumTypeEmotion.getString(meTypeEmotion);
    oRetValue += " levelEmotion:"+enumTypeLevelEmotion.getString(meTypeLevelEmotion);
    return oRetValue;
  }
};


  
