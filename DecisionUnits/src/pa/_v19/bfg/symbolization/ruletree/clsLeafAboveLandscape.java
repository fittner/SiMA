// File clsRuleTreeLeaf.java
// May 02, 2006
//

// Belongs to package
package pa._v19.bfg.symbolization.ruletree;

// Imports
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

import pa._v19.bfg.symbolization.brainimages.clsIdentity;
import pa._v19.bfg.symbolization.brainimages.clsImageAbstract;
import pa._v19.bfg.symbolization.brainimages.clsImagePerception;

import du.itf.sensors.clsDataBase;
import du.itf.sensors.clsSensorData;
//import bfg.symbolization.brainimages.clsPerceptionAboveLandscape;
import bfg.utils.enums.eOptional;
import bfg.utils.enumsOld.enumTypeLandscape;
import bfg.utils.enumsOld.enumTypeTrippleState;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 * @deprecated
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
  public boolean evaluateTree( clsSensorData poPerception, 
			  				   clsIdentity poBrainsIdentity, 
			  				   int[] poCompareResult)
  //---------------------------------------------------------------------------
  {
//    Engine.log.println("clsLeafAboveEnergySource::evaluateTree");
    if( meOptionalType != eOptional.OPTIONAL )
    {
      // for optional leafes we get a counter in the match list, but not in the list of all entries -->
      // compareResultValue increases if optional leafs match (can lead to more than 100% )
      poCompareResult[1]++;
    }
    boolean oResult = false;
    if( compare( null))//new clsPerceptionAboveLandscape() ) )
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


  /* (non-Javadoc)
   *
   * @author langr
   * 15.09.2009, 13:39:07
   * 
   * @see bfg.symbolization.ruletree.clsRuleTreeLeaf#compare(decisionunit.itf.sensors.clsDataBase)
   */
  @Override
  public boolean compare(clsDataBase poData) {
//    //leafBubblesVisible info:
//    boolean nResult = false;
//
//    if( meCompareOperator.compareInteger(poAboveLandscape.mnLandscapeType, meAboveLandscape) )
//    {
//      nResult = true;
//    }
//    if( mnNegated )
//    {
//      nResult = !nResult;
//    }
  	return false;
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

