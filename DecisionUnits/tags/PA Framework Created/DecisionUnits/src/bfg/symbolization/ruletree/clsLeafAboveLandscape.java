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
import bfg.symbolization.brainimages.clsPerceptionAboveLandscape;
import bfg.utils.enums.enumOptionalType;
import bfg.utils.enums.enumTypeLandscape;
import bfg.utils.enums.enumTypeTrippleState;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 *
 */
class clsLeafAboveLandscape extends clsRuleTreeLeaf
{                               
  public int meAboveLandscape = enumTypeLandscape.TLANDSCAPE_UNDEFINED;

  //---------------------------------------------------------------------------
  public static clsRuleTreeElement create(Node poNode)
  //---------------------------------------------------------------------------
  {
//    Engine.log.println("clsLeafAboveEnergySource::evaluateTree");
    clsLeafAboveLandscape oResult = new clsLeafAboveLandscape();
    if( oResult != null )
    {
      NamedNodeMap oAttributes = poNode.getAttributes();
      if( oAttributes.getNamedItem("value") != null )
      {
        oResult.meAboveLandscape = enumTypeLandscape.getInteger( oAttributes.getNamedItem("value").getNodeValue() );
      }
    }
    return oResult;
  }

  //---------------------------------------------------------------------------                                    
  @Override
  public boolean evaluateTree(clsImagePerception poImage, clsImageAbstract poAbstractImage, int[] poCompareResult, /*clsContainerComplexEmotion poBrainsComplexEmotions,*/ clsContainerPerceptions poBrainsPerceptions, clsIdentity poBrainsIdentity)
  //---------------------------------------------------------------------------
  {
//    Engine.log.println("clsLeafAboveEnergySource::evaluateTree");
    if( meOptionalType != enumOptionalType.TOPT_OPTIONAL )
    {
      // for optional leafes we get a counter in the match list, but not in the list of all entries -->
      // compareResultValue increases if optional leafs match (can lead to more than 100% )
      poCompareResult[1]++;
    }
    boolean oResult = false;
    if( compare( poBrainsPerceptions.get(poBrainsPerceptions.moPerceptions.size()-1).moAboveLandscape ) )
    {
      //Engine.log.println( "Match with leaf: " + this.toString() );
      poCompareResult[0]++;
      oResult = true;
    }
    return oResult;
  }
  
  //---------------------------------------------------------------------------
  @Override
  public void weight(clsImagePerception pImage, clsImageAbstract aImage, clsRuleCompareResult compareResult)
  //--------------------------------------------------------------------------- 
  {
    //return E_NOTIMPL; ;-)
  }

  //---------------------------------------------------------------------------
  public boolean compare(clsPerceptionAboveLandscape poAboveLandscape)
  //---------------------------------------------------------------------------
  {
    //leafBubblesVisible info:
    boolean nResult = false;

    if( meCompareOperator.compareInteger(poAboveLandscape.mnLandscapeType, meAboveLandscape) )
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
  @Override
  public String toString() 
  //---------------------------------------------------------------------------
  {
    String oRetValue = "clsLeafAboveEnergySource: ";
    oRetValue += super.toString();
    oRetValue += " isAbove="+enumTypeTrippleState.getString(meAboveLandscape);
    return oRetValue;
  }
};


