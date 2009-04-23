// File clsRuleTreeLeaf.java
// May 02, 2006
//

// Belongs to package
package bfg.symbolization.ruletree;

// Imports
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import bfg.symbolization.brainimages.clsIdentity;
import bfg.symbolization.brainimages.clsImagePerception;
import bfg.symbolization.brainimages.clsImageAbstract;
import bfg.symbolization.brainimages.clsContainerPerceptions;
import bfg.symbolization.brainimages.clsPerceptionBumped;
import bfg.utils.enums.enumOptionalType;
import bfg.utils.enums.enumTypeTrippleState;

//import pkgBrainComplexEmotion.clsContainerComplexEmotion;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 *
 */
class clsLeafBumped extends clsRuleTreeLeaf
{
  public int meBumped = enumTypeTrippleState.TTRIPPLE_UNDEFINED;

  //---------------------------------------------------------------------------
  public static clsRuleTreeElement create(Node poNode)
  //---------------------------------------------------------------------------
  {
    clsLeafBumped oResult = new clsLeafBumped();
    if( oResult != null )
    {
      NamedNodeMap oAttributes = poNode.getAttributes();
      if( oAttributes.getNamedItem("value") != null )
      {
        oResult.meBumped = enumTypeTrippleState.getInteger( oAttributes.getNamedItem("value").getNodeValue() );
      }
    }
    return oResult;
  }

  //---------------------------------------------------------------------------
  public boolean evaluateTree(clsImagePerception poImage, clsImageAbstract poAbstractImage, int[] poCompareResult, /*clsContainerComplexEmotion poBrainsComplexEmotions,*/ clsContainerPerceptions poBrainsPerceptions, clsIdentity poBrainsIdentity)
  //---------------------------------------------------------------------------
  {
    if( meOptionalType != enumOptionalType.TOPT_OPTIONAL )
    {
      // for optional leafes we get a counter in the match list, but not in the list of all entries -->
      // compareResultValue increases if optional leafs match (can lead to more than 100% )
      poCompareResult[1]++;
    }
    boolean oResult = false;
    if( compare( poBrainsPerceptions.get(poBrainsPerceptions.moPerceptions.size()-1).moBumped ) )
    {
      //Engine.log.println( "Match with leaf: " + this.toString() );
      poCompareResult[0]++;
      oResult = true;
    }
    return oResult;
  }
  
  //---------------------------------------------------------------------------
  public void weight(clsImagePerception pImage, clsImageAbstract aImage, clsRuleCompareResult compareResult)
  //--------------------------------------------------------------------------- 
  {
    //return E_NOTIMPL; ;-)
  }

  //---------------------------------------------------------------------------
  public boolean compare(clsPerceptionBumped poBumped)
  //---------------------------------------------------------------------------
  {
    //leafBubblesVisible info:
    boolean nResult = false;

    int nIsBumped = 0; 
    if( poBumped.isBumped() ) nIsBumped = 1;

    if( meCompareOperator.compareInteger(nIsBumped, meBumped) )
    {
      nResult = true;
    }
    if( mnNegated )
    {
      nResult = !nResult;
    }
    return nResult;
  }

  //---------------------------------------------------------------------------
  public String toString() 
  //---------------------------------------------------------------------------
  {
    String oRetValue = "clsLeafBumped: ";
    oRetValue += super.toString();
    oRetValue += " isBumped="+enumTypeTrippleState.getString(meBumped);
    return oRetValue;
  }
};

